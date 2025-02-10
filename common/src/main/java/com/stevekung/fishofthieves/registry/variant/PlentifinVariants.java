package com.stevekung.fishofthieves.registry.variant;

import java.util.function.Predicate;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.PlentifinVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.variant.StructureCheck;

public class PlentifinVariants
{
    public static final ResourceKey<PlentifinVariant> OLIVE = createKey("olive");
    public static final ResourceKey<PlentifinVariant> AMBER = createKey("amber");
    public static final ResourceKey<PlentifinVariant> CLOUDY = createKey("cloudy");
    public static final ResourceKey<PlentifinVariant> BONEDUST = createKey("bonedust");
    public static final ResourceKey<PlentifinVariant> WATERY = createKey("watery");

    public static void bootstrap(BootstrapContext<PlentifinVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("plentifin", PlentifinVariant::new);
        var structureLookup = context.lookup(Registries.STRUCTURE);

        registerContext.register(context, OLIVE, "olive", 0);
        registerContext.register(context, AMBER, "amber", 1, Predicate.not(RainingCondition.raining().build()).and(TimeOfDayCondition.timeOfDay(MinMaxBounds.Doubles.between(0.75d, 0.9d))).and(SeeSkyCondition.seeSky()));
        registerContext.register(context, CLOUDY, "cloudy", 2, RainingCondition.raining().build().and(SeeSkyCondition.seeSky()));
        registerContext.register(context, BONEDUST, "bonedust", 3,
                registerContext.select(new StructureCheck(structureLookup.getOrThrow(FOTTags.Structures.BONEDUST_PLENTIFINS_SPAWN_IN)).and(RandomChanceCondition.chance(10)), 0),
                registerContext.select(ProbabilityCondition.defaultRareProbablity(), 1));
        registerContext.register(context, WATERY, "watery", 4, true, NightCondition.night().and(SeeSkyCondition.seeSky()));
    }

    public static void bootstrapSimple(BootstrapContext<PlentifinVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("plentifin", PlentifinVariant::new);
        registerContext.register(context, OLIVE, "olive", 0);
        registerContext.register(context, AMBER, "amber", 1);
        registerContext.register(context, CLOUDY, "cloudy", 2);
        registerContext.register(context, BONEDUST, "bonedust", 3, ProbabilityCondition.defaultRareProbablity());
        registerContext.register(context, WATERY, "watery", 4, true, NightCondition.night().and(SeeSkyCondition.seeSky()));
    }

    private static ResourceKey<PlentifinVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.PLENTIFIN_VARIANT, FishOfThieves.id(name));
    }
}