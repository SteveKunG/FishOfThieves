package com.stevekung.fishofthieves.entity.animal;

import java.util.List;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Dynamic;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.WildsplashVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.WildsplashVariants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
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
        super(entityType, level);
    }

    @Override
    protected Brain.Provider<AbstractSchoolingThievesFish<?>> brainProvider()
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
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(VARIANT, this.registryAccess().registryOrThrow(FOTRegistries.WILDSPLASH_VARIANT).getHolderOrThrow(WildsplashVariants.RUSSET));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putString(VARIANT_TAG, this.getVariant().unwrapKey().orElse(WildsplashVariants.RUSSET).location().toString());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);
        this.readVariantTag(compound, FOTRegistries.WILDSPLASH_VARIANT);
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
    public BiMap<String, Integer> variantToCustomModelData()
    {
        return VARIANT_TO_INT;
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
        return EARTHWORMS_FOOD.test(itemStack);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData)
    {
        var holder = AbstractFishVariant.getSpawnVariant(level.getLevel(), this.registryAccess(), FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.RUSSET, this, spawnType == MobSpawnType.BUCKET);
        this.setVariant(holder);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, ServerLevelAccessor level, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var isSurfaceWater = WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, level, mobSpawnType, blockPos, random);
        var isWater = level.getFluidState(blockPos.below()).is(FluidTags.WATER) && level.getBlockState(blockPos.above()).is(Blocks.WATER);
        var biome = level.getBiome(blockPos);
        return isWater && biome.is(FOTTags.Biomes.SPAWNS_WILDSPLASH) || isSurfaceWater;
    }
}