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

public record SplashtailVariant(String name, ClientAsset.ResourceTexture texture, Optional<ClientAsset.ResourceTexture> glowTexture, Optional<Boolean> treasured, SpawnSettings spawnSettings, int customModelData) implements AbstractFishVariant
{
    public static final Codec<SplashtailVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(SplashtailVariant::new);
    public static final Codec<SplashtailVariant> NETWORK_CODEC = AbstractFishVariant.networkCodec(SplashtailVariant::new);
    public static final Codec<Holder<SplashtailVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.SPLASHTAIL_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SplashtailVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FOTRegistries.SPLASHTAIL_VARIANT);

    public SplashtailVariant(String name, ClientAsset.ResourceTexture texture, Optional<ClientAsset.ResourceTexture> glowTexture, Optional<Boolean> treasured, int customModelData)
    {
        this(name, texture, glowTexture, treasured, SpawnSettings.EMPTY, customModelData);
    }

    @Override
    public ResourceLocation fullTexture()
    {
        return this.texture.texturePath();
    }

    @Override
    public Optional<ResourceLocation> fullGlowTexture()
    {
        return this.glowTexture.map(ClientAsset.ResourceTexture::texturePath);
    }
}