package com.stevekung.fishofthieves.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class MediumRotatedPillarBlock extends RotatedPillarBlock
{
    private static final VoxelShape SHAPE_VERTICAL = Block.box(1, 0, 1, 15, 16, 15);
    private static final VoxelShape SHAPE_HORIZONTAL_NS = Block.box(1, 1, 0, 15, 15, 16);
    private static final VoxelShape SHAPE_HORIZONTAL_WE = Block.box(0, 1, 1, 16, 15, 15);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public MediumRotatedPillarBlock(Properties properties)
    {
        super(properties);
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
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        if (state.getValue(WATERLOGGED))
        {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction)
    {
        return adjacentState.is(this) && state.getValue(AXIS) == adjacentState.getValue(AXIS);
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
}