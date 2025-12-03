package com.stevekung.fishofthieves.entity.ai.behavior;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class FollowLivingWithEffect extends Behavior<PathfinderMob>
{
    private final Function<LivingEntity, Float> speedModifier;
    private final Function<LivingEntity, Double> closeEnoughDistance;
    private final Function<LivingEntity, Predicate<LivingEntity>> hasEffectPredicate;

    public FollowLivingWithEffect(Function<LivingEntity, Float> speedModifier, Function<LivingEntity, Predicate<LivingEntity>> hasEffectPredicate)
    {
        this(speedModifier, livingEntity -> 2.5, hasEffectPredicate);
    }

    public FollowLivingWithEffect(Function<LivingEntity, Float> speedModifier, Function<LivingEntity, Double> closeEnoughDistance, Function<LivingEntity, Predicate<LivingEntity>> hasEffectPredicate)
    {
        super(Map.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, FOTMemoryModuleTypes.FOLLOW_WITH_EFFECT_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT, FOTMemoryModuleTypes.IS_EFFECT_FOLLOWER, MemoryStatus.REGISTERED, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT, MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT));
        this.speedModifier = speedModifier;
        this.closeEnoughDistance = closeEnoughDistance;
        this.hasEffectPredicate = hasEffectPredicate;
    }

    protected float getSpeedModifier(PathfinderMob pathfinder)
    {
        return this.speedModifier.apply(pathfinder);
    }

    private Optional<NearestVisibleLivingEntities> getLivingEntitiesHasEffect(PathfinderMob pathfinder)
    {
        return pathfinder.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
    }

    @Override
    protected boolean timedOut(long gameTime)
    {
        return false;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, PathfinderMob entity, long gameTime)
    {
        return this.getLivingEntitiesHasEffect(entity).isPresent() && !entity.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING);
    }

    @Override
    protected void start(ServerLevel level, PathfinderMob entity, long gameTime)
    {
        entity.getBrain().setMemory(FOTMemoryModuleTypes.IS_EFFECT_FOLLOWER, true);
    }

    @Override
    protected void stop(ServerLevel level, PathfinderMob entity, long gameTime)
    {
        var brain = entity.getBrain();
        brain.setMemory(FOTMemoryModuleTypes.FOLLOW_WITH_EFFECT_COOLDOWN_TICKS, 100);
        brain.setMemory(FOTMemoryModuleTypes.IS_EFFECT_FOLLOWER, false);
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
    }

    @Override
    protected void tick(ServerLevel level, PathfinderMob owner, long gameTime)
    {
        var player = this.getLivingEntitiesHasEffect(owner).get().findClosest(this.hasEffectPredicate.apply(owner)).get();
        var brain = owner.getBrain();
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(player, true));
        double distance = this.closeEnoughDistance.apply(owner);

        if (owner.distanceToSqr(player) < Mth.square(distance))
        {
            brain.eraseMemory(MemoryModuleType.WALK_TARGET);

            if (player instanceof ServerPlayer serverPlayer)
            {
                FOTCriteriaTriggers.FOLLOW_LIVING_WITH_EFFECT.trigger(serverPlayer, owner);
            }
        }
        else
        {
            brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(player, false), this.getSpeedModifier(owner), 2));
        }
    }
}