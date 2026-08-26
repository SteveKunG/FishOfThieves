package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;

public abstract class AbstractBananaClusterBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected AbstractBananaClusterBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos.above());

        if (blockState.is(FOTBlocks.BANANA_LEAVES))
        {
            return blockState.getValue(BananaLeavesBlock.TYPE) == BananaLeavesBlock.Type.UPPER;
        }
        return blockState.is(FOTTags.Blocks.BANANA_CLUSTER_PLANTS);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource)
    {
        if (state.getValue(WATERLOGGED))
        {
            scheduledTickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, scheduledTickAccess, currentPos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType pathComputationType)
    {
        return false;
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile)
    {
        var blockPos = hit.getBlockPos();

        if (level instanceof ServerLevel serverLevel && projectile.mayInteract(serverLevel, blockPos) && projectile.getType().is(EntityTypeTags.IMPACT_PROJECTILES))
        {
            level.destroyBlock(blockPos, true, projectile);
        }
    }
}