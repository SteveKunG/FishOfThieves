package com.stevekung.fishofthieves.registry.variant.muha;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.variant.muha.condition.*;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;

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

    public static Holder<SplashtailVariant> getSpawnVariant(RegistryAccess registryAccess, LivingEntity livingEntity, boolean fromBucket)
    {
        var registry = registryAccess.registryOrThrow(FOTRegistries.SPLASHTAIL_VARIANT);
        return registry.holders().filter(reference ->
        {
            var con = reference.value().conditions();
            return fromBucket || Util.allOf(con).test(livingEntity);
        }).findFirst().orElse(registry.getHolderOrThrow(RUBY));
    }

    private static ResourceKey<SplashtailVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.SPLASHTAIL_VARIANT, FishOfThieves.res(name));
    }
}