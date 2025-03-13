package com.stevekung.fishofthieves.mixin.damagesource;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.stevekung.fishofthieves.damagesource.FOTDamageSources;
import com.stevekung.fishofthieves.registry.FOTDamageTypes;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

@Mixin(DamageSources.class)
public class MixinDamageSources implements FOTDamageSources
{
    @Shadow
    DamageSource source(ResourceKey<DamageType> damageTypeKey, @Nullable Entity entity)
    {
        throw new AssertionError("Implemented via mixin");
    }

    @Override
    public DamageSource fishofthieves$fallingMango(Entity entity)
    {
        return this.source(FOTDamageTypes.MANGO, entity);
    }

    @Override
    public DamageSource fishofthieves$fallingCoconut(Entity entity)
    {
        return this.source(FOTDamageTypes.COCONUT, entity);
    }
}