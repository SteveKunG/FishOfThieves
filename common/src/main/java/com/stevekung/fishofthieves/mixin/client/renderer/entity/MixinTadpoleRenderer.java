package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.TadpoleModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.TadpoleRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.frog.Tadpole;

@Mixin(TadpoleRenderer.class)
public abstract class MixinTadpoleRenderer extends MobRenderer<Tadpole, TadpoleModel<Tadpole>>
{
    MixinTadpoleRenderer()
    {
        super(null, null, 0);
    }

    @Override
    public void setupRotations(Tadpole entity, PoseStack poseStack, float bob, float rotationYaw, float partialTicks, float scale)
    {
        super.setupRotations(entity, poseStack, bob, rotationYaw, partialTicks, scale);

        if (entity.isDancing())
        {
            var degree = -20.0f * Mth.sin(2.0f * bob);
            poseStack.mulPose(Axis.YP.rotationDegrees(degree));
        }
    }
}