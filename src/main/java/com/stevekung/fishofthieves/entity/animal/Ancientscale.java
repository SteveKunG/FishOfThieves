package com.stevekung.fishofthieves.entity.animal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Maps;
import com.stevekung.fishofthieves.FOTItems;
import com.stevekung.fishofthieves.FOTSoundEvents;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.GlowFish;
import com.stevekung.fishofthieves.entity.ThievesFish;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public class Ancientscale extends AbstractSchoolingFish implements GlowFish
{
    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(Ancientscale.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> TROPHY = SynchedEntityData.defineId(Ancientscale.class, EntityDataSerializers.BOOLEAN);
    private static final Map<FishVariant, ResourceLocation> GLOW_BY_TYPE = Util.make(Maps.newHashMap(), map ->
    {
        map.put(Variant.STARSHINE, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/ancientscale/starshine_glow.png"));
    });

    public Ancientscale(EntityType<? extends Ancientscale> entityType, Level level)
    {
        super(entityType, level);
        this.refreshDimensions();
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(TYPE, 0);
        this.entityData.define(TROPHY, false);
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
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putInt(VARIANT_TAG, this.getVariant().ordinal());
        compound.putBoolean(TROPHY_TAG, this.isTrophy());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);
        this.setVariant(Variant.BY_ID[compound.getInt(VARIANT_TAG)]);
        this.setTrophy(compound.getBoolean(TROPHY_TAG));
    }

    @Override
    public void saveToBucketTag(ItemStack itemStack)
    {
        super.saveToBucketTag(itemStack);
        this.saveToBucket(itemStack, this.getVariant().ordinal(), this.getVariant().getName());
    }

    @Override
    public void loadFromBucketTag(CompoundTag compound)
    {
        super.loadFromBucketTag(compound);
        this.loadFromBucket(Variant.BY_ID[compound.getInt(VARIANT_TAG)].ordinal(), compound);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
        return this.defaultFinalizeSpawn(this, reason, spawnData, dataTag, Variant.getSpawnVariant(this));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key)
    {
        if (TROPHY.equals(key))
        {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
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
        return Variant.BY_ID[this.entityData.get(TYPE)];
    }

    @Override
    public void setVariant(int id)
    {
        this.entityData.set(TYPE, id);
    }

    @Override
    public Map<FishVariant, ResourceLocation> getGlowTextureByType()
    {
        return GLOW_BY_TYPE;
    }

    @Override
    public boolean isTrophy()
    {
        return this.entityData.get(TROPHY);
    }

    @Override
    public void setTrophy(boolean trophy)
    {
        this.entityData.set(TROPHY, trophy);
    }

    public void setVariant(Variant variant)
    {
        this.setVariant(variant.ordinal());
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
            return level.random.nextInt(100) == 0 || level.random.nextInt(10) == 0 && (isInFeature(level, blockPos, StructureFeature.MINESHAFT) || isInFeature(level, blockPos, StructureFeature.STRONGHOLD));
        }),
        STARSHINE(context -> context.level().getMoonBrightness() <= 0.25F && context.level().isNight() && context.level().canSeeSkyFromBelowWater(context.blockPos()));

        public static final Variant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Variant::ordinal)).toArray(Variant[]::new);
        private final ThievesFish.Condition condition;

        Variant(ThievesFish.Condition condition)
        {
            this.condition = condition;
        }

        Variant()
        {
            this(context -> true);
        }

        public String getName()
        {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public static Variant getSpawnVariant(LivingEntity livingEntity)
        {
            var variants = Arrays.stream(BY_ID).filter(variant -> variant.condition.spawn(new ThievesFish.SpawnConditionContext((ServerLevel) livingEntity.level, livingEntity.blockPosition()))).toArray(Variant[]::new);
            return Util.getRandom(variants, livingEntity.getRandom());
        }

        private static boolean isInFeature(ServerLevel level, BlockPos blockPos, StructureFeature<?> structureFeature)
        {
            return level.structureFeatureManager().getStructureWithPieceAt(blockPos, structureFeature).isValid();
        }
    }
}