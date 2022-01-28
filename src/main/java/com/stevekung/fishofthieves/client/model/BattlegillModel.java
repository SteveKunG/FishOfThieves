package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.FishOfThieves;
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

public class BattlegillModel<T extends Battlegill> extends EntityModel<T> implements ScaleableModel<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(FishOfThieves.MOD_ID, "battlegill"), "main");
    private final ModelPart body_front;
    private final ModelPart body_back;
    private final ModelPart head;
    private final ModelPart mouth;

    public BattlegillModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.body_front = part.getChild("body_front");
        this.body_back = part.getChild("body_back");
        this.head = part.getChild("head");
        this.mouth = this.head.getChild("mouth");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var body_front = partDefinition.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.25F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(16, 10).addBox(-2.0F, 3.0F, -1.25F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.25F, -1.25F));

        body_front.addOrReplaceChild("fin_back_1", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, -1.75F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.25F, 2.75F));

        body_front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(19, 23).mirror().addBox(-3.8035F, -1.5F, 0.9576F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.75F, 1.25F, -1.5708F, 0.0F, -0.6981F));

        body_front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(19, 26).addBox(0.8035F, -1.5F, 0.9576F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.75F, 1.25F, -1.5708F, 0.0F, 0.6981F));

        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(18, 2).addBox(-1.5F, -0.75F, -1.25F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 1.5F));

        body_back.addOrReplaceChild("fin_back_2", CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, -1.75F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        body_back.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(10, 24).addBox(0.625F, 1.0825F, -2.5F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 2.0F, 1.25F, 0.0F, 0.0F, 0.5236F));

        body_back.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(10, 26).addBox(-0.625F, 1.0825F, -2.5F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 2.0F, 1.25F, 0.0F, 0.0F, -0.5236F));

        body_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 20).addBox(0.0F, -1.25F, 0.0F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.75F));

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 18.5F, -3.55F, 0.1745F, 0.0F, 0.0F));

        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.533F, -1.2739F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 1.512F, -0.8684F, 0.7854F, 0.0F, 0.0F));

        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(12, 16).addBox(-2.0F, -1.3284F, -1.0945F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.4247F, 0.2254F, 0.5672F, 0.0F, 0.0F));

        head.addOrReplaceChild("eyes_r1", CubeListBuilder.create().texOffs(18, 0).addBox(-2.0F, 0.217F, -2.0239F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0868F, 0.4924F, 0.7854F, 0.0F, 0.0F));

        var mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.01F, 3.3751F, 0.7723F));

        mouth.addOrReplaceChild("teeth_r1", CubeListBuilder.create().texOffs(0, 31).addBox(-1.53F, 0.1921F, 0.3759F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.02F, -3.2987F, -3.9663F, -0.48F, 0.0F, 0.0F));

        mouth.addOrReplaceChild("teeth_r2", CubeListBuilder.create().texOffs(0, 26).addBox(-0.1479F, 0.1232F, 0.8783F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.06F, -3.0111F, -3.5207F, -0.6068F, -0.0749F, -0.1074F));

        mouth.addOrReplaceChild("teeth_r3", CubeListBuilder.create().texOffs(0, 26).addBox(0.1479F, 0.1232F, 0.8783F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.04F, -3.0111F, -3.5207F, -0.6068F, 0.0749F, 0.1074F));

        mouth.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(22, 16).addBox(-2.0F, 1.1829F, -4.8217F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.04F)), PartPose.offsetAndRotation(-0.01F, -0.2293F, 0.4522F, -0.6109F, 0.0F, 0.0F));

        mouth.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(0, 10).addBox(-4.05F, 0.0829F, 0.5783F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(2.04F, -2.11F, -4.1516F, -0.6109F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        var backRotation = 1.0f;
        var backRotSpeed = 1.0f;
        var mouthSpeed = 0.4f;

        if (!entity.isInWater())
        {
            backRotation = 1.5f;
            backRotSpeed = 1.7f;
            mouthSpeed = 1.8f;
        }
        this.body_back.yRot = -backRotation * 0.2f * Mth.sin(backRotSpeed * 0.6f * ageInTicks);
        this.mouth.xRot = -0.0F + Mth.cos(mouthSpeed * ageInTicks) * (float)Math.PI * 0.06f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.body_front.render(poseStack, buffer, packedLight, packedOverlay);
        this.body_back.render(poseStack, buffer, packedLight, packedOverlay);
        this.head.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void scale(T entity, PoseStack poseStack)
    {
        var scale = 1.5f;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0f, -0.3f, -0.06f);
    }
}