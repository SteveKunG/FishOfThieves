package com.stevekung.fishofthieves.entity;

import java.util.Map;

import net.minecraft.resources.ResourceLocation;

public interface GlowFish
{
    boolean canGlow();
    Map<ThievesFish.FishVariant, ResourceLocation> getGlowTextureByType();

    default float getGlowBrightness(float ageInTicks)
    {
        return 1.0F;
    }
}