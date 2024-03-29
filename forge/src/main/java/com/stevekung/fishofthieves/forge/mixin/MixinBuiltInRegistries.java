package com.stevekung.fishofthieves.forge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

@Mixin(BuiltInRegistries.class)
public class MixinBuiltInRegistries
{
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void fishofthieves$initEarlyRegistries(CallbackInfo info)
    {
        FishOfThieves.LOGGER.warn("This is the stupidest thing I've ever made in Minecraft modding history...");
        FOTRegistry.forge();
    }
}