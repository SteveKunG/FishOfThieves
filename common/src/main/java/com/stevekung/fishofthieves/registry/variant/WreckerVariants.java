package com.stevekung.fishofthieves.registry.variant;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.WreckerVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderSet;
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
        var registerContext = AbstractFishVariant.RegisterContext.create("wrecker", FOTItems.WRECKER, WreckerVariant::new);
        var biomeLookup = context.lookup(Registries.BIOME);
        registerContext.register(context, ROSE, "rose", 0);
        registerContext.register(context, SUN, "sun", 1, List.of(AllOfCondition.allOf(DayCondition.day(), SeeSkyCondition.seeSkyBelowWater()).build()), List.of(AllOfCondition.allOf(DayCondition.day(), SeeSkyCondition.seeSky()).build()));
        registerContext.register(context, BLACKCLOUD, "blackcloud", 2, List.of(AllOfCondition.allOf(RainingCondition.raining().thundering(true), SeeSkyCondition.seeSkyBelowWater()).build()), List.of(AllOfCondition.allOf(RainingCondition.raining().thundering(true), SeeSkyCondition.seeSky()).build()));
        registerContext.register(context, SNOW, "snow", 3, AnyOfCondition.anyOf(ProbabilityCondition.defaultRareProbablity(), MatchBiomeCondition.biomes(HolderSet.direct(biomeLookup.getOrThrow(Biomes.FROZEN_OCEAN), biomeLookup.getOrThrow(Biomes.DEEP_FROZEN_OCEAN))).and(RandomChanceCondition.chance(10))).build());
        registerContext.register(context, MOON, "moon", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSkyBelowWater(), MoonBrightnessCondition.moonBrightness(MinMaxBounds.Doubles.atMost(1.0d))).build());
    }

    public static void bootstrapSimple(BootstrapContext<WreckerVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("wrecker", FOTItems.WRECKER, WreckerVariant::new);
        registerContext.register(context, ROSE, "rose", 0);
        registerContext.register(context, SUN, "sun", 1);
        registerContext.register(context, BLACKCLOUD, "blackcloud", 2);
        registerContext.register(context, SNOW, "snow", 3, ProbabilityCondition.defaultRareProbablity().build());
        registerContext.register(context, MOON, "moon", 4, true, List.of(AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSkyBelowWater()).build()), List.of(AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build()));
    }

    private static ResourceKey<WreckerVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.WRECKER_VARIANT, FishOfThieves.id(name));
    }
}