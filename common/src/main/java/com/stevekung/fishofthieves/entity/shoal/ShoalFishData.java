package com.stevekung.fishofthieves.entity.shoal;

import java.util.UUID;

import net.minecraft.nbt.CompoundTag;

public record ShoalFishData(String id, UUID uuid, CompoundTag data)
{
    public static final String ID_TAG = "id";
    public static final String DATA_TAG = "data";
    public static final String UUID_TAG = "uuid";
}