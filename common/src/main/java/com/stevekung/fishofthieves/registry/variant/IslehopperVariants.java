package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.IslehopperVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;

public class IslehopperVariants
{
    public static final IslehopperVariant STONE = IslehopperVariant.builder().condition(SpawnSelectors.always()).texture("stone").build();
    public static final IslehopperVariant MOSS = IslehopperVariant.builder().condition(SpawnSelectors.simpleSpawn(SpawnSelectors.biomeTag(FOTTags.Biomes.SPAWNS_MOSS_ISLEHOPPERS))).texture("moss").build();
    public static final IslehopperVariant HONEY = IslehopperVariant.builder().condition(SpawnSelectors.simpleSpawn(context ->
    {
        var poiManager = context.level().getPoiManager();
        var optional = poiManager.findClosest(type -> type.is(PoiTypes.BEEHIVE) || type.is(PoiTypes.BEE_NEST), context.blockPos(), 9, PoiManager.Occupancy.ANY);

        if (optional.isPresent())
        {
            var blockState = context.level().getBlockState(optional.get());
            return blockState.hasProperty(BeehiveBlock.HONEY_LEVEL) && BeehiveBlockEntity.getHoneyLevel(blockState) == BeehiveBlock.MAX_HONEY_LEVELS;
        }
        return false;
    })).texture("honey").build();
    public static final IslehopperVariant RAVEN = IslehopperVariant.builder().condition(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.variant.ravenIslehopperProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.variant.ravenIslehopperProbability).and(context -> context.blockPos().getY() <= 0))).texture("raven").build();
    public static final IslehopperVariant AMETHYST = IslehopperVariant.builder().condition(SpawnSelectors.simpleSpawn(true, context -> TerrainUtils.lookForBlocksWithSize(context.blockPos(), 2, 16, blockPos2 -> context.level().getBlockState(blockPos2).is(FOTTags.Blocks.AMETHYST_ISLEHOPPER_SPAWNABLE_ON)))).texture("amethyst").glowTexture("amethyst_glow").build();

    public static void init()
    {
        register("stone", IslehopperVariants.STONE);
        register("moss", IslehopperVariants.MOSS);
        register("honey", IslehopperVariants.HONEY);
        register("raven", IslehopperVariants.RAVEN);
        register("amethyst", IslehopperVariants.AMETHYST);
    }

    private static void register(String key, IslehopperVariant variant)
    {
        Registry.register(FOTRegistry.ISLEHOPPER_VARIANT, FishOfThieves.id(key), variant);
    }
}