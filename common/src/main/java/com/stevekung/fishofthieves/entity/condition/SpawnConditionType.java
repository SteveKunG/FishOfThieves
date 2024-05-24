package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;

public record SpawnConditionType(MapCodec<? extends SpawnCondition> codec)
{}