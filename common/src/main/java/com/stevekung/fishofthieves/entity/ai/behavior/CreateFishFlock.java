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
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

@SuppressWarnings({ "rawtypes", "unchecked" })
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

        if (optional.isPresent() && !(entity.isFollower() || entity.hasFollowers()))
        {
            Predicate<AbstractSchoolingThievesFish> canFollow = AbstractSchoolingThievesFish::canBeFollowed;
            Predicate<AbstractSchoolingThievesFish> hasFollowers = AbstractSchoolingThievesFish::hasFollowers;
            Predicate<AbstractSchoolingThievesFish> notFollower = Predicate.not(AbstractSchoolingThievesFish::isFollower);
            Predicate<AbstractSchoolingThievesFish> lineOfSight = entity::hasLineOfSight;
            Predicate<AbstractSchoolingThievesFish> trophy = AbstractSchoolingThievesFish::isTrophy;

            var nearestFish = optional.get().stream().filter(canFollow.and(notFollower).and(lineOfSight)).findAny();
            var leader = DataFixUtils.orElse(nearestFish, entity);

            // Select trophy to be leader first, then adds non-trophy or trophy to follower list
            if (leader.isTrophy())
            {
                leader.addThievesFishFollowers(optional.get().stream().filter(notFollower.and(hasFollowers.negate()).and(trophy.negate().or(trophy))));
            }
            else
            {
                // If leader is not trophy, tries to find new leader as trophy then add non-trophy to follower list
                Supplier<Stream<AbstractSchoolingThievesFish>> supplier = () -> optional.get().stream().filter(notFollower.and(hasFollowers.negate()));
                supplier.get().filter(trophy).findAny().ifPresentOrElse(fish -> fish.addThievesFishFollowers(supplier.get()), () -> leader.addThievesFishFollowers(supplier.get())); // if it can't find a leader, form a flock
            }
        }
    }

    @Override
    protected void stop(ServerLevel level, AbstractSchoolingThievesFish entity, long gameTime)
    {
        var brain = entity.getBrain();
        brain.setMemory(FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS, nextStartTick(entity));
    }

    public static int nextStartTick(AbstractSchoolingThievesFish entity)
    {
        return Mth.positiveCeilDiv(200 + entity.getRandom().nextInt(200) % 20, 2);
    }
}