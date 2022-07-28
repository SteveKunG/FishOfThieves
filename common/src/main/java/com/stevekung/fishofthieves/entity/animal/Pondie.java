package com.stevekung.fishofthieves.entity.animal;

import java.util.function.Consumer;

import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.registry.FOTDataSerializers;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.PondieVariant;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Pondie extends AbstractSchoolingThievesFish<PondieVariant>
{
    private static final EntityDataAccessor<PondieVariant> VARIANT = SynchedEntityData.defineId(Pondie.class, FOTDataSerializers.PONDIE_VARIANT);
    public static final Consumer<Int2ObjectOpenHashMap<String>> DATA_FIX_MAP = map ->
    {
        map.defaultReturnValue("fishofthieves:charcoal");
        map.put(0, "fishofthieves:charcoal");
        map.put(1, "fishofthieves:orchid");
        map.put(2, "fishofthieves:bronze");
        map.put(3, "fishofthieves:bright");
        map.put(4, "fishofthieves:moonsky");
    };

    public Pondie(EntityType<? extends Pondie> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, WORMS, false));
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(VARIANT, PondieVariant.CHARCOAL);
    }

    @Override
    public Registry<PondieVariant> getRegistry()
    {
        return FOTRegistry.PONDIE_VARIANT;
    }

    @Override
    public void setVariant(PondieVariant variant)
    {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public PondieVariant getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public Holder<PondieVariant> getSpawnVariant(boolean fromBucket)
    {
        return this.getSpawnVariant(this, FishVariantTags.DEFAULT_PONDIE_SPAWNS, PondieVariant.CHARCOAL, fromBucket);
    }

    @Override
    public Consumer<Int2ObjectOpenHashMap<String>> getDataFix()
    {
        return DATA_FIX_MAP;
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.PONDIE_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.PONDIE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.PONDIE_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.PONDIE_FLOP;
    }

    @Override
    public int getMaxSchoolSize()
    {
        return 5;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.35F, 0.25F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.35F : 0.18F;
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return WORMS.test(itemStack);
    }
}