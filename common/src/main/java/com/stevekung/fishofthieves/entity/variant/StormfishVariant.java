package com.stevekung.fishofthieves.entity.variant;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.codec.RegistryFileCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record StormfishVariant(String name, ClientAsset.ResourceTexture texture, Optional<ClientAsset.ResourceTexture> glowTexture, Optional<Boolean> treasured, SpawnSettings spawnSettings, int order) implements AbstractFishVariant
{
    public static final Codec<StormfishVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(StormfishVariant::new);
    public static final Codec<StormfishVariant> NETWORK_CODEC = AbstractFishVariant.networkCodec(StormfishVariant::new);
    public static final Codec<Holder<StormfishVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.STORMFISH_VARIANT, DIRECT_CODEC, false);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<StormfishVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FOTRegistries.STORMFISH_VARIANT);

    public StormfishVariant(String name, ClientAsset.ResourceTexture texture, Optional<ClientAsset.ResourceTexture> glowTexture, Optional<Boolean> treasured, int order)
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