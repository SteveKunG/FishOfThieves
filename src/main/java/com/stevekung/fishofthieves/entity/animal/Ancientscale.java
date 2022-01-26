package com.stevekung.fishofthieves.entity.animal;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.utils.TerrainUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public class Ancientscale extends AbstractSchoolingThievesFish
{
    private static final Map<ThievesFish.FishVariant, ResourceLocation> GLOW_BY_TYPE = Collections.singletonMap(Variant.STARSHINE, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/ancientscale/starshine_glow.png"));

    public Ancientscale(EntityType<? extends Ancientscale> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.ANCIENTSCALE_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.ANCIENTSCALE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.ANCIENTSCALE_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.ANCIENTSCALE_FLOP;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.3F, 0.25F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.3575F : 0.18F;
    }

    @Override
    public boolean canGlow()
    {
        return this.getVariant() == Variant.STARSHINE;
    }

    @Override
    public Variant getVariant()
    {
        return Variant.byId(this.entityData.get(TYPE));
    }

    @Override
    public int getSpawnVariantId(boolean bucket)
    {
        return ThievesFish.getSpawnVariant(this, Variant.BY_ID, Variant[]::new, bucket);
    }

    @Override
    public Map<FishVariant, ResourceLocation> getGlowTextureByType()
    {
        return GLOW_BY_TYPE;
    }

    public enum Variant implements ThievesFish.FishVariant
    {
        ALMOND,
        SAPPHIRE,
        SMOKE,
        BONE(context ->
        {
            var level = context.level();
            var blockPos = context.blockPos();
            return context.random().nextFloat() < FishOfThieves.CONFIG.spawnRate.boneAncientscaleProbability || context.random().nextInt(10) == 0 && (TerrainUtils.isInFeature(level, blockPos, StructureFeature.MINESHAFT) || TerrainUtils.isInFeature(level, blockPos, StructureFeature.STRONGHOLD));
        }),
        STARSHINE(context -> context.level().getMoonBrightness() <= 0.25F && context.isNight() && context.seeSkyInWater());

        public static final Variant[] BY_ID = Stream.of(values()).sorted(Comparator.comparingInt(Variant::getId)).toArray(Variant[]::new);
        private final ThievesFish.Condition condition;

        Variant(ThievesFish.Condition condition)
        {
            this.condition = condition;
        }

        Variant()
        {
            this(ThievesFish.Condition.always());
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

        @Override
        public ThievesFish.Condition getCondition()
        {
            return this.condition;
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
    }
}