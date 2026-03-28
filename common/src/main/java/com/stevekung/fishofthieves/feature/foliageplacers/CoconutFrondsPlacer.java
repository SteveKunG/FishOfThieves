package com.stevekung.fishofthieves.feature.foliageplacers;

import java.util.Arrays;
import java.util.List;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import com.stevekung.fishofthieves.registry.FOTFoliagePlacerTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluids;

public class CoconutFrondsPlacer extends FoliagePlacer
{
    public static final MapCodec<CoconutFrondsPlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> frondsPart(instance).apply(instance, CoconutFrondsPlacer::new));
    final int height;
    final int maxLeavesDistanceFromLocalY;
    final BlockStateProvider topLeavesState;
    final BlockStateProvider middleLeavesState;
    final BlockStateProvider tailLeavesState;
    final List<Pair<Integer, Integer>> reduceLeavesDistance;

    static <P extends CoconutFrondsPlacer> Products.P6<Mu<P>, Integer, Integer, BlockStateProvider, BlockStateProvider, BlockStateProvider, List<Pair<Integer, Integer>>> frondsPart(Instance<P> instance)
    {
        return instance.group(Codec.intRange(0, 8).fieldOf("height").forGetter(placer -> placer.height))
                .and(Codec.intRange(0, 8).fieldOf("max_leaves_distance_from_local_y").forGetter(placer -> placer.maxLeavesDistanceFromLocalY))
                .and(BlockStateProvider.CODEC.fieldOf("top_leaves_state").forGetter(placer -> placer.topLeavesState))
                .and(BlockStateProvider.CODEC.fieldOf("middle_leaves_state").forGetter(placer -> placer.middleLeavesState))
                .and(BlockStateProvider.CODEC.fieldOf("tail_leaves_state").forGetter(placer -> placer.tailLeavesState))
                .and(Codec.mapPair(Codec.intRange(0, 16).fieldOf("at_tree_height"), Codec.intRange(0, 8).fieldOf("reduce_by")).codec().listOf().optionalFieldOf("reduce_leaves_distance", List.of()).forGetter(placer -> placer.reduceLeavesDistance));
    }

    public CoconutFrondsPlacer(int height, int maxLeavesDistanceFromLocalY, BlockStateProvider topLeavesState, BlockStateProvider middleLeavesState, BlockStateProvider tailLeavesState, List<Pair<Integer, Integer>> reduceLeavesDistance)
    {
        super(ConstantInt.of(0), ConstantInt.of(0));
        this.height = height;
        this.maxLeavesDistanceFromLocalY = maxLeavesDistanceFromLocalY;
        this.topLeavesState = topLeavesState;
        this.middleLeavesState = middleLeavesState;
        this.tailLeavesState = tailLeavesState;
        this.reduceLeavesDistance = reduceLeavesDistance;
    }

    @SafeVarargs
    public CoconutFrondsPlacer(int height, int maxLeavesDistanceFromLocalY, BlockStateProvider topLeavesState, BlockStateProvider middleLeavesState, BlockStateProvider tailLeavesState, Pair<Integer, Integer>... reduceLeavesDistance)
    {
        this(height, maxLeavesDistanceFromLocalY, topLeavesState, middleLeavesState, tailLeavesState, Arrays.stream(reduceLeavesDistance).toList());
    }

    @Override
    protected FoliagePlacerType<?> type()
    {
        return FOTFoliagePlacerTypes.COCONUT_FRONDS_PLACER;
    }

    @Override
    protected void createFoliage(WorldGenLevel level, FoliagePlacer.FoliageSetter blockSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliagePlacer.FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset)
    {
        var pos = attachment.pos();

        if (!TreeFeature.validTreePos(level, pos))
        {
            return;
        }

        for (var localY = offset; localY >= offset - foliageHeight; localY--)
        {
            if (localY == 0)
            {
                this.placeTopLeaves(level, pos, random, blockSetter);
            }
            else
            {
                var maxLeavesDistanceFromLocalY = this.maxLeavesDistanceFromLocalY - localY + 1;

                for (var pair : this.reduceLeavesDistance)
                {
                    if (maxFreeTreeHeight == pair.getFirst())
                    {
                        maxLeavesDistanceFromLocalY -= pair.getSecond();
                    }
                }

                this.placeLeavesHorizontalDirections(level, pos, random, config, blockSetter, maxLeavesDistanceFromLocalY, localY);
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config)
    {
        return this.height;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large)
    {
        return localX == range && localZ == range && (random.nextInt(2) == 0 || localY == 0);
    }

    private void placeTopLeaves(WorldGenLevel level, BlockPos blockPos, RandomSource random, FoliagePlacer.FoliageSetter blockSetter)
    {
        if (TreeFeature.validTreePos(level, blockPos))
        {
            var blockState = this.topLeavesState.getState(level, random, blockPos);

            if (blockState.hasProperty(BlockStateProperties.WATERLOGGED))
            {
                blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(blockPos, fluidState -> fluidState.isSourceOfType(Fluids.WATER)));
            }
            blockSetter.set(blockPos, blockState);
        }
    }

    private void placeLeavesHorizontalDirections(WorldGenLevel level, BlockPos blockPos, RandomSource random, TreeConfiguration config, FoliagePlacer.FoliageSetter blockSetter, int maxLeavesDistanceFromLocalY, int localY)
    {
        var mutableBlockPos = blockPos.mutable();

        for (var direction : Direction.Plane.HORIZONTAL)
        {
            var opposite = direction.getOpposite();
            var leavesBlockState = config.foliageProvider.getState(level, random, blockPos);

            if (leavesBlockState.hasProperty(BlockStateProperties.HORIZONTAL_FACING))
            {
                leavesBlockState = leavesBlockState.setValue(BlockStateProperties.HORIZONTAL_FACING, opposite);
            }
            if (leavesBlockState.hasProperty(BlockStateProperties.WATERLOGGED))
            {
                leavesBlockState = leavesBlockState.setValue(BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(blockPos, fluidState -> fluidState.isSourceOfType(Fluids.WATER)));
            }

            if (maxLeavesDistanceFromLocalY == 1)
            {
                var posAroundLog = mutableBlockPos.offset(opposite.getStepX(), localY, opposite.getStepZ());

                if (this.isAir(level, posAroundLog))
                {
                    blockSetter.set(posAroundLog, leavesBlockState);
                }
            }
            else
            {
                var tailLeavesState = this.tailLeavesState.getState(level, random, blockPos);

                if (tailLeavesState.hasProperty(BlockStateProperties.HORIZONTAL_FACING))
                {
                    tailLeavesState = tailLeavesState.setValue(BlockStateProperties.HORIZONTAL_FACING, opposite);
                }

                var middleLeavesState = this.middleLeavesState.getState(level, random, blockPos);

                if (middleLeavesState.hasProperty(BlockStateProperties.HORIZONTAL_FACING))
                {
                    middleLeavesState = middleLeavesState.setValue(BlockStateProperties.HORIZONTAL_FACING, opposite);
                }

                for (var i = 1; i <= maxLeavesDistanceFromLocalY; i++)
                {
                    var posAroundLog = mutableBlockPos.offset(opposite.getStepX() * i, localY, opposite.getStepZ() * i);

                    if (this.isAir(level, posAroundLog))
                    {
                        if (i > 1 && i < maxLeavesDistanceFromLocalY)
                        {
                            blockSetter.set(posAroundLog, middleLeavesState);
                        }
                        else if (i == maxLeavesDistanceFromLocalY)
                        {
                            blockSetter.set(posAroundLog, tailLeavesState);
                        }
                        else
                        {
                            blockSetter.set(posAroundLog, leavesBlockState);
                        }
                    }
                    else
                    {
                        var previousLeavesPos = mutableBlockPos.offset(opposite.getStepX() * (i - 1), localY, opposite.getStepZ() * (i - 1));

                        if (level.isStateAtPosition(previousLeavesPos, middleLeavesState::equals))
                        {
                            blockSetter.set(previousLeavesPos, tailLeavesState);
                        }
                        // Skip when found non-air while placing leaves and set leaves to tail state
                        break;
                    }
                }
            }
        }
    }

    private boolean isAir(LevelSimulatedReader level, BlockPos blockPos)
    {
        return level.isStateAtPosition(blockPos, BlockBehaviour.BlockStateBase::isAir);
    }
}