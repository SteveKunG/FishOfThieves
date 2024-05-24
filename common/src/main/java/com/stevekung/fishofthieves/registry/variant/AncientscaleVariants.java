package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.FOTBuiltInRegistries;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class AncientscaleVariants
{
    public static final ResourceKey<AncientscaleVariant> ALMOND = createKey("almond");
    public static final ResourceKey<AncientscaleVariant> SAPPHIRE = createKey("sapphire");
    public static final ResourceKey<AncientscaleVariant> SMOKE = createKey("smoke");
    public static final ResourceKey<AncientscaleVariant> BONE = createKey("bone");
    public static final ResourceKey<AncientscaleVariant> STARSHINE = createKey("starshine");
    
    public static final AncientscaleVariant BONE = AncientscaleVariant.builder().condition(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.variant.boneAncientscaleProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.variant.boneAncientscaleProbability).or(SpawnSelectors.structureTag(FOTTags.Structures.BONE_ANCIENTSCALES_SPAWN_IN).and(context -> context.random().nextInt(10) == 0)))).texture("bone").build();
    public static final AncientscaleVariant STARSHINE = AncientscaleVariant.builder().condition(SpawnSelectors.simpleSpawn(true, SpawnSelectors.nightAndSeeSky().and(context -> context.level().getMoonBrightness() <= 0.25F))).texture("starshine").glowTexture("starshine_glow").build();

    public static void bootstrap(BootstrapContext<AncientscaleVariant> context)
    {
        register(context, ALMOND, "almond");
        register(context, SAPPHIRE, "sapphire");
        register(context, SMOKE, "smoke");

        context.lookup(Registries.STRUCTURE).getOrThrow(FOTTags.Structures.BONE_ANCIENTSCALES_SPAWN_IN);

        register(context, BONE, "bone", AnyOfCondition.anyOf(ProbabilityCondition.defaultRareProbablity(), MatchStructureCondition.hasStructure(context.lookup(Registries.STRUCTURE).getOrThrow(FOTTags.Structures.BONE_ANCIENTSCALES_SPAWN_IN)).and()).build());
        register(context, STARSHINE, "starshine", true, AllOfCondition.allOf(IsNightCondition.isNight(), SeeSkyInWaterCondition.seeSkyInWater()).build());
    }

    static void register(BootstrapContext<AncientscaleVariant> context, ResourceKey<AncientscaleVariant> key, String name, SpawnCondition... conditions)
    {
        register(context, key, name, false, conditions);
    }

    static void register(BootstrapContext<AncientscaleVariant> context, ResourceKey<AncientscaleVariant> key, String name, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.res("entity/ancientscale/" + name);
        var glowTexture = FishOfThieves.res("entity/ancientscale/" + name + "_glow");
        context.register(key, new AncientscaleVariant(texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions)));
    }

    private static ResourceKey<AncientscaleVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.ANCIENTSCALE_VARIANT, FishOfThieves.res(name));
    }
}