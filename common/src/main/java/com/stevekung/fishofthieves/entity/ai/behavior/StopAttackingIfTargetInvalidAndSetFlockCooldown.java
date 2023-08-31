package com.stevekung.fishofthieves.entity.ai.behavior;

import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;

public class StopAttackingIfTargetInvalidAndSetFlockCooldown<E extends Mob> extends StopAttackingIfTargetInvalid<E>
{
    public StopAttackingIfTargetInvalidAndSetFlockCooldown()
    {
        super(livingEntity -> false, (mob, livingEntity) ->
        {
        });
    }

    @Override
    protected void clearAttackTarget(E memoryHolder)
    {
        super.clearAttackTarget(memoryHolder);
        memoryHolder.getBrain().setMemory(FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS, CreateFishFlock.nextStartTick(memoryHolder.getRandom(), 200));
    }
}