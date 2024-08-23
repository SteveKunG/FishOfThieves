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

public class PondieModel<S extends ThievesFishRenderState> extends EntityModel<S> implements HeadphoneModel.Scaleable<S>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(FishOfThieves.id("pondie"), "main");
    private final ModelPart root;
    private final ModelPart mouth;
    private final ModelPart body_back;

    public PondieModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.root = part;
        var head = part.getChild("head");
        this.mouth = head.getChild("mouth_r1");
        this.body_back = part.getChild("body_back");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        head.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(0, 20).addBox(-2.5F, 0.025F, -5.05F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -1.15F, -3.0F, -0.8727F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -2.19F, -2.3F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -4.26F, -3.0F, 0.48F, 0.0F, 0.0F));
        var body_main = partDefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -7.0F, -2.5F, 5.0F, 6.0F, 5.0F, CubeDeformation.NONE).texOffs(15, 0).addBox(-2.5F, -2.0F, -3.5F, 5.0F, 1.0F, 1.0F, CubeDeformation.NONE).texOffs(18, 18).addBox(0.0F, -8.5F, -2.0F, 0.0F, 2.0F, 4.0F, CubeDeformation.NONE).texOffs(15, 1).addBox(0.0F, -1.0F, -2.0F, 0.0F, 1.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 24.0F, 0.0F));
        body_main.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(24, 17).addBox(-3.5F, -5.0F, 0.5F, 3.0F, 3.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.25F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        body_main.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(18, 17).addBox(0.5F, -5.0F, 0.5F, 3.0F, 3.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.25F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(20, 4).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 2.0F, CubeDeformation.NONE).texOffs(20, 10).addBox(-1.0F, -1.5F, 1.5F, 2.0F, 3.0F, 2.0F, CubeDeformation.NONE).texOffs(0, 23).addBox(0.0F, -2.5F, 3.5F, 0.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 19.5F, 2.5F));
        body_back.addOrReplaceChild("right_fin_r2", CubeListBuilder.create().texOffs(24, 17).addBox(0.75F, 1.3F, 3.0F, 0.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.0F, 0.0F, -2.5F, 0.0F, 0.0F, 0.5236F));
        body_back.addOrReplaceChild("left_fin_r2", CubeListBuilder.create().texOffs(18, 17).addBox(-0.75F, 1.3F, 3.0F, 0.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.0F, 0.0F, -2.5F, 0.0F, 0.0F, -0.5236F));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(S renderState)
    {
        var backRotation = 1.0f;
        var backRotSpeed = 1.0f;
        var mouthSpeed = 0.15f;

        if (!renderState.isInWater && !renderState.isNoFlip)
        {
            backRotation = 1.5f;
            backRotSpeed = 1.7f;
            mouthSpeed = 2.1f;
        }
        this.body_back.yRot = -backRotation * 0.15f * Mth.sin(backRotSpeed * 0.65f * renderState.ageInTicks);
        this.mouth.xRot = -0.8727F + Mth.cos(mouthSpeed * renderState.ageInTicks) * (float) Math.PI * 0.02f;
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
        poseStack.scale(scale, scale - 0.5f, scale - 0.5f);
        poseStack.translate(0.0f, -0.3f, -0.025f);
    }
}