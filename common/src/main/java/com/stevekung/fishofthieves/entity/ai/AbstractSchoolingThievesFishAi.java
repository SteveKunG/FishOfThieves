package com.stevekung.fishofthieves.entity.ai;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.behavior.CreateFishFlock;
import com.stevekung.fishofthieves.entity.ai.behavior.FishBreaching;
import com.stevekung.fishofthieves.entity.ai.behavior.FollowFlockLeader;
import com.stevekung.fishofthieves.entity.ai.behavior.MergeOtherFlock;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;

public class AbstractSchoolingThievesFishAi
{
    private static final UniformInt TIME_BETWEEN_BREACH = UniformInt.of(900, 1600);

    public static void initMemories(AbstractSchoolingThievesFish<?> fish)
    {
        fish.getBrain().setMemory(FOTMemoryModuleTypes.SCHOOL_SIZE, 1);
        fish.getBrain().setMemory(FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS, CreateFishFlock.nextStartTick(fish.getRandom()));

        if (!fish.fromBucket())
        {
            fish.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, TIME_BETWEEN_BREACH.sample(fish.getRandom()));
        }
    }

    public static void resetMemories(AbstractSchoolingThievesFish<?> fish)
    {
        initMemories(fish);
        fish.getBrain().eraseMemory(FOTMemoryModuleTypes.IS_FLOCK_LEADER);
        fish.getBrain().eraseMemory(FOTMemoryModuleTypes.IS_FLOCK_FOLLOWER);
        fish.getBrain().eraseMemory(FOTMemoryModuleTypes.FLOCK_LEADER);
        fish.getBrain().eraseMemory(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK);
    }

    public static Brain<?> makeBrain(Brain<AbstractSchoolingThievesFish<?>> brain)
    {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initRetreatActivity(brain);
        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void updateActivity(AbstractSchoolingThievesFish<?> fish)
    {
        fish.getBrain().setActiveActivityToFirstValid(List.of(Activity.AVOID, Activity.IDLE));
    }

    public static <T extends AbstractSchoolingThievesFish<?>> void customServerAiStep(T fish, Brain<T> brain)
    {
        var name = BuiltInRegistries.ENTITY_TYPE.getKey(fish.getType()).getPath();
        fish.level().getProfiler().push(name + "Brain");
        brain.tick((ServerLevel) fish.level(), fish);
        fish.level().getProfiler().popPush(name + "ActivityUpdate");
        AbstractSchoolingThievesFishAi.updateActivity(fish);
        fish.level().getProfiler().pop();
    }

    private static void initCoreActivity(Brain<AbstractSchoolingThievesFish<?>> brain)
    {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new AnimalPanic(2.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                AbstractThievesFishAi.avoidPlayer(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new CountDownCooldownTicks(FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS),
                new CountDownCooldownTicks(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS),
                new CountDownCooldownTicks(FOTMemoryModuleTypes.BREACHED_TICK)
        ));
    }

    @SuppressWarnings("deprecation")
    private static void initIdleActivity(Brain<AbstractSchoolingThievesFish<?>> brain)
    {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(1, new RunOne<>(List.of(
                        Pair.of(AbstractThievesFishAi.avoidRepellent(), 1),
                        Pair.of(new FollowTemptation(livingEntity -> 1.25F), 1),
                        Pair.of(new CreateFishFlock(), 2),
                        Pair.of(new FollowFlockLeader(1.25f), 3),
                        Pair.of(new FishBreaching<>(TIME_BETWEEN_BREACH, 0.3F, 0.16f), 2)
                ))),
                Pair.of(2, new MergeOtherFlock()),
                Pair.of(3, new GateBehavior<>(Map.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Set.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, List.of(
                        Pair.of(RandomStroll.swim(1.0F), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(0.5F, 3), 3),
                        Pair.of(BehaviorBuilder.triggerIf(Entity::isInWaterOrBubble), 5))))));
    }

    private static void initRetreatActivity(Brain<AbstractSchoolingThievesFish<?>> brain)
    {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.AVOID, 10, ImmutableList.of(
                SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 5.0F, 12, true),
                createIdleLookBehaviors(),
                createIdleMovementBehaviors(),
                EraseMemoryIf.create(AbstractThievesFishAi::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
    }

    private static RunOne<AbstractSchoolingThievesFish<?>> createIdleLookBehaviors()
    {
        return new RunOne<>(List.of(
                Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 6.0F), 1),
                Pair.of(SetEntityLookTarget.create(8.0F), 1),
                Pair.of(new DoNothing(30, 60), 1)));
    }

    private static RunOne<AbstractSchoolingThievesFish<?>> createIdleMovementBehaviors()
    {
        return new RunOne<>(List.of(
                Pair.of(RandomStroll.swim(1.0F), 2),
                Pair.of(new DoNothing(30, 60), 1)));
    }

    public static void wasHurtBy(AbstractSchoolingThievesFish<?> fish)
    {
        var brain = fish.getBrain();

        if (fish.isFollower())
        {
            fish.getLeader().removeFollower();
            brain.setMemory(FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS, CreateFishFlock.nextStartTick(fish.getRandom(), 1200));
            brain.eraseMemory(FOTMemoryModuleTypes.IS_FLOCK_FOLLOWER);
            brain.eraseMemory(FOTMemoryModuleTypes.FLOCK_LEADER);
        }
    }
}