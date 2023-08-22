package com.stevekung.fishofthieves.entity.ai.sensing;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class TrophyFishSensor extends Sensor<LivingEntity>
{
    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
    }

    @Override
    protected void doTick(ServerLevel level, LivingEntity entity)
    {
        entity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).ifPresent(nearestVisibleLivingEntities -> this.setNearestVisibleTrophyFish(entity, nearestVisibleLivingEntities));
    }

    private void setNearestVisibleTrophyFish(LivingEntity entity, NearestVisibleLivingEntities nearbyEntities)
    {
        var optional = nearbyEntities.findClosest(livingEntity -> livingEntity instanceof ThievesFish<?> thievesFish && thievesFish.isTrophy()).map(LivingEntity.class::cast);
        entity.getBrain().setMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_TROPHY, optional);
    }
}