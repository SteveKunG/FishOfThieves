package com.stevekung.fishofthieves.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.Splashtail;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SplashtailModel<T extends Splashtail> extends EntityModel<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(FishOfThieves.MOD_ID, "splashtail"), "main");
    private final ModelPart body_front;
    private final ModelPart body_back;
    private final ModelPart head;

    public SplashtailModel(ModelPart part)
    {
        this.body_front = part.getChild("body_front");
        this.body_back = part.getChild("body_back");
        this.head = part.getChild("head");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var body_front = partDefinition.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, -0.25F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(11, 0).addBox(-1.5F, 0.25F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 18.0F, -0.5F));

        body_front.addOrReplaceChild("fin_back_1", CubeListBuilder.create().texOffs(20, 14).addBox(0.0F, 1.25F, 0.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, 0.0F));

        body_front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(20, 22).addBox(-3.0F, 0.75F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -2.0F, 0.0F, 0.7854F, 0.0F));

        body_front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(20, 19).addBox(0.0F, 0.75F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

        body_front.addOrReplaceChild("fin_under", CubeListBuilder.create().texOffs(20, 10).addBox(0.0F, 2.25F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 1.0F));

        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(16, 6).addBox(-1.5F, 0.0F, -0.25F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.2F))
                .texOffs(0, 19).addBox(-1.5F, 0.75F, 0.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 2.5F));

        body_back.addOrReplaceChild("fin_back_2", CubeListBuilder.create().texOffs(20, 6).addBox(0.0F, 0.75F, 0.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, 0.0F));

        body_back.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(26, 16).addBox(1.125F, 1.9486F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.5236F));

        body_back.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(26, 14).addBox(-1.125F, 1.9486F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, 4.0F, 0.0F, 0.0F, -0.5236F));

        body_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(20, 20).addBox(0.0F, -0.25F, 0.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 6.0F));

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, -3.5F));

        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(16, 0).addBox(-1.5F, 1.0F, -4.3F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 2.25F, -0.5F, -0.1309F, 0.0F, 0.0F));

        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.25F, -4.25F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.25F, -0.5F, 0.0873F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        var f = 1.0f;

        if (!entity.isInWater())
        {
            f = 1.5f;
        }
        this.body_back.yRot = -f * 0.2f * Mth.sin(0.6f * ageInTicks);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.body_front.render(poseStack, buffer, packedLight, packedOverlay);
        this.body_back.render(poseStack, buffer, packedLight, packedOverlay);
        this.head.render(poseStack, buffer, packedLight, packedOverlay);
    }
}