package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.StormfishVariant;
import com.stevekung.fishofthieves.registry.FOTBuiltInRegistries;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class StormfishVariants
{
    public static final ResourceKey<StormfishVariant> ANCIENT = createKey("ancient");
    public static final ResourceKey<StormfishVariant> SHORES = createKey("shores");
    public static final ResourceKey<StormfishVariant> WILD = createKey("wild");
    public static final ResourceKey<StormfishVariant> SHADOW = createKey("shadow");
    public static final ResourceKey<StormfishVariant> TWILIGHT = createKey("twilight");

    public static void bootstrap(BootstrapContext<StormfishVariant> context)
    {
        register(context, ANCIENT, "ancient");
        register(context, SHORES, "shores", AnyOfCondition.anyOf(ContinentalnessCondition.builder().continentalness(Continentalness.COAST)).build());
        register(context, WILD, "wild", AnyOfCondition.anyOf(MatchBiomeCondition.biomes(context.lookup(Registries.BIOME).getOrThrow(FOTTags.Biomes.SPAWNS_WILD_STORMFISH))).build());
        register(context, SHADOW, "shadow", AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), SkyBrightnessCondition.skyBrightness(0, 4)).build());
        register(context, TWILIGHT, "twilight", true, AnyOfCondition.anyOf(SkyDarkenCondition.skyDarken(9, 16)).build());
    }

    private static void register(String key, StormfishVariant variant)
    {
        Registry.register(FOTBuiltInRegistries.STORMFISH_VARIANT, FishOfThieves.res(key), variant);
    }

    static void register(BootstrapContext<StormfishVariant> context, ResourceKey<StormfishVariant> key, String name, SpawnCondition... conditions)
    {
        register(context, key, name, false, conditions);
    }

    static void register(BootstrapContext<StormfishVariant> context, ResourceKey<StormfishVariant> key, String name, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.res("entity/ancientscale/" + name);
        var glowTexture = FishOfThieves.res("entity/ancientscale/" + name + "_glow");
        context.register(key, new StormfishVariant(texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions)));
    }

    private static ResourceKey<StormfishVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.STORMFISH_VARIANT, FishOfThieves.res(name));
    }
}