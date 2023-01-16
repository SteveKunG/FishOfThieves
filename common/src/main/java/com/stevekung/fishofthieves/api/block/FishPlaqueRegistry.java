package com.stevekung.fishofthieves.api.block;

import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.world.entity.EntityType;

public class FishPlaqueRegistry
{
    private static final Map<EntityType<?>, FishPlaqueTagConverter> REGISTRY = Util.make(new Object2ObjectOpenHashMap<>(), map -> map.defaultReturnValue(FishPlaqueTagConverter.NOOP));

    public static void register(EntityType<?> entityType, FishPlaqueTagConverter converter)
    {
        REGISTRY.put(entityType, converter);
    }

    public static FishPlaqueTagConverter get(EntityType<?> type)
    {
        return REGISTRY.get(type);
    }
}