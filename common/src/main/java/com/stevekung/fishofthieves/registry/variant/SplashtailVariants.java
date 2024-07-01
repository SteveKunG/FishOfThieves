package com.stevekung.fishofthieves.registry.variant;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.SplashtailVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class SplashtailVariants
{
    public static final ResourceKey<SplashtailVariant> RUBY = createKey("ruby");
    public static final ResourceKey<SplashtailVariant> SUNNY = createKey("sunny");
    public static final ResourceKey<SplashtailVariant> INDIGO = createKey("indigo");
    public static final ResourceKey<SplashtailVariant> UMBER = createKey("umber");
    public static final ResourceKey<SplashtailVariant> SEAFOAM = createKey("seafoam");

    public static void bootstrap(BootstrapContext<SplashtailVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("splashtail", SplashtailVariant::new);
        registerContext.register(context, RUBY, "ruby", 0);
        registerContext.register(context, SUNNY, "sunny", 1, List.of(AllOfCondition.allOf(DayCondition.day(), SeeSkyCondition.seeSky()).build()), List.of(AllOfCondition.allOf(DayCondition.day(), SeeSkyCondition.seeSky()).build()));
        registerContext.register(context, INDIGO, "indigo", 2);
        registerContext.register(context, UMBER, "umber", 3, ProbabilityCondition.defaultRareProbablity().build());
        registerContext.register(context, SEAFOAM, "seafoam", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build());
    }

    public static void bootstrapSimple(BootstrapContext<SplashtailVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("splashtail", SplashtailVariant::new);
        registerContext.register(context, RUBY, "ruby", 0);
        registerContext.register(context, SUNNY, "sunny", 1);
        registerContext.register(context, INDIGO, "indigo", 2);
        registerContext.register(context, UMBER, "umber", 3, ProbabilityCondition.defaultRareProbablity().build());
        registerContext.register(context, SEAFOAM, "seafoam", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build());
    }

    private static ResourceKey<SplashtailVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.SPLASHTAIL_VARIANT, FishOfThieves.id(name));
    }
}