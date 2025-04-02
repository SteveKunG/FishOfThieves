package com.stevekung.fishofthieves.feature.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTPlacementModifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class VegetationFilter extends AbstractNoiseRouterFilter
{
    public static final Codec<VegetationFilter> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            FloatProvider.codec(-1.0f, 1.0f).fieldOf("vegetation").forGetter(filter -> filter.vegetation)
    ).apply(instance, VegetationFilter::new));
    private final FloatProvider vegetation;

    private VegetationFilter(FloatProvider vegetation)
    {
        this.vegetation = vegetation;
    }

    public static VegetationFilter vegetation(UniformFloat uniformFloat)
    {
        return new VegetationFilter(uniformFloat);
    }

    @Override
    protected boolean shouldFilterWith(PlacementContext context, RandomSource random, BlockPos pos, NoiseRouter noiseRouter, DensityFunction.SinglePointContext singlePointContext)
    {
        var vegetation = noiseRouter.vegetation().compute(singlePointContext);
        return vegetation >= this.vegetation.getMinValue() && vegetation <= this.vegetation.getMaxValue();
    }

    @Override
    public PlacementModifierType<?> type()
    {
        return FOTPlacementModifiers.VEGETATION_FILTER;
    }
}