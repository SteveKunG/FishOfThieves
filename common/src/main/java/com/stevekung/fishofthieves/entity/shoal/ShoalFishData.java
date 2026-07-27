package com.stevekung.fishofthieves.entity.shoal;

import java.util.List;
import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import io.netty.buffer.ByteBuf;

public record ShoalFishData(String id, UUID uuid, CompoundTag data)
{
    public static final Codec<List<ShoalFishData>> CODEC = Codec.list(RecordCodecBuilder.create(instance -> instance
            .group(
                    Codec.STRING.fieldOf("id").forGetter(ShoalFishData::id),
                    UUIDUtil.CODEC.optionalFieldOf("uuid", UUID.randomUUID()).forGetter(ShoalFishData::uuid),
                    CompoundTag.CODEC.optionalFieldOf("data", new CompoundTag()).forGetter(ShoalFishData::data)
            )
            .apply(instance, ShoalFishData::new)));

    public static final StreamCodec<ByteBuf, ShoalFishData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            ShoalFishData::id,
            UUIDUtil.STREAM_CODEC,
            ShoalFishData::uuid,
            ByteBufCodecs.TRUSTED_COMPOUND_TAG,
            ShoalFishData::data,
            ShoalFishData::new
    );
}