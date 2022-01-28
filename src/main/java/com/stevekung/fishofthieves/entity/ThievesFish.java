package com.stevekung.fishofthieves.entity;

import java.util.Random;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FishOfThieves;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;

public interface ThievesFish extends GlowFish, PartyFish
{
    String VARIANT_TAG = "Variant";
    String TROPHY_TAG = "Trophy";
    String NAME_TAG = "Name";
    net.minecraft.tags.Tag.Named<EntityType<?>> THIEVES_FISH = TagFactory.ENTITY_TYPE.create(new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish"));

    FishVariant getVariant();
    int getSpawnVariantId(boolean bucket);
    void setVariant(int id);
    boolean isTrophy();
    void setTrophy(boolean trophy);

    default void saveToBucket(CompoundTag compound)
    {
        compound.putInt(VARIANT_TAG, this.getVariant().getId());
        compound.putBoolean(TROPHY_TAG, this.isTrophy());
        compound.putString(NAME_TAG, this.getVariant().getName());
    }

    default void loadFromBucket(CompoundTag compound)
    {
        if (compound.contains(VARIANT_TAG))
        {
            this.setVariant(compound.getInt(VARIANT_TAG));
        }
        if (compound.contains(TROPHY_TAG))
        {
            this.setTrophy(compound.getBoolean(TROPHY_TAG));
        }
    }

    default SpawnGroupData defaultFinalizeSpawn(LivingEntity livingEntity, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        if (reason == MobSpawnType.BUCKET && dataTag != null && dataTag.contains(VARIANT_TAG, Tag.TAG_INT))
        {
            this.setVariant(dataTag.getInt(VARIANT_TAG));
            this.setTrophy(dataTag.getBoolean(TROPHY_TAG));
            return spawnData;
        }
        this.randomTrophy(livingEntity);
        this.setVariant(this.getSpawnVariantId(reason == MobSpawnType.BUCKET));
        return spawnData;
    }

    private void randomTrophy(LivingEntity livingEntity)
    {
        if (livingEntity.getRandom().nextFloat() < FishOfThieves.CONFIG.spawnRate.trophyProbability)
        {
            this.setTrophy(true);
            livingEntity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FishOfThieves.CONFIG.general.trophyMaxHealth);
            livingEntity.setHealth(FishOfThieves.CONFIG.general.trophyMaxHealth);
        }
    }

    static ThievesFish.SpawnConditionContext create(LivingEntity livingEntity)
    {
        var level = (ServerLevel) livingEntity.level;
        var blockPos = livingEntity.blockPosition();
        return new ThievesFish.SpawnConditionContext(level, blockPos, livingEntity.getRandom(), level.isDay(), level.isNight(), level.isRaining(), level.canSeeSkyFromBelowWater(blockPos));
    }

    static int getSpawnVariant(LivingEntity livingEntity, FishVariant[] ids, IntFunction<FishVariant[]> generator, boolean random)
    {
        var variants = random ? Stream.of(ids).toArray(generator) : Stream.of(ids).filter(variant -> variant.getCondition().spawn(ThievesFish.create(livingEntity))).toArray(generator);
        return Util.getRandom(variants, livingEntity.getRandom()).getId();
    }

    @FunctionalInterface
    interface Condition
    {
        boolean spawn(SpawnConditionContext context);

        static Condition always()
        {
            return context -> true;
        }
    }

    record SpawnConditionContext(ServerLevel level, BlockPos blockPos, Random random, boolean isDay, boolean isNight, boolean isRaining, boolean seeSkyInWater) {}
}