package com.stevekung.fishofthieves.block;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class CoconutFruitBlock extends HorizontalDirectionalBlock implements BonemealableBlock
{
    private static final VoxelShape[] EAST_AABB = new VoxelShape[] {
            Block.box(12, 8, 5, 18, 14, 11),
            Block.box(10, 6, 4, 18, 14, 12),
            Block.box(7, 3, 2.5, 18, 14, 13.5) };
    private static final VoxelShape[] WEST_AABB = new VoxelShape[] {
            Block.box(-2, 8, 5, 4, 14, 11),
            Block.box(-2, 6, 4, 6, 14, 12),
            Block.box(-2, 3, 2.5, 9, 14, 13.5) };
    private static final VoxelShape[] NORTH_AABB = new VoxelShape[] {
            Block.box(5, 8, -2, 11, 14, 4),
            Block.box(4, 6, -2, 12, 14, 6),
            Block.box(2.5, 3, -2, 13.5, 14, 9) };
    private static final VoxelShape[] SOUTH_AABB = new VoxelShape[] {
            Block.box(5, 8, 12, 11, 14, 18),
            Block.box(4, 6, 10, 12, 14, 18),
            Block.box(2.5, 3, 7, 13.5, 14, 18) };

    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;

    public CoconutFruitBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, 0));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(AGE) < 2;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (level.random.nextInt(5) == 0)
        {
            int i = state.getValue(AGE);

            if (i < 2)
            {
                level.setBlock(pos, state.setValue(AGE, i + 1), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos.relative(state.getValue(FACING)));
        return blockState.is(FOTBlocks.SMALL_COCONUT_LOG) && blockState.getValue(BlockStateProperties.AXIS) == Direction.Axis.Y;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int i = state.getValue(AGE);
        return switch (state.getValue(FACING))
        {
            case SOUTH -> SOUTH_AABB[i];
            default -> NORTH_AABB[i];
            case WEST -> WEST_AABB[i];
            case EAST -> EAST_AABB[i];
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var blockState = this.defaultBlockState();
        var levelReader = context.getLevel();
        var blockPos = context.getClickedPos();

        for (var direction : context.getNearestLookingDirections())
        {
            if (direction.getAxis().isHorizontal())
            {
                blockState = blockState.setValue(FACING, direction);

                if (blockState.canSurvive(levelReader, blockPos))
                {
                    return blockState;
                }
            }
        }
        return null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        return direction == state.getValue(FACING) && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return state.getValue(AGE) < 2;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        level.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), Block.UPDATE_CLIENTS);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, AGE);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return false;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return new ItemStack(FOTItems.COCONUT);
    }
}