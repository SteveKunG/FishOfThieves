package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.item.*;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;

public class FOTItems
{
    public static final Item EARTHWORMS = register("earthworms", new Item(new Item.Properties().food(FOTFoodProperties.WORMS)));
    public static final Item GRUBS = register("grubs", new Item(new Item.Properties().food(FOTFoodProperties.WORMS)));
    public static final Item LEECHES = register("leeches", new Item(new Item.Properties().food(FOTFoodProperties.WORMS)));

    public static final Item SPLASHTAIL = register("splashtail", new FOTItem(new Item.Properties().food(FOTFoodProperties.SPLASHTAIL), FOTEntities.SPLASHTAIL, FOTRegistries.SPLASHTAIL_VARIANT));
    public static final Item PONDIE = register("pondie", new FOTItem(new Item.Properties().food(FOTFoodProperties.PONDIE), FOTEntities.PONDIE, FOTRegistries.PONDIE_VARIANT));
    public static final Item ISLEHOPPER = register("islehopper", new FOTItem(new Item.Properties().food(FOTFoodProperties.ISLEHOPPER), FOTEntities.ISLEHOPPER, FOTRegistries.ISLEHOPPER_VARIANT));
    public static final Item ANCIENTSCALE = register("ancientscale", new FOTItem(new Item.Properties().food(FOTFoodProperties.ANCIENTSCALE), FOTEntities.ANCIENTSCALE, FOTRegistries.ANCIENTSCALE_VARIANT));
    public static final Item PLENTIFIN = register("plentifin", new FOTItem(new Item.Properties().food(FOTFoodProperties.PLENTIFIN), FOTEntities.PLENTIFIN, FOTRegistries.PLENTIFIN_VARIANT));
    public static final Item WILDSPLASH = register("wildsplash", new FOTItem(new Item.Properties().food(FOTFoodProperties.WILDSPLASH), FOTEntities.WILDSPLASH, FOTRegistries.WILDSPLASH_VARIANT));
    public static final Item DEVILFISH = register("devilfish", new FOTItem(new Item.Properties().food(FOTFoodProperties.DEVILFISH), FOTEntities.DEVILFISH, FOTRegistries.DEVILFISH_VARIANT));
    public static final Item BATTLEGILL = register("battlegill", new FOTItem(new Item.Properties().food(FOTFoodProperties.BATTLEGILL), FOTEntities.BATTLEGILL, FOTRegistries.BATTLEGILL_VARIANT));
    public static final Item WRECKER = register("wrecker", new FOTItem(new Item.Properties().food(FOTFoodProperties.WRECKER), FOTEntities.WRECKER, FOTRegistries.WRECKER_VARIANT));
    public static final Item STORMFISH = register("stormfish", new FOTItem(new Item.Properties().food(FOTFoodProperties.STORMFISH), FOTEntities.STORMFISH, FOTRegistries.STORMFISH_VARIANT));

    public static final Item COOKED_SPLASHTAIL = register("cooked_splashtail", new Item(new Item.Properties().food(FOTFoodProperties.COOKED_SPLASHTAIL)));
    public static final Item COOKED_PONDIE = register("cooked_pondie", new Item(new Item.Properties().food(FOTFoodProperties.COOKED_PONDIE)));
    public static final Item COOKED_ISLEHOPPER = register("cooked_islehopper", new Item(new Item.Properties().food(FOTFoodProperties.COOKED_ISLEHOPPER)));
    public static final Item COOKED_ANCIENTSCALE = register("cooked_ancientscale", new Item(new Item.Properties().food(FOTFoodProperties.COOKED_ANCIENTSCALE)));
    public static final Item COOKED_PLENTIFIN = register("cooked_plentifin", new Item(new Item.Properties().food(FOTFoodProperties.COOKED_PLENTIFIN)));
    public static final Item COOKED_WILDSPLASH = register("cooked_wildsplash", new Item(new Item.Properties().food(FOTFoodProperties.COOKED_WILDSPLASH)));
    public static final Item COOKED_DEVILFISH = register("cooked_devilfish", new Item(new Item.Properties().food(FOTFoodProperties.COOKED_DEVILFISH)));
    public static final Item COOKED_BATTLEGILL = register("cooked_battlegill", new Item(new Item.Properties().food(FOTFoodProperties.COOKED_BATTLEGILL)));
    public static final Item COOKED_WRECKER = register("cooked_wrecker", new Item(new Item.Properties().food(FOTFoodProperties.COOKED_WRECKER)));
    public static final Item COOKED_STORMFISH = register("cooked_stormfish", new Item(new Item.Properties().food(FOTFoodProperties.COOKED_STORMFISH)));

