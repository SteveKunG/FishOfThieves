//package com.stevekung.fishofthieves.entity.ai.behavior;
//
//import java.util.Optional;
//import java.util.function.Function;
//
//import com.google.common.collect.ImmutableMap;
//import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
//import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
//import net.minecraft.Util;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.util.Mth;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.behavior.Behavior;
//import net.minecraft.world.entity.ai.behavior.EntityTracker;
//import net.minecraft.world.entity.ai.memory.MemoryModuleType;
//import net.minecraft.world.entity.ai.memory.MemoryStatus;
//import net.minecraft.world.entity.ai.memory.WalkTarget;
//
//public class FollowFlockLeader extends Behavior<AbstractSchoolingThievesFish<?>>
//{
//    private final Function<LivingEntity, Float> speedModifier;
//
//    public FollowFlockLeader(Function<LivingEntity, Float> function)
//    {
//        super(Util.make(() ->
//        {
//            var builder = ImmutableMap.<MemoryModuleType<?>, MemoryStatus>builder();
//            builder.put(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED);
//            builder.put(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED);
//            builder.put(FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT);
//            builder.put(FOTMemoryModuleTypes.IS_FLOCK_FOLLOWER, MemoryStatus.REGISTERED);
//            builder.put(FOTMemoryModuleTypes.NEAREST_FLOCK_LEADER, MemoryStatus.VALUE_PRESENT);
//            builder.put(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT);
//            return builder.build();
//        }));
//        this.speedModifier = function;
//    }
//
//    private float getSpeedModifier(AbstractSchoolingThievesFish<?> entity)
//    {
//        return this.speedModifier.apply(entity);
//    }
//
//    private Optional<LivingEntity> getFlockLeader(AbstractSchoolingThievesFish<?> entity)
//    {
//        return entity.getBrain().getMemory(FOTMemoryModuleTypes.NEAREST_FLOCK_LEADER);
//    }
//
//    private int nextStartTick(AbstractSchoolingThievesFish<?> entity)
//    {
//        return Mth.positiveCeilDiv(200 + entity.getRandom().nextInt(200) % 20, 2);
//    }
//
//    @Override
//    protected boolean timedOut(long gameTime)
//    {
//        return false;
//    }
//
//    @Override
//    protected boolean canStillUse(ServerLevel level, AbstractSchoolingThievesFish<?> entity, long gameTime)
//    {
//        return this.getFlockLeader(entity).isPresent() && !entity.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING) && !entity.getBrain().hasMemoryValue(MemoryModuleType.AVOID_TARGET);
//    }
//
//    @Override
//    protected void start(ServerLevel level, AbstractSchoolingThievesFish<?> entity, long gameTime)
//    {
//        entity.getBrain().setMemory(FOTMemoryModuleTypes.IS_FLOCK_FOLLOWER, true);
//    }
//
//    @Override
//    protected void stop(ServerLevel level, AbstractSchoolingThievesFish<?> entity, long gameTime)
//    {
//        var brain = entity.getBrain();
//        brain.setMemory(FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS, this.nextStartTick(entity));
//        brain.setMemory(FOTMemoryModuleTypes.IS_FLOCK_FOLLOWER, false);
//        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
//        brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
//    }
//
//    @Override
//    protected void tick(ServerLevel level, AbstractSchoolingThievesFish<?> owner, long gameTime)
//    {
//        var flockLeader = this.getFlockLeader(owner).get();
//        var brain = owner.getBrain();
//        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(flockLeader, true));
//
//        if (owner.distanceToSqr(flockLeader) <= 121)
//        {
//            brain.eraseMemory(MemoryModuleType.WALK_TARGET);
//        }
//        else
//        {
//            brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(flockLeader, false), this.getSpeedModifier(owner), 2));
//        }
//    }
//}