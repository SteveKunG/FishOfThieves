package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class FOTNoises
{
    public static final ResourceKey<NormalNoise> SAND_PATCHES = createKey("sand_patches");

    public static void bootstrap(BootstrapContext<NormalNoise> context)
    {
        register(context, SAND_PATCHES, -6, 1.0, 1.0, 1.0, 1.0, 1.0);
    }

    private static void register(BootstrapContext<NormalNoise> context, ResourceKey<NormalNoise> key, int firstOctave, double... amplitudes)
    {
        context.register(key, NormalNoise.createParity(firstOctave, amplitudes));
    }

    private static ResourceKey<NormalNoise> createKey(String key)
    {
        return ResourceKey.create(Registries.NOISE, FishOfThieves.id(key));
    }
}