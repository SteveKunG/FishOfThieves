package com.stevekung.fishofthieves.compatibility.biolith;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBiomes;
import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.biome.SubBiomeMatcher;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;

import net.minecraft.world.level.biome.Biomes;

public class FOTBiolith
{
    public static void init()
    {
        BiomePlacement.addSubOverworld(Biomes.MUSHROOM_FIELDS, FOTBiomes.TROPICAL_ISLAND, SubBiomeMatcher.of(
                // Tropical climate
                SubBiomeMatcher.Criterion.ofRange(SubBiomeMatcher.CriterionTargets.TEMPERATURE, SubBiomeMatcher.CriterionTypes.VALUE, 0.2f, 1.2f, false),
                SubBiomeMatcher.Criterion.ofRange(SubBiomeMatcher.CriterionTargets.WEIRDNESS, SubBiomeMatcher.CriterionTypes.VALUE, FishOfThieves.CONFIG.biome.tropicalIslandMinimumWeirdness, FishOfThieves.CONFIG.biome.tropicalIslandMaximumWeirdness, false)
        ));

        SurfaceGeneration.addOverworldSurfaceRules(FishOfThieves.id("surface_rules"), FOTSurfaceRuleData.overworld());
    }
}