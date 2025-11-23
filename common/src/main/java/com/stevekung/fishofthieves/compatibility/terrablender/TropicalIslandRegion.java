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
import terrablender.api.VanillaParameterOverlayBuilder;

public class TropicalIslandRegion extends Region
{
    public TropicalIslandRegion(ResourceLocation name, int weight)
    {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        var temperature = Climate.Parameter.span(0.2F, 1.0F); // Tropical temperature

        // Parameters are almost the same as mushroom islands because we can't generate islands manually
        // So replacing the mushroom island biome at the tropical temperature is the best choice
        var humidity = Climate.Parameter.span(-1.0F, 1.0F);
        var continentalness = Climate.Parameter.span(-1.2F, -0.9F);
        var erosion = Climate.Parameter.span(-1.0F, 1.0F);
        var weirdness = Climate.Parameter.span(-1.0F, 1.0F);
        var depth = Climate.Parameter.point(0.0F);

        var builder = new VanillaParameterOverlayBuilder();
        new ParameterUtils.ParameterPointListBuilder()
                .temperature(temperature)
                .humidity(humidity)
                .continentalness(continentalness)
                .erosion(erosion)
                .weirdness(weirdness)
                .depth(depth)
                .build().forEach(point -> builder.add(point, FOTBiomes.TROPICAL_ISLAND));

        builder.build().forEach(mapper);
    }
}