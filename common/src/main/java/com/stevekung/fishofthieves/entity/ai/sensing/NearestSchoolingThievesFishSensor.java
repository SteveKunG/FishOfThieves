package com.stevekung.fishofthieves.entity.ai.sensing;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import com.stevekung.fishofthieves.entity.AbstractFlockFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class NearestSchoolingThievesFishSensor extends Sensor<LivingEntity>
{
    private static final Predicate<AbstractFlockFish> CAN_BE_FOLLOWED = fish -> (fish.canBeFollowed() || !fish.isFollower()) && fish.isAlive();

    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return Set.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH);
    }

    @Override
    protected void doTick(ServerLevel level, LivingEntity entity)
    {
        var list = level.getEntitiesOfClass(AbstractFlockFish.class, entity.getBoundingBox().inflate(16.0), CAN_BE_FOLLOWED).stream().sorted(Comparator.comparingDouble(entity::distanceToSqr)).toList();
        entity.getBrain().setMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH, list);
    }
}