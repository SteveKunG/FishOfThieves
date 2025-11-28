package com.stevekung.fishofthieves.network;

import java.util.List;

import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.entity.shoal.ShoalFishData;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

public class FOTClientPackets
{
    public static void setFishingHookBait(Minecraft minecraft, int entityId, ItemStack itemStack)
    {
        minecraft.execute(() ->
        {
            if (minecraft.level != null)
            {
                var fishingHook = (FishingHook) minecraft.level.getEntity(entityId);

                if (fishingHook == null)
                {
                    return;
                }

                fishingHook.fishofthieves$setBaitStack(itemStack);
            }
        });
    }

    public static void syncClientShoalFish(Minecraft minecraft, int entityId, List<ShoalFishData> shoalFishData, boolean forcedUpdate)
    {
        minecraft.execute(() ->
        {
            if (minecraft.level != null)
            {
                var shoal = (Shoal) minecraft.level.getEntity(entityId);

                if (shoal == null)
                {
                    return;
                }

                shoal.syncClientShoalFish(shoalFishData, forcedUpdate);
            }
        });
    }
}