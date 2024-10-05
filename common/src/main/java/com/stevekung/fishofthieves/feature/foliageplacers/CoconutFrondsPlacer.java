package com.stevekung.fishofthieves.feature.foliageplacers;

import com.mojang.datafixers.Products;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.material.Fluids;

public class CoconutFrondsPlacer extends FoliagePlacer
{
    public static final Codec<CoconutFrondsPlacer> CODEC = RecordCodecBuilder.create(instance -> frondsPart(instance).apply(instance, CoconutFrondsPlacer::new));
    protected final int height;
    protected final int maxLeavesLengthFromLocalY;

    protected static <P extends CoconutFrondsPlacer> Products.P2<Mu<P>, Integer, Integer> frondsPart(Instance<P> instance)
    {
        return instance.group(Codec.intRange(0, 8).fieldOf("height").forGetter(blobFoliagePlacer -> blobFoliagePlacer.height))
                .and(Codec.intRange(0, 8).fieldOf("max_leaves_length_from_local_y").forGetter(blobFoliagePlacer -> blobFoliagePlacer.maxLeavesLengthFromLocalY));
    }

    public CoconutFrondsPlacer(int height, int maxLeavesLengthFromLocalY)
    {
        super(ConstantInt.of(0), ConstantInt.of(0));
        this.height = height;
        this.maxLeavesLengthFromLocalY = maxLeavesLengthFromLocalY;
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

        for (var localY = offset; localY >= offset - foliageHeight; localY--)
        {
            if (localY == 0)
            {
                var blockState = FOTBlocks.VERTICAL_COCONUT_FRONDS.defaultBlockState();

                if (blockState.hasProperty(BlockStateProperties.WATERLOGGED))
                {
                    blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(pos, fluidState -> fluidState.isSourceOfType(Fluids.WATER)));
                }
                blockSetter.set(pos, blockState);
            }
            else
            {
                var mutableBlockPos = pos.mutable();
                var maxLeavesFromLocalYLength = this.maxLeavesLengthFromLocalY - localY;

                for (var direction : Direction.Plane.HORIZONTAL)
                {
                    var direction2 = direction.getOpposite();
                    var blockPos2 = mutableBlockPos.offset(direction2.getStepX(), localY, direction2.getStepZ());
                    var isPositiveDir = direction2.getAxisDirection() == Direction.AxisDirection.POSITIVE;
                    var blockState = config.foliageProvider.getState(random, pos).setValue(CoconutFrondsBlock.FACING, direction2);

                    if (blockState.hasProperty(BlockStateProperties.WATERLOGGED))
                    {
                        blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(pos, fluidState -> fluidState.isSourceOfType(Fluids.WATER)));
                    }

                    blockSetter.set(blockPos2, blockState);

                    for (var leavesLength = 0; leavesLength < maxLeavesFromLocalYLength; leavesLength++)
                    {
                        if (leavesLength == maxLeavesFromLocalYLength - 1)
                        {
                            blockState = blockState.setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.TAIL);
                        }
                        else
                        {
                            blockState = blockState.setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.MIDDLE);
                        }

                        switch (direction.getAxis())
                        {
                            case X ->
                            {
                                var x = isPositiveDir ? direction2.getStepX() + leavesLength : direction2.getStepX() - leavesLength;
                                blockSetter.set(blockPos2.offset(x, 0, 0), blockState);
                            }
                            case Z ->
                            {
                                var z = isPositiveDir ? direction2.getStepZ() + leavesLength : direction2.getStepZ() - leavesLength;
                                blockSetter.set(blockPos2.offset(0, 0, z), blockState);
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