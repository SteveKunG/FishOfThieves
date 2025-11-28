package com.stevekung.fishofthieves.entity.ai;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.entity.AbstractThievesFish;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.ai.behavior.FishBreaching;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import com.stevekung.fishofthieves.registry.FOTMobEffects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Item;

public class AbstractThievesFishAi
{
    public static final UniformInt TIME_BETWEEN_BREACH = UniformInt.of(1200, 2000);

    public static Brain<?> makeBrain(Brain<AbstractThievesFish<?>> brain)
    {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initRetreatActivity(brain);
        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void initMemories(AbstractThievesFish<?> fish)
    {
        if (!fish.fromBucket())
        {
            fish.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, TIME_BETWEEN_BREACH.sample(fish.getRandom()));
        }
    }

    public static void updateActivity(AbstractThievesFish<?> fish)
    {
        fish.getBrain().setActiveActivityToFirstValid(List.of(Activity.AVOID, Activity.IDLE));
    }

    public static TagKey<Item> getCommonTemptations()
    {
        return ThievesFish.WORMS;
    }

    public static TagKey<Item> getLeechesTemptations()
    {
        return ThievesFish.LEECHES_FOOD;
    }

    public static TagKey<Item> getEarthwormsTemptations()
    {
        return ThievesFish.EARTHWORMS_FOOD;
    }

    public static TagKey<Item> getGrubsTemptations()
    {
        return ThievesFish.GRUBS_FOOD;
    }

    public static <T extends AbstractThievesFish<?>> void customServerAiStep(T fish, Brain<T> brain)
    {
        var name = BuiltInRegistries.ENTITY_TYPE.getKey(fish.getType()).getPath();
        var profiler = Profiler.get();
        profiler.push(name + "Brain");
        brain.tick((ServerLevel) fish.level(), fish);
        profiler.popPush(name + "ActivityUpdate");
        AbstractThievesFishAi.updateActivity(fish);
        profiler.pop();
    }

    private static void initCoreActivity(Brain<AbstractThievesFish<?>> brain)
    {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new AnimalPanic<>(2.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                avoidPlayer(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new CountDownCooldownTicks(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS),
                new CountDownCooldownTicks(FOTMemoryModuleTypes.BREACHED_TICK)
        ));
    }

    @SuppressWarnings("deprecation")
    private static void initIdleActivity(Brain<AbstractThievesFish<?>> brain)
    {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(1, new RunOne<>(List.of(
                        Pair.of(avoidRepellent(), 1),
                        Pair.of(new FollowTemptation(livingEntity -> 1.25F), 1),
                        Pair.of(new FishBreaching<>(TIME_BETWEEN_BREACH, 0.3F, 0.16f), 2)
                ))),
                Pair.of(2, new GateBehavior<>(Map.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Set.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, List.of(
                        Pair.of(RandomStroll.swim(1.0F), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(0.5F, 3), 3),
                        Pair.of(BehaviorBuilder.triggerIf(Entity::isInWater), 5))))));
    }

    private static void initRetreatActivity(Brain<AbstractThievesFish<?>> brain)
    {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.AVOID, 10, ImmutableList.of(
                SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 5.0F, 12, true),
                createIdleLookBehaviors(),
                createIdleMovementBehaviors(),
                EraseMemoryIf.create(AbstractThievesFishAi::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
    }

    private static RunOne<AbstractThievesFish<?>> createIdleLookBehaviors()
    {
        return new RunOne<>(List.of(
                Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 6.0F), 1),
                Pair.of(SetEntityLookTarget.create(8.0F), 1),
                Pair.of(new DoNothing(30, 60), 1)));
    }

    private static RunOne<AbstractThievesFish<?>> createIdleMovementBehaviors()
    {
        return new RunOne<>(List.of(
                Pair.of(RandomStroll.swim(1.0F), 2),
                Pair.of(new DoNothing(30, 60), 1)));
    }

    public static BehaviorControl<PathfinderMob> avoidRepellent()
    {
        return SetWalkTargetAwayFrom.pos(MemoryModuleType.NEAREST_REPELLENT, 5.0F, 10, true);
    }

    public static <T extends AbstractFish> boolean isPosNearNearestRepellent(T fish, BlockPos pos)
    {
        var optional = fish.getBrain().getMemory(MemoryModuleType.NEAREST_REPELLENT);
        return optional.isPresent() && optional.get().closerThan(pos, 10.0);
    }

    public static BehaviorControl<LivingEntity> avoidPlayer()
    {
        return CopyMemoryWithExpiry.create(AbstractThievesFishAi::isNearPlayerNotCrouching, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.AVOID_TARGET, TimeUtil.rangeOfSeconds(5, 7));
    }

    public static <T extends AbstractFish> boolean wantsToStopFleeing(T fish)
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

    public static <T extends LivingEntity> boolean isNearPlayerNotCrouching(T fish)
    {
        var brain = fish.getBrain();

        if (brain.hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_PLAYER))
        {
            var player = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).get();
            return !player.isCrouching() && !player.hasEffect(FOTMobEffects.GUARDIAN_STIFLE) && fish.closerThan(player, 6.0);
        }
        else
        {
            return false;
        }
    }
}