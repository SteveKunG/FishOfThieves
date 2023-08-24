package com.stevekung.fishofthieves.entity.animal;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.mojang.serialization.Dynamic;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.entity.variant.SplashtailVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.SplashtailVariants;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Splashtail extends AbstractSchoolingThievesFish<SplashtailVariant>
{
    private static final EntityDataAccessor<SplashtailVariant> VARIANT = SynchedEntityData.defineId(Splashtail.class, FOTDataSerializers.SPLASHTAIL_VARIANT);
    public static final Consumer<Int2ObjectOpenHashMap<String>> DATA_FIX_MAP = map ->
    {
        map.defaultReturnValue("fishofthieves:ruby");
        map.put(0, "fishofthieves:ruby");
        map.put(1, "fishofthieves:sunny");
        map.put(2, "fishofthieves:indigo");
        map.put(3, "fishofthieves:umber");
        map.put(4, "fishofthieves:seafoam");
    };

    public Splashtail(EntityType<? extends Splashtail> entityType, Level level)
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
        this.level.getProfiler().push("splashtailBrain");
        this.getBrain().tick((ServerLevel) this.level, this);
        this.level.getProfiler().popPush("splashtailActivityUpdate");
        AbstractSchoolingThievesFishAi.updateActivity(this);
        this.level.getProfiler().pop();
        super.customServerAiStep();
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(VARIANT, SplashtailVariants.RUBY);
    }

    @Override
    public Registry<SplashtailVariant> getRegistry()
    {
        return FOTRegistry.SPLASHTAIL_VARIANT;
    }

    @Override
    public void setVariant(SplashtailVariant variant)
    {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public SplashtailVariant getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public Holder<SplashtailVariant> getSpawnVariant(boolean fromBucket)
    {
        return this.getSpawnVariant(this, FOTTags.FishVariant.DEFAULT_SPLASHTAIL_SPAWNS, SplashtailVariants.RUBY, fromBucket);
    }

    @Override
    public Consumer<Int2ObjectOpenHashMap<String>> getDataFix()
    {
        return DATA_FIX_MAP;
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.SPLASHTAIL_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.SPLASHTAIL_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.SPLASHTAIL_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.SPLASHTAIL_FLOP;
    }

    @Override
    public int getMaxSchoolSize()
    {
        return 5;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.45F, 0.25F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.3F : 0.15F;
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return WORMS.test(itemStack);
    }
}