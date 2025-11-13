package com.stevekung.fishofthieves;

import java.util.Optional;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.registry.FOTPoiTypes;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class FlockSpawnTest
{
    public static final FlockSpawnTest INSTANCE = new FlockSpawnTest();

    public void testplayer(ServerLevel level)
    {
                if (level.getRandomPlayer() != null)
                {
                    if (level.getRandomPlayer().tickCount % 20 == 0)
                    {

                    }
//                    System.out.println();
//                        this.calculateOpenWater(level, level.getRandomPlayer().blockPosition());
                }
    }

    public void test(ServerLevel level, int x, int z)
    {
        if (level.random.nextInt(100) == 0)
        {
            var blockPos = this.findLightningTargetAround(level, level.getBlockRandomPos(x, level.getSeaLevel(), z, 0));

            if (blockPos != null)
            {
                var bl2 = !level.getBlockState(blockPos.below()).is(Blocks.LIGHT_BLUE_WOOL);

                if (bl2)
                {
                    var lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
                    if (lightningBolt != null)
                    {
                        lightningBolt.moveTo(Vec3.atBottomCenterOf(blockPos));
                        lightningBolt.setVisualOnly(true);
                        level.addFreshEntity(lightningBolt);
                    }
                }
            }
        }
    }

    private Optional<BlockPos> findLightningRod(ServerLevel level, BlockPos pos)
    {
        var optional = level.getPoiManager()
                .findClosest(holder -> holder.is(FOTPoiTypes.FISH_FLOCK), blockPos -> blockPos.getY() == level.getHeight(Heightmap.Types.WORLD_SURFACE, blockPos.getX(), blockPos.getZ()) - 1, pos, 48, PoiManager.Occupancy.ANY);
        return optional.map(blockPos -> blockPos.above(1));
    }

    private boolean calculateOpenWater(ServerLevel level, BlockPos pos) {

        var hasSourceWater = BlockPos.betweenClosedStream(pos.offset(-2, -1, -2), pos.offset(2, -2, 2)).allMatch(blockPos ->
        {
            var blockState = level.getBlockState(blockPos);
            var fluidState = blockState.getFluidState();
            return fluidState.is(FluidTags.WATER) && fluidState.isSource() && blockState.getCollisionShape(level, blockPos).isEmpty();
        });
        var hasOpenAir = BlockPos.betweenClosedStream(pos.offset(-2, 0, -2), pos.offset(2, 0, 2)).allMatch(blockPos ->
        {
            var blockState = level.getBlockState(blockPos);
            return blockState.isAir() && !blockState.is(Blocks.LILY_PAD);
        });
        return hasSourceWater && hasOpenAir;
    }

    @Nullable
    protected BlockPos findLightningTargetAround(ServerLevel level, BlockPos pos)
    {
        var blockPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos);
        var optional = this.findLightningRod(level, blockPos);
        var biome = level.getBiome(blockPos);

//        System.out.println(optional);

        if (optional.isEmpty())
        {
            if (biome.is(BiomeTags.IS_RIVER)
                    || biome.is(BiomeTags.IS_OCEAN)
                    || biome.is(BiomeTags.IS_BEACH)
                    || biome.is(Biomes.SWAMP)
                    || biome.is(Biomes.MANGROVE_SWAMP)
            )
            {
                var continentalness = TerrainUtils.getContinentalness(level, blockPos);
                var isCoast = !biome.is(Biomes.MUSHROOM_FIELDS) && continentalness != Continentalness.MUSHROOM_FIELDS;

                if (isCoast && this.calculateOpenWater(level, pos)&& level.getFluidState(blockPos.below()).is(FluidTags.WATER))
                {
                    if (Util.getRandom(PoolChance.VALUES, level.getRandom()).getPoolChance(level, blockPos, level.getRandom()))
                    {
                        System.out.println(blockPos);
                        System.out.println(biome);
                        level.setBlock(blockPos.below(), Blocks.LIGHT_BLUE_WOOL.defaultBlockState(), Block.UPDATE_ALL);
                    }
                }
            }
        }
        return null;
    }

    public record PoolSpawnContext(Holder<Biome> biome, Continentalness continentalness) {}

    public enum PoolChance
    {
        RIVER_OR_BEACH(context -> context.biome().is(BiomeTags.IS_RIVER) || context.biome().is(BiomeTags.IS_BEACH), 6),
        COAST(context -> context.continentalness() == Continentalness.COAST, 16),
        OCEAN(context -> context.biome().is(BiomeTags.IS_OCEAN), 80),
        DEEP_OCEAN(context -> context.biome().is(BiomeTags.IS_DEEP_OCEAN), 95),
        SWAMP(context -> context.biome().is(Biomes.SWAMP) || context.biome().is(Biomes.MANGROVE_SWAMP), 20)
        ;

        private static final PoolChance[] VALUES = values();
        private final Predicate<PoolSpawnContext> context;
        private final int poolWeight;

        PoolChance(Predicate<PoolSpawnContext> context, int poolWeight)
        {
            this.context = context;
            this.poolWeight = poolWeight;
        }

        public boolean getPoolChance(ServerLevel level, BlockPos blockPos, RandomSource randomSource)
        {
            var biome = level.getBiome(blockPos);
            var continentalness = TerrainUtils.getContinentalness(level, blockPos);
            var context = new PoolSpawnContext(biome, continentalness);

            if (this.context.test(context))
            {
                var poolChance = this.poolWeight / 100.0f;
                System.out.println(poolChance);
                System.out.println(this.name());
                return randomSource.nextFloat() >= poolChance;
            }
            return false;
        }
    }
}