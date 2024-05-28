package com.stevekung.fishofthieves.entity.animal;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Dynamic;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.AncientscaleVariants;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class Ancientscale extends AbstractSchoolingThievesFish<AncientscaleVariant>
{
    private static final EntityDataAccessor<AncientscaleVariant> VARIANT = SynchedEntityData.defineId(Ancientscale.class, FOTDataSerializers.ANCIENTSCALE_VARIANT);
    public static final HashBiMap<String, Integer> VARIANT_TO_INT = HashBiMap.create().put("fishofthieves:almond", 0, "fishofthieves:sapphire", 1, "fishofthieves:smoke", 2, "fishofthieves:bone", 3, "fishofthieves:starshine", 4);

    public Ancientscale(EntityType<? extends Ancientscale> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected Brain.Provider<AbstractSchoolingThievesFish<?>> brainProvider()
    {
        return Brain.provider(MEMORY_TYPES, Stream.of(SENSOR_TYPES, List.of(FOTSensorTypes.LEECHES_THIEVES_FISH_TEMPTATIONS)).flatMap(List::stream).toList());
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic)
    {
        return AbstractSchoolingThievesFishAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<AbstractSchoolingThievesFish<?>> getBrain()
    {
        return (Brain<AbstractSchoolingThievesFish<?>>) super.getBrain();
    }

    @Override
    protected void customServerAiStep()
    {
        AbstractSchoolingThievesFishAi.customServerAiStep(this, this.getBrain());
        super.customServerAiStep();
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(VARIANT, AncientscaleVariants.ALMOND);
    }

    @Override
    public Registry<AncientscaleVariant> getRegistry()
    {
        return FOTRegistry.ANCIENTSCALE_VARIANT;
    }

    @Override
    public void setVariant(AncientscaleVariant variant)
    {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public AncientscaleVariant getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public Holder<AncientscaleVariant> getSpawnVariant(boolean fromBucket)
    {
        return this.getSpawnVariant(this, FOTTags.FishVariant.DEFAULT_ANCIENTSCALE_SPAWNS, AncientscaleVariants.ALMOND, fromBucket);
    }

    @Override
    public HashBiMap<String, Integer> variantToCustomModelData()
    {
        return VARIANT_TO_INT;
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.ANCIENTSCALE_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.ANCIENTSCALE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.ANCIENTSCALE_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.ANCIENTSCALE_FLOP;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.3F, 0.25F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.35F : 0.17F;
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return LEECHES_FOOD.test(itemStack);
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, ServerLevelAccessor level, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var isSurfaceWater = WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, level, mobSpawnType, blockPos, random);
        var isWater = level.getFluidState(blockPos.below()).is(FluidTags.WATER) && level.getBlockState(blockPos.above()).is(Blocks.WATER);
        return isSurfaceWater || isWater && TerrainUtils.isInFeature(level.getLevel(), blockPos, FOTTags.Structures.ANCIENTSCALES_SPAWN_IN);
    }
}