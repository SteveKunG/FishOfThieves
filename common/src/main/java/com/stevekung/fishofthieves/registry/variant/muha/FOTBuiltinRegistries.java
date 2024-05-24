package com.stevekung.fishofthieves.registry.variant.muha;

import com.stevekung.fishofthieves.registry.variant.muha.condition.SpawnConditionType;
import com.stevekung.fishofthieves.registry.variant.muha.condition.SpawnConditions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTBuiltinRegistries
{
    public static final Registry<SpawnConditionType> SPAWN_CONDITION_TYPE = BuiltInRegistries.registerSimple(FOTRegistries.SPAWN_CONDITION_TYPE, registry -> SpawnConditions.ANY_OF);

    public static void init() {}
}