package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.PlentifinVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

public class PlentifinVariants
{
    public static final ResourceKey<PlentifinVariant> OLIVE = createKey("olive");
    public static final ResourceKey<PlentifinVariant> AMBER = createKey("amber");
    public static final ResourceKey<PlentifinVariant> CLOUDY = createKey("cloudy");
    public static final ResourceKey<PlentifinVariant> BONEDUST = createKey("bonedust");
    public static final ResourceKey<PlentifinVariant> WATERY = createKey("watery");

    public static void bootstrap(BootstrapContext<PlentifinVariant> context)
    {
        var structureLookup = context.lookup(Registries.STRUCTURE);
        var registerContext = AbstractFishVariant.RegisterContext.create("plentifin", PlentifinVariant::new);
        registerContext.register(context, OLIVE, "olive", 0);
        registerContext.register(context, AMBER, "amber", 1, RainingCondition.raining().invert().and(TimeOfDayCondition.timeOfDay(MinMaxBounds.Doubles.between(0.75d, 0.9d))).and(SeeSkyCondition.seeSky()).build());
        registerContext.register(context, CLOUDY, "cloudy", 2, AllOfCondition.allOf(RainingCondition.raining(), SeeSkyCondition.seeSky()).build());
        registerContext.register(context, BONEDUST, "bonedust", 3, AnyOfCondition.anyOf(ProbabilityCondition.defaultRareProbablity(), RandomChanceCondition.chance(10).and(MatchStructureCondition.structures(HolderSet.direct(structureLookup.getOrThrow(BuiltinStructures.STRONGHOLD))).or(MatchStructureCondition.structures(structureLookup.getOrThrow(StructureTags.MINESHAFT))))).build());
        registerContext.register(context, WATERY, "watery", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build());
    }

    public static void bootstrapSimple(BootstrapContext<PlentifinVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("plentifin", PlentifinVariant::new);
        registerContext.register(context, OLIVE, "olive", 0);
        registerContext.register(context, AMBER, "amber", 1);
        registerContext.register(context, CLOUDY, "cloudy", 2);
        registerContext.register(context, BONEDUST, "bonedust", 3, ProbabilityCondition.defaultRareProbablity().build());
        registerContext.register(context, WATERY, "watery", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build());
    }

    private static ResourceKey<PlentifinVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.PLENTIFIN_VARIANT, FishOfThieves.id(name));
    }
}