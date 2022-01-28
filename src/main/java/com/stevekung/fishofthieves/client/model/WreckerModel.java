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

public class WreckerModel<T extends Wrecker> extends EntityModel<T> implements ScaleableModel<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(FishOfThieves.MOD_ID, "wrecker"), "main");
    private final ModelPart body_front;
    private final ModelPart body_back;
    private final ModelPart head;
    private final ModelPart bulb;
    private final ModelPart mouth;

    public WreckerModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.body_front = part.getChild("body_front");
        this.body_back = part.getChild("body_back");
        this.head = part.getChild("head");
        this.bulb = this.head.getChild("bulb");
        this.mouth = this.head.getChild("mouth");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var body_front = partDefinition.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 9).addBox(-2.5F, -2.5F, -2.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 19.6F, -0.5F));

        body_front.addOrReplaceChild("body_front_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-2.5F, -1.0F, -5.0F, 5.0F, 1.0F, 5.0F), PartPose.offsetAndRotation(0.0F, 2.5F, 2.0F, 0.2094F, 0.0F, 0.0F));

        body_front.addOrReplaceChild("fin_back_1", CubeListBuilder.create().texOffs(20, 21).addBox(0.0F, -3.0F, -3.0F, 0.0F, 3.0F, 3.0F), PartPose.offset(0.0F, -2.5F, 2.0F));

        body_front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(27, 21).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F), PartPose.offsetAndRotation(-2.5F, -0.5F, 0.5F, 0.0F, 0.8727F, 0.0F));

        body_front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(27, 23).addBox(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F), PartPose.offsetAndRotation(2.5F, -0.5F, 0.5F, 0.0F, -0.8727F, 0.0F));

        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(18, 10).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 4.0F, 4.0F), PartPose.offset(0.0F, 19.5F, 1.5F));

        body_back.addOrReplaceChild("fin_back_2", CubeListBuilder.create().texOffs(20, 25).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 3.0F), PartPose.offset(0.0F, -2.0F, 0.0F));

        body_back.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(26, 23).addBox(0.0F, 0.0F, -1.5F, 0.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(-1.5F, 2.0F, 1.5F, 0.0F, 0.0F, 0.5236F));

        body_back.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(26, 25).addBox(0.0F, 0.0F, -1.5F, 0.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(1.5F, 2.0F, 1.5F, 0.0F, 0.0F, -0.5236F));

        body_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(20, 15).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 3.0F));

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 19.5F, -3.55F, 0.1745F, 0.0F, 0.0F));

        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.51F, -2.1887F, -2.3124F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.01F, 0.4021F, 0.4914F, 0.3054F, 0.0F, 0.0F));

        head.addOrReplaceChild("eyes_r1", CubeListBuilder.create().texOffs(14, 0).addBox(-3.0F, -1.7F, 0.1F, 6.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(15, 2).addBox(-2.5F, -1.5F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 0.0868F, 0.4924F, -0.1745F, 0.0F, 0.0F));

        head.addOrReplaceChild("bulb", CubeListBuilder.create().texOffs(18, 1).addBox(-0.02F, -4.0881F, -2.448F, 0.0F, 5.0F, 3.0F)
                .texOffs(27, 19).addBox(-0.52F, -3.0881F, -3.448F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.02F, -1.9679F, 0.9558F, 0.3054F, 0.0F, 0.0F));

        var mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, 2.8751F, 0.2723F));

        mouth.addOrReplaceChild("teeth_r1", CubeListBuilder.create().texOffs(0, 25).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 5.0F), PartPose.offsetAndRotation(-2.7F, -3.8306F, -2.6918F, -1.0439F, -0.1133F, -0.0657F));

        mouth.addOrReplaceChild("teeth_r2", CubeListBuilder.create().texOffs(0, 25).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 5.0F), PartPose.offsetAndRotation(2.7F, -3.8306F, -2.6918F, -1.0439F, 0.1133F, 0.0657F));

        mouth.addOrReplaceChild("teeth_r3", CubeListBuilder.create().texOffs(10, 30).addBox(-2.5F, -1.5F, 0.0F, 5.0F, 2.0F, 0.0F), PartPose.offsetAndRotation(0.0F, -3.9918F, -2.7849F, -0.8727F, 0.0F, 0.0F));

        mouth.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 24).addBox(-2.5F, 0.1788F, -4.7569F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.3116F, -0.3248F, -1.0472F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        var backRotation = 1.0f;
        var backRotSpeed = 1.0f;
        var mouthSpeed = 0.4f;
        var bulbSpeed = 0.1f;

        if (!entity.isInWater())
        {
            backRotation = 1.5f;
            backRotSpeed = 1.7f;
        }
        this.body_back.yRot = -backRotation * 0.2f * Mth.sin(backRotSpeed * 0.6f * ageInTicks);
        this.mouth.xRot = 0.1F + Mth.cos(mouthSpeed * ageInTicks) * (float)Math.PI * 0.07f;
        this.bulb.xRot = 0.25F + Mth.cos(bulbSpeed * ageInTicks) * (float)Math.PI * 0.03f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.body_front.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body_back.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void scale(T entity, PoseStack poseStack)
    {
        var scale = 2.0f;
        poseStack.scale(scale, scale - 0.5f, scale - 0.5f);
        poseStack.translate(0.0f, -0.3f, -0.04f);
    }
}