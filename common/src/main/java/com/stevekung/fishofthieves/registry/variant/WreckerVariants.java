package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.WreckerVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;

public class WreckerVariants
{
    public static final ResourceKey<WreckerVariant> ROSE = createKey("rose");
    public static final ResourceKey<WreckerVariant> SUN = createKey("sun");
    public static final ResourceKey<WreckerVariant> BLACKCLOUD = createKey("blackcloud");
    public static final ResourceKey<WreckerVariant> SNOW = createKey("snow");
    public static final ResourceKey<WreckerVariant> MOON = createKey("moon");

    public static void bootstrap(BootstrapContext<WreckerVariant> context)
    {
        var biomeLookup = context.lookup(Registries.BIOME);
        register(context, ROSE, "rose", 0);
        register(context, SUN, "sun", 1, AllOfCondition.allOf(DayCondition.day(), SeeSkyInWaterCondition.seeSkyInWater()).build());
        register(context, BLACKCLOUD, "blackcloud", 2, AllOfCondition.allOf(RainingCondition.raining().thundering(true), SeeSkyInWaterCondition.seeSkyInWater()).build());
        register(context, SNOW, "snow", 3, AnyOfCondition.anyOf(ProbabilityCondition.defaultRareProbablity(), MatchBiomeCondition.biomes(HolderSet.direct(biomeLookup.getOrThrow(Biomes.FROZEN_OCEAN), biomeLookup.getOrThrow(Biomes.DEEP_FROZEN_OCEAN))).and(RandomChanceCondition.chance(10))).build());
        register(context, MOON, "moon", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater(), MoonBrightnessCondition.moonBrightness(0f, 1f)).build());
    }

    static void register(BootstrapContext<WreckerVariant> context, ResourceKey<WreckerVariant> key, String name, int customModelData, SpawnCondition... conditions)
    {
        register(context, key, name, customModelData, false, conditions);
    }

    static void register(BootstrapContext<WreckerVariant> context, ResourceKey<WreckerVariant> key, String name, int customModelData, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.id("entity/wrecker/" + name);
        var glowTexture = FishOfThieves.id("entity/wrecker/" + name + "_glow");
        context.register(key, new WreckerVariant(name, texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions), BuiltInRegistries.ITEM.wrapAsHolder(FOTItems.WRECKER), customModelData == 0 ? Optional.empty() : Optional.of(customModelData)));
    }

    private static ResourceKey<WreckerVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.WRECKER_VARIANT, FishOfThieves.id(name));
    }
}