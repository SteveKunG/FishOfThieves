package com.stevekung.fishofthieves.entity.animal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

import com.google.common.collect.Maps;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class Pondie extends AbstractSchoolingThievesFish
{
    private static final Map<FishVariant, ResourceLocation> GLOW_BY_TYPE = Util.make(Maps.newHashMap(), map -> map.put(Variant.MOONSKY, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/pondie/moonsky_glow.png")));

    public Pondie(EntityType<? extends Pondie> entityType, Level level)
    {
        super(entityType, level);
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
    public boolean canGlow()
    {
        return this.getVariant() == Variant.MOONSKY;
    }

    @Override
    public Variant getVariant()
    {
        return Variant.byId(this.entityData.get(TYPE));
    }

    @Override
    public int getSpawnVariantId()
    {
        return Variant.getSpawnVariant(this);
    }

    @Override
    public Map<FishVariant, ResourceLocation> getGlowTextureByType()
    {
        return GLOW_BY_TYPE;
    }

    public enum Variant implements ThievesFish.FishVariant
    {
        CHARCOAL,
        ORCHID,
        BRONZE,
        BRIGHT(context -> context.level().random.nextInt(150) == 0),
        MOONSKY(context -> context.level().random.nextInt(3) == 0 && context.level().isWaterAt(context.blockPos()) && (context.level().isNight() && context.level().canSeeSkyFromBelowWater(context.blockPos()) || context.level().getBrightness(LightLayer.SKY, context.blockPos()) < 10));

        public static final Variant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Variant::getId)).toArray(Variant[]::new);
        private final ThievesFish.Condition condition;

        Variant(ThievesFish.Condition condition)
        {
            this.condition = condition;
        }

        Variant()
        {
            this(context -> true);
        }

        @Override
        public String getName()
        {
            return this.name().toLowerCase(Locale.ROOT);
        }

        @Override
        public int getId()
        {
            return this.ordinal();
        }

        public static Variant byId(int id)
        {
            var types = BY_ID;

            if (id < 0 || id >= types.length)
            {
                id = 0;
            }
            return types[id];
        }

        public static int getSpawnVariant(LivingEntity livingEntity)
        {
            var variants = Arrays.stream(BY_ID).filter(variant -> variant.condition.spawn(new ThievesFish.SpawnConditionContext((ServerLevel) livingEntity.level, livingEntity.blockPosition()))).toArray(Variant[]::new);
            return Util.getRandom(variants, livingEntity.getRandom()).getId();
        }
    }
}