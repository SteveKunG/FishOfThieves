package com.stevekung.fishofthieves.utils;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.stevekung.fishofthieves.mixin.accessor.PointedDripstoneBlockAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class CauldronUtils
{
    public static void fillCauldronFromLeavesTail(Level level, BlockPos pos)
    {
        var optional = CauldronUtils.findFillableCauldronBelowLeavesTail(level, pos);

        if (optional.isPresent())
        {
            var blockPos2 = optional.get();
            var blockState2 = level.getBlockState(blockPos2);

            if (blockState2.getBlock() != Blocks.WATER_CAULDRON)
            {
                var blockState = Blocks.WATER_CAULDRON.defaultBlockState();
                level.setBlockAndUpdate(blockPos2, blockState);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos2, GameEvent.Context.of(blockState));
                level.levelEvent(LevelEvent.SOUND_DRIP_WATER_INTO_CAULDRON, blockPos2, 0);
            }
            else if (!((LayeredCauldronBlock) blockState2.getBlock()).isFull(blockState2))
            {
                var blockState = blockState2.setValue(LayeredCauldronBlock.LEVEL, blockState2.getValue(LayeredCauldronBlock.LEVEL) + 1);
                level.setBlockAndUpdate(blockPos2, blockState);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos2, GameEvent.Context.of(blockState));
                level.levelEvent(LevelEvent.SOUND_DRIP_WATER_INTO_CAULDRON, blockPos2, 0);
            }
        }
    }

    public static Optional<BlockPos> findFillableCauldronBelowLeavesTail(Level level, BlockPos pos)
    {
        Predicate<BlockState> predicate = blockState -> blockState.getBlock() instanceof AbstractCauldronBlock;
        BiPredicate<BlockPos, BlockState> biPredicate = (blockPos, blockState) -> PointedDripstoneBlockAccessor.invokeCanDripThrough(level, blockPos, blockState);
        return PointedDripstoneBlockAccessor.invokeFindBlockVertical(level, pos, Direction.DOWN.getAxisDirection(), biPredicate, predicate, 11);
    }
}