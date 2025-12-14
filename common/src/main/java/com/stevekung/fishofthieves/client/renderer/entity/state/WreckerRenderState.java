package com.stevekung.fishofthieves.client.renderer.entity.state;

import java.util.function.Function;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.WreckerVariant;
import com.stevekung.fishofthieves.registry.variant.WreckerVariants;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;

public class WreckerRenderState extends ThievesFishRenderState
{
    public static final Function<Holder<WreckerVariant>, Identifier> BULB_BY_VARIANT = variant -> FishOfThieves.id("textures/entity/wrecker/%s_bulb.png".formatted((variant.value().treasured().isPresent() ? "treasured/" : "") + variant.unwrapKey().orElse(WreckerVariants.ROSE).identifier().getPath()));

    public Identifier bulbTexture;
}