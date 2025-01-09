package com.stevekung.fishofthieves.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class PomegranateItem extends ReturnedOnConsumeItem
{
    public PomegranateItem(Properties properties)
    {
        super(properties);
    }

    @Override
    protected Item getReturnedItem()
    {
        return Items.WHEAT_SEEDS;
    }
}