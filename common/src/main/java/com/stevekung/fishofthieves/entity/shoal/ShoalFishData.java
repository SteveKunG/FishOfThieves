package com.stevekung.fishofthieves.entity.shoal;

import net.minecraft.nbt.CompoundTag;

public record ShoalFishData(String id, CompoundTag data)
{
    public static final String ID_TAG = "id";
    public static final String DATA_TAG = "data";
}