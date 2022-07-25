package com.stevekung.fishofthieves.mixin.util.datafix;

import java.util.Map;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.stevekung.fishofthieves.core.FishOfThieves;
import net.minecraft.util.datafix.schemas.V3078;

@Mixin(V3078.class)
public class MixinV3078
{
    static
    {
        FishOfThieves.LOGGER.info("Register FishOfThieves entities into " + V3078.class.getName());
    }

    @Shadow
    static void registerMob(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {}

    @Inject(method = "registerEntities", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void fishofthieves$addFixers(Schema schema, CallbackInfoReturnable<Map<String, Supplier<TypeTemplate>>> info, Map<String, Supplier<TypeTemplate>> map)
    {
        registerMob(schema, map, "fishofthieves:splashtail");
    }
}