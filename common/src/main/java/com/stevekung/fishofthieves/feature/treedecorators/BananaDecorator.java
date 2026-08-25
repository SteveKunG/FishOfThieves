package com.stevekung.fishofthieves.feature.treedecorators;

import java.util.Collections;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.block.BananaClusterGrowableStemBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTreeDecoratorTypes;

import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.material.Fluids;

public class BananaDecorator extends TreeDecorator
{
    public static final Codec<BananaDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
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
        var level = context.level();
        var randomSource = context.random();
        var list = context.logs();
        var yAtStart = list.get(0).getY();
        var maxY = Collections.max(list.stream().map(blockPos -> blockPos.getY() - yAtStart).toList());
        var yToGrowAt = maxY - 1;

        list.stream().filter(blockPos -> blockPos.getY() - yAtStart == yToGrowAt).forEach(blockPos ->
        {
            for (var direction : Direction.Plane.HORIZONTAL)
            {
                if (randomSource.nextFloat() < this.probability)
                {
                    var opposite = direction.getOpposite();
                    var posAroundLog = blockPos.offset(opposite.getStepX(), 0, opposite.getStepZ());

                    if (context.isAir(posAroundLog) && level.isStateAtPosition(posAroundLog.above(), blockState -> blockState.is(FOTBlocks.BANANA_LEAVES)))
                    {
                        BananaClusterGrowableStemBlock.growBananaBlossomOrCluster(opposite, level, (blockPos1, blockState, flags) -> context.setBlock(blockPos1, blockState), blockPosx -> level.isFluidAtPosition(blockPosx, fluidState -> fluidState.is(Fluids.WATER)), randomSource, posAroundLog);
                    }
                }
            }
        });
    }
}