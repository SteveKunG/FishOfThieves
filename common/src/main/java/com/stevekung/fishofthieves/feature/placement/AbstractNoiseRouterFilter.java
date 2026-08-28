package com.stevekung.fishofthieves.feature.placement;

import com.stevekung.fishofthieves.mixin.accessor.RandomStateAccessor;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunction;
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
        //TODO Test
        var serverChunkCache = context.getLevel().getLevel().getChunkSource();
        var randomState = serverChunkCache.randomState();
        var interval = this.getDensityFunction(((RandomStateAccessor) (Object) randomState).getRouter()).range();
        return interval.min() >= this.floatProvider.min() && interval.max() <= this.floatProvider.max();
    }

    protected abstract DensityFunction getDensityFunction(NoiseRouter noiseRouter);
}