package com.stevekung.fishofthieves.block;

import net.minecraft.nbt.CompoundTag;

public interface FishPlaqueTagConverter
{
    FishPlaqueTagConverter NOOP = compoundTag ->
    {};

    void convert(CompoundTag compoundTag);
}