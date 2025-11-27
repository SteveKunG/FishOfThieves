package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

public class NeverCondition implements SpawnCondition
{
    private static final NeverCondition INSTANCE = new NeverCondition();
    public static final MapCodec<NeverCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.NEVER;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return false;
    }

    public static Builder never()
    {
        return () -> INSTANCE;
    }
}