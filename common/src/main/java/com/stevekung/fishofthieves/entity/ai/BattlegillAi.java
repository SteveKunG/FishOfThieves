package com.stevekung.fishofthieves.entity.ai;

import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ai.behavior.CreateFishFlock;
import com.stevekung.fishofthieves.entity.ai.behavior.FollowFlockLeader;
import com.stevekung.fishofthieves.entity.ai.behavior.StartAttackingIgnoreFlockLeader;
import com.stevekung.fishofthieves.entity.ai.behavior.StopAttackingIfTargetInvalidAndSetFlockCooldown;
import com.stevekung.fishofthieves.entity.animal.Battlegill;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;

public class BattlegillAi
{
    public static Brain<?> makeBrain(Brain<Battlegill> brain)
    {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void updateActivity(Battlegill fish)
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
    private static void initCoreActivity(Brain<Battlegill> brain)
    {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new AnimalPanic(2.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new CountDownCooldownTicks(FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS)));
    }

    private static void initIdleActivity(Brain<Battlegill> brain)
    {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new RunSometimes<>(new SetEntityLookTarget(EntityType.PLAYER, 6.0F), UniformInt.of(30, 60))),
                Pair.of(1, new RunOne<>(ImmutableList.of(
                        Pair.of(AbstractThievesFishAi.avoidRepellent(), 1),
                        Pair.of(new FollowTemptation(livingEntity -> 1.25F), 1),
                        Pair.of(new CreateFishFlock(), 2),
                        Pair.of(new FollowFlockLeader(1.25f), 3)))),
                Pair.of(2, new StartAttackingIgnoreFlockLeader<>(BattlegillAi::findNearestValidAttackTarget)),
                Pair.of(3, new GateBehavior<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, ImmutableList.of(
                        Pair.of(new RandomSwim(1.0F), 2),
                        Pair.of(new SetWalkTargetFromLookTarget(0.5F, 3), 3),
                        Pair.of(new RunIf<>(Entity::isInWaterOrBubble, new DoNothing(30, 60)), 5))))));
    }

    private static void initFightActivity(Brain<Battlegill> brain)
    {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 0, ImmutableList.of(
                new StopAttackingIfTargetInvalidAndSetFlockCooldown<>(),
                new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.25f),
                new MeleeAttack(20)), MemoryModuleType.ATTACK_TARGET);
    }
    //@formatter:on

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Battlegill fish)
    {
        return fish.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }
}