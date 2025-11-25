package com.stevekung.fishofthieves.mixin.client.map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;

import net.minecraft.client.gui.MapRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.saveddata.maps.MapDecoration;

@Mixin(MapRenderer.class)
public class MixinMapRenderer
{
    @Mixin(targets = "net.minecraft.client.gui.MapRenderer$MapInstance")
    public static class MixinMapInstance
    {
        @Unique
        private static final RenderType MAP_ICONS = RenderType.text(FishOfThieves.id("textures/map/map_icons.png"));

        @ModifyVariable(method = "draw", at = @At("STORE"))
        private byte fishofthieves$redirectIconIndex(byte defaultValue, @Local MapDecoration mapDecoration)
        {
            // Only client side will start icon index at 0
            if (mapDecoration.getType() == FOTMapDecorationTypes.TREASURED_FISH)
            {
                return 0;
            }
            return defaultValue;
        }

        @WrapOperation(method = "draw", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/MultiBufferSource.getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
        private VertexConsumer fishofthieves$drawExternalMapIcons(MultiBufferSource bufferSource, RenderType renderType, Operation<VertexConsumer> operation, @Local MapDecoration mapDecoration)
        {
            if (mapDecoration.getType() == FOTMapDecorationTypes.TREASURED_FISH)
            {
                return bufferSource.getBuffer(MAP_ICONS);
            }
            return operation.call(bufferSource, renderType);
        }
    }
}