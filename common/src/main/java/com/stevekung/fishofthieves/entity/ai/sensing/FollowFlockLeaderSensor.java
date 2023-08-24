package com.stevekung.fishofthieves.entity.ai.sensing;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class FollowFlockLeaderSensor extends Sensor<AbstractSchoolingThievesFish<?>>
{
    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, FOTMemoryModuleTypes.NEAREST_FLOCK_LEADER, FOTMemoryModuleTypes.IS_FLOCK_LEADER);
    }

    @Override
    protected void doTick(ServerLevel level, AbstractSchoolingThievesFish<?> entity)
    {
        entity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).ifPresent(nearestVisibleLivingEntities -> this.setNearestVisibleTrophyFish(entity, nearestVisibleLivingEntities));
    }

    private void setNearestVisibleTrophyFish(LivingEntity entity, NearestVisibleLivingEntities nearbyEntities)
    {
        var optional = nearbyEntities.findClosest(livingEntity -> livingEntity instanceof AbstractSchoolingThievesFish<?>).map(LivingEntity.class::cast);
        entity.getBrain().setMemory(FOTMemoryModuleTypes.NEAREST_FLOCK_LEADER, optional);
        optional.ifPresent(abstractSchoolingThievesFish -> abstractSchoolingThievesFish.getBrain().setMemory(FOTMemoryModuleTypes.IS_FLOCK_LEADER, true));
    }
}