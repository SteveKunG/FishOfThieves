package com.stevekung.fishofthieves.entity;

import java.util.function.IntFunction;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface ThievesFish extends GlowFish, PartyFish
{
    Ingredient WORMS = Ingredient.of(FOTTags.WORMS);
    Ingredient EARTHWORMS_FOOD = Ingredient.of(FOTTags.EARTHWORMS_FOOD);
    Ingredient GRUBS_FOOD = Ingredient.of(FOTTags.GRUBS_FOOD);
    Ingredient LEECHES_FOOD = Ingredient.of(FOTTags.LEECHES_FOOD);

    String VARIANT_TAG = "Variant";
    String TROPHY_TAG = "Trophy";
    String HAS_FED_TAG = "HasFed";
    String NAME_TAG = "Name";

    FishVariant getVariant();

    int getSpawnVariantId(boolean bucket);

    void setVariant(int id);

    boolean isTrophy();

    void setTrophy(boolean trophy);

    boolean hasFed();

    void setHasFed(boolean hasFed);

    boolean isFood(ItemStack itemStack);

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
        if (livingEntity.getRandom().nextFloat() < FishOfThieves.CONFIG.spawnRate.trophyProbability)
        {
            this.setTrophy(true);
            livingEntity.setHealth(FishOfThieves.CONFIG.general.trophyMaxHealth);
        }
        this.setVariant(this.getSpawnVariantId(reason == MobSpawnType.BUCKET));
        return spawnData;
    }

    static int getSpawnVariant(LivingEntity livingEntity, FishVariant[] ids, IntFunction<FishVariant[]> generator, boolean random)
    {
        var variants = random ? Stream.of(ids).toArray(generator) : Stream.of(ids).filter(variant -> variant.getCondition().test(SpawnSelectors.get(livingEntity))).toArray(generator);
        return Util.getRandom(variants, livingEntity.getRandom()).getId();
    }
}