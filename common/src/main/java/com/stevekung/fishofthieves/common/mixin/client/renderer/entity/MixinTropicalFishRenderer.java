package com.stevekung.fishofthieves.common.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.world.entity.animal.TropicalFish;

@Mixin(TropicalFishRenderer.class)
public class MixinTropicalFishRenderer
{
    @ModifyConstant(method = "setupRotations", constant = @Constant(floatValue = 4.3f))
    private float fishofthieves$modifyBaseDegree(float defaultValue, TropicalFish tropicalFish)
    {
        return tropicalFish.isDancing() ? -20.0f : defaultValue;
    }

    @ModifyConstant(method = "setupRotations", constant = @Constant(floatValue = 0.6f))
    private float fishofthieves$modifyBodyRotSpeed(float defaultValue, TropicalFish tropicalFish)
    {
        return tropicalFish.isDancing() ? 2.0f : defaultValue;
    }
}