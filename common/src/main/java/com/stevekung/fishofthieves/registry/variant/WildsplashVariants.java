package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.WildsplashVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class WildsplashVariants
{
    public static final WildsplashVariant RUSSET = WildsplashVariant.builder().condition(SpawnSelectors.always()).texture("russet").build();
    public static final WildsplashVariant SANDY = WildsplashVariant.builder().condition(SpawnSelectors.simpleSpawn(SpawnSelectors.biomeTag(FOTTags.Biomes.SPAWNS_SANDY_WILDSPLASH).and(SpawnSelectors.continentalness(Continentalness.COAST)))).texture("sandy").build();
    public static final WildsplashVariant OCEAN = WildsplashVariant.builder().condition(SpawnSelectors.simpleSpawn(SpawnSelectors.biomeTag(FOTTags.Biomes.SPAWNS_OCEAN_WILDSPLASH))).texture("ocean").build();
    public static final WildsplashVariant MUDDY = WildsplashVariant.builder().condition(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.muddyWildsplashProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.muddyWildsplashProbability).and(SpawnSelectors.biomeTag(FOTTags.Biomes.SPAWNS_MUDDY_WILDSPLASH)))).texture("muddy").build();
    public static final WildsplashVariant CORAL = WildsplashVariant.builder().condition(SpawnSelectors.simpleSpawn(true, SpawnSelectors.nightAndSeeSky().and(SpawnSelectors.biomeTag(FOTTags.Biomes.SPAWNS_CORAL_WILDSPLASH)).and(context -> TerrainUtils.lookForBlocksWithSize(context.blockPos(), 3, 24, blockPos -> context.level().getBlockState(blockPos).is(FOTTags.Blocks.CORAL_WILDSPLASH_SPAWNABLE_ON))))).texture("coral").glowTexture("coral_glow").build();

    public static void init()
    {
        register("russet", RUSSET);
        register("sandy", SANDY);
        register("ocean", OCEAN);
        register("muddy", MUDDY);
        register("coral", CORAL);
    }

    private static void register(String key, WildsplashVariant variant)
    {
        Registry.register(FOTRegistry.WILDSPLASH_VARIANT, new ResourceLocation(FishOfThieves.MOD_ID, key), variant);
    }
}