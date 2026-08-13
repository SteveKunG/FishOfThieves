package com.stevekung.fishofthieves.feature.placement;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunction;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;

public class VegetationFilter extends AbstractNoiseRouterFilter
{
    public static final MapCodec<VegetationFilter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            FloatProviders.codec(-1.0f, 1.0f).fieldOf("noise").forGetter(filter -> filter.floatProvider)
    ).apply(instance, VegetationFilter::new));

    private VegetationFilter(FloatProvider floatProvider)
    {
        super(floatProvider);
    }

    public static VegetationFilter vegetation(FloatProvider floatProvider)
    {
        return new VegetationFilter(floatProvider);
    }

    @Override
    protected DensityFunction getDensityFunction(NoiseRouter noiseRouter)
    {
        return noiseRouter.vegetation();
    }

    @Override
    public MapCodec<? extends PlacementFilter> codec()
    {
        return CODEC;
    }
}