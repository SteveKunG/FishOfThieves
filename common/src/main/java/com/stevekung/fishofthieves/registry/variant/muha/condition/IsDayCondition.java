package com.stevekung.fishofthieves.registry.variant.muha.condition;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.LivingEntity;

public class IsDayCondition implements SpawnCondition
{
    private static final IsDayCondition INSTANCE = new IsDayCondition();
    public static final MapCodec<IsDayCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public SpawnConditionType getType()
    {
        return SpawnConditions.IS_DAY;
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