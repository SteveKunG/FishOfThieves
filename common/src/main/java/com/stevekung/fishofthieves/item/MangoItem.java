package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.world.item.Item;

public class MangoItem extends ReturnedOnConsumeItem
{
    public MangoItem(Properties properties)
    {
        super(properties);
    }

    @Override
    protected Item getReturnedItem()
    {
        return FOTItems.MANGO_PIT;
    }
}