package com.stevekung.fishofthieves.entity.ai.behavior;

import java.util.Map;

import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.Bucketable;

public class FishBreaching<E extends LivingEntity> extends Behavior<E>
{
    private static final int[] STEPS_TO_CHECK = new int[] { 1, 2, 3 };
    private final float jumpVelocity;
    private final float horizontalVelocity;
    private final UniformInt chance;

    public FishBreaching(UniformInt chance, float jumpVelocity, float horizontalVelocity)
    {
        super(Map.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT, FOTMemoryModuleTypes.BREACHED_TICK, MemoryStatus.REGISTERED));
        this.jumpVelocity = jumpVelocity;
        this.horizontalVelocity = horizontalVelocity;
        this.chance = chance;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E owner)
    {
        if (owner instanceof Bucketable bucketable && bucketable.fromBucket())
        {
            return false;
        }

        var direction = owner.getMotionDirection();
        var stepX = direction.getStepX();
        var stepZ = direction.getStepZ();
        var blockPos = owner.blockPosition();

        for (var scale : STEPS_TO_CHECK)
        {
            if (!this.waterIsClear(owner, blockPos, stepX, stepZ, scale) || !this.surfaceIsClear(owner, blockPos, stepX, stepZ, scale))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void start(ServerLevel level, E entity, long gameTime)
    {
        var direction = entity.getMotionDirection();
        entity.playSound(FOTSoundEvents.FISH_JUMP);
        entity.setDeltaMovement(entity.getDeltaMovement().add(direction.getStepX() * this.horizontalVelocity, this.jumpVelocity, direction.getStepZ() * this.horizontalVelocity));
        entity.getBrain().setMemory(FOTMemoryModuleTypes.BREACHED_TICK, 20);
    }

    @Override
    protected void stop(ServerLevel level, E entity, long gameTime)
    {
        entity.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, this.chance.sample(level.random));
        entity.setXRot(0.0F);
    }

    @SuppressWarnings("deprecation")
    private boolean waterIsClear(E entity, BlockPos blockPos, int dx, int dz, int scale)
    {
        blockPos = blockPos.offset(dx * scale, 0, dz * scale);
        return entity.level().getFluidState(blockPos).is(FluidTags.WATER) && !entity.level().getBlockState(blockPos).blocksMotion();
    }

    private boolean surfaceIsClear(E entity, BlockPos blockPos, int dx, int dz, int scale)
    {
        return entity.level().getBlockState(blockPos.offset(dx * scale, 1, dz * scale)).isAir() && entity.level().getBlockState(blockPos.offset(dx * scale, 2, dz * scale)).isAir();
    }
}