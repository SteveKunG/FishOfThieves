package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class HeadphoneModel<T extends Entity> extends EntityModel<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(FishOfThieves.MOD_ID, "headphone"), "main");
    private final ModelPart headphone;

    public HeadphoneModel(ModelPart part)
    {
        this.headphone = part.getChild("headphone");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("headphone", CubeListBuilder.create().texOffs(0, 3).addBox(1.5F, -7.5F, -1.0F, 1.0F, 2.0F, 2.0F).texOffs(0, 3).addBox(-2.5F, -7.5F, -1.0F, 1.0F, 2.0F, 2.0F).texOffs(0, 0).addBox(-2.0F, -9.5F, -0.5F, 4.0F, 2.0F, 1.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.headphone.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public interface Scaleable<T extends Entity>
    {
        void scale(T entity, PoseStack poseStack);
    }
}