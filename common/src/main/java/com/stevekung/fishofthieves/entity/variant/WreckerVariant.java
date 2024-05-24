package com.stevekung.fishofthieves.entity.variant;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.entity.condition.SpawnCondition;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

public record WreckerVariant(ResourceLocation texture, Optional<ResourceLocation> glowTexture, List<SpawnCondition> conditions) implements AbstractFishVariant
{
    public static final Codec<WreckerVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> AbstractFishVariant.commonFields(instance).apply(instance, WreckerVariant::new));
    public static final Codec<Holder<WreckerVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.WRECKER_VARIANT, DIRECT_CODEC);

    @Override
    public ResourceLocation texture()
    {
        return AbstractFishVariant.fullTextureId(this.texture);
    }

    @Override
    public Optional<ResourceLocation> glowTexture()
    {
        return this.glowTexture.map(AbstractFishVariant::fullTextureId);
    }
}