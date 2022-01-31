package com.stevekung.fishofthieves.client.renderer;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.client.model.ScaleableModel;
import com.stevekung.fishofthieves.client.renderer.entity.layers.GlowFishLayer;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.entity.FishVariant;
import com.stevekung.fishofthieves.entity.ThievesFish;

import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.AbstractFish;

public abstract class ThievesFishRenderer<T extends AbstractFish & ThievesFish, M extends EntityModel<T> & ScaleableModel<T>> extends MobRenderer<T, M>
{
    protected ThievesFishRenderer(EntityRendererProvider.Context context, M entityModel, float shadowSize)
    {
        super(context, entityModel, shadowSize);
        this.addLayer(new GlowFishLayer<>(this));
        this.addLayer(new HeadphoneLayer<>(this, context));
    }

    @Override
    protected void scale(T livingEntity, PoseStack poseStack, float partialTickTime)
    {
        var scale = livingEntity.isTrophy() ? 1.0F : 0.5F;
        poseStack.scale(scale, scale, scale);
    }

    protected static Map<FishVariant, ResourceLocation> createTextureByType(FishVariant[] variants, String name)
    {
        return Util.make(Maps.newHashMap(), map -> map.putAll(Stream.of(variants).collect(ImmutableMap.toImmutableMap(Function.identity(), variant -> new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/%s/%s.png".formatted(name, variant.getName()))))));
    }
}