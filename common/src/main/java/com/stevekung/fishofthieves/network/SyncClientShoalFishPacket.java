package com.stevekung.fishofthieves.network;

import java.util.ArrayList;
import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.ShoalFishData;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SyncClientShoalFishPacket(int entityId, List<ShoalFishData> shoalFishData, boolean forcedUpdate) implements CustomPacketPayload
{
    public static final Type<SyncClientShoalFishPacket> TYPE = new Type<>(FishOfThieves.SYNC_CLIENT_SHOAL_FISH);
    public static final StreamCodec<FriendlyByteBuf, SyncClientShoalFishPacket> CODEC = CustomPacketPayload.codec(SyncClientShoalFishPacket::write, SyncClientShoalFishPacket::new);

    public SyncClientShoalFishPacket(FriendlyByteBuf buf)
    {
        this(buf.readVarInt(), buf.readCollection(size -> new ArrayList<>(Math.min(size, ShoalFishData.MAX_SHOAL_FISH)), buf1 -> new ShoalFishData(buf1.readUtf(), buf1.readUUID(), buf1.readNbt())), buf.readBoolean());
    }

    public void write(FriendlyByteBuf buff)
    {
        buff.writeVarInt(this.entityId);
        buff.writeCollection(this.shoalFishData, (buf, shoalFish) ->
        {
            buf.writeUtf(shoalFish.id());
            buf.writeUUID(shoalFish.uuid());
            buf.writeNbt(shoalFish.data());
        });
        buff.writeBoolean(this.forcedUpdate);
    }

    @Override
    public Type<SyncClientShoalFishPacket> type()
    {
        return TYPE;
    }
}