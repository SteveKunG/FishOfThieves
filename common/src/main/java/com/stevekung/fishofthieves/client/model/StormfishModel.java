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

public class StormfishModel<T extends Stormfish> extends EntityModel<T> implements HeadphoneModel.Scaleable<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(FishOfThieves.res("stormfish"), "main");
    private final ModelPart head;
    private final ModelPart body_main;
    private final ModelPart body_back;
    private final ModelPart body_back_2;

    public StormfishModel(ModelPart part)
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

        var head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -7.25F, -6.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)).texOffs(40, -3).addBox(0.0F, -12.25F, -6.0F, 0.0F, 5.0F, 3.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 24.0F, 0.0F));
        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(26, 15).addBox(-0.5F, -2.3F, -4.0F, 1.0F, 1.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, -6.0F, -0.1571F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(25, 21).addBox(-0.5F, -3.15F, -5.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.0873F, 0.0F, 0.0F));
        head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(12, 14).addBox(-1.0F, 0.125F, -4.91F, 2.0F, 3.0F, 5.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -7.25F, -6.165F, 1.0297F, 0.0F, 0.0F));
        var body_main = partDefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -7.25F, -3.0F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.1F)).texOffs(20, 3).addBox(0.0F, -11.25F, -3.0F, 0.0F, 4.0F, 7.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 24.0F, 0.0F));
        body_main.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(16, 0).addBox(-6.0F, 0.0F, -3.0F, 6.0F, 0.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.5F, -2.75F, 0.0F, 0.0F, 0.0F, -0.2618F));
        body_main.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(16, 4).addBox(0.0F, 0.0F, -3.0F, 6.0F, 0.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(1.5F, -2.75F, 0.0F, 0.0F, 0.0F, 0.2618F));
        var body_back = partDefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(0, 23).addBox(-1.0F, -2.5F, 0.0F, 2.0F, 5.0F, 4.0F, CubeDeformation.NONE).texOffs(40, 4).addBox(0.0F, -5.5F, 0.0F, 0.0F, 3.0F, 2.0F, CubeDeformation.NONE).texOffs(40, 9).addBox(0.0F, 2.5F, 1.0F, 0.0F, 3.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 19.5F, 4.0F));
        body_back.addOrReplaceChild("body_back_2", CubeListBuilder.create().texOffs(13, 23).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 3.0F, CubeDeformation.NONE).texOffs(32, -4).addBox(0.0F, -4.5F, 3.0F, 0.0F, 9.0F, 4.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 4.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
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
        this.body_back_2.yRot = -backRotation * 0.25f * Mth.sin(backRotSpeed * 0.65f * ageInTicks);
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
        poseStack.scale(scale - 0.5f, scale, scale - 0.5f);
        poseStack.translate(0.0f, -0.5f, -0.2f);
    }
}