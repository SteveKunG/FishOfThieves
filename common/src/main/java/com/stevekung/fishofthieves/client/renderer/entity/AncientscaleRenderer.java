package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.AncientscaleModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.FishData;
import com.stevekung.fishofthieves.entity.animal.Ancientscale;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AncientscaleRenderer extends ThievesFishRenderer<FishData, Ancientscale, AncientscaleModel<Ancientscale>>
{
//    private static final Map<FishData, ResourceLocation> TEXTURE_BY_TYPE = ThievesFishRenderer.createTextureByType(Ancientscale.Variant.BY_ID, "ancientscale");

    public AncientscaleRenderer(EntityRendererProvider.Context context)
    {
        super(context, new AncientscaleModel<>(context.bakeLayer(AncientscaleModel.LAYER)));
    }

//    @Override
//    protected Map<FishData, ResourceLocation> getTextureMap()
//    {
//        return TEXTURE_BY_TYPE;
//    }

    @Override
    protected void setupRotations(Ancientscale ancientscale, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(ancientscale, poseStack, ageInTicks, rotationYaw, partialTicks);
        var bodyRotBase = 1.0f;
        var baseDegree = ancientscale.isPartying() ? -20.0f : 5.0f;
        var bodyRotSpeed = ancientscale.isPartying() ? ancientscale.isInWater() ? 2.0f : 1.0f : 0.65f;

        if (!ancientscale.isInWater())
        {
            bodyRotBase = 1.7f;
        }

        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!ancientscale.isInWater())
        {
            poseStack.translate(0.165f, 0.1f, 0.1f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}