package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
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
        register(context, CHARCOAL, "charcoal");
        register(context, ORCHID, "orchid");
        register(context, BRONZE, "bronze");
        register(context, BRIGHT, "bright", AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), DayCondition.day(), SeeSkyInWaterCondition.seeSkyInWater()).build());
        register(context, MOONSKY, "moonsky", true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater()).build());
    }

    static void register(BootstrapContext<PondieVariant> context, ResourceKey<PondieVariant> key, String name, SpawnCondition... conditions)
    {
        register(context, key, name, false, conditions);
    }

    static void register(BootstrapContext<PondieVariant> context, ResourceKey<PondieVariant> key, String name, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.id("entity/pondie/" + name);
        var glowTexture = FishOfThieves.id("entity/pondie/" + name + "_glow");
        context.register(key, new PondieVariant(texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions)));
    }

    private static ResourceKey<PondieVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.PONDIE_VARIANT, FishOfThieves.id(name));
    }
}