package com.stevekung.fishofthieves.mixin.util.datafix;

import java.util.function.BiFunction;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import com.stevekung.fishofthieves.core.ItemStackFOTBucketRenameFix;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.EntityVariantFix;
import net.minecraft.util.datafix.fixes.References;

@Mixin(DataFixers.class)
public class MixinDataFixers
{
    @Shadow
    @Final
    static BiFunction<Integer, Schema, Schema> SAME_NAMESPACED;

    @Inject(method = "addFixers", at = @At("TAIL"))
    private static void fishofthieves$addFixers(DataFixerBuilder builder, CallbackInfo info)
    {
        Schema schema63 = builder.addSchema(3105, SAME_NAMESPACED);
        builder.addFixer(new ItemStackFOTBucketRenameFix(schema63, false));

        var schema2 = builder.addSchema(3105, SAME_NAMESPACED);
        builder.addFixer(new EntityVariantFix(schema2, "Change splashtail variant type", References.ENTITY, "fishofthieves:splashtail", "Variant", Util.make(new Int2ObjectOpenHashMap<String>(), map ->
        {
            map.defaultReturnValue("fishofthieves:ruby");
            map.put(0, "fishofthieves:ruby");
            map.put(1, "fishofthieves:sunny");
            map.put(2, "fishofthieves:indigo");
            map.put(3, "fishofthieves:umber");
            map.put(4, "fishofthieves:seafoam");
        })::get));
    }
}