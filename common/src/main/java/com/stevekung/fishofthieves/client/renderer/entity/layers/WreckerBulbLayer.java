package com.stevekung.fishofthieves.client.renderer.entity.layers;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.FishVariant;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.animal.Wrecker.Variant;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class WreckerBulbLayer<T extends LivingEntity & ThievesFish, M extends EntityModel<T>> extends RenderLayer<T, M>
{
    private static final Map<FishVariant, ResourceLocation> BULB_BY_TYPE = Util.make(Maps.newHashMap(), map -> map.putAll(Stream.of(Variant.BY_ID).collect(ImmutableMap.toImmutableMap(Function.identity(), variant -> new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wrecker/%s_bulb.png".formatted(variant.getName()))))));

    public WreckerBulbLayer(RenderLayerParent<T, M> renderLayerParent)
    {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (!livingEntity.isInvisible())
        {
            var vertexConsumer = buffer.getBuffer(RenderType.eyes(BULB_BY_TYPE.get(livingEntity.getVariant())));
            var color = Mth.clamp(1.0F + Mth.cos(ageInTicks * 0.05f), 0.25F, 1.0F);
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, color, color, color, 1.0f);
        }
    }
}