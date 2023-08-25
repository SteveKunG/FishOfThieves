package com.stevekung.fishofthieves.entity.ai.sensing;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DataFixUtils;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.FishData;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;

@SuppressWarnings("rawtypes")
public class NearestSchoolingThievesFishSensor extends Sensor<AbstractSchoolingThievesFish>
{
    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return ImmutableSet.of(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH);
    }

    @Override
    protected void doTick(ServerLevel level, AbstractSchoolingThievesFish entity)
    {
        var brain = entity.getBrain();
        //        var list = level.getEntitiesOfClass(ItemEntity.class, entity.getBoundingBox().inflate(32.0, 16.0, 32.0), itemEntity -> true);
        //        list.sort(Comparator.comparingDouble(entity::distanceToSqr));
        //        var optional = list.stream().filter(itemEntity -> entity.wantsToPickUp(itemEntity.getItem())).filter(itemEntity -> itemEntity.closerThan(entity, 32.0)).filter(entity::hasLineOfSight).findFirst();
        //        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, optional);

        Predicate<AbstractSchoolingThievesFish> predicate = arg -> arg.canBeFollowed() || !arg.isFollower();
        var list = level.getEntitiesOfClass(AbstractSchoolingThievesFish.class, entity.getBoundingBox().inflate(8.0, 8.0, 8.0), predicate);
        list.sort(Comparator.comparingDouble(entity::distanceToSqr));
//        var optional = list.stream().filter(AbstractSchoolingFish::canBeFollowed).filter(Predicate.not(AbstractSchoolingFish::isFollower))/*.filter(itemEntity -> itemEntity.closerThan(entity, 32.0))*/.filter(entity::hasLineOfSight).findAny();
//        var optional = DataFixUtils.orElse(list.stream().filter(AbstractSchoolingFish::canBeFollowed).filter(Predicate.not(AbstractSchoolingFish::isFollower))/*.filter(itemEntity -> itemEntity.closerThan(entity, 32.0))*/.filter(entity::hasLineOfSight).findAny(), entity);
//        System.out.println(list);
        brain.setMemory(FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH, list);

        //        AbstractSchoolingFish abstractSchoolingFish = DataFixUtils.orElse(list.stream().filter(AbstractSchoolingFish::canBeFollowed).findAny(), entity);
        //        abstractSchoolingFish.addFollowers(list.stream().filter(arg -> !arg.isFollower()));
    }
}