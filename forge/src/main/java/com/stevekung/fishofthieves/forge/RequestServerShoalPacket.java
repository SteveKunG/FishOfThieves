package com.stevekung.fishofthieves.forge;

import java.util.function.Supplier;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.entity.shoal.Shoal;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class RequestServerShoalPacket
{
    private final int entityId;

    public RequestServerShoalPacket(int entityId)
    {
        this.entityId = entityId;
    }

    public RequestServerShoalPacket(FriendlyByteBuf buf)
    {
        this.entityId = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.entityId);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            var player = ctx.get().getSender();
            var shoal = (Shoal) player.level().getEntity(this.entityId);

            if (shoal != null)
            {
                FOTPlatform.syncClientShoalFish(shoal, false);
            }
        });
    }
}