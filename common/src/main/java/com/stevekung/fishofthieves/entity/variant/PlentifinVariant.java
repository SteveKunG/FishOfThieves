package com.stevekung.fishofthieves.entity.variant;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

public record PlentifinVariant(String name, ClientAsset texture, Optional<ClientAsset> glowTexture, Optional<Boolean> treasured, SpawnSettings spawnSettings, int order) implements AbstractFishVariant
{
    public static final Codec<PlentifinVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(PlentifinVariant::new);
    public static final Codec<PlentifinVariant> NETWORK_CODEC = AbstractFishVariant.networkCodec(PlentifinVariant::new);
    public static final Codec<Holder<PlentifinVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.PLENTIFIN_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<PlentifinVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FOTRegistries.PLENTIFIN_VARIANT);

    public PlentifinVariant(String name, ClientAsset texture, Optional<ClientAsset> glowTexture, Optional<Boolean> treasured, int order)
    {
        this(name, texture, glowTexture, treasured, SpawnSettings.EMPTY, order);
    }

    @Override
    public ResourceLocation fullTexture()
    {
        return this.texture.texturePath();
    }

    @Override
    public Optional<ResourceLocation> fullGlowTexture()
    {
        return this.glowTexture.map(ClientAsset::texturePath);
    }
}