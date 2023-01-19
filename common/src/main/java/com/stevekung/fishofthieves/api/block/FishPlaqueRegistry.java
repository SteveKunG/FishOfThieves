package com.stevekung.fishofthieves.api.block;

import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.world.entity.EntityType;

public class FishPlaqueRegistry
{
    private static final Map<EntityType<?>, FishPlaqueTagConverter> CONVERTERS = Util.make(new Object2ObjectOpenHashMap<>(), map -> map.defaultReturnValue(FishPlaqueTagConverter.NOOP));

    public static void registerTagConverter(EntityType<?> entityType, FishPlaqueTagConverter converter)
    {
        CONVERTERS.put(entityType, converter);
    }

    public static FishPlaqueTagConverter getTagConverter(EntityType<?> type)
    {
        return CONVERTERS.get(type);
    }
}