package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;

public class FOTMapDecorationTypes
{
    public static final Holder<MapDecorationType> TREASURED_FISH = register("treasured_fish", "treasured_fish", true, -1, true, false);

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Map Decoration Types");
    }

    private static Holder<MapDecorationType> register(String name, String assetId, boolean showOnItemFrame, int mapColor, boolean explorationMapElement, boolean trackCount)
    {
        var resourceKey = ResourceKey.create(Registries.MAP_DECORATION_TYPE, FishOfThieves.id(name));
        var mapDecorationType = new MapDecorationType(FishOfThieves.id(assetId), showOnItemFrame, mapColor, trackCount, explorationMapElement);
        return Registry.registerForHolder(BuiltInRegistries.MAP_DECORATION_TYPE, resourceKey, mapDecorationType);
    }
}