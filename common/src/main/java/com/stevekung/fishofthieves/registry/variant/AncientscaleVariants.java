package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class AncientscaleVariants
{
    public static final AncientscaleVariant ALMOND = AncientscaleVariant.builder().condition(SpawnSelectors.always()).texture("almond").build();
    public static final AncientscaleVariant SAPPHIRE = AncientscaleVariant.builder().condition(SpawnSelectors.always()).texture("sapphire").build();
    public static final AncientscaleVariant SMOKE = AncientscaleVariant.builder().condition(SpawnSelectors.always()).texture("smoke").build();
    public static final AncientscaleVariant BONE = AncientscaleVariant.builder().condition(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.boneAncientscaleProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.boneAncientscaleProbability).or(SpawnSelectors.structureTag(FOTTags.Structures.BONE_ANCIENTSCALES_SPAWN_IN).and(context -> context.random().nextInt(10) == 0)))).texture("bone").build();
    public static final AncientscaleVariant STARSHINE = AncientscaleVariant.builder().condition(SpawnSelectors.simpleSpawn(true, SpawnSelectors.nightAndSeeSky().and(context -> context.level().getMoonBrightness() <= 0.25F))).texture("starshine").glowTexture("starshine_glow").build();

    public static void init()
    {
        register("almond", AncientscaleVariants.ALMOND);
        register("sapphire", AncientscaleVariants.SAPPHIRE);
        register("smoke", AncientscaleVariants.SMOKE);
        register("bone", AncientscaleVariants.BONE);
        register("starshine", AncientscaleVariants.STARSHINE);
    }

    private static void register(String key, AncientscaleVariant variant)
    {
        Registry.register(FOTRegistry.ANCIENTSCALE_VARIANT, FishOfThieves.res(key), variant);
    }
}