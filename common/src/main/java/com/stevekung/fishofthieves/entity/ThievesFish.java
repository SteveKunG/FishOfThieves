package com.stevekung.fishofthieves.entity;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTTags;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;

public interface ThievesFish extends PartyFish
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

    default void saveToBucket(ItemStack bucket)
    {
        //        var variant = this.getRegistry().getKey(this.getVariant());

        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, compoundTag ->
        {
            //            if (variant != null)TODO
            //            {
            //                if (FishOfThieves.CONFIG.general.enableFishItemWithAllVariant)
            //                {
            //                    var oldMap = Util.make(new Int2ObjectOpenHashMap<>(), this.getDataFix());
            //                    var swapped = oldMap.int2ObjectEntrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
            //                    var customModelData = swapped.get(variant.toString());
            //
            //                    if (customModelData > 0)
            //                    {
            //                        compoundTag.putInt("CustomModelData", customModelData);
            //                    }
            //                }
            //                compoundTag.putString(VARIANT_TAG, variant.toString());
            //            }
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
        ThievesFish.fixData(compound, this.getDataFix());

        //        if (compound.contains(VARIANT_TAG))TODO
        //        {
        //            var variant = this.getRegistry().get(ResourceLocation.tryParse(compound.getString(VARIANT_TAG)));
        //
        //            if (variant != null)
        //            {
        //                this.setVariant(variant);
        //            }
        //        }
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

    default SpawnGroupData defaultFinalizeSpawn(LivingEntity livingEntity, MobSpawnType reason, @Nullable SpawnGroupData spawnData)
    {
        //        if (reason == MobSpawnType.BUCKET && dataTag != null && dataTag.contains(VARIANT_TAG, Tag.TAG_STRING))TODO
        //        {
        //            var variant = this.getRegistry().get(ResourceLocation.tryParse(dataTag.getString(VARIANT_TAG)));
        //
        //            if (variant != null)
        //            {
        //                this.setVariant(variant);
        //            }
        //
        //            this.setTrophy(dataTag.getBoolean(TROPHY_TAG));
        //            return spawnData;
        //        }
        if (livingEntity.getRandom().nextFloat() < FishOfThieves.CONFIG.spawnRate.trophyProbability)
        {
            this.setTrophy(true);
            livingEntity.setHealth(FishOfThieves.CONFIG.general.trophyMaxHealth);
        }
        //        this.setVariant(this.getSpawnVariant(reason == MobSpawnType.BUCKET).value());TODO
        return spawnData;
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