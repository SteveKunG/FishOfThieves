package com.stevekung.fishofthieves.registry;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;

public class FOTGrassColorModifier
{
    public static BiomeSpecialEffects.GrassColorModifier TROPICAL_ISLAND;

    @SuppressWarnings("removal")
    public static int getGrassColor(double x, double z)
    {
        var offset = 0.0275;
        var noise = Biome.BIOME_INFO_NOISE.getValue(x * offset, z * offset, false);
        return noise < -0.1 ? 7000834 : 8769546;
    }
}