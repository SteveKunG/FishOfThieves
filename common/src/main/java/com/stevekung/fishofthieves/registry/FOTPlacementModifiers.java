package com.stevekung.fishofthieves.registry;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.placement.ContinentsFilter;
import com.stevekung.fishofthieves.feature.placement.VegetationFilter;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class FOTPlacementModifiers
{
    public static final MapCodec<VegetationFilter> VEGETATION_FILTER = register("vegetation_filter", VegetationFilter.CODEC);
    public static final MapCodec<ContinentsFilter> CONTINENTS_FILTER = register("continents_filter", ContinentsFilter.CODEC);

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Placement Modifier");
    }

    private static <P extends PlacementModifier> MapCodec<P> register(String key, MapCodec<P> type)
    {
        return Registry.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, FishOfThieves.id(key), type);
    }
}