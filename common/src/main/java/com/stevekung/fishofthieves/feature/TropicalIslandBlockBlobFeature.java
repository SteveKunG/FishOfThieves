package com.stevekung.fishofthieves.feature;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class TropicalIslandBlockBlobFeature extends Feature<BlockStateConfiguration>
{
    public TropicalIslandBlockBlobFeature(Codec<BlockStateConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context)
    {
        var blockPos = context.origin();
        var worldGenLevel = context.level();
        var randomSource = context.random();

        BlockStateConfiguration blockStateConfiguration;

        for (blockStateConfiguration = context.config(); blockPos.getY() > worldGenLevel.getMinY() + 3; blockPos = blockPos.below())
        {
            if (!worldGenLevel.isEmptyBlock(blockPos.below()))
            {
                var blockState = worldGenLevel.getBlockState(blockPos.below());

                if (isDirt(blockState) || isStone(blockState))
                {
                    break;
                }
            }
        }

        if (blockPos.getY() <= worldGenLevel.getMinY() + 3)
        {
            return false;
        }
        else
        {
            for (var i = 0; i < 5; i++)
            {
                var xr = 1 + randomSource.nextInt(2);
                var yr = 1 + randomSource.nextInt(2);
                var zr = 1 + randomSource.nextInt(2);
                var tr = (xr + yr + zr) * 0.333F + 0.75F;

                for (var blockPos2 : BlockPos.betweenClosed(blockPos.offset(-xr, -yr, -zr), blockPos.offset(xr, yr, zr)))
                {
                    if (blockPos2.distSqr(blockPos) <= tr * tr)
                    {
                        worldGenLevel.setBlock(blockPos2, blockStateConfiguration.state, Block.UPDATE_ALL);
                    }
                }

                blockPos = blockPos.offset(-1 + randomSource.nextInt(2), -randomSource.nextInt(2), -1 + randomSource.nextInt(2));
            }
            return true;
        }
    }
}