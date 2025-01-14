package com.stevekung.fishofthieves.block;

import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class MangoFruitBlock extends AbstractMangoFruitBlock
{
    private static final VoxelShape[] EAST_AABB = new VoxelShape[] {
            Block.box(13, 5, 6.5, 16, 10, 9.5),
            Block.box(11, 3, 5.5, 16, 10, 10.5) };
    private static final VoxelShape[] WEST_AABB = new VoxelShape[] {
            Block.box(0, 5, 6.5, 3, 10, 9.5),
            Block.box(0, 3, 5.5, 5, 10, 10.5) };
    private static final VoxelShape[] NORTH_AABB = new VoxelShape[] {
            Block.box(6.5, 5, 0, 9.5, 10, 3),
            Block.box(5.5, 3, 0, 10.5, 10, 5) };
    private static final VoxelShape[] SOUTH_AABB = new VoxelShape[] {
            Block.box(6.5, 5, 13, 9.5, 10, 16),
            Block.box(5.5, 3, 11, 10.5, 10, 16) };

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MangoFruitBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, 0).setValue(FALLING, false));
    }

    @Override
    public float getMaxHorizontalOffset()
    {
        return 0F;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos.relative(state.getValue(FACING)));
        return blockState.is(FOTBlocks.MANGO_LEAVES);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        var age = Math.min(state.getValue(AGE), 1);
        var offset = state.getOffset(level, pos);
        return (switch (state.getValue(FACING))
        {
            case SOUTH -> SOUTH_AABB[age];
            case WEST -> WEST_AABB[age];
            case EAST -> EAST_AABB[age];
            default -> NORTH_AABB[age];
        }).move(offset.x, offset.y, offset.z);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        var otherBlockState = level.getBlockState(pos.relative(state.getValue(FACING)));

        if (!otherBlockState.is(FOTBlocks.MANGO_LEAVES) && !isFree(level.getBlockState(pos.below())))
        {
            return Blocks.AIR.defaultBlockState();
        }

        if (!state.canSurvive(level, pos))
        {
            level.scheduleTick(pos, this, 2);
        }
        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(FACING));
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