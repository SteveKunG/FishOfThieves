package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public class FOTNumberProviders
{
    public static final ResourceKey<NumberProvider> COMPOSTABLE_LOW_35 = create("compostable/low_35");
    public static final ResourceKey<NumberProvider> COMPOSTABLE_LOW_40 = create("compostable/low_40");
    public static final ResourceKey<NumberProvider> COMPOSTABLE_VERY_LOW_10 = create("compostable/very_low_10");
    public static final ResourceKey<NumberProvider> COMPOSTABLE_VERY_LOW_15 = create("compostable/very_low_15");
    public static final ResourceKey<NumberProvider> COMPOSTABLE_VERY_LOW_20 = create("compostable/very_low_20");
    public static final ResourceKey<NumberProvider> COMPOSTABLE_VERY_LOW_25 = create("compostable/very_low_25");
    public static final ResourceKey<NumberProvider> COMPOSTABLE_MEDIUM_60 = create("compostable/medium_60");
    public static final ResourceKey<NumberProvider> COMPOSTABLE_MEDIUM_70 = create("compostable/medium_70");
    public static final ResourceKey<NumberProvider> COMPOSTABLE_MEDIUM_HIGH_75 = create("compostable/medium_high_75");
    public static final ResourceKey<NumberProvider> COMPOSTABLE_MEDIUM_HIGH_80 = create("compostable/medium_high_80");

    public static void bootstrap(BootstrapContext<NumberProvider> context)
    {
        context.register(COMPOSTABLE_LOW_35, NumberProviders.compostable(35));
        context.register(COMPOSTABLE_LOW_40, NumberProviders.compostable(40));
        context.register(COMPOSTABLE_VERY_LOW_10, NumberProviders.compostable(10));
        context.register(COMPOSTABLE_VERY_LOW_15, NumberProviders.compostable(15));
        context.register(COMPOSTABLE_VERY_LOW_20, NumberProviders.compostable(20));
        context.register(COMPOSTABLE_VERY_LOW_25, NumberProviders.compostable(25));
        context.register(COMPOSTABLE_MEDIUM_60, NumberProviders.compostable(60));
        context.register(COMPOSTABLE_MEDIUM_70, NumberProviders.compostable(70));
        context.register(COMPOSTABLE_MEDIUM_HIGH_75, NumberProviders.compostable(75));
        context.register(COMPOSTABLE_MEDIUM_HIGH_80, NumberProviders.compostable(80));
    }

    private static ResourceKey<NumberProvider> create(String location)
    {
        return ResourceKey.create(Registries.NUMBER_PROVIDER, FishOfThieves.id(location));
    }
}