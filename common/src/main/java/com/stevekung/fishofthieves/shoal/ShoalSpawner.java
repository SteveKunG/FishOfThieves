package com.stevekung.fishofthieves.shoal;

import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTPoiTypes;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;

public final class ShoalSpawner
{
    private ShoalSpawner() {}

    public static void spawn(ServerLevel level, int x, int z)
    {
        var chance = level.isRaining() ? FishOfThieves.CONFIG.shoal.shoalChanceRaining : FishOfThieves.CONFIG.shoal.shoalChance;

        if (level.random.nextInt(chance) == 0)
        {
            findSuitableShoalPosition(level, level.getBlockRandomPos(x, level.getSeaLevel(), z, 0));
        }
    }

    private static void findSuitableShoalPosition(ServerLevel level, BlockPos pos)
    {
        var blockPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos);
        var biome = level.getBiome(blockPos);
        var continentalness = TerrainUtils.getContinentalness(level, blockPos);
        var isException = biome.is(Biomes.MUSHROOM_FIELDS) || continentalness == Continentalness.MUSHROOM_FIELDS;

        if (isException || findNearestExistingShoal(level, blockPos).isPresent())
        {
            return;
        }

        if (biome.is(FOTTags.Biomes.SPAWNS_SHOAL) && isOpenWater(level, blockPos) && ShoalChance.canSpawnAt(level, blockPos))
        {
            level.setBlock(blockPos.below(), FOTBlocks.SHOAL_BLOCK.defaultBlockState(), Block.UPDATE_ALL);

            var shoal = FOTEntities.SHOAL.create(level);
            shoal.moveTo(blockPos.getX() + 0.5d, blockPos.getY() - 0.75d, blockPos.getZ() + 0.5d, 0, 0);
            shoal.createNaturalSpawn();
            level.addFreshEntity(shoal);

            FishOfThieves.LOGGER.debug("Spawn shoal at region {}, {}, {}, {}", biome.unwrapKey().get().location(), blockPos.getX(), blockPos.getY(), blockPos.getZ());

            if (FishOfThieves.CONFIG.debug.spawnBeaconAtShoal)
            {
                spawnBeacon(level, blockPos);
            }
        }
    }

    private static Optional<BlockPos> findNearestExistingShoal(ServerLevel level, BlockPos pos)
    {
        var optional = level.getPoiManager().findClosest(holder -> holder.is(FOTPoiTypes.SHOAL), blockPos -> blockPos.getY() == level.getHeight(Heightmap.Types.WORLD_SURFACE, blockPos.getX(), blockPos.getZ()) - 1, pos, FishOfThieves.CONFIG.shoal.shoalSpreadDistance, PoiManager.Occupancy.ANY);
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