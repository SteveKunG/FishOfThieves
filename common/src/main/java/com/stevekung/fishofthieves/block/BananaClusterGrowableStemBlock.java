package com.stevekung.fishofthieves.block;

import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.util.TriConsumer;

import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class BananaClusterGrowableStemBlock extends BananaStemBlock implements BonemealableBlock
{
    public BananaClusterGrowableStemBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (level.isRaining() && level.canSeeSky(pos) && random.nextInt(8) == 0)
        {
            this.growRandomBananaCluster(level, random, pos);
        }
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData)
    {
        return new ItemStack(FOTBlocks.BANANA_STEM);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state)
    {
        return Direction.Plane.HORIZONTAL.stream().anyMatch(direction -> this.canGrowBananaBunch(level, pos, direction));
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return random.nextInt(level.isRaining() ? 3 : 4) == 0;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        this.growRandomBananaCluster(level, random, pos);
    }

    private boolean canGrowBananaBunch(BlockGetter level, BlockPos pos, Direction direction)
    {
        var leavesState = level.getBlockState(pos.relative(direction));

        if (leavesState.is(FOTBlocks.BANANA_LEAVES))
        {
            return leavesState.getValue(BananaLeavesBlock.TYPE) == BananaLeavesBlock.Type.UPPER && level.getBlockState(pos.below().relative(direction)).isAir();
        }
        return false;
    }

    private void growRandomBananaCluster(ServerLevel level, RandomSource random, BlockPos pos)
    {
        Direction.Plane.HORIZONTAL.shuffledCopy(random)
                .stream()
                .filter(direction -> this.canGrowBananaBunch(level, pos, direction))
                .findFirst()
                .ifPresent(direction -> growBananaBlossomOrCluster(direction, level, level::setBlock, blockPos -> level.getFluidState(blockPos).getType() == Fluids.WATER, random, pos.below().relative(direction)));
    }

    public static void growBananaBlossomOrCluster(Direction direction, LevelSimulatedReader level, TriConsumer<BlockPos, BlockState, Integer> setBlock, Function<BlockPos, Boolean> isWater, RandomSource random, BlockPos pos)
    {
        var maxY = findMaxYBelow(level, pos);
        var isSmallCluster = false;

        if (maxY == 1)
        {
            setBlock.accept(pos, createBlossomState(direction, isWater.apply(pos), BananaHangingType.STEM), Block.UPDATE_ALL);
        }
        else
        {
            var yBottom = 0;
            var randHeight = 1 + random.nextInt(maxY);

            for (var i = 0; i < randHeight; i++)
            {
                var blockPos = pos.below(i);
                Predicate<BlockState> stateAbove = state -> level.isStateAtPosition(blockPos.above(), blockState -> blockState.is(state.getBlock()));
                var banana = selectBananaState(random);

                banana = updateBananaHangingState(banana, stateAbove, i);
                isSmallCluster |= banana.hasProperty(UnderripeBananaClusterPlantBlock.HANGING);

                setBlock.accept(blockPos, banana.setValue(BananaClusterPlantBlock.FACING, direction.getOpposite()).setValue(BananaClusterPlantBlock.WATERLOGGED, isWater.apply(blockPos)), Block.UPDATE_ALL);

                yBottom = Math.max(yBottom, i);
            }
            setBlock.accept(pos.below(yBottom), createBlossomState(direction, isWater.apply(pos.below(yBottom)), determineHangingType(yBottom, isSmallCluster)), Block.UPDATE_ALL);
        }
    }

    private static BlockState createBlossomState(Direction direction, boolean isWaterlogged, BananaHangingType hangingType)
    {
        return FOTBlocks.BANANA_BLOSSOM_PLANT.defaultBlockState()
                .setValue(BananaBlossomPlantBlock.FACING, direction.getOpposite())
                .setValue(BananaBlossomPlantBlock.HANGING, hangingType)
                .setValue(BananaBlossomPlantBlock.WATERLOGGED, isWaterlogged);
    }

    private static BlockState selectBananaState(RandomSource random)
    {
        return random.nextFloat() < 0.2f ? FOTBlocks.RIPE_BANANA_CLUSTER_PLANT.defaultBlockState()
                : random.nextFloat() < 0.4f ? FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT.defaultBlockState()
                : FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT.defaultBlockState();
    }

    private static BlockState updateBananaHangingState(BlockState banana, Predicate<BlockState> stateAbove, int i)
    {
        if (banana.hasProperty(BananaClusterPlantBlock.HANGING))
        {
            banana = banana.setValue(BananaClusterPlantBlock.HANGING, i == 0 ? BananaClusterPlantBlock.HangingType.STEM : BananaClusterPlantBlock.HangingType.NONE);

            if (stateAbove.test(FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT.defaultBlockState()))
            {
                banana = banana.setValue(BananaClusterPlantBlock.HANGING, BananaClusterPlantBlock.HangingType.SMALL_CLUSTER);
            }
        }
        else if (banana.hasProperty(UnderripeBananaClusterPlantBlock.HANGING))
        {
            var isLargeCluster = stateAbove.test(FOTBlocks.RIPE_BANANA_CLUSTER_PLANT.defaultBlockState()) || stateAbove.test(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT.defaultBlockState());
            banana = banana.setValue(UnderripeBananaClusterPlantBlock.HANGING, i == 0 ? BananaHangingType.STEM : isLargeCluster ? BananaHangingType.CLUSTER : BananaHangingType.SMALL_CLUSTER);
        }
        return banana;
    }

    private static BananaHangingType determineHangingType(int yBottom, boolean isSmallCluster)
    {
        return yBottom == 0 ? BananaHangingType.STEM : isSmallCluster ? BananaHangingType.SMALL_CLUSTER : BananaHangingType.CLUSTER;
    }

    private static int findMaxYBelow(LevelSimulatedReader level, BlockPos pos)
    {
        var maxY = 0;

        while (level.isStateAtPosition(pos.below(maxY), BlockState::canBeReplaced) && maxY < 6)
        {
            ++maxY;
        }
        return maxY;
    }
}