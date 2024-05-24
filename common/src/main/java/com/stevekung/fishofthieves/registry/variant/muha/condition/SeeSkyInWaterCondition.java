package com.stevekung.fishofthieves.registry.variant.muha.condition;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.LivingEntity;

public class SeeSkyInWaterCondition implements SpawnCondition
{
    private static final SeeSkyInWaterCondition INSTANCE = new SeeSkyInWaterCondition();
    public static final MapCodec<SeeSkyInWaterCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public SpawnConditionType getType()
    {
        return SpawnConditions.SEE_SKY_IN_WATER;
    }

    @Override
    public boolean test(LivingEntity livingEntity)
    {
        return livingEntity.level().canSeeSkyFromBelowWater(livingEntity.blockPosition());
    }

    public static Builder seeSkyInWater()
    {
        return () -> INSTANCE;
    }
}