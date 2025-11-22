package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.item.*;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;

public class FOTItems
{
    public static final Item EARTHWORMS = new Item(new Item.Properties().food(FOTFoodProperties.WORMS));
    public static final Item GRUBS = new Item(new Item.Properties().food(FOTFoodProperties.WORMS));
    public static final Item LEECHES = new Item(new Item.Properties().food(FOTFoodProperties.WORMS));

    public static final Item SPLASHTAIL = new FOTItem(new Item.Properties().food(FOTFoodProperties.SPLASHTAIL), FOTEntities.SPLASHTAIL, Splashtail.VARIANT_TO_INT);
    public static final Item PONDIE = new FOTItem(new Item.Properties().food(FOTFoodProperties.PONDIE), FOTEntities.PONDIE, Pondie.VARIANT_TO_INT);
    public static final Item ISLEHOPPER = new FOTItem(new Item.Properties().food(FOTFoodProperties.ISLEHOPPER), FOTEntities.ISLEHOPPER, Islehopper.VARIANT_TO_INT);
    public static final Item ANCIENTSCALE = new FOTItem(new Item.Properties().food(FOTFoodProperties.ANCIENTSCALE), FOTEntities.ANCIENTSCALE, Ancientscale.VARIANT_TO_INT);
    public static final Item PLENTIFIN = new FOTItem(new Item.Properties().food(FOTFoodProperties.PLENTIFIN), FOTEntities.PLENTIFIN, Plentifin.VARIANT_TO_INT);
    public static final Item WILDSPLASH = new FOTItem(new Item.Properties().food(FOTFoodProperties.WILDSPLASH), FOTEntities.WILDSPLASH, Wildsplash.VARIANT_TO_INT);
    public static final Item DEVILFISH = new FOTItem(new Item.Properties().food(FOTFoodProperties.DEVILFISH), FOTEntities.DEVILFISH, Devilfish.VARIANT_TO_INT);
    public static final Item BATTLEGILL = new FOTItem(new Item.Properties().food(FOTFoodProperties.BATTLEGILL), FOTEntities.BATTLEGILL, Battlegill.VARIANT_TO_INT);
    public static final Item WRECKER = new FOTItem(new Item.Properties().food(FOTFoodProperties.WRECKER), FOTEntities.WRECKER, Wrecker.VARIANT_TO_INT);
    public static final Item STORMFISH = new FOTItem(new Item.Properties().food(FOTFoodProperties.STORMFISH), FOTEntities.STORMFISH, Stormfish.VARIANT_TO_INT);

    public static final Item COOKED_SPLASHTAIL = new Item(new Item.Properties().food(FOTFoodProperties.COOKED_SPLASHTAIL));
    public static final Item COOKED_PONDIE = new Item(new Item.Properties().food(FOTFoodProperties.COOKED_PONDIE));
    public static final Item COOKED_ISLEHOPPER = new Item(new Item.Properties().food(FOTFoodProperties.COOKED_ISLEHOPPER));
    public static final Item COOKED_ANCIENTSCALE = new Item(new Item.Properties().food(FOTFoodProperties.COOKED_ANCIENTSCALE));
    public static final Item COOKED_PLENTIFIN = new Item(new Item.Properties().food(FOTFoodProperties.COOKED_PLENTIFIN));
    public static final Item COOKED_WILDSPLASH = new Item(new Item.Properties().food(FOTFoodProperties.COOKED_WILDSPLASH));
    public static final Item COOKED_DEVILFISH = new Item(new Item.Properties().food(FOTFoodProperties.COOKED_DEVILFISH));
    public static final Item COOKED_BATTLEGILL = new Item(new Item.Properties().food(FOTFoodProperties.COOKED_BATTLEGILL));
    public static final Item COOKED_WRECKER = new Item(new Item.Properties().food(FOTFoodProperties.COOKED_WRECKER));
    public static final Item COOKED_STORMFISH = new Item(new Item.Properties().food(FOTFoodProperties.COOKED_STORMFISH));

