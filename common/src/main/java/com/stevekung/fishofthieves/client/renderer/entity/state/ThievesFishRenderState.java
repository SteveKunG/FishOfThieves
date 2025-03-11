package com.stevekung.fishofthieves.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class ThievesFishRenderState extends LivingEntityRenderState
{
    public boolean isTrophy;
    public boolean isNoFlip;
    public ResourceLocation fullTexture;
    public ResourceLocation fullGlowTexture;
    public float glowBrightness;
}