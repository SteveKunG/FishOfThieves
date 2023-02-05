package com.stevekung.fishofthieves.entity;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface ThievesFish<T extends FishData> extends PartyFish
{
    Ingredient WORMS = Ingredient.of(FOTTags.Items.WORMS);
    Ingredient EARTHWORMS_FOOD = Ingredient.of(FOTTags.Items.EARTHWORMS_FOOD);
    Ingredient GRUBS_FOOD = Ingredient.of(FOTTags.Items.GRUBS_FOOD);
    Ingredient LEECHES_FOOD = Ingredient.of(FOTTags.Items.LEECHES_FOOD);

    String OLD_VARIANT_TAG = "Variant";
    String OLD_NAME_TAG = "Name";

    String VARIANT_TAG = "variant";
    String TROPHY_TAG = "Trophy";
    String HAS_FED_TAG = "HasFed";
    String NO_FLIP_TAG = "NoFlip";

    T getVariant();

    void setVariant(T variant);

    Holder<T> getSpawnVariant(boolean fromBucket);

    Registry<T> getRegistry();

    Consumer<Int2ObjectOpenHashMap<String>> getDataFix();

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

    default void saveToBucket(CompoundTag compound)
    {
        var variant = this.getRegistry().getKey(this.getVariant());

        if (variant != null)
        {
            if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
            {
                var oldMap = Util.make(new Int2ObjectOpenHashMap<>(), this.getDataFix());
                var swapped = oldMap.int2ObjectEntrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
                var customModelData = swapped.get(variant.toString());

                if (customModelData > 0)
                {
                    compound.putInt("CustomModelData", customModelData);
                }
            }
            compound.putString(VARIANT_TAG, variant.toString());
        }
        if (this.isTrophy())
        {
            compound.putBoolean(TROPHY_TAG, this.isTrophy());
        }
        if (this.hasFed())
        {
            compound.putBoolean(HAS_FED_TAG, this.hasFed());
        }
        if (this.isNoFlip())
        {
            compound.putBoolean(NO_FLIP_TAG, this.isNoFlip());
        }
    }

    default void loadFromBucket(CompoundTag compound)
    {
        ThievesFish.fixData(compound, this.getDataFix());

        if (compound.contains(VARIANT_TAG))
        {
            var variant = this.getRegistry().get(ResourceLocation.tryParse(compound.getString(VARIANT_TAG)));

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
        if (reason == MobSpawnType.BUCKET && dataTag != null && dataTag.contains(VARIANT_TAG, Tag.TAG_STRING))
        {
            var variant = this.getRegistry().get(ResourceLocation.tryParse(dataTag.getString(VARIANT_TAG)));

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
        this.setVariant(this.getSpawnVariant(reason == MobSpawnType.BUCKET).value());
        return spawnData;
    }

    default Holder<T> getSpawnVariant(LivingEntity livingEntity, TagKey<T> tagKey, T defaultSpawn, boolean fromBucket)
    {
        return this.getRegistry().getTag(tagKey).flatMap(named -> named.getRandomElement(livingEntity.getRandom())).filter(variant -> fromBucket || variant.value().getCondition().test(SpawnSelectors.get(livingEntity))).orElseGet(() -> Holder.direct(defaultSpawn));
    }

    static void fixData(CompoundTag compound, Consumer<Int2ObjectOpenHashMap<String>> consumer)
    {
        if (compound.contains(OLD_VARIANT_TAG, Tag.TAG_INT))
        {
            var variant = compound.getInt(OLD_VARIANT_TAG);
            var oldMap = Util.make(new Int2ObjectOpenHashMap<>(), consumer);
            compound.remove(OLD_VARIANT_TAG);
            compound.putString(VARIANT_TAG, oldMap.get(variant));
        }
        if (compound.contains(OLD_NAME_TAG))
        {
            compound.remove(OLD_NAME_TAG);
        }
    }
}