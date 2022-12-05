package com.stevekung.fishofthieves.registry;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;

public class FOTDisplayItems
{
    //TODO
    public static void displayItems(FeatureFlagSet featureFlagSet, CreativeModeTab.Output output, boolean hasOp)
    {
        output.accept(FOTBlocks.FISH_BONE);
    }
}