    public static final Item SPLASHTAIL_BUCKET = new FOTMobBucketItem(FOTEntities.SPLASHTAIL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Splashtail.VARIANT_TO_INT, new Item.Properties().stacksTo(1));
    public static final Item PONDIE_BUCKET = new FOTMobBucketItem(FOTEntities.PONDIE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Pondie.VARIANT_TO_INT, new Item.Properties().stacksTo(1));
    public static final Item ISLEHOPPER_BUCKET = new FOTMobBucketItem(FOTEntities.ISLEHOPPER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Islehopper.VARIANT_TO_INT, new Item.Properties().stacksTo(1));
    public static final Item ANCIENTSCALE_BUCKET = new FOTMobBucketItem(FOTEntities.ANCIENTSCALE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Ancientscale.VARIANT_TO_INT, new Item.Properties().stacksTo(1));
    public static final Item PLENTIFIN_BUCKET = new FOTMobBucketItem(FOTEntities.PLENTIFIN, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Plentifin.VARIANT_TO_INT, new Item.Properties().stacksTo(1));
    public static final Item WILDSPLASH_BUCKET = new FOTMobBucketItem(FOTEntities.WILDSPLASH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Wildsplash.VARIANT_TO_INT, new Item.Properties().stacksTo(1));
    public static final Item DEVILFISH_BUCKET = new FOTMobBucketItem(FOTEntities.DEVILFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Devilfish.VARIANT_TO_INT, new Item.Properties().stacksTo(1));
    public static final Item BATTLEGILL_BUCKET = new FOTMobBucketItem(FOTEntities.BATTLEGILL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Battlegill.VARIANT_TO_INT, new Item.Properties().stacksTo(1));
    public static final Item WRECKER_BUCKET = new FOTMobBucketItem(FOTEntities.WRECKER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Wrecker.VARIANT_TO_INT, new Item.Properties().stacksTo(1));
    public static final Item STORMFISH_BUCKET = new FOTMobBucketItem(FOTEntities.STORMFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Stormfish.VARIANT_TO_INT, new Item.Properties().stacksTo(1));

    public static final Item SPLASHTAIL_SPAWN_EGG = new FOTSpawnEggItem(FOTEntities.SPLASHTAIL, 10368309, 3949737, new Item.Properties());
    public static final Item PONDIE_SPAWN_EGG = new FOTSpawnEggItem(FOTEntities.PONDIE, 8553918, 6255174, new Item.Properties());
    public static final Item ISLEHOPPER_SPAWN_EGG = new FOTSpawnEggItem(FOTEntities.ISLEHOPPER, 5854313, 8600128, new Item.Properties());
    public static final Item ANCIENTSCALE_SPAWN_EGG = new FOTSpawnEggItem(FOTEntities.ANCIENTSCALE, 16224860, 7878952, new Item.Properties());
    public static final Item PLENTIFIN_SPAWN_EGG = new FOTSpawnEggItem(FOTEntities.PLENTIFIN, 12901959, 3298579, new Item.Properties());
    public static final Item WILDSPLASH_SPAWN_EGG = new FOTSpawnEggItem(FOTEntities.WILDSPLASH, 6453062, 7556888, new Item.Properties());
    public static final Item DEVILFISH_SPAWN_EGG = new FOTSpawnEggItem(FOTEntities.DEVILFISH, 8618392, 13068147, new Item.Properties());
    public static final Item BATTLEGILL_SPAWN_EGG = new FOTSpawnEggItem(FOTEntities.BATTLEGILL, 2311985, 11047794, new Item.Properties());
    public static final Item WRECKER_SPAWN_EGG = new FOTSpawnEggItem(FOTEntities.WRECKER, 12022988, 4597359, new Item.Properties());
    public static final Item STORMFISH_SPAWN_EGG = new FOTSpawnEggItem(FOTEntities.STORMFISH, 9541044, 8608620, new Item.Properties());

    public static final Item COCONUT = new ItemNameBlockItem(FOTBlocks.COCONUT_SAPLING, new Item.Properties().food(FOTFoodProperties.COCONUT));
    public static final Item BANANA = new Item(new Item.Properties().food(FOTFoodProperties.BANANA));
    public static final Item HALF_PINEAPPLE = new Item(new Item.Properties().food(FOTFoodProperties.HALF_PINEAPPLE));
    public static final Item PINEAPPLE = new PineappleItem(new Item.Properties().food(FOTFoodProperties.PINEAPPLE));
    public static final Item CROWNLESS_PINEAPPLE = new PineappleItem(new Item.Properties().food(FOTFoodProperties.PINEAPPLE));
    public static final Item PINEAPPLE_SEEDS = new PineappleBlockItem(false, new Item.Properties());
    public static final Item PINEAPPLE_CROWN = new PineappleBlockItem(true, new Item.Properties());
    public static final Item MANGO = new MangoItem(new Item.Properties().food(FOTFoodProperties.MANGO));
    public static final Item RAW_MANGO = new MangoItem(new Item.Properties().food(FOTFoodProperties.RAW_MANGO));
    public static final Item POMEGRANATE = new PomegranateItem(new Item.Properties().food(FOTFoodProperties.POMEGRANATE));
    public static final Item GUARDIAN_FRUIT = new GuardianFruitItem(new Item.Properties().food(FOTFoodProperties.GUARDIAN_FRUIT));

    public static final Item STORMFISH_POTTERY_SHERD = new Item(new Item.Properties());
    public static final Item KRAKEN_POTTERY_SHERD = new Item(new Item.Properties());
    public static final Item MEGALODON_POTTERY_SHERD = new Item(new Item.Properties());
    public static final Item GREAT_MOUTH_POTTERY_SHERD = new Item(new Item.Properties());

