package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

public class DayCondition implements SpawnCondition
{
    private static final DayCondition INSTANCE = new DayCondition();
    public static final MapCodec<DayCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.IS_DAY;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return context.level().isDay();
    }

    public static SpawnCondition.Builder day()
    {
        return () -> INSTANCE;
    }
}