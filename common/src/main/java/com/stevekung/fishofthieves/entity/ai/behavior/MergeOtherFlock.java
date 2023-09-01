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

    //TODO Fix merge small leader to trophy leader if found
    @Override
    protected void start(ServerLevel level, AbstractSchoolingThievesFish owner, long gameTime)
    {
        var brain = owner.getBrain();
        var mergedFromOtherFlock = brain.hasMemoryValue(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK) && brain.getMemory(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK).get();
        var optional = brain.getMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_FLOCK_LEADER);

        if (owner.isLeader() && !mergedFromOtherFlock && optional.isPresent())
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
                        // set a flag to follower that already merged from the other flock
                        follower.getBrain().setMemory(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK, true);
                        // set leader to this (executor)
                        follower.getBrain().setMemory(FOTMemoryModuleTypes.FLOCK_LEADER, owner);
                        // increased this (executor) school size by 1
                        owner.addFollower();
                        // add a follower to this (executor)
                        brain.getMemory(FOTMemoryModuleTypes.FLOCK_FOLLOWERS).get().add(follower);
                    }));

                    // reset other flock leader school size to 1
                    nearestLeader.getBrain().setMemory(FOTMemoryModuleTypes.SCHOOL_SIZE, 1);
                    // set new leader to this (executor)
                    nearestLeader.getBrain().setMemory(FOTMemoryModuleTypes.FLOCK_LEADER, owner);
                    // set is follower flag
                    nearestLeader.getBrain().setMemory(FOTMemoryModuleTypes.IS_FLOCK_FOLLOWER, true);
                    // set a flag that already merged from the other flock
                    nearestLeader.getBrain().setMemory(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK, true);
                    // increased this (executor) school size by 1
                    owner.addFollower();
                    // add a follower to this (executor)
                    brain.getMemory(FOTMemoryModuleTypes.FLOCK_FOLLOWERS).get().add(nearestLeader);
                    // erase is flock leader memory
                    nearestLeader.getBrain().eraseMemory(FOTMemoryModuleTypes.IS_FLOCK_LEADER);
                }
            }
        }
    }
}