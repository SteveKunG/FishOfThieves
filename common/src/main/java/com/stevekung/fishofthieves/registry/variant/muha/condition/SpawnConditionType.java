package com.stevekung.fishofthieves.registry.variant.muha.condition;

import com.mojang.serialization.MapCodec;

public record SpawnConditionType(MapCodec<? extends SpawnCondition> codec)
{}