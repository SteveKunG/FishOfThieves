package com.stevekung.fishofthieves.item.trade;

import net.minecraft.world.item.ItemStack;

public interface TreasuredFishMapRestock
{
    String RESTOCKABLE_DATA_TAG = "RestockableData";

    default void fishofthieves$setResult(ItemStack result)
    {
        throw new AssertionError("Implemented via mixin");
    }
}