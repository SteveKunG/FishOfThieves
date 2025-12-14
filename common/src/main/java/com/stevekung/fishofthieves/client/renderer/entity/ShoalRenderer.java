package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.ArrayList;
import java.util.List;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.client.renderer.entity.state.ShoalRenderState;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.mixin.client.accessor.EntityRenderDispatcherAccessor;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ShoalRenderer extends EntityRenderer<Shoal, ShoalRenderState>
{
    private final EntityRenderDispatcher entityRenderDispatcher;

    public ShoalRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.entityRenderDispatcher = context.getEntityRenderDispatcher();
    }

    @Override
    public ShoalRenderState createRenderState()
    {
        return new ShoalRenderState();
    }

    // For petals rotation equations credit to https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/client/render/block_entity/PetalApothecaryBlockEntityRenderer.java#L63
    // Under the Botania license.
    @Override
    public void render(ShoalRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
    {
        poseStack.pushPose();
        var ageInTicks = renderState.ageInTicks;
        var list = renderState.shoalFishClient;

        var offsetPerFish = 360F / list.size();
        var modifier = 15F;
        var rotationModifier = 0.25F;
        var radiusBase = 1.0F;
        var radiusMod = 0.1F;
        var v = 1F / 8F;

        poseStack.translate(-0.05F, 0F, 0F);

        for (var index = 0; index < list.size(); index++)
        {
            var livingEntityRenderState = list.get(index);
            var offset = offsetPerFish * index;
            float deg = (int) (ageInTicks / rotationModifier % 360F + offset);
            var rad = (float) (deg / 180F * Math.PI);
            var radiusX = (float) (radiusBase + radiusMod * Math.sin(ageInTicks / modifier));
            var radiusZ = (float) (radiusBase + radiusMod * Math.cos(ageInTicks / modifier));
            var x = (float) (radiusX * Math.cos(rad));
            var z = (float) (radiusZ * Math.sin(rad));
            var y = (float) Math.cos((ageInTicks + 50 * index) / 5F) / 10F;

            poseStack.pushPose();
            poseStack.translate(x, y, z);
            var yRotate = Math.max(0.6F, Mth.sin(ageInTicks * 0.1f) / 4.0F);
            var zRotate = (float) Math.cos(0.1f * ageInTicks) / 16F;

            v /= 2F;
            poseStack.translate(v, v, v);
            poseStack.mulPose(new Quaternionf().rotateAxis(-rad, 0, yRotate, zRotate));
            poseStack.translate(-v, -v, -v);
            v *= 2F;

            this.renderEntityInShoal(livingEntityRenderState, poseStack, bufferSource, packedLight);
            poseStack.popPose();
        }

        poseStack.popPose();
        super.render(renderState, poseStack, bufferSource, packedLight);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void extractRenderState(Shoal entity, ShoalRenderState renderState, float partialTicks)
    {
        super.extractRenderState(entity, renderState, partialTicks);
        List<EntityRenderState> entityRenderStateList = new ArrayList<>();

        for (var livingEntity : entity.getShoalFishClient())
        {
            var renderer = (EntityRenderer<? super Entity, ? super EntityRenderState>) ((EntityRenderDispatcherAccessor) this.entityRenderDispatcher).getRenderers().get(livingEntity.getType());
            var entityRenderState = renderer.createRenderState(livingEntity, 1.0f);
            entityRenderState.ageInTicks = entity.tickCount + partialTicks;
            entityRenderStateList.add(entityRenderState);
        }
        renderState.shoalFishClient = entityRenderStateList;
    }

    @SuppressWarnings("unchecked")
    private void renderEntityInShoal(EntityRenderState entityRenderState, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        var renderer = (EntityRenderer<? super Entity, ? super EntityRenderState>) ((EntityRenderDispatcherAccessor) this.entityRenderDispatcher).getRenderers().get(entityRenderState.entityType);
        renderer.render(entityRenderState, poseStack, buffer, packedLight);
    }
}