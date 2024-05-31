package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.PondieVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.core.registries.BuiltInRegistries;
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
        register(context, CHARCOAL, "charcoal", 0);
        register(context, ORCHID, "orchid", 1);
        register(context, BRONZE, "bronze", 2);
        register(context, BRIGHT, "bright", 3, AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), DayCondition.day(), SeeSkyInWaterCondition.seeSkyInWater()).build());
        register(context, MOONSKY, "moonsky", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater()).build());
    }

    public static void bootstrapSimple(BootstrapContext<PondieVariant> context)
    {
        register(context, CHARCOAL, "charcoal", 0);
        register(context, ORCHID, "orchid", 1);
        register(context, BRONZE, "bronze", 2);
        register(context, BRIGHT, "bright", 3, ProbabilityCondition.defaultRareProbablity().build());
        register(context, MOONSKY, "moonsky", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater()).build());
    }

    static void register(BootstrapContext<PondieVariant> context, ResourceKey<PondieVariant> key, String name, int customModelData, SpawnCondition... conditions)
    {
        register(context, key, name, customModelData, false, conditions);
    }

    static void register(BootstrapContext<PondieVariant> context, ResourceKey<PondieVariant> key, String name, int customModelData, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.id("entity/pondie/" + name);
        var glowTexture = FishOfThieves.id("entity/pondie/" + name + "_glow");
        context.register(key, new PondieVariant(name, texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions), BuiltInRegistries.ITEM.wrapAsHolder(FOTItems.PONDIE), customModelData));
    }

    private static ResourceKey<PondieVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.PONDIE_VARIANT, FishOfThieves.id(name));
    }
}