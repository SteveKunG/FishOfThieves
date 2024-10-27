package com.stevekung.fishofthieves.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class GrowableBananaStemBlock extends BananaStemBlock implements BonemealableBlock
{
    public GrowableBananaStemBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return Direction.Plane.HORIZONTAL.stream().anyMatch(direction -> level.getBlockState(pos.relative(direction)).canBeReplaced());
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        var direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        var blockState = level.getBlockState(pos.relative(direction));

        if (blockState.isAir())
        {
//            level.setBlock(pos.relative(direction), FOTBlocks.COCONUT_FRUIT.defaultBlockState().setValue(CoconutFruitBlock.FACING, direction.getOpposite()), Block.UPDATE_CLIENTS);
        }
    }
}