package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.item.FOTMobBucketItem;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.material.Fluids;

public class FOTItems
{
    public static final Item EARTHWORMS = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.WORMS));
    public static final Item GRUBS = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.WORMS));
    public static final Item LEECHES = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.WORMS));

    public static final Item SPLASHTAIL = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.SPLASHTAIL));
    public static final Item PONDIE = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.PONDIE));
    public static final Item ISLEHOPPER = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.ISLEHOPPER));
    public static final Item ANCIENTSCALE = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.ANCIENTSCALE));
    public static final Item PLENTIFIN = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.PLENTIFIN));
    public static final Item WILDSPLASH = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.WILDSPLASH));
    public static final Item DEVILFISH = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.DEVILFISH));
    public static final Item BATTLEGILL = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.BATTLEGILL));
    public static final Item WRECKER = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.WRECKER));
    public static final Item STORMFISH = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.STORMFISH));

    public static final Item COOKED_SPLASHTAIL = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.COOKED_SPLASHTAIL));
    public static final Item COOKED_PONDIE = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.COOKED_PONDIE));
    public static final Item COOKED_ISLEHOPPER = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.COOKED_ISLEHOPPER));
    public static final Item COOKED_ANCIENTSCALE = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.COOKED_ANCIENTSCALE));
    public static final Item COOKED_PLENTIFIN = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.COOKED_PLENTIFIN));
    public static final Item COOKED_WILDSPLASH = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.COOKED_WILDSPLASH));
    public static final Item COOKED_DEVILFISH = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.COOKED_DEVILFISH));
    public static final Item COOKED_BATTLEGILL = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.COOKED_BATTLEGILL));
    public static final Item COOKED_WRECKER = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.COOKED_WRECKER));
    public static final Item COOKED_STORMFISH = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(FOTFoodProperties.COOKED_STORMFISH));

    public static final Item SPLASHTAIL_BUCKET = new FOTMobBucketItem(FOTEntities.SPLASHTAIL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Splashtail.DATA_FIX_MAP, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item PONDIE_BUCKET = new FOTMobBucketItem(FOTEntities.PONDIE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Pondie.DATA_FIX_MAP, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item ISLEHOPPER_BUCKET = new FOTMobBucketItem(FOTEntities.ISLEHOPPER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Islehopper.DATA_FIX_MAP, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item ANCIENTSCALE_BUCKET = new FOTMobBucketItem(FOTEntities.ANCIENTSCALE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Ancientscale.DATA_FIX_MAP, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item PLENTIFIN_BUCKET = new FOTMobBucketItem(FOTEntities.PLENTIFIN, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Plentifin.DATA_FIX_MAP, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item WILDSPLASH_BUCKET = new FOTMobBucketItem(FOTEntities.WILDSPLASH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Wildsplash.DATA_FIX_MAP, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item DEVILFISH_BUCKET = new FOTMobBucketItem(FOTEntities.DEVILFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Devilfish.DATA_FIX_MAP, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item BATTLEGILL_BUCKET = new FOTMobBucketItem(FOTEntities.BATTLEGILL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Battlegill.DATA_FIX_MAP, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item WRECKER_BUCKET = new FOTMobBucketItem(FOTEntities.WRECKER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Wrecker.DATA_FIX_MAP, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item STORMFISH_BUCKET = new FOTMobBucketItem(FOTEntities.STORMFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, Stormfish.DATA_FIX_MAP, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));

    public static final Item SPLASHTAIL_SPAWN_EGG = new SpawnEggItem(FOTEntities.SPLASHTAIL, 10368309, 3949737, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item PONDIE_SPAWN_EGG = new SpawnEggItem(FOTEntities.PONDIE, 8553918, 6255174, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item ISLEHOPPER_SPAWN_EGG = new SpawnEggItem(FOTEntities.ISLEHOPPER, 5854313, 8600128, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item ANCIENTSCALE_SPAWN_EGG = new SpawnEggItem(FOTEntities.ANCIENTSCALE, 16224860, 7878952, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item PLENTIFIN_SPAWN_EGG = new SpawnEggItem(FOTEntities.PLENTIFIN, 12901959, 3298579, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item WILDSPLASH_SPAWN_EGG = new SpawnEggItem(FOTEntities.WILDSPLASH, 6453062, 7556888, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item DEVILFISH_SPAWN_EGG = new SpawnEggItem(FOTEntities.DEVILFISH, 8618392, 13068147, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item BATTLEGILL_SPAWN_EGG = new SpawnEggItem(FOTEntities.BATTLEGILL, 2311985, 11047794, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item WRECKER_SPAWN_EGG = new SpawnEggItem(FOTEntities.WRECKER, 12022988, 4597359, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item STORMFISH_SPAWN_EGG = new SpawnEggItem(FOTEntities.STORMFISH, 9541044, 8608620, new Item.Properties().tab(FishOfThieves.FOT_TAB));

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
}