package com.stevekung.fishofthieves.entity.ai.behavior;

import java.util.function.Function;

import com.google.common.collect.ImmutableMap;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

@SuppressWarnings("rawtypes")
public class FollowFlockLeader extends Behavior<AbstractSchoolingThievesFish>
{
    private final Function<LivingEntity, Float> speedModifier;

    public FollowFlockLeader(Function<LivingEntity, Float> function)
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
        this.speedModifier = function;
    }

    @Override
    protected boolean timedOut(long gameTime)
    {
        return false;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, AbstractSchoolingThievesFish entity, long gameTime)
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
                return !entity.getLeader().getBrain().hasMemoryValue(MemoryModuleType.IS_TEMPTED) || !entity.getLeader().getBrain().getMemory(MemoryModuleType.IS_TEMPTED).get();
            }
        }
        return false;
    }

    @Override
    protected void stop(ServerLevel level, AbstractSchoolingThievesFish entity, long gameTime)
    {
        var brain = entity.getBrain();
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
    }

    @Override
    protected void tick(ServerLevel level, AbstractSchoolingThievesFish owner, long gameTime)
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
            brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(flockLeader, false), this.getSpeedModifier(owner) * 3.0f, closeDistance));
        }
        else
        {
            brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(flockLeader, false), this.getSpeedModifier(owner), closeDistance));
        }
    }

    private float getSpeedModifier(AbstractSchoolingThievesFish entity)
    {
        return this.speedModifier.apply(entity);
    }
}