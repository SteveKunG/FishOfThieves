package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BananaStemBlock extends SmallRotatedPillarBlock
{
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    private static final VoxelShape SHAPE_VERTICAL = Block.box(3, 0, 3, 13, 16, 13);
    private static final VoxelShape SHAPE_HORIZONTAL_NS = Block.box(3, 3, 0, 13, 13, 16);
    private static final VoxelShape SHAPE_HORIZONTAL_WE = Block.box(0, 3, 3, 16, 13, 13);

    public BananaStemBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(WATERLOGGED, false).setValue(TOP, false));
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction)
    {
        return (adjacentState.is(this) || adjacentState.is(FOTTags.Blocks.NON_FULL_LOGS)) && state.getValue(AXIS) == adjacentState.getValue(AXIS);
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(TOP));
    }
}