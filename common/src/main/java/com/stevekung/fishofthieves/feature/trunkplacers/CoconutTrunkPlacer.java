package com.stevekung.fishofthieves.feature.trunkplacers;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.block.CoconutGrowableLogBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTrunkPlacerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class CoconutTrunkPlacer extends TrunkPlacer
{
    public static final Codec<CoconutTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.intRange(0, 32).fieldOf("base_height").forGetter(trunkPlacer -> trunkPlacer.baseHeight),
                    Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter(trunkPlacer -> trunkPlacer.heightRandA),
                    Codec.intRange(0, 32).fieldOf("medium_trunk_height").forGetter(trunkPlacer -> trunkPlacer.mediumTrunkHeight))
            .apply(instance, CoconutTrunkPlacer::new));
    private final int mediumTrunkHeight;

    public CoconutTrunkPlacer(int baseHeight, int heightRandA, int mediumTrunkHeight)
    {
        super(baseHeight, heightRandA, 0);
        this.mediumTrunkHeight = mediumTrunkHeight;
    }

    @Override
    protected TrunkPlacerType<?> type()
    {
        return FOTTrunkPlacerTypes.COCONUT_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config)
    {
        setDirtAt(level, blockSetter, random, pos.below(), config);

        for (var i = 0; i < freeTreeHeight; i++)
        {
            this.placeLog(level, blockSetter, random, pos.above(i), config, Function.identity(), i, i == freeTreeHeight - 1);
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight), 0, false));
    }

    private void placeLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeConfiguration config, Function<BlockState, BlockState> propertySetter, int height, boolean isTop)
    {
        if (this.validTreePos(level, pos))
        {
            var blockState = propertySetter.apply(config.trunkProvider.getState(random, pos));
            var mediumTrunkHeight = random.nextInt(this.mediumTrunkHeight) + 1;

            if (height > 0 && height <= mediumTrunkHeight)
            {
                blockState = propertySetter.apply(FOTBlocks.MEDIUM_COCONUT_LOG.defaultBlockState());
            }
            else if (height > mediumTrunkHeight)
            {
                var smallLog = FOTBlocks.SMALL_COCONUT_LOG.defaultBlockState();

                if (isTop)
                {
                    smallLog = smallLog.setValue(CoconutGrowableLogBlock.TOP, true);
                }

                blockState = propertySetter.apply(smallLog);
            }
            blockSetter.accept(pos, blockState);
        }
    }
}