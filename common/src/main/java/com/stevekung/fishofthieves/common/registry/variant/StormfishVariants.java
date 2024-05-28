package com.stevekung.fishofthieves.common.registry.variant;

import com.stevekung.fishofthieves.common.FishOfThieves;
import com.stevekung.fishofthieves.common.entity.variant.StormfishVariant;
import com.stevekung.fishofthieves.common.registry.FOTRegistry;
import com.stevekung.fishofthieves.common.registry.FOTTags;
import com.stevekung.fishofthieves.common.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.common.utils.Continentalness;
import net.minecraft.core.Registry;
import net.minecraft.world.level.LightLayer;

public class StormfishVariants
{
    public static final StormfishVariant ANCIENT = StormfishVariant.builder().condition(SpawnSelectors.always()).texture("ancient").build();
    public static final StormfishVariant SHORES = StormfishVariant.builder().condition(SpawnSelectors.simpleSpawn(SpawnSelectors.continentalness(Continentalness.COAST))).texture("shores").build();
    public static final StormfishVariant WILD = StormfishVariant.builder().condition(SpawnSelectors.simpleSpawn(SpawnSelectors.biomeTag(FOTTags.Biomes.SPAWNS_WILD_STORMFISH))).texture("wild").build();
    public static final StormfishVariant SHADOW = StormfishVariant.builder().condition(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.variant.shadowStormfishProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.variant.shadowStormfishProbability).and(context -> context.level().getBrightness(LightLayer.SKY, context.blockPos()) <= 4))).texture("shadow").build();
    public static final StormfishVariant TWILIGHT = StormfishVariant.builder().condition(SpawnSelectors.simpleSpawn(true, context -> context.level().getSkyDarken() >= 9)).texture("twilight").glowTexture("twilight_glow").build();

    public static void init()
    {
        register("ancient", StormfishVariants.ANCIENT);
        register("shores", StormfishVariants.SHORES);
        register("wild", StormfishVariants.WILD);
        register("shadow", StormfishVariants.SHADOW);
        register("twilight", StormfishVariants.TWILIGHT);
    }

    private static void register(String key, StormfishVariant variant)
    {
        Registry.register(FOTRegistry.STORMFISH_VARIANT, FishOfThieves.id(key), variant);
    }
}