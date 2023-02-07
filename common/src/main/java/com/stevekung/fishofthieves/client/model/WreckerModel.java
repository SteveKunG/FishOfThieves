package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Wrecker;
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

public class WreckerModel<T extends Wrecker> extends EntityModel<T> implements HeadphoneModel.Scaleable<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(FishOfThieves.res("wrecker"), "main");
    private final ModelPart head;
    private final ModelPart body_main;
    private final ModelPart body_back;
    private final ModelPart bulb;
    private final ModelPart mouth;

    public WreckerModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.head = part.getChild("head");
        this.body_main = part.getChild("body_main");
        this.body_back = part.getChild("body_back");
        this.bulb = this.head.getChild("bulb");
        this.mouth = this.head.getChild("mouth");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 19.5F, -3.55F, 0.1745F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.21F, -2.27F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.46F, 0.445F, 0.3054F, 0.0F, 0.0F));
        head.addOrReplaceChild("bulb", CubeListBuilder.create().texOffs(18, 1).addBox(0.0F, -4.0F, -2.5F, 0.0F, 5.0F, 3.0F, CubeDeformation.NONE).texOffs(27, 19).addBox(-0.5F, -2.95F, -3.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -2.3F, 0.7F, 0.3054F, 0.0F, 0.0F));
        var mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 0.3F));
        mouth.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(0, 25).addBox(0.1F, -2.0F, 0.0F, 0.0F, 2.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.7F, -4.5F, -2.75F, -1.0472F, -0.1134F, -0.0654F));
        mouth.addOrReplaceChild("mouth_r2", CubeListBuilder.create().texOffs(0, 25).addBox(-0.1F, -2.0F, 0.0F, 0.0F, 2.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.7F, -4.5F, -2.75F, -1.0472F, 0.1134F, 0.0654F));
        mouth.addOrReplaceChild("mouth_r3", CubeListBuilder.create().texOffs(10, 30).addBox(-2.5F, -1.5F, 0.0F, 5.0F, 2.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -4.65F, -2.75F, -0.8727F, 0.0F, 0.0F));
        mouth.addOrReplaceChild("mouth_r4", CubeListBuilder.create().texOffs(0, 24).addBox(-2.5F, 0.2F, -4.85F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -0.35F, -0.25F, -1.0472F, 0.0F, 0.0F));
        var body_main = partDefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(0, 9).addBox(-2.5F, -6.9F, -2.5F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.01F)).texOffs(20, 21).addBox(0.0F, -9.9F, -1.5F, 0.0F, 3.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 24.0F, 0.0F));
        body_main.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(27, 21).addBox(-2.0F, -4.9F, 0.0F, 2.0F, 2.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.5F, 0.0F, 0.0F, 0.0F, 0.8727F, 0.0F));
        body_main.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(27, 23).addBox(0.0F, -4.9F, 0.0F, 2.0F, 2.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.5F, 0.0F, 0.0F, 0.0F, -0.8727F, 0.0F));
        body_main.addOrReplaceChild("body_main_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-2.5F, -0.9F, -5.0F, 5.0F, 1.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -2.0F, 1.5F, 0.2094F, 0.0F, 0.0F));
        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(18, 10).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 4.0F, 4.0F, CubeDeformation.NONE).texOffs(20, 25).addBox(0.0F, -5.0F, 0.0F, 0.0F, 3.0F, 3.0F, CubeDeformation.NONE).texOffs(20, 15).addBox(0.0F, -2.5F, 3.0F, 0.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 19.5F, 1.5F));
        body_back.addOrReplaceChild("right_fin_r2", CubeListBuilder.create().texOffs(26, 23).addBox(-0.5F, -0.5F, 1.5F, 0.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.25F, 2.5F, -1.5F, 0.0F, 0.0F, 0.5236F));
        body_back.addOrReplaceChild("left_fin_r2", CubeListBuilder.create().texOffs(26, 25).addBox(0.5F, -0.5F, 1.5F, 0.0F, 2.0F, 3.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.25F, 2.5F, -1.5F, 0.0F, 0.0F, -0.5236F));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        var backRotation = 1.0f;
        var backRotSpeed = 1.0f;
        var mouthSpeed = 0.4f;
        var bulbSpeed = 0.1f;

        if (!entity.isInWater() && !entity.isNoFlip())
        {
            backRotation = 1.5f;
            backRotSpeed = 1.7f;
            mouthSpeed = 1.8f;
        }
        this.body_back.yRot = -backRotation * 0.2f * Mth.sin(backRotSpeed * 0.6f * ageInTicks);
        this.mouth.xRot = 0.1F + Mth.cos(mouthSpeed * ageInTicks) * (float) Math.PI * 0.07f;
        this.bulb.xRot = 0.25F + Mth.cos(bulbSpeed * ageInTicks) * (float) Math.PI * 0.03f;
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
        var scale = 2.0f;
        poseStack.scale(scale, scale - 0.5f, scale - 0.5f);
        poseStack.translate(0.0f, -0.3f, -0.04f);
    }
}