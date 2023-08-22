package com.stevekung.fishofthieves.entity;

import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;

public class AbstractThievesFishAi
{
    public static Brain<?> makeBrain(Brain<AbstractThievesFish<?>> brain)
    {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initRetreatActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void updateActivity(AbstractThievesFish<?> fish)
    {
        fish.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE, Activity.AVOID));
    }

    public static Ingredient getCommonTemptations()
    {
        return ThievesFish.WORMS;
    }

    public static Ingredient getLeechesTemptations()
    {
        return ThievesFish.LEECHES_FOOD;
    }

    //@formatter:off
    private static void initCoreActivity(Brain<AbstractThievesFish<?>> brain)
    {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new AnimalPanic(2.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                avoidPlayer(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)));
    }

    private static void initIdleActivity(Brain<AbstractThievesFish<?>> brain)
    {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new RunSometimes<>(new SetEntityLookTarget(EntityType.PLAYER, 6.0F), UniformInt.of(30, 60))),
                Pair.of(1, new FollowTemptation(livingEntity -> 1.25F)),
                Pair.of(2, new RunIf<>(Predicate.not(AbstractThievesFishAi::isTempted), new GateBehavior<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, ImmutableList.of(
                        Pair.of(new RandomSwim(0.5F), 2),
                        Pair.of(new SetWalkTargetFromLookTarget(0.5F, 3), 3),
                        Pair.of(new RunIf<>(Entity::isInWaterOrBubble, new DoNothing(30, 60)), 5)))))));
    }

    private static void initRetreatActivity(Brain<AbstractThievesFish<?>> brain)
    {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.AVOID, 10, ImmutableList.of(
                SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 5.0F, 12, true),
                createIdleLookBehaviors(),
                createIdleMovementBehaviors(),
                new EraseMemoryIf<>(AbstractThievesFishAi::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
    }

    private static RunOne<AbstractThievesFish<?>> createIdleLookBehaviors()
    {
        return new RunOne<>(ImmutableList.of(
                Pair.of(new SetEntityLookTarget(EntityType.PLAYER, 6.0F), 1),
                Pair.of(new SetEntityLookTarget(8.0F), 1),
                Pair.of(new DoNothing(30, 60), 1)));
    }

    private static RunIf<AbstractThievesFish<?>> createIdleMovementBehaviors()
    {
        return new RunIf<>(Predicate.not(AbstractThievesFishAi::isTempted), new RunOne<>(ImmutableList.of(
                Pair.of(new RandomSwim(0.5F), 2),
                Pair.of(new DoNothing(30, 60), 1))));
    }
    //@formatter:on

    private static CopyMemoryWithExpiry<AbstractThievesFish<?>, LivingEntity> avoidPlayer()
    {
        return new CopyMemoryWithExpiry<>(AbstractThievesFishAi::isNearPlayer, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.AVOID_TARGET, TimeUtil.rangeOfSeconds(5, 7));
    }

    private static boolean isTempted(AbstractThievesFish<?> fish)
    {
        return fish.getBrain().hasMemoryValue(MemoryModuleType.IS_TEMPTED);
    }

    private static boolean wantsToStopFleeing(AbstractThievesFish<?> fish)
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

    private static boolean isNearPlayer(AbstractThievesFish<?> fish)
    {
        var brain = fish.getBrain();

        if (brain.hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_PLAYER))
        {
            var player = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).get();
            return fish.closerThan(player, 6.0);
        }
        else
        {
            return false;
        }
    }
}