    public static final Item SPLASHTAIL_BUCKET = register("splashtail_bucket", new FOTMobBucketItem(FOTEntities.SPLASHTAIL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.SPLASHTAIL_VARIANT, new Item.Properties().stacksTo(1)));
    public static final Item PONDIE_BUCKET = register("pondie_bucket", new FOTMobBucketItem(FOTEntities.PONDIE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.PONDIE_VARIANT, new Item.Properties().stacksTo(1)));
    public static final Item ISLEHOPPER_BUCKET = register("islehopper_bucket", new FOTMobBucketItem(FOTEntities.ISLEHOPPER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.ISLEHOPPER_VARIANT, new Item.Properties().stacksTo(1)));
    public static final Item ANCIENTSCALE_BUCKET = register("ancientscale_bucket", new FOTMobBucketItem(FOTEntities.ANCIENTSCALE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.ANCIENTSCALE_VARIANT, new Item.Properties().stacksTo(1)));
    public static final Item PLENTIFIN_BUCKET = register("plentifin_bucket", new FOTMobBucketItem(FOTEntities.PLENTIFIN, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.PLENTIFIN_VARIANT, new Item.Properties().stacksTo(1)));
    public static final Item WILDSPLASH_BUCKET = register("wildsplash_bucket", new FOTMobBucketItem(FOTEntities.WILDSPLASH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.WILDSPLASH_VARIANT, new Item.Properties().stacksTo(1)));
    public static final Item DEVILFISH_BUCKET = register("devilfish_bucket", new FOTMobBucketItem(FOTEntities.DEVILFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.DEVILFISH_VARIANT, new Item.Properties().stacksTo(1)));
    public static final Item BATTLEGILL_BUCKET = register("battlegill_bucket", new FOTMobBucketItem(FOTEntities.BATTLEGILL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.BATTLEGILL_VARIANT, new Item.Properties().stacksTo(1)));
    public static final Item WRECKER_BUCKET = register("wrecker_bucket", new FOTMobBucketItem(FOTEntities.WRECKER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.WRECKER_VARIANT, new Item.Properties().stacksTo(1)));
    public static final Item STORMFISH_BUCKET = register("stormfish_bucket", new FOTMobBucketItem(FOTEntities.STORMFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.STORMFISH_VARIANT, new Item.Properties().stacksTo(1)));

    public static final Item SPLASHTAIL_SPAWN_EGG = register("splashtail_spawn_egg", new FOTSpawnEggItem(FOTEntities.SPLASHTAIL, 10368309, 3949737, new Item.Properties()));
    public static final Item PONDIE_SPAWN_EGG = register("pondie_spawn_egg", new FOTSpawnEggItem(FOTEntities.PONDIE, 8553918, 6255174, new Item.Properties()));
    public static final Item ISLEHOPPER_SPAWN_EGG = register("islehopper_spawn_egg", new FOTSpawnEggItem(FOTEntities.ISLEHOPPER, 5854313, 8600128, new Item.Properties()));
    public static final Item ANCIENTSCALE_SPAWN_EGG = register("ancientscale_spawn_egg", new FOTSpawnEggItem(FOTEntities.ANCIENTSCALE, 16224860, 7878952, new Item.Properties()));
    public static final Item PLENTIFIN_SPAWN_EGG = register("plentifin_spawn_egg", new FOTSpawnEggItem(FOTEntities.PLENTIFIN, 12901959, 3298579, new Item.Properties()));
    public static final Item WILDSPLASH_SPAWN_EGG = register("wildsplash_spawn_egg", new FOTSpawnEggItem(FOTEntities.WILDSPLASH, 6453062, 7556888, new Item.Properties()));
    public static final Item DEVILFISH_SPAWN_EGG = register("devilfish_spawn_egg", new FOTSpawnEggItem(FOTEntities.DEVILFISH, 8618392, 13068147, new Item.Properties()));
    public static final Item BATTLEGILL_SPAWN_EGG = register("battlegill_spawn_egg", new FOTSpawnEggItem(FOTEntities.BATTLEGILL, 2311985, 11047794, new Item.Properties()));
    public static final Item WRECKER_SPAWN_EGG = register("wrecker_spawn_egg", new FOTSpawnEggItem(FOTEntities.WRECKER, 12022988, 4597359, new Item.Properties()));
    public static final Item STORMFISH_SPAWN_EGG = register("stormfish_spawn_egg", new FOTSpawnEggItem(FOTEntities.STORMFISH, 9541044, 8608620, new Item.Properties()));

