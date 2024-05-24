package com.stevekung.fishofthieves.registry.variant.muha.condition;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.LivingEntity;

public class IsNightCondition implements SpawnCondition
{
    private static final IsNightCondition INSTANCE = new IsNightCondition();
    public static final MapCodec<IsNightCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public SpawnConditionType getType()
    {
        return SpawnConditions.IS_NIGHT;
    }

    @Override
    public boolean test(LivingEntity livingEntity)
    {
        return livingEntity.level().isNight();
    }

    public static Builder isNight()
    {
        return () -> INSTANCE;
    }
}