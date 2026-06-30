package com.stevekung.fishofthieves.compatibility.biolith;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBiomes;
import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;

import net.minecraft.world.level.biome.Climate;

public class FOTBiolith
{
    public static void init()
    {
        var temperature = Climate.Parameter.span(0.2F, 1.0F); // Tropical temperature

        // Parameters are almost the same as mushroom islands because we can't generate islands manually
        // So replacing the mushroom island biome at the tropical temperature is the best choice
        var humidity = Climate.Parameter.span(-1.0F, 1.0F);
        var continentalness = Climate.Parameter.span(-1.2F, -0.9F);
        var erosion = Climate.Parameter.span(-1.0F, 1.0F);
        var weirdness = Climate.Parameter.span(-1.0F, 1.0F);
        var depth = Climate.Parameter.point(0.0F);

        BiomePlacement.addOverworld(FOTBiomes.TROPICAL_ISLAND,
                new Climate.ParameterPoint(
                        temperature,
                        humidity,
                        continentalness,
                        erosion,
                        depth,
                        weirdness,
                        0L));

        SurfaceGeneration.addOverworldSurfaceRules(FishOfThieves.id("surface_rules"), FOTSurfaceRuleData.overworld());
    }
}