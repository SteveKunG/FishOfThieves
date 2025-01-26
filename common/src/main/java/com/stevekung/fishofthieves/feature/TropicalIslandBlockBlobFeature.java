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

        for (blockStateConfiguration = context.config(); blockPos.getY() > worldGenLevel.getMinBuildHeight() + 3; blockPos = blockPos.below())
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

        if (blockPos.getY() <= worldGenLevel.getMinBuildHeight() + 3)
        {
            return false;
        }
        else
        {
            for (var i = 0; i < 5; i++)
            {
                var j = 1 + randomSource.nextInt(2);
                var k = 1 + randomSource.nextInt(2);
                var l = 1 + randomSource.nextInt(2);
                var f = (float) (j + k + l) * 0.333F + 0.75F;

                for (var blockPos2 : BlockPos.betweenClosed(blockPos.offset(-j, -k, -l), blockPos.offset(j, k, l)))
                {
                    if (blockPos2.distSqr(blockPos) <= (double) (f * f))
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