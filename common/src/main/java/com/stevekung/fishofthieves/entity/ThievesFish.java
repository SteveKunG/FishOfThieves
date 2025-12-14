package com.stevekung.fishofthieves.entity;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;

public interface ThievesFish<T extends AbstractFishVariant> extends PartyFish, VariantHolder<Holder<T>>
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

    ResourceKey<? extends Registry<T>> getRegistryKey();

    ResourceKey<T> getDefaultKey();

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
        return this.getVariant().value().treasured().isPresent();
    }

    default void saveToBucket(ItemStack bucket)
    {
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, compoundTag ->
        {
            compoundTag.putString(this.getVariantKey(), this.getVariant().unwrapKey().orElse(this.getDefaultKey()).location().toString());

            if (this.isTrophy())
            {
                compoundTag.putBoolean(HAS_FED_TAG, this.hasFed());
                compoundTag.putBoolean(TROPHY_TAG, this.isTrophy());
            }
            if (this.isNoFlip())
            {
                compoundTag.putBoolean(NO_FLIP_TAG, this.isNoFlip());
            }
        });
    }

    default void loadFromBucket(CompoundTag compound, RegistryAccess registryAccess)
    {
        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(this.getVariantKey()))).map(resourceLocation -> ResourceKey.create(this.getRegistryKey(), resourceLocation)).flatMap(resourceKey -> registryAccess.registryOrThrow(this.getRegistryKey()).getHolder(resourceKey)).ifPresent(this::setVariant);

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

    default SpawnGroupData defaultFinalizeSpawn(ServerLevelAccessor accessor, LivingEntity livingEntity, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData)
    {
        var holder = AbstractFishVariant.getSpawnVariant(accessor.getLevel(), accessor.registryAccess(), this.getRegistryKey(), this.getDefaultKey(), livingEntity, spawnType == MobSpawnType.BUCKET);
        this.setVariant(holder);

        if (livingEntity.getRandom().nextFloat() < FishOfThieves.CONFIG.spawnRate.trophyProbability)
        {
            this.setTrophy(true);
            livingEntity.setHealth(FishOfThieves.CONFIG.general.trophyMaxHealth);
        }
        return spawnData;
    }

    private String getVariantKey()
    {
        return this.getRegistryKey().location().getPath();
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