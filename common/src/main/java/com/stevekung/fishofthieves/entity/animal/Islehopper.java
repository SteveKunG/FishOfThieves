package com.stevekung.fishofthieves.entity.animal;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
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
    public static final Consumer<Int2ObjectOpenHashMap<String>> DATA_FIX_MAP = map ->
    {
        map.defaultReturnValue("fishofthieves:stone");
        map.put(0, "fishofthieves:stone");
        map.put(1, "fishofthieves:moss");
        map.put(2, "fishofthieves:honey");
        map.put(3, "fishofthieves:raven");
        map.put(4, "fishofthieves:amethyst");
    };

    public Islehopper(EntityType<? extends Islehopper> entityType, Level level)
    {
        super(entityType, level);
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
        builder.define(VARIANT, Holder.direct(IslehopperVariants.STONE));
    }

    @Override
    public Registry<IslehopperVariant> getRegistry()
    {
        return FOTRegistry.ISLEHOPPER_VARIANT;
    }

    @Override
    public void setVariant(IslehopperVariant variant)
    {
        this.entityData.set(VARIANT, Holder.direct(variant));
    }

    @Override
    public IslehopperVariant getVariant()
    {
        return this.entityData.get(VARIANT).value();
    }

    @Override
    public Holder<IslehopperVariant> getSpawnVariant(boolean fromBucket)
    {
        return this.getSpawnVariant(this, FOTTags.FishVariant.DEFAULT_ISLEHOPPER_SPAWNS, IslehopperVariants.STONE, fromBucket);
    }

    @Override
    public Consumer<Int2ObjectOpenHashMap<String>> getDataFix()
    {
        return DATA_FIX_MAP;
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
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.3F, 0.2F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.29F : 0.15F;
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