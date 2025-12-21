package com.stevekung.fishofthieves.shoal;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;

public final class ShoalSpawner
{
    private ShoalSpawner() {}

    @Nullable
    public static BlockPos spawn(ServerLevel level, int x, int z)
    {
        return spawnShoalAtSuitablePos(level, level.getBlockRandomPos(x, level.getSeaLevel(), z, 0));
    }

    public static Optional<BlockPos> findFarthest(Predicate<Holder<PoiType>> typePredicate, BlockPos pos, int minimumDistance, int maximumDistance, PoiManager poiManager)
    {
        return getInRange(typePredicate, pos, minimumDistance, maximumDistance, poiManager).map(PoiRecord::getPos).min(Comparator.comparingDouble(blockPos2 -> blockPos2.distSqr(pos)));
    }

    private static Stream<PoiRecord> getInRange(Predicate<Holder<PoiType>> typePredicate, BlockPos pos, int minimumDistance, int maximumDistance, PoiManager poiManager)
    {
        var minSum = minimumDistance * minimumDistance;
        var maxSum = maximumDistance * maximumDistance;
        return getInSquare(typePredicate, pos, minimumDistance, maximumDistance, poiManager).filter(poiRecord ->
        {
            var blockPos = poiRecord.getPos();
            FishOfThieves.LOGGER.debug("minSum {} <= {}: {}", blockPos.distSqr(pos), minSum, blockPos.distSqr(pos) >= minSum);
            FishOfThieves.LOGGER.debug("maxSum {} <= {}: {}", blockPos.distSqr(pos), maxSum, blockPos.distSqr(pos) <= maxSum);
            return blockPos.distSqr(pos) >= minSum && blockPos.distSqr(pos) <= maxSum;
        });
    }

    private static Stream<PoiRecord> getInSquare(Predicate<Holder<PoiType>> typePredicate, BlockPos pos, int minimumDistance, int maximumDistance, PoiManager poiManager)
    {
        var chunkRadius = Math.floorDiv(maximumDistance, 16) + 1;
        return ChunkPos.rangeClosed(new ChunkPos(pos), chunkRadius).flatMap(chunkPos -> poiManager.getInChunk(typePredicate, chunkPos, PoiManager.Occupancy.ANY)).filter(poiRecord ->
        {
            var blockPos2 = poiRecord.getPos();
            FishOfThieves.LOGGER.debug("absX: {}", Math.abs(blockPos2.getX() - pos.getX()));
            FishOfThieves.LOGGER.debug("absZ: {}", Math.abs(blockPos2.getZ() - pos.getZ()));
            FishOfThieves.LOGGER.debug("minimumDistance: {}", minimumDistance);
            FishOfThieves.LOGGER.debug("maximumDistance: {}", maximumDistance);
            FishOfThieves.LOGGER.debug("logic minimumDistance: {}", Math.abs(blockPos2.getX() - pos.getX()) >= minimumDistance && Math.abs(blockPos2.getZ() - pos.getZ()) >= minimumDistance);
            FishOfThieves.LOGGER.debug("logic maximumDistance: {}", Math.abs(blockPos2.getX() - pos.getX()) <= maximumDistance && Math.abs(blockPos2.getZ() - pos.getZ()) <= maximumDistance);
            return Math.abs(blockPos2.getX() - pos.getX()) >= minimumDistance && Math.abs(blockPos2.getZ() - pos.getZ()) >= minimumDistance && Math.abs(blockPos2.getX() - pos.getX()) <= maximumDistance && Math.abs(blockPos2.getZ() - pos.getZ()) <= maximumDistance;
        });
    }

    public static BlockPos attemptSpawnShoal(ServerLevel serverLevel, BlockPos blockPos, int maxAttempt)
    {
        if (maxAttempt > 0)
        {
            BlockPos shoalPos = null;
            var currentChunkPos = new ChunkPos(blockPos);

            for (var chunkPos : ChunkPos.rangeClosed(currentChunkPos, 8).toList())
            {
                shoalPos = ShoalSpawner.spawn(serverLevel, chunkPos.getMinBlockX(), chunkPos.getMinBlockZ());

                if (shoalPos != null)
                {
                    break;
                }
            }

            if (shoalPos == null)
            {
                FishOfThieves.LOGGER.debug("Shoal spawning attempt: {}", maxAttempt);
                return attemptSpawnShoal(serverLevel, blockPos, maxAttempt - 1);
            }
            else
            {
                FishOfThieves.LOGGER.debug("Attempted to spawn shoal at: {}", shoalPos.below());
                return shoalPos.below();
            }
        }
        return null;
    }

