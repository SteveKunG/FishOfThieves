package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.core.FishOfThieves;
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
    private final ModelPart head;
    private final ModelPart body_main;
    private final ModelPart body_back;
    private final ModelPart body_back_2;

    public PlentifinModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.head = part.getChild("head");
        this.body_main = part.getChild("body_main");
        this.body_back = part.getChild("body_back");
        this.body_back_2 = this.body_back.getChild("body_back_2");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(9, 14).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(0.0F, -1.0F, -4.25F, -0.3054F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(14, 0).addBox(-1.0F, -1.85F, -3.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -3.5F, -3.55F, 1.1781F, 0.0F, 0.0F));
        var body_main = partDefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -6.1F, -4.5F, 2.0F, 6.0F, 5.0F, CubeDeformation.NONE).texOffs(0, 7).addBox(0.0F, -8.65F, -3.5F, 0.0F, 3.0F, 4.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 24.0F, 0.0F));
        body_main.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(14, 20).addBox(-0.75F, -3.65F, -3.5F, 3.0F, 3.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.75F, 0.0F, 2.0F, 0.0F, 0.9599F, 0.0F));
        body_main.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(14, 17).addBox(0.0F, -3.65F, 0.0F, 3.0F, 3.0F, 0.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.75F, 0.0F, -2.0F, 0.0F, -0.9599F, 0.0F));
        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(14, 8).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 2.0F, CubeDeformation.NONE).texOffs(1, 11).addBox(0.0F, -5.15F, 0.0F, 0.0F, 4.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 20.5F, 0.5F));
        body_back.addOrReplaceChild("right_fin_r2", CubeListBuilder.create().texOffs(6, 14).addBox(0.0F, 0.0F, 0.5F, 0.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.5F, 1.5F, -0.5F, 0.0F, 0.0F, 0.5236F));
        body_back.addOrReplaceChild("left_fin_r2", CubeListBuilder.create().texOffs(6, 16).addBox(0.0F, 0.0F, 0.5F, 0.0F, 2.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.5F, 1.5F, -0.5F, 0.0F, 0.0F, -0.5236F));
        body_back.addOrReplaceChild("body_back_2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 1.0F, CubeDeformation.NONE).texOffs(0, 15).addBox(0.0F, -2.65F, 1.0F, 0.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 2.0F));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        var backRotation = 1.0f;
        var backRotSpeed = 1.0f;

        if (!entity.isInWater() && !entity.isNoFlip())
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
        this.head.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body_main.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body_back.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void scale(T entity, PoseStack poseStack)
    {
        var scale = 1.25f;
        poseStack.scale(scale, scale + 0.5f, scale);
        poseStack.translate(0.0f, -0.375f, -0.125f);
    }
}