    public static final Item COCONUT = register("coconut", new ItemNameBlockItem(FOTBlocks.COCONUT_SAPLING, new Item.Properties().food(FOTFoodProperties.COCONUT)));
    public static final Item BANANA = register("banana", new Item(new Item.Properties().food(FOTFoodProperties.BANANA)));
    public static final Item HALF_PINEAPPLE = register("half_pineapple", new Item(new Item.Properties().food(FOTFoodProperties.HALF_PINEAPPLE)));
    public static final Item PINEAPPLE = register("pineapple", new PineappleItem(new Item.Properties().food(FOTFoodProperties.PINEAPPLE)));
    public static final Item CROWNLESS_PINEAPPLE = register("crownless_pineapple", new PineappleItem(new Item.Properties().food(FOTFoodProperties.PINEAPPLE)));
    public static final Item PINEAPPLE_SEEDS = register("pineapple_seeds", new PineappleBlockItem(false, new Item.Properties()));
    public static final Item PINEAPPLE_CROWN = register("pineapple_crown", new PineappleBlockItem(true, new Item.Properties()));
    public static final Item MANGO = register("mango", new MangoItem(new Item.Properties().food(FOTFoodProperties.MANGO)));
    public static final Item RAW_MANGO = register("raw_mango", new MangoItem(new Item.Properties().food(FOTFoodProperties.RAW_MANGO)));
    public static final Item POMEGRANATE = register("pomegranate", new PomegranateItem(new Item.Properties().food(FOTFoodProperties.POMEGRANATE)));

    public static final Item STORMFISH_POTTERY_SHERD = register("stormfish_pottery_sherd", new Item(new Item.Properties()));
    public static final Item KRAKEN_POTTERY_SHERD = register("kraken_pottery_sherd", new Item(new Item.Properties()));
    public static final Item MEGALODON_POTTERY_SHERD = register("megalodon_pottery_sherd", new Item(new Item.Properties()));

    public static final Item COCONUT_SIGN = register("coconut_sign", new FOTSignItem(new Item.Properties().stacksTo(16), FOTBlocks.COCONUT_SIGN, FOTBlocks.COCONUT_WALL_SIGN));
    public static final Item COCONUT_HANGING_SIGN = register("coconut_hanging_sign", new FOTHangingSignItem(FOTBlocks.COCONUT_HANGING_SIGN, FOTBlocks.COCONUT_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16)));
    public static final Item COCONUT_BOAT = register("coconut_boat", new BoatItem(false, FOTBoatTypes.COCONUT, new Item.Properties().stacksTo(1)));
    public static final Item COCONUT_CHEST_BOAT = register("coconut_chest_boat", new BoatItem(true, FOTBoatTypes.COCONUT, new Item.Properties().stacksTo(1)));
    public static final Item COCONUT_DOOR = register("coconut_door", new DoubleHighBlockItem(FOTBlocks.COCONUT_DOOR, new Item.Properties()));

    public static final Item MANGO_PIT = register("mango_pit", new ItemNameBlockItem(FOTBlocks.MANGO_PIT, new Item.Properties()));
    public static final Item TALL_POMEGRANATE_PLANT = register("tall_pomegranate_plant", new DoubleHighBlockItem(FOTBlocks.TALL_POMEGRANATE_PLANT, new Item.Properties()));
    public static final Item POMEGRANATE_SEEDS = register("pomegranate_seeds", new ItemNameBlockItem(FOTBlocks.POMEGRANATE_SAPLING, new Item.Properties()));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Item");
    }

    public static Item register(String key, Item item)
    {
        return Registry.register(BuiltInRegistries.ITEM, FishOfThieves.id(key), item);
    }
}