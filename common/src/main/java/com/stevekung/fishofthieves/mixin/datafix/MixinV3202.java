package com.stevekung.fishofthieves.mixin.datafix;

import java.util.Map;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;

import net.minecraft.util.datafix.schemas.V3202;
import net.minecraft.util.datafix.schemas.V99;

@Mixin(V3202.class)
public class MixinV3202
{
    @ModifyReturnValue(method = "registerBlockEntities", at = @At("RETURN"))
    private Map<String, Supplier<TypeTemplate>> fishofthieves$registerBlockEntities(Map<String, Supplier<TypeTemplate>> original, @Local(argsOnly = true) Schema schema)
    {
        original.put("fishofthieves:sign", () -> V99.sign(schema));
        original.put("fishofthieves:hanging_sign", () -> V99.sign(schema));
        return original;
    }
}