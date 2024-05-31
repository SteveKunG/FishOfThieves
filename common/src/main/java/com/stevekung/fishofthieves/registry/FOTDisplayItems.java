package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.item.FOTItem;
import com.stevekung.fishofthieves.item.FOTMobBucketItem;
import com.stevekung.fishofthieves.item.FOTSpawnEggItem;
import net.minecraft.world.item.CreativeModeTab;

public class FOTDisplayItems
{
    public static void displayItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output)
    {
        output.accept(FOTBlocks.FISH_BONE);

        output.accept(FOTBlocks.OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.SPRUCE_FISH_PLAQUE);
        output.accept(FOTBlocks.BIRCH_FISH_PLAQUE);
        output.accept(FOTBlocks.JUNGLE_FISH_PLAQUE);
        output.accept(FOTBlocks.ACACIA_FISH_PLAQUE);
        output.accept(FOTBlocks.DARK_OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.MANGROVE_FISH_PLAQUE);
        output.accept(FOTBlocks.CHERRY_FISH_PLAQUE);
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
        output.accept(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE);
        output.accept(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE);
        output.accept(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE);
        output.accept(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE);

        output.accept(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE);
        output.accept(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE);

        output.accept(FOTBlocks.GILDED_OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_CHERRY_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE);
        output.accept(FOTBlocks.GILDED_WARPED_FISH_PLAQUE);

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

        FOTSpawnEggItem.addTrophySpawnEgg(output, FOTItems.SPLASHTAIL_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(output, FOTItems.PONDIE_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(output, FOTItems.ISLEHOPPER_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(output, FOTItems.ANCIENTSCALE_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(output, FOTItems.PLENTIFIN_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(output, FOTItems.WILDSPLASH_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(output, FOTItems.DEVILFISH_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(output, FOTItems.BATTLEGILL_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(output, FOTItems.WRECKER_SPAWN_EGG);
        FOTSpawnEggItem.addTrophySpawnEgg(output, FOTItems.STORMFISH_SPAWN_EGG);
    }
}