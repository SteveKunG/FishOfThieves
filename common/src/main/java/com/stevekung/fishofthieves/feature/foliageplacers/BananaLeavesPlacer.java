package com.stevekung.fishofthieves.feature.foliageplacers;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import com.stevekung.fishofthieves.block.BananaLeavesBlock;
import com.stevekung.fishofthieves.registry.FOTFoliagePlacerTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluids;

public class BananaLeavesPlacer extends FoliagePlacer
{
    public static final Codec<BananaLeavesPlacer> CODEC = RecordCodecBuilder.create(instance -> frondsPart(instance).apply(instance, BananaLeavesPlacer::new));
    final float oneLeavesChance;
    final BlockStateProvider topLeavesState;
    final BlockStateProvider tailLeavesState;

    static <P extends BananaLeavesPlacer> Products.P3<Mu<P>, Float, BlockStateProvider, BlockStateProvider> frondsPart(Instance<P> instance)
    {
        return instance.group(Codec.floatRange(0.0f, 1.0f).fieldOf("one_leaves_chance").forGetter(placer -> placer.oneLeavesChance))
                .and(BlockStateProvider.CODEC.fieldOf("top_leaves_state").forGetter(placer -> placer.topLeavesState))
                .and(BlockStateProvider.CODEC.fieldOf("tail_leaves_state").forGetter(placer -> placer.tailLeavesState));
    }

    public BananaLeavesPlacer(float oneLeavesChance, BlockStateProvider topLeavesState, BlockStateProvider tailLeavesState)
    {
        super(ConstantInt.of(0), ConstantInt.of(0));
        this.oneLeavesChance = oneLeavesChance;
        this.topLeavesState = topLeavesState;
        this.tailLeavesState = tailLeavesState;
    }

    @Override
    protected FoliagePlacerType<?> type()
    {
        return FOTFoliagePlacerTypes.BANANA_LEAVES_PLACER;
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter blockSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset)
    {
        var pos = attachment.pos();

        if (!TreeFeature.validTreePos(level, pos))
        {
            return;
        }

        for (var localY = offset; localY >= offset - 1; localY--)
        {
            if (localY == 0)
            {
                this.placeTopLeaves(level, pos, random, blockSetter);
            }
            else
            {
                var mutableBlockPos = pos.mutable();

                for (var direction : Direction.Plane.HORIZONTAL)
                {
                    var opposite = direction.getOpposite();
                    var posAroundLog = mutableBlockPos.offset(opposite.getStepX(), localY, opposite.getStepZ());

                    if (this.isAir(level, posAroundLog) && this.isAir(level, posAroundLog.relative(opposite)))
                    {
                        var singleLeaves = random.nextFloat() < this.oneLeavesChance;
                        blockSetter.set(posAroundLog, this.applyAdditionalState(level, posAroundLog, random, config.foliageProvider.getState(random, pos), opposite, singleLeaves));
                        blockSetter.set(posAroundLog.relative(opposite), this.applyAdditionalState(level, posAroundLog, random, this.tailLeavesState.getState(random, pos), opposite, singleLeaves));
                    }
                }
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config)
    {
        return 1;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large)
    {
        return localX == range && localZ == range && (random.nextInt(2) == 0 || localY == 0);
    }

    private boolean isAir(LevelSimulatedReader level, BlockPos blockPos)
    {
        return level.isStateAtPosition(blockPos, BlockBehaviour.BlockStateBase::isAir);
    }

    private void placeTopLeaves(LevelSimulatedReader level, BlockPos blockPos, RandomSource random, FoliageSetter blockSetter)
    {
        if (TreeFeature.validTreePos(level, blockPos))
        {
            var blockState = this.topLeavesState.getState(random, blockPos);

            if (blockState.hasProperty(BlockStateProperties.WATERLOGGED))
            {
                blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(blockPos, fluidState -> fluidState.isSourceOfType(Fluids.WATER)));
            }

            blockSetter.set(blockPos, blockState);
        }
    }

    private BlockState applyAdditionalState(LevelSimulatedReader level, BlockPos blockPos, RandomSource random, BlockState blockState, Direction opposite, boolean singleLeaves)
    {
        if (blockState.hasProperty(BlockStateProperties.WATERLOGGED))
        {
            blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(blockPos, fluidState -> fluidState.isSourceOfType(Fluids.WATER)));
        }
        if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_FACING))
        {
            blockState = blockState.setValue(BlockStateProperties.HORIZONTAL_FACING, opposite);
        }
        if (blockState.hasProperty(BananaLeavesBlock.COUNT))
        {
            blockState = blockState.setValue(BananaLeavesBlock.COUNT, singleLeaves ? 1 : 2);
        }
        return blockState;
    }
}