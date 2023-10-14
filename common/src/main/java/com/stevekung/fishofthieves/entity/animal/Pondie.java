package com.stevekung.fishofthieves.entity.animal;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.mojang.serialization.Dynamic;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.entity.variant.PondieVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.PondieVariants;
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
import net.minecraft.world.entity.ai.Brain;
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
    protected Brain.Provider<AbstractSchoolingThievesFish<?>> brainProvider()
    {
        return Brain.provider(MEMORY_TYPES, Stream.of(SENSOR_TYPES, List.of(FOTSensorTypes.COMMON_THIEVES_FISH_TEMPTATIONS)).flatMap(List::stream).toList());
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
        this.entityData.define(VARIANT, PondieVariants.CHARCOAL);
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
        return this.getSpawnVariant(this, FOTTags.FishVariant.DEFAULT_PONDIE_SPAWNS, PondieVariants.CHARCOAL, fromBucket);
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