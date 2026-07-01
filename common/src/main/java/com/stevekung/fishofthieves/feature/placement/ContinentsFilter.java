package com.stevekung.fishofthieves.feature.placement;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;

public class ContinentsFilter extends AbstractNoiseRouterFilter
{
    public static final MapCodec<ContinentsFilter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            FloatProviders.codec(-2.0f, 1.0f).fieldOf("noise").forGetter(filter -> filter.floatProvider)
    ).apply(instance, ContinentsFilter::new));

    private ContinentsFilter(FloatProvider floatProvider)
    {
        super(floatProvider);
    }

    public static ContinentsFilter continents(FloatProvider floatProvider)
    {
        return new ContinentsFilter(floatProvider);
    }

    @Override
    protected DensityFunction getDensityFunction(NoiseRouter noiseRouter)
    {
        return noiseRouter.continents();
    }

    @Override
    public MapCodec<? extends PlacementFilter> codec()
    {
        return CODEC;
    }
}