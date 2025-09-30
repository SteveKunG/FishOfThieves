package com.stevekung.fishofthieves.entity.ai.behavior;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DataFixUtils;
import com.stevekung.fishofthieves.entity.AbstractFlockFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class CreateFishFlock extends Behavior<AbstractFlockFish>
{
    private static final Predicate<AbstractFlockFish> CAN_BE_FOLLOWED = AbstractFlockFish::canBeFollowed;
    private static final Predicate<AbstractFlockFish> NOT_LEADER = Predicate.not(AbstractFlockFish::isLeader);
    private static final Predicate<AbstractFlockFish> NOT_FOLLOWER = Predicate.not(AbstractFlockFish::isFollower);
    private static final Predicate<AbstractFlockFish> TROPHY = AbstractFlockFish::isTrophy;
    private static final Predicate<AbstractFlockFish> HAS_NO_FOLLOW_COOLDOWN = Predicate.not(AbstractFlockFish::hasFollowCooldown);

    public CreateFishFlock()
    {
        super(ImmutableMap.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH, MemoryStatus.VALUE_PRESENT, FOTMemoryModuleTypes.SCHOOL_SIZE, MemoryStatus.VALUE_PRESENT, FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected void start(ServerLevel level, AbstractFlockFish entity, long gameTime)
    {
        var optional = entity.getBrain().getMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH);

        if (!(entity.isFollower() || entity.isLeader()) && optional.isPresent())
        {
            Predicate<AbstractFlockFish> lineOfSight = entity::hasLineOfSight;
            var nearestFish = optional.get().stream().filter(lineOfSight.and(CAN_BE_FOLLOWED).and(NOT_FOLLOWER)).findAny();
            var leader = DataFixUtils.orElse(nearestFish, entity);

            // Select trophy to be leader first, then adds non-trophy or trophy to the follower list
            if (leader.isTrophy())
            {
                leader.addThievesFishFollowers(optional.get().stream().filter(leader::isSameType).filter(lineOfSight.and(NOT_FOLLOWER).and(NOT_LEADER).and(HAS_NO_FOLLOW_COOLDOWN).and(TROPHY.negate().or(TROPHY))));
            }
            else
            {
                // If leader is not trophy, tries to find a new leader as trophy then add non-trophy to the follower list
                Supplier<Stream<AbstractFlockFish>> supplier = () -> optional.get().stream().filter(lineOfSight.and(NOT_FOLLOWER).and(NOT_LEADER));
                supplier.get().filter(TROPHY).findAny().ifPresentOrElse(fish -> fish.addThievesFishFollowers(supplier.get().filter(HAS_NO_FOLLOW_COOLDOWN.and(fish::isSameType))), () -> leader.addThievesFishFollowers(supplier.get().filter(HAS_NO_FOLLOW_COOLDOWN.and(leader::isSameType)))); // if it can't find a leader, form a flock
            }
        }
    }

    public static int nextStartTick(RandomSource randomSource)
    {
        return nextStartTick(randomSource, 200);
    }

    public static int nextStartTick(RandomSource randomSource, int nextTicks)
    {
        return Mth.positiveCeilDiv(nextTicks + randomSource.nextInt(nextTicks) % 20, 2);
    }
}