package com.stevekung.fishofthieves.client.renderer.entity;

import com.stevekung.fishofthieves.client.renderer.entity.layers.GlowFishLayer;
import com.stevekung.fishofthieves.entity.GlowFish;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.animal.AbstractFish;

public abstract class ThievesFishRenderer<T extends AbstractFish & GlowFish, M extends EntityModel<T>> extends MobRenderer<T, M>
{
    protected ThievesFishRenderer(EntityRendererProvider.Context context, M entityModel, float shadowSize)
    {
        super(context, entityModel, shadowSize);
        this.addLayer(new GlowFishLayer<>(this));
    }
}