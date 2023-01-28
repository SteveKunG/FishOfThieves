package com.stevekung.fishofthieves.entity.animal;

import java.util.function.Consumer;

import com.stevekung.fishofthieves.entity.AbstractThievesFish;
import com.stevekung.fishofthieves.entity.variant.StormfishVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.StormfishVariants;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class Stormfish extends AbstractThievesFish<StormfishVariant>
{
    private static final EntityDataAccessor<StormfishVariant> VARIANT = SynchedEntityData.defineId(Stormfish.class, FOTDataSerializers.STORMFISH_VARIANT);
    public static final Consumer<Int2ObjectOpenHashMap<String>> DATA_FIX_MAP = map ->
    {
        map.defaultReturnValue("fishofthieves:ancient");
        map.put(0, "fishofthieves:ancient");
        map.put(1, "fishofthieves:shores");
        map.put(2, "fishofthieves:wild");
        map.put(3, "fishofthieves:shadow");
        map.put(4, "fishofthieves:twilight");
    };

    public Stormfish(EntityType<? extends Stormfish> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, LEECHES_FOOD, false));
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(VARIANT, StormfishVariants.ANCIENT);
    }

    @Override
    public Registry<StormfishVariant> getRegistry()
    {
        return FOTRegistry.STORMFISH_VARIANT;
    }

    @Override
    public void setVariant(StormfishVariant variant)
    {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public StormfishVariant getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public Holder<StormfishVariant> getSpawnVariant(boolean fromBucket)
    {
        return this.getSpawnVariant(this, FOTTags.FishVariant.DEFAULT_STORMFISH_SPAWNS, StormfishVariants.ANCIENT, fromBucket);
    }

    @Override
    public Consumer<Int2ObjectOpenHashMap<String>> getDataFix()
    {
        return DATA_FIX_MAP;
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.STORMFISH_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.STORMFISH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.STORMFISH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.STORMFISH_FLOP;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.4F, 0.3F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.225F : 0.11F;
    }

    @Override
    public void thunderHit(ServerLevel level, LightningBolt lightning) {}

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return LEECHES_FOOD.test(itemStack);
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, LevelAccessor level, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var isWater = level.getFluidState(blockPos.below()).is(FluidTags.WATER) && level.getBlockState(blockPos.above()).is(Blocks.WATER);
        var levelData = level.getLevelData();
        return isWater && levelData.isRaining() && levelData.isThundering() && level.canSeeSkyFromBelowWater(blockPos);
    }
}