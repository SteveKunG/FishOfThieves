package com.stevekung.fishofthieves.entity.animal;

import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Dynamic;
import com.stevekung.fishofthieves.entity.AbstractFlockFish;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.entity.variant.WildsplashVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.WildsplashVariants;
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

public class Wildsplash extends AbstractSchoolingThievesFish<WildsplashVariant>
{
    private static final EntityDataAccessor<Holder<WildsplashVariant>> VARIANT = SynchedEntityData.defineId(Wildsplash.class, FOTDataSerializers.WILDSPLASH_VARIANT);
    public static final BiMap<String, Integer> VARIANT_TO_INT = Util.make(HashBiMap.create(), map ->
    {
        map.put("fishofthieves:russet", 0);
        map.put("fishofthieves:sandy", 1);
        map.put("fishofthieves:ocean", 2);
        map.put("fishofthieves:muddy", 3);
        map.put("fishofthieves:coral", 4);
    });

    public Wildsplash(EntityType<? extends Wildsplash> entityType, Level level)
    {
        super(entityType, level, FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.RUSSET);
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
    public Brain<Wildsplash> getBrain()
    {
        return (Brain<Wildsplash>) super.getBrain();
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
        builder.define(VARIANT, this.registryAccess().registryOrThrow(FOTRegistries.WILDSPLASH_VARIANT).getHolderOrThrow(WildsplashVariants.RUSSET));
    }

    @Override
    public Holder<WildsplashVariant> getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(Holder<WildsplashVariant> variant)
    {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.WILDSPLASH_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.WILDSPLASH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.WILDSPLASH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.WILDSPLASH_FLOP;
    }

    @Override
    public int getMaxSchoolSize()
    {
        return 4;
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDefaultDimensions(pose).withEyeHeight(0.38F) : EntityDimensions.fixed(0.3F, 0.25F).withEyeHeight(0.2F);
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
        var biome = level.getBiome(blockPos);
        return isWater && biome.is(FOTTags.Biomes.SPAWNS_WILDSPLASH) || isSurfaceWater;
    }
}