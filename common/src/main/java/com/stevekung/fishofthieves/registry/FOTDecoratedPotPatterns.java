package com.stevekung.fishofthieves.registry;

import java.util.function.BiConsumer;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

public class FOTDecoratedPotPatterns
{
    public static final String STORMFISH_NAME = "stormfish_pottery_pattern";
    public static final String KRAKEN_NAME = "kraken_pottery_pattern";
    public static final String MEGALODON_NAME = "megalodon_pottery_pattern";
    public static final String GREAT_MOUTH_NAME = "great_mouth_pottery_pattern";

    public static final ResourceKey<DecoratedPotPattern> STORMFISH = create(STORMFISH_NAME);
    public static final ResourceKey<DecoratedPotPattern> KRAKEN = create(KRAKEN_NAME);
    public static final ResourceKey<DecoratedPotPattern> MEGALODON = create(MEGALODON_NAME);
    public static final ResourceKey<DecoratedPotPattern> GREAT_MOUTH = create(GREAT_MOUTH_NAME);

    public static void init()
    {
        register(STORMFISH_NAME);
        register(KRAKEN_NAME);
        register(MEGALODON_NAME);
        register(GREAT_MOUTH_NAME);
    }

    public static void putItemsToPotTexture(BiConsumer<ResourceKey<Item>, ResourceKey<DecoratedPotPattern>> itemToPattern)
    {
        itemToPattern.accept(FOTItemIds.STORMFISH_POTTERY_SHERD, FOTDecoratedPotPatterns.STORMFISH);
        itemToPattern.accept(FOTItemIds.KRAKEN_POTTERY_SHERD, FOTDecoratedPotPatterns.KRAKEN);
        itemToPattern.accept(FOTItemIds.MEGALODON_POTTERY_SHERD, FOTDecoratedPotPatterns.MEGALODON);
        itemToPattern.accept(FOTItemIds.GREAT_MOUTH_POTTERY_SHERD, FOTDecoratedPotPatterns.GREAT_MOUTH);
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