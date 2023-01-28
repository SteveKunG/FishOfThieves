package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.item.FOTItem;
import com.stevekung.fishofthieves.item.FOTMobBucketItem;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;

public class FOTDisplayItems
{
    public static void displayItems(FeatureFlagSet featureFlagSet, CreativeModeTab.Output output, boolean hasOp)
    {
        output.accept(FOTBlocks.FISH_BONE);

        output.accept(FOTBlocks.OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.SPRUCE_FISH_PLAQUE);
        output.accept(FOTBlocks.BIRCH_FISH_PLAQUE);
        output.accept(FOTBlocks.JUNGLE_FISH_PLAQUE);
        output.accept(FOTBlocks.ACACIA_FISH_PLAQUE);
        output.accept(FOTBlocks.DARK_OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.MANGROVE_FISH_PLAQUE);
        output.accept(FOTBlocks.BAMBOO_FISH_PLAQUE);
        output.accept(FOTBlocks.CRIMSON_FISH_PLAQUE);
        output.accept(FOTBlocks.WARPED_FISH_PLAQUE);

        output.accept(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE);
        output.accept(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE);
        output.accept(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE);
        output.accept(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE);
        output.accept(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE);
        output.accept(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE);
        output.accept(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE);

        output.accept(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE);

        output.accept(FOTBlocks.GILDED_OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_WARPED_FISH_PLAQUE);

        output.accept(FOTItems.EARTHWORMS);
        output.accept(FOTItems.GRUBS);
        output.accept(FOTItems.LEECHES);

        FOTItem.addFishVariants(output, FOTItems.SPLASHTAIL);
        output.accept(FOTItems.COOKED_SPLASHTAIL);
        FOTItem.addFishVariants(output, FOTItems.PONDIE);
        output.accept(FOTItems.COOKED_PONDIE);
        FOTItem.addFishVariants(output, FOTItems.ISLEHOPPER);
        output.accept(FOTItems.COOKED_ISLEHOPPER);
        FOTItem.addFishVariants(output, FOTItems.ANCIENTSCALE);
        output.accept(FOTItems.COOKED_ANCIENTSCALE);
        FOTItem.addFishVariants(output, FOTItems.PLENTIFIN);
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

        FOTMobBucketItem.addFishVariantsBucket(output, FOTItems.SPLASHTAIL_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(output, FOTItems.PONDIE_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(output, FOTItems.ISLEHOPPER_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(output, FOTItems.ANCIENTSCALE_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(output, FOTItems.PLENTIFIN_BUCKET);
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