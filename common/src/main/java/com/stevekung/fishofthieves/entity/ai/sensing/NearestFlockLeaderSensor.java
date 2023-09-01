package com.stevekung.fishofthieves.entity.ai.sensing;

import java.util.Comparator;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

@SuppressWarnings("rawtypes")
public class NearestFlockLeaderSensor extends Sensor<AbstractSchoolingThievesFish>
{
    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return ImmutableSet.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_FLOCK_LEADER);
    }

    @Override
    protected void doTick(ServerLevel level, AbstractSchoolingThievesFish entity)
    {
        var list = level.getEntitiesOfClass(AbstractSchoolingThievesFish.class, entity.getBoundingBox().inflate(16.0), fish -> fish.isLeader() && fish.isAlive());
        list.sort(Comparator.comparingDouble(entity::distanceToSqr));
        entity.getBrain().setMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_FLOCK_LEADER, list);
    }
}