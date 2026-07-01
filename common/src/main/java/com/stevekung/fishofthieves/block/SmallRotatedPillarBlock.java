package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallRotatedPillarBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock
{
    private static final VoxelShape SHAPE_VERTICAL = Block.box(2, 0, 2, 14, 16, 14);
    private static final VoxelShape SHAPE_HORIZONTAL_NS = Block.box(2, 2, 0, 14, 14, 16);
    private static final VoxelShape SHAPE_HORIZONTAL_WE = Block.box(0, 2, 2, 16, 14, 14);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final BlockState blockState;

    public SmallRotatedPillarBlock(Properties properties)
    {
        this(null, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(WATERLOGGED, false));
    }

    public SmallRotatedPillarBlock(BlockState blockState, Properties properties)
    {
        super(properties);
        this.blockState = blockState;
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(WATERLOGGED));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return super.getStateForPlacement(context).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData)
    {
        return this.blockState != null ? new ItemStack(this.blockState.getBlock()) : super.getCloneItemStack(level, pos, state, includeData);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource)
    {
        if (state.getValue(WATERLOGGED))
        {
            scheduledTickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, level, scheduledTickAccess, currentPos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction)
    {
        return adjacentState.is(FOTTags.Blocks.NON_FULL_LOGS) && state.getValue(AXIS) == adjacentState.getValue(AXIS);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        switch (state.getValue(AXIS))
        {
            case X ->
            {
                return SHAPE_HORIZONTAL_WE;
            }
            case Z ->
            {
                return SHAPE_HORIZONTAL_NS;
            }
            default ->
            {
                return SHAPE_VERTICAL;
            }
        }
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state)
    {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type)
    {
        return false;
    }
}