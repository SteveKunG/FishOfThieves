package com.stevekung.fishofthieves.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;

public class TerrainUtils
{
    public static Continentalness getContinentalness(ServerLevel level, BlockPos blockPos)
    {
        var chunkX = QuartPos.fromBlock(blockPos.getX());
        var chunkY = QuartPos.fromBlock(blockPos.getY());
        var chunkZ = QuartPos.fromBlock(blockPos.getZ());
        var targetPoint = level.getChunkSource().getGenerator().climateSampler().sample(chunkX, chunkY, chunkZ);
        var continentalness = Climate.unquantizeCoord(targetPoint.continentalness());
        var overworldBiomeBuilder = new OverworldBiomeBuilder();
        return Continentalness.byName(overworldBiomeBuilder.getDebugStringForContinentalness(continentalness));
    }
}