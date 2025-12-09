package com.stevekung.fishofthieves.feature.trunkplacers;

import java.util.List;
import java.util.function.BiConsumer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTTrunkPlacerTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class BananaTrunkPlacer extends TrunkPlacer
{
    public static final Codec<BananaTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.intRange(0, 16).fieldOf("base_height").forGetter(trunkPlacer -> trunkPlacer.baseHeight),
                    Codec.intRange(0, 12).fieldOf("height_rand_a").forGetter(trunkPlacer -> trunkPlacer.heightRandA),
                    BlockStateProvider.CODEC.fieldOf("top_log").forGetter(trunkPlacer -> trunkPlacer.topLog))
            .apply(instance, BananaTrunkPlacer::new));
    private final BlockStateProvider topLog;

    public BananaTrunkPlacer(int baseHeight, int heightRandA, BlockStateProvider topLog)
    {
        super(baseHeight, heightRandA, 0);
        this.topLog = topLog;
    }

    @Override
    protected TrunkPlacerType<?> type()
    {
        return FOTTrunkPlacerTypes.BANANA_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config)
    {
        setDirtAt(level, blockSetter, random, pos.below(), config);

        for (var i = 0; i < freeTreeHeight; i++)
        {
            this.placeLog(level, blockSetter, random, pos.above(i), config, i == freeTreeHeight - 1);
        }
        return List.of(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight), 0, false));
    }

    private void placeLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeConfiguration config, boolean isTop)
    {
        if (this.validTreePos(level, pos))
        {
            var log = isTop ? this.topLog : config.trunkProvider;
            var blockState = log.getState(random, pos);
            blockSetter.accept(pos, blockState);
        }
    }
}