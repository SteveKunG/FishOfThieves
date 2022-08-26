package com.stevekung.fishofthieves.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class FishBoneBlock extends HorizontalDirectionalBlock
{
    private static final VoxelShape X_SHAPE = Block.box(4, 0, 0, 12, 3, 16);
    private static final VoxelShape Y_SHAPE = Block.box(0, 0, 4, 16, 3, 12);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public FishBoneBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public float getMaxHorizontalOffset()
    {
        return 0.2f;
    }

    @Override
    public float getMaxVerticalOffset()
    {
        return 0.05f;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state)
    {
        return PushReaction.DESTROY;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context)
    {
        var vec3 = blockState.getOffset(level, blockPos);
        var direction = blockState.getValue(FACING);

        if (direction.getAxis() == Direction.Axis.X)
        {
            return X_SHAPE.move(vec3.x, vec3.y, vec3.z);
        }
        return Y_SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos fromPos, boolean isMoving)
    {
        if (!blockState.canSurvive(level, blockPos))
        {
            level.destroyBlock(blockPos, true);
        }
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos)
    {
        return canSupportRigidBlock(level, blockPos.below());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation)
    {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror)
    {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }
}