package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.stevekung.fishofthieves.entity.condition.SpawnContextExtender;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.variant.SpawnContext;

@Mixin(SpawnContext.class)
public class MixinSpawnContext implements SpawnContextExtender
{
    @Unique
    private LivingEntity livingEntity;

    @Override
    public LivingEntity fishofthieves$livingEntity()
    {
        return this.livingEntity;
    }

    @Override
    public void fishofthieves$setLivingEntity(LivingEntity livingEntity)
    {
        this.livingEntity = livingEntity;
    }
}