package com.stevekung.fishofthieves.api.block.fish_plaque;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

public record FishPlaqueInteraction(ResourceLocation entityType, ResourceLocation item)
{
    //@formatter:off
    public static final Codec<FishPlaqueInteraction> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("entity_type").forGetter(FishPlaqueInteraction::entityType),
            ResourceLocation.CODEC.fieldOf("item").forGetter(FishPlaqueInteraction::item)
    ).apply(instance, FishPlaqueInteraction::new));
    //@formatter:on
    public static final Codec<Holder<FishPlaqueInteraction>> CODEC = RegistryFileCodec.create(FOTRegistries.FISH_PLAQUE_INTERACTION, DIRECT_CODEC);
}