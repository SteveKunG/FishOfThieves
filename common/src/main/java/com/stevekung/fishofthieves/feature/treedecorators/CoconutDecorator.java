package com.stevekung.fishofthieves.feature.treedecorators;

import java.util.Collections;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.block.CoconutFruitBlock;
import com.stevekung.fishofthieves.block.CoconutGrowableLogBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTreeDecoratorTypes;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class CoconutDecorator extends TreeDecorator
{
    public static final Codec<CoconutDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(attachedToLeavesDecorator -> attachedToLeavesDecorator.probability),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("coconut_probability").forGetter(attachedToLeavesDecorator -> attachedToLeavesDecorator.coconutProbability),
                    Codec.intRange(0, 16).fieldOf("y_to_grow_coconut_at").forGetter(attachedToLeavesDecorator -> attachedToLeavesDecorator.yToGrowCoconutAt))
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

        if (!(randomSource.nextFloat() >= this.probability))
        {
            var list = context.logs();
            var yAtStart = list.get(0).getY();
            var maxY = Collections.max(list.stream().map(blockPos -> blockPos.getY() - yAtStart).toList());
            var yToGrowCoconutAt = maxY - this.yToGrowCoconutAt;

            list.stream().filter(blockPos -> blockPos.getY() - yAtStart == yToGrowCoconutAt).forEach(blockPos ->
            {
                context.setBlock(blockPos, FOTBlocks.SMALL_COCONUT_LOG.defaultBlockState().setValue(CoconutGrowableLogBlock.GROW, true));

                for (var direction : Direction.Plane.HORIZONTAL)
                {
                    if (randomSource.nextFloat() <= this.coconutProbability)
                    {
                        var direction2 = direction.getOpposite();
                        var blockPos2 = blockPos.offset(direction2.getStepX(), 0, direction2.getStepZ());

                        if (context.isAir(blockPos2))
                        {
                            context.setBlock(blockPos2, FOTBlocks.COCONUT_FRUIT.defaultBlockState().setValue(CoconutFruitBlock.AGE, randomSource.nextInt(3)).setValue(CoconutFruitBlock.FACING, direction));
                        }
                    }
                }
            });
        }
    }
}