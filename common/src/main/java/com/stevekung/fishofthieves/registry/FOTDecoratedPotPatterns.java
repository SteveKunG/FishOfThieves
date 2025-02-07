package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;

public class FOTDecoratedPotPatterns
{
    public static final String STORMFISH_NAME = "stormfish_pottery_pattern";
    public static final String KRAKEN_NAME = "kraken_pottery_pattern";
    public static final String MEGALODON_NAME = "megalodon_pottery_pattern";

    public static final ResourceKey<DecoratedPotPattern> STORMFISH = create(STORMFISH_NAME);
    public static final ResourceKey<DecoratedPotPattern> KRAKEN = create(KRAKEN_NAME);
    public static final ResourceKey<DecoratedPotPattern> MEGALODON = create(MEGALODON_NAME);

    public static void init()
    {
        register(STORMFISH_NAME);
        register(KRAKEN_NAME);
        register(MEGALODON_NAME);
    }

    public static void putItemsToPotTexture()
    {
        DecoratedPotPatterns.ITEM_TO_POT_TEXTURE.put(FOTItems.STORMFISH_POTTERY_SHERD, FOTDecoratedPotPatterns.STORMFISH);
        DecoratedPotPatterns.ITEM_TO_POT_TEXTURE.put(FOTItems.KRAKEN_POTTERY_SHERD, FOTDecoratedPotPatterns.KRAKEN);
        DecoratedPotPatterns.ITEM_TO_POT_TEXTURE.put(FOTItems.MEGALODON_POTTERY_SHERD, FOTDecoratedPotPatterns.MEGALODON);
    }

    private static void register(String key)
    {
        Registry.register(BuiltInRegistries.DECORATED_POT_PATTERN, FishOfThieves.id(key), new DecoratedPotPattern(FishOfThieves.id(key)));
    }

    private static ResourceKey<DecoratedPotPattern> create(String name)
    {
        return ResourceKey.create(Registries.DECORATED_POT_PATTERN, FishOfThieves.id(name));
    }
}