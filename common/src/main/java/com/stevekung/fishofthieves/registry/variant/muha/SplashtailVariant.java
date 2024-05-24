package com.stevekung.fishofthieves.registry.variant.muha;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.variant.muha.condition.SpawnCondition;
import com.stevekung.fishofthieves.registry.variant.muha.condition.SpawnConditions;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

public record SplashtailVariant(ResourceLocation texture, Optional<ResourceLocation> glowTexture, List<SpawnCondition> conditions) implements AbstractFishVariant
{
    //@formatter:off
    public static final Codec<SplashtailVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(variant -> variant.texture),
            ResourceLocation.CODEC.optionalFieldOf("glow_texture").forGetter(variant -> variant.glowTexture),
            SpawnConditions.DIRECT_CODEC.listOf().fieldOf("conditions").forGetter(condition -> condition.conditions)
    ).apply(instance, SplashtailVariant::new));
    //@formatter:on
    public static final Codec<Holder<SplashtailVariant>> CODEC = RegistryFileCodec.create(FOTRegistries.SPLASHTAIL_VARIANT, DIRECT_CODEC);

    @Override
    public ResourceLocation texture()
    {
        return fullTextureId(this.texture);
    }

    @Override
    public Optional<ResourceLocation> glowTexture()
    {
        return this.glowTexture.map(SplashtailVariant::fullTextureId);
    }

    private static ResourceLocation fullTextureId(ResourceLocation texture)
    {
        return texture.withPath(string -> "textures/" + string + ".png");
    }

    @Override
    public boolean equals(Object object)
    {
        if (object == this)
        {
            return true;
        }
        else if (!(object instanceof SplashtailVariant variant))
        {
            return false;
        }
        else
        {
            return Objects.equals(this.texture, variant.texture) && Objects.equals(this.glowTexture, variant.glowTexture) && Objects.equals(this.conditions, variant.conditions);
        }
    }
}