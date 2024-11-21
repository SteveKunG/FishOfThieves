package com.stevekung.fishofthieves.registry;

import java.util.function.Function;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.item.FOTItem;
import com.stevekung.fishofthieves.item.FOTMobBucketItem;
import com.stevekung.fishofthieves.item.FOTSpawnEggItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;

public class FOTItems
{
    public static final Item EARTHWORMS = register("earthworms", properties -> new Item(properties.food(FOTFoodProperties.WORMS, FOTConsumables.WORMS)));
    public static final Item GRUBS = register("grubs", properties -> new Item(properties.food(FOTFoodProperties.WORMS, FOTConsumables.WORMS)));
    public static final Item LEECHES = register("leeches", properties -> new Item(properties.food(FOTFoodProperties.WORMS, FOTConsumables.WORMS)));

    public static final Item SPLASHTAIL = register("splashtail", properties -> new FOTItem(properties.food(FOTFoodProperties.SPLASHTAIL), FOTEntities.SPLASHTAIL, FOTRegistries.SPLASHTAIL_VARIANT));
    public static final Item PONDIE = register("pondie", properties -> new FOTItem(properties.food(FOTFoodProperties.PONDIE), FOTEntities.PONDIE, FOTRegistries.PONDIE_VARIANT));
    public static final Item ISLEHOPPER = register("islehopper", properties -> new FOTItem(properties.food(FOTFoodProperties.ISLEHOPPER), FOTEntities.ISLEHOPPER, FOTRegistries.ISLEHOPPER_VARIANT));
    public static final Item ANCIENTSCALE = register("ancientscale", properties -> new FOTItem(properties.food(FOTFoodProperties.ANCIENTSCALE), FOTEntities.ANCIENTSCALE, FOTRegistries.ANCIENTSCALE_VARIANT));
    public static final Item PLENTIFIN = register("plentifin", properties -> new FOTItem(properties.food(FOTFoodProperties.PLENTIFIN), FOTEntities.PLENTIFIN, FOTRegistries.PLENTIFIN_VARIANT));
    public static final Item WILDSPLASH = register("wildsplash", properties -> new FOTItem(properties.food(FOTFoodProperties.WILDSPLASH), FOTEntities.WILDSPLASH, FOTRegistries.WILDSPLASH_VARIANT));
    public static final Item DEVILFISH = register("devilfish", properties -> new FOTItem(properties.food(FOTFoodProperties.DEVILFISH, FOTConsumables.DEVILFISH), FOTEntities.DEVILFISH, FOTRegistries.DEVILFISH_VARIANT));
    public static final Item BATTLEGILL = register("battlegill", properties -> new FOTItem(properties.food(FOTFoodProperties.BATTLEGILL), FOTEntities.BATTLEGILL, FOTRegistries.BATTLEGILL_VARIANT));
    public static final Item WRECKER = register("wrecker", properties -> new FOTItem(properties.food(FOTFoodProperties.WRECKER), FOTEntities.WRECKER, FOTRegistries.WRECKER_VARIANT));
    public static final Item STORMFISH = register("stormfish", properties -> new FOTItem(properties.food(FOTFoodProperties.STORMFISH), FOTEntities.STORMFISH, FOTRegistries.STORMFISH_VARIANT));

    public static final Item COOKED_SPLASHTAIL = register("cooked_splashtail", properties -> new Item(properties.food(FOTFoodProperties.COOKED_SPLASHTAIL)));
    public static final Item COOKED_PONDIE = register("cooked_pondie", properties -> new Item(properties.food(FOTFoodProperties.COOKED_PONDIE)));
    public static final Item COOKED_ISLEHOPPER = register("cooked_islehopper", properties -> new Item(properties.food(FOTFoodProperties.COOKED_ISLEHOPPER)));
    public static final Item COOKED_ANCIENTSCALE = register("cooked_ancientscale", properties -> new Item(properties.food(FOTFoodProperties.COOKED_ANCIENTSCALE)));
    public static final Item COOKED_PLENTIFIN = register("cooked_plentifin", properties -> new Item(properties.food(FOTFoodProperties.COOKED_PLENTIFIN, FOTConsumables.COOKED_PLENTIFIN)));
    public static final Item COOKED_WILDSPLASH = register("cooked_wildsplash", properties -> new Item(properties.food(FOTFoodProperties.COOKED_WILDSPLASH)));
    public static final Item COOKED_DEVILFISH = register("cooked_devilfish", properties -> new Item(properties.food(FOTFoodProperties.COOKED_DEVILFISH)));
    public static final Item COOKED_BATTLEGILL = register("cooked_battlegill", properties -> new Item(properties.food(FOTFoodProperties.COOKED_BATTLEGILL, FOTConsumables.COOKED_BATTLEGILL)));
    public static final Item COOKED_WRECKER = register("cooked_wrecker", properties -> new Item(properties.food(FOTFoodProperties.COOKED_WRECKER)));
    public static final Item COOKED_STORMFISH = register("cooked_stormfish", properties -> new Item(properties.food(FOTFoodProperties.COOKED_STORMFISH)));

