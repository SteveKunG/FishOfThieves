package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.PufferfishRenderState;
import net.minecraft.client.renderer.entity.state.TropicalFishRenderState;
import net.minecraft.world.entity.animal.TropicalFish;

public class HeadphoneModel<S extends EntityRenderState> extends EntityModel<S>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(FishOfThieves.id("headphone"), "main");

    public HeadphoneModel(ModelPart part)
    {
        super(part);
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("headphone", CubeListBuilder.create().texOffs(0, 3).addBox(1.5F, -7.5F, -1.0F, 1.0F, 2.0F, 2.0F).texOffs(0, 3).addBox(-2.5F, -7.5F, -1.0F, 1.0F, 2.0F, 2.0F).texOffs(0, 0).addBox(-2.0F, -9.5F, -0.5F, 4.0F, 2.0F, 1.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Override
    public void setupAnim(S renderState) {}

    public interface Scaleable<S extends LivingEntityRenderState>
    {
        HeadphoneModel.Scaleable<LivingEntityRenderState> COD = (entity, poseStack) -> poseStack.translate(0.0f, 0.25f, -0.05f);
        HeadphoneModel.Scaleable<LivingEntityRenderState> SALMON = (entity, poseStack) ->
        {
            poseStack.scale(1.5f, 1.5f, 1.5f);
            poseStack.translate(0.0f, -0.275f, 0.0f);
        };
        HeadphoneModel.Scaleable<PufferfishRenderState> PUFFERFISH = (renderState, poseStack) ->
        {
            var y = 0.0f;

            switch (renderState.puffState)
            {
                case 0 ->
                {
                    y = 0.25f;
                    poseStack.scale(1.25f, 1.0f, 0.75f);
                }
                case 1 ->
                {
                    y = -0.275f;
                    poseStack.scale(1.75f, 1.5f, 1.5f);
                }
                case 2 ->
                {
                    y = -0.55f;
                    poseStack.scale(3.0f, 2.0f, 2.0f);
                }
            }
            poseStack.translate(0.0f, y, -0.025f);
        };
        HeadphoneModel.Scaleable<TropicalFishRenderState> TROPICAL_FISH = (renderState, poseStack) ->
        {
            var baseVariant = renderState.variant.base();
            var y = 0.0f;
            var z = 0.0f;

            if (baseVariant == TropicalFish.Base.SMALL)
            {
                y = 0.275f;
                z = -0.08f;
            }
            else
            {
                y = -0.325f;
                z = -0.1f;
                poseStack.scale(1.0f, 1.5f, 1.0f);
            }
            poseStack.translate(0.0f, y, z);
        };
        HeadphoneModel.Scaleable<LivingEntityRenderState> TADPOLE = (entity, poseStack) ->
        {
            poseStack.scale(1.1f, 1.1f, 1.1f);
            poseStack.translate(0.0f, 0.15f, -0.08f);
        };

        void scale(S renderState, PoseStack poseStack);
    }
}