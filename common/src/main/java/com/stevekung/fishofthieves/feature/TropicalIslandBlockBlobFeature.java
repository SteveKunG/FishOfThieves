package com.stevekung.fishofthieves.feature;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
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
                var xr = 1 + random.nextInt(2);
                var yr = 1 + random.nextInt(2);
                var zr = 1 + random.nextInt(2);
                var tr = (xr + yr + zr) * 0.333F + 0.75F;

                for (var blockPos2 : BlockPos.betweenClosed(origin.offset(-xr, -yr, -zr), origin.offset(xr, yr, zr)))
                {
                    if (blockPos2.distSqr(origin) <= tr * tr)
                    {
                        level.setBlockAndUpdate(blockPos2, this.state);
                    }
                }

                origin = origin.offset(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
            }
            return true;
        }
    }
}