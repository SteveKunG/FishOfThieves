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
    public static final Item SPLASHTAIL_SPAWN_EGG = new SpawnEggItem(FOTEntities.SPLASHTAIL, 10368309, 3949737, new Item.Properties().tab(FishOfThieves.FOT_TAB));
    public static final Item SPLASHTAIL_BUCKET = new MobBucketItem(FOTEntities.SPLASHTAIL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).tab(FishOfThieves.FOT_TAB));

    public static void init()
    {
        register("splashtail", SPLASHTAIL);
        register("splashtail_spawn_egg", SPLASHTAIL_SPAWN_EGG);
        register("splashtail_bucket", SPLASHTAIL_BUCKET);
    }

    private static Item register(String key, Item item)
    {
        return Registry.register(Registry.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, key), item);
    }
}