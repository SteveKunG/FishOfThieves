package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.DevilfishModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.FishData;
import com.stevekung.fishofthieves.entity.animal.Devilfish;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DevilfishRenderer extends ThievesFishRenderer<FishData, Devilfish, DevilfishModel<Devilfish>>
{
//    private static final Map<FishData, ResourceLocation> TEXTURE_BY_TYPE = ThievesFishRenderer.createTextureByType(Devilfish.Variant.BY_ID, "devilfish");

    public DevilfishRenderer(EntityRendererProvider.Context context)
    {
        super(context, new DevilfishModel<>(context.bakeLayer(DevilfishModel.LAYER)));
    }

//    @Override
//    protected Map<FishData, ResourceLocation> getTextureMap()
//    {
//        return TEXTURE_BY_TYPE;
//    }

    @Override
    protected void setupRotations(Devilfish devilfish, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(devilfish, poseStack, ageInTicks, rotationYaw, partialTicks);
        var bodyRotBase = 1.0f;
        var baseDegree = devilfish.isPartying() ? -20.0f : 4.0f;
        var bodyRotSpeed = devilfish.isPartying() ? devilfish.isInWater() ? 2.0f : 1.0f : 0.6f;

        if (!devilfish.isInWater())
        {
            bodyRotBase = 1.7f;
        }

        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!devilfish.isInWater())
        {
            poseStack.translate(0.175f, 0.1f, 0.0f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}