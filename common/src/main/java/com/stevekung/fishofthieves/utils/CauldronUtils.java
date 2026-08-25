package com.stevekung.fishofthieves.utils;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.stevekung.fishofthieves.mixin.accessor.PointedDripstoneBlockAccessor;
import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public class CauldronUtils
{
    private static final Predicate<BlockState> IS_CAULDRON = blockState -> blockState.getBlock() instanceof AbstractCauldronBlock;

    private CauldronUtils() {}

    public static void fillCauldronFromLeavesTail(BlockState state, ServerLevel level, BlockPos pos)
    {
        var optional = CauldronUtils.findFillableCauldronBelowLeavesTail(level, pos);

        if (optional.isPresent())
        {
            var blockPos2 = optional.get();
            var blockState2 = level.getBlockState(blockPos2);
            var trigger = false;

            if (blockState2.getBlock() != Blocks.WATER_CAULDRON)
            {
                var blockState = Blocks.WATER_CAULDRON.defaultBlockState();
                level.setBlockAndUpdate(blockPos2, blockState);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos2, GameEvent.Context.of(blockState));
                level.levelEvent(LevelEvent.SOUND_DRIP_WATER_INTO_CAULDRON, blockPos2, 0);
                trigger = true;
            }
            else if (!((LayeredCauldronBlock) blockState2.getBlock()).isFull(blockState2))
            {
                var blockState = blockState2.setValue(LayeredCauldronBlock.LEVEL, blockState2.getValue(LayeredCauldronBlock.LEVEL) + 1);
                level.setBlockAndUpdate(blockPos2, blockState);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos2, GameEvent.Context.of(blockState));
                level.levelEvent(LevelEvent.SOUND_DRIP_WATER_INTO_CAULDRON, blockPos2, 0);
                trigger = true;
            }

            if (trigger)
            {
                for (var serverPlayer : level.getNearbyPlayers(TargetingConditions.forNonCombat(), null, new AABB(pos).inflate(8)).stream().map(ServerPlayer.class::cast).toList())
                {
                    FOTCriteriaTriggers.WATER_DRIP_ON_BLOCK.trigger(level, pos, serverPlayer, state);
                }
            }
        }
    }

    public static Optional<BlockPos> findFillableCauldronBelowLeavesTail(Level level, BlockPos pos)
    {
        BiPredicate<BlockPos, BlockState> biPredicate = (blockPos, blockState) -> PointedDripstoneBlockAccessor.invokeCanDripThrough(level, blockPos, blockState);
        return PointedDripstoneBlockAccessor.invokeFindBlockVertical(level, pos, Direction.DOWN.getAxisDirection(), biPredicate, IS_CAULDRON, 11);
    }
}