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

public class ContinentsFilter extends AbstractNoiseRouterFilter
{
    public static final Codec<ContinentsFilter> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            FloatProvider.codec(-2.0f, 1.0f).fieldOf("continents").forGetter(filter -> filter.continents)
    ).apply(instance, ContinentsFilter::new));
    private final FloatProvider continents;

    private ContinentsFilter(FloatProvider continents)
    {
        this.continents = continents;
    }

    public static ContinentsFilter continents(UniformFloat uniformFloat)
    {
        return new ContinentsFilter(uniformFloat);
    }

    @Override
    protected boolean shouldFilterWith(PlacementContext context, RandomSource random, BlockPos pos, NoiseRouter noiseRouter, DensityFunction.SinglePointContext singlePointContext)
    {
        var continents = noiseRouter.continents().compute(singlePointContext);
        return continents >= this.continents.getMinValue() && continents <= this.continents.getMaxValue();
    }

    @Override
    public PlacementModifierType<?> type()
    {
        return FOTPlacementModifiers.CONTINENTS_FILTER;
    }
}