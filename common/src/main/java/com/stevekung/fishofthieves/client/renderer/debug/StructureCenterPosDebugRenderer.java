package com.stevekung.fishofthieves.client.renderer.debug;

import java.util.Comparator;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTDebugSubscriptions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.debug.DebugValueAccess;
import net.minecraft.world.phys.AABB;

public record StructureCenterPosDebugRenderer(Minecraft minecraft) implements DebugRenderer.SimpleDebugRenderer
{
    @Override
    public void emitGizmos(double camX, double camY, double camZ, DebugValueAccess debugValueAccess, Frustum frustum, float partialTicks)
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
                    Gizmos.cuboid(aabb, GizmoStyle.fill(ARGB.colorFromFloat(0.5F, 0.0F, 1.0F, 0.0F)));
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

                    this.minecraft.gui.setOverlayMessage(Component.literal(optional.get().identifier() + ": " + distFromStructure + ", pos: " + blockPos.toShortString()), false);
                }
            }
        }));
    }
}