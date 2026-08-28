package com.stevekung.fishofthieves.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.RandomState;

@Mixin(RandomState.class)
public interface RandomStateAccessor
{
    @Accessor
    NoiseRouter getRouter();
}