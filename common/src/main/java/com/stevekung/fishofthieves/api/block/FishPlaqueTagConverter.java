package com.stevekung.fishofthieves.api.block;

import net.minecraft.nbt.CompoundTag;

public interface FishPlaqueTagConverter
{
    FishPlaqueTagConverter NOOP = compoundTag ->
    {};

    void convert(CompoundTag compoundTag);
}