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
        registerContext.register(context, CHARCOAL, "charcoal");
        registerContext.register(context, ORCHID, "orchid");
        registerContext.register(context, BRONZE, "bronze");
        registerContext.register(context, BRIGHT, "bright", AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), DayCondition.day(), SeeSkyCondition.seeSky()).build());
        registerContext.register(context, MOONSKY, "moonsky", true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build());
    }

    public static void bootstrapSimple(BootstrapContext<PondieVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("pondie", PondieVariant::new);
        registerContext.register(context, CHARCOAL, "charcoal");
        registerContext.register(context, ORCHID, "orchid");
        registerContext.register(context, BRONZE, "bronze");
        registerContext.register(context, BRIGHT, "bright", ProbabilityCondition.defaultRareProbablity().build());
        registerContext.register(context, MOONSKY, "moonsky", true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build());
    }

    private static ResourceKey<PondieVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.PONDIE_VARIANT, FishOfThieves.id(name));
    }
}