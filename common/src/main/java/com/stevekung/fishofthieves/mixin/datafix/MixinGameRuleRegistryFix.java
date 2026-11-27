package com.stevekung.fishofthieves.mixin.datafix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Dynamic;

import net.minecraft.util.datafix.fixes.GameRuleRegistryFix;

@Mixin(GameRuleRegistryFix.class)
public class MixinGameRuleRegistryFix
{
    @Shadow
    static Dynamic<?> convertBoolean(Dynamic<?> data)
    {
        throw new AssertionError();
    }

    @ModifyReturnValue(method = "lambda$makeRule$2", at = @At("RETURN"))
    private static Dynamic<?> fishofthieves$fixShoalSpawning(Dynamic<?> returnValue)
    {
        return returnValue.setFieldIfPresent("fishofthieves:shoal_spawning", returnValue.get("fishofthieves:shoal_spawning").result().map(MixinGameRuleRegistryFix::convertBoolean));
    }
}