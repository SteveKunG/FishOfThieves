package com.stevekung.fishofthieves.item.trade;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record RestockableData(int index, int tier)
{
    public static final Codec<RestockableData> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("index").forGetter(data -> data.index), Codec.INT.fieldOf("tier").forGetter(data -> data.tier)).apply(instance, RestockableData::new));
    public static final Codec<Set<RestockableData>> CODEC_LINKED_SET = Codec.list(CODEC).xmap(LinkedHashSet::new, ArrayList::new);
}
