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

public record AncientscaleVariant(String name, ClientAsset.ResourceTexture texture, Optional<ClientAsset.ResourceTexture> glowTexture, Optional<Boolean> treasured, SpawnSettings spawnSettings, int order) implements AbstractFishVariant
{
    public static final Codec<AncientscaleVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(AncientscaleVariant::new);
    public static final Codec<AncientscaleVariant> NETWORK_CODEC = AbstractFishVariant.networkCodec(AncientscaleVariant::new);
    public static final Codec<Holder<AncientscaleVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.ANCIENTSCALE_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<AncientscaleVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FOTRegistries.ANCIENTSCALE_VARIANT);

    public AncientscaleVariant(String name, ClientAsset.ResourceTexture texture, Optional<ClientAsset.ResourceTexture> glowTexture, Optional<Boolean> treasured, int order)
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