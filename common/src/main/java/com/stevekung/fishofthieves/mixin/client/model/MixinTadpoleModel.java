package com.stevekung.fishofthieves.mixin.client.model;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.client.model.TadpoleModel;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Mixin(TadpoleModel.class)
public class MixinTadpoleModel
{
    @ModifyConstant(method = "setupAnim", constant = @Constant(floatValue = 0.25F))
    private float fishofthieves$modifyBaseDegree(float defaultValue, LivingEntityRenderState renderState)
    {
        return renderState.isDancing() ? 0.35f : defaultValue;
    }

    @ModifyConstant(method = "setupAnim", constant = @Constant(floatValue = 0.3f))
    private float fishofthieves$modifyBodyRotSpeed(float defaultValue, LivingEntityRenderState renderState)
    {
        return renderState.isDancing() ? 3.0f : defaultValue;
    }
}