package com.stevekung.fishofthieves.registry;

import java.util.function.Function;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.item.FOTItem;
import com.stevekung.fishofthieves.item.FOTMobBucketItem;
import com.stevekung.fishofthieves.item.FOTSpawnEggItem;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;

public class FOTItems
{
    public static final Item EARTHWORMS = item("earthworms", properties -> new Item(properties.food(FOTFoodProperties.WORMS, FOTConsumables.WORMS)));
    public static final Item GRUBS = item("grubs", properties -> new Item(properties.food(FOTFoodProperties.WORMS, FOTConsumables.WORMS)));
    public static final Item LEECHES = item("leeches", properties -> new Item(properties.food(FOTFoodProperties.WORMS, FOTConsumables.WORMS)));

    public static final Item SPLASHTAIL = item("splashtail", properties -> new FOTItem(properties.food(FOTFoodProperties.SPLASHTAIL), FOTEntities.SPLASHTAIL, FOTRegistries.SPLASHTAIL_VARIANT));
    public static final Item PONDIE = item("pondie", properties -> new FOTItem(properties.food(FOTFoodProperties.PONDIE), FOTEntities.PONDIE, FOTRegistries.PONDIE_VARIANT));
    public static final Item ISLEHOPPER = item("islehopper", properties -> new FOTItem(properties.food(FOTFoodProperties.ISLEHOPPER), FOTEntities.ISLEHOPPER, FOTRegistries.ISLEHOPPER_VARIANT));
    public static final Item ANCIENTSCALE = item("ancientscale", properties -> new FOTItem(properties.food(FOTFoodProperties.ANCIENTSCALE), FOTEntities.ANCIENTSCALE, FOTRegistries.ANCIENTSCALE_VARIANT));
    public static final Item PLENTIFIN = item("plentifin", properties -> new FOTItem(properties.food(FOTFoodProperties.PLENTIFIN), FOTEntities.PLENTIFIN, FOTRegistries.PLENTIFIN_VARIANT));
    public static final Item WILDSPLASH = item("wildsplash", properties -> new FOTItem(properties.food(FOTFoodProperties.WILDSPLASH), FOTEntities.WILDSPLASH, FOTRegistries.WILDSPLASH_VARIANT));
    public static final Item DEVILFISH = item("devilfish", properties -> new FOTItem(properties.food(FOTFoodProperties.DEVILFISH, FOTConsumables.DEVILFISH), FOTEntities.DEVILFISH, FOTRegistries.DEVILFISH_VARIANT));
    public static final Item BATTLEGILL = item("battlegill", properties -> new FOTItem(properties.food(FOTFoodProperties.BATTLEGILL), FOTEntities.BATTLEGILL, FOTRegistries.BATTLEGILL_VARIANT));
    public static final Item WRECKER = item("wrecker", properties -> new FOTItem(properties.food(FOTFoodProperties.WRECKER), FOTEntities.WRECKER, FOTRegistries.WRECKER_VARIANT));
    public static final Item STORMFISH = item("stormfish", properties -> new FOTItem(properties.food(FOTFoodProperties.STORMFISH), FOTEntities.STORMFISH, FOTRegistries.STORMFISH_VARIANT));

    public static final Item COOKED_SPLASHTAIL = item("cooked_splashtail", properties -> new Item(properties.food(FOTFoodProperties.COOKED_SPLASHTAIL)));
    public static final Item COOKED_PONDIE = item("cooked_pondie", properties -> new Item(properties.food(FOTFoodProperties.COOKED_PONDIE)));
    public static final Item COOKED_ISLEHOPPER = item("cooked_islehopper", properties -> new Item(properties.food(FOTFoodProperties.COOKED_ISLEHOPPER)));
    public static final Item COOKED_ANCIENTSCALE = item("cooked_ancientscale", properties -> new Item(properties.food(FOTFoodProperties.COOKED_ANCIENTSCALE)));
    public static final Item COOKED_PLENTIFIN = item("cooked_plentifin", properties -> new Item(properties.food(FOTFoodProperties.COOKED_PLENTIFIN, FOTConsumables.COOKED_PLENTIFIN)));
    public static final Item COOKED_WILDSPLASH = item("cooked_wildsplash", properties -> new Item(properties.food(FOTFoodProperties.COOKED_WILDSPLASH)));
    public static final Item COOKED_DEVILFISH = item("cooked_devilfish", properties -> new Item(properties.food(FOTFoodProperties.COOKED_DEVILFISH)));
    public static final Item COOKED_BATTLEGILL = item("cooked_battlegill", properties -> new Item(properties.food(FOTFoodProperties.COOKED_BATTLEGILL, FOTConsumables.COOKED_BATTLEGILL)));
    public static final Item COOKED_WRECKER = item("cooked_wrecker", properties -> new Item(properties.food(FOTFoodProperties.COOKED_WRECKER)));
    public static final Item COOKED_STORMFISH = item("cooked_stormfish", properties -> new Item(properties.food(FOTFoodProperties.COOKED_STORMFISH)));

