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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.animal.frog.Tadpole;

public class HeadphoneModel<T extends Entity> extends EntityModel<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(FishOfThieves.res("headphone"), "main");
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
        HeadphoneModel.Scaleable<Cod> COD = (entity, poseStack) -> poseStack.translate(0.0f, 0.25f, -0.05f);
        HeadphoneModel.Scaleable<Salmon> SALMON = (entity, poseStack) ->
        {
            poseStack.scale(1.5f, 1.5f, 1.5f);
            poseStack.translate(0.0f, -0.275f, 0.0f);
        };
        HeadphoneModel.Scaleable<Pufferfish> PUFFERFISH = (entity, poseStack) ->
        {
            var y = 0.0f;

            switch (entity.getPuffState())
            {
                case 0 ->
                {
                    y = 0.25f;
                    poseStack.scale(1.25f, 1.0f, 0.75f);
                }
                case 1 ->
                {
                    y = -0.275f;
                    poseStack.scale(1.75f, 1.5f, 1.5f);
                }
                case 2 ->
                {
                    y = -0.55f;
                    poseStack.scale(3.0f, 2.0f, 2.0f);
                }
            }
            poseStack.translate(0.0f, y, -0.025f);
        };
        HeadphoneModel.Scaleable<TropicalFish> TROPICAL_FISH = (entity, poseStack) ->
        {
            var baseVariant = entity.getVariant().base();
            var y = 0.0f;
            var z = 0.0f;

            if (baseVariant == TropicalFish.Base.SMALL)
            {
                y = 0.275f;
                z = -0.08f;
            }
            else
            {
                y = -0.325f;
                z = -0.1f;
                poseStack.scale(1.0f, 1.5f, 1.0f);
            }
            poseStack.translate(0.0f, y, z);
        };
        HeadphoneModel.Scaleable<Tadpole> TADPOLE = (entity, poseStack) ->
        {
            poseStack.scale(1.1f, 1.1f, 1.1f);
            poseStack.translate(0.0f, 0.15f, -0.08f);
        };

        void scale(T entity, PoseStack poseStack);
    }
}