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

public record WreckerVariant(String name, ClientAsset texture, Optional<ClientAsset> glowTexture, Optional<Boolean> treasured, SpawnSettings spawnSettings, int customModelData) implements AbstractFishVariant
{
    public static final Codec<WreckerVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(WreckerVariant::new);
    public static final Codec<WreckerVariant> NETWORK_CODEC = AbstractFishVariant.networkCodec(WreckerVariant::new);
    public static final Codec<Holder<WreckerVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.WRECKER_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<WreckerVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FOTRegistries.WRECKER_VARIANT);

    public WreckerVariant(String name, ClientAsset texture, Optional<ClientAsset> glowTexture, Optional<Boolean> treasured, int customModelData)
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
        return this.glowTexture.map(ClientAsset::texturePath);
    }
}