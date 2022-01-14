package com.stevekung.fishofthieves.entity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.stevekung.fishofthieves.FOTItems;
import com.stevekung.fishofthieves.FOTSoundEvents;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Splashtail extends AbstractSchoolingFish implements GlowFish
{
    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(Splashtail.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> TROPHY = SynchedEntityData.defineId(Splashtail.class, EntityDataSerializers.BOOLEAN);
    private static final Map<FishType, ResourceLocation> GLOW_BY_TYPE = Util.make(Maps.newHashMap(), map ->
    {
        map.put(Variant.SEAFOAM, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/splashtail/seafoam_glow.png"));
    });

    public static final String VARIANT_TAG = "Variant";
    public static final String TROPHY_TAG = "Trophy";

    public Splashtail(EntityType<? extends Splashtail> entityType, Level level)
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
        var compound = itemStack.getOrCreateTag();
        compound.putInt(VARIANT_TAG, this.getVariant().ordinal());
        compound.putBoolean(TROPHY_TAG, this.isTrophy());
    }

    @Override
    public void loadFromBucketTag(CompoundTag compound)
    {
        super.loadFromBucketTag(compound);
        this.setVariant(Variant.BY_ID[compound.getInt(VARIANT_TAG)]);
        this.setTrophy(compound.getBoolean(TROPHY_TAG));
    }

    @Override
    public int getMaxSchoolSize()
    {
        return 5;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        if (reason == MobSpawnType.BUCKET)
        {
            if (dataTag != null && dataTag.contains(VARIANT_TAG, Tag.TAG_INT))
            {
                this.setVariant(dataTag.getInt(VARIANT_TAG));
            }
            return spawnData;
        }
        if (spawnData instanceof AbstractSchoolingFish.SchoolSpawnGroupData data)
        {
            this.startFollowing(data.leader);
        }
        else
        {
            spawnData = new AbstractSchoolingFish.SchoolSpawnGroupData(this);
        }

        this.setVariant(Variant.getSpawnVariant(this));

        if (this.random.nextInt(15) == 0)
        {
            this.setTrophy(true);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
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
        var dimension = super.getDimensions(pose);
        return this.isTrophy() ? dimension : EntityDimensions.fixed(0.45F, 0.25F);
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
        return Variant.BY_ID[this.entityData.get(TYPE)];
    }

    @Override
    public Map<FishType, ResourceLocation> getGlowTextureByType()
    {
        return GLOW_BY_TYPE;
    }

    public boolean isTrophy()
    {
        return this.entityData.get(TROPHY);
    }

    public void setTrophy(boolean trophy)
    {
        if (trophy)
        {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(5.0D);
        }
        this.entityData.set(TROPHY, trophy);
    }

    private void setVariant(Variant variant)
    {
        this.setVariant(variant.ordinal());
    }

    private void setVariant(int id)
    {
        this.entityData.set(TYPE, id);
    }

    public enum Variant implements FishType
    {
        RUBY,
        SUNNY(Level::isDay),
        INDIGO,
        UMBER(level -> level.random.nextInt(100) == 0),
        SEAFOAM(Level::isNight);

        public static final Variant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Variant::ordinal)).toArray(Variant[]::new);
        private final Predicate<Level> condition;

        Variant(Predicate<Level> condition)
        {
            this.condition = condition;
        }

        Variant()
        {
            this(level -> true);
        }

        public String getName()
        {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public static Variant getSpawnVariant(LivingEntity livingEntity)
        {
            var variants = Arrays.stream(BY_ID).filter(variant -> variant.condition.apply(livingEntity.level)).toArray(Variant[]::new);
            return Util.getRandom(variants, livingEntity.getRandom());
        }
    }
}