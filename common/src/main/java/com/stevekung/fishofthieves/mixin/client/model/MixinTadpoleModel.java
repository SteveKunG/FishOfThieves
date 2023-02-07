package com.stevekung.fishofthieves.mixin.client.model;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.client.model.HeadphoneModel;
import net.minecraft.client.model.TadpoleModel;
import net.minecraft.world.entity.animal.frog.Tadpole;

@Mixin(TadpoleModel.class)
public class MixinTadpoleModel implements HeadphoneModel.Scaleable<Tadpole>
{
    @ModifyConstant(method = "setupAnim", constant = @Constant(floatValue = 0.25F))
    private float fishofthieves$modifyBaseDegree(float defaultValue, Tadpole tadpole)
    {
        return tadpole.isDancing() ? 0.35f : defaultValue;
    }

    @ModifyConstant(method = "setupAnim", constant = @Constant(floatValue = 0.3f))
    private float fishofthieves$modifyBodyRotSpeed(float defaultValue, Tadpole tadpole)
    {
        return tadpole.isDancing() ? 3.0f : defaultValue;
    }

    @Override
    public void scale(Tadpole entity, PoseStack poseStack)
    {
        var scale = 1.1f;
        var y = 0.15f;
        var z = -0.08f;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0f, y, z);
    }
}