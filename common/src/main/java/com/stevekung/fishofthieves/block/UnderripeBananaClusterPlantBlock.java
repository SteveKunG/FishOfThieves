package com.stevekung.fishofthieves.block;

import java.util.Map;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
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
public class UnderripeBananaClusterPlantBlock extends AbstractBananaClusterBlock implements BonemealableBlock
{
    private static final Map<Direction, VoxelShape> UNDERRIPE_SHAPES = Map.of(
            Direction.NORTH, Block.box(4, 4, 2, 12, 16, 10),
            Direction.WEST, Block.box(2, 4, 4, 10, 16, 12),
            Direction.SOUTH, Block.box(4, 4, 6, 12, 16, 14),
            Direction.EAST, Block.box(6, 4, 4, 14, 16, 12)
    );

    public static final EnumProperty<BananaHangingType> HANGING = EnumProperty.create("hanging", BananaHangingType.class);

    public UnderripeBananaClusterPlantBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, BananaHangingType.STEM).setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return true;
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
        var otherCluster = level.getBlockState(pos.above());

        if (random.nextFloat() < 0.4f)
        {
            level.setBlock(pos, FOTBlocks.RIPE_BANANA_CLUSTER_PLANT.defaultBlockState().setValue(BananaClusterPlantBlock.HANGING, state.getValue(HANGING) == BananaHangingType.STEM ? BananaClusterPlantBlock.HangingType.STEM : otherCluster.is(this) ? BananaClusterPlantBlock.HangingType.SMALL_CLUSTER : BananaClusterPlantBlock.HangingType.NONE).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(FACING, state.getValue(FACING)), Block.UPDATE_ALL);
        }
        else
        {
            level.setBlock(pos, FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT.defaultBlockState().setValue(BananaClusterPlantBlock.HANGING, state.getValue(HANGING) == BananaHangingType.STEM ? BananaClusterPlantBlock.HangingType.STEM : otherCluster.is(this) ? BananaClusterPlantBlock.HangingType.SMALL_CLUSTER : BananaClusterPlantBlock.HangingType.NONE).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(FACING, state.getValue(FACING)), Block.UPDATE_ALL);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return new ItemStack(FOTBlocks.UNDERRIPE_BANANA_CLUSTER);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        if (direction == Direction.UP && (neighborState.is(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT) || neighborState.is(FOTBlocks.RIPE_BANANA_CLUSTER_PLANT)))
        {
            return state.setValue(HANGING, BananaHangingType.CLUSTER);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return UNDERRIPE_SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, HANGING, WATERLOGGED);
    }
}