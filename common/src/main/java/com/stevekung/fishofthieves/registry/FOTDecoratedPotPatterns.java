package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public class FOTDecoratedPotPatterns
{
    private static final String STORMFISH_NAME = "stormfish_pottery_pattern";
    public static final ResourceKey<String> STORMFISH = create(STORMFISH_NAME);

    public static void init()
    {
        register(STORMFISH_NAME, STORMFISH_NAME);
    }

    private static void register(String key, String pattern)
    {
        Registry.register(BuiltInRegistries.DECORATED_POT_PATTERNS, FishOfThieves.id(key), pattern);
    }

    private static ResourceKey<String> create(String name)
    {
        return ResourceKey.create(Registries.DECORATED_POT_PATTERNS, FishOfThieves.id(name));
    }
}