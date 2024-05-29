package com.stevekung.fishofthieves.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.entity.AbstractThievesFish;
import com.stevekung.fishofthieves.entity.ThievesFish;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
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
import net.minecraft.world.entity.animal.AbstractFish;
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
        fish.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.AVOID, Activity.IDLE));
    }

    public static Ingredient getCommonTemptations()
    {
        return ThievesFish.WORMS;
    }

    public static Ingredient getLeechesTemptations()
    {
        return ThievesFish.LEECHES_FOOD;
    }

    public static Ingredient getEarthwormsTemptations()
    {
        return ThievesFish.EARTHWORMS_FOOD;
    }

    public static Ingredient getGrubsTemptations()
    {
        return ThievesFish.GRUBS_FOOD;
    }

    public static <T extends AbstractThievesFish<?>> void customServerAiStep(T fish, Brain<T> brain)
    {
        var name = BuiltInRegistries.ENTITY_TYPE.getKey(fish.getType()).getPath();
        fish.level().getProfiler().push(name + "Brain");
        brain.tick((ServerLevel) fish.level(), fish);
        fish.level().getProfiler().popPush(name + "ActivityUpdate");
        AbstractThievesFishAi.updateActivity(fish);
        fish.level().getProfiler().pop();
    }

    //@formatter:off
    private static void initCoreActivity(Brain<AbstractThievesFish<?>> brain)
    {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new AnimalPanic<>(2.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                avoidPlayer(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)));
    }

    @SuppressWarnings("deprecation")
    private static void initIdleActivity(Brain<AbstractThievesFish<?>> brain)
    {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(1, new RunOne<>(ImmutableList.of(
                        Pair.of(avoidRepellent(), 1),
                        Pair.of(new FollowTemptation(livingEntity -> 1.25F), 1)))),
                Pair.of(2, new GateBehavior<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, ImmutableList.of(
                        Pair.of(RandomStroll.swim(1.0F), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(0.5F, 3), 3),
                        Pair.of(BehaviorBuilder.triggerIf(Entity::isInWaterOrBubble), 5))))));
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
        return new RunOne<>(ImmutableList.of(
                Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 6.0F), 1),
                Pair.of(SetEntityLookTarget.create(8.0F), 1),
                Pair.of(new DoNothing(30, 60), 1)));
    }

    private static RunOne<AbstractThievesFish<?>> createIdleMovementBehaviors()
    {
        return new RunOne<>(ImmutableList.of(
                Pair.of(RandomStroll.swim(1.0F), 2),
                Pair.of(new DoNothing(30, 60), 1)));
    }
    //@formatter:on

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
            return !player.isCrouching() && fish.closerThan(player, 6.0);
        }
        else
        {
            return false;
        }
    }
}