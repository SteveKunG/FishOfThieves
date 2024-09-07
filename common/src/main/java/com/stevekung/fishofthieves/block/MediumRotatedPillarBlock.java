package com.stevekung.fishofthieves.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MediumRotatedPillarBlock extends RotatedPillarBlock
{
    private static final VoxelShape SHAPE_VERTICAL = Block.box(1, 0, 1, 15, 16, 15);
    private static final VoxelShape SHAPE_HORIZONTAL_NS = Block.box(1, 1, 0, 15, 15, 16);
    private static final VoxelShape SHAPE_HORIZONTAL_WE = Block.box(0, 1, 1, 16, 15, 15);

    public MediumRotatedPillarBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
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