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

public record DevilfishVariant(String name, ClientAsset.ResourceTexture texture, Optional<ClientAsset.ResourceTexture> glowTexture, Optional<Boolean> treasured, SpawnSettings spawnSettings, int customModelData) implements AbstractFishVariant
{
    public static final Codec<DevilfishVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(DevilfishVariant::new);
    public static final Codec<DevilfishVariant> NETWORK_CODEC = AbstractFishVariant.networkCodec(DevilfishVariant::new);
    public static final Codec<Holder<DevilfishVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.DEVILFISH_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DevilfishVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FOTRegistries.DEVILFISH_VARIANT);

    public DevilfishVariant(String name, ClientAsset.ResourceTexture texture, Optional<ClientAsset.ResourceTexture> glowTexture, Optional<Boolean> treasured, int customModelData)
    {
        this(name, texture, glowTexture, treasured, SpawnSettings.EMPTY, customModelData);
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