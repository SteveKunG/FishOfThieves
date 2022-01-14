package com.stevekung.fishofthieves;

import net.minecraft.resources.ResourceLocation;

public class FOTLootTables
{
    public static final ResourceLocation SPLASHTAIL = create("entities/splashtail");

    private static ResourceLocation create(String key)
    {
        return new ResourceLocation(FishOfThieves.MOD_ID, key);
    }
}