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

public class DevilfishModel<S extends ThievesFishRenderState> extends EntityModel<S> implements HeadphoneModel.Scaleable<S>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(FishOfThieves.id("devilfish"), "main");
    private final ModelPart root;
    private final ModelPart body_back;
    private final ModelPart mouth;

    public DevilfishModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.root = part;
        var head = part.getChild("head");
        this.body_back = part.getChild("body_back");
        this.mouth = head.getChild("mouth");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 18.5F, -3.5F));
        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(18, 7).addBox(-2.0F, -2.25F, -0.175F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 1.4F, -1.475F, 0.7854F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(18, 0).addBox(-2.0F, -2.63F, -0.35F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.63F, -0.15F, 0.5149F, 0.0F, 0.0F));
        var mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 0.25F));
        mouth.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(12, 17).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));
        mouth.addOrReplaceChild("mouth_r2", CubeListBuilder.create().texOffs(0, 11).addBox(-2.0F, 0.0F, -5.25F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.5F, -0.6109F, 0.0F, 0.0F));
        var body_main = partDefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.25F, -2.25F, 4.0F, 6.0F, 5.0F, CubeDeformation.NONE).texOffs(6, 22).addBox(0.0F, -5.25F, -2.25F, 0.0F, 2.0F, 5.0F, CubeDeformation.NONE).texOffs(6, 25).addBox(0.0F, 2.75F, -1.75F, 0.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 19.25F, -1.25F));
        body_main.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(0, 24).mirror().addBox(-3.0F, -9.5F, 0.0F, 3.0F, 3.0F, 0.0F, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-2.0F, 8.75F, 0.25F, 0.0F, 0.7854F, 0.0F));
        body_main.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(6, 24).addBox(0.0F, -9.5F, 0.0F, 3.0F, 3.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.0F, 8.75F, 0.25F, 0.0F, -0.7854F, 0.0F));
        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(0, 17).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 4.0F, 3.0F, CubeDeformation.NONE).texOffs(16, 22).addBox(0.0F, -4.0F, 0.0F, 0.0F, 2.0F, 2.0F, CubeDeformation.NONE).texOffs(0, 24).addBox(0.0F, -2.5F, 2.5F, 0.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 19.0F, 1.5F));
        body_back.addOrReplaceChild("right_fin_r2", CubeListBuilder.create().texOffs(16, 25).addBox(0.0F, 0.0F, 1.75F, 0.0F, 2.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.5F, 2.0F, -1.5F, 0.0F, 0.0F, 0.5236F));
        body_back.addOrReplaceChild("left_fin_r2", CubeListBuilder.create().texOffs(16, 27).addBox(0.0F, 0.0F, 1.75F, 0.0F, 2.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.5F, 2.0F, -1.5F, 0.0F, 0.0F, -0.5236F));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(S renderState)
    {
        var backRotation = 1.0f;
        var backRotSpeed = 1.0f;
        var mouthSpeed = 0.4f;

        if (!renderState.isInWater && !renderState.isNoFlip)
        {
            backRotation = 1.5f;
            backRotSpeed = 1.7f;
            mouthSpeed = 1.8f;
        }
        this.body_back.yRot = -backRotation * 0.2f * Mth.sin(backRotSpeed * 0.6f * renderState.ageInTicks);
        this.mouth.xRot = -0.0F + Mth.cos(mouthSpeed * renderState.ageInTicks) * (float) Math.PI * 0.06f;
    }

    @Override
    public ModelPart root()
    {
        return this.root;
    }

    @Override
    public void scale(S renderState, PoseStack poseStack)
    {
        var scale = 1.5f;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0f, -0.35f, -0.075f);
    }
}