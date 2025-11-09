package com.stevekung.fishofthieves.network;

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
}