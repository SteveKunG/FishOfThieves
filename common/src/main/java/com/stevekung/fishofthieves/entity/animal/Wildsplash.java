package com.stevekung.fishofthieves.entity.animal;

import java.util.function.Consumer;

import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.WildsplashVariant;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class Wildsplash extends AbstractSchoolingThievesFish<WildsplashVariant>
{
    private static final EntityDataAccessor<WildsplashVariant> VARIANT = SynchedEntityData.defineId(Wildsplash.class, FOTDataSerializers.WILDSPLASH_VARIANT);
    public static final Consumer<Int2ObjectOpenHashMap<String>> DATA_FIX_MAP = map ->
    {
        map.defaultReturnValue("fishofthieves:russet");
        map.put(0, "fishofthieves:russet");
        map.put(1, "fishofthieves:sandy");
        map.put(2, "fishofthieves:ocean");
        map.put(3, "fishofthieves:muddy");
        map.put(4, "fishofthieves:coral");
    };

    public Wildsplash(EntityType<? extends Wildsplash> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(VARIANT, WildsplashVariant.RUSSET);
    }

    @Override
    public Registry<WildsplashVariant> getRegistry()
    {
        return FOTRegistry.WILDSPLASH_VARIANT;
    }

    @Override
    public void setVariant(WildsplashVariant variant)
    {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public WildsplashVariant getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public Holder<WildsplashVariant> getSpawnVariant(boolean fromBucket)
    {
        return this.getSpawnVariant(this, FishVariantTags.DEFAULT_WILDSPLASH_SPAWNS, WildsplashVariant.RUSSET, fromBucket);
    }

    @Override
    public Consumer<Int2ObjectOpenHashMap<String>> getDataFix()
    {
        return DATA_FIX_MAP;
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
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.3F, 0.25F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.38F : 0.2F;
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var biome = levelAccessor.getBiome(blockPos);
        return biome.is(FOTTags.SPAWNS_WILDSPLASH) || WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, random);
    }
}