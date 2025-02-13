package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.item.FOTItem;
import com.stevekung.fishofthieves.item.FOTMobBucketItem;
import com.stevekung.fishofthieves.item.FOTSpawnEggItem;

import net.minecraft.world.item.CreativeModeTab;

public class FOTDisplayItems
{
    public static void displayFishItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output)
    {
        var holder = itemDisplayParameters.holders();

        output.accept(FOTItems.EARTHWORMS);
        output.accept(FOTItems.GRUBS);
        output.accept(FOTItems.LEECHES);

        FOTItem.addFishVariants(itemDisplayParameters, output, FOTItems.SPLASHTAIL);
        output.accept(FOTItems.COOKED_SPLASHTAIL);
        FOTItem.addFishVariants(itemDisplayParameters, output, FOTItems.PONDIE);
        output.accept(FOTItems.COOKED_PONDIE);
        FOTItem.addFishVariants(itemDisplayParameters, output, FOTItems.ISLEHOPPER);
        output.accept(FOTItems.COOKED_ISLEHOPPER);
        FOTItem.addFishVariants(itemDisplayParameters, output, FOTItems.ANCIENTSCALE);
        output.accept(FOTItems.COOKED_ANCIENTSCALE);
        FOTItem.addFishVariants(itemDisplayParameters, output, FOTItems.PLENTIFIN);
        output.accept(FOTItems.COOKED_PLENTIFIN);
        FOTItem.addFishVariants(itemDisplayParameters, output, FOTItems.WILDSPLASH);
        output.accept(FOTItems.COOKED_WILDSPLASH);
        FOTItem.addFishVariants(itemDisplayParameters, output, FOTItems.DEVILFISH);
        output.accept(FOTItems.COOKED_DEVILFISH);
        FOTItem.addFishVariants(itemDisplayParameters, output, FOTItems.BATTLEGILL);
        output.accept(FOTItems.COOKED_BATTLEGILL);
        FOTItem.addFishVariants(itemDisplayParameters, output, FOTItems.WRECKER);
        output.accept(FOTItems.COOKED_WRECKER);
        FOTItem.addFishVariants(itemDisplayParameters, output, FOTItems.STORMFISH);
        output.accept(FOTItems.COOKED_STORMFISH);

        FOTMobBucketItem.addFishVariantsBucket(itemDisplayParameters, output, FOTItems.SPLASHTAIL_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(itemDisplayParameters, output, FOTItems.PONDIE_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(itemDisplayParameters, output, FOTItems.ISLEHOPPER_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(itemDisplayParameters, output, FOTItems.ANCIENTSCALE_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(itemDisplayParameters, output, FOTItems.PLENTIFIN_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(itemDisplayParameters, output, FOTItems.WILDSPLASH_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(itemDisplayParameters, output, FOTItems.DEVILFISH_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(itemDisplayParameters, output, FOTItems.BATTLEGILL_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(itemDisplayParameters, output, FOTItems.WRECKER_BUCKET);
        FOTMobBucketItem.addFishVariantsBucket(itemDisplayParameters, output, FOTItems.STORMFISH_BUCKET);

        FOTSpawnEggItem.addTrophySpawnEgg(holder, output, FOTItems.SPLASHTAIL_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(holder, output, FOTItems.PONDIE_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(holder, output, FOTItems.ISLEHOPPER_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(holder, output, FOTItems.ANCIENTSCALE_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(holder, output, FOTItems.PLENTIFIN_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(holder, output, FOTItems.WILDSPLASH_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(holder, output, FOTItems.DEVILFISH_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(holder, output, FOTItems.BATTLEGILL_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(holder, output, FOTItems.WRECKER_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(holder, output, FOTItems.STORMFISH_SPAWN_EGG);
    }

    public static void displayMainItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output)
    {
        output.accept(FOTItems.COCONUT_LOG);
        output.accept(FOTItems.COCONUT_WOOD);
        output.accept(FOTItems.MEDIUM_COCONUT_LOG);
        output.accept(FOTItems.MEDIUM_COCONUT_WOOD);
        output.accept(FOTItems.SMALL_COCONUT_LOG);
        output.accept(FOTItems.SMALL_COCONUT_WOOD);
        output.accept(FOTItems.STRIPPED_COCONUT_LOG);
        output.accept(FOTItems.STRIPPED_COCONUT_WOOD);
        output.accept(FOTItems.STRIPPED_MEDIUM_COCONUT_LOG);
        output.accept(FOTItems.STRIPPED_MEDIUM_COCONUT_WOOD);
        output.accept(FOTItems.STRIPPED_SMALL_COCONUT_LOG);
        output.accept(FOTItems.STRIPPED_SMALL_COCONUT_WOOD);
        output.accept(FOTItems.COCONUT_PLANKS);
        output.accept(FOTItems.COCONUT_STAIRS);
        output.accept(FOTItems.COCONUT_SLAB);
        output.accept(FOTItems.COCONUT_FENCE);
        output.accept(FOTItems.COCONUT_FENCE_GATE);
        output.accept(FOTItems.COCONUT_DOOR);
        output.accept(FOTItems.COCONUT_TRAPDOOR);
        output.accept(FOTItems.COCONUT_PRESSURE_PLATE);
        output.accept(FOTItems.COCONUT_BUTTON);
        output.accept(FOTItems.COCONUT_SIGN);
        output.accept(FOTItems.COCONUT_HANGING_SIGN);
        output.accept(FOTItems.COCONUT_BOAT);
        output.accept(FOTItems.COCONUT_CHEST_BOAT);
        output.accept(FOTBlocks.COCONUT_FRONDS);
        output.accept(FOTBlocks.BANANA_STEM);
        output.accept(FOTBlocks.BANANA_LEAVES);
        output.accept(FOTBlocks.BANANA_SHOOTS);
        output.accept(FOTBlocks.BANANA_BLOSSOM);
        output.accept(FOTItems.UNDERRIPE_BANANA_CLUSTER);
        output.accept(FOTItems.BARELY_RIPE_BANANA_CLUSTER);
        output.accept(FOTItems.RIPE_BANANA_CLUSTER);
        output.accept(FOTItems.PINEAPPLE_SEEDS);
        output.accept(FOTItems.PINEAPPLE_CROWN);
        output.accept(FOTItems.UNDERRIPE_PINEAPPLE_BLOCK);
        output.accept(FOTItems.RIPE_PINEAPPLE_BLOCK);
        output.accept(FOTItems.CROWNLESS_RIPE_PINEAPPLE_BLOCK);
        output.accept(FOTBlocks.MANGO_LEAVES);
        output.accept(FOTItems.MANGO_PIT);
        output.accept(FOTBlocks.MANGO_SAPLING);
        output.accept(FOTBlocks.POMEGRANATE_PLANT);
        output.accept(FOTBlocks.TALL_POMEGRANATE_PLANT);
        output.accept(FOTItems.POMEGRANATE_SEEDS);
        output.accept(FOTItems.PRISMARIZED_LOG);

        output.accept(FOTBlocks.PINK_PLUMERIA);
        output.accept(FOTBlocks.LIGHT_BLUE_PLUMERIA);
        output.accept(FOTBlocks.WHITE_PLUMERIA);
        output.accept(FOTItems.TROPICAL_RED_FERN);
        output.accept(FOTItems.TROPICAL_MONSTERA);

        output.accept(FOTItems.FISH_BONE);

        output.accept(FOTItems.OAK_FISH_PLAQUE);
        output.accept(FOTItems.SPRUCE_FISH_PLAQUE);
        output.accept(FOTItems.BIRCH_FISH_PLAQUE);
        output.accept(FOTItems.JUNGLE_FISH_PLAQUE);
        output.accept(FOTItems.ACACIA_FISH_PLAQUE);
        output.accept(FOTItems.DARK_OAK_FISH_PLAQUE);
        output.accept(FOTItems.MANGROVE_FISH_PLAQUE);
        output.accept(FOTItems.CHERRY_FISH_PLAQUE);
        output.accept(FOTItems.BAMBOO_FISH_PLAQUE);
        output.accept(FOTItems.CRIMSON_FISH_PLAQUE);
        output.accept(FOTItems.WARPED_FISH_PLAQUE);
        output.accept(FOTItems.COCONUT_FISH_PLAQUE);

        output.accept(FOTItems.IRON_FRAME_OAK_FISH_PLAQUE);
        output.accept(FOTItems.IRON_FRAME_SPRUCE_FISH_PLAQUE);
        output.accept(FOTItems.IRON_FRAME_BIRCH_FISH_PLAQUE);
        output.accept(FOTItems.IRON_FRAME_JUNGLE_FISH_PLAQUE);
        output.accept(FOTItems.IRON_FRAME_ACACIA_FISH_PLAQUE);
        output.accept(FOTItems.IRON_FRAME_DARK_OAK_FISH_PLAQUE);
        output.accept(FOTItems.IRON_FRAME_MANGROVE_FISH_PLAQUE);
        output.accept(FOTItems.IRON_FRAME_CHERRY_FISH_PLAQUE);
        output.accept(FOTItems.IRON_FRAME_BAMBOO_FISH_PLAQUE);
        output.accept(FOTItems.IRON_FRAME_CRIMSON_FISH_PLAQUE);
        output.accept(FOTItems.IRON_FRAME_WARPED_FISH_PLAQUE);
        output.accept(FOTItems.IRON_FRAME_COCONUT_FISH_PLAQUE);

        output.accept(FOTItems.GOLDEN_FRAME_OAK_FISH_PLAQUE);
        output.accept(FOTItems.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE);
        output.accept(FOTItems.GOLDEN_FRAME_BIRCH_FISH_PLAQUE);
        output.accept(FOTItems.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE);
        output.accept(FOTItems.GOLDEN_FRAME_ACACIA_FISH_PLAQUE);
        output.accept(FOTItems.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE);
        output.accept(FOTItems.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE);
        output.accept(FOTItems.GOLDEN_FRAME_CHERRY_FISH_PLAQUE);
        output.accept(FOTItems.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE);
        output.accept(FOTItems.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE);
        output.accept(FOTItems.GOLDEN_FRAME_WARPED_FISH_PLAQUE);
        output.accept(FOTItems.GOLDEN_FRAME_COCONUT_FISH_PLAQUE);

        output.accept(FOTItems.GILDED_OAK_FISH_PLAQUE);
        output.accept(FOTItems.GILDED_SPRUCE_FISH_PLAQUE);
        output.accept(FOTItems.GILDED_BIRCH_FISH_PLAQUE);
        output.accept(FOTItems.GILDED_JUNGLE_FISH_PLAQUE);
        output.accept(FOTItems.GILDED_ACACIA_FISH_PLAQUE);
        output.accept(FOTItems.GILDED_DARK_OAK_FISH_PLAQUE);
        output.accept(FOTItems.GILDED_MANGROVE_FISH_PLAQUE);
        output.accept(FOTItems.GILDED_CHERRY_FISH_PLAQUE);
        output.accept(FOTItems.GILDED_BAMBOO_FISH_PLAQUE);
        output.accept(FOTItems.GILDED_CRIMSON_FISH_PLAQUE);
        output.accept(FOTItems.GILDED_WARPED_FISH_PLAQUE);
        output.accept(FOTItems.GILDED_COCONUT_FISH_PLAQUE);

        output.accept(FOTItems.COCONUT);
        output.accept(FOTItems.BANANA);
        output.accept(FOTItems.PINEAPPLE);
        output.accept(FOTItems.CROWNLESS_PINEAPPLE);
        output.accept(FOTItems.HALF_PINEAPPLE);
        output.accept(FOTItems.MANGO);
        output.accept(FOTItems.RAW_MANGO);
        output.accept(FOTItems.POMEGRANATE);

        output.accept(FOTItems.STORMFISH_POTTERY_SHERD);
        output.accept(FOTItems.KRAKEN_POTTERY_SHERD);
        output.accept(FOTItems.MEGALODON_POTTERY_SHERD);
    }
}