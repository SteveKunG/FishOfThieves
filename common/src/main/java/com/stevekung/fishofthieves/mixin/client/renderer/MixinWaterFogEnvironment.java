package com.stevekung.fishofthieves.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.registry.FOTBiomes;

import net.minecraft.client.renderer.fog.environment.WaterFogEnvironment;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;

@Mixin(WaterFogEnvironment.class)
public class MixinWaterFogEnvironment
{
    @Inject(method = "method_75471(Lnet/minecraft/core/Holder;)Lnet/minecraft/world/phys/Vec3;", cancellable = true, at = @At("HEAD"))
    private static void fishofthieves$clearWaterFog(Holder<Biome> holder, CallbackInfoReturnable<Vec3> info)
    {
        //TODO Test fog value
        if (holder.is(FOTBiomes.TROPICAL_ISLAND))
        {
            info.setReturnValue(new Vec3(1.25, 0.0, 0.0));
        }
    }
}