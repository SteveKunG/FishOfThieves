package com.stevekung.fishofthieves.entity.ai.behavior;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DataFixUtils;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("rawtypes")
public class FormSchoolingFish extends Behavior<AbstractSchoolingThievesFish>
{
    public FormSchoolingFish()
    {
        super(ImmutableMap.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH, MemoryStatus.VALUE_PRESENT, FOTMemoryModuleTypes.school_size, MemoryStatus.VALUE_PRESENT));
    }

    //    @Override
    //    protected boolean checkExtraStartConditions(ServerLevel level, AbstractSchoolingThievesFish owner)
    //    {
    //        if (owner.hasFollowers())
    //        {
    //            return false;
    //        }
    //        else if (owner.isFollower())
    //        {
    //            return true;
    //        }
    ////        else if (this.nextStartTick > 0)
    ////        {
    ////            --this.nextStartTick;
    ////            return false;
    ////        }
    //        else
    //        {
    //            //            this.nextStartTick = this.nextStartTick(this.mob);
    //            return owner.isFollower();
    //        }
    //    }

    //    @Override
    //    protected void stop(ServerLevel level, AbstractSchoolingThievesFish entity, long gameTime) {
    //        entity.stopFollowing();
    //    }

    @Override
    protected void start(ServerLevel level, AbstractSchoolingThievesFish entity, long gameTime)
    {
        var brain = entity.getBrain();
        var optional = entity.getBrain().getMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH);

        if (optional.isPresent())
        {
            Predicate<AbstractSchoolingThievesFish> canFollow = AbstractSchoolingThievesFish::canBeFollowed;
            Predicate<AbstractSchoolingThievesFish> hasFollowers = AbstractSchoolingThievesFish::hasFollowers;
            Predicate<AbstractSchoolingThievesFish> notFollower = Predicate.not(AbstractSchoolingThievesFish::isFollower);
            Predicate<AbstractSchoolingThievesFish> lineOfSight = entity::hasLineOfSight;
            Predicate<AbstractSchoolingThievesFish> trophy = AbstractSchoolingThievesFish::isTrophy;

            var canBeFollow = optional.get().stream().filter(canFollow.and(notFollower).and(lineOfSight)).filter(trophy.negate()).findFirst().or(() -> optional.get().stream().filter(canFollow.and(notFollower).and(lineOfSight)).findAny());
            var leader = DataFixUtils.orElse(canBeFollow, entity);
            leader.addThievesFishFollowers(optional.get().stream().filter(notFollower.and(hasFollowers.negate())));
        }
    }
}