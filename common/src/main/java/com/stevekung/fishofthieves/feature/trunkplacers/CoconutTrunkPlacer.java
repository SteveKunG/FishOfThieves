package com.stevekung.fishofthieves.feature.trunkplacers;

import java.util.List;
import java.util.function.BiConsumer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTTrunkPlacerTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class CoconutTrunkPlacer extends TrunkPlacer
{
    public static final MapCodec<CoconutTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.intRange(0, 32).fieldOf("base_height").forGetter(trunkPlacer -> trunkPlacer.baseHeight),
                    Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter(trunkPlacer -> trunkPlacer.heightRandA),
                    IntProviders.codec(0, 16).fieldOf("medium_trunk_start").forGetter(trunkPlacer -> trunkPlacer.mediumTrunkStart),
                    IntProviders.codec(0, 32).fieldOf("medium_trunk_height").forGetter(trunkPlacer -> trunkPlacer.mediumTrunkHeight),
                    Codec.BOOL.fieldOf("increase_medium_trunk_by_one").forGetter(trunkPlacer -> trunkPlacer.increaseMediumTrunkByOne),
                    BlockStateProvider.CODEC.fieldOf("small_log").forGetter(trunkPlacer -> trunkPlacer.smallLog),
                    BlockStateProvider.CODEC.fieldOf("medium_log").forGetter(trunkPlacer -> trunkPlacer.mediumLog),
                    BlockStateProvider.CODEC.fieldOf("top_log").forGetter(trunkPlacer -> trunkPlacer.topLog))
            .apply(instance, CoconutTrunkPlacer::new));
    private final IntProvider mediumTrunkStart;
    private final IntProvider mediumTrunkHeight;
    private final boolean increaseMediumTrunkByOne;
    private final Holder<BlockStateProvider> smallLog;
    private final Holder<BlockStateProvider> mediumLog;
    private final Holder<BlockStateProvider> topLog;

    public CoconutTrunkPlacer(int baseHeight, int heightRandA, IntProvider mediumTrunkStart, IntProvider mediumTrunkHeight, boolean increaseMediumTrunkByOne, Holder<BlockStateProvider> smallLog, Holder<BlockStateProvider> mediumLog, Holder<BlockStateProvider> topLog)
    {
        super(baseHeight, heightRandA, 0);
        this.mediumTrunkStart = mediumTrunkStart;
        this.mediumTrunkHeight = mediumTrunkHeight;
        this.increaseMediumTrunkByOne = increaseMediumTrunkByOne;
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
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(WorldGenLevel level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeFeature feature)
    {
        placeBelowTrunkBlock(level, blockSetter, random, pos.below(), feature);
        var trunkStart = this.mediumTrunkStart.sample(random);
        var trunkHeight = this.mediumTrunkHeight.sample(random);

        // If medium trunk start position is same with medium trunk height limit, increase by one
        if (this.increaseMediumTrunkByOne && trunkStart == trunkHeight)
        {
            trunkHeight += 1;
        }

        for (var height = 0; height < freeTreeHeight; height++)
        {
            this.placeLog(level, blockSetter, random, pos.above(height), feature, trunkStart, trunkHeight, height, height == freeTreeHeight - 1);
        }
        return List.of(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight), 0, false));
    }

    private void placeLog(WorldGenLevel level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeFeature feature, int mediumTrunkStart, int mediumTrunkHeight, int height, boolean isTop)
    {
        if (this.validTreePos(level, pos))
        {
            var blockState = feature.trunkProvider().value().getState(level, random, pos);
            var maxMediumTrunkHeight = mediumTrunkStart + mediumTrunkHeight;

            if (isTop)
            {
                blockState = this.topLog.value().getState(level, random, pos);
            }
            else if (height >= mediumTrunkStart && height < maxMediumTrunkHeight)
            {
                blockState = this.mediumLog.value().getState(level, random, pos);
            }
            else if (height >= maxMediumTrunkHeight)
            {
                blockState = this.smallLog.value().getState(level, random, pos);
            }
            blockSetter.accept(pos, blockState);
        }
    }
}