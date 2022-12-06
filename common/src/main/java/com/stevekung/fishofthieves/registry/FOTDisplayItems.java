package com.stevekung.fishofthieves.registry;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;

public class FOTDisplayItems
{
    public static void displayItems(FeatureFlagSet featureFlagSet, CreativeModeTab.Output output, boolean hasOp)
    {
        output.accept(FOTBlocks.FISH_BONE);

        output.accept(FOTItems.EARTHWORMS);
        output.accept(FOTItems.GRUBS);
        output.accept(FOTItems.LEECHES);

        output.accept(FOTItems.SPLASHTAIL);
        output.accept(FOTItems.COOKED_SPLASHTAIL);
        output.accept(FOTItems.PONDIE);
        output.accept(FOTItems.COOKED_PONDIE);
        output.accept(FOTItems.ISLEHOPPER);
        output.accept(FOTItems.COOKED_ISLEHOPPER);
        output.accept(FOTItems.ANCIENTSCALE);
        output.accept(FOTItems.COOKED_ANCIENTSCALE);
        output.accept(FOTItems.PLENTIFIN);
        output.accept(FOTItems.COOKED_PLENTIFIN);
        output.accept(FOTItems.WILDSPLASH);
        output.accept(FOTItems.COOKED_WILDSPLASH);
        output.accept(FOTItems.DEVILFISH);
        output.accept(FOTItems.COOKED_DEVILFISH);
        output.accept(FOTItems.BATTLEGILL);
        output.accept(FOTItems.COOKED_BATTLEGILL);
        output.accept(FOTItems.WRECKER);
        output.accept(FOTItems.COOKED_WRECKER);
        output.accept(FOTItems.STORMFISH);
        output.accept(FOTItems.COOKED_STORMFISH);

        output.accept(FOTItems.SPLASHTAIL_BUCKET);
        output.accept(FOTItems.PONDIE_BUCKET);
        output.accept(FOTItems.ISLEHOPPER_BUCKET);
        output.accept(FOTItems.ANCIENTSCALE_BUCKET);
        output.accept(FOTItems.PLENTIFIN_BUCKET);
        output.accept(FOTItems.WILDSPLASH_BUCKET);
        output.accept(FOTItems.DEVILFISH_BUCKET);
        output.accept(FOTItems.BATTLEGILL_BUCKET);
        output.accept(FOTItems.WRECKER_BUCKET);
        output.accept(FOTItems.STORMFISH_BUCKET);

        output.accept(FOTItems.SPLASHTAIL_SPAWN_EGG);
        output.accept(FOTItems.PONDIE_SPAWN_EGG);
        output.accept(FOTItems.ISLEHOPPER_SPAWN_EGG);
        output.accept(FOTItems.ANCIENTSCALE_SPAWN_EGG);
        output.accept(FOTItems.PLENTIFIN_SPAWN_EGG);
        output.accept(FOTItems.WILDSPLASH_SPAWN_EGG);
        output.accept(FOTItems.DEVILFISH_SPAWN_EGG);
        output.accept(FOTItems.BATTLEGILL_SPAWN_EGG);
        output.accept(FOTItems.WRECKER_SPAWN_EGG);
        output.accept(FOTItems.STORMFISH_SPAWN_EGG);
    }
}