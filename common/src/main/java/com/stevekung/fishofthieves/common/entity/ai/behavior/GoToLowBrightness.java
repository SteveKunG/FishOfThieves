package com.stevekung.fishofthieves.common.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.stevekung.fishofthieves.common.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;

public class GoToLowBrightness extends Behavior<LivingEntity>
{
    private final float speedModifier;
    private final int closeEnoughDistance;

    public GoToLowBrightness(float speedModifier, int closeEnoughDistance)
    {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, FOTMemoryModuleTypes.NEAREST_LOW_BRIGHTNESS, MemoryStatus.VALUE_PRESENT));
        this.speedModifier = speedModifier;
        this.closeEnoughDistance = closeEnoughDistance;
    }

    @Override
    protected void start(ServerLevel level, LivingEntity entity, long gameTime)
    {
        var brain = entity.getBrain();

        if (brain.hasMemoryValue(FOTMemoryModuleTypes.NEAREST_LOW_BRIGHTNESS))
        {
            brain.getMemory(FOTMemoryModuleTypes.NEAREST_LOW_BRIGHTNESS).ifPresent(blockPos -> entity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(Vec3.atCenterOf(blockPos), this.speedModifier, this.closeEnoughDistance)));
        }
    }
}