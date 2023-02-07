package com.stevekung.fishofthieves.entity.animal;

import java.util.function.Consumer;

import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.variant.PlentifinVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.PlentifinVariants;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

public class Plentifin extends AbstractSchoolingThievesFish<PlentifinVariant>
{
    private static final EntityDataAccessor<PlentifinVariant> VARIANT = SynchedEntityData.defineId(Plentifin.class, FOTDataSerializers.PLENTIFIN_VARIANT);
    public static final Consumer<Int2ObjectOpenHashMap<String>> DATA_FIX_MAP = map ->
    {
        map.defaultReturnValue("fishofthieves:olive");
        map.put(0, "fishofthieves:olive");
        map.put(1, "fishofthieves:amber");
        map.put(2, "fishofthieves:cloudy");
        map.put(3, "fishofthieves:bonedust");
        map.put(4, "fishofthieves:watery");
    };

    public Plentifin(EntityType<? extends Plentifin> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, EARTHWORMS_FOOD, false));
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(VARIANT, PlentifinVariants.OLIVE);
    }

    @Override
    public Registry<PlentifinVariant> getRegistry()
    {
        return FOTRegistry.PLENTIFIN_VARIANT;
    }

    @Override
    public void setVariant(PlentifinVariant variant)
    {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public PlentifinVariant getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public Holder<PlentifinVariant> getSpawnVariant(boolean fromBucket)
    {
        return this.getSpawnVariant(this, FOTTags.FishVariant.DEFAULT_PLENTIFIN_SPAWNS, PlentifinVariants.OLIVE, fromBucket);
    }

    @Override
    public Consumer<Int2ObjectOpenHashMap<String>> getDataFix()
    {
        return DATA_FIX_MAP;
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
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.275F, 0.25F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.18F : 0.09F;
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return EARTHWORMS_FOOD.test(itemStack);
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, ServerLevelAccessor level, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var isSurfaceWater = WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, level, mobSpawnType, blockPos, random);
        var isWater = level.getFluidState(blockPos.below()).is(FluidTags.WATER) && level.getBlockState(blockPos.above()).is(Blocks.WATER);
        return isSurfaceWater || isWater && (TerrainUtils.isInFeature(level.getLevel(), blockPos, StructureTags.MINESHAFT) || TerrainUtils.isInFeature(level.getLevel(), blockPos, BuiltinStructures.STRONGHOLD));
    }
}