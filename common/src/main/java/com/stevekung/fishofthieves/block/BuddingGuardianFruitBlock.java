package com.stevekung.fishofthieves.block;

import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

@SuppressWarnings("deprecation")
public class BuddingGuardianFruitBlock extends FOTRotatedPillarBlock
{
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty UP = PipeBlock.UP;
    public static final BooleanProperty DOWN = PipeBlock.DOWN;
    private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;

    public BuddingGuardianFruitBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (random.nextInt(5) == 0)
        {
            var blockState = level.getBlockState(pos.below());

            if (canFruitGrowAtState(blockState))
            {
                var blockState2 = Blocks.DIAMOND_BLOCK.defaultBlockState();
                level.setBlockAndUpdate(pos.below(), blockState2);
            }
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return super.rotate(state.setValue(PROPERTY_BY_DIRECTION.get(rotation.rotate(Direction.NORTH)), state.getValue(NORTH)).setValue(PROPERTY_BY_DIRECTION.get(rotation.rotate(Direction.SOUTH)), state.getValue(SOUTH)).setValue(PROPERTY_BY_DIRECTION.get(rotation.rotate(Direction.EAST)), state.getValue(EAST)).setValue(PROPERTY_BY_DIRECTION.get(rotation.rotate(Direction.WEST)), state.getValue(WEST)).setValue(PROPERTY_BY_DIRECTION.get(rotation.rotate(Direction.UP)), state.getValue(UP)).setValue(PROPERTY_BY_DIRECTION.get(rotation.rotate(Direction.DOWN)), state.getValue(DOWN)), rotation);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return state.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.NORTH)), state.getValue(NORTH)).setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.SOUTH)), state.getValue(SOUTH)).setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.EAST)), state.getValue(EAST)).setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.WEST)), state.getValue(WEST)).setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.UP)), state.getValue(UP)).setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.DOWN)), state.getValue(DOWN));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST));
    }

    public static boolean canFruitGrowAtState(BlockState state)
    {
        return state.is(Blocks.WATER) && state.getFluidState().getAmount() == 8;
    }
}