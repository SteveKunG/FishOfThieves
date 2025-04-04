package com.stevekung.fishofthieves.feature.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTPlacementModifiers;

import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class VegetationFilter extends AbstractNoiseRouterFilter
{
    public static final Codec<VegetationFilter> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            FloatProvider.codec(-1.0f, 1.0f).fieldOf("noise").forGetter(filter -> filter.floatProvider)
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
    public PlacementModifierType<?> type()
    {
        return FOTPlacementModifiers.VEGETATION_FILTER;
    }
}