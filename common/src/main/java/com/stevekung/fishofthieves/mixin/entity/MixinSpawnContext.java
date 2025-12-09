package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.stevekung.fishofthieves.entity.condition.SpawnContextExtender;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.variant.SpawnContext;

@Mixin(SpawnContext.class)
public class MixinSpawnContext implements SpawnContextExtender
{
    @Unique
    private Entity entity;

    @Override
    public Entity fishofthieves$entity()
    {
        return this.entity;
    }

    @Override
    public void fishofthieves$setEntity(Entity entity)
    {
        this.entity = entity;
    }
}