    public static final Item COCONUT_SIGN = new FOTSignItem(new Item.Properties().stacksTo(16), FOTBlocks.COCONUT_SIGN, FOTBlocks.COCONUT_WALL_SIGN);
    public static final Item COCONUT_HANGING_SIGN = new FOTHangingSignItem(FOTBlocks.COCONUT_HANGING_SIGN, FOTBlocks.COCONUT_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16));
    public static final Item COCONUT_BOAT = new BoatItem(false, FOTBoatTypes.COCONUT, new Item.Properties().stacksTo(1));
    public static final Item COCONUT_CHEST_BOAT = new BoatItem(true, FOTBoatTypes.COCONUT, new Item.Properties().stacksTo(1));
    public static final Item COCONUT_DOOR = new DoubleHighBlockItem(FOTBlocks.COCONUT_DOOR, new Item.Properties());

    // Forge being stupid again...
    public static Item PINK_PLUMERIA;
    public static Item LIGHT_BLUE_PLUMERIA;
    public static Item WHITE_PLUMERIA;
    public static Item BANANA_SHOOTS;
    public static Item MANGO_PIT;
    public static Item MANGO_SAPLING;
    public static Item POMEGRANATE_PLANT;
    public static Item TALL_POMEGRANATE_PLANT;
    public static Item POMEGRANATE_SEEDS;
    public static Item TROPICAL_RED_FERN;
    public static Item TROPICAL_MONSTERA;

    public static final Item COCONUT_FRONDS = blockItem(FOTBlocks.COCONUT_FRONDS);
    public static final Item BANANA_LEAVES = blockItem(FOTBlocks.BANANA_LEAVES);
    public static final Item BANANA_BLOSSOM = blockItem(FOTBlocks.BANANA_BLOSSOM);
    public static final Item UNDERRIPE_BANANA_CLUSTER = blockItem(FOTBlocks.UNDERRIPE_BANANA_CLUSTER);
    public static final Item BARELY_RIPE_BANANA_CLUSTER = blockItem(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER);
    public static final Item RIPE_BANANA_CLUSTER = blockItem(FOTBlocks.RIPE_BANANA_CLUSTER);
    public static final Item BANANA_STEM = blockItem(FOTBlocks.BANANA_STEM);
    public static final Item UNDERRIPE_PINEAPPLE_BLOCK = blockItem(FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK);
    public static final Item RIPE_PINEAPPLE_BLOCK = blockItem(FOTBlocks.RIPE_PINEAPPLE_BLOCK);
    public static final Item CROWNLESS_RIPE_PINEAPPLE_BLOCK = blockItem(FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK);
    public static final Item MANGO_LEAVES = blockItem(FOTBlocks.MANGO_LEAVES);

    public static void init()
    {
        register("earthworms", EARTHWORMS);
        register("grubs", GRUBS);
        register("leeches", LEECHES);

        register("splashtail", SPLASHTAIL);
        register("cooked_splashtail", COOKED_SPLASHTAIL);
        register("pondie", PONDIE);
        register("cooked_pondie", COOKED_PONDIE);
        register("islehopper", ISLEHOPPER);
        register("cooked_islehopper", COOKED_ISLEHOPPER);
        register("ancientscale", ANCIENTSCALE);
        register("cooked_ancientscale", COOKED_ANCIENTSCALE);
        register("plentifin", PLENTIFIN);
        register("cooked_plentifin", COOKED_PLENTIFIN);
        register("wildsplash", WILDSPLASH);
        register("cooked_wildsplash", COOKED_WILDSPLASH);
        register("devilfish", DEVILFISH);
        register("cooked_devilfish", COOKED_DEVILFISH);
        register("battlegill", BATTLEGILL);
        register("cooked_battlegill", COOKED_BATTLEGILL);
        register("wrecker", WRECKER);
        register("cooked_wrecker", COOKED_WRECKER);
        register("stormfish", STORMFISH);
        register("cooked_stormfish", COOKED_STORMFISH);

        register("splashtail_bucket", SPLASHTAIL_BUCKET);
        register("pondie_bucket", PONDIE_BUCKET);
        register("islehopper_bucket", ISLEHOPPER_BUCKET);
        register("ancientscale_bucket", ANCIENTSCALE_BUCKET);
        register("plentifin_bucket", PLENTIFIN_BUCKET);
        register("wildsplash_bucket", WILDSPLASH_BUCKET);
        register("devilfish_bucket", DEVILFISH_BUCKET);
        register("battlegill_bucket", BATTLEGILL_BUCKET);
        register("wrecker_bucket", WRECKER_BUCKET);
        register("stormfish_bucket", STORMFISH_BUCKET);

        register("splashtail_spawn_egg", SPLASHTAIL_SPAWN_EGG);
        register("pondie_spawn_egg", PONDIE_SPAWN_EGG);
        register("islehopper_spawn_egg", ISLEHOPPER_SPAWN_EGG);
        register("ancientscale_spawn_egg", ANCIENTSCALE_SPAWN_EGG);
        register("plentifin_spawn_egg", PLENTIFIN_SPAWN_EGG);
        register("wildsplash_spawn_egg", WILDSPLASH_SPAWN_EGG);
        register("devilfish_spawn_egg", DEVILFISH_SPAWN_EGG);
        register("battlegill_spawn_egg", BATTLEGILL_SPAWN_EGG);
        register("wrecker_spawn_egg", WRECKER_SPAWN_EGG);
        register("stormfish_spawn_egg", STORMFISH_SPAWN_EGG);

        register("coconut", COCONUT);
        register("banana", BANANA);
        register("half_pineapple", HALF_PINEAPPLE);
        register("pineapple", PINEAPPLE);
        register("crownless_pineapple", CROWNLESS_PINEAPPLE);
        register("pineapple_seeds", PINEAPPLE_SEEDS);
        register("pineapple_crown", PINEAPPLE_CROWN);
        register("mango", MANGO);
        register("raw_mango", RAW_MANGO);
        register("pomegranate", POMEGRANATE);
        register("guardian_fruit", GUARDIAN_FRUIT);

        register("stormfish_pottery_sherd", STORMFISH_POTTERY_SHERD);
        register("kraken_pottery_sherd", KRAKEN_POTTERY_SHERD);
        register("megalodon_pottery_sherd", MEGALODON_POTTERY_SHERD);
        register("great_mouth_pottery_sherd", GREAT_MOUTH_POTTERY_SHERD);

        register("coconut_sign", COCONUT_SIGN);
        register("coconut_hanging_sign", COCONUT_HANGING_SIGN);
        register("coconut_boat", COCONUT_BOAT);
        register("coconut_chest_boat", COCONUT_CHEST_BOAT);
        register("coconut_door", COCONUT_DOOR);

        register("coconut_fronds", COCONUT_FRONDS);
        register("banana_leaves", BANANA_LEAVES);
        register("banana_blossom", BANANA_BLOSSOM);
        register("underripe_banana_cluster", UNDERRIPE_BANANA_CLUSTER);
        register("barely_ripe_banana_cluster", BARELY_RIPE_BANANA_CLUSTER);
        register("ripe_banana_cluster", RIPE_BANANA_CLUSTER);
        register("banana_stem", BANANA_STEM);
        register("underripe_pineapple_block", UNDERRIPE_PINEAPPLE_BLOCK);
        register("ripe_pineapple_block", RIPE_PINEAPPLE_BLOCK);
        register("crownless_ripe_pineapple_block", CROWNLESS_RIPE_PINEAPPLE_BLOCK);
        register("mango_leaves", MANGO_LEAVES);
    }

    public static void initFabric()
    {
        register("pink_plumeria", PINK_PLUMERIA = blockItem(FOTBlocks.PINK_PLUMERIA));
        register("light_blue_plumeria", LIGHT_BLUE_PLUMERIA = blockItem(FOTBlocks.LIGHT_BLUE_PLUMERIA));
        register("white_plumeria", WHITE_PLUMERIA = blockItem(FOTBlocks.WHITE_PLUMERIA));
        register("banana_shoots", BANANA_SHOOTS = blockItem(FOTBlocks.BANANA_SHOOTS));
        register("mango_pit", MANGO_PIT = new ItemNameBlockItem(FOTBlocks.MANGO_PIT, new Item.Properties()));
        register("mango_sapling", MANGO_SAPLING = blockItem(FOTBlocks.MANGO_SAPLING));
        register("pomegranate_plant", POMEGRANATE_PLANT = blockItem(FOTBlocks.POMEGRANATE_PLANT));
        register("tall_pomegranate_plant", TALL_POMEGRANATE_PLANT = new DoubleHighBlockItem(FOTBlocks.TALL_POMEGRANATE_PLANT, new Item.Properties()));
        register("pomegranate_seeds", POMEGRANATE_SEEDS = new ItemNameBlockItem(FOTBlocks.POMEGRANATE_SAPLING, new Item.Properties()));
        register("tropical_red_fern", TROPICAL_RED_FERN = blockItem(FOTBlocks.TROPICAL_RED_FERN));
        register("tropical_monstera", TROPICAL_MONSTERA = blockItem(FOTBlocks.TROPICAL_MONSTERA));
    }

    public static Item blockItem(Block block)
    {
        return new BlockItem(block, new Item.Properties());
    }

    private static void register(String key, Item item)
    {
        FOTPlatform.registerItem(key, item);
    }
}