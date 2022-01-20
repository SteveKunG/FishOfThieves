package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
    public static final Item COOKED_SPLASHTAIL = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_SALMON));
    public static final Item COOKED_PONDIE = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_COD));
    public static final Item COOKED_ISLEHOPPER = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_COD));
    public static final Item COOKED_ANCIENTSCALE = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_SALMON));
    public static final Item COOKED_PLENTIFIN = new Item(new Item.Properties().tab(FishOfThieves.FOT_TAB).food(Foods.COOKED_SALMON));
    public static final Item SPLASHTAIL_BUCKET = new MobBucketItem(FOTEntities.SPLASHTAIL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item PONDIE_BUCKET = new MobBucketItem(FOTEntities.PONDIE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item ISLEHOPPER_BUCKET = new MobBucketItem(FOTEntities.ISLEHOPPER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item ANCIENTSCALE_BUCKET = new MobBucketItem(FOTEntities.ANCIENTSCALE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item PLENTIFIN_BUCKET = new MobBucketItem(FOTEntities.PLENTIFIN, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));
    public static final Item SPLASHTAIL_SPAWN_EGG = new SpawnEggItem(FOTEntities.SPLASHTAIL, 10368309, 3949737, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item PONDIE_SPAWN_EGG = new SpawnEggItem(FOTEntities.PONDIE, 8553918, 6255174, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item ISLEHOPPER_SPAWN_EGG = new SpawnEggItem(FOTEntities.ISLEHOPPER, 5854313, 8600128, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item ANCIENTSCALE_SPAWN_EGG = new SpawnEggItem(FOTEntities.ANCIENTSCALE, 16224860, 7878952, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item PLENTIFIN_SPAWN_EGG = new SpawnEggItem(FOTEntities.PLENTIFIN, 12901959, 3298579, new Item.Properties().tab(FishOfThieves.FOT_TAB));

    public static final ItemStack[] CAT_FOODS = new ItemStack[] { new ItemStack(FOTItems.SPLASHTAIL), new ItemStack(FOTItems.PONDIE), new ItemStack(FOTItems.ISLEHOPPER), new ItemStack(FOTItems.ANCIENTSCALE), new ItemStack(FOTItems.PLENTIFIN) };

    public static void init()
    {
        register("splashtail", SPLASHTAIL);
        register("pondie", PONDIE);
        register("islehopper", ISLEHOPPER);
        register("ancientscale", ANCIENTSCALE);
        register("plentifin", PLENTIFIN);
        register("cooked_splashtail", COOKED_SPLASHTAIL);
        register("cooked_pondie", COOKED_PONDIE);
        register("cooked_islehopper", COOKED_ISLEHOPPER);
        register("cooked_ancientscale", COOKED_ANCIENTSCALE);
        register("cooked_plentifin", COOKED_PLENTIFIN);
        register("splashtail_bucket", SPLASHTAIL_BUCKET);
        register("pondie_bucket", PONDIE_BUCKET);
        register("islehopper_bucket", ISLEHOPPER_BUCKET);
        register("ancientscale_bucket", ANCIENTSCALE_BUCKET);
        register("plentifin_bucket", PLENTIFIN_BUCKET);
        register("splashtail_spawn_egg", SPLASHTAIL_SPAWN_EGG);
        register("pondie_spawn_egg", PONDIE_SPAWN_EGG);
        register("islehopper_spawn_egg", ISLEHOPPER_SPAWN_EGG);
        register("ancientscale_spawn_egg", ANCIENTSCALE_SPAWN_EGG);
        register("plentifin_spawn_egg", PLENTIFIN_SPAWN_EGG);
    }

    private static Item register(String key, Item item)
    {
        return Registry.register(Registry.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, key), item);
    }
}