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

public class PlentifinModel<S extends ThievesFishRenderState> extends EntityModel<S> implements HeadphoneModel.Scaleable<S>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(FishOfThieves.id("plentifin"), "main");
    private final ModelPart body_back;
    private final ModelPart body_back_2;

    public PlentifinModel(ModelPart part)
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
        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(9, 14).addBox(-1.0F, -0.07F, -1.97F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(0.0F, -1.0F, -4.25F, -0.3054F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(14, 0).addBox(-1.0F, -1.835F, -2.975F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -3.515F, -3.575F, 1.1781F, 0.0F, 0.0F));
        var body_main = partDefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -6.1F, -4.5F, 2.0F, 6.0F, 5.0F, CubeDeformation.NONE).texOffs(0, 7).addBox(0.0F, -8.65F, -3.5F, 0.0F, 3.0F, 4.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 24.0F, 0.0F));
        body_main.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(14, 20).addBox(-0.75F, -3.65F, -3.5F, 3.0F, 3.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.75F, 0.0F, 2.0F, 0.0F, 0.9599F, 0.0F));
        body_main.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(14, 17).addBox(0.0F, -3.65F, 0.0F, 3.0F, 3.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.75F, 0.0F, -2.0F, 0.0F, -0.9599F, 0.0F));
        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(14, 8).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 2.0F, CubeDeformation.NONE).texOffs(1, 11).addBox(0.0F, -5.15F, 0.0F, 0.0F, 4.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 20.5F, 0.5F));
        body_back.addOrReplaceChild("right_fin_r2", CubeListBuilder.create().texOffs(6, 14).addBox(0.0F, 0.0F, 0.5F, 0.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.5F, 1.5F, -0.5F, 0.0F, 0.0F, 0.5236F));
        body_back.addOrReplaceChild("left_fin_r2", CubeListBuilder.create().texOffs(6, 16).addBox(0.0F, 0.0F, 0.5F, 0.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.5F, 1.5F, -0.5F, 0.0F, 0.0F, -0.5236F));
        body_back.addOrReplaceChild("body_back_2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 1.0F, CubeDeformation.NONE).texOffs(0, 15).addBox(0.0F, -2.65F, 1.0F, 0.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 2.0F));
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
        this.body_back.yRot = -backRotation * 0.2f * Mth.sin(backRotSpeed * 0.65f * renderState.ageInTicks);
        this.body_back_2.yRot = -backRotation * 0.3f * Mth.sin(backRotSpeed * 0.65f * renderState.ageInTicks);
    }

    @Override
    public void scale(S renderState, PoseStack poseStack)
    {
        var scale = 1.25f;
        poseStack.scale(scale, scale + 0.5f, scale);
        poseStack.translate(0.0f, -0.375f, -0.125f);
    }
}