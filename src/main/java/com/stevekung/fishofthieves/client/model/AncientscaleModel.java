package com.stevekung.fishofthieves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Ancientscale;

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

public class AncientscaleModel<T extends Ancientscale> extends EntityModel<T> implements ScaleableModel<T>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(FishOfThieves.MOD_ID, "ancientscale"), "main");
    private final ModelPart body_front;
    private final ModelPart body_back;
    private final ModelPart body_back_2;
    private final ModelPart head;

    public AncientscaleModel(ModelPart part)
    {
        super(RenderType::entityCutout);
        this.body_front = part.getChild("body_front");
        this.body_back = part.getChild("body_back");
        this.head = part.getChild("head");
        this.body_back_2 = this.body_back.getChild("body_back_2");
    }

    public static LayerDefinition createBodyLayer()
    {
        var meshdefinition = new MeshDefinition();
        var partdefinition = meshdefinition.getRoot();

        var body_front = partdefinition.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.5F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(20, 8).addBox(-2.0F, 1.5F, -4.5F, 4.0F, 1.0F, 2.0F)
                .texOffs(14, 11).addBox(-2.0F, -1.75F, -2.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 19.5F, -2.5F));

        body_front.addOrReplaceChild("fin_back_1", CubeListBuilder.create().texOffs(22, -4).addBox(0.0F, -1.0F, -4.0F, 0.0F, 2.0F, 4.0F), PartPose.offset(0.0F, -3.25F, 3.0F));

        body_front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(-2.0F, 1.0F, 0.25F, 0.0F, 1.0472F, 0.0F));

        body_front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(2.0F, 1.0F, 0.25F, 0.0F, -1.0472F, 0.0F));

        body_front.addOrReplaceChild("fin_under", CubeListBuilder.create().texOffs(15, 1).addBox(0.0F, 1.5F, -3.5F, 0.0F, 2.0F, 3.0F), PartPose.offset(0.0F, 1.0F, 2.0F));

        var body_back = partdefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 4.0F), PartPose.offset(0.0F, 19.5F, 0.5F));

        body_back.addOrReplaceChild("fin_back_2", CubeListBuilder.create().texOffs(22, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 3.0F, 2.0F), PartPose.offset(0.0F, -3.25F, 0.0F));

        body_back.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(15, -3).addBox(0.75F, 1.299F, -2.5F, 0.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(-1.0F, -0.25F, 5.0F, 0.0F, 0.0F, 0.5236F));

        body_back.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(15, -1).addBox(-0.75F, 1.299F, -2.5F, 0.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(1.0F, -0.25F, 5.0F, 0.0F, 0.0F, -0.5236F));

        var body_back_2 = body_back.addOrReplaceChild("body_back_2", CubeListBuilder.create().texOffs(14, 19).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(0.0F, -0.25F, 4.0F));

        body_back_2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(23, 17).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 2.0F));

        var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.5F, 19.5F, -5.5F));

        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(23, 16).mirror().addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 2.0F).mirror(false), PartPose.offsetAndRotation(-2.56F, 2.4241F, -1.5178F, -1.2464F, -0.1313F, 0.3712F));

        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(23, 16).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 2.0F), PartPose.offsetAndRotation(1.56F, 2.4241F, -1.5178F, -1.2464F, 0.1313F, -0.3712F));

        head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(14, 13).addBox(-1.55F, -0.6246F, -3.7607F, 3.0F, 1.0F, 4.0F), PartPose.offsetAndRotation(-0.45F, 2.3946F, -1.1977F, -0.9599F, 0.0F, 0.0F));

        head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(0, 11).addBox(-2.0F, -2.3012F, -2.1953F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.06F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
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
        var scale = 1.65f;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0f, -0.365f, -0.125f);
    }
}