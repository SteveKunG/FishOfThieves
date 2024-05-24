package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.WildsplashVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class WildsplashVariants
{
    public static final ResourceKey<WildsplashVariant> RUSSET = createKey("russet");
    public static final ResourceKey<WildsplashVariant> SANDY = createKey("sandy");
    public static final ResourceKey<WildsplashVariant> OCEAN = createKey("ocean");
    public static final ResourceKey<WildsplashVariant> MUDDY = createKey("muddy");
    public static final ResourceKey<WildsplashVariant> CORAL = createKey("coral");

    public static void bootstrap(BootstrapContext<WildsplashVariant> context)
    {
        register(context, RUSSET, "russet");
        register(context, SANDY, "sandy", AnyOfCondition.anyOf(MatchBiomeCondition.biomes(context.lookup(Registries.BIOME).getOrThrow(FOTTags.Biomes.SPAWNS_SANDY_WILDSPLASH)).and(ContinentalnessCondition.builder().continentalness(Continentalness.COAST))).build());
        register(context, OCEAN, "ocean", AnyOfCondition.anyOf(MatchBiomeCondition.biomes(context.lookup(Registries.BIOME).getOrThrow(FOTTags.Biomes.SPAWNS_OCEAN_WILDSPLASH))).build());
        register(context, MUDDY, "muddy", AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), MatchBiomeCondition.biomes(context.lookup(Registries.BIOME).getOrThrow(FOTTags.Biomes.SPAWNS_MUDDY_WILDSPLASH))).build());
        register(context, CORAL, "coral", true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater(), MatchBiomeCondition.biomes(context.lookup(Registries.BIOME).getOrThrow(FOTTags.Biomes.SPAWNS_CORAL_WILDSPLASH)), MatchMinimumBlocksInRangeCondition.minimumBlocksInRange(Optional.of(context.lookup(Registries.BLOCK).getOrThrow(FOTTags.Blocks.CORAL_WILDSPLASH_SPAWNABLE_ON)), Optional.empty(), 4, 24)).build());
    }

    static void register(BootstrapContext<WildsplashVariant> context, ResourceKey<WildsplashVariant> key, String name, SpawnCondition... conditions)
    {
        register(context, key, name, false, conditions);
    }

    static void register(BootstrapContext<WildsplashVariant> context, ResourceKey<WildsplashVariant> key, String name, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.res("entity/wildsplash/" + name);
        var glowTexture = FishOfThieves.res("entity/wildsplash/" + name + "_glow");
        context.register(key, new WildsplashVariant(texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions)));
    }

    private static ResourceKey<WildsplashVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.WILDSPLASH_VARIANT, FishOfThieves.res(name));
    }
}