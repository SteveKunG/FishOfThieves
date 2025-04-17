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
    public static final PlacementModifierType<VegetationFilter> VEGETATION_FILTER = register("vegetation_filter", () -> VegetationFilter.CODEC);
    public static final PlacementModifierType<ContinentsFilter> CONTINENTS_FILTER = register("continents_filter", () -> ContinentsFilter.CODEC);

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Placement Modifier");
    }

    private static <P extends PlacementModifier> PlacementModifierType<P> register(String key, PlacementModifierType<P> type)
    {
        return Registry.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, FishOfThieves.id(key), type);
    }
}