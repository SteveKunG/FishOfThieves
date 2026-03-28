package com.stevekung.fishofthieves.item.trade;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.VillagerTrade;

public interface TreasuredFishMapRestock
{
    String RESTOCKABLE_DATA_TAG = "RestockableData";

    default void fishofthieves$setResult(ItemStack result)
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void fishofthieves$setIsTreasuredFishMap()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default boolean fishofthieves$isTreasuredFishMap()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void fishofthieves$setResourceKey(ResourceKey<VillagerTrade> key)
    {
        throw new AssertionError("Implemented via mixin");
    }

    default ResourceKey<VillagerTrade> fishofthieves$getResourceKey()
    {
        throw new AssertionError("Implemented via mixin");
    }
}