package com.stevekung.fishofthieves.entity;

import java.util.Map;

import net.minecraft.resources.ResourceLocation;

public interface GlowFish extends ThievesFish
{
    boolean canGlow();
    Map<FishVariant, ResourceLocation> getGlowTextureByType();
}