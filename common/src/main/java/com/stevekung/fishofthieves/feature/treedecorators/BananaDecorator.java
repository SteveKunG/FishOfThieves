package com.stevekung.fishofthieves.feature.treedecorators;

import java.util.Collections;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.block.BananaClusterGrowableStemBlock;
import com.stevekung.fishofthieves.registry.FOTTreeDecoratorTypes;

import net.minecraft.core.Direction;
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
                        BananaClusterGrowableStemBlock.growBananaBlossomOrCluster(direction2, context.level(), (blockPos1, blockState, flags) -> context.setBlock(blockPos1, blockState), blockPosx -> context.level().isFluidAtPosition(blockPosx, fluidState -> fluidState.is(Fluids.WATER)), randomSource, blockPos2);
                    }
                }
            }
        });
    }
}