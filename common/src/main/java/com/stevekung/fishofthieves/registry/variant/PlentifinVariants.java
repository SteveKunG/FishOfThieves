package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.PlentifinVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class PlentifinVariants
{
    public static final ResourceKey<PlentifinVariant> OLIVE = createKey("olive");
    public static final ResourceKey<PlentifinVariant> AMBER = createKey("amber");
    public static final ResourceKey<PlentifinVariant> CLOUDY = createKey("cloudy");
    public static final ResourceKey<PlentifinVariant> BONEDUST = createKey("bonedust");
    public static final ResourceKey<PlentifinVariant> WATERY = createKey("watery");

    public static void bootstrap(BootstrapContext<PlentifinVariant> context)
    {
        register(context, OLIVE, "olive");
        register(context, AMBER, "amber", AnyOfCondition.anyOf(RainingCondition.raining().invert().and(TimeOfDayCondition.timeOfDay(0.75f, 0.9f)).and(SeeSkyInWaterCondition.seeSkyInWater())).build());
        register(context, CLOUDY, "cloudy", AllOfCondition.allOf(RainingCondition.raining(), SeeSkyInWaterCondition.seeSkyInWater()).build());
        register(context, BONEDUST, "bonedust", AnyOfCondition.anyOf(ProbabilityCondition.defaultRareProbablity(), MatchStructureCondition.structures(FOTTags.Structures.BONEDUST_PLENTIFINS_SPAWN_IN).and(RandomChanceCondition.chance(10))).build());
        register(context, WATERY, "watery", true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater()).build());
    }

    static void register(BootstrapContext<PlentifinVariant> context, ResourceKey<PlentifinVariant> key, String name, SpawnCondition... conditions)
    {
        register(context, key, name, false, conditions);
    }

    static void register(BootstrapContext<PlentifinVariant> context, ResourceKey<PlentifinVariant> key, String name, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.res("entity/plentifin/" + name);
        var glowTexture = FishOfThieves.res("entity/plentifin/" + name + "_glow");
        context.register(key, new PlentifinVariant(texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions)));
    }

    private static ResourceKey<PlentifinVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.PLENTIFIN_VARIANT, FishOfThieves.res(name));
    }
}