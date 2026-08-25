package com.stevekung.fishofthieves.network;

import java.util.List;

import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.client.renderer.debug.DebugRendererAccessor;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.entity.shoal.ShoalFishData;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

public class FOTClientPackets
{
    private FOTClientPackets() {}

    public static void setFishingHookBait(Minecraft minecraft, int entityId, ItemStack itemStack)
    {
        minecraft.execute(() ->
        {
            if (minecraft.level == null || !(minecraft.level.getEntity(entityId) instanceof FishingHook fishingHook))
            {
                return;
            }

            fishingHook.fishofthieves$setBaitStack(itemStack);
        });
    }

    public static void addDebugStructureCenterPos(Minecraft minecraft, List<Pair<BlockPos, ResourceLocation>> structurePosList)
    {
        minecraft.execute(() ->
        {
            if (minecraft.level != null)
            {
                ((DebugRendererAccessor) minecraft.debugRenderer).fishofthieves$getStructureCenterPosDebugRenderer().addStructure(structurePosList);
            }
        });
    }

    public static void syncClientShoalFish(Minecraft minecraft, int entityId, List<ShoalFishData> shoalFishData, boolean forcedUpdate)
    {
        minecraft.execute(() ->
        {
            if (minecraft.level == null || !(minecraft.level.getEntity(entityId) instanceof Shoal shoal))
            {
                return;
            }

            shoal.syncClientShoalFish(shoalFishData, forcedUpdate);
        });
    }
}