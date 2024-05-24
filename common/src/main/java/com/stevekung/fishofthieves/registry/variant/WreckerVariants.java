package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.WreckerVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class WreckerVariants
{
    public static final ResourceKey<WreckerVariant> ROSE = createKey("rose");
    public static final ResourceKey<WreckerVariant> SUN = createKey("sun");
    public static final ResourceKey<WreckerVariant> BLACKCLOUD = createKey("blackcloud");
    public static final ResourceKey<WreckerVariant> SNOW = createKey("snow");
    public static final ResourceKey<WreckerVariant> MOON = createKey("moon");

    public static void bootstrap(BootstrapContext<WreckerVariant> context)
    {
        register(context, ROSE, "rose");
        register(context, SUN, "sun", AllOfCondition.allOf(DayCondition.day(), SeeSkyInWaterCondition.seeSkyInWater()).build());
        register(context, BLACKCLOUD, "blackcloud", AllOfCondition.allOf(RainingCondition.raining().thundering(true), SeeSkyInWaterCondition.seeSkyInWater()).build());
        register(context, SNOW, "snow", AnyOfCondition.anyOf(ProbabilityCondition.defaultRareProbablity(), MatchBiomeCondition.biomes(context.lookup(Registries.BIOME).getOrThrow(FOTTags.Biomes.SPAWNS_SNOW_WRECKERS)).and(RandomChanceCondition.chance(10))).build());
        register(context, MOON, "moon", true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater(), MoonBrightnessCondition.moonBrightness(0f, 1f)).build());
    }

    static void register(BootstrapContext<WreckerVariant> context, ResourceKey<WreckerVariant> key, String name, SpawnCondition... conditions)
    {
        register(context, key, name, false, conditions);
    }

    static void register(BootstrapContext<WreckerVariant> context, ResourceKey<WreckerVariant> key, String name, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.res("entity/wrecker/" + name);
        var glowTexture = FishOfThieves.res("entity/wrecker/" + name + "_glow");
        context.register(key, new WreckerVariant(texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions)));
    }

    private static ResourceKey<WreckerVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.WRECKER_VARIANT, FishOfThieves.res(name));
    }
}