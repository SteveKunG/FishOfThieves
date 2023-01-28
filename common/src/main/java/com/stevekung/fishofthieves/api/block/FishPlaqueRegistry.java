package com.stevekung.fishofthieves.api.block;

import java.util.Map;

import org.jetbrains.annotations.Nullable;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class FishPlaqueRegistry
{
    private static final Map<EntityType<?>, FishPlaqueTagConverter> CONVERTERS = Util.make(new Object2ObjectOpenHashMap<>(), map -> map.defaultReturnValue(FishPlaqueTagConverter.NOOP));
    private static final Map<EntityType<?>, Item> INTERACT_ITEMS_BY_TYPE = Util.make(new Object2ObjectOpenHashMap<>(), map -> map.defaultReturnValue(Items.WATER_BUCKET));
    private static final Map<String, Item> INTERACT_ITEMS_BY_KEY = new Object2ObjectOpenHashMap<>();

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
        INTERACT_ITEMS_BY_TYPE.put(entityType, item);
    }

    public static void registerInteractionItem(String key, Item item)
    {
        INTERACT_ITEMS_BY_KEY.put(key, item);
    }

    public static Item getInteractionItemByType(EntityType<?> type)
    {
        return INTERACT_ITEMS_BY_TYPE.get(type);
    }

    @Nullable
    public static Item getInteractionItemByEntityKey(String key)
    {
        return INTERACT_ITEMS_BY_KEY.get(key);
    }
}