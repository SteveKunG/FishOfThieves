package com.stevekung.fishofthieves.item.trade;

import net.minecraft.world.item.ItemStack;

public interface TreasuredFishMapRestock
{
    String IS_TREASURED_MAP_TAG = "fishofthieves$isTreasuredMap";
    String TIER_TAG = "fishofthieves$tier";

    default boolean fishofthieves$isTreasuredFishMap()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void fishofthieves$setIsTreasuredFishMap(boolean isTreasuredMap)
    {
        throw new AssertionError("Implemented via mixin");
    }

    default int fishofthieves$getTier()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void fishofthieves$setTier(int tier)
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void fishofthieves$setResult(ItemStack result)
    {
        throw new AssertionError("Implemented via mixin");
    }
}