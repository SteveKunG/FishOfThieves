package com.stevekung.fishofthieves.feature;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;

public record TropicalIslandBlockBlobFeature(BlockState state, BlockPredicate canPlaceOn) implements Feature
{
    public static final MapCodec<TropicalIslandBlockBlobFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BlockState.CODEC.fieldOf("state").forGetter(TropicalIslandBlockBlobFeature::state), BlockPredicate.CODEC.fieldOf("can_place_on").forGetter(TropicalIslandBlockBlobFeature::canPlaceOn)).apply(instance, TropicalIslandBlockBlobFeature::new));

    @Override
    public MapCodec<? extends Feature> codec()
    {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin)
    {
        while (origin.getY() > level.getMinY() + 3 && !this.canPlaceOn.test(level, origin.below()))
        {
            origin = origin.below();
        }

        if (origin.getY() <= level.getMinY() + 3)
        {
            return false;
        }
        else
        {
            for (var i = 0; i < 5; i++)
            {
                var j = 1 + random.nextInt(2);
                var k = 1 + random.nextInt(2);
                var l = 1 + random.nextInt(2);
                var f = (float) (j + k + l) * 0.333F + 0.75F;

                for (var blockPos2 : BlockPos.betweenClosed(origin.offset(-j, -k, -l), origin.offset(j, k, l)))
                {
                    if (blockPos2.distSqr(origin) <= (double) (f * f))
                    {
                        level.setBlock(blockPos2, this.state, Block.UPDATE_ALL);
                    }
                }

                origin = origin.offset(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
            }
            return true;
        }
    }
}