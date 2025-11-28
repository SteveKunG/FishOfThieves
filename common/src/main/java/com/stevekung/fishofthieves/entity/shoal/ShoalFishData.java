package com.stevekung.fishofthieves.entity.shoal;

import java.util.List;
import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;

public record ShoalFishData(String id, UUID uuid, CompoundTag data)
{
    public static final Codec<List<ShoalFishData>> CODEC = Codec.list(RecordCodecBuilder.create(instance -> instance
            .group(
                    Codec.STRING.fieldOf("id").forGetter(ShoalFishData::id),
                    UUIDUtil.CODEC.fieldOf("uuid").forGetter(ShoalFishData::uuid),
                    CompoundTag.CODEC.fieldOf("data").forGetter(ShoalFishData::data)
            )
            .apply(instance, ShoalFishData::new)));
}