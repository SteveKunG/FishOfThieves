package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.placement.ContinentsFilter;
import com.stevekung.fishofthieves.feature.placement.VegetationFilter;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class FOTPlacementModifiers
{
    public static final PlacementModifierType<VegetationFilter> VEGETATION_FILTER = () -> VegetationFilter.CODEC;
    public static final PlacementModifierType<ContinentsFilter> CONTINENTS_FILTER = () -> ContinentsFilter.CODEC;

    public static void init()
    {
        register("vegetation_filter", VEGETATION_FILTER);
        register("continents_filter", CONTINENTS_FILTER);
    }

    private static <P extends PlacementModifier> void register(String key, PlacementModifierType<P> type)
    {
        Registry.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, FishOfThieves.id(key), type);
    }
}