package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.WreckerVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class WreckerVariants
{
    public static final WreckerVariant ROSE = WreckerVariant.builder().condition(SpawnSelectors.always()).texture("rose").build();
    public static final WreckerVariant SUN = WreckerVariant.builder().condition(SpawnSelectors.simpleSpawn(SpawnSelectors.dayAndSeeSky())).texture("sun").glowTexture("sun_glow").build();
    public static final WreckerVariant BLACKCLOUD = WreckerVariant.builder().condition(SpawnSelectors.thunderingAndSeeSky()).texture("blackcloud").glowTexture("blackcloud_glow").build();
    public static final WreckerVariant SNOW = WreckerVariant.builder().condition(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.snowWreckerProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.snowWreckerProbability).and(SpawnSelectors.biomeTag(FOTTags.Biomes.SPAWNS_SNOW_WRECKERS)))).texture("snow").glowTexture("snow_glow").build();
    public static final WreckerVariant MOON = WreckerVariant.builder().condition(SpawnSelectors.simpleSpawn(true, SpawnSelectors.nightAndSeeSky().and(context -> context.level().getMoonBrightness() > 0F))).texture("moon").glowTexture("moon_glow").build();

    public static void init()
    {
        register("rose", WreckerVariants.ROSE);
        register("sun", WreckerVariants.SUN);
        register("blackcloud", WreckerVariants.BLACKCLOUD);
        register("snow", WreckerVariants.SNOW);
        register("moon", WreckerVariants.MOON);
    }

    private static void register(String key, WreckerVariant variant)
    {
        Registry.register(FOTRegistry.WRECKER_VARIANT, new ResourceLocation(FishOfThieves.MOD_ID, key), variant);
    }
}