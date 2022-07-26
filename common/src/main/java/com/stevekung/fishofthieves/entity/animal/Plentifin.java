package com.stevekung.fishofthieves.entity.animal;

import java.util.function.Consumer;

import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.registry.FOTDataSerializers;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.registry.variants.PlentifinVariant;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(VARIANT, PlentifinVariant.OLIVE);
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
        return this.getSpawnVariant(this, FishVariantTags.DEFAULT_PLENTIFIN_SPAWNS, PlentifinVariant.OLIVE, fromBucket);
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
        return this.isTrophy() ? 0.17F : 0.09F;
    }
}