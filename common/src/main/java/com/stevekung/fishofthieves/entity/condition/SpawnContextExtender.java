package com.stevekung.fishofthieves.entity.condition;

import net.minecraft.world.entity.Entity;

public interface SpawnContextExtender
{
    default Entity fishofthieves$entity()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void fishofthieves$setEntity(Entity entity)
    {
        throw new AssertionError("Implemented via mixin");
    }
}