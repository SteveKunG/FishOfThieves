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

public class WildsplashModel<S extends ThievesFishRenderState> extends EntityModel<S> implements HeadphoneModel.Scaleable<S>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(FishOfThieves.id("wildsplash"), "main");
    private final ModelPart root;
    private final ModelPart body_back;
    private final ModelPart body_back_2;

    public WildsplashModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.root = part;
        this.body_back = part.getChild("body_back");
        this.body_back_2 = this.body_back.getChild("body_back_2");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        head.addOrReplaceChild("right_barbel_r1", CubeListBuilder.create().texOffs(2, 19).addBox(0.0F, 0.0F, -6.0F, 0.0F, 3.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.5F, -2.0F, 0.0F, 0.0F, 0.0F, 0.1745F));
        head.addOrReplaceChild("left_barbel_r1", CubeListBuilder.create().texOffs(0, 19).addBox(0.0F, 0.0F, -6.0F, 0.0F, 3.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.5F, -2.0F, 0.0F, 0.0F, 0.0F, -0.1745F));
        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 28).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -1.5F, -4.5F, -0.5672F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 19).addBox(-2.5F, -2.254F, -3.294F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -4.756F, -4.256F, 0.9163F, 0.0F, 0.0F));
        var body_main = partDefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.5F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.01F)).texOffs(22, 1).addBox(0.0F, -2.5F, -2.0F, 0.0F, 1.0F, 5.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 18.0F, -2.0F));
        body_main.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-3.5F, -5.5F, 0.5F, 3.0F, 4.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.25F, 6.0F, -0.5F, 0.0F, 0.7854F, 0.0F));
        body_main.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(16, 11).addBox(0.5F, -5.5F, 0.5F, 3.0F, 4.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.25F, 6.0F, -0.5F, 0.0F, -0.7854F, 0.0F));
        body_main.addOrReplaceChild("body_main_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -1.75F, -6.0F, 5.0F, 2.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 3.5F, 3.0F, 0.192F, 0.0F, 0.0F));
        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(20, 21).addBox(-1.5F, -1.85F, 0.0F, 3.0F, 4.0F, 3.0F, CubeDeformation.NONE).texOffs(22, 5).addBox(0.0F, -3.25F, -0.75F, 0.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 19.0F, 1.0F));
        body_back.addOrReplaceChild("right_fin_r2", CubeListBuilder.create().texOffs(23, 9).addBox(0.25F, 1.0F, 1.5F, 0.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.75F, 1.0F, -1.0F, 0.0F, 0.0F, 0.5236F));
        body_back.addOrReplaceChild("left_fin_r2", CubeListBuilder.create().texOffs(23, 11).addBox(-0.25F, 1.0F, 1.5F, 0.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.75F, 1.0F, -1.0F, 0.0F, 0.0F, -0.5236F));
        body_back.addOrReplaceChild("body_back_2", CubeListBuilder.create().texOffs(17, 0).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 2.0F, CubeDeformation.NONE).texOffs(0, -3).addBox(0.0F, -2.5F, 1.5F, 0.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 0.25F, 3.0F));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(S renderState)
    {
        var backRotation = 1.0f;
        var backRotSpeed = 1.0f;

        if (!renderState.isInWater && !renderState.isNoFlip)
        {
            backRotation = 1.6f;
            backRotSpeed = 1.8f;
        }
        this.body_back.yRot = -backRotation * 0.15f * Mth.sin(backRotSpeed * 0.7f * renderState.ageInTicks);
        this.body_back_2.yRot = -backRotation * 0.2f * Mth.sin(backRotSpeed * 0.7f * renderState.ageInTicks);
    }

    @Override
    public ModelPart root()
    {
        return this.root;
    }

    @Override
    public void scale(S renderState, PoseStack poseStack)
    {
        var scale = 2.0f;
        poseStack.scale(scale, scale - 0.5f, scale - 0.25f);
        poseStack.translate(0.0f, -0.3f, -0.1f);
    }
}