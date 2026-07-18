package com.stevekung.fishofthieves.mixin.client.entity;

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
import net.minecraft.world.level.block.entity.SignTextSlot;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer
{
    @Shadow
    @Final
    Minecraft minecraft;

    @Inject(method = "openTextEdit", cancellable = true, at = @At("HEAD"))
    private void fishofthieves$openTextEdit(SignBlockEntity signEntity, SignTextSlot slot, CallbackInfo info)
    {
        if (signEntity instanceof FOTHangingSignBlockEntity hangingSignBlockEntity)
        {
            this.minecraft.gui.setScreen(new HangingSignEditScreen(hangingSignBlockEntity, slot, this.minecraft.isTextFilteringEnabled()));
            info.cancel();
        }
    }
}