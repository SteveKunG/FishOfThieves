package com.stevekung.fishofthieves.entity;

import java.util.*;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Maps;
import com.stevekung.fishofthieves.FOTItems;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Splashtail extends AbstractSchoolingFish implements GlowFish
{
    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(Splashtail.class, EntityDataSerializers.INT);
    public static final String VARIANT_TAG = "Variant";

    public Splashtail(EntityType<? extends Splashtail> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(TYPE, 0);
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.SPLASHTAIL_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.COD_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return SoundEvents.COD_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return SoundEvents.COD_FLOP;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putInt(VARIANT_TAG, this.getVariant().getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);
        this.setVariant(Variant.BY_ID[compound.getInt(VARIANT_TAG)]);
    }

    @Override
    public void saveToBucketTag(ItemStack itemStack)
    {
        super.saveToBucketTag(itemStack);
        var compound = itemStack.getOrCreateTag();
        compound.putInt(VARIANT_TAG, this.getVariant().getId());
    }

    @Override
    public void loadFromBucketTag(CompoundTag compound)
    {
        super.loadFromBucketTag(compound);
        this.setVariant(Variant.BY_ID[compound.getInt(VARIANT_TAG)]);
    }

    @Override
    public int getMaxSchoolSize()
    {
        return 5;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        if (reason == MobSpawnType.BUCKET && dataTag != null && dataTag.contains(VARIANT_TAG, 3))
        {
            this.setVariant(dataTag.getInt(VARIANT_TAG));
            return spawnData;
        }

        if (spawnData instanceof SplashtailGroupData data)
        {
            this.startFollowing(data.leader);
        }
        else
        {
            spawnData = new SplashtailGroupData(this, Variant.getCommonSpawnVariant(this.level.random), Variant.getCommonSpawnVariant(this.level.random));
        }
        this.setVariant(((SplashtailGroupData)spawnData).getVariant(this.level.random));
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    @Override
    public boolean canGlow(FishType type)
    {
        return type == Variant.SEAFOAM;
    }

    @Override
    public Variant getVariant()
    {
        return Variant.BY_ID[this.entityData.get(TYPE)];
    }

    @Override
    public Map<FishType, ResourceLocation> getGlowTextureByType()
    {
        return Util.make(Maps.newHashMap(), map ->
        {
            map.put(Variant.SEAFOAM, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/splashtail/seafoam_glow.png"));
        });
    }

    private void setVariant(Variant variant)
    {
        this.setVariant(variant.getId());
    }

    private void setVariant(int id)
    {
        this.entityData.set(TYPE, id);
    }

    public enum Variant implements FishType
    {
        RUBY(true),
        SUNNY(true),
        INDIGO(true),
        UMBER(false),
        SEAFOAM(true);

        public static final Variant[] BY_ID = Arrays.stream(Variant.values()).sorted(Comparator.comparingInt(Variant::getId)).toArray(Variant[]::new);
        private final boolean common;

        Variant(boolean common)
        {
            this.common = common;
        }

        public int getId()
        {
            return this.ordinal();
        }

        public String getName()
        {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public static Variant getCommonSpawnVariant(Random random)
        {
            return Variant.getSpawnVariant(random, true);
        }

        public static Variant getRareSpawnVariant(Random random)
        {
            return Variant.getSpawnVariant(random, false);
        }

        private static Variant getSpawnVariant(Random random, boolean common)
        {
            var variants = Arrays.stream(BY_ID).filter(variant -> variant.common == common).toArray(Variant[]::new);
            return Util.getRandom(variants, random);
        }
    }

    public static class SplashtailGroupData extends AbstractSchoolingFish.SchoolSpawnGroupData
    {
        public final Variant[] types;

        public SplashtailGroupData(AbstractSchoolingFish abstractSchoolingFish, Variant... variants)
        {
            super(abstractSchoolingFish);
            this.types = variants;
        }

        public Variant getVariant(Random random)
        {
            return this.types[random.nextInt(this.types.length)];
        }
    }
}