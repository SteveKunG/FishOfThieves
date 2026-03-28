package com.stevekung.fishofthieves.item.trade;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.VillagerTrade;

public record RestockableData(int index, ResourceKey<VillagerTrade> trade)
{
    public static final Codec<RestockableData> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("index").forGetter(data -> data.index), ResourceKey.codec(Registries.VILLAGER_TRADE).fieldOf("trade").forGetter(data -> data.trade)).apply(instance, RestockableData::new));
    public static final Codec<Set<RestockableData>> CODEC_LINKED_SET = Codec.list(CODEC).xmap(LinkedHashSet::new, ArrayList::new);
}