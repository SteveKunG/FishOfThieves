package com.stevekung.fishofthieves.feature.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;

public abstract class AbstractNoiseRouterFilter extends PlacementFilter
{
    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos)
    {
        var serverChunkCache = context.getLevel().getLevel().getChunkSource();
        var randomState = serverChunkCache.randomState();
        var singlePointContext = new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ());
        return this.shouldFilterWith(context, random, pos, randomState.router(), singlePointContext);
    }

    protected abstract boolean shouldFilterWith(PlacementContext context, RandomSource random, BlockPos pos, NoiseRouter noiseRouter, DensityFunction.SinglePointContext singlePointContext);
}