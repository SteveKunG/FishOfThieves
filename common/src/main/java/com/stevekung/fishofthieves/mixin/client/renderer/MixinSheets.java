package com.stevekung.fishofthieves.mixin.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTWoodTypes;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.level.block.state.properties.WoodType;

@Mixin(Sheets.class)
public class MixinSheets
{
    @Inject(method = "createSignSprite", cancellable = true, at = @At("HEAD"))
    private static void fishofthieves$createSignMaterial(WoodType woodType, CallbackInfoReturnable<SpriteId> info)
    {
        if (woodType == FOTWoodTypes.COCONUT)
        {
            info.setReturnValue(Sheets.SIGN_MAPPER.apply(FishOfThieves.id("coconut")));
        }
    }

    @Inject(method = "createHangingSignSprite", cancellable = true, at = @At("HEAD"))
    private static void fishofthieves$createHangingSignMaterial(WoodType woodType, CallbackInfoReturnable<SpriteId> info)
    {
        if (woodType == FOTWoodTypes.COCONUT)
        {
            info.setReturnValue(Sheets.HANGING_SIGN_MAPPER.apply(FishOfThieves.id("coconut")));
        }
    }
}