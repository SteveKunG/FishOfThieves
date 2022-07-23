package com.stevekung.fishofthieves.entity;

import java.util.Collections;
import java.util.Map;

import net.minecraft.resources.ResourceLocation;

public interface GlowFish
{
    default boolean canGlow()
    {
        return false;
    }

    default Map<FishVariant, ResourceLocation> getGlowTextureByType()
    {
        return Collections.emptyMap();
    }

    default float getGlowBrightness(float ageInTicks)
    {
        return 1.0F;
    }
}