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

public record WildsplashVariant(String name, ResourceLocation texture, Optional<ResourceLocation> glowTexture, List<SpawnCondition> conditions, Optional<List<SpawnCondition>> fishingOverride, Holder<Item> baseItem, int customModelData) implements AbstractFishVariant
{
    public static final Codec<WildsplashVariant> DIRECT_CODEC = AbstractFishVariant.simpleCodec(WildsplashVariant::new);
    public static final Codec<Holder<WildsplashVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.WILDSPLASH_VARIANT, DIRECT_CODEC);

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