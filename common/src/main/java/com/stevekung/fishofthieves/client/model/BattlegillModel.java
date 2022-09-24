package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Battlegill;
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

public class BattlegillModel<T extends Battlegill> extends EntityModel<T> implements HeadphoneModel.Scaleable<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(FishOfThieves.MOD_ID, "battlegill"), "main");
    private final ModelPart head;
    private final ModelPart body_main;
    private final ModelPart body_back;
    private final ModelPart mouth;

    public BattlegillModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.head = part.getChild("head");
        this.body_main = part.getChild("body_main");
        this.body_back = part.getChild("body_back");
        this.mouth = this.head.getChild("mouth");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 18.5F, -3.55F, 0.1745F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.555F, -1.29F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 1.48F, -0.86F, 0.7854F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(12, 16).addBox(-2.0F, -1.39F, -1.07F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.47F, 0.22F, 0.5672F, 0.0F, 0.0F));
        var mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 1.05F));
        mouth.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(0, 26).addBox(-0.2F, 0.25F, 0.85F, 0.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.0F, -3.25F, -3.85F, -0.6109F, -0.0698F, -0.1047F));
        mouth.addOrReplaceChild("mouth_r2", CubeListBuilder.create().texOffs(0, 26).addBox(0.2F, 0.25F, 0.85F, 0.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.0F, -3.25F, -3.85F, -0.6109F, 0.0698F, 0.1047F));
        mouth.addOrReplaceChild("mouth_r3", CubeListBuilder.create().texOffs(0, 31).addBox(-1.5F, 0.25F, 0.535F, 3.0F, 1.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -3.5F, -4.35F, -0.48F, 0.0F, 0.0F));
        mouth.addOrReplaceChild("mouth_r4", CubeListBuilder.create().texOffs(0, 10).addBox(-4.05F, 0.1F, 0.15F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(2.05F, -2.25F, -4.5F, -0.6109F, 0.0F, 0.0F));
        var body_main = partDefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.75F, -3.5F, 4.0F, 5.0F, 5.0F, CubeDeformation.NONE).texOffs(16, 10).addBox(-2.0F, -1.75F, -2.5F, 4.0F, 1.0F, 4.0F, CubeDeformation.NONE).texOffs(0, 18).addBox(0.0F, -9.75F, -3.5F, 0.0F, 3.0F, 5.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 24.0F, 0.0F));
        body_main.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(19, 23).mirror().addBox(-6.0F, 0.0F, -1.0F, 3.0F, 3.0F, 0.0F, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(1.0F, -4.0F, 1.5F, -1.5708F, 0.0F, -0.6981F));
        body_main.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(19, 26).addBox(3.0F, 0.0F, -1.0F, 3.0F, 3.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.0F, -4.0F, 1.5F, -1.5708F, 0.0F, 0.6981F));
        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(18, 2).addBox(-1.5F, -2.0F, -1.25F, 3.0F, 4.0F, 4.0F, CubeDeformation.NONE).texOffs(0, 24).addBox(0.0F, -5.0F, 0.0F, 0.0F, 3.0F, 2.0F, CubeDeformation.NONE).texOffs(10, 20).addBox(0.0F, -2.5F, 2.75F, 0.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 20.25F, 1.5F));
        body_back.addOrReplaceChild("right_fin_r2", CubeListBuilder.create().texOffs(10, 24).addBox(0.375F, 0.5825F, 0.25F, 0.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.25F, 1.25F, -1.5F, 0.0F, 0.0F, 0.5236F));
        body_back.addOrReplaceChild("left_fin_r2", CubeListBuilder.create().texOffs(10, 26).addBox(-0.375F, 0.5825F, 0.25F, 0.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.25F, 1.25F, -1.5F, 0.0F, 0.0F, -0.5236F));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        var backRotation = 1.0f;
        var backRotSpeed = 1.0f;
        var mouthSpeed = 0.4f;

        if (!entity.isInWater() && !entity.isNoFlip())
        {
            backRotation = 1.5f;
            backRotSpeed = 1.7f;
            mouthSpeed = 1.8f;
        }
        this.body_back.yRot = -backRotation * 0.2f * Mth.sin(backRotSpeed * 0.6f * ageInTicks);
        this.mouth.xRot = -0.0F + Mth.cos(mouthSpeed * ageInTicks) * (float) Math.PI * 0.06f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.head.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body_main.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body_back.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void scale(T entity, PoseStack poseStack)
    {
        var scale = 1.5f;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0f, -0.3f, -0.06f);
    }
}