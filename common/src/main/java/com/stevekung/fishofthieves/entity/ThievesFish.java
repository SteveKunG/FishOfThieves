package com.stevekung.fishofthieves.entity;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public interface ThievesFish<T extends FishData> extends PartyFish
{
    Ingredient WORMS = Ingredient.of(FOTTags.Items.WORMS);
    Ingredient EARTHWORMS_FOOD = Ingredient.of(FOTTags.Items.EARTHWORMS_FOOD);
    Ingredient GRUBS_FOOD = Ingredient.of(FOTTags.Items.GRUBS_FOOD);
    Ingredient LEECHES_FOOD = Ingredient.of(FOTTags.Items.LEECHES_FOOD);

    String VARIANT_TAG = "variant";
    String CREATIVE_TAG = "creative";
    String TROPHY_TAG = "Trophy";
    String HAS_FED_TAG = "HasFed";
    String NO_FLIP_TAG = "NoFlip";

    T getVariant();

    void setVariant(T variant);

    Holder<T> getSpawnVariant(boolean creativeBucket);

    Registry<T> getRegistry();

    boolean isTrophy();

    void setTrophy(boolean trophy);

    boolean hasFed();

    void setHasFed(boolean hasFed);

    boolean isFood(ItemStack itemStack);

    void setNoFlip(boolean noFlip);

    boolean isNoFlip();

    default float getGlowBrightness(float ageInTicks)
    {
        return 1.0F;
    }

    default boolean isFishBreached(Brain<?> brain)
    {
        return brain.hasMemoryValue(FOTMemoryModuleTypes.BREACHED_TICK) && brain.getMemory(FOTMemoryModuleTypes.BREACHED_TICK).get() > 0;
    }

    default boolean isTreasured()
    {
        return this.getVariant().isTreasured().isPresent();
    }

    default void saveToBucket(CompoundTag compound)
    {
        var variant = this.getRegistry().getKey(this.getVariant());

        if (variant != null)
        {
            compound.putString(this.getVariantKey(), variant.toString());
        }
        if (this.isTrophy())
        {
            compound.putBoolean(HAS_FED_TAG, this.hasFed());
            compound.putBoolean(TROPHY_TAG, this.isTrophy());
        }
        if (this.isNoFlip())
        {
            compound.putBoolean(NO_FLIP_TAG, this.isNoFlip());
        }
    }

    default void loadFromBucket(CompoundTag compound)
    {
        if (compound.contains(this.getVariantKey()))
        {
            var variant = this.getRegistry().get(ResourceLocation.tryParse(this.getVariantKey()));

            if (variant != null)
            {
                this.setVariant(variant);
            }
        }
        if (compound.contains(TROPHY_TAG))
        {
            this.setTrophy(compound.getBoolean(TROPHY_TAG));
        }
        if (compound.contains(HAS_FED_TAG))
        {
            this.setHasFed(compound.getBoolean(HAS_FED_TAG));
        }
        if (compound.contains(NO_FLIP_TAG))
        {
            this.setNoFlip(compound.getBoolean(NO_FLIP_TAG));
        }
    }

    default SpawnGroupData defaultFinalizeSpawn(LivingEntity livingEntity, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        var fromCreative = dataTag != null && dataTag.contains(CREATIVE_TAG);

        if (reason == MobSpawnType.BUCKET && dataTag != null && dataTag.contains(this.getVariantKey()) && !dataTag.contains(CREATIVE_TAG))
        {
            var variant = this.getRegistry().get(ResourceLocation.tryParse(dataTag.getString(this.getVariantKey())));

            if (variant != null)
            {
                this.setVariant(variant);
            }

            this.setTrophy(dataTag.getBoolean(TROPHY_TAG));
            return spawnData;
        }
        if (livingEntity.getRandom().nextFloat() < FishOfThieves.CONFIG.spawnRate.trophyProbability)
        {
            this.setTrophy(true);
            livingEntity.setHealth(FishOfThieves.CONFIG.general.trophyMaxHealth);
        }
        this.setVariant(this.getSpawnVariant(reason == MobSpawnType.BUCKET && fromCreative).value());
        return spawnData;
    }

    default Holder<T> getSpawnVariant(LivingEntity livingEntity, TagKey<T> tagKey, T defaultSpawn, boolean creativeBucket)
    {
        return this.getRegistry().getTag(tagKey).flatMap(named -> named.getRandomElement(livingEntity.getRandom())).filter(variant -> creativeBucket || variant.value().getCondition().test(SpawnSelectors.get((ServerLevel) livingEntity.level(), livingEntity.blockPosition(), livingEntity.getRandom()))).orElseGet(() -> Holder.direct(defaultSpawn));
    }

    private String getVariantKey()
    {
        return this.getRegistry().key().location().getPath();
    }

    default float calculateTreasuredGlow(Level level, BlockPos blockPos)
    {
        var dayTime = level.getDayTime() % Level.TICKS_PER_DAY;
        var skyLight = level.getBrightness(LightLayer.SKY, blockPos);
        var glowIntensityWithSkylight = (15 - skyLight) / 15.0f;
        var glowingNightTimeStart = 12500;
        var glowingNightTimeEnd = 13500;
        var glowingMorningTimeStart = 22700;

        if (dayTime >= glowingNightTimeStart && dayTime < glowingNightTimeEnd)
        {
            glowIntensityWithSkylight = Math.min(0.5f, Math.max((dayTime - glowingNightTimeStart) / 1000.0f, glowIntensityWithSkylight) + 0.05f);
        }
        else if (dayTime >= glowingNightTimeEnd && dayTime < glowingMorningTimeStart)
        {
            glowIntensityWithSkylight = 0.5f;
        }
        else if (dayTime >= glowingMorningTimeStart)
        {
            glowIntensityWithSkylight = Math.max(glowIntensityWithSkylight, 0.5f - (dayTime - glowingMorningTimeStart) / 1000.0f + 0.05f);
        }
        return Math.min(glowIntensityWithSkylight, (15 - level.getBrightness(LightLayer.BLOCK, blockPos)) / 15.0f);
    }
}