package com.stevekung.fishofthieves.compatibility.terrablender;

import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.registry.FOTBiomes;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

public class TropicalIslandsRegion extends Region
{
    public TropicalIslandsRegion(ResourceLocation name, int weight)
    {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        this.addBiome(mapper,
                Climate.Parameter.span(0.2F, 0.55F),
                Climate.Parameter.span(0.1F, 0.3F),
                ParameterUtils.Continentalness.MUSHROOM_FIELDS.parameter(),
                ParameterUtils.Erosion.FULL_RANGE.parameter(),
                ParameterUtils.Weirdness.FULL_RANGE.parameter(),
                ParameterUtils.Depth.SURFACE.parameter(), 0.0f, FOTBiomes.TROPICAL_ISLANDS);
    }
}