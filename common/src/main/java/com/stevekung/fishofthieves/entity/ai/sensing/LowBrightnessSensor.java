package com.stevekung.fishofthieves.entity.ai.sensing;

import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class LowBrightnessSensor extends Sensor<LivingEntity>
{
    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return ImmutableSet.of(FOTMemoryModuleTypes.NEAREST_LOW_BRIGHTNESS);
    }

    @Override
    protected void doTick(ServerLevel level, LivingEntity entity)
    {
        var brain = entity.getBrain();
        brain.setMemory(FOTMemoryModuleTypes.NEAREST_LOW_BRIGHTNESS, findNearestLowLight(level, entity));
    }

    private static Optional<BlockPos> findNearestLowLight(ServerLevel level, LivingEntity livingEntity)
    {
        return BlockPos.findClosestMatch(livingEntity.blockPosition(), 8, 8, blockPos -> isLowLight(level, blockPos));
    }

    private static boolean isLowLight(ServerLevel level, BlockPos blockPos)
    {
        return level.getFluidState(blockPos).is(FluidTags.WATER) && level.getRawBrightness(blockPos, 0) <= 5;
    }
}