package com.stevekung.fishofthieves.api.block;

import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class FishPlaqueRegistry
{
    private static final Map<String, Item> INTERACT_ITEMS = new Object2ObjectOpenHashMap<>();

    public static void registerInteractionItem(Item item, EntityType<?>... entityTypes)
    {
        for (var entityType : entityTypes)
        {
            INTERACT_ITEMS.put(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString(), item);
        }
    }

    public static void registerInteractionItem(Item item, String... keys)
    {
        for (var key : keys)
        {
            INTERACT_ITEMS.put(key, item);
        }
    }

    public static Map<String, Item> getInteractionItem()
    {
        return INTERACT_ITEMS;
    }
}