package com.stevekung.fishofthieves.feature;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.block.FishBoneBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.material.Fluids;

public record FishBoneFeature() implements Feature
{
    public static final FishBoneFeature INSTANCE = new FishBoneFeature();
    public static final MapCodec<FishBoneFeature> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public MapCodec<? extends Feature> codec()
    {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin)
    {
        var place = false;
        var x = random.nextInt(8) - random.nextInt(8);
        var z = random.nextInt(8) - random.nextInt(8);
        var y = level.getHeight(Heightmap.Types.OCEAN_FLOOR, origin.getX() + x, origin.getZ() + z);
        var blockPos2 = new BlockPos(origin.getX() + x, y, origin.getZ() + z);
        var blockState = level.getBlockState(blockPos2);

        if (blockState.is(Blocks.WATER))
        {
            var blockState2 = FOTBlocks.FISH_BONE.defaultBlockState();

            if (blockState2.canSurvive(level, blockPos2))
            {
                level.setBlock(blockPos2, blockState2.setValue(FishBoneBlock.WATERLOGGED, level.getFluidState(blockPos2).is(Fluids.WATER)).setValue(FishBoneBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), Block.UPDATE_CLIENTS);
                place = true;
            }
        }
        return place;
    }
}
