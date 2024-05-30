package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

public class NightCondition implements SpawnCondition
{
    private static final NightCondition INSTANCE = new NightCondition();
    public static final MapCodec<NightCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.IS_NIGHT;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return context.level().isNight();
    }

    public static Builder night()
    {
        return () -> INSTANCE;
    }
}