package com.stevekung.fishofthieves.feature.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;

public abstract class AbstractNoiseRouterFilter implements PlacementFilter
{
    protected final FloatProvider floatProvider;

    public AbstractNoiseRouterFilter(FloatProvider floatProvider)
    {
        this.floatProvider = floatProvider;
    }

    @Override
    public boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos)
    {
        var serverChunkCache = context.getLevel().getLevel().getChunkSource();
        var randomState = serverChunkCache.randomState();
        var singlePointContext = new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ());
        var densityFunction = this.getDensityFunction(randomState.router()).compute(singlePointContext);
        return densityFunction >= this.floatProvider.min() && densityFunction <= this.floatProvider.max();
    }

    protected abstract DensityFunction getDensityFunction(NoiseRouter noiseRouter);
}