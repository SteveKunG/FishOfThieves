package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.PlentifinVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;

import net.minecraft.core.Registry;

public class PlentifinVariants
{
    public static final PlentifinVariant OLIVE = PlentifinVariant.builder().condition(SpawnSelectors.always()).texture("olive").build();
    public static final PlentifinVariant AMBER = PlentifinVariant.builder().condition(SpawnSelectors.simpleSpawn(SpawnSelectors.rainingAndSeeSky().negate().and(context ->
    {
        var time = context.level().getTimeOfDay(1.0F);
        return time >= 0.75F && time <= 0.9F;
    }).and(SpawnConditionContext::seeSkyInWater))).texture("amber").build();
    public static final PlentifinVariant CLOUDY = PlentifinVariant.builder().condition(SpawnSelectors.simpleSpawn(SpawnSelectors.rainingAndSeeSky())).texture("cloudy").build();
    public static final PlentifinVariant BONEDUST = PlentifinVariant.builder().condition(SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.variant.bonedustPlentifinProbability)).texture("bonedust").build();
    public static final PlentifinVariant WATERY = PlentifinVariant.builder().condition(SpawnSelectors.nightAndSeeSky()).texture("watery").glowTexture("watery_glow").build();
    public static final PlentifinVariant CRIMSON = PlentifinVariant.builder().condition(SpawnSelectors.never()).texture("treasured/crimson").glowTexture("treasured/crimson_glow").treasured().build();

    public static void init()
    {
        register("olive", PlentifinVariants.OLIVE);
        register("amber", PlentifinVariants.AMBER);
        register("cloudy", PlentifinVariants.CLOUDY);
        register("bonedust", PlentifinVariants.BONEDUST);
        register("watery", PlentifinVariants.WATERY);
        register("crimson", PlentifinVariants.CRIMSON);
    }

    private static void register(String key, PlentifinVariant variant)
    {
        Registry.register(FOTRegistry.PLENTIFIN_VARIANT, FishOfThieves.id(key), variant);
    }
}