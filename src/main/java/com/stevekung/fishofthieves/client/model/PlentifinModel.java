package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Plentifin;

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

public class PlentifinModel<T extends Plentifin> extends EntityModel<T> implements ScaleableModel<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(FishOfThieves.MOD_ID, "plentifin"), "main");
    private final ModelPart body_front;
    private final ModelPart body_back;
    private final ModelPart body_back_2;
    private final ModelPart head;

    public PlentifinModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.body_front = part.getChild("body_front");
        this.body_back = part.getChild("body_back");
        this.body_back_2 = this.body_back.getChild("body_back_2");
        this.head = part.getChild("head");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var body_front = partDefinition.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.6862F, -2.5135F, 2.0F, 6.0F, 5.0F)
                .texOffs(17, 14).addBox(-1.0F, 0.5F, -1.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 20.6F, -2.0F));

        body_front.addOrReplaceChild("fin_back_1", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -2.5F, -4.0F, 0.0F, 3.0F, 4.0F), PartPose.offset(0.0F, -2.75F, 2.5F));

        body_front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(14, 20).addBox(-3.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(-0.75F, 1.25F, 0.0F, 0.0F, 0.9599F, 0.0F));

        body_front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(14, 17).addBox(0.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(0.75F, 1.25F, 0.0F, 0.0F, -0.9599F, 0.0F));

        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(14, 8).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, 20.5F, 0.5F));

        body_back.addOrReplaceChild("fin_back_2", CubeListBuilder.create().texOffs(1, 11).addBox(0.0F, -2.5F, 0.0F, 0.0F, 4.0F, 3.0F), PartPose.offset(0.0F, -2.65F, 0.0F));

        body_back.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(6, 14).addBox(0.0F, 0.0F, -2.5F, 0.0F, 2.0F, 4.0F), PartPose.offsetAndRotation(-0.5F, 1.45F, 2.5F, 0.0F, 0.0F, 0.5236F));

        body_back.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(6, 16).addBox(0.0F, 0.0F, -2.5F, 0.0F, 2.0F, 4.0F), PartPose.offsetAndRotation(0.5F, 1.45F, 2.5F, 0.0F, 0.0F, -0.5236F));

        var body_back_2 = body_back.addOrReplaceChild("body_back_2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 2.0F));

        body_back_2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, -0.25F, -2.0F, 0.0F, 5.0F, 3.0F), PartPose.offset(0.0F, -2.4F, 3.0F));

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 18.1F, -2.5F));

        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(9, 14).addBox(-1.01F, -0.0953F, -1.9997F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.01F, 4.9509F, -1.7418F, -0.3054F, 0.0F, 0.0F));

        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(14, 0).addBox(-1.0F, -1.8649F, -3.0074F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.03F)), PartPose.offsetAndRotation(0.0F, 2.4349F, -1.0426F, 1.1781F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
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
        this.body_back_2.yRot = -backRotation * 0.3f * Mth.sin(backRotSpeed * 0.65f * ageInTicks);
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
        var scale = 1.25f;
        poseStack.scale(scale, scale + 0.5f, scale);
        poseStack.translate(0.0f, -0.375f, -0.125f);
    }
}