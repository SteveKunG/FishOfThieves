package com.stevekung.fishofthieves.registry;

import net.minecraft.world.item.ItemStack;

public interface FOTEarlyItems
{
    interface Cat
    {
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
    }

    interface Pig
    {
        ItemStack[] PIG_FOODS = {
                new ItemStack(FOTItems.BANANA),
                new ItemStack(FOTItems.COCONUT),
                new ItemStack(FOTItems.PINEAPPLE),
                new ItemStack(FOTItems.HALF_PINEAPPLE),
                new ItemStack(FOTItems.CROWNLESS_PINEAPPLE),
                new ItemStack(FOTItems.MANGO),
                new ItemStack(FOTItems.RAW_MANGO),
                new ItemStack(FOTItems.POMEGRANATE),
                new ItemStack(FOTItems.GUARDIAN_FRUIT),
                new ItemStack(FOTBlocks.BANANA_BLOSSOM)
        };
    }

    interface Chicken
    {
        ItemStack[] CHICKEN_FOODS = {
                new ItemStack(FOTItems.PINEAPPLE_SEEDS),
                new ItemStack(FOTItems.POMEGRANATE_SEEDS)
        };
    }
}