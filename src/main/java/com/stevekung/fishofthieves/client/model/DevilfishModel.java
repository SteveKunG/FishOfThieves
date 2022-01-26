package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Devilfish;

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

public class DevilfishModel<T extends Devilfish> extends EntityModel<T> implements ScaleableModel<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(FishOfThieves.MOD_ID, "devilfish"), "main");
    private final ModelPart body_front;
    private final ModelPart body_back;
    private final ModelPart head;
    private final ModelPart mouth;

    public DevilfishModel(ModelPart part)
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

        var body_front = partDefinition.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.25F, -2.25F, 4.0F, 6.0F, 5.0F), PartPose.offset(0.0F, 19.25F, -1.25F));

        body_front.addOrReplaceChild("fin_back_1", CubeListBuilder.create().texOffs(6, 22).addBox(0.0F, -2.0F, -5.0F, 0.0F, 2.0F, 5.0F), PartPose.offset(0.0F, -3.25F, 2.75F));

        body_front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(0, 24).mirror().addBox(-3.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.75F, 0.25F, 0.0F, 0.7854F, 0.0F));

        body_front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(6, 24).addBox(0.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(2.0F, 0.75F, 0.25F, 0.0F, -0.7854F, 0.0F));

        body_front.addOrReplaceChild("fin_under", CubeListBuilder.create().texOffs(6, 25).addBox(0.0F, 0.0F, -1.5F, 0.0F, 2.0F, 4.0F), PartPose.offset(0.0F, 2.75F, -0.25F));

        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(0, 17).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 4.0F, 3.0F), PartPose.offset(0.0F, 19.0F, 1.5F));

        body_back.addOrReplaceChild("fin_back_2", CubeListBuilder.create().texOffs(16, 22).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));

        body_back.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(16, 25).addBox(0.0F, 0.0F, -1.5F, 0.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(-1.5F, 2.0F, 1.75F, 0.0F, 0.0F, 0.5236F));

        body_back.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(16, 27).addBox(0.0F, 0.0F, -1.5F, 0.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(1.5F, 2.0F, 1.75F, 0.0F, 0.0F, -0.5236F));

        body_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 2.5F));

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 18.5F, -3.5F));

        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(18, 7).addBox(-2.0F, -2.25F, -0.25F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 1.4252F, -1.3608F, 0.7854F, 0.0F, 0.0F));

        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(18, 0).addBox(-2.0F, -2.25F, -0.25F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.3379F, -0.267F, 0.5672F, 0.0F, 0.0F));

        head.addOrReplaceChild("eyes_r1", CubeListBuilder.create().texOffs(12, 21).addBox(-2.0F, -0.75F, -0.25F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5672F, 0.0F, 0.0F));

        var mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.01F, 3.2883F, 0.2799F));

        mouth.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(12, 19).addBox(-6.09F, -1.8547F, -2.3224F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.06F))
                .texOffs(12, 17).addBox(-6.09F, -2.3547F, -4.8503F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.06F)), PartPose.offsetAndRotation(4.08F, 1.0936F, -0.1075F, -0.6109F, 0.0F, 0.0F));

        mouth.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(0, 11).addBox(-2.0F, 0.05F, -5.05F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-0.01F, -0.2293F, 0.4522F, -0.6109F, 0.0F, 0.0F));

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
        poseStack.translate(0.0f, -0.35f, -0.075f);
    }
}