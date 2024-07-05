package com.stevekung.fishofthieves.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;

public class GoToClosestWreckerLocated extends Behavior<LivingEntity>
{
    private final float speedModifier;
    private final int closeEnoughDistance;

    public GoToClosestWreckerLocated(float speedModifier, int closeEnoughDistance)
    {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, FOTMemoryModuleTypes.NEAREST_WRECKER_LOCATED, MemoryStatus.VALUE_PRESENT));
        this.speedModifier = speedModifier;
        this.closeEnoughDistance = closeEnoughDistance;
    }

    @Override
    protected void start(ServerLevel level, LivingEntity entity, long gameTime)
    {
        var brain = entity.getBrain();

        if (brain.hasMemoryValue(FOTMemoryModuleTypes.NEAREST_WRECKER_LOCATED))
        {
            brain.getMemory(FOTMemoryModuleTypes.NEAREST_WRECKER_LOCATED).ifPresent(blockPos ->
            {
                var vec3 = Vec3.atCenterOf(blockPos);
                entity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new Vec3(vec3.x, entity.getY(), vec3.z), this.speedModifier, this.closeEnoughDistance));
            });
        }
    }
}