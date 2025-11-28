package com.stevekung.fishofthieves.shoal;

import java.util.function.Predicate;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.biome.Biomes;

public enum ShoalChance
{
    RIVER_OR_BEACH(context -> context.biome().is(BiomeTags.IS_RIVER) || context.biome().is(BiomeTags.IS_BEACH), FishOfThieves.CONFIG.shoal.weight.riverOrBeach),
    COAST(context -> context.continentalness() == Continentalness.COAST, FishOfThieves.CONFIG.shoal.weight.coast),
    OCEAN(context -> context.biome().is(BiomeTags.IS_OCEAN), FishOfThieves.CONFIG.shoal.weight.ocean),
    DEEP_OCEAN(context -> context.biome().is(BiomeTags.IS_DEEP_OCEAN), FishOfThieves.CONFIG.shoal.weight.deepOcean),
    SWAMP(context -> context.biome().is(Biomes.SWAMP) || context.biome().is(Biomes.MANGROVE_SWAMP), FishOfThieves.CONFIG.shoal.weight.swamp);

    private static final ShoalChance[] VALUES = values();
    private final Predicate<ShoalSpawnContext> context;
    private final int poolWeight;

    ShoalChance(Predicate<ShoalSpawnContext> context, int poolWeight)
    {
        this.context = context;
        this.poolWeight = poolWeight;
    }

    private boolean canSpawnAt(ServerLevel level, BlockPos blockPos, RandomSource randomSource)
    {
        var biome = level.getBiome(blockPos);
        var continentalness = TerrainUtils.getContinentalness(level, blockPos);
        var context = new ShoalSpawnContext(biome, continentalness);

        if (this.context.test(context))
        {
            var poolChance = this.poolWeight / 100.0f;
            return randomSource.nextFloat() >= poolChance;
        }
        return false;
    }

    public static boolean canSpawnAt(ServerLevel level, BlockPos blockPos)
    {
        return Util.getRandom(ShoalChance.VALUES, level.getRandom()).canSpawnAt(level, blockPos, level.getRandom());
    }
}