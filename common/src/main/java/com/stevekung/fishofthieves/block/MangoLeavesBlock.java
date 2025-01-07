package com.stevekung.fishofthieves.block;

import java.util.stream.Stream;

import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MangoLeavesBlock extends LeavesBlock implements BonemealableBlock
{
    public MangoLeavesBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return Stream.of(Direction.values()).filter(direction -> direction != Direction.UP).anyMatch(direction -> level.getBlockState(pos.relative(direction)).isAir());
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        this.growMangos(level, random, pos);
    }

    private void growMangos(ServerLevel level, RandomSource random, BlockPos pos)
    {
        Util.shuffledCopy(Direction.values(), random).stream().filter(direction -> direction != Direction.UP && level.getBlockState(pos.relative(direction)).isAir()).findFirst().ifPresent(direction -> this.setMangoBlock(level, pos, direction));
    }

    private void setMangoBlock(ServerLevel level, BlockPos pos, Direction direction)
    {
        if (direction == Direction.DOWN)
        {
            level.setBlock(pos.relative(direction), FOTBlocks.HANGING_MANGO_FRUIT.defaultBlockState(), Block.UPDATE_CLIENTS);
        }
        else
        {
            level.setBlock(pos.relative(direction), FOTBlocks.MANGO_FRUIT.defaultBlockState().setValue(MangoFruitBlock.FACING, direction.getOpposite()), Block.UPDATE_CLIENTS);
        }
    }
}