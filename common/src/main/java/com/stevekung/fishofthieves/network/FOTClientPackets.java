package com.stevekung.fishofthieves.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FOTClientPackets
{
    public static void setFishingHookBait(Minecraft minecraft, Level level, int entityId, ItemStack itemStack)
    {
        minecraft.execute(() ->
        {
            if (level != null)
            {
                var fishingHook = (FishingHook) level.getEntity(entityId);

                if (fishingHook == null)
                {
                    return;
                }

                fishingHook.fishofthieves$setBaitStack(itemStack);
            }
        });
    }
}