package com.stevekung.fishofthieves.entity.ai;

import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ai.behavior.GoToClosestShipwreck;
import com.stevekung.fishofthieves.entity.animal.Wrecker;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;

public class WreckerAi
{
    public static Brain<?> makeBrain(Brain<Wrecker> brain)
    {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void updateActivity(Wrecker fish)
    {
        if (FishOfThieves.CONFIG.general.neutralFishBehavior)
        {
            fish.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        }
        else
        {
            fish.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
        }
    }

    //@formatter:off
    private static void initCoreActivity(Brain<Wrecker> brain)
    {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new AnimalPanic(2.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)));
    }

    private static void initIdleActivity(Brain<Wrecker> brain)
    {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new RunSometimes<>(new SetEntityLookTarget(EntityType.PLAYER, 6.0F), UniformInt.of(30, 60))),
                Pair.of(1, new RunOne<>(ImmutableList.of(
                        Pair.of(new FollowTemptation(livingEntity -> 1.15F), 1),
                        Pair.of(new GoToClosestShipwreck(1.15f, 8), 2)))),
                Pair.of(2, new StartAttacking<>(WreckerAi::findNearestValidAttackTarget)),
                Pair.of(3, new GateBehavior<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, ImmutableList.of(
                        Pair.of(new RandomSwim(0.8F), 2),
                        Pair.of(new SetWalkTargetFromLookTarget(0.8F, 3), 3),
                        Pair.of(new RunIf<>(Entity::isInWaterOrBubble, new DoNothing(30, 60)), 5))))));
    }

    private static void initFightActivity(Brain<Wrecker> brain)
    {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 0, ImmutableList.of(
                new StopAttackingIfTargetInvalid<>(),
                new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.25f),
                new MeleeAttack(20)), MemoryModuleType.ATTACK_TARGET);
    }
    //@formatter:on

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Wrecker fish)
    {
        return fish.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }
}