package com.stevekung.fishofthieves.feature.treedecorators;

import java.util.List;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.feature.stateproviders.DirectionalRandomizedIntBooleanStateProvider;
import com.stevekung.fishofthieves.registry.FOTTreeDecoratorTypes;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class DirectionalAttachedToLeavesDecorator extends TreeDecorator
{
    public static final Codec<DirectionalAttachedToLeavesDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(decorator -> decorator.probability),
                    Codec.intRange(0, 16).fieldOf("exclusion_radius_xz").forGetter(decorator -> decorator.exclusionRadiusXZ),
                    Codec.intRange(0, 16).fieldOf("exclusion_radius_y").forGetter(decorator -> decorator.exclusionRadiusY),
                    DirectionalRandomizedIntBooleanStateProvider.CODEC.fieldOf("block_provider").forGetter(decorator -> decorator.blockProvider),
                    Codec.intRange(1, 16).fieldOf("required_empty_blocks").forGetter(decorator -> decorator.requiredEmptyBlocks),
                    ExtraCodecs.nonEmptyList(Direction.CODEC.listOf()).fieldOf("directions").forGetter(decorator -> decorator.directions),
                    Codec.BOOL.fieldOf("opposite").forGetter(decorator -> decorator.opposite))
            .apply(instance, DirectionalAttachedToLeavesDecorator::new));
    protected final float probability;
    protected final int exclusionRadiusXZ;
    protected final int exclusionRadiusY;
    protected final DirectionalRandomizedIntBooleanStateProvider blockProvider;
    protected final int requiredEmptyBlocks;
    protected final List<Direction> directions;
    protected final boolean opposite;

    public DirectionalAttachedToLeavesDecorator(float probability, int exclusionRadiusXZ, int exclusionRadiusY, DirectionalRandomizedIntBooleanStateProvider blockProvider, int requiredEmptyBlocks, List<Direction> directions, boolean opposite)
    {
        this.probability = probability;
        this.exclusionRadiusXZ = exclusionRadiusXZ;
        this.exclusionRadiusY = exclusionRadiusY;
        this.blockProvider = blockProvider;
        this.requiredEmptyBlocks = requiredEmptyBlocks;
        this.directions = directions;
        this.opposite = opposite;
    }

    @Override
    public void place(TreeDecorator.Context context)
    {
        var set = Sets.<BlockPos>newHashSet();
        var randomSource = context.random();

        for (var blockPos : Util.shuffledCopy(context.leaves(), randomSource))
        {
            var direction = Util.getRandom(this.directions, randomSource);
            var blockPos2 = blockPos.relative(direction);

            if (!set.contains(blockPos2) && randomSource.nextFloat() < this.probability && this.hasRequiredEmptyBlocks(context, blockPos, direction))
            {
                var blockPos3 = blockPos2.offset(-this.exclusionRadiusXZ, -this.exclusionRadiusY, -this.exclusionRadiusXZ);
                var blockPos4 = blockPos2.offset(this.exclusionRadiusXZ, this.exclusionRadiusY, this.exclusionRadiusXZ);

                for (var blockPos5 : BlockPos.betweenClosed(blockPos3, blockPos4))
                {
                    set.add(blockPos5.immutable());
                }

                context.setBlock(blockPos2, this.blockProvider.getState(randomSource, blockPos2, this.opposite ? direction.getOpposite() : direction));
            }
        }
    }

    private boolean hasRequiredEmptyBlocks(TreeDecorator.Context context, BlockPos pos, Direction direction)
    {
        for (var i = 1; i <= this.requiredEmptyBlocks; i++)
        {
            var blockPos = pos.relative(direction, i);

            if (!context.isAir(blockPos))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    protected TreeDecoratorType<?> type()
    {
        return FOTTreeDecoratorTypes.DIRECTIONAL_ATTACHED_TO_LEAVES;
    }
}