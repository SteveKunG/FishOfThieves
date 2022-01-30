package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Stormfish;

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

public class StormfishModel<T extends Stormfish> extends EntityModel<T> implements ScaleableModel<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(FishOfThieves.MOD_ID, "stormfish"), "main");
    private final ModelPart head;
    private final ModelPart body_front;
    private final ModelPart body_back;
    private final ModelPart body_back_2;

    public StormfishModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.head = part.getChild("head");
        this.body_front = part.getChild("body_front");
        this.body_back = part.getChild("body_back");
        this.body_back_2 = this.body_back.getChild("body_back_2");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.5F, -4.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 19.75F, -3.0F));

        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(25, 21).addBox(-0.09F, -1.01F, -5.01F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.01F, 2.11F, -3.99F, 0.0874F, 0.0522F, 0.0046F));

        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(25, 21).addBox(-0.9F, -1.0F, -5.0F, 1.0F, 1.0F, 5.0F), PartPose.offsetAndRotation(0.0F, 2.1F, -4.0F, 0.0874F, -0.0522F, -0.0046F));

        head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-1.5037F, 0.01F, -4.0709F, 2.0F, 6.0F, 4.0F).mirror(false), PartPose.offsetAndRotation(0.0F, -3.01F, 1.06F, 0.0F, -0.1222F, 0.0F));

        head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(0, 13).addBox(-0.4963F, 0.01F, -4.0709F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -3.01F, 1.06F, 0.0F, 0.1222F, 0.0F));

        head.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(12, 14).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 3.0F, 5.0F), PartPose.offsetAndRotation(0.0F, -2.973F, -3.1772F, 1.0297F, 0.0F, 0.0F));

        head.addOrReplaceChild("front_head_fin", CubeListBuilder.create().texOffs(40, -3).addBox(0.0F, -5.0F, -3.0F, 0.0F, 5.0F, 3.0F), PartPose.offset(0.0F, -3.0F, 0.0F));

        var mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, -3.0F, -0.1745F, 0.0F, 0.0F));

        mouth.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(26, 15).addBox(-0.9F, -1.0F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(0.0F, 0.9848F, 0.1736F, 0.1313F, -0.0779F, -0.0103F));

        mouth.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(26, 15).addBox(-0.09F, -1.01F, -4.01F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.01F, 0.9948F, 0.1836F, 0.1313F, 0.0779F, 0.0103F));

        var body_front = partDefinition.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -3.0F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 19.75F, 0.0F));

        body_front.addOrReplaceChild("front_fin", CubeListBuilder.create().texOffs(20, 3).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 7.0F), PartPose.offset(0.0F, -3.0F, -3.0F));

        body_front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(16, 4).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F), PartPose.offsetAndRotation(1.5F, 1.5F, -1.0F, 0.0F, 0.0F, 0.2618F));

        body_front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(16, 0).addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F), PartPose.offsetAndRotation(-1.5F, 1.5F, -1.0F, 0.0F, 0.0F, -0.2618F));

        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(0, 23).addBox(-1.0F, -2.5F, -0.1F, 2.0F, 5.0F, 4.0F), PartPose.offset(0.0F, 19.5F, 4.1F));

        var back_fin = body_back.addOrReplaceChild("back_fin", CubeListBuilder.create().texOffs(40, 4).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 2.0F), PartPose.offset(0.0F, -2.5F, -0.1F));

        back_fin.addOrReplaceChild("back_fin_r1", CubeListBuilder.create().texOffs(40, 9).addBox(0.0F, -3.0F, -2.0F, 0.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 5.0F, 1.0F, 3.1416F, 0.0F, 0.0F));

        body_back.addOrReplaceChild("body_back_2", CubeListBuilder.create().texOffs(32, -4).addBox(0.0F, -4.5F, 2.9F, 0.0F, 9.0F, 4.0F)
                .texOffs(13, 23).addBox(-1.0F, -1.5F, -0.1F, 2.0F, 3.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 4.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        var backRotation = 1.0f;
        var backRotSpeed = 1.0f;

        if (!entity.isInWater())
        {
            backRotation = 1.5f;
            backRotSpeed = 1.7f;
        }
        this.body_back.yRot = -backRotation * 0.2f * Mth.sin(backRotSpeed * 0.65f * ageInTicks);
        this.body_back_2.yRot = -backRotation * 0.25f * Mth.sin(backRotSpeed * 0.65f * ageInTicks);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.head.render(poseStack, buffer, packedLight, packedOverlay);
        this.body_front.render(poseStack, buffer, packedLight, packedOverlay);
        this.body_back.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void scale(T entity, PoseStack poseStack)
    {
        var scale = 2.0f;
        poseStack.scale(scale - 0.5f, scale, scale - 0.5f);
        poseStack.translate(0.0f, -0.5f, -0.2f);
    }
}