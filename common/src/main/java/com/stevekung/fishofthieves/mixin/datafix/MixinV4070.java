package com.stevekung.fishofthieves.mixin.datafix;

import java.util.Map;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;

import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.V4070;

@Mixin(V4070.class)
public class MixinV4070
{
    @ModifyReturnValue(method = "registerEntities", at = @At("RETURN"))
    private Map<String, Supplier<TypeTemplate>> fishofthieves$registerEntities(Map<String, Supplier<TypeTemplate>> original, @Local(argsOnly = true) Schema schema)
    {
        schema.registerSimple(original, "fishofthieves:coconut_boat");
        schema.register(original, "fishofthieves:coconut_chest_boat", string -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(schema))));
        return original;
    }
}