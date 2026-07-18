package com.stevekung.fishofthieves.mixin.client.screen;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTWoodTypes;

import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.gui.screens.inventory.HangingSignEditScreen;
import net.minecraft.resources.Identifier;

@Mixin(HangingSignEditScreen.class)
public abstract class MixinHangingSignEditScreen extends AbstractSignEditScreen
{
    @Shadow
    @Final
    @Mutable
    Identifier texture;

    MixinHangingSignEditScreen()
    {
        super(null, null, false);
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void fishofthieves$redirectTextureLocation(CallbackInfo info)
    {
        if (this.woodType == FOTWoodTypes.COCONUT)
        {
            this.texture = FishOfThieves.id("textures/gui/hanging_signs/coconut.png");
        }
    }
}