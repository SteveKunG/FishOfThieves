package com.stevekung.fishofthieves.neoforge;

import com.stevekung.fishofthieves.network.FOTClientPackets;
import com.stevekung.fishofthieves.network.SyncClientShoalFishPacket;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SyncClientShoalFishPacketNeoForge
{
    public static void handle(SyncClientShoalFishPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() -> FOTClientPackets.syncClientShoalFish(Minecraft.getInstance(), packet.entityId(), packet.shoalFishData()));
    }
}