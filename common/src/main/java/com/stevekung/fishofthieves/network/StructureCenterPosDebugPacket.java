package com.stevekung.fishofthieves.network;

import java.util.List;

import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record StructureCenterPosDebugPacket(List<Pair<BlockPos, ResourceLocation>> structurePosList) implements CustomPacketPayload
{
    public static final Type<StructureCenterPosDebugPacket> TYPE = new Type<>(FishOfThieves.STRUCTURE_CENTER_POS_DEBUG);
    public static final StreamCodec<FriendlyByteBuf, StructureCenterPosDebugPacket> CODEC = CustomPacketPayload.codec(StructureCenterPosDebugPacket::write, StructureCenterPosDebugPacket::new);

    public StructureCenterPosDebugPacket(FriendlyByteBuf buf)
    {
        this(buf.readList(object -> Pair.of(buf.readBlockPos(), buf.readResourceLocation())));
    }

    public void write(FriendlyByteBuf buff)
    {
        buff.writeCollection(this.structurePosList, (buf, pair) ->
        {
            buf.writeBlockPos(pair.getFirst());
            buf.writeResourceLocation(pair.getSecond());
        });
    }

    @Override
    public Type<StructureCenterPosDebugPacket> type()
    {
        return TYPE;
    }
}