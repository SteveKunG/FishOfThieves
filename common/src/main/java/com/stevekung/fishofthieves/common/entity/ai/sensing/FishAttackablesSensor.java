package com.stevekung.fishofthieves.common.entity.ai.sensing;

import java.util.function.Predicate;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class FishAttackablesSensor extends NearestVisibleLivingEntitySensor
{
    private final Predicate<LivingEntity> targetPredicate;

    public FishAttackablesSensor(Predicate<LivingEntity> targetPredicate)
    {
        this.targetPredicate = targetPredicate;
    }

    @Override
    protected boolean isMatchingEntity(LivingEntity attacker, LivingEntity target)
    {
        return this.isClose(attacker, target) && target.isInWaterOrBubble() && this.isAttackableTarget(target) && Sensor.isEntityAttackable(attacker, target);
    }

    @Override
    protected MemoryModuleType<LivingEntity> getMemory()
    {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }

    private boolean isAttackableTarget(LivingEntity target)
    {
        return this.targetPredicate.test(target);
    }

    private boolean isClose(LivingEntity attacker, LivingEntity target)
    {
        return target.distanceToSqr(attacker) <= 64.0;
    }
}