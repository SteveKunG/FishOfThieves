package com.stevekung.fishofthieves.feature.foliageplacers;

import java.util.Arrays;
import java.util.List;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import com.stevekung.fishofthieves.block.CoconutFrondsBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTFoliagePlacerTypes;

import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.material.Fluids;

public class CoconutFrondsPlacer extends FoliagePlacer
{
    public static final Codec<CoconutFrondsPlacer> CODEC = RecordCodecBuilder.create(instance -> frondsPart(instance).apply(instance, CoconutFrondsPlacer::new));
    protected final int height;
    protected final int maxLeavesDistanceFromLocalY;
    protected final List<Pair<Integer, Integer>> reduceLeavesDistance;

    protected static <P extends CoconutFrondsPlacer> Products.P3<Mu<P>, Integer, Integer, List<Pair<Integer, Integer>>> frondsPart(Instance<P> instance)
    {
        return instance.group(Codec.intRange(0, 8).fieldOf("height").forGetter(placer -> placer.height))
                .and(Codec.intRange(0, 8).fieldOf("max_leaves_distance_from_local_y").forGetter(placer -> placer.maxLeavesDistanceFromLocalY))
                .and(Codec.mapPair(Codec.intRange(0, 16).fieldOf("at_tree_height"), Codec.intRange(0, 8).fieldOf("reduce_by")).codec().listOf().optionalFieldOf("reduce_leaves_distance", List.of()).forGetter(placer -> placer.reduceLeavesDistance));
    }

    public CoconutFrondsPlacer(int height, int maxLeavesDistanceFromLocalY, List<Pair<Integer, Integer>> reduceLeavesDistance)
    {
        super(ConstantInt.of(0), ConstantInt.of(0));
        this.height = height;
        this.maxLeavesDistanceFromLocalY = maxLeavesDistanceFromLocalY;
        this.reduceLeavesDistance = reduceLeavesDistance;
    }

    @SafeVarargs
    public CoconutFrondsPlacer(int height, int maxLeavesDistanceFromLocalY, Pair<Integer, Integer>... reduceLeavesDistance)
    {
        this(height, maxLeavesDistanceFromLocalY, Arrays.stream(reduceLeavesDistance).toList());
    }

    @Override
    protected FoliagePlacerType<?> type()
    {
        return FOTFoliagePlacerTypes.COCONUT_FRONDS_PLACER;
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliagePlacer.FoliageSetter blockSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliagePlacer.FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset)
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
                if (TreeFeature.validTreePos(level, pos))
                {
                    var blockState = FOTBlocks.VERTICAL_COCONUT_FRONDS.defaultBlockState();

                    if (blockState.hasProperty(BlockStateProperties.WATERLOGGED))
                    {
                        blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(pos, fluidState -> fluidState.isSourceOfType(Fluids.WATER)));
                    }
                    blockSetter.set(pos, blockState);
                }
            }
            else
            {
                var mutableBlockPos = pos.mutable();
                var maxLeavesDistanceFromLocalY = this.maxLeavesDistanceFromLocalY - localY + 1;

                for (var pair : this.reduceLeavesDistance)
                {
                    if (maxFreeTreeHeight == pair.getFirst())
                    {
                        maxLeavesDistanceFromLocalY -= pair.getSecond();
                    }
                }

                for (var direction : Direction.Plane.HORIZONTAL)
                {
                    var opposite = direction.getOpposite();
                    var blockState = config.foliageProvider.getState(random, pos).setValue(CoconutFrondsBlock.FACING, opposite);

                    if (blockState.hasProperty(BlockStateProperties.WATERLOGGED))
                    {
                        blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(pos, fluidState -> fluidState.isSourceOfType(Fluids.WATER)));
                    }

                    if (maxLeavesDistanceFromLocalY == 1)
                    {
                        var posAroundLog = mutableBlockPos.offset(opposite.getStepX(), localY, opposite.getStepZ());
                        blockSetter.set(posAroundLog, blockState);
                    }
                    else
                    {
                        for (var i = 1; i <= maxLeavesDistanceFromLocalY; i++)
                        {
                            var posAroundLog = mutableBlockPos.offset(opposite.getStepX() * i, localY, opposite.getStepZ() * i);

                            if (level.isStateAtPosition(posAroundLog, BlockBehaviour.BlockStateBase::isAir))
                            {
                                if (i > 1 && i < maxLeavesDistanceFromLocalY)
                                {
                                    blockState = blockState.setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.MIDDLE);
                                }
                                else if (i == maxLeavesDistanceFromLocalY)
                                {
                                    blockState = blockState.setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.TAIL);
                                }
                                blockSetter.set(posAroundLog, blockState);
                            }
                            else
                            {
                                var previousLeavesPos = mutableBlockPos.offset(opposite.getStepX() * (i - 1), localY, opposite.getStepZ() * (i - 1));

                                if (level.isStateAtPosition(previousLeavesPos, blockState1 -> blockState1.is(FOTBlocks.COCONUT_FRONDS) && blockState1.getValue(CoconutFrondsBlock.PART) == CoconutFrondsBlock.Part.MIDDLE))
                                {
                                    blockSetter.set(previousLeavesPos, blockState.setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.TAIL));
                                }
                                // Skip when found non-air while placing leaves and set leaves to tail state
                                break;
                            }
                        }
                    }
                }
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
}