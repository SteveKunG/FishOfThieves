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

public class IslehopperModel<S extends ThievesFishRenderState> extends EntityModel<S> implements HeadphoneModel.Scaleable<S>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(FishOfThieves.id("islehopper"), "main");
    private final ModelPart body_back;
    private final ModelPart body_back_2;

    public IslehopperModel(ModelPart part)
    {
        super(part, RenderType::entityCutout);
        this.body_back = part.getChild("body_back");
        this.body_back_2 = this.body_back.getChild("body_back_2");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(18, 10).addBox(-2.0F, -3.15F, -0.775F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, -1.1F, -2.475F, 1.1781F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -2.32F, 0.425F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -0.98F, -3.475F, 1.0908F, 0.0F, 0.0F));
        var body_main = partDefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -3.5F, 4.0F, 5.0F, 5.0F, CubeDeformation.NONE).texOffs(18, 0).addBox(-2.5F, -5.25F, -3.75F, 5.0F, 2.0F, 2.0F, CubeDeformation.NONE).texOffs(0, 14).addBox(0.0F, -7.0F, -3.5F, 0.0F, 1.0F, 5.0F, CubeDeformation.NONE).texOffs(0, 17).addBox(0.0F, -1.0F, -3.5F, 0.0F, 1.0F, 5.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 24.0F, 0.0F));
        body_main.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(6, 25).addBox(-2.35F, -3.5F, -0.75F, 3.0F, 2.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        body_main.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(6, 27).addBox(-0.65F, -3.5F, -0.75F, 3.0F, 2.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        body_main.addOrReplaceChild("bottom_right_fin_r1", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, -3.5F, 0.0F, 1.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
        body_main.addOrReplaceChild("bottom_left_fin_r1", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -3.5F, 0.0F, 1.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        body_main.addOrReplaceChild("top_left_fin_r1", CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, -1.0F, -3.5F, 0.0F, 1.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
        body_main.addOrReplaceChild("top_right_fin_r1", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, -1.0F, -3.5F, 0.0F, 1.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.0F, -6.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(18, 4).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 20.25F, 1.5F));
        body_back.addOrReplaceChild("bottom_right_fin_r2", CubeListBuilder.create().texOffs(11, 18).addBox(0.0F, 0.0F, 1.5F, 0.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.5F, 2.0F, -1.5F, 0.0F, 0.0F, 0.7854F));
        body_back.addOrReplaceChild("bottom_left_fin_r2", CubeListBuilder.create().texOffs(11, 19).addBox(0.0F, 0.0F, 1.5F, 0.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.5F, 2.0F, -1.5F, 0.0F, 0.0F, -0.7854F));
        body_back.addOrReplaceChild("top_right_fin_r2", CubeListBuilder.create().texOffs(11, 16).addBox(0.0F, -1.0F, 1.5F, 0.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.5F, -2.0F, -1.5F, 0.0F, 0.0F, -0.7854F));
        body_back.addOrReplaceChild("top_left_fin_r2", CubeListBuilder.create().texOffs(11, 17).addBox(0.0F, -1.0F, 1.5F, 0.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.5F, -2.0F, -1.5F, 0.0F, 0.0F, 0.7854F));
        body_back.addOrReplaceChild("body_back_2", CubeListBuilder.create().texOffs(18, 14).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 2.0F, CubeDeformation.NONE).texOffs(0, 22).addBox(0.0F, -2.5F, 1.5F, 0.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, -0.25F, 2.0F));
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
        this.body_back.yRot = -backRotation * 0.05f * Mth.sin(backRotSpeed * 0.6f * renderState.ageInTicks);
        this.body_back_2.yRot = -backRotation * 0.1f * Mth.sin(backRotSpeed * 0.6f * renderState.ageInTicks);
    }

    @Override
    public void scale(S renderState, PoseStack poseStack)
    {
        var scale = 2.0f;
        poseStack.scale(scale, scale - 0.5f, scale - 0.5f);
        poseStack.translate(0.0f, -0.265f, -0.06f);
    }
}