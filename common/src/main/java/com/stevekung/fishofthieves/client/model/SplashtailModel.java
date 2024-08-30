package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.client.renderer.entity.state.ThievesFishRenderState;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class SplashtailModel<S extends ThievesFishRenderState> extends EntityModel<S> implements HeadphoneModel.Scaleable<S>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(FishOfThieves.id("splashtail"), "main");
    private final ModelPart body_back;

    public SplashtailModel(ModelPart part)
    {
        super(part, RenderType::entityCutout);
        this.body_back = part.getChild("body_back");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(16, 0).addBox(-1.5F, 0.75F, -4.3F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, -3.75F, -4.0F, -0.1309F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.25F, -4.25F, 3.0F, 3.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -3.75F, -4.0F, 0.0873F, 0.0F, 0.0F));
        var body_main = partDefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, -6.25F, -3.5F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.01F)).texOffs(20, 14).addBox(0.0F, -8.25F, -0.5F, 0.0F, 2.0F, 3.0F, CubeDeformation.NONE).texOffs(20, 10).addBox(0.0F, -1.25F, -2.5F, 0.0F, 1.0F, 6.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 24.0F, 0.0F));
        body_main.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(20, 22).addBox(-2.25F, -4.25F, -3.0F, 3.0F, 3.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.5F, 0.0F, 0.5F, 0.0F, 0.7854F, 0.0F));
        body_main.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(20, 19).addBox(-0.75F, -4.25F, -3.0F, 3.0F, 3.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.5F, 0.0F, -0.7854F, 0.0F));
        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(0, 19).addBox(-1.5F, -1.75F, 0.0F, 3.0F, 4.0F, 6.0F, CubeDeformation.NONE).texOffs(16, 6).addBox(-1.5F, -2.5F, -0.25F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.2F)).texOffs(20, 6).addBox(0.0F, -5.25F, 0.0F, 0.0F, 4.0F, 6.0F, CubeDeformation.NONE).texOffs(20, 20).addBox(0.0F, -2.25F, 6.0F, 0.0F, 5.0F, 5.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 20.5F, 2.5F));
        body_back.addOrReplaceChild("right_fin_r2", CubeListBuilder.create().texOffs(26, 16).addBox(1.125F, 1.95F, 5.0F, 0.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.0F, -0.5F, -2.5F, 0.0F, 0.0F, 0.5236F));
        body_back.addOrReplaceChild("left_fin_r2", CubeListBuilder.create().texOffs(26, 14).addBox(-1.125F, 1.95F, 5.0F, 0.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.0F, -0.5F, -2.5F, 0.0F, 0.0F, -0.5236F));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(S renderState)
    {
        var backRotation = 1.0f;
        var backRotSpeed = 1.0f;

        if (!renderState.isInWater && !renderState.isNoFlip)
        {
            backRotation = 1.5f;
            backRotSpeed = 1.7f;
        }
        this.body_back.yRot = -backRotation * 0.2f * Mth.sin(backRotSpeed * 0.6f * renderState.ageInTicks);
    }

    @Override
    public void scale(S renderState, PoseStack poseStack)
    {
        var scale = 1.5f;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0f, -0.275f, -0.075f);
    }
}