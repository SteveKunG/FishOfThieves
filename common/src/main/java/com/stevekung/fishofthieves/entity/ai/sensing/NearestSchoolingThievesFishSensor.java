package com.stevekung.fishofthieves.entity.ai.sensing;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableSet;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

@SuppressWarnings("rawtypes")
public class NearestSchoolingThievesFishSensor extends Sensor<AbstractSchoolingThievesFish>
{
    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return ImmutableSet.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH);
    }

    @Override
    protected void doTick(ServerLevel level, AbstractSchoolingThievesFish entity)
    {
        var brain = entity.getBrain();
        Predicate<AbstractSchoolingThievesFish> predicate = fish -> fish.canBeFollowed() || !fish.isFollower();
        var list = level.getEntitiesOfClass(AbstractSchoolingThievesFish.class, entity.getBoundingBox().inflate(8.0, 8.0, 8.0), predicate.and(AbstractSchoolingThievesFish::isAlive));
        list.sort(Comparator.comparingDouble(entity::distanceToSqr));
        brain.setMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH, list);
    }
}