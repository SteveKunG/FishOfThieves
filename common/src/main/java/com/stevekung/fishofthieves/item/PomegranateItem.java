package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.world.item.Item;

public class PomegranateItem extends ReturnedOnConsumeItem
{
    public PomegranateItem(Properties properties)
    {
        super(properties);
    }

    @Override
    protected Item getReturnedItem()
    {
        return FOTItems.POMEGRANATE_SEEDS;
    }
}