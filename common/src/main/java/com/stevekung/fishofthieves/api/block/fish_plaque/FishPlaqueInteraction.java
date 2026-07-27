package com.stevekung.fishofthieves.api.block.fish_plaque;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.codec.RegistryFileCodec;
import net.minecraft.resources.Identifier;

public record FishPlaqueInteraction(Identifier entityType, Identifier item)
{
    public static final Codec<FishPlaqueInteraction> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("entity_type").forGetter(FishPlaqueInteraction::entityType),
            Identifier.CODEC.fieldOf("item").forGetter(FishPlaqueInteraction::item)
    ).apply(instance, FishPlaqueInteraction::new));
    public static final Codec<Holder<FishPlaqueInteraction>> CODEC = RegistryFileCodec.create(FOTRegistries.FISH_PLAQUE_INTERACTION, DIRECT_CODEC, false);
}