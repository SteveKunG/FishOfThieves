package com.stevekung.fishofthieves.fabric.mixin.world.level.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.stevekung.fishofthieves.registry.FOTGrassColorModifier;

import net.minecraft.world.level.biome.BiomeSpecialEffects;

@Mixin(BiomeSpecialEffects.GrassColorModifier.class)
public enum MixinBiomeSpecialEffects_GrassColorModifier
{
    FISHOFTHIEVES_TROPICAL_ISLAND("fishofthieves:tropical_island")
    {
        @Override
        public int modifyColor(double x, double z, int baseColor)
        {
            return FOTGrassColorModifier.getGrassColor(x, z);
        }
    };

    @Shadow
    MixinBiomeSpecialEffects_GrassColorModifier(String name) {}

    @Shadow
    abstract int modifyColor(double x, double z, int baseColor);
}