    @Nullable
    private static BlockPos spawnShoalAtSuitablePos(ServerLevel level, BlockPos pos)
    {
        var blockPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos);
        var biome = level.getBiome(blockPos);
        var isException = biome.is(FOTTags.Biomes.SHOAL_CANNOT_SPAWN);

        if (isException || !biome.is(FOTTags.Biomes.SPAWNS_SHOAL) || findNearestExistingShoal(level, blockPos).isPresent())
        {
            return null;
        }

        var belowState = level.getBlockState(blockPos.below());

        if (belowState.is(Blocks.ICE))
        {
            blockPos = blockPos.below();
        }

        if ((isOpenWater(level, blockPos) || belowState.is(Blocks.ICE)) && ShoalChance.canSpawnAt(level, blockPos))
        {
            level.setBlock(blockPos.below(), FOTBlocks.SHOAL.defaultBlockState(), Block.UPDATE_ALL);

            var shoal = FOTEntities.SHOAL.create(level, EntitySpawnReason.NATURAL);
            shoal.snapTo(blockPos.getX() + 0.5d, blockPos.getY() - 0.75d, blockPos.getZ() + 0.5d, 0, 0);
            shoal.createNaturalSpawn(true);
            level.addFreshEntity(shoal);

            FishOfThieves.LOGGER.debug("Spawn shoal at region {}, {}, {}, {}", biome.unwrapKey().get().identifier(), blockPos.getX(), blockPos.getY(), blockPos.getZ());

            //noinspection ConstantValue
            if (FishOfThieves.CONFIG.debug.spawnBeaconAtShoal && FOTPlatform.isDevelopment())
            {
                spawnBeacon(level, blockPos);
            }
            return blockPos;
        }
        return null;
    }

    private static Optional<BlockPos> findNearestExistingShoal(ServerLevel level, BlockPos pos)
    {
        var optional = level.getPoiManager().findClosest(holder -> holder.is(FOTTags.PoiTypes.SHOAL), blockPos -> level.getBlockState(blockPos.above(1)).is(Blocks.ICE) || blockPos.getY() == level.getHeight(Heightmap.Types.WORLD_SURFACE, blockPos.getX(), blockPos.getZ()) - 1, pos, FishOfThieves.CONFIG.shoal.spreadDistance, PoiManager.Occupancy.ANY);
        return optional.map(blockPos -> blockPos.above(1));
    }

    private static boolean isOpenWater(ServerLevel level, BlockPos pos)
    {
        var hasSourceWater = BlockPos.betweenClosedStream(pos.offset(-2, -1, -2), pos.offset(2, -2, 2)).allMatch(blockPos ->
        {
            var blockState = level.getBlockState(blockPos);
            var fluidState = blockState.getFluidState();
            return fluidState.is(FluidTags.WATER) && fluidState.isSource() && blockState.getCollisionShape(level, blockPos).isEmpty();
        });
        var hasOpenAir = BlockPos.betweenClosedStream(pos.offset(-2, 0, -2), pos.offset(2, 0, 2)).allMatch(blockPos ->
        {
            var blockState = level.getBlockState(blockPos);
            return level.canSeeSky(blockPos) && blockState.isAir() && !blockState.is(Blocks.LILY_PAD);
        });
        return hasSourceWater && hasOpenAir;
    }

    private static void spawnBeacon(ServerLevel level, BlockPos blockPos)
    {
        for (var blockPos1 : BlockPos.betweenClosed(blockPos.offset(-1, -5, -1), blockPos.offset(1, -5, 1)))
        {
            level.setBlock(blockPos1, Blocks.IRON_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
        }
        level.setBlock(blockPos.below(4), Blocks.BEACON.defaultBlockState(), Block.UPDATE_ALL);
    }
}