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

public record AncientscaleVariant(String name, ResourceLocation texture, Optional<ResourceLocation> glowTexture, List<SpawnCondition> conditions, Holder<Item> baseItem, Optional<Integer> customModelData) implements AbstractFishVariant
{
    public static final Codec<AncientscaleVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(AncientscaleVariant::new);
    public static final Codec<Holder<AncientscaleVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.ANCIENTSCALE_VARIANT, DIRECT_CODEC);

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