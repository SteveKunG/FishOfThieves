package com.stevekung.fishofthieves.common.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.structure.Structure;

public class TerrainUtils
{
    private static final OverworldBiomeBuilder OVERWORLD_BUILDER = new OverworldBiomeBuilder();

    public static Continentalness getContinentalness(ServerLevel level, BlockPos blockPos)
    {
        var continentalness = Climate.unquantizeCoord(TerrainUtils.getTargetPoint(level, blockPos).continentalness());
        return Continentalness.byName(TerrainUtils.OVERWORLD_BUILDER.getDebugStringForContinentalness(continentalness));
    }

    public static PeakTypes getPeakTypes(ServerLevel level, BlockPos blockPos)
    {
        var weirdness = Climate.unquantizeCoord(TerrainUtils.getTargetPoint(level, blockPos).weirdness());
        var peakTypes = NoiseRouterData.peaksAndValleys(weirdness);
        return PeakTypes.byName(OverworldBiomeBuilder.getDebugStringForPeaksAndValleys(peakTypes));
    }

    public static boolean isInFeature(ServerLevel level, BlockPos blockPos, TagKey<Structure> tagKey)
    {
        return level.structureManager().getStructureWithPieceAt(blockPos, tagKey).isValid();
    }

    public static boolean isInFeature(ServerLevel level, BlockPos blockPos, ResourceKey<Structure> resourceKey)
    {
        return level.structureManager().getStructureWithPieceAt(blockPos, resourceKey).isValid();
    }

    public static Optional<BlockPos> lookForBlock(BlockPos blockPos, int range, Predicate<BlockPos> posFilter)
    {
        return BlockPos.findClosestMatch(blockPos, range, range, posFilter);
    }

    public static boolean lookForBlocksWithSize(BlockPos blockPos, int range, int maxSize, Predicate<BlockPos> posFilter)
    {
        var size = 0;

        for (var blockPos2 : BlockPos.withinManhattan(blockPos, range, range, range))
        {
            if (!posFilter.test(blockPos2))
            {
                continue;
            }
            if (++size >= maxSize)
            {
                return true;
            }
        }
        return false;
    }

    private static Climate.TargetPoint getTargetPoint(ServerLevel level, BlockPos blockPos)
    {
        var chunkX = QuartPos.fromBlock(blockPos.getX());
        var chunkY = QuartPos.fromBlock(blockPos.getY());
        var chunkZ = QuartPos.fromBlock(blockPos.getZ());
        return Objects.requireNonNullElseGet(level.getChunkSource().randomState().sampler(), Climate::empty).sample(chunkX, chunkY, chunkZ);
    }
}