package com.stevekung.fishofthieves.entity.ai.behavior;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DataFixUtils;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CreateFishFlock extends Behavior<AbstractSchoolingThievesFish>
{
    public CreateFishFlock()
    {
        super(ImmutableMap.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH, MemoryStatus.VALUE_PRESENT, FOTMemoryModuleTypes.SCHOOL_SIZE, MemoryStatus.VALUE_PRESENT, FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected void start(ServerLevel level, AbstractSchoolingThievesFish entity, long gameTime)
    {
        var optional = entity.getBrain().getMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH);

        if (!(entity.isFollower() || entity.isLeader()) && optional.isPresent())
        {
            Predicate<AbstractSchoolingThievesFish> canBeFollowed = AbstractSchoolingThievesFish::canBeFollowed;
            Predicate<AbstractSchoolingThievesFish> notLeader = Predicate.not(AbstractSchoolingThievesFish::isLeader);
            Predicate<AbstractSchoolingThievesFish> notFollower = Predicate.not(AbstractSchoolingThievesFish::isFollower);
            Predicate<AbstractSchoolingThievesFish> lineOfSight = entity::hasLineOfSight;
            Predicate<AbstractSchoolingThievesFish> trophy = AbstractSchoolingThievesFish::isTrophy;
            Predicate<AbstractSchoolingThievesFish> hasNoFollowCooldown = Predicate.not(AbstractSchoolingThievesFish::hasFollowCooldown);

            var nearestFish = optional.get().stream().filter(lineOfSight.and(canBeFollowed).and(notFollower)).findAny();
            var leader = DataFixUtils.orElse(nearestFish, entity);

            // Select trophy to be leader first, then adds non-trophy or trophy to the follower list
            if (leader.isTrophy())
            {
                leader.addThievesFishFollowers(optional.get().stream().filter(leader::isSameType).filter(lineOfSight.and(notFollower).and(notLeader).and(hasNoFollowCooldown).and(trophy.negate().or(trophy))));
            }
            else
            {
                // If leader is not trophy, tries to find a new leader as trophy then add non-trophy to the follower list
                Supplier<Stream<AbstractSchoolingThievesFish>> supplier = () -> optional.get().stream().filter(lineOfSight.and(notFollower).and(notLeader));
                supplier.get().filter(trophy).findAny().ifPresentOrElse(fish -> fish.addThievesFishFollowers(supplier.get().filter(hasNoFollowCooldown.and(fish::isSameType))), () -> leader.addThievesFishFollowers(supplier.get().filter(hasNoFollowCooldown.and(leader::isSameType)))); // if it can't find a leader, form a flock
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