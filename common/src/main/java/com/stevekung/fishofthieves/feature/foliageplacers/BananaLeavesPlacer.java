package com.stevekung.fishofthieves.feature.foliageplacers;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import com.stevekung.fishofthieves.block.BananaLeavesBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTFoliagePlacerTypes;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.material.Fluids;

public class BananaLeavesPlacer extends FoliagePlacer
{
    public static final Codec<BananaLeavesPlacer> CODEC = RecordCodecBuilder.create(instance -> frondsPart(instance).apply(instance, BananaLeavesPlacer::new));
    protected final float oneLeavesChance;

    protected static <P extends BananaLeavesPlacer> Products.P1<Mu<P>, Float> frondsPart(Instance<P> instance)
    {
        return instance.group(Codec.floatRange(0.0f, 1.0f).fieldOf("one_leaves_chance").forGetter(placer -> placer.oneLeavesChance));
    }

    public BananaLeavesPlacer(float oneLeavesChance)
    {
        super(ConstantInt.of(0), ConstantInt.of(0));
        this.oneLeavesChance = oneLeavesChance;
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

        if (TreeFeature.validTreePos(level, pos))
        {
            for (var localY = offset; localY >= offset - 1; localY--)
            {
                if (localY == 0)
                {
                    var blockState = FOTBlocks.VERTICAL_BANANA_LEAVES.defaultBlockState();

                    if (blockState.hasProperty(BlockStateProperties.WATERLOGGED))
                    {
                        blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(pos, fluidState -> fluidState.isSourceOfType(Fluids.WATER)));
                    }
                    blockSetter.set(pos, blockState);
                }
                else
                {
                    var mutableBlockPos = pos.mutable();

                    for (var direction : Direction.Plane.HORIZONTAL)
                    {
                        var direction2 = direction.getOpposite();
                        var blockPos2 = mutableBlockPos.offset(direction2.getStepX(), localY, direction2.getStepZ());

                        var blockState = config.foliageProvider.getState(random, pos).setValue(BananaLeavesBlock.FACING, direction2).setValue(BananaLeavesBlock.COUNT, random.nextFloat() < this.oneLeavesChance ? 1 : 2).setValue(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER);

                        if (blockState.hasProperty(BlockStateProperties.WATERLOGGED))
                        {
                            blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(pos, fluidState -> fluidState.isSourceOfType(Fluids.WATER)));
                        }

                        if (TreeFeature.validTreePos(level, blockPos2))
                        {
                            blockSetter.set(blockPos2, blockState);
                            blockSetter.set(blockPos2.relative(direction2, 1), blockState.setValue(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL));
                        }
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
}