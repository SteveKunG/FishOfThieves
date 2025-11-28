package com.stevekung.fishofthieves.network;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record RequestServerShoalFishPacket(int entityId) implements CustomPacketPayload
{
    public static final Type<RequestServerShoalFishPacket> TYPE = new Type<>(FishOfThieves.REQUEST_SERVER_SHOAL_FISH);
    public static final StreamCodec<FriendlyByteBuf, RequestServerShoalFishPacket> CODEC = CustomPacketPayload.codec(RequestServerShoalFishPacket::write, RequestServerShoalFishPacket::new);

    public RequestServerShoalFishPacket(FriendlyByteBuf buf)
    {
        this(buf.readVarInt());
    }

    public void write(FriendlyByteBuf buff)
    {
        buff.writeVarInt(this.entityId);
    }

    @Override
    public Type<RequestServerShoalFishPacket> type()
    {
        return TYPE;
    }
}