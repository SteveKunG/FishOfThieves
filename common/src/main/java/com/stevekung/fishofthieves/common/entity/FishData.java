package com.stevekung.fishofthieves.common.entity;

import java.util.Optional;
import java.util.function.Predicate;

import com.stevekung.fishofthieves.common.spawn.SpawnConditionContext;
import net.minecraft.resources.ResourceLocation;

public interface FishData
{
    Predicate<SpawnConditionContext> getCondition();

    ResourceLocation getTexture();

    Optional<ResourceLocation> getGlowTexture();
}