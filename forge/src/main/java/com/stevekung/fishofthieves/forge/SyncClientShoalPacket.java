package com.stevekung.fishofthieves.forge;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.stevekung.fishofthieves.entity.shoal.ShoalFishData;
import com.stevekung.fishofthieves.network.FOTClientPackets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class SyncClientShoalPacket
{
    private final int entityId;
    private final List<ShoalFishData> shoalFishData;

    public SyncClientShoalPacket(int entityId, List<ShoalFishData> shoalFishData)
    {
        this.entityId = entityId;
        this.shoalFishData = shoalFishData;
    }

    public SyncClientShoalPacket(FriendlyByteBuf buf)
    {
        this.entityId = buf.readVarInt();
        this.shoalFishData = buf.readCollection(ArrayList::new, buf1 -> new ShoalFishData(buf1.readUtf(), buf1.readUUID(), buf1.readNbt()));
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeVarInt(this.entityId);
        buf.writeCollection(this.shoalFishData, (buff, shoalFish) ->
        {
            buff.writeUtf(shoalFish.id());
            buff.writeUUID(shoalFish.uuid());
            buff.writeNbt(shoalFish.data());
        });
    }

    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        var minecraft = Minecraft.getInstance();
        ctx.get().enqueueWork(() -> FOTClientPackets.syncClientShoalFish(minecraft, this.entityId, this.shoalFishData));
    }
}