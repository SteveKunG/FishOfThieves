package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.WreckerModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.layers.WreckerBulbLayer;
import com.stevekung.fishofthieves.entity.FishVariant;
import com.stevekung.fishofthieves.entity.animal.Wrecker;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WreckerRenderer extends ThievesFishRenderer<Wrecker, WreckerModel<Wrecker>>
{
    private static final Map<FishVariant, ResourceLocation> TEXTURE_BY_TYPE = ThievesFishRenderer.createTextureByType(Wrecker.Variant.BY_ID, "wrecker");

    public WreckerRenderer(EntityRendererProvider.Context context)
    {
        super(context, new WreckerModel<>(context.bakeLayer(WreckerModel.LAYER)), 0.2F);
        this.addLayer(new WreckerBulbLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Wrecker wrecker)
    {
        return TEXTURE_BY_TYPE.get(wrecker.getVariant());
    }

    @Override
    protected void setupRotations(Wrecker wrecker, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(wrecker, poseStack, ageInTicks, rotationYaw, partialTicks);
        var bodyRotBase = 1.0f;
        var baseDegree = wrecker.isPartying() ? -20.0f : 4.0f;
        var bodyRotSpeed = wrecker.isPartying() ? wrecker.isInWater() ? 2.0f : 1.0f : 0.6f;

        if (!wrecker.isInWater())
        {
            bodyRotBase = 1.7f;
        }

        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!wrecker.isInWater())
        {
            poseStack.translate(0.15f, 0.1f, 0.0f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}