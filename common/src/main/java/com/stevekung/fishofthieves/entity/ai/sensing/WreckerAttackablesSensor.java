package com.stevekung.fishofthieves.entity.ai.sensing;

import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class WreckerAttackablesSensor extends NearestVisibleLivingEntitySensor
{
    @Override
    protected boolean isMatchingEntity(LivingEntity attacker, LivingEntity target)
    {
        return this.isClose(attacker, target) && target.isInWaterOrBubble() && this.isAttackableTarget(target) && Sensor.isEntityAttackable(attacker, target);
    }

    private boolean isAttackableTarget(LivingEntity target)
    {
        return target.getType().is(FOTTags.EntityTypes.WRECKER_ATTACKABLE);
    }

    private boolean isClose(LivingEntity attacker, LivingEntity target)
    {
        return target.distanceToSqr(attacker) <= 64.0;
    }

    @Override
    protected MemoryModuleType<LivingEntity> getMemory()
    {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}