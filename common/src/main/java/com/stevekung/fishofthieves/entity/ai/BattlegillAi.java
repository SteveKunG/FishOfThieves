package com.stevekung.fishofthieves.entity.ai;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.ai.behavior.*;
import com.stevekung.fishofthieves.entity.animal.Battlegill;
import com.stevekung.fishofthieves.entity.variant.BattlegillVariant;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import com.stevekung.fishofthieves.registry.variant.BattlegillVariants;

import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;

public class BattlegillAi
{
    private BattlegillAi() {}

    public static List<ActivityData<Battlegill>> getActivities()
    {
        return List.of(initCoreActivity(), initIdleActivity(), initFightActivity());
    }

    public static void updateActivity(Battlegill fish)
    {
        if (FishOfThieves.CONFIG.general.neutralFishBehavior)
        {
            fish.getBrain().setActiveActivityToFirstValid(List.of(Activity.FIGHT, Activity.IDLE));
        }
        else
        {
            fish.getBrain().setActiveActivityToFirstValid(List.of(Activity.IDLE));
        }
    }

    public static void initMemories(Battlegill fish)
    {
        if (!fish.fromBucket())
        {
            fish.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, AbstractThievesFishAi.TIME_BETWEEN_BREACH.sample(fish.getRandom()));
        }
    }

    private static ActivityData<Battlegill> initCoreActivity()
    {
        return ActivityData.create(Activity.CORE, 0, ImmutableList.of(
                new AnimalPanic<>(2.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new CountDownCooldownTicks(FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS),
                new CountDownCooldownTicks(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS),
                new CountDownCooldownTicks(FOTMemoryModuleTypes.BREACHED_TICK),
                new CountDownCooldownTicks(FOTMemoryModuleTypes.FOLLOW_WITH_EFFECT_COOLDOWN_TICKS)
        ));
    }

    @SuppressWarnings("deprecation")
    private static ActivityData<Battlegill> initIdleActivity()
    {
        return ActivityData.create(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(1, new RunOne<>(List.of(
                        Pair.of(AbstractThievesFishAi.avoidRepellent(), 1),
                        Pair.of(new FollowTemptation(_ -> 1.25F), 1),
                        Pair.of(new FollowLivingWithEffect(_ -> 1.25F, sourceEntity ->
                        {
                            if (!(sourceEntity instanceof ThievesFish<?> thievesFish))
                            {
                                return _ -> false;
                            }
                            return livingEntity -> isRumBattlegill(thievesFish.getVariant()) && livingEntity.hasEffect(MobEffects.NAUSEA);
                        }), 1),
                        Pair.of(new CreateFishFlock(), 2),
                        Pair.of(new FollowFlockLeader(1.25f), 3),
                        Pair.of(new FishBreaching<>(AbstractThievesFishAi.TIME_BETWEEN_BREACH, 0.2F, 0.12f), 4)
                ))),
                Pair.of(2, new StartAttackingIgnoreFlockLeader<>(BattlegillAi::findNearestValidAttackTarget)),
                Pair.of(3, new GateBehavior<>(Map.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Set.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, List.of(
                        Pair.of(RandomStroll.swim(1.0F), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(0.5F, 3), 3),
                        Pair.of(BehaviorBuilder.triggerIf(Entity::isInWater), 5))))));
    }

    private static ActivityData<Battlegill> initFightActivity()
    {
        return ActivityData.create(Activity.FIGHT, 0, ImmutableList.of(
                StopAttackingIfTargetInvalid.create((_, _) -> false, (_, _, livingEntity) -> livingEntity.getBrain().setMemory(FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS, CreateFishFlock.nextStartTick(livingEntity.getRandom(), 200)), true),
                SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.25f),
                MeleeAttack.create(20)), MemoryModuleType.ATTACK_TARGET);
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Battlegill fish)
    {
        return fish.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    @SuppressWarnings("unchecked")
    private static boolean isRumBattlegill(Object object)
    {
        return ((Holder<BattlegillVariant>) object).is(BattlegillVariants.RUM);
    }
}