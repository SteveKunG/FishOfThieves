package com.stevekung.fishofthieves.damagesource;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public interface FOTDamageSources
{
    default DamageSource fallingMango(Entity entity)
    {
        throw new AssertionError("Implemented via mixin");
    }
}