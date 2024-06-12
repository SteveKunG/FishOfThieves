package com.stevekung.fishofthieves.entity.ai.sensing;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableSet;
import com.stevekung.fishofthieves.entity.AbstractFlockFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class NearestSchoolingThievesFishSensor extends Sensor<LivingEntity>
{
    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return ImmutableSet.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH);
    }

    @Override
    protected void doTick(ServerLevel level, LivingEntity entity)
    {
        var brain = entity.getBrain();
        Predicate<AbstractFlockFish> predicate = livingEntity -> (livingEntity.canBeFollowed() || !livingEntity.isFollower()) && livingEntity.isAlive();
        var list = level.getEntitiesOfClass(AbstractFlockFish.class, entity.getBoundingBox().inflate(16.0), predicate);
        list.sort(Comparator.comparingDouble(entity::distanceToSqr));
        brain.setMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH, list);
    }
}