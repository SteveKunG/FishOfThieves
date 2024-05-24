package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
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
        register(context, RUBY, "ruby");
        register(context, SUNNY, "sunny", AllOfCondition.allOf(IsDayCondition.isDay(), SeeSkyInWaterCondition.seeSkyInWater()).build());
        register(context, INDIGO, "indigo");
        register(context, UMBER, "umber", ProbabilityCondition.defaultRareProbablity().build());
        register(context, SEAFOAM, "seafoam", true, AllOfCondition.allOf(IsNightCondition.isNight(), SeeSkyInWaterCondition.seeSkyInWater()).build());
    }

    static void register(BootstrapContext<SplashtailVariant> context, ResourceKey<SplashtailVariant> key, String name, SpawnCondition... conditions)
    {
        register(context, key, name, false, conditions);
    }

    static void register(BootstrapContext<SplashtailVariant> context, ResourceKey<SplashtailVariant> key, String name, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.res("entity/splashtail/" + name);
        var glowTexture = FishOfThieves.res("entity/splashtail/" + name + "_glow");
        context.register(key, new SplashtailVariant(texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions)));
    }

    private static ResourceKey<SplashtailVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.SPLASHTAIL_VARIANT, FishOfThieves.res(name));
    }
}