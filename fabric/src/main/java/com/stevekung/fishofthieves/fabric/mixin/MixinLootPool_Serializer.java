package com.stevekung.fishofthieves.fabric.mixin;

import java.lang.reflect.Type;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.level.storage.loot.LootPool;

@Mixin(LootPool.Serializer.class)
public class MixinLootPool_Serializer
{
    @Inject(method = "serialize", at = @At(value = "CONSTANT", args = "stringValue=rolls"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void fishofthieves$addForgeName(LootPool lootPool, Type type, JsonSerializationContext jsonSerializationContext, CallbackInfoReturnable<JsonElement> info, JsonObject jsonObject)
    {
        jsonObject.add("name", jsonSerializationContext.serialize("main"));
    }
}