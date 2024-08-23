package com.stevekung.fishofthieves.block;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FishBoneBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock
{
    private static final VoxelShape X_SHAPE = Block.box(4, 0, 0, 12, 3, 16);
    private static final VoxelShape Y_SHAPE = Block.box(0, 0, 4, 16, 3, 12);
    public static final MapCodec<FishBoneBlock> CODEC = simpleCodec(FishBoneBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public FishBoneBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return CODEC;
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
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context)
    {
        var vec3 = blockState.getOffset(blockPos);
        var direction = blockState.getValue(FACING);

        if (direction.getAxis() == Direction.Axis.X)
        {
            return X_SHAPE.move(vec3.x, vec3.y, vec3.z);
        }
        return Y_SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos)
    {
        if (blockState.getValue(WATERLOGGED))
        {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(blockState, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        if (state.getValue(WATERLOGGED))
        {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, @Nullable Orientation orientation, boolean isMoving)
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
        var fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
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
        builder.add(FACING, WATERLOGGED);
    }
}