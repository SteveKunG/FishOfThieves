package com.stevekung.fishofthieves.feature.placement;

import com.stevekung.fishofthieves.mixin.accessor.RandomStateAccessor;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunction;
import net.minecraft.world.level.levelgen.densityfunction.SamplerContext;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;

public abstract class AbstractNoiseRouterFilter implements PlacementFilter
{
    protected final FloatProvider floatProvider;

    protected AbstractNoiseRouterFilter(FloatProvider floatProvider)
    {
        this.floatProvider = floatProvider;
    }

    @Override
    public boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos)
    {
        var serverChunkCache = context.getLevel().getLevel().getChunkSource();
        var randomState = serverChunkCache.randomState();
        var densityFunction = this.getDensityFunction(((RandomStateAccessor) (Object) randomState).getRouter());
        var samplerContext = SamplerContext.builder().enableCaches().useBufferArena(randomState.acquireDensityBufferPool()).build();
        var value = randomState.getSampler(densityFunction).sampleValue(samplerContext, pos.getX(), pos.getY(), pos.getZ());
        return value >= this.floatProvider.min() && value <= this.floatProvider.max();
    }

    protected abstract DensityFunction getDensityFunction(NoiseRouter noiseRouter);
}