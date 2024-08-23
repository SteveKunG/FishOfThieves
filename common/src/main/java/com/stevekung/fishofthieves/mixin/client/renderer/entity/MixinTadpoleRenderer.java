package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.TadpoleModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.TadpoleRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.frog.Tadpole;

@Mixin(TadpoleRenderer.class)
public abstract class MixinTadpoleRenderer extends MobRenderer<Tadpole, LivingEntityRenderState, TadpoleModel>
{
    MixinTadpoleRenderer()
    {
        super(null, null, 0);
    }

    @Override
    public void setupRotations(LivingEntityRenderState renderState, PoseStack poseStack, float partialTicks, float scale)
    {
        super.setupRotations(renderState, poseStack, partialTicks, scale);

        if (renderState.isDancing())
        {
            var degree = -20.0f * Mth.sin(2.0f * renderState.ageInTicks);
            poseStack.mulPose(Axis.YP.rotationDegrees(degree));
        }
    }
}