package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.stevekung.fishofthieves.entity.PartyFish;

import net.minecraft.client.renderer.entity.SalmonRenderer;
import net.minecraft.world.entity.animal.Salmon;

@Mixin(SalmonRenderer.class)
public class MixinSalmonRenderer
{
    @ModifyConstant(method = "setupRotations", constant = @Constant(floatValue = 4.3f))
    private float fishofthieves$modifyBaseDegree(float defaultValue, Salmon salmon)
    {
        return ((PartyFish)salmon).isPartying() ? -20.0f : defaultValue;
    }

    @ModifyConstant(method = "setupRotations", constant = @Constant(floatValue = 0.6f))
    private float fishofthieves$modifyBodyRotSpeed(float defaultValue, Salmon salmon)
    {
        return ((PartyFish)salmon).isPartying() ? salmon.isInWater() ? 2.0f : 1.0f : defaultValue;
    }
}