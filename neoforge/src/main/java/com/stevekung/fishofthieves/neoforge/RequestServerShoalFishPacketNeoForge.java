package com.stevekung.fishofthieves.neoforge;

import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.network.RequestServerShoalFishPacket;
import com.stevekung.fishofthieves.network.SyncClientShoalFishPacket;

import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class RequestServerShoalFishPacketNeoForge
{
    public static void handle(RequestServerShoalFishPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player() instanceof ServerPlayer player)
            {
                var shoal = (Shoal) player.level().getEntity(packet.entityId());

                if (shoal != null)
                {
                    player.connection.send(new ClientboundCustomPayloadPacket(new SyncClientShoalFishPacket(shoal.getId(), shoal.getShoalFish(), false)));
                }
            }
        });
    }
}