    public static final Item SPLASHTAIL_BUCKET = item("splashtail_bucket", properties -> new FOTMobBucketItem(FOTEntities.SPLASHTAIL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.SPLASHTAIL_VARIANT, properties.stacksTo(1)));
    public static final Item PONDIE_BUCKET = item("pondie_bucket", properties -> new FOTMobBucketItem(FOTEntities.PONDIE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.PONDIE_VARIANT, properties.stacksTo(1)));
    public static final Item ISLEHOPPER_BUCKET = item("islehopper_bucket", properties -> new FOTMobBucketItem(FOTEntities.ISLEHOPPER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.ISLEHOPPER_VARIANT, properties.stacksTo(1)));
    public static final Item ANCIENTSCALE_BUCKET = item("ancientscale_bucket", properties -> new FOTMobBucketItem(FOTEntities.ANCIENTSCALE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.ANCIENTSCALE_VARIANT, properties.stacksTo(1)));
    public static final Item PLENTIFIN_BUCKET = item("plentifin_bucket", properties -> new FOTMobBucketItem(FOTEntities.PLENTIFIN, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.PLENTIFIN_VARIANT, properties.stacksTo(1)));
    public static final Item WILDSPLASH_BUCKET = item("wildsplash_bucket", properties -> new FOTMobBucketItem(FOTEntities.WILDSPLASH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.WILDSPLASH_VARIANT, properties.stacksTo(1)));
    public static final Item DEVILFISH_BUCKET = item("devilfish_bucket", properties -> new FOTMobBucketItem(FOTEntities.DEVILFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.DEVILFISH_VARIANT, properties.stacksTo(1)));
    public static final Item BATTLEGILL_BUCKET = item("battlegill_bucket", properties -> new FOTMobBucketItem(FOTEntities.BATTLEGILL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.BATTLEGILL_VARIANT, properties.stacksTo(1)));
    public static final Item WRECKER_BUCKET = item("wrecker_bucket", properties -> new FOTMobBucketItem(FOTEntities.WRECKER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.WRECKER_VARIANT, properties.stacksTo(1)));
    public static final Item STORMFISH_BUCKET = item("stormfish_bucket", properties -> new FOTMobBucketItem(FOTEntities.STORMFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.STORMFISH_VARIANT, properties.stacksTo(1)));

    public static final Item SPLASHTAIL_SPAWN_EGG = item("splashtail_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.SPLASHTAIL, 10368309, 3949737, properties));
    public static final Item PONDIE_SPAWN_EGG = item("pondie_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.PONDIE, 8553918, 6255174, properties));
    public static final Item ISLEHOPPER_SPAWN_EGG = item("islehopper_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.ISLEHOPPER, 5854313, 8600128, properties));
    public static final Item ANCIENTSCALE_SPAWN_EGG = item("ancientscale_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.ANCIENTSCALE, 16224860, 7878952, properties));
    public static final Item PLENTIFIN_SPAWN_EGG = item("plentifin_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.PLENTIFIN, 12901959, 3298579, properties));
    public static final Item WILDSPLASH_SPAWN_EGG = item("wildsplash_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.WILDSPLASH, 6453062, 7556888, properties));
    public static final Item DEVILFISH_SPAWN_EGG = item("devilfish_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.DEVILFISH, 8618392, 13068147, properties));
    public static final Item BATTLEGILL_SPAWN_EGG = item("battlegill_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.BATTLEGILL, 2311985, 11047794, properties));
    public static final Item WRECKER_SPAWN_EGG = item("wrecker_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.WRECKER, 12022988, 4597359, properties));
    public static final Item STORMFISH_SPAWN_EGG = item("stormfish_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.STORMFISH, 9541044, 8608620, properties));

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
    }

    private static void register(String key, Item item)
    {
        FOTPlatform.registerItem(key, item);
    }

    public static Item item(String key, Function<Item.Properties, Item> function)
    {
        return item(key, function, new Item.Properties());
    }

    public static Item item(String key, Function<Item.Properties, Item> function, Item.Properties properties)
    {
        return function.apply(properties.setId(ResourceKey.create(Registries.ITEM, FishOfThieves.id(key))));
    }
}