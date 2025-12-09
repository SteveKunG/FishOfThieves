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
import net.minecraft.resources.Identifier;

public record PondieVariant(String name, ClientAsset.ResourceTexture texture, Optional<ClientAsset.ResourceTexture> glowTexture, Optional<Boolean> treasured, SpawnSettings spawnSettings, int order) implements AbstractFishVariant
{
    public static final Codec<PondieVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(PondieVariant::new);
    public static final Codec<PondieVariant> NETWORK_CODEC = AbstractFishVariant.networkCodec(PondieVariant::new);
    public static final Codec<Holder<PondieVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.PONDIE_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<PondieVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FOTRegistries.PONDIE_VARIANT);

    public PondieVariant(String name, ClientAsset.ResourceTexture texture, Optional<ClientAsset.ResourceTexture> glowTexture, Optional<Boolean> treasured, int order)
    {
        this(name, texture, glowTexture, treasured, SpawnSettings.EMPTY, order);
    }

    @Override
    public Identifier fullTexture()
    {
        return this.texture.texturePath();
    }

    @Override
    public Optional<Identifier> fullGlowTexture()
    {
        return this.glowTexture.map(ClientAsset.ResourceTexture::texturePath);
    }
}