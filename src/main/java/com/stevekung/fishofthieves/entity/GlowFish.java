package com.stevekung.fishofthieves.entity;

import java.util.Map;

import net.minecraft.resources.ResourceLocation;

public interface GlowFish
{
    boolean canGlow();
    Map<FishType, ResourceLocation> getGlowTextureByType();
    FishType getVariant();
}