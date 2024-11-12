package com.stevekung.fishofthieves.block;

import java.util.function.Supplier;

import com.stevekung.fishofthieves.registry.FOTBlocks;
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
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class BananaClusterBlock extends Block implements BonemealableBlock, SimpleWaterloggedBlock
{
    private static final VoxelShape UNDERRIPE_SHAPE = Block.box(4, 0, 4, 12, 12, 12);
    private static final VoxelShape NORMAL_SHAPE = Block.box(2, 0, 2, 14, 16, 14);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final Type type;

    public BananaClusterBlock(Type type, Properties properties)
    {
        super(properties);
        this.type = type;
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
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
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return this.type != Type.RIPE;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        //        var fluidState = level.getFluidState(pos);
        //
        //        if (state.is(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT))
        //        {
        //            level.setBlock(pos, FOTBlocks.RIPE_BANANA_CLUSTER_PLANT.defaultBlockState().setValue(HANGING, state.getValue(HANGING)).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(FACING, state.getValue(FACING)), Block.UPDATE_ALL);
        //        }
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction)
    {
        if (this.type == Type.UNDERRIPE)
        {
            return false;
        }
        return adjacentState.is(this) && direction.getAxis().isVertical();
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return new ItemStack(this.type.block().get());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return this.type == Type.UNDERRIPE ? UNDERRIPE_SHAPE : NORMAL_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
    }

    public enum Type
    {
        UNDERRIPE(() -> FOTBlocks.UNDERRIPE_BANANA_CLUSTER),
        BARELY_RIPE(() -> FOTBlocks.BARELY_RIPE_BANANA_CLUSTER),
        RIPE(() -> FOTBlocks.RIPE_BANANA_CLUSTER);

        private final Supplier<Block> block;

        Type(Supplier<Block> block)
        {
            this.block = block;
        }

        public Supplier<Block> block()
        {
            return this.block;
        }
    }
}