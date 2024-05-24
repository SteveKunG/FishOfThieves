package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.world.entity.LivingEntity;

public class IsNightCondition implements SpawnCondition
{
    private static final IsNightCondition INSTANCE = new IsNightCondition();
    public static final MapCodec<IsNightCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.IS_NIGHT;
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