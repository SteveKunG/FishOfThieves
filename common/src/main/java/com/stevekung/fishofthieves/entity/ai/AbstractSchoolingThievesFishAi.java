package com.stevekung.fishofthieves.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.behavior.FormSchoolingFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;

public class AbstractSchoolingThievesFishAi
{
    public static void initMemories(AbstractSchoolingThievesFish<?> fish)
    {
        fish.getBrain().setMemory(FOTMemoryModuleTypes.school_size, 1);
    }

    public static Brain<?> makeBrain(Brain<AbstractSchoolingThievesFish<?>> brain)
    {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initRetreatActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void updateActivity(AbstractSchoolingThievesFish<?> fish)
    {
        fish.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.AVOID, Activity.IDLE));
    }

    //@formatter:off
    private static void initCoreActivity(Brain<AbstractSchoolingThievesFish<?>> brain)
    {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new AnimalPanic(2.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                avoidPlayer(),
                new FormSchoolingFish(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)));
    }

    private static void initIdleActivity(Brain<AbstractSchoolingThievesFish<?>> brain)
    {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new RunSometimes<>(new SetEntityLookTarget(EntityType.PLAYER, 6.0F), UniformInt.of(30, 60))),
                Pair.of(1, new RunOne<>(ImmutableList.of(Pair.of(new FollowTemptation(livingEntity -> 1.25F), 1)))),
                Pair.of(2, new GateBehavior<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, ImmutableList.of(
                        Pair.of(new RandomSwim(1.0F), 2),
                        Pair.of(new SetWalkTargetFromLookTarget(0.5F, 3), 3),
                        Pair.of(new RunIf<>(Entity::isInWaterOrBubble, new DoNothing(30, 60)), 5))))));
    }

    private static void initRetreatActivity(Brain<AbstractSchoolingThievesFish<?>> brain)
    {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.AVOID, 10, ImmutableList.of(
                SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 5.0F, 12, true),
                createIdleLookBehaviors(),
                createIdleMovementBehaviors(),
                new EraseMemoryIf<>(AbstractSchoolingThievesFishAi::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
    }

    private static RunOne<AbstractSchoolingThievesFish<?>> createIdleLookBehaviors()
    {
        return new RunOne<>(ImmutableList.of(
                Pair.of(new SetEntityLookTarget(EntityType.PLAYER, 6.0F), 1),
                Pair.of(new SetEntityLookTarget(8.0F), 1),
                Pair.of(new DoNothing(30, 60), 1)));
    }

    private static RunOne<AbstractSchoolingThievesFish<?>> createIdleMovementBehaviors()
    {
        return new RunOne<>(ImmutableList.of(
                Pair.of(new RandomSwim(1.0F), 2),
                Pair.of(new DoNothing(30, 60), 1)));
    }
    //@formatter:on

    private static CopyMemoryWithExpiry<AbstractSchoolingThievesFish<?>, LivingEntity> avoidPlayer()
    {
        return new CopyMemoryWithExpiry<>(AbstractSchoolingThievesFishAi::isNearPlayerNotCrouching, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.AVOID_TARGET, TimeUtil.rangeOfSeconds(5, 7));
    }

    private static boolean wantsToStopFleeing(AbstractSchoolingThievesFish<?> fish)
    {
        var brain = fish.getBrain();

        if (!brain.hasMemoryValue(MemoryModuleType.AVOID_TARGET))
        {
            return true;
        }
        else
        {
            var livingEntity = brain.getMemory(MemoryModuleType.AVOID_TARGET).get();

            if (livingEntity instanceof Player player)
            {
                return !brain.isMemoryValue(MemoryModuleType.NEAREST_VISIBLE_PLAYER, player);
            }
            else
            {
                return false;
            }
        }
    }

    private static boolean isNearPlayerNotCrouching(AbstractSchoolingThievesFish<?> fish)
    {
        var brain = fish.getBrain();

        if (brain.hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_PLAYER))
        {
            var player = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).get();
            return !player.isCrouching() && fish.closerThan(player, 6.0);
        }
        else
        {
            return false;
        }
    }
}