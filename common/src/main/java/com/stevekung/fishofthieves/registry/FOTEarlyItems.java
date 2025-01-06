package com.stevekung.fishofthieves.registry;

import net.minecraft.world.item.ItemStack;

public interface FOTEarlyItems
{
    //@formatter:off
    ItemStack[] CAT_FOODS = {
            new ItemStack(FOTItems.SPLASHTAIL),
            new ItemStack(FOTItems.PONDIE),
            new ItemStack(FOTItems.ISLEHOPPER),
            new ItemStack(FOTItems.ANCIENTSCALE),
            new ItemStack(FOTItems.PLENTIFIN),
            new ItemStack(FOTItems.WILDSPLASH),
            new ItemStack(FOTItems.DEVILFISH),
            new ItemStack(FOTItems.BATTLEGILL),
            new ItemStack(FOTItems.WRECKER),
            new ItemStack(FOTItems.STORMFISH)
    };

    ItemStack[] PIG_FOODS = {
            new ItemStack(FOTItems.BANANA),
            new ItemStack(FOTItems.COCONUT),
            new ItemStack(FOTItems.PINEAPPLE),
            new ItemStack(FOTItems.HALF_PINEAPPLE),
            new ItemStack(FOTItems.CROWNLESS_PINEAPPLE)
    };

    ItemStack[] CHICKEN_FOODS = {
            new ItemStack(FOTItems.PINEAPPLE_SEEDS)
    };
    //@formatter:on
}