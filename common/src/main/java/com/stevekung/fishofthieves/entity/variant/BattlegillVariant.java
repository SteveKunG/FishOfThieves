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

public record BattlegillVariant(String name, ResourceLocation texture, Optional<ResourceLocation> glowTexture, List<SpawnCondition> conditions, Holder<Item> baseItem, int customModelData) implements AbstractFishVariant
{
    public static final Codec<BattlegillVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(BattlegillVariant::new);
    public static final Codec<Holder<BattlegillVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.BATTLEGILL_VARIANT, DIRECT_CODEC);

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