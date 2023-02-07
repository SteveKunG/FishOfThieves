package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.SplashtailVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import net.minecraft.core.Registry;

public class SplashtailVariants
{
    public static final SplashtailVariant RUBY = SplashtailVariant.builder().condition(SpawnSelectors.always()).texture("ruby").build();
    public static final SplashtailVariant SUNNY = SplashtailVariant.builder().condition(SpawnSelectors.simpleSpawn(SpawnSelectors.dayAndSeeSky())).texture("sunny").build();
    public static final SplashtailVariant INDIGO = SplashtailVariant.builder().condition(SpawnSelectors.always()).texture("indigo").build();
    public static final SplashtailVariant UMBER = SplashtailVariant.builder().condition(SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.umberSplashtailProbability)).texture("umber").build();
    public static final SplashtailVariant SEAFOAM = SplashtailVariant.builder().condition(SpawnSelectors.nightAndSeeSky()).texture("seafoam").glowTexture("seafoam_glow").build();

    public static void init()
    {
        register("ruby", SplashtailVariants.RUBY);
        register("sunny", SplashtailVariants.SUNNY);
        register("indigo", SplashtailVariants.INDIGO);
        register("umber", SplashtailVariants.UMBER);
        register("seafoam", SplashtailVariants.SEAFOAM);
    }

    private static void register(String key, SplashtailVariant variant)
    {
        Registry.register(FOTRegistry.SPLASHTAIL_VARIANT, FishOfThieves.res(key), variant);
    }
}