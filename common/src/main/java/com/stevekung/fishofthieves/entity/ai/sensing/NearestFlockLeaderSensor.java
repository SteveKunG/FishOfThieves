package com.stevekung.fishofthieves.entity.ai.sensing;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

@SuppressWarnings("rawtypes")
public class NearestFlockLeaderSensor extends Sensor<AbstractSchoolingThievesFish>
{
    private static final Predicate<AbstractSchoolingThievesFish> IS_LEADER = fish -> fish.isLeader() && fish.isAlive();

    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return Set.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_FLOCK_LEADER);
    }

    @Override
    protected void doTick(ServerLevel level, AbstractSchoolingThievesFish entity)
    {
        var list = level.getEntitiesOfClass(AbstractSchoolingThievesFish.class, entity.getBoundingBox().inflate(16.0), IS_LEADER).stream().sorted(Comparator.comparingDouble(entity::distanceToSqr)).toList();
        entity.getBrain().setMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_FLOCK_LEADER, list);
    }
}