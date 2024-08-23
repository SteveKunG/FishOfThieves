package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Mixin(CodRenderer.class)
public class MixinCodRenderer
{
    @ModifyConstant(method = "setupRotations", constant = @Constant(floatValue = 4.3f))
    private float fishofthieves$modifyBaseDegree(float defaultValue, LivingEntityRenderState renderState)
    {
        return renderState.isDancing() ? -20.0f : defaultValue;
    }

    @ModifyConstant(method = "setupRotations", constant = @Constant(floatValue = 0.6f))
    private float fishofthieves$modifyBodyRotSpeed(float defaultValue, LivingEntityRenderState renderState)
    {
        return renderState.isDancing() ? 2.0f : defaultValue;
    }
}