package com.stevekung.fishofthieves.client.renderer.debug;

import java.util.Comparator;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTDebugSubscriptions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.debug.DebugValueAccess;
import net.minecraft.world.phys.AABB;

public record StructureCenterPosDebugRenderer(Minecraft minecraft) implements DebugRenderer.SimpleDebugRenderer
{
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, double camX, double camY, double camZ, DebugValueAccess debugValueAccess, Frustum frustum)
    {
        var entityPos = this.minecraft.player.blockPosition();

        debugValueAccess.forEachChunk(FOTDebugSubscriptions.STRUCTURE_CENTER_POS, (chunkPos, list) -> list.forEach(info ->
        {
            info.structurePosList().forEach(structurePos ->
            {
                var blockPos = structurePos.blockPos();

                if (blockPos.distManhattan(entityPos) < 512)
                {
                    var aabb = new AABB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1F, blockPos.getY() + 1F, blockPos.getZ() + 1F);
                    DebugRenderer.renderFilledBox(poseStack, buffer, aabb.move(-camX, -camY, -camZ), 0.0F, 1.0F, 0.0F, 0.5F);
                }
            });

            if (FishOfThieves.CONFIG.debug.displayStructureCenterPosInfo)
            {
                var distFromStructure = Integer.MAX_VALUE;
                var optional = info.structurePosList()
                        .stream()
                        .filter(structurePos -> structurePos.blockPos().distManhattan(entityPos) <= FishOfThieves.CONFIG.debug.structureCenterPosRangeLimit)
                        .min(Comparator.comparing(structurePos -> structurePos.blockPos().distManhattan(entityPos)));

                if (optional.isPresent())
                {
                    var blockPos = optional.get().blockPos();
                    var structureDist = blockPos.distManhattan(entityPos);

                    // Get nearest structure range
                    if (structureDist < distFromStructure)
                    {
                        distFromStructure = structureDist;
                    }

                    this.minecraft.gui.setOverlayMessage(Component.literal(optional.get().resourceLocation() + ": " + distFromStructure + ", pos: " + blockPos.toShortString()), false);
                }
            }
        }));
    }
}