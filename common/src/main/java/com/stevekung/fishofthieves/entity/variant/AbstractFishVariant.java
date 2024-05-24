package com.stevekung.fishofthieves.entity.variant;

import java.util.List;
import java.util.Optional;

import com.mojang.datafixers.Products;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.entity.condition.SpawnCondition;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public interface AbstractFishVariant
{
    ResourceLocation texture();

    ResourceLocation fullTexture();

    Optional<ResourceLocation> fullGlowTexture();

    Optional<ResourceLocation> glowTexture();

    List<SpawnCondition> conditions();

    static <T extends AbstractFishVariant> Products.P3<RecordCodecBuilder.Mu<T>, ResourceLocation, Optional<ResourceLocation>, List<SpawnCondition>> commonFields(RecordCodecBuilder.Instance<T> instance)
    {
        //@formatter:off
        return instance.group(
                ResourceLocation.CODEC.fieldOf("texture").forGetter(AbstractFishVariant::texture),
                ResourceLocation.CODEC.optionalFieldOf("glow_texture").forGetter(AbstractFishVariant::glowTexture),
                FOTSpawnConditions.DIRECT_CODEC.listOf().fieldOf("conditions").forGetter(AbstractFishVariant::conditions)
        );
        //@formatter:on
    }

    static ResourceLocation fullTextureId(ResourceLocation texture)
    {
        return texture.withPath(string -> "textures/" + string + ".png");
    }

    static <T extends AbstractFishVariant> Holder<T> getSpawnVariant(RegistryAccess registryAccess, ResourceKey<? extends Registry<? extends T>> registryKey, ResourceKey<T> defaultKey, LivingEntity livingEntity, boolean fromBucket)
    {
        var registry = registryAccess.registryOrThrow(registryKey);
        var muha = Util.getRandomSafe(registry.holders().filter(variant -> fromBucket || Util.allOf(variant.value().conditions()).test(livingEntity)).toList(), livingEntity.getRandom());
        return muha.orElseGet(() -> registry.getHolderOrThrow(defaultKey));
    }
}