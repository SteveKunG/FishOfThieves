package com.stevekung.fishofthieves.entity.variant;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

public record StormfishVariant(String name, ResourceLocation texture, Optional<ResourceLocation> glowTexture, SpawnSettings spawnSettings, int customModelData) implements AbstractFishVariant
{
    public static final Codec<StormfishVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(StormfishVariant::new);
    public static final Codec<Holder<StormfishVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.STORMFISH_VARIANT, DIRECT_CODEC);

    @Override
    public ResourceLocation fullTexture()
    {
        return AbstractFishVariant.fullTextureId(this.texture);
    }

    @Override
    public Optional<ResourceLocation> fullGlowTexture()
    {
        return this.glowTexture.map(AbstractFishVariant::fullTextureId);
    }
}