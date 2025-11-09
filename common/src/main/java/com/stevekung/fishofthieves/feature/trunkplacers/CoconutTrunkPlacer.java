package com.stevekung.fishofthieves.feature.trunkplacers;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
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

public class CoconutTrunkPlacer extends TrunkPlacer
{
    public static final MapCodec<CoconutTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.intRange(0, 32).fieldOf("base_height").forGetter(trunkPlacer -> trunkPlacer.baseHeight),
                    Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter(trunkPlacer -> trunkPlacer.heightRandA),
                    Codec.intRange(0, 32).fieldOf("medium_trunk_height").forGetter(trunkPlacer -> trunkPlacer.mediumTrunkHeight),
                    BlockStateProvider.CODEC.fieldOf("small_log").forGetter(trunkPlacer -> trunkPlacer.smallLog),
                    BlockStateProvider.CODEC.fieldOf("medium_log").forGetter(trunkPlacer -> trunkPlacer.mediumLog),
                    BlockStateProvider.CODEC.fieldOf("top_log").forGetter(trunkPlacer -> trunkPlacer.topLog))
            .apply(instance, CoconutTrunkPlacer::new));
    private final int mediumTrunkHeight;
    private final BlockStateProvider smallLog;
    private final BlockStateProvider mediumLog;
    private final BlockStateProvider topLog;

    public CoconutTrunkPlacer(int baseHeight, int heightRandA, int mediumTrunkHeight, BlockStateProvider smallLog, BlockStateProvider mediumLog, BlockStateProvider topLog)
    {
        super(baseHeight, heightRandA, 0);
        this.mediumTrunkHeight = mediumTrunkHeight;
        this.smallLog = smallLog;
        this.mediumLog = mediumLog;
        this.topLog = topLog;
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
        return List.of(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight), 0, false));
    }

    private void placeLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeConfiguration config, Function<BlockState, BlockState> propertySetter, int height, boolean isTop)
    {
        if (this.validTreePos(level, pos))
        {
            var blockState = propertySetter.apply(config.trunkProvider.getState(random, pos));
            var mediumTrunkHeight = random.nextInt(this.mediumTrunkHeight) + 1;

            if (height > 0 && height <= mediumTrunkHeight)
            {
                blockState = propertySetter.apply(this.mediumLog.getState(random, pos));
            }
            else if (height > mediumTrunkHeight)
            {
                var log = isTop ? this.topLog : this.smallLog;
                blockState = propertySetter.apply(log.getState(random, pos));
            }
            blockSetter.accept(pos, blockState);
        }
    }
}