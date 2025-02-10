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

public record IslehopperVariant(String name, ClientAsset texture, Optional<ClientAsset> glowTexture, SpawnSettings spawnSettings, int customModelData) implements AbstractFishVariant
{
    public static final Codec<IslehopperVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(IslehopperVariant::new);
    public static final Codec<IslehopperVariant> NETWORK_CODEC = AbstractFishVariant.networkCodec(IslehopperVariant::new);
    public static final Codec<Holder<IslehopperVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.ISLEHOPPER_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<IslehopperVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FOTRegistries.ISLEHOPPER_VARIANT);

    public IslehopperVariant(String name, ClientAsset texture, Optional<ClientAsset> glowTexture, int customModelData)
    {
        this(name, texture, glowTexture, SpawnSettings.EMPTY, customModelData);
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