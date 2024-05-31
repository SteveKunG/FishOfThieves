package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

public class SeeSkyInWaterCondition implements SpawnCondition
{
    private static final SeeSkyInWaterCondition INSTANCE = new SeeSkyInWaterCondition();
    public static final MapCodec<SeeSkyInWaterCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.SEE_SKY_IN_WATER;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return context.level().canSeeSkyFromBelowWater(context.blockPos());
    }

    public static Builder seeSkyInWater()
    {
        return () -> INSTANCE;
    }
}