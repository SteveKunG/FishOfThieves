package com.stevekung.fishofthieves.entity.variant;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.entity.condition.SpawnCondition;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record PondieVariant(String name, ResourceLocation texture, Optional<ResourceLocation> glowTexture, List<SpawnCondition> conditions, Holder<Item> baseItem, Optional<Integer> customModelData) implements AbstractFishVariant
{
    public static final Codec<PondieVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(PondieVariant::new);
    public static final Codec<Holder<PondieVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.PONDIE_VARIANT, DIRECT_CODEC);

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