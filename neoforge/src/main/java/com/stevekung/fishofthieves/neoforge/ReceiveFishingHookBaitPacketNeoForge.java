package com.stevekung.fishofthieves.neoforge;

import com.stevekung.fishofthieves.network.FOTClientPackets;
import com.stevekung.fishofthieves.network.ReceiveFishingHookBaitPacket;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ReceiveFishingHookBaitPacketNeoForge
{
    public static void handle(ReceiveFishingHookBaitPacket packet, IPayloadContext context)
    {
        var minecraft = Minecraft.getInstance();
        var level = minecraft.level;
        context.enqueueWork(() -> FOTClientPackets.setFishingHookBait(minecraft, level, packet.entityId(), packet.itemStack()));
    }
}