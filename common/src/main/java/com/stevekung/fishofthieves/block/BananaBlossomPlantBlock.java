package com.stevekung.fishofthieves.block;

import java.util.Map;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BananaBlossomPlantBlock extends BananaBlossomBlock
{
    private static final Map<Direction, VoxelShape> SHAPES = Map.of(
            Direction.NORTH, Shapes.join(Block.box(6.5, 9, 4.5, 9.5, 11, 7.5), Block.box(5.5, 11, 3.5, 10.5, 16, 8.5), BooleanOp.OR),
            Direction.WEST, Shapes.join(Block.box(4.5, 9, 6.5, 7.5, 11, 9.5), Block.box(3.5, 11, 5.5, 8.5, 16, 10.5), BooleanOp.OR),
            Direction.SOUTH, Shapes.join(Block.box(6.5, 9, 8.5, 9.5, 11, 11.5), Block.box(5.5, 11, 7.5, 10.5, 16, 12.5), BooleanOp.OR),
            Direction.EAST, Shapes.join(Block.box(8.5, 9, 6.5, 11.5, 11, 9.5), Block.box(7.5, 11, 5.5, 12.5, 16, 10.5), BooleanOp.OR)
    );
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<BananaHangingType> HANGING = EnumProperty.create("hanging", BananaHangingType.class);

    public BananaBlossomPlantBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(HANGING, BananaHangingType.STEM));
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
        if (direction == Direction.UP && (neighborState.is(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT) || neighborState.is(FOTBlocks.RIPE_BANANA_CLUSTER_PLANT)))
        {
            return state.setValue(HANGING, BananaHangingType.CLUSTER);
        }
        return super.updateShape(state, level, scheduledTickAccess, currentPos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(FACING, HANGING));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}