package com.stevekung.fishofthieves.feature.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.block.BananaShootsPlantBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTreeDecoratorTypes;

import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class BananaShootsDecorator extends TreeDecorator
{
    public static final MapCodec<BananaShootsDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(decorator -> decorator.probability)
    ).apply(instance, BananaShootsDecorator::new));
    private final float probability;

    public BananaShootsDecorator(float probability)
    {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> type()
    {
        return FOTTreeDecoratorTypes.BANANA_SHOOTS;
    }

    @Override
    public void place(Context context)
    {
        var randomSource = context.random();
        var blockPos = context.logs().getFirst();

        for (var direction : Direction.Plane.HORIZONTAL)
        {
            if (!(randomSource.nextFloat() >= this.probability))
            {
                var opposite = direction.getOpposite();
                var posAroundLog = blockPos.offset(opposite.getStepX(), 0, opposite.getStepZ());

                if (context.isAir(posAroundLog))
                {
                    context.setBlock(posAroundLog, FOTBlocks.BANANA_SHOOTS_PLANT.defaultBlockState().setValue(BananaShootsPlantBlock.FACING, direction));
                }
            }
        }
    }
}