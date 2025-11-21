package com.stevekung.fishofthieves.entity.condition;

import net.minecraft.world.entity.LivingEntity;

public interface SpawnContextExtender
{
    default LivingEntity fishofthieves$livingEntity()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void fishofthieves$setLivingEntity(LivingEntity livingEntity)
    {
        throw new AssertionError("Implemented via mixin");
    }
}