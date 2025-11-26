package com.stevekung.fishofthieves.client.renderer.debug;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public class StructureCenterPosDebugRenderer implements DebugRenderer.SimpleDebugRenderer
{
    private final Minecraft minecraft;
    private final List<Pair<BlockPos, ResourceLocation>> structurePosList = new ArrayList<>();

    public StructureCenterPosDebugRenderer(Minecraft minecraft)
    {
        this.minecraft = minecraft;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, double camX, double camY, double camZ)
    {
        var distFromStructure = Integer.MAX_VALUE;
        var entityPos = this.minecraft.player.blockPosition();
        var posList = this.structurePosList.stream().distinct().toList();

        posList.forEach(pair ->
        {
            var blockPos = pair.getFirst();

            if (blockPos.distManhattan(entityPos) < 512)
            {
                var aabb = new AABB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1F, blockPos.getY() + 1F, blockPos.getZ() + 1F);
                DebugRenderer.renderFilledBox(poseStack, buffer, aabb.move(-camX, -camY, -camZ), 0.0F, 1.0F, 0.0F, 0.5F);
            }
        });

        if (FishOfThieves.CONFIG.debug.displayStructureCenterPosInfo)
        {
            var optional = posList.stream()
                    .filter(pair -> pair.getFirst().distManhattan(entityPos) <= FishOfThieves.CONFIG.debug.structureCenterPosRangeLimit)
                    .min(Comparator.comparing(pair -> pair.getFirst().distManhattan(entityPos)));

            if (optional.isPresent())
            {
                var blockPos = optional.get().getFirst();
                var structureDist = blockPos.distManhattan(entityPos);

                // Get nearest structure range
                if (structureDist < distFromStructure)
                {
                    distFromStructure = structureDist;
                }

                this.minecraft.gui.setOverlayMessage(Component.literal(optional.get().getSecond() + ": " + distFromStructure + ", pos: " + blockPos.toShortString()), false);
            }
        }
    }

    @Override
    public void clear()
    {
        this.structurePosList.clear();
    }

    public void addStructure(List<Pair<BlockPos, ResourceLocation>> structurePosList)
    {
        this.structurePosList.addAll(structurePosList);
    }
}