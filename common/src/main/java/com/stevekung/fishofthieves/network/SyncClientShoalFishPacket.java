package com.stevekung.fishofthieves.network;

import java.util.ArrayList;
import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.ShoalFishData;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import io.netty.buffer.ByteBuf;

public record SyncClientShoalFishPacket(int entityId, List<ShoalFishData> shoalFishData, boolean forcedUpdate) implements CustomPacketPayload
{
    public static final Type<SyncClientShoalFishPacket> TYPE = new Type<>(FishOfThieves.SYNC_CLIENT_SHOAL_FISH);
    public static final StreamCodec<ByteBuf, SyncClientShoalFishPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SyncClientShoalFishPacket::entityId,
            ShoalFishData.STREAM_CODEC.apply(ByteBufCodecs.collection(ArrayList::new, ShoalFishData.MAX_SHOAL_FISH)),
            SyncClientShoalFishPacket::shoalFishData,
            ByteBufCodecs.BOOL,
            SyncClientShoalFishPacket::forcedUpdate,
            SyncClientShoalFishPacket::new
    );

    @Override
    public Type<SyncClientShoalFishPacket> type()
    {
        return TYPE;
    }
}