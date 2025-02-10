package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.PondieVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class PondieVariants
{
    public static final ResourceKey<PondieVariant> CHARCOAL = createKey("charcoal");
    public static final ResourceKey<PondieVariant> ORCHID = createKey("orchid");
    public static final ResourceKey<PondieVariant> BRONZE = createKey("bronze");
    public static final ResourceKey<PondieVariant> BRIGHT = createKey("bright");
    public static final ResourceKey<PondieVariant> MOONSKY = createKey("moonsky");

    public static void bootstrap(BootstrapContext<PondieVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("pondie", PondieVariant::new);
        registerContext.register(context, CHARCOAL, "charcoal", 0);
        registerContext.register(context, ORCHID, "orchid", 1);
        registerContext.register(context, BRONZE, "bronze", 2);
        registerContext.register(context, BRIGHT, "bright", 3, AllConditionCheck.allOf(ProbabilityCheck.defaultRareProbablity(), DayCheck.day(), SeeSkyCheck.seeSky()));
        registerContext.register(context, MOONSKY, "moonsky", 4, true, AllConditionCheck.allOf(NightCheck.night(), SeeSkyCheck.seeSky()));
    }

    public static void bootstrapSimple(BootstrapContext<PondieVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("pondie", PondieVariant::new);
        registerContext.register(context, CHARCOAL, "charcoal", 0);
        registerContext.register(context, ORCHID, "orchid", 1);
        registerContext.register(context, BRONZE, "bronze", 2);
        registerContext.register(context, BRIGHT, "bright", 3, ProbabilityCheck.defaultRareProbablity());
        registerContext.register(context, MOONSKY, "moonsky", 4, true, AllConditionCheck.allOf(NightCheck.night(), SeeSkyCheck.seeSky()));
    }

    private static ResourceKey<PondieVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.PONDIE_VARIANT, FishOfThieves.id(name));
    }
}