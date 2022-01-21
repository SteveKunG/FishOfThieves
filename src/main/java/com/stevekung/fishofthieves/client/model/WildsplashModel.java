package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Wildsplash;

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

public class WildsplashModel<T extends Wildsplash> extends EntityModel<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(FishOfThieves.MOD_ID, "wildsplash"), "main");
    private final ModelPart body_front;
    private final ModelPart body_back;
    private final ModelPart body_back_2;
    private final ModelPart head;

    public WildsplashModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.body_front = part.getChild("body_front");
        this.body_back = part.getChild("body_back");
        this.head = part.getChild("head");
        this.body_back_2 = this.body_back.getChild("body_back_2");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var body_front = partDefinition.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.5F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(20, 19).addBox(-2.5F, -0.75F, -3.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 18.0F, -2.0F));

        body_front.addOrReplaceChild("body_front_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -1.75F, -6.0F, 5.0F, 2.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 3.5F, 3.0F, 0.192F, 0.0F, 0.0F));

        body_front.addOrReplaceChild("fin_back_1", CubeListBuilder.create().texOffs(22, 1).addBox(0.0F, 0.0F, -5.0F, 0.0F, 1.0F, 5.0F), PartPose.offset(0.0F, -2.5F, 3.0F));

        body_front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(0, 11).addBox(-3.5303F, -0.5F, 0.5303F, 3.0F, 4.0F, 0.0F), PartPose.offsetAndRotation(-2.25F, 1.0F, -0.5F, 0.0F, 0.7854F, 0.0F));

        body_front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(16, 11).addBox(0.5303F, -0.5F, 0.5303F, 3.0F, 4.0F, 0.0F), PartPose.offsetAndRotation(2.25F, 1.0F, -0.5F, 0.0F, -0.7854F, 0.0F));

        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(20, 21).addBox(-1.5F, -1.85F, 0.0F, 3.0F, 4.0F, 3.0F), PartPose.offset(0.0F, 19.0F, 1.0F));

        body_back.addOrReplaceChild("fin_back_2", CubeListBuilder.create().texOffs(22, 5).addBox(0.0F, 0.25F, -0.75F, 0.0F, 2.0F, 3.0F), PartPose.offset(0.0F, -3.5F, 0.0F));

        body_back.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(23, 9).addBox(0.5F, 0.866F, -1.5F, 0.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(-1.0F, 0.75F, 2.5F, 0.0F, 0.0F, 0.5236F));

        body_back.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(23, 11).addBox(-0.5F, 0.866F, -1.5F, 0.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(1.0F, 0.75F, 2.5F, 0.0F, 0.0F, -0.5236F));

        body_back.addOrReplaceChild("body_back_2", CubeListBuilder.create().texOffs(17, 0).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 2.0F)
                .texOffs(0, -3).addBox(0.0F, -2.5F, 1.5F, 0.0F, 5.0F, 3.0F), PartPose.offset(0.0F, 0.25F, 3.0F));

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 18.5F, -5.0F));

        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 28).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 1.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 3.917F, 0.4641F, -0.5672F, 0.0F, 0.0F));

        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 19).addBox(-2.5F, -2.25F, -3.25F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.7581F, 0.7197F, 0.9163F, 0.0F, 0.0F));

        head.addOrReplaceChild("comb_r1", CubeListBuilder.create().texOffs(2, 19).addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(-2.52F, 3.4299F, 0.1333F, 0.0F, 0.0F, 0.1745F));

        head.addOrReplaceChild("comb_r2", CubeListBuilder.create().texOffs(0, 19).addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(2.52F, 3.4299F, 0.1333F, 0.0F, 0.0F, -0.1745F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        var backRotation = 1.0f;
        var backRotSpeed = 1.0f;

        if (!entity.isInWater())
        {
            backRotation = 1.6f;
            backRotSpeed = 1.8f;
        }
        this.body_back.yRot = -backRotation * 0.15f * Mth.sin(backRotSpeed * 0.7f * ageInTicks);
        this.body_back_2.yRot = -backRotation * 0.2f * Mth.sin(backRotSpeed * 0.7f * ageInTicks);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.body_front.render(poseStack, buffer, packedLight, packedOverlay);
        this.body_back.render(poseStack, buffer, packedLight, packedOverlay);
        this.head.render(poseStack, buffer, packedLight, packedOverlay);
    }
}