    public static final Item SPLASHTAIL_BUCKET = register("splashtail_bucket", properties -> new FOTMobBucketItem(FOTEntities.SPLASHTAIL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.SPLASHTAIL_VARIANT, properties.stacksTo(1)));
    public static final Item PONDIE_BUCKET = register("pondie_bucket", properties -> new FOTMobBucketItem(FOTEntities.PONDIE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.PONDIE_VARIANT, properties.stacksTo(1)));
    public static final Item ISLEHOPPER_BUCKET = register("islehopper_bucket", properties -> new FOTMobBucketItem(FOTEntities.ISLEHOPPER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.ISLEHOPPER_VARIANT, properties.stacksTo(1)));
    public static final Item ANCIENTSCALE_BUCKET = register("ancientscale_bucket", properties -> new FOTMobBucketItem(FOTEntities.ANCIENTSCALE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.ANCIENTSCALE_VARIANT, properties.stacksTo(1)));
    public static final Item PLENTIFIN_BUCKET = register("plentifin_bucket", properties -> new FOTMobBucketItem(FOTEntities.PLENTIFIN, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.PLENTIFIN_VARIANT, properties.stacksTo(1)));
    public static final Item WILDSPLASH_BUCKET = register("wildsplash_bucket", properties -> new FOTMobBucketItem(FOTEntities.WILDSPLASH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.WILDSPLASH_VARIANT, properties.stacksTo(1)));
    public static final Item DEVILFISH_BUCKET = register("devilfish_bucket", properties -> new FOTMobBucketItem(FOTEntities.DEVILFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.DEVILFISH_VARIANT, properties.stacksTo(1)));
    public static final Item BATTLEGILL_BUCKET = register("battlegill_bucket", properties -> new FOTMobBucketItem(FOTEntities.BATTLEGILL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.BATTLEGILL_VARIANT, properties.stacksTo(1)));
    public static final Item WRECKER_BUCKET = register("wrecker_bucket", properties -> new FOTMobBucketItem(FOTEntities.WRECKER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.WRECKER_VARIANT, properties.stacksTo(1)));
    public static final Item STORMFISH_BUCKET = register("stormfish_bucket", properties -> new FOTMobBucketItem(FOTEntities.STORMFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.STORMFISH_VARIANT, properties.stacksTo(1)));

    public static final Item SPLASHTAIL_SPAWN_EGG = register("splashtail_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.SPLASHTAIL, properties));
    public static final Item PONDIE_SPAWN_EGG = register("pondie_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.PONDIE, properties));
    public static final Item ISLEHOPPER_SPAWN_EGG = register("islehopper_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.ISLEHOPPER, properties));
    public static final Item ANCIENTSCALE_SPAWN_EGG = register("ancientscale_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.ANCIENTSCALE, properties));
    public static final Item PLENTIFIN_SPAWN_EGG = register("plentifin_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.PLENTIFIN, properties));
    public static final Item WILDSPLASH_SPAWN_EGG = register("wildsplash_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.WILDSPLASH, properties));
    public static final Item DEVILFISH_SPAWN_EGG = register("devilfish_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.DEVILFISH, properties));
    public static final Item BATTLEGILL_SPAWN_EGG = register("battlegill_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.BATTLEGILL, properties));
    public static final Item WRECKER_SPAWN_EGG = register("wrecker_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.WRECKER, properties));
    public static final Item STORMFISH_SPAWN_EGG = register("stormfish_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.STORMFISH, properties));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Item");
    }

    public static Item register(String key, Item item)
    {
        return Registry.register(BuiltInRegistries.ITEM, FishOfThieves.id(key), item);
    }

    public static Item register(String key, Function<Item.Properties, Item> function)
    {
        return register(key, function, new Item.Properties());
    }

    public static Item register(String key, Function<Item.Properties, Item> function, Item.Properties properties)
    {
        var item = function.apply(properties.setId(ResourceKey.create(Registries.ITEM, FishOfThieves.id(key))));
        return register(key, item);
    }
}