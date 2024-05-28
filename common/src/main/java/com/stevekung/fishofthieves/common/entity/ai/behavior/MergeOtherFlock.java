package com.stevekung.fishofthieves.common.entity.ai.behavior;

import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableMap;
import com.stevekung.fishofthieves.common.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.common.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

@SuppressWarnings("rawtypes")
public class MergeOtherFlock extends Behavior<AbstractSchoolingThievesFish>
{
    private static final Predicate<AbstractSchoolingThievesFish> NOT_MERGE_FROM_OTHER_FLOCK = follower -> !follower.getBrain().hasMemoryValue(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK);

    public MergeOtherFlock()
    {
        super(ImmutableMap.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_FLOCK_LEADER, MemoryStatus.VALUE_PRESENT, FOTMemoryModuleTypes.SCHOOL_SIZE, MemoryStatus.REGISTERED, FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK, MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, AbstractSchoolingThievesFish owner)
    {
        var nearestLeaderOptional = this.getNearestLeader(owner);

        if (nearestLeaderOptional.isPresent())
        {
            var nearestLeader = nearestLeaderOptional.get();

            // if this is not trophy and found the nearest leader is trophy, don't run behavior
            if (!owner.isTrophy() && nearestLeader.isTrophy())
            {
                return false;
            }
            // if both are not trophy, run behavior
            else if (!owner.isTrophy() && !nearestLeader.isTrophy())
            {
                return true;
            }
        }
        // if both are trophy, run behavior
        return true;
    }

    @Override
    protected void start(ServerLevel level, AbstractSchoolingThievesFish owner, long gameTime)
    {
        var brain = owner.getBrain();
        var mergedFromOtherFlock = brain.hasMemoryValue(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK) && brain.getMemory(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK).get();
        var nearestLeaderOptional = this.getNearestLeader(owner);

        if (owner.isLeader() && !mergedFromOtherFlock && nearestLeaderOptional.isPresent())
        {
            var nearestLeader = nearestLeaderOptional.get();
            var thisSchoolSize = brain.getMemory(FOTMemoryModuleTypes.SCHOOL_SIZE).get();
            var otherSchoolSize = nearestLeader.getBrain().getMemory(FOTMemoryModuleTypes.SCHOOL_SIZE).get();
            var combinedSchoolSize = thisSchoolSize + otherSchoolSize;

            if (combinedSchoolSize <= owner.getMaxSchoolSize())
            {
                var flockFollowersFromOtherLeader = nearestLeader.getBrain().getMemory(FOTMemoryModuleTypes.FLOCK_FOLLOWERS);

                flockFollowersFromOtherLeader.ifPresent(list -> list.stream().filter(NOT_MERGE_FROM_OTHER_FLOCK).forEach(follower ->
                {
                    // set a flag to follower that already merged from the other flock
                    follower.getBrain().setMemory(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK, true);
                    // set leader to this
                    follower.getBrain().setMemory(FOTMemoryModuleTypes.FLOCK_LEADER, owner);
                    // increased this schoolSize by 1
                    owner.addFollower();
                    // add a follower to this
                    brain.getMemory(FOTMemoryModuleTypes.FLOCK_FOLLOWERS).get().add(follower);
                }));

                // reset nearest leader schoolSize to 1
                nearestLeader.getBrain().setMemory(FOTMemoryModuleTypes.SCHOOL_SIZE, 1);
                // set new leader to this
                nearestLeader.getBrain().setMemory(FOTMemoryModuleTypes.FLOCK_LEADER, owner);
                // set is follower flag
                nearestLeader.getBrain().setMemory(FOTMemoryModuleTypes.IS_FLOCK_FOLLOWER, true);
                // set a flag that already merged from the other flock
                nearestLeader.getBrain().setMemory(FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK, true);
                // increased this schoolSize by 1
                owner.addFollower();
                // add a follower to this
                brain.getMemory(FOTMemoryModuleTypes.FLOCK_FOLLOWERS).get().add(nearestLeader);
                // erase is flock leader memory
                nearestLeader.getBrain().eraseMemory(FOTMemoryModuleTypes.IS_FLOCK_LEADER);
            }
        }
    }

    private Optional<AbstractSchoolingThievesFish> getNearestLeader(AbstractSchoolingThievesFish owner)
    {
        var brain = owner.getBrain();
        var optional = brain.getMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_FLOCK_LEADER);
        Predicate<AbstractSchoolingThievesFish> isFlockLeader = AbstractSchoolingThievesFish::isLeader;
        Predicate<AbstractSchoolingThievesFish> lineOfSight = owner::hasLineOfSight;
        return optional.get().stream().filter(fish -> fish != owner).filter(owner::isSameType).filter(isFlockLeader.and(lineOfSight).and(NOT_MERGE_FROM_OTHER_FLOCK)).findAny();
    }
}