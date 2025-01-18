package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class FOTBiomes
{
    public static final ResourceKey<Biome> TROPICAL_ISLANDS = register("tropical_islands");

    public static void bootstrap(BootstapContext<Biome> context)
    {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var configuredCarver = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(TROPICAL_ISLANDS, FOTOverworldBiomes.tropicalIslands(placedFeature, configuredCarver));
    }

    private static ResourceKey<Biome> register(String key)
    {
        return ResourceKey.create(Registries.BIOME, FishOfThieves.id(key));
    }
}