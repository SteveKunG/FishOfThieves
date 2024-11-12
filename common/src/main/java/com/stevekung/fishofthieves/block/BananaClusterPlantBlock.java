package com.stevekung.fishofthieves.block;

import java.util.Locale;
import java.util.Map;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class BananaClusterPlantBlock extends AbstractBananaClusterBlock implements BonemealableBlock
{
    private static final Map<Direction, VoxelShape> SHAPES = Map.of(
            Direction.NORTH, Block.box(2, 0, 0, 14, 16, 12),
            Direction.WEST, Block.box(0, 0, 2, 12, 16, 14),
            Direction.SOUTH, Block.box(2, 0, 4, 14, 16, 16),
            Direction.EAST, Block.box(4, 0, 2, 16, 16, 14)
    );

    public static final EnumProperty<HangingType> HANGING = EnumProperty.create("hanging", HangingType.class);
    private final BananaClusterBlock.Type type;

    public BananaClusterPlantBlock(BananaClusterBlock.Type type, Properties properties)
    {
        super(properties);
        this.type = type;
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, HangingType.NONE).setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return state.is(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        var fluidState = level.getFluidState(pos);

        if (state.is(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT))
        {
            level.setBlock(pos, FOTBlocks.RIPE_BANANA_CLUSTER_PLANT.defaultBlockState().setValue(HANGING, state.getValue(HANGING)).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(FACING, state.getValue(FACING)), Block.UPDATE_ALL);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return new ItemStack(this.type.block().get());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        if (direction == Direction.UP && (neighborState.is(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT) || neighborState.is(FOTBlocks.RIPE_BANANA_CLUSTER_PLANT)))
        {
            return state.getValue(HANGING) == HangingType.STEM ? state : state.setValue(HANGING, HangingType.NONE);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction)
    {
        return adjacentState.is(this) && direction.getAxis().isVertical();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, HANGING, WATERLOGGED);
    }

    public enum HangingType implements StringRepresentable
    {
        NONE, SMALL_CLUSTER, STEM;

        @Override
        public String getSerializedName()
        {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}