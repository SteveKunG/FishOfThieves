package com.stevekung.fishofthieves.block;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

// TODO: Removed in 1.21.5
public interface BushSpreadable extends BonemealableBlock
{
    static boolean hasSpreadableNeighbourPos(LevelReader level, BlockPos pos, BlockState state)
    {
        return getSpreadableNeighbourPos(Direction.Plane.HORIZONTAL.stream().toList(), level, pos, state).isPresent();
    }

    static Optional<BlockPos> findSpreadableNeighbourPos(Level level, BlockPos pos, BlockState state)
    {
        return getSpreadableNeighbourPos(Direction.Plane.HORIZONTAL.shuffledCopy(level.random), level, pos, state);
    }

    private static Optional<BlockPos> getSpreadableNeighbourPos(List<Direction> directions, LevelReader level, BlockPos pos, BlockState state)
    {
        for (var direction : directions)
        {
            var blockPos = pos.relative(direction);

            if (level.isEmptyBlock(blockPos) && state.canSurvive(level, blockPos))
            {
                return Optional.of(blockPos);
            }
        }
        return Optional.empty();
    }
}