package com.stevekung.fishofthieves.fabric.mixin.client.renderer;

import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.registry.FOTBiomes;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

@Mixin(FogRenderer.class)
public class MixinFogRenderer
{
    @Inject(method = "setupFog", at = @At(value = "FIELD", target = "net/minecraft/tags/BiomeTags.HAS_CLOSER_WATER_FOG:Lnet/minecraft/tags/TagKey;"))
    private static void fishofthieves$clearWaterFog(Camera camera, FogRenderer.FogMode fogMode, Vector4f fogColor, float renderDistance, boolean isFoggy, float partialTick, CallbackInfoReturnable<FogParameters> info, @Local FogRenderer.FogData fogData, @Local Holder<Biome> holder)
    {
        if (holder.is(FOTBiomes.TROPICAL_ISLAND))
        {
            fogData.end = 192.0F;
        }
    }
}