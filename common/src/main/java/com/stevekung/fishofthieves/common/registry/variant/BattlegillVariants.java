package com.stevekung.fishofthieves.common.registry.variant;

import com.stevekung.fishofthieves.common.FishOfThieves;
import com.stevekung.fishofthieves.common.entity.variant.BattlegillVariant;
import com.stevekung.fishofthieves.common.registry.FOTRegistry;
import com.stevekung.fishofthieves.common.registry.FOTTags;
import com.stevekung.fishofthieves.common.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.common.spawn.SpawnSelectors;
import net.minecraft.core.Registry;

public class BattlegillVariants
{
    public static final BattlegillVariant JADE = BattlegillVariant.builder().condition(SpawnSelectors.always()).texture("jade").build();
    public static final BattlegillVariant SKY = BattlegillVariant.builder().condition(SpawnSelectors.simpleSpawn(SpawnConditionContext::seeSkyInWater)).texture("sky").build();
    public static final BattlegillVariant RUM = BattlegillVariant.builder().condition(SpawnSelectors.always()).texture("rum").build();
    public static final BattlegillVariant SAND = BattlegillVariant.builder().condition(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.variant.sandBattlegillProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.variant.sandBattlegillProbability).and(SpawnSelectors.biomeTag(FOTTags.Biomes.SPAWNS_SAND_BATTLEGILLS)))).texture("sand").build();
    public static final BattlegillVariant BITTERSWEET = BattlegillVariant.builder().condition(SpawnSelectors.nightAndSeeSky()).texture("bittersweet").glowTexture("bittersweet_glow").build();

    public static void init()
    {
        register("jade", BattlegillVariants.JADE);
        register("sky", BattlegillVariants.SKY);
        register("rum", BattlegillVariants.RUM);
        register("sand", BattlegillVariants.SAND);
        register("bittersweet", BattlegillVariants.BITTERSWEET);
    }

    private static void register(String key, BattlegillVariant variant)
    {
        Registry.register(FOTRegistry.BATTLEGILL_VARIANT, FishOfThieves.id(key), variant);
    }
}