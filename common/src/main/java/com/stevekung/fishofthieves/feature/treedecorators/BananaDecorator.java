package com.stevekung.fishofthieves.feature.treedecorators;

import java.util.Collections;
import java.util.function.Function;
import java.util.function.Predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.block.BananaBlossomPlantBlock;
import com.stevekung.fishofthieves.block.BananaClusterPlantBlock;
import com.stevekung.fishofthieves.block.BananaHangingType;
import com.stevekung.fishofthieves.block.UnderripeBananaClusterPlantBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTreeDecoratorTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.material.Fluids;

public class BananaDecorator extends TreeDecorator
{
    public static final MapCodec<BananaDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(decorator -> decorator.probability),
            Codec.floatRange(0.0F, 1.0F).fieldOf("ripe_probability").forGetter(decorator -> decorator.ripeProbability),
            Codec.floatRange(0.0F, 1.0F).fieldOf("barely_ripe_probability").forGetter(decorator -> decorator.barelyRipeProbability),
            Codec.intRange(1, 16).fieldOf("max_banana_cluster").forGetter(decorator -> decorator.maxBananaCluster)
    ).apply(instance, BananaDecorator::new));
    private final float probability;
    private final float ripeProbability;
    private final float barelyRipeProbability;
    private final int maxBananaCluster;

    public BananaDecorator(float probability, float ripeProbability, float barelyRipeProbability, int maxBananaCluster)
    {
        this.probability = probability;
        this.ripeProbability = ripeProbability;
        this.barelyRipeProbability = barelyRipeProbability;
        this.maxBananaCluster = maxBananaCluster;
    }

    @Override
    protected TreeDecoratorType<?> type()
    {
        return FOTTreeDecoratorTypes.BANANA;
    }

    @Override
    public void place(Context context)
    {
        var randomSource = context.random();
        var list = context.logs();
        var yAtStart = list.get(0).getY();
        var maxY = Collections.max(list.stream().map(blockPos -> blockPos.getY() - yAtStart).toList());
        var yToGrowAt = maxY - 1;

        list.stream().filter(blockPos -> blockPos.getY() - yAtStart == yToGrowAt).forEach(blockPos ->
        {
            for (var direction : Direction.Plane.HORIZONTAL)
            {
                if (!(randomSource.nextFloat() >= this.probability))
                {
                    var direction2 = direction.getOpposite();
                    var blockPos2 = blockPos.offset(direction2.getStepX(), 0, direction2.getStepZ());

                    if (context.isAir(blockPos2))
                    {
                        this.growBananaBlossomOrCluster(direction2, context, randomSource, blockPos2);
                    }
                }
            }
        });
    }

    private void growBananaBlossomOrCluster(Direction direction, Context context, RandomSource random, BlockPos pos)
    {
        var level = context.level();
        Function<BlockPos, Boolean> isWater = blockPos -> level.isFluidAtPosition(blockPos, fluidState -> fluidState.is(Fluids.WATER));
        var maxY = this.findMaxYBelow(level, pos);
        var isSmallCluster = false;

        if (maxY == 1)
        {
            context.setBlock(pos, FOTBlocks.BANANA_BLOSSOM_PLANT.defaultBlockState().setValue(BananaBlossomPlantBlock.FACING, direction.getOpposite()).setValue(BananaBlossomPlantBlock.HANGING, BananaHangingType.STEM).setValue(BananaBlossomPlantBlock.WATERLOGGED, isWater.apply(pos)));
        }
        else
        {
            var yBottom = 0;
            var randHeight = 1 + random.nextInt(maxY);

            for (var i = 0; i < randHeight; i++)
            {
                var blockPos = pos.below(i);
                Function<Predicate<BlockState>, Boolean> stateAbove = predicate -> level.isStateAtPosition(blockPos.above(), predicate);
                var banana = random.nextFloat() < this.ripeProbability ? FOTBlocks.RIPE_BANANA_CLUSTER_PLANT.defaultBlockState() : random.nextFloat() < this.barelyRipeProbability ? FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT.defaultBlockState() : FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT.defaultBlockState();

                if (banana.hasProperty(BananaClusterPlantBlock.HANGING))
                {
                    banana = banana.setValue(BananaClusterPlantBlock.HANGING, i == 0 ? BananaClusterPlantBlock.HangingType.STEM : BananaClusterPlantBlock.HangingType.NONE);

                    if (stateAbove.apply(blockState -> blockState.is(FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT)))
                    {
                        banana = banana.setValue(BananaClusterPlantBlock.HANGING, BananaClusterPlantBlock.HangingType.SMALL_CLUSTER);
                    }
                }
                else if (banana.hasProperty(UnderripeBananaClusterPlantBlock.HANGING))
                {
                    var isLargeCluster = stateAbove.apply(blockState -> blockState.is(FOTBlocks.RIPE_BANANA_CLUSTER_PLANT) || blockState.is(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT));
                    banana = banana.setValue(UnderripeBananaClusterPlantBlock.HANGING, i == 0 ? BananaHangingType.STEM : isLargeCluster ? BananaHangingType.CLUSTER : BananaHangingType.SMALL_CLUSTER);
                    isSmallCluster = true;
                }

                context.setBlock(blockPos, banana.setValue(BananaClusterPlantBlock.FACING, direction.getOpposite()).setValue(BananaClusterPlantBlock.WATERLOGGED, isWater.apply(blockPos)));

                if (yBottom < i)
                {
                    yBottom = i;
                }
            }
            context.setBlock(pos.below(yBottom), FOTBlocks.BANANA_BLOSSOM_PLANT.defaultBlockState().setValue(BananaBlossomPlantBlock.FACING, direction.getOpposite()).setValue(BananaBlossomPlantBlock.HANGING, yBottom == 0 ? BananaHangingType.STEM : isSmallCluster ? BananaHangingType.SMALL_CLUSTER : BananaHangingType.CLUSTER).setValue(BananaBlossomPlantBlock.WATERLOGGED, isWater.apply(pos.below(yBottom))));
        }
    }

    private int findMaxYBelow(LevelSimulatedReader level, BlockPos pos)
    {
        var maxY = 0;

        while (level.isStateAtPosition(pos.below(maxY), BlockState::canBeReplaced) && maxY < this.maxBananaCluster)
        {
            ++maxY;
        }
        return maxY;
    }
}