package com.stevekung.fishofthieves.fabric.asm;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.level.biome.BiomeSpecialEffects;

@Mixin(BiomeSpecialEffects.GrassColorModifier.class)
public abstract class FOTGrassModifier
{
    @Shadow
    public abstract int modifyColor(double x, double z, int grassColor);
}