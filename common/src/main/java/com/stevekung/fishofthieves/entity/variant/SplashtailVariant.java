package com.stevekung.fishofthieves.entity.variant;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

public record SplashtailVariant(String name, ResourceLocation texture, Optional<ResourceLocation> glowTexture, SpawnSettings spawnSettings, int customModelData) implements AbstractFishVariant
{
    public static final Codec<SplashtailVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(SplashtailVariant::new);
    public static final Codec<SplashtailVariant> NETWORK_CODEC = AbstractFishVariant.networkCodec(SplashtailVariant::new);
    public static final Codec<Holder<SplashtailVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.SPLASHTAIL_VARIANT, DIRECT_CODEC);

    public SplashtailVariant(String name, ResourceLocation texture, Optional<ResourceLocation> glowTexture, int customModelData)
    {
        this(name, texture, glowTexture, SpawnSettings.EMPTY, customModelData);
    }

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