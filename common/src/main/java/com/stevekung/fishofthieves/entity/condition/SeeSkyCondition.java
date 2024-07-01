package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

public record SeeSkyCondition() implements SpawnCondition
{
    private static final SeeSkyCondition INSTANCE = new SeeSkyCondition();
    public static final MapCodec<SeeSkyCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.SEE_SKY;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return context.level().canSeeSkyFromBelowWater(context.blockPos());
    }

    public static SpawnCondition.Builder seeSky()
    {
        return () -> INSTANCE;
    }
}