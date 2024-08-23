package com.stevekung.fishofthieves.entity.animal;

import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Dynamic;
import com.stevekung.fishofthieves.entity.AbstractFlockFish;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.entity.variant.PlentifinVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.PlentifinVariants;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class Plentifin extends AbstractSchoolingThievesFish<PlentifinVariant>
{
    private static final EntityDataAccessor<Holder<PlentifinVariant>> VARIANT = SynchedEntityData.defineId(Plentifin.class, FOTDataSerializers.PLENTIFIN_VARIANT);
    public static final BiMap<String, Integer> VARIANT_TO_INT = Util.make(HashBiMap.create(), map ->
    {
        map.put("fishofthieves:olive", 0);
        map.put("fishofthieves:amber", 1);
        map.put("fishofthieves:cloudy", 2);
        map.put("fishofthieves:bonedust", 3);
        map.put("fishofthieves:watery", 4);
    });

    public Plentifin(EntityType<? extends Plentifin> entityType, Level level)
    {
        super(entityType, level, FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.OLIVE);
    }

    @Override
    protected Brain.Provider<AbstractFlockFish> brainProvider()
    {
        return Brain.provider(MEMORY_TYPES, Stream.of(SENSOR_TYPES, List.of(FOTSensorTypes.EARTHWORMS_THIEVES_FISH_TEMPTATIONS)).flatMap(List::stream).toList());
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic)
    {
        return AbstractSchoolingThievesFishAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<Plentifin> getBrain()
    {
        return (Brain<Plentifin>) super.getBrain();
    }

    @Override
    protected void customServerAiStep()
    {
        AbstractSchoolingThievesFishAi.customServerAiStep(this, this.getBrain());
        super.customServerAiStep();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(VARIANT, this.registryAccess().registryOrThrow(FOTRegistries.PLENTIFIN_VARIANT).getHolderOrThrow(PlentifinVariants.OLIVE));
    }

    @Override
    public Holder<PlentifinVariant> getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(Holder<PlentifinVariant> variant)
    {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.PLENTIFIN_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.PLENTIFIN_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.PLENTIFIN_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.PLENTIFIN_FLOP;
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDefaultDimensions(pose).withEyeHeight(0.18F) : EntityDimensions.fixed(0.275F, 0.25F).withEyeHeight(0.09F);
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return itemStack.is(EARTHWORMS_FOOD);
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, ServerLevelAccessor level, EntitySpawnReason EntitySpawnReason, BlockPos blockPos, RandomSource random)
    {
        var isSurfaceWater = WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, level, EntitySpawnReason, blockPos, random);
        var isWater = level.getFluidState(blockPos.below()).is(FluidTags.WATER) && level.getBlockState(blockPos.above()).is(Blocks.WATER);
        return isSurfaceWater || isWater && TerrainUtils.isInFeature(level.getLevel(), blockPos, FOTTags.Structures.PLENTIFINS_SPAWN_IN);
    }
}