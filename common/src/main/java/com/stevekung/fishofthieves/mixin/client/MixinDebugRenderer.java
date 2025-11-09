package com.stevekung.fishofthieves.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.client.renderer.debug.DebugRendererAccessor;
import com.stevekung.fishofthieves.client.renderer.debug.StructureCenterPosDebugRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;

@Mixin(DebugRenderer.class)
public class MixinDebugRenderer implements DebugRendererAccessor
{
    @Unique
    private StructureCenterPosDebugRenderer structureRenderer;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void fishofthieves$init(Minecraft minecraft, CallbackInfo info)
    {
        this.structureRenderer = new StructureCenterPosDebugRenderer(minecraft);
    }

    @Inject(method = "clear", at = @At("TAIL"))
    private void fishofthieves$clear(CallbackInfo info)
    {
        this.structureRenderer.clear();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void fishofthieves$render(PoseStack poseStack, Frustum frustum, MultiBufferSource.BufferSource bufferSource, double camX, double camY, double camZ, CallbackInfo info)
    {
        if (FishOfThieves.CONFIG.debug.enableStructureCenterPosRender)
        {
            this.structureRenderer.render(poseStack, bufferSource, camX, camY, camZ);
        }
    }

    @Override
    public StructureCenterPosDebugRenderer fishofthieves$getStructureCenterPosDebugRenderer()
    {
        return this.structureRenderer;
    }
}