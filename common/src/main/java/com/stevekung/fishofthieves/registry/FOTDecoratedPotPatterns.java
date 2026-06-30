package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

public class FOTDecoratedPotPatterns
{
    public static final ResourceKey<DecoratedPotPattern> STORMFISH = create("stormfish");
    public static final ResourceKey<DecoratedPotPattern> KRAKEN = create("kraken");
    public static final ResourceKey<DecoratedPotPattern> MEGALODON = create("megalodon");
    public static final ResourceKey<DecoratedPotPattern> GREAT_MOUTH = create("great_mouth");

    public static void bootstrap(BootstrapContext<DecoratedPotPattern> context)
    {
        register(context, STORMFISH);
        register(context, KRAKEN);
        register(context, MEGALODON);
        register(context, GREAT_MOUTH);
    }

    private static void register(BootstrapContext<DecoratedPotPattern> registry, ResourceKey<DecoratedPotPattern> key)
    {
        registry.register(key, new DecoratedPotPattern(key.identifier().withSuffix("_pottery_pattern")));
    }

    private static ResourceKey<DecoratedPotPattern> create(String name)
    {
        return ResourceKey.create(Registries.DECORATED_POT_PATTERN, FishOfThieves.id(name));
    }
}