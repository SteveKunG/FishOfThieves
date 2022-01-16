package com.stevekung.fishofthieves;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.material.Fluids;

public class FOTItems
{
    public static final Item SPLASHTAIL = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.SALMON));
    public static final Item COOKED_SPLASHTAIL = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_SALMON));
    public static final Item PONDIE = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COD));
    public static final Item COOKED_PONDIE = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_COD));
    public static final Item SPLASHTAIL_BUCKET = new MobBucketItem(FOTEntities.SPLASHTAIL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item PONDIE_BUCKET = new MobBucketItem(FOTEntities.PONDIE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item SPLASHTAIL_SPAWN_EGG = new SpawnEggItem(FOTEntities.SPLASHTAIL, 10368309, 3949737, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item PONDIE_SPAWN_EGG = new SpawnEggItem(FOTEntities.PONDIE, 8553918, 6255174, new Item.Properties().tab(FishOfThieves.FOT_TAB));

    public static void init()
    {
        register("splashtail", SPLASHTAIL);
        register("cooked_splashtail", COOKED_SPLASHTAIL);
        register("pondie", PONDIE);
        register("cooked_pondie", COOKED_PONDIE);
        register("splashtail_bucket", SPLASHTAIL_BUCKET);
        register("pondie_bucket", PONDIE_BUCKET);
        register("splashtail_spawn_egg", SPLASHTAIL_SPAWN_EGG);
        register("pondie_spawn_egg", PONDIE_SPAWN_EGG);
    }

    private static Item register(String key, Item item)
    {
        return Registry.register(Registry.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, key), item);
    }
}