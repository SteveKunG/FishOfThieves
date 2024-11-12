package com.stevekung.fishofthieves.block;

import java.util.function.Function;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

@SuppressWarnings("deprecation")
public class GrowableBananaBunchStemBlock extends BananaStemBlock implements BonemealableBlock
{
    public GrowableBananaBunchStemBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (level.isRaining() && random.nextInt(10) == 0)
        {
            this.growBananaBunch(level, random, pos);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient)
    {
        return Direction.Plane.HORIZONTAL.stream().anyMatch(direction -> this.canGrowBananaBunch(level, pos, direction));
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state)
    {
        return true;
        //        return random.nextInt(5) == 0;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        this.growBananaBunch(level, random, pos);
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

    private void growBananaBunch(ServerLevel level, RandomSource random, BlockPos pos)
    {
        Direction.Plane.HORIZONTAL.shuffledCopy(random).stream().filter(direction -> this.canGrowBananaBunch(level, pos, direction)).findFirst().ifPresent(direction -> this.setClusterOrBlossomBlockRandomly(direction, level, random, pos.below()));
    }

    private void setClusterOrBlossomBlockRandomly(Direction direction, ServerLevel level, RandomSource random, BlockPos pos)
    {
        Function<BlockPos, Boolean> isWater = blockPos -> level.getFluidState(blockPos).getType() == Fluids.WATER;
        var yOffset = 0;
        var isSmallCluster = false;

        if (random.nextBoolean())
        {
            var banana = random.nextFloat() < 0.2f ? FOTBlocks.RIPE_BANANA_CLUSTER_PLANT.defaultBlockState() : random.nextFloat() < 0.4f ? FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT.defaultBlockState() : FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT.defaultBlockState();

            for (var i = 0; i < 1 + random.nextInt(3) && level.getBlockState(pos.below().relative(direction)).isAir(); i++)
            {
                var blockStateBelow = level.getBlockState(pos.below(i).relative(direction));

                if (blockStateBelow.isAir())
                {
                    if (banana.hasProperty(BananaClusterBlock.HANGING))
                    {
                        banana = banana.setValue(BananaClusterBlock.HANGING, yOffset == 0 ? BananaClusterBlock.HangingType.STEM : BananaClusterBlock.HangingType.NONE);
                    }
                    else if (banana.hasProperty(UnderripeBananaClusterPlantBlock.HANGING))
                    {
                        banana = banana.setValue(UnderripeBananaClusterPlantBlock.HANGING, yOffset == 0 ? BananaHangingType.STEM : BananaHangingType.SMALL_CLUSTER);
                        isSmallCluster = true;
                    }

                    level.setBlock(pos.below(i).relative(direction), banana.setValue(BananaClusterBlock.FACING, direction.getOpposite()), Block.UPDATE_CLIENTS);
                    yOffset++;
                }
                else
                {
                    break;
                }
            }
        }
        if (level.getBlockState(pos.below(yOffset).relative(direction)).isAir())
        {
            level.setBlock(pos.below(yOffset).relative(direction), FOTBlocks.BANANA_BLOSSOM_PLANT.defaultBlockState().setValue(BananaBlossomPlantBlock.FACING, direction.getOpposite()).setValue(BananaBlossomPlantBlock.HANGING, yOffset == 0 ? BananaHangingType.STEM : isSmallCluster ? BananaHangingType.SMALL_CLUSTER : BananaHangingType.CLUSTER).setValue(BananaBlossomPlantBlock.WATERLOGGED, isWater.apply(pos.below(yOffset).relative(direction))), Block.UPDATE_CLIENTS);
        }
    }
}