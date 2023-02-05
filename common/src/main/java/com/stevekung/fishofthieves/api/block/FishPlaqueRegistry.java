package com.stevekung.fishofthieves.api.block;

import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class FishPlaqueRegistry
{
    private static final Map<EntityType<?>, FishPlaqueTagConverter> CONVERTERS = Util.make(new Object2ObjectOpenHashMap<>(), map -> map.defaultReturnValue(FishPlaqueTagConverter.NOOP));
    private static final Map<String, Item> INTERACT_ITEMS = new Object2ObjectOpenHashMap<>();

    public static void registerTagConverter(EntityType<?> entityType, FishPlaqueTagConverter converter)
    {
        CONVERTERS.put(entityType, converter);
    }

    public static FishPlaqueTagConverter getTagConverter(EntityType<?> type)
    {
        return CONVERTERS.get(type);
    }

    public static void registerInteractionItem(EntityType<?> entityType, Item item)
    {
        INTERACT_ITEMS.put(Registry.ENTITY_TYPE.getKey(entityType).toString(), item);
    }

    public static void registerInteractionItem(String key, Item item)
    {
        INTERACT_ITEMS.put(key, item);
    }

    public static Map<String, Item> getInteractionItem()
    {
        return INTERACT_ITEMS;
    }
}