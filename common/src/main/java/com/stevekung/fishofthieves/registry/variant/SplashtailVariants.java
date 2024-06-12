package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.SplashtailVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.core.registries.BuiltInRegistries;
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
        register(context, RUBY, "ruby", 0);
        register(context, SUNNY, "sunny", 1, AllOfCondition.allOf(DayCondition.day(), SeeSkyCondition.seeSkyBelowWater()).build());
        register(context, INDIGO, "indigo", 2);
        register(context, UMBER, "umber", 3, ProbabilityCondition.defaultRareProbablity().build());
        register(context, SEAFOAM, "seafoam", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSkyBelowWater()).build());
    }

    public static void bootstrapSimple(BootstrapContext<SplashtailVariant> context)
    {
        register(context, RUBY, "ruby", 0);
        register(context, SUNNY, "sunny", 1);
        register(context, INDIGO, "indigo", 2);
        register(context, UMBER, "umber", 3, ProbabilityCondition.defaultRareProbablity().build());
        register(context, SEAFOAM, "seafoam", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSkyBelowWater()).build());
    }

    static void register(BootstrapContext<SplashtailVariant> context, ResourceKey<SplashtailVariant> key, String name, int customModelData, SpawnCondition... conditions)
    {
        register(context, key, name, customModelData, false, conditions);
    }

    static void register(BootstrapContext<SplashtailVariant> context, ResourceKey<SplashtailVariant> key, String name, int customModelData, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.id("entity/splashtail/" + name);
        var glowTexture = FishOfThieves.id("entity/splashtail/" + name + "_glow");
        context.register(key, new SplashtailVariant(name, texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions), BuiltInRegistries.ITEM.wrapAsHolder(FOTItems.SPLASHTAIL), customModelData));
    }

    private static ResourceKey<SplashtailVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.SPLASHTAIL_VARIANT, FishOfThieves.id(name));
    }
}