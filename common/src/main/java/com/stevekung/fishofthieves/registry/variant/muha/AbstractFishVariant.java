package com.stevekung.fishofthieves.registry.variant.muha;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.registry.variant.muha.condition.SpawnCondition;
import net.minecraft.resources.ResourceLocation;

public interface AbstractFishVariant
{
    ResourceLocation texture();

    Optional<ResourceLocation> glowTexture();

    List<SpawnCondition> conditions();
}