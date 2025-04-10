package com.stevekung.fishofthieves.mixin.datafix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.datafix.fixes.BoatSplitFix;

@Mixin(BoatSplitFix.class)
public class MixinBoatSplitFix
{
    @Inject(method = "mapVariantToNormalBoat", cancellable = true, at = @At("HEAD"))
    private static void fishofthieves$mapNormalBoat(String variant, CallbackInfoReturnable<String> info)
    {
        if (variant.equals("fishofthieves_coconut"))
        {
            info.setReturnValue("fishofthieves:coconut_boat");
        }
    }

    @Inject(method = "mapVariantToChestBoat", cancellable = true, at = @At("HEAD"))
    private static void fishofthieves$mapChestBoat(String variant, CallbackInfoReturnable<String> info)
    {
        if (variant.equals("fishofthieves_coconut"))
        {
            info.setReturnValue("fishofthieves:coconut_chest_boat");
        }
    }
}