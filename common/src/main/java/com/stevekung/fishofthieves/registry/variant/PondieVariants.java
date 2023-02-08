package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.PondieVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import net.minecraft.core.Registry;

public class PondieVariants
{
    public static final PondieVariant CHARCOAL = PondieVariant.builder().condition(SpawnSelectors.always()).texture("charcoal").build();
    public static final PondieVariant ORCHID = PondieVariant.builder().condition(SpawnSelectors.always()).texture("orchid").build();
    public static final PondieVariant BRONZE = PondieVariant.builder().condition(SpawnSelectors.always()).texture("bronze").build();
    public static final PondieVariant BRIGHT = PondieVariant.builder().condition(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.brightPondieProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.brightPondieProbability).and(SpawnSelectors.dayAndSeeSky()))).texture("bright").build();
    public static final PondieVariant MOONSKY = PondieVariant.builder().condition(SpawnSelectors.nightAndSeeSky()).texture("moonsky").glowTexture("moonsky_glow").build();

    public static void init()
    {
        register("charcoal", PondieVariants.CHARCOAL);
        register("orchid", PondieVariants.ORCHID);
        register("bronze", PondieVariants.BRONZE);
        register("bright", PondieVariants.BRIGHT);
        register("moonsky", PondieVariants.MOONSKY);
    }

    private static void register(String key, PondieVariant variant)
    {
        Registry.register(FOTRegistry.PONDIE_VARIANT, FishOfThieves.res(key), variant);
    }
}