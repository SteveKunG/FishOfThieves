package com.stevekung.fishofthieves.feature;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.block.FishBoneBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;

public class FishBoneFeature extends Feature<NoneFeatureConfiguration>
{
    public FishBoneFeature(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        var place = false;
        var randomSource = context.random();
        var level = context.level();
        var blockPos = context.origin();
        var x = randomSource.nextInt(8) - randomSource.nextInt(8);
        var z = randomSource.nextInt(8) - randomSource.nextInt(8);
        var y = level.getHeight(Heightmap.Types.OCEAN_FLOOR, blockPos.getX() + x, blockPos.getZ() + z);
        var blockPos2 = new BlockPos(blockPos.getX() + x, y, blockPos.getZ() + z);
        var blockState = level.getBlockState(blockPos2);

        if (blockState.is(Blocks.WATER))
        {
            var blockState2 = FOTBlocks.FISH_BONE.defaultBlockState();

            if (blockState2.canSurvive(level, blockPos2))
            {
                System.out.println(blockPos2);
                level.setBlock(blockPos2, blockState2.setValue(FishBoneBlock.WATERLOGGED, level.getFluidState(blockPos2).is(Fluids.WATER)).setValue(FishBoneBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomSource)), Block.UPDATE_CLIENTS);
                place = true;
            }
        }
        return place;
    }
}
