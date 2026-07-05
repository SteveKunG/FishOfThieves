package com.stevekung.fishofthieves.compatibility.biolith;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBiomes;
import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.biome.sub.BiomeParameterTargets;
import com.terraformersmc.biolith.impl.biome.sub.AllOfCriterion;
import com.terraformersmc.biolith.impl.biome.sub.ValueCriterion;

import net.minecraft.world.level.biome.Biomes;

public class FOTBiolith
{
    public static void init()
    {
        BiomePlacement.addSubOverworld(Biomes.MUSHROOM_FIELDS, FOTBiomes.TROPICAL_ISLAND, new AllOfCriterion(List.of(
                // Tropical climate
                new ValueCriterion(BiomeParameterTargets.TEMPERATURE, 0.2f, 1.2f),
                new ValueCriterion(BiomeParameterTargets.WEIRDNESS, FishOfThieves.CONFIG.biome.tropicalIslandMinimumWeirdness, FishOfThieves.CONFIG.biome.tropicalIslandMaximumWeirdness)
        )));

//        SurfaceGeneration.addOverworldSurfaceRules(FishOfThieves.id("surface_rules"), FOTSurfaceRuleData.overworld(null));//TODO Waiting for biolith
    }
}