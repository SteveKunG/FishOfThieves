package com.stevekung.fishofthieves.block;

import java.util.function.Function;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

@SuppressWarnings("deprecation")
public class GrowableBananaClusterStemBlock extends BananaStemBlock implements BonemealableBlock
{
    public GrowableBananaClusterStemBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (level.isRaining() && level.canSeeSky(pos) && random.nextInt(10) == 0)
        {
            this.growRandomBananaCluster(level, random, pos);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return new ItemStack(FOTBlocks.BANANA_STEM);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return Direction.Plane.HORIZONTAL.stream().anyMatch(direction -> this.canGrowBananaBunch(level, pos, direction));
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return random.nextInt(4) == 0;
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
        Direction.Plane.HORIZONTAL.shuffledCopy(random).stream().filter(direction -> this.canGrowBananaBunch(level, pos, direction)).findFirst().ifPresent(direction -> this.setClusterOrBlossomBlockRandomly(direction, level, random, pos.below()));
    }

    private void setClusterOrBlossomBlockRandomly(Direction direction, ServerLevel level, RandomSource random, BlockPos pos)
    {
        pos = pos.relative(direction);

        Function<BlockPos, Boolean> isWater = blockPos -> level.getFluidState(blockPos).getType() == Fluids.WATER;
        var maxY = this.findMaxYBelow(level, pos);
        var isSmallCluster = false;

        if (maxY == 1)
        {
            level.setBlock(pos, FOTBlocks.BANANA_BLOSSOM_PLANT.defaultBlockState().setValue(BananaBlossomPlantBlock.FACING, direction.getOpposite()).setValue(BananaBlossomPlantBlock.HANGING, BananaHangingType.STEM).setValue(BananaBlossomPlantBlock.WATERLOGGED, isWater.apply(pos)), Block.UPDATE_CLIENTS);
        }
        else
        {
            var yBottom = 0;
            var randHeight = 1 + random.nextInt(maxY);

            for (var i = 0; i < randHeight; i++)
            {
                var blockPos = pos.below(i);
                var banana = random.nextFloat() < 0.2f ? FOTBlocks.RIPE_BANANA_CLUSTER_PLANT.defaultBlockState() : random.nextFloat() < 0.4f ? FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT.defaultBlockState() : FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT.defaultBlockState();

                if (banana.hasProperty(BananaClusterPlantBlock.HANGING))
                {
                    banana = banana.setValue(BananaClusterPlantBlock.HANGING, i == 0 ? BananaClusterPlantBlock.HangingType.STEM : BananaClusterPlantBlock.HangingType.NONE);

                    if (level.getBlockState(blockPos.above()).is(FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT))
                    {
                        banana = banana.setValue(BananaClusterPlantBlock.HANGING, BananaClusterPlantBlock.HangingType.SMALL_CLUSTER);
                    }
                }
                else if (banana.hasProperty(UnderripeBananaClusterPlantBlock.HANGING))
                {
                    banana = banana.setValue(UnderripeBananaClusterPlantBlock.HANGING, i == 0 ? BananaHangingType.STEM : BananaHangingType.SMALL_CLUSTER);
                    isSmallCluster = true;
                }

                level.setBlock(blockPos, banana.setValue(BananaClusterPlantBlock.FACING, direction.getOpposite()).setValue(BananaClusterPlantBlock.WATERLOGGED, isWater.apply(pos)), Block.UPDATE_CLIENTS);

                if (yBottom < i)
                {
                    yBottom = i;
                }
            }
            level.setBlock(pos.below(yBottom), FOTBlocks.BANANA_BLOSSOM_PLANT.defaultBlockState().setValue(BananaBlossomPlantBlock.FACING, direction.getOpposite()).setValue(BananaBlossomPlantBlock.HANGING, yBottom == 0 ? BananaHangingType.STEM : isSmallCluster ? BananaHangingType.SMALL_CLUSTER : BananaHangingType.CLUSTER).setValue(BananaBlossomPlantBlock.WATERLOGGED, isWater.apply(pos.below(yBottom))), Block.UPDATE_CLIENTS);
        }
    }

    private int findMaxYBelow(ServerLevel level, BlockPos pos)
    {
        var maxBananaCluster = 6;
        var maxY = 0;

        while (level.getBlockState(pos.below(maxY)).canBeReplaced() && maxY < maxBananaCluster)
        {
            ++maxY;
        }
        return maxY;
    }
}