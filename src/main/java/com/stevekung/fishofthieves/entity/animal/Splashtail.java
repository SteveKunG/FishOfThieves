package com.stevekung.fishofthieves.entity.animal;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.FishVariant;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Splashtail extends AbstractSchoolingThievesFish
{
    private static final Map<FishVariant, ResourceLocation> GLOW_BY_TYPE = Collections.singletonMap(Variant.SEAFOAM, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/splashtail/seafoam_glow.png"));

    public Splashtail(EntityType<? extends Splashtail> entityType, Level level)
    {
        super(entityType, level);
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
        return this.isTrophy() ? 0.325F : 0.165F;
    }

    @Override
    public boolean canGlow()
    {
        return this.getVariant() == Variant.SEAFOAM;
    }

    @Override
    public Variant getVariant()
    {
        return Variant.BY_ID[Mth.positiveModulo(this.entityData.get(TYPE), Variant.BY_ID.length)];
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

    public enum Variant implements FishVariant
    {
        RUBY(SpawnSelectors.always()),
        SUNNY(SpawnSelectors.dayAndSeeSky()),
        INDIGO(SpawnSelectors.always()),
        UMBER(SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.umberSplashtailProbability)),
        SEAFOAM(SpawnSelectors.nightAndSeeSky());

        public static final Variant[] BY_ID = Stream.of(values()).sorted(Comparator.comparingInt(Variant::getId)).toArray(Variant[]::new);
        private final Predicate<SpawnConditionContext> condition;

        Variant(Predicate<SpawnConditionContext> condition)
        {
            this.condition = condition;
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
        public Predicate<SpawnConditionContext> getCondition()
        {
            return this.condition;
        }
    }
}