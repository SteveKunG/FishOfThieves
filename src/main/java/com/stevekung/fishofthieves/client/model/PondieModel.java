package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Pondie;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PondieModel<T extends Pondie> extends EntityModel<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(FishOfThieves.MOD_ID, "pondie"), "main");
    private final ModelPart body_front;
    private final ModelPart body_back;
    private final ModelPart head;
    private final ModelPart mouth;

    public PondieModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.body_front = part.getChild("body_front");
        this.body_back = part.getChild("body_back");
        this.head = part.getChild("head");
        this.mouth = this.head.getChild("head_r1");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var body_front = partDefinition.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.0F, 0.0F, 5.0F, 6.0F, 5.0F)
                .texOffs(15, 0).addBox(-2.5F, 4.0F, -1.0F, 5.0F, 1.0F, 1.0F)
                .texOffs(15, 2).addBox(-2.5F, -0.25F, 0.75F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 18.0F, -2.5F));

        body_front.addOrReplaceChild("fin_back_1", CubeListBuilder.create().texOffs(18, 18).addBox(0.0F, -0.5F, 0.0F, 0.0F, 2.0F, 4.0F), PartPose.offset(0.0F, -2.0F, 0.5F));

        body_front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(24, 17).addBox(-3.5303F, 0.0F, 0.5303F, 3.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(-2.25F, 1.0F, 2.5F, 0.0F, 0.7854F, 0.0F));

        body_front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(18, 17).addBox(0.5303F, 0.0F, 0.5303F, 3.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(2.25F, 1.0F, 2.5F, 0.0F, -0.7854F, 0.0F));

        body_front.addOrReplaceChild("fin_under", CubeListBuilder.create().texOffs(15, 1).addBox(0.0F, 1.5F, -1.5F, 0.0F, 1.0F, 3.0F), PartPose.offset(0.0F, 3.5F, 2.0F));

        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(20, 10).addBox(-1.0F, -1.5F, 1.5F, 2.0F, 3.0F, 2.0F)
                .texOffs(20, 4).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 2.0F), PartPose.offset(0.0F, 19.5F, 2.5F));

        body_back.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(24, 17).addBox(0.75F, 1.299F, -1.5F, 0.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(-1.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        body_back.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(18, 17).addBox(-0.75F, 1.299F, -1.5F, 0.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(1.0F, 0.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        body_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 23).addBox(0.0F, -1.0F, 0.0F, 0.0F, 5.0F, 3.0F), PartPose.offset(0.0F, -1.5F, 3.5F));

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, -2.5F));

        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 20).addBox(-2.51F, 0.0047F, -4.9997F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.01F, 4.8672F, -0.5987F, -0.8727F, 0.0F, 0.0F));

        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -2.25F, -2.25F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 1.8038F, -0.5133F, 0.48F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        var backRotation = 1.0f;
        var backRotSpeed = 1.0f;
        var mouthSpeed = 0.15f;

        if (!entity.isInWater())
        {
            backRotation = 1.5f;
            backRotSpeed = 1.7f;
            mouthSpeed = 2.1f;
        }
        this.body_back.yRot = -backRotation * 0.15f * Mth.sin(backRotSpeed * 0.65f * ageInTicks);
        this.mouth.xRot = -0.8727F + Mth.cos(mouthSpeed * ageInTicks) * (float)Math.PI * 0.02f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.body_front.render(poseStack, buffer, packedLight, packedOverlay);
        this.body_back.render(poseStack, buffer, packedLight, packedOverlay);
        this.head.render(poseStack, buffer, packedLight, packedOverlay);
    }
}