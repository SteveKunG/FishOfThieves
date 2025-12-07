package com.stevekung.fishofthieves.entity.variant;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

public record WreckerVariant(String name, ResourceLocation texture, Optional<ResourceLocation> glowTexture, Optional<Boolean> treasured, SpawnSettings spawnSettings, int order) implements AbstractFishVariant
{
    public static final Codec<WreckerVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(WreckerVariant::new);
    public static final Codec<WreckerVariant> NETWORK_CODEC = AbstractFishVariant.networkCodec(WreckerVariant::new);
    public static final Codec<Holder<WreckerVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.WRECKER_VARIANT, DIRECT_CODEC);

    public WreckerVariant(String name, ResourceLocation texture, Optional<ResourceLocation> glowTexture, Optional<Boolean> treasured, int order)
    {
        this(name, texture, glowTexture, treasured, SpawnSettings.EMPTY, order);
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