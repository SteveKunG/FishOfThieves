package com.stevekung.fishofthieves.mixin.datafix;

import java.util.Map;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;

import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.datafix.schemas.V3439;

@Mixin(V3439.class)
public class MixinV3439 extends NamespacedSchema
{
    MixinV3439()
    {
        super(0, null);
    }

    @Shadow
    static TypeTemplate sign(Schema schema)
    {
        throw new AssertionError();
    }

    @ModifyReturnValue(method = "registerBlockEntities", at = @At("RETURN"))
    private Map<String, Supplier<TypeTemplate>> fishofthieves$registerBlockEntities(Map<String, Supplier<TypeTemplate>> original, @Local(argsOnly = true) Schema schema)
    {
        this.register(original, "fishofthieves:sign", () -> sign(schema));
        this.register(original, "fishofthieves:hanging_sign", () -> sign(schema));
        return original;
    }
}