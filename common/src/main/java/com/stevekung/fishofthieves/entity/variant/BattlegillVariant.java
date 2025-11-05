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

public record BattlegillVariant(String name, ClientAsset.ResourceTexture texture, Optional<ClientAsset.ResourceTexture> glowTexture, SpawnSettings spawnSettings, int customModelData) implements AbstractFishVariant
{
    public static final Codec<BattlegillVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(BattlegillVariant::new);
    public static final Codec<BattlegillVariant> NETWORK_CODEC = AbstractFishVariant.networkCodec(BattlegillVariant::new);
    public static final Codec<Holder<BattlegillVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.BATTLEGILL_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BattlegillVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FOTRegistries.BATTLEGILL_VARIANT);

    public BattlegillVariant(String name, ClientAsset.ResourceTexture texture, Optional<ClientAsset.ResourceTexture> glowTexture, int customModelData)
    {
        this(name, texture, glowTexture, SpawnSettings.EMPTY, customModelData);
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