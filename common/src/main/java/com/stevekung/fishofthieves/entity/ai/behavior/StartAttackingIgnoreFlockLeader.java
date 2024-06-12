package com.stevekung.fishofthieves.entity.ai.behavior;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableMap;
import com.stevekung.fishofthieves.entity.AbstractFlockFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class StartAttackingIgnoreFlockLeader<E extends Mob> extends Behavior<E>
{
    private final Predicate<E> canAttackPredicate;
    private final Function<E, Optional<? extends LivingEntity>> targetFinderFunction;

    public StartAttackingIgnoreFlockLeader(Predicate<E> canAttackPredicate, Function<E, Optional<? extends LivingEntity>> targetFinderFunction)
    {
        this(canAttackPredicate, targetFinderFunction, 60);
    }

    public StartAttackingIgnoreFlockLeader(Predicate<E> canAttackPredicate, Function<E, Optional<? extends LivingEntity>> targetFinderFunction, int i)
    {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED, FOTMemoryModuleTypes.FLOCK_LEADER, MemoryStatus.REGISTERED), i);
        this.canAttackPredicate = canAttackPredicate;
        this.targetFinderFunction = targetFinderFunction;
    }

    public StartAttackingIgnoreFlockLeader(Function<E, Optional<? extends LivingEntity>> targetFinderFunction)
    {
        this(mob -> true, targetFinderFunction);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E owner)
    {
        if (!this.canAttackPredicate.test(owner))
        {
            return false;
        }
        else
        {
            var optional = this.targetFinderFunction.apply(owner);
            return optional.isPresent() && owner.canAttack(optional.get());
        }
    }

    @Override
    protected void start(ServerLevel level, E entity, long gameTime)
    {
        this.targetFinderFunction.apply(entity).ifPresent(livingEntity -> setAttackTarget(entity, livingEntity));
    }

    public static <E extends Mob> void setAttackTarget(E mob, LivingEntity attackTarget)
    {
        var brain = mob.getBrain();
        brain.setMemory(MemoryModuleType.ATTACK_TARGET, attackTarget);
        brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.getMemory(FOTMemoryModuleTypes.FLOCK_LEADER).ifPresent(AbstractFlockFish::removeFollower);
        brain.eraseMemory(FOTMemoryModuleTypes.FLOCK_LEADER);
    }
}