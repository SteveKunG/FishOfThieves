package com.stevekung.fishofthieves.entity.animal;

import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Dynamic;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractThievesFish;
import com.stevekung.fishofthieves.entity.ai.AbstractThievesFishAi;
import com.stevekung.fishofthieves.entity.variant.IslehopperVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.IslehopperVariants;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.PeakTypes;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class Islehopper extends AbstractThievesFish<IslehopperVariant>
{
    private static final EntityDataAccessor<Holder<IslehopperVariant>> VARIANT = SynchedEntityData.defineId(Islehopper.class, FOTDataSerializers.ISLEHOPPER_VARIANT);
    public static final BiMap<String, Integer> VARIANT_TO_INT = Util.make(HashBiMap.create(), map ->
    {
        map.put("fishofthieves:stone", 0);
        map.put("fishofthieves:moss", 1);
        map.put("fishofthieves:honey", 2);
        map.put("fishofthieves:raven", 3);
        map.put("fishofthieves:amethyst", 4);
    });

    public Islehopper(EntityType<? extends Islehopper> entityType, Level level)
    {
        super(entityType, level, FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.STONE);
    }

    @Override
    protected Brain.Provider<AbstractThievesFish<?>> brainProvider()
    {
        return Brain.provider(MEMORY_TYPES, Stream.of(SENSOR_TYPES, List.of(FOTSensorTypes.COMMON_THIEVES_FISH_TEMPTATIONS)).flatMap(List::stream).toList());
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic)
    {
        return AbstractThievesFishAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<AbstractThievesFish<?>> getBrain()
    {
        return (Brain<AbstractThievesFish<?>>) super.getBrain();
    }

    @Override
    protected void customServerAiStep()
    {
        AbstractThievesFishAi.customServerAiStep(this, this.getBrain());
        super.customServerAiStep();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(VARIANT, this.registryAccess().registryOrThrow(FOTRegistries.ISLEHOPPER_VARIANT).getHolderOrThrow(IslehopperVariants.STONE));
    }

    @Override
    public Holder<IslehopperVariant> getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(Holder<IslehopperVariant> variant)
    {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.ISLEHOPPER_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.ISLEHOPPER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.ISLEHOPPER_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.ISLEHOPPER_FLOP;
    }

    @Override
    public boolean skipAttackInteraction(Entity entity)
    {
        if (FishOfThieves.CONFIG.general.neutralFishBehavior)
        {
            var multiplier = this.isTrophy() ? 2 : 1;

            if (entity instanceof ServerPlayer serverPlayer && entity.hurt(this.damageSources().mobAttack(this), multiplier))
            {
                if (!this.isSilent())
                {
                    serverPlayer.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.PUFFER_FISH_STING, 0.0f));
                }
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * multiplier, 0), this);
            }
        }
        return false;
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDefaultDimensions(pose).withEyeHeight(0.29F) : EntityDimensions.fixed(0.3F, 0.2F).withEyeHeight(0.15F);
    }

    @Override
    public float getGlowBrightness(float ageInTicks)
    {
        return Mth.clamp(1.0F + Mth.cos(ageInTicks * 0.05f), 0.5F, 1.0F);
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return WORMS.test(itemStack);
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, ServerLevelAccessor level, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var isSurfaceWater = WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, level, mobSpawnType, blockPos, random);
        var isWater = level.getFluidState(blockPos.below()).is(FluidTags.WATER) && level.getBlockState(blockPos.above()).is(Blocks.WATER);
        var continentalness = TerrainUtils.getContinentalness(level.getLevel(), blockPos);
        var peakTypes = TerrainUtils.getPeakTypes(level.getLevel(), blockPos);

        if (level.getBiome(blockPos).is(FOTTags.Biomes.ISLEHOPPER_SPAWN_AT_COAST))
        {
            return isSurfaceWater && (peakTypes == PeakTypes.LOW || peakTypes == PeakTypes.MID) && (continentalness == Continentalness.COAST || continentalness == Continentalness.OCEAN);
        }
        return isWater && blockPos.getY() <= 0;
    }
}