package com.stevekung.fishofthieves.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.fishofthieves.registry.FOTBiomes;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.WaterFogEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

@Mixin(WaterFogEnvironment.class)
public class MixinWaterFogEnvironment
{
    @Inject(
            method = "setupFog",
            at = @At(
                    value = "FIELD",
                    target = "net/minecraft/tags/BiomeTags.HAS_CLOSER_WATER_FOG:Lnet/minecraft/tags/TagKey;"))
    private void fishofthieves$clearWaterFog(FogData fogData, Entity entity, BlockPos blockPos, ClientLevel clientLevel, float partialTicks, DeltaTracker deltaTracker, CallbackInfo info)
    {
        if (clientLevel.getBiome(blockPos).is(FOTBiomes.TROPICAL_ISLAND))
        {
            fogData.environmentalEnd = 192.0F;
        }
    }
}