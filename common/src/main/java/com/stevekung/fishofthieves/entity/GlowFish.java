package com.stevekung.fishofthieves.entity;

public interface GlowFish
{
    default float getGlowBrightness(float ageInTicks)
    {
        return 1.0F;
    }
}