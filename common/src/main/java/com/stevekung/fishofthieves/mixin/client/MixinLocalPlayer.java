package com.stevekung.fishofthieves.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.blockentity.FOTHangingSignBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.HangingSignEditScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.block.entity.SignBlockEntity;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer
{
    @Shadow
    @Final
    Minecraft minecraft;

    @Inject(method = "openTextEdit", cancellable = true, at = @At("HEAD"))
    private void fishofthieves$openTextEdit(SignBlockEntity signEntity, boolean isFrontText, CallbackInfo info)
    {
        if (signEntity instanceof FOTHangingSignBlockEntity hangingSignBlockEntity)
        {
            this.minecraft.setScreen(new HangingSignEditScreen(hangingSignBlockEntity, isFrontText, this.minecraft.isTextFilteringEnabled()));
            info.cancel();
        }
    }
}