package com.stevekung.fishofthieves.entity.animal;

import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Dynamic;
import com.stevekung.fishofthieves.entity.AbstractFlockFish;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.entity.variant.PondieVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.PondieVariants;
import net.minecraft.Util;
import net.minecraft.core.Holder;
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
    private static final EntityDataAccessor<Holder<PondieVariant>> VARIANT = SynchedEntityData.defineId(Pondie.class, FOTDataSerializers.PONDIE_VARIANT);
    public static final BiMap<String, Integer> VARIANT_TO_INT = Util.make(HashBiMap.create(), map ->
    {
        map.put("fishofthieves:charcoal", 0);
        map.put("fishofthieves:orchid", 1);
        map.put("fishofthieves:bronze", 2);
        map.put("fishofthieves:bright", 3);
        map.put("fishofthieves:moonsky", 4);
    });

    public Pondie(EntityType<? extends Pondie> entityType, Level level)
    {
        super(entityType, level, FOTRegistries.PONDIE_VARIANT, PondieVariants.CHARCOAL);
    }

    @Override
    protected Brain.Provider<AbstractFlockFish> brainProvider()
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
    public Brain<Pondie> getBrain()
    {
        return (Brain<Pondie>) super.getBrain();
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
        builder.define(VARIANT, this.registryAccess().registryOrThrow(FOTRegistries.PONDIE_VARIANT).getHolderOrThrow(PondieVariants.CHARCOAL));
    }

    @Override
    public Holder<PondieVariant> getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(Holder<PondieVariant> variant)
    {
        this.entityData.set(VARIANT, variant);
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
    public EntityDimensions getDefaultDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDefaultDimensions(pose).withEyeHeight(0.35F) : EntityDimensions.fixed(0.35F, 0.25F).withEyeHeight(0.18F);
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return WORMS.test(itemStack);
    }
}