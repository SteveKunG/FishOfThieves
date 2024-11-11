package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;

@SuppressWarnings("deprecation")
public class BananaClusterBlock extends HorizontalDirectionalBlock
{
//    private static final Map<Direction, VoxelShape> SHAPES = Map.of(
//            Direction.NORTH, Shapes.join(Block.box(6.5, 9, 4.5, 9.5, 11, 7.5), Block.box(5.5, 11, 3.5, 10.5, 16, 8.5), BooleanOp.OR),
//            Direction.WEST, Shapes.join(Block.box(4.5, 9, 6.5, 7.5, 11, 9.5), Block.box(3.5, 11, 5.5, 8.5, 16, 10.5), BooleanOp.OR),
//            Direction.SOUTH, Shapes.join(Block.box(6.5, 9, 8.5, 9.5, 11, 11.5), Block.box(5.5, 11, 7.5, 10.5, 16, 12.5), BooleanOp.OR),
//            Direction.EAST, Shapes.join(Block.box(8.5, 9, 6.5, 11.5, 11, 9.5), Block.box(7.5, 11, 5.5, 12.5, 16, 10.5), BooleanOp.OR)
//    );

    public BananaClusterBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos.above());

        if (blockState.is(FOTBlocks.BANANA_LEAVES))
        {
            return blockState.getValue(BananaLeavesBlock.TYPE) == BananaLeavesBlock.Type.UPPER;
        }
        return blockState.is(this);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return new ItemStack(FOTItems.BANANA);
    }

//    @Override
//    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
//    {
//        return SHAPES.get(state.getValue(FACING));
//    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return false;
    }
}