package com.stevekung.fishofthieves.mixin.client.debug;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.client.renderer.debug.StructureCenterPosDebugRenderer;
import com.stevekung.fishofthieves.client.FOTDebugScreenEntries;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.debug.DebugRenderer;

@Mixin(DebugRenderer.class)
public class MixinDebugRenderer
{
    @Shadow
    @Final
    List<DebugRenderer.SimpleDebugRenderer> opaqueRenderers;

    @Inject(method = "refreshRendererList", at = @At("TAIL"))
    private void fishofthieves$refreshRendererList(CallbackInfo info, @Local Minecraft minecraft)
    {
        if (minecraft.debugEntries.isCurrentlyEnabled(FOTDebugScreenEntries.STRUCTURE_CENTER_POS))
        {
            this.opaqueRenderers.add(new StructureCenterPosDebugRenderer(minecraft));
        }
    }
}