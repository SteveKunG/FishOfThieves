package com.stevekung.fishofthieves.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class ThievesFishRenderState extends LivingEntityRenderState
{
    public boolean isTrophy;
    public boolean isNoFlip;
    public boolean hasImpulse;
    public Identifier fullTexture;
    public Identifier fullGlowTexture;
    public float glowBrightness;
    public boolean isTreasured;
}