package com.stevekung.fishofthieves.feature;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockBlobConfiguration;

public class TropicalIslandBlockBlobFeature extends Feature<BlockBlobConfiguration>
{
    public TropicalIslandBlockBlobFeature(Codec<BlockBlobConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockBlobConfiguration> context)
    {
        var blockPos = context.origin();
        var level = context.level();
        var randomSource = context.random();
        var config = context.config();

        while (blockPos.getY() > level.getMinY() + 3 && !config.canPlaceOn().test(level, blockPos.below()))
        {
            blockPos = blockPos.below();
        }

        if (blockPos.getY() <= level.getMinY() + 3)
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
                        level.setBlock(blockPos2, config.state(), Block.UPDATE_ALL);
                    }
                }

                blockPos = blockPos.offset(-1 + randomSource.nextInt(2), -randomSource.nextInt(2), -1 + randomSource.nextInt(2));
            }
            return true;
        }
    }
}