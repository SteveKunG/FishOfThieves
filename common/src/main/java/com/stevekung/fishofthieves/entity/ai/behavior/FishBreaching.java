package com.stevekung.fishofthieves.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;


//Code อาจจะยังไม่สมบูรณ์ อาจจะ Refactor ได้ดีกว่านี้
public class FishBreaching extends Behavior<LivingEntity>
{
    protected final float maxbreachVelocity;
    private final UniformInt breachChane;
    private static final int[] STEPS_TO_CHECK = new int[]{0, 1, 4};

    public FishBreaching(UniformInt breachChane, float maxbreachVelocity)
    {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT));
        this.maxbreachVelocity = maxbreachVelocity;
        this.breachChane = breachChane;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, LivingEntity owner)
    {
        Direction direction = owner.getMotionDirection();
        int i = direction.getStepX();
        int j = direction.getStepZ();
        BlockPos blockPos = owner.blockPosition();
        boolean bl = owner.isInWater();
        if (!bl)
        {
            owner.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, this.breachChane.sample(level.random));
        }
        for (int k : STEPS_TO_CHECK)
        {
            boolean bl2 = !this.waterIsClear(owner, blockPos, i, j, k) || !this.surfaceIsClear(owner, blockPos, i, j, k);
            return bl && !bl2;
        }
        return false;
    }

    private boolean waterIsClear(LivingEntity entity, BlockPos pos, int dx, int dz, int scale)
    {
        BlockPos blockPos = pos.offset(dx * scale, 0, dz * scale);
        return entity.level().getFluidState(blockPos).is(FluidTags.WATER) && !entity.level().getBlockState(blockPos).blocksMotion();
    }

    private boolean surfaceIsClear(LivingEntity entity, BlockPos pos, int dx, int dz, int scale)
    {
        return entity.level().getBlockState(pos.offset(dx * scale, 1, dz * scale)).isAir() && entity.level().getBlockState(pos.offset(dx * scale, 2, dz * scale)).isAir();
    }

    @Override
    protected void start(ServerLevel level, LivingEntity entity, long gameTime)
    {
        Direction direction = entity.getMotionDirection();
        //Maybe set to new 'Breach' pose TODO
        entity.setPose(Pose.SWIMMING);
        entity.playSound(SoundEvents.DOLPHIN_JUMP);
        entity.setDeltaMovement(entity.getDeltaMovement().add(direction.getStepX() * 0.02, this.maxbreachVelocity, direction.getStepZ() * 0.02));
    }

    @Override
    protected void stop(ServerLevel level, LivingEntity entity, long gameTime)
    {
        entity.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, this.breachChane.sample(level.random));
    }

    @Override
    protected void tick(ServerLevel level, LivingEntity owner, long gameTime)
    {
        Vec3 vec3 = owner.getDeltaMovement();
        if (vec3.y * vec3.y < 0.03F && owner.getXRot() != 0.0F)
        {
            owner.setXRot(Mth.rotLerp(0.2F, owner.getXRot(), 0.0F));
        } else if (vec3.length() > 1.0E-5F)
        {
            double d = vec3.horizontalDistance();
            double e = Math.atan2(-vec3.y, d) * 180.0F / (float) Math.PI;
            owner.setXRot((float) e);
        }
    }
}
