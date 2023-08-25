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
        super(ImmutableMap.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH, MemoryStatus.VALUE_PRESENT, FOTMemoryModuleTypes.school_size, MemoryStatus.VALUE_ABSENT));
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
//        brain.setMemory(FOTMemoryModuleTypes.school_size, 1);

        var optional = entity.getBrain().getMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH);

        System.out.println(optional);
        if (optional.isPresent())
        {
            var optional2 = DataFixUtils.orElse(optional.get().stream().filter(AbstractSchoolingThievesFish::canBeFollowed).filter(Predicate.not(AbstractSchoolingThievesFish::isFollower))/*.filter(itemEntity -> itemEntity.closerThan(entity, 32.0))*/.filter(entity::hasLineOfSight).findAny(), entity);
            //noinspection unchecked
            optional2.addThievesFishFollowers(optional.get().stream().filter(Predicate.not(AbstractSchoolingThievesFish::isFollower)));
        }

        //        if (brain.hasMemoryValue(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH))
        //        {
        //            brain.getMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH).ifPresent(fish ->
        //            {
        ////                followers.limit((long)(this.getMaxSchoolSize() - this.schoolSize)).filter(abstractSchoolingFish -> abstractSchoolingFish != this).forEach(abstractSchoolingFish -> abstractSchoolingFish.startFollowing(this));
        ////                var vec3 = Vec3.atCenterOf(blockPos);
        //
        ////                entity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new Vec3(vec3.x, entity.getY(), vec3.z), this.speedModifier, this.closeEnoughDistance));
        //            });
        //        }
    }
}