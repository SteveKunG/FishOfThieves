package com.stevekung.fishofthieves.feature.treedecorators;

import java.util.Collections;
import java.util.function.Predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.block.CoconutFruitBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.FOTTreeDecoratorTypes;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class CoconutDecorator extends TreeDecorator
{
    public static final MapCodec<CoconutDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(decorator -> decorator.probability),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("coconut_probability").forGetter(decorator -> decorator.coconutProbability),
                    Codec.intRange(0, 16).fieldOf("y_to_grow_coconut_at").forGetter(decorator -> decorator.yToGrowCoconutAt))
            .apply(instance, CoconutDecorator::new));
    private final float probability;
    private final float coconutProbability;
    private final int yToGrowCoconutAt;

    public CoconutDecorator(float probability, float coconutProbability, int yToGrowCoconutAt)
    {
        this.probability = probability;
        this.coconutProbability = coconutProbability;
        this.yToGrowCoconutAt = yToGrowCoconutAt;
    }

    @Override
    protected TreeDecoratorType<?> type()
    {
        return FOTTreeDecoratorTypes.COCONUT;
    }

    @Override
    public void place(TreeDecorator.Context context)
    {
        var randomSource = context.random();

        if (randomSource.nextFloat() < this.probability)
        {
            var list = context.logs();
            var yAtStart = list.getFirst().getY();
            var maxY = Collections.max(list.stream().map(blockPos -> blockPos.getY() - yAtStart).toList());
            var yToGrowCoconut = maxY - this.yToGrowCoconutAt;

            list.stream().filter(blockPos -> blockPos.getY() - yAtStart == yToGrowCoconut).findFirst().ifPresent(blockPos ->
            {
                Predicate<BlockState> canGrow = blockState -> blockState.is(FOTTags.Blocks.COCONUT_GROWABLE_LOG_SPAWNABLE);

                if (context.level().isStateAtPosition(blockPos.above(), canGrow) && context.level().isStateAtPosition(blockPos.below(), canGrow))
                {
                    context.setBlock(blockPos, FOTBlocks.COCONUT_FRUIT_GROWABLE_LOG.defaultBlockState());

                    for (var direction : Direction.Plane.HORIZONTAL)
                    {
                        if (randomSource.nextFloat() <= this.coconutProbability)
                        {
                            var opposite = direction.getOpposite();
                            var posAroundLog = blockPos.offset(opposite.getStepX(), 0, opposite.getStepZ());

                            if (context.isAir(posAroundLog))
                            {
                                context.setBlock(posAroundLog, FOTBlocks.COCONUT_FRUIT.defaultBlockState().setValue(CoconutFruitBlock.AGE, randomSource.nextInt(3)).setValue(HorizontalDirectionalBlock.FACING, direction));
                            }
                        }
                    }
                }
            });
        }
    }
}