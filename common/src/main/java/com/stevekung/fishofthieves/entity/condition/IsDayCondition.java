package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.world.entity.LivingEntity;

public class IsDayCondition implements SpawnCondition
{
    private static final IsDayCondition INSTANCE = new IsDayCondition();
    public static final MapCodec<IsDayCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.IS_DAY;
    }

    @Override
    public boolean test(LivingEntity livingEntity)
    {
        return livingEntity.level().isDay();
    }

    public static SpawnCondition.Builder isDay()
    {
        return () -> INSTANCE;
    }
}