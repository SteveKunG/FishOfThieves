package com.stevekung.fishofthieves.entity.ai.behavior;

import java.util.function.Predicate;

import com.google.common.collect.ImmutableMap;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

@SuppressWarnings("rawtypes")
public class MergeOtherFlock extends Behavior<AbstractSchoolingThievesFish>
{
    public MergeOtherFlock()
    {
        super(ImmutableMap.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_FLOCK_LEADER, MemoryStatus.VALUE_PRESENT, FOTMemoryModuleTypes.SCHOOL_SIZE, MemoryStatus.REGISTERED, FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK, MemoryStatus.VALUE_ABSENT));
    }

    //TODO Fix sometime flock not merged properly, they are swapped between flock ;-;
    @Override
    protected void start(ServerLevel level, AbstractSchoolingThievesFish owner, long gameTime)
    {
        var brain = owner.getBrain();
        var optional = brain.getMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_FLOCK_LEADER);

        if (owner.isLeader() && optional.isPresent())
        {
            Predicate<AbstractSchoolingThievesFish> isFlockLeader = AbstractSchoolingThievesFish::isLeader;
            Predicate<AbstractSchoolingThievesFish> notMergeFromOtherFlock = follower -> !follower.getBrain().hasMemoryValue(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK);
            Predicate<AbstractSchoolingThievesFish> lineOfSight = owner::hasLineOfSight;
            var nearestLeaderOptional = optional.get().stream().filter(fish -> fish != owner).filter(fish -> fish.getType() == owner.getType()).filter(isFlockLeader.and(lineOfSight).and(notMergeFromOtherFlock)).findAny();

            if (nearestLeaderOptional.isPresent())
            {
                var nearestLeader = nearestLeaderOptional.get();
                var thisSchoolSize = brain.getMemory(FOTMemoryModuleTypes.SCHOOL_SIZE).get();
                var otherSchoolSize = nearestLeader.getBrain().getMemory(FOTMemoryModuleTypes.SCHOOL_SIZE).get();
                var combinedSchoolSize = thisSchoolSize + otherSchoolSize;

                if (combinedSchoolSize <= owner.getMaxSchoolSize())
                {
                    var flockFollowersFromOtherLeader = nearestLeader.getBrain().getMemory(FOTMemoryModuleTypes.FLOCK_FOLLOWERS);

                    flockFollowersFromOtherLeader.ifPresent(list -> list.stream().filter(notMergeFromOtherFlock).forEach(follower ->
                    {
                        // follower set leader to this owner (executor)
                        follower.getBrain().setMemory(FOTMemoryModuleTypes.FLOCK_LEADER, owner);

                        // set a flag when merged from the other flock
                        follower.getBrain().setMemory(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK, true);

                        // increased owner (executor) school size by 1
                        owner.addFollower();

                        // decreased the nearest leader school size by 1
                        nearestLeader.removeFollower(false);

                        // check if the nearest leader has school size equals 1
                        if (nearestLeader.getSchoolSize() == 1)
                        {
                            // set the nearest leader school size to 1
                            nearestLeader.getBrain().setMemory(FOTMemoryModuleTypes.SCHOOL_SIZE, 1);

                            // owner (executor) increases school size by 1
                            owner.addFollower();

                            // set leader to this (executor)
                            nearestLeader.getBrain().setMemory(FOTMemoryModuleTypes.FLOCK_LEADER, owner);

                            // set a flag when merged from the other flock
                            nearestLeader.getBrain().setMemory(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK, true);

                            // erase IS_FLOCK_LEADER memory
                            nearestLeader.getBrain().eraseMemory(FOTMemoryModuleTypes.IS_FLOCK_LEADER);
                        }
                    }));
                }
            }
        }
    }
}