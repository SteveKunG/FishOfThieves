package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.animal.Pondie;
import com.stevekung.fishofthieves.entity.animal.Splashtail;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.material.Fluids;

public class FOTItems
{
    public static final Item SPLASHTAIL = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.SALMON));
    public static final Item PONDIE = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COD));
    public static final Item ISLEHOPPER = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COD));
    public static final Item ANCIENTSCALE = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.SALMON));
    public static final Item PLENTIFIN = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.SALMON));
    public static final Item WILDSPLASH = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.SALMON));
    public static final Item DEVILFISH = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.SALMON));
    public static final Item BATTLEGILL = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.SALMON));
    public static final Item WRECKER = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.SALMON));
    public static final Item STORMFISH = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.SALMON));

    public static final Item COOKED_SPLASHTAIL = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_SALMON));
    public static final Item COOKED_PONDIE = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_COD));
    public static final Item COOKED_ISLEHOPPER = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_COD));
    public static final Item COOKED_ANCIENTSCALE = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_SALMON));
    public static final Item COOKED_PLENTIFIN = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_SALMON));
    public static final Item COOKED_WILDSPLASH = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_SALMON));
    public static final Item COOKED_DEVILFISH = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_SALMON));
    public static final Item COOKED_BATTLEGILL = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_SALMON));
    public static final Item COOKED_WRECKER = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_SALMON));
    public static final Item COOKED_STORMFISH = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_SALMON));

    public static final Item SPLASHTAIL_BUCKET = new MobBucketItem(FOTEntities.SPLASHTAIL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB))
    {
        @Override
        public void verifyTagAfterLoad(CompoundTag compoundTag)
        {
            super.verifyTagAfterLoad(compoundTag);
            ThievesFish.fixData(compoundTag, Splashtail.DATA_FIX_MAP);
        }
    };
    public static final Item PONDIE_BUCKET = new MobBucketItem(FOTEntities.PONDIE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB))
    {
        @Override
        public void verifyTagAfterLoad(CompoundTag compoundTag)
        {
            super.verifyTagAfterLoad(compoundTag);
            ThievesFish.fixData(compoundTag, Pondie.DATA_FIX_MAP);
        }
    };
    public static final Item ISLEHOPPER_BUCKET = new MobBucketItem(FOTEntities.ISLEHOPPER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item ANCIENTSCALE_BUCKET = new MobBucketItem(FOTEntities.ANCIENTSCALE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item PLENTIFIN_BUCKET = new MobBucketItem(FOTEntities.PLENTIFIN, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item WILDSPLASH_BUCKET = new MobBucketItem(FOTEntities.WILDSPLASH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item DEVILFISH_BUCKET = new MobBucketItem(FOTEntities.DEVILFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item BATTLEGILL_BUCKET = new MobBucketItem(FOTEntities.BATTLEGILL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item WRECKER_BUCKET = new MobBucketItem(FOTEntities.WRECKER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item STORMFISH_BUCKET = new MobBucketItem(FOTEntities.STORMFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));

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
        register("splashtail", SPLASHTAIL);
        register("pondie", PONDIE);
        register("islehopper", ISLEHOPPER);
        register("ancientscale", ANCIENTSCALE);
        register("plentifin", PLENTIFIN);
        register("wildsplash", WILDSPLASH);
        register("devilfish", DEVILFISH);
        register("battlegill", BATTLEGILL);
        register("wrecker", WRECKER);
        register("stormfish", STORMFISH);

        register("cooked_splashtail", COOKED_SPLASHTAIL);
        register("cooked_pondie", COOKED_PONDIE);
        register("cooked_islehopper", COOKED_ISLEHOPPER);
        register("cooked_ancientscale", COOKED_ANCIENTSCALE);
        register("cooked_plentifin", COOKED_PLENTIFIN);
        register("cooked_wildsplash", COOKED_WILDSPLASH);
        register("cooked_devilfish", COOKED_DEVILFISH);
        register("cooked_battlegill", COOKED_BATTLEGILL);
        register("cooked_wrecker", COOKED_WRECKER);
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