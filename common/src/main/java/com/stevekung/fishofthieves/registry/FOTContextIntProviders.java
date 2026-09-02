package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class FOTContextIntProviders
{
    public static final ResourceKey<ContextIntProvider> COMPOSTABLE_LOW_35 = create("compostable/low_35");
    public static final ResourceKey<ContextIntProvider> COMPOSTABLE_LOW_40 = create("compostable/low_40");
    public static final ResourceKey<ContextIntProvider> COMPOSTABLE_VERY_LOW_10 = create("compostable/very_low_10");
    public static final ResourceKey<ContextIntProvider> COMPOSTABLE_VERY_LOW_15 = create("compostable/very_low_15");
    public static final ResourceKey<ContextIntProvider> COMPOSTABLE_VERY_LOW_20 = create("compostable/very_low_20");
    public static final ResourceKey<ContextIntProvider> COMPOSTABLE_VERY_LOW_25 = create("compostable/very_low_25");
    public static final ResourceKey<ContextIntProvider> COMPOSTABLE_MEDIUM_60 = create("compostable/medium_60");
    public static final ResourceKey<ContextIntProvider> COMPOSTABLE_MEDIUM_70 = create("compostable/medium_70");
    public static final ResourceKey<ContextIntProvider> COMPOSTABLE_MEDIUM_HIGH_75 = create("compostable/medium_high_75");
    public static final ResourceKey<ContextIntProvider> COMPOSTABLE_MEDIUM_HIGH_80 = create("compostable/medium_high_80");

    public static void bootstrap(BootstrapContext<ContextIntProvider> context)
    {
        var blocks = context.lookup(Registries.BLOCK);
        context.register(COMPOSTABLE_LOW_35, ContextIntProviders.compostable(blocks, 35));
        context.register(COMPOSTABLE_LOW_40, ContextIntProviders.compostable(blocks, 40));
        context.register(COMPOSTABLE_VERY_LOW_10, ContextIntProviders.compostable(blocks, 10));
        context.register(COMPOSTABLE_VERY_LOW_15, ContextIntProviders.compostable(blocks, 15));
        context.register(COMPOSTABLE_VERY_LOW_20, ContextIntProviders.compostable(blocks, 20));
        context.register(COMPOSTABLE_VERY_LOW_25, ContextIntProviders.compostable(blocks, 25));
        context.register(COMPOSTABLE_MEDIUM_60, ContextIntProviders.compostable(blocks, 60));
        context.register(COMPOSTABLE_MEDIUM_70, ContextIntProviders.compostable(blocks, 70));
        context.register(COMPOSTABLE_MEDIUM_HIGH_75, ContextIntProviders.compostable(blocks, 75));
        context.register(COMPOSTABLE_MEDIUM_HIGH_80, ContextIntProviders.compostable(blocks, 80));
    }

    private static ResourceKey<ContextIntProvider> create(String location)
    {
        return ResourceKey.create(Registries.CONTEXT_INT_PROVIDER, FishOfThieves.id(location));
    }
}