package com.stevekung.fishofthieves.api.block;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.animal.TropicalFish;

public interface FishPlaqueTagConverter
{
    FishPlaqueTagConverter NOOP = compoundTag -> {};

    FishPlaqueTagConverter TROPICAL_FISH = compoundTag ->
    {
        if (compoundTag.contains(TropicalFish.BUCKET_VARIANT_TAG, Tag.TAG_INT))
        {
            compoundTag.putInt("Variant", compoundTag.getInt(TropicalFish.BUCKET_VARIANT_TAG));
            compoundTag.remove(TropicalFish.BUCKET_VARIANT_TAG);
        }
    };

    void convert(CompoundTag compoundTag);
}