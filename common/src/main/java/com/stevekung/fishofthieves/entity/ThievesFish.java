package com.stevekung.fishofthieves.entity;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ServerLevelAccessor;

public interface ThievesFish<T extends AbstractFishVariant> extends PartyFish, VariantHolder<Holder<T>>
{
    TagKey<Item> WORMS = FOTTags.Items.WORMS;
    TagKey<Item> EARTHWORMS_FOOD = FOTTags.Items.EARTHWORMS_FOOD;
    TagKey<Item> GRUBS_FOOD = FOTTags.Items.GRUBS_FOOD;
    TagKey<Item> LEECHES_FOOD = FOTTags.Items.LEECHES_FOOD;

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

    default void loadFromBucket(CompoundTag compound)
    {
        compound.getBoolean(TROPHY_TAG).ifPresent(this::setTrophy);
        compound.getBoolean(HAS_FED_TAG).ifPresent(this::setHasFed);
        compound.getBoolean(NO_FLIP_TAG).ifPresent(this::setNoFlip);
    }

    default SpawnGroupData defaultFinalizeSpawn(ServerLevelAccessor accessor, LivingEntity livingEntity, EntitySpawnReason entitySpawnReason, @Nullable SpawnGroupData spawnData)
    {
        var holder = AbstractFishVariant.getSpawnVariant(accessor.getLevel(), accessor.registryAccess(), this.getRegistryKey(), this.getDefaultKey(), livingEntity, entitySpawnReason == EntitySpawnReason.BUCKET);
        this.setVariant(holder);

        if (livingEntity.getRandom().nextFloat() < FishOfThieves.CONFIG.spawnRate.trophyProbability)
        {
            this.setTrophy(true);
            livingEntity.setHealth(FishOfThieves.CONFIG.general.trophyMaxHealth);
        }
        return spawnData;
    }

    default void setRandomVariant(RegistryAccess registryAccess, RandomSource randomSource)
    {
        // Set random variant for bucket that has no data component
        var registry = registryAccess.lookupOrThrow(this.getRegistryKey());
        var muha = Util.getRandomSafe(registry.listElements().filter(holder -> holder.value().treasured().isEmpty()).toList(), randomSource);
        this.setVariant(muha.orElseGet(() -> registry.getOrThrow(this.getDefaultKey())));
        this.setTrophy(randomSource.nextBoolean());
    }
}