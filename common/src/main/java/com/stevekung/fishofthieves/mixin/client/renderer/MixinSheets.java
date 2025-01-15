package com.stevekung.fishofthieves.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTWoodTypes;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.properties.WoodType;

@Mixin(Sheets.class)
public class MixinSheets
{
    @Inject(method = "createSignMaterial", cancellable = true, at = @At("HEAD"))
    private static void fishofthieves$createSignMaterial(WoodType woodType, CallbackInfoReturnable<Material> info)
    {
        if (woodType == FOTWoodTypes.COCONUT)
        {
            info.setReturnValue(new Material(Sheets.SIGN_SHEET, FishOfThieves.id("entity/signs/coconut")));
        }
    }

    @Inject(method = "createHangingSignMaterial", cancellable = true, at = @At("HEAD"))
    private static void fishofthieves$createHangingSignMaterial(WoodType woodType, CallbackInfoReturnable<Material> info)
    {
        if (woodType == FOTWoodTypes.COCONUT)
        {
            info.setReturnValue(new Material(Sheets.SIGN_SHEET, FishOfThieves.id("entity/signs/hanging/coconut")));
        }
    }
}