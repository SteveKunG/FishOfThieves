package com.stevekung.fishofthieves.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.stevekung.fishofthieves.entity.AbstractFlockFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class FollowFlockLeader extends Behavior<AbstractFlockFish>
{
    private final float speedModifier;

    public FollowFlockLeader(float speedModifier)
    {
        super(Util.make(() ->
        {
            var builder = ImmutableMap.<MemoryModuleType<?>, MemoryStatus>builder();
            builder.put(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED);
            builder.put(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED);
            builder.put(FOTMemoryModuleTypes.FLOCK_LEADER, MemoryStatus.VALUE_PRESENT);
            builder.put(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT);
            return builder.build();
        }));
        this.speedModifier = speedModifier;
    }

    @Override
    protected boolean timedOut(long gameTime)
    {
        return false;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, AbstractFlockFish entity, long gameTime)
    {
        var brain = entity.getBrain();

        if (brain.hasMemoryValue(MemoryModuleType.IS_PANICKING) || brain.hasMemoryValue(MemoryModuleType.AVOID_TARGET) || brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET))
        {
            return false;
        }
        else
        {
            if (entity.hasLeader())
            {
                var leader = entity.getLeader();
                return entity.hasLineOfSight(leader) && (!leader.getBrain().hasMemoryValue(MemoryModuleType.IS_TEMPTED) || !leader.getBrain().getMemory(MemoryModuleType.IS_TEMPTED).get());
            }
        }
        return false;
    }

    @Override
    protected void tick(ServerLevel level, AbstractFlockFish owner, long gameTime)
    {
        var flockLeader = owner.getLeader();
        var brain = owner.getBrain();
        var closeDistance = 2;
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(flockLeader, true));

        if (owner.distanceToSqr(flockLeader) < closeDistance)
        {
            brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        }
        else if (owner.distanceToSqr(flockLeader) > 6)
        {
            brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(flockLeader, false), this.speedModifier * 3.0f, closeDistance));
        }
        else
        {
            brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(flockLeader, false), this.speedModifier, closeDistance));
        }
    }
}