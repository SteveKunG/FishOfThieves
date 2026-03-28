package com.stevekung.fishofthieves.mixin.datafix;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

import net.minecraft.util.datafix.fixes.EntitySpawnerItemVariantComponentFix;

@Mixin(EntitySpawnerItemVariantComponentFix.class)
public class MixinEntitySpawnerItemVariantComponentFix
{
    @Unique
    private static final Map<String, String> FISH_BUCKET_COMPONENTS = Map.of(
            "fishofthieves:splashtail_bucket", "splashtail/variant",
            "fishofthieves:pondie_bucket", "pondie/variant",
            "fishofthieves:islehopper_bucket", "islehopper/variant",
            "fishofthieves:ancientscale_bucket", "ancientscale/variant",
            "fishofthieves:plentifin_bucket", "plentifin/variant",
            "fishofthieves:wildsplash_bucket", "wildsplash/variant",
            "fishofthieves:devilfish_bucket", "devilfish/variant",
            "fishofthieves:battlegill_bucket", "battlegill/variant",
            "fishofthieves:wrecker_bucket", "wrecker/variant",
            "fishofthieves:stormfish_bucket", "stormfish/variant"
    );

    @Inject(method = "lambda$makeRule$0(Lcom/mojang/datafixers/OpticFinder;Lcom/mojang/datafixers/OpticFinder;Lcom/mojang/datafixers/Typed;)Lcom/mojang/datafixers/Typed;",
            cancellable = true,
            at = @At(value = "HEAD"))
    private static void fishofthieves$fixThievesBucket(OpticFinder<Pair<String, String>> opticFinder, OpticFinder<?> opticFinder2, Typed<?> typed, CallbackInfoReturnable<Typed<?>> info)
    {
        var itemName = typed.getOptional(opticFinder).map(Pair::getSecond).orElse("");
        var component = FISH_BUCKET_COMPONENTS.get(itemName);

        if (component != null)
        {
            info.setReturnValue(typed.updateTyped(opticFinder2, typed1 -> new EntitySpawnerItemVariantComponentFix.Fixer()
            {
                @Override
                public <T> Dynamic<T> fixRemainder(Dynamic<T> dynamic, Dynamic<T> dynamic2)
                {
                    return fixThievesFishBucket(component, dynamic, dynamic2);
                }
            }.apply(typed1)));
        }
    }

    @Unique
    private static <T> Dynamic<T> fixThievesFishBucket(String component, Dynamic<T> dynamic, Dynamic<T> dynamic2)
    {
        var optional = dynamic2.get("variant").asString().result();

        if (optional.isEmpty())
        {
            return dynamic;
        }
        else
        {
            return dynamic.update("minecraft:bucket_entity_data", dynamicx -> dynamicx.remove("variant"))
                    .set("fishofthieves:" + component, dynamic.createString(optional.get()));
        }
    }
}