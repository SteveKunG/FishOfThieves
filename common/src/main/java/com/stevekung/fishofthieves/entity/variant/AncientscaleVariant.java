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

public record AncientscaleVariant(ResourceLocation texture, Optional<ResourceLocation> glowTexture, List<SpawnCondition> conditions) implements AbstractFishVariant
{
    public static final Codec<AncientscaleVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> AbstractFishVariant.commonFields(instance).apply(instance, AncientscaleVariant::new));
    public static final Codec<Holder<AncientscaleVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.ANCIENTSCALE_VARIANT, DIRECT_CODEC);

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