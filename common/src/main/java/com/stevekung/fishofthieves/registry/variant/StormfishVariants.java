package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.StormfishVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.core.registries.BuiltInRegistries;
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
        register(context, ANCIENT, "ancient", 0);
        register(context, SHORES, "shores", 1, ContinentalnessCondition.builder().continentalness(Continentalness.COAST).build());
        register(context, WILD, "wild", 2, MatchBiomeCondition.biomes(context.lookup(Registries.BIOME).getOrThrow(FOTTags.Biomes.SPAWNS_WILD_STORMFISH)).build());
        register(context, SHADOW, "shadow", 3, AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), SkyBrightnessCondition.skyBrightness(0, 4)).build());
        register(context, TWILIGHT, "twilight", 4, true, SkyDarkenCondition.skyDarken(9, 16).build());
    }

    static void register(BootstrapContext<StormfishVariant> context, ResourceKey<StormfishVariant> key, String name, int customModelData, SpawnCondition... conditions)
    {
        register(context, key, name, customModelData, false, conditions);
    }

    static void register(BootstrapContext<StormfishVariant> context, ResourceKey<StormfishVariant> key, String name, int customModelData, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.id("entity/stormfish/" + name);
        var glowTexture = FishOfThieves.id("entity/stormfish/" + name + "_glow");
        context.register(key, new StormfishVariant(name, texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions), BuiltInRegistries.ITEM.wrapAsHolder(FOTItems.STORMFISH), customModelData == 0 ? Optional.empty() : Optional.of(customModelData)));
    }

    private static ResourceKey<StormfishVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.STORMFISH_VARIANT, FishOfThieves.id(name));
    }
}