package com.stevekung.fishofthieves.network;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public record DebugCenterPosStructureInfo(List<StructurePos> structurePosList)
{
    public static final StreamCodec<FriendlyByteBuf, DebugCenterPosStructureInfo> STREAM_CODEC = StreamCodec.composite(StructurePos.STREAM_CODEC.apply(ByteBufCodecs.list()), DebugCenterPosStructureInfo::structurePosList, DebugCenterPosStructureInfo::new);

    public static void addStructures(StructureStart structureStart, List<DebugCenterPosStructureInfo> list, BiConsumer<List<StructurePos>, StructureStart> consumer)
    {
        List<DebugCenterPosStructureInfo.StructurePos> structurePosList = new ArrayList<>();
        consumer.accept(structurePosList, structureStart);
        list.add(new DebugCenterPosStructureInfo(structurePosList));
    }

    public record StructurePos(BlockPos blockPos, ResourceLocation resourceLocation)
    {
        public static final StreamCodec<FriendlyByteBuf, StructurePos> STREAM_CODEC = StreamCodec.composite(BlockPos.STREAM_CODEC, StructurePos::blockPos, ResourceLocation.STREAM_CODEC, StructurePos::resourceLocation, StructurePos::new);
    }
}
