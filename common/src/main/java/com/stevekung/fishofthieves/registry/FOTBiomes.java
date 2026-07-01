package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class FOTBiomes
{
    public static final ResourceKey<Biome> TROPICAL_ISLAND = register("tropical_island");

    public static void bootstrap(BootstrapContext<Biome> context)
    {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var configuredCarver = context.lookup(Registries.CARVER);
        context.register(TROPICAL_ISLAND, FOTOverworldBiomes.tropicalIsland(placedFeature, configuredCarver));
    }

    private static ResourceKey<Biome> register(String key)
    {
        return ResourceKey.create(Registries.BIOME, FishOfThieves.id(key));
    }
}