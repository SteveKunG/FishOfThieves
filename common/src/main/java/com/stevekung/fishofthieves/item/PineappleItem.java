package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.world.item.Item;

public class PineappleItem extends ReturnedOnConsumeItem
{
    public PineappleItem(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    protected Item getReturnedItem()
    {
        return FOTItems.HALF_PINEAPPLE;
    }
}