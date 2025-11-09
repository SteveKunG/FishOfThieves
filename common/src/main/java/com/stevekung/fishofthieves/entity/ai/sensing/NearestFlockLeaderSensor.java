package com.stevekung.fishofthieves.entity.ai.sensing;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import com.stevekung.fishofthieves.entity.AbstractFlockFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class NearestFlockLeaderSensor extends Sensor<AbstractFlockFish>
{
    private static final Predicate<AbstractFlockFish> IS_LEADER = fish -> fish.isLeader() && fish.isAlive();

    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return Set.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_FLOCK_LEADER);
    }

    @Override
    protected void doTick(ServerLevel level, AbstractFlockFish livingEntity)
    {
        var list = level.getEntitiesOfClass(AbstractFlockFish.class, livingEntity.getBoundingBox().inflate(16.0), IS_LEADER).stream().sorted(Comparator.comparingDouble(livingEntity::distanceToSqr)).toList();
        livingEntity.getBrain().setMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_FLOCK_LEADER, list);
    }
}