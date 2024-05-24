package com.stevekung.fishofthieves.registry.variant.muha;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.variant.muha.condition.SpawnCondition;
import com.stevekung.fishofthieves.registry.variant.muha.condition.SpawnConditionType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FOTRegistries
{
    public static final ResourceKey<Registry<SpawnCondition>> SPAWN_CONDITION = createRegistryKey("spawn_condition");

    public static final ResourceKey<Registry<SpawnConditionType>> SPAWN_CONDITION_TYPE = createRegistryKey("spawn_condition_type");

    public static final ResourceKey<Registry<SplashtailVariant>> SPLASHTAIL_VARIANT = createRegistryKey("splashtail_variant");

    public static void init() {}

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String name)
    {
        return ResourceKey.createRegistryKey(FishOfThieves.res(name));
    }
}