package com.stevekung.fishofthieves.common.mixin.animal;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.google.common.collect.ObjectArrays;
import com.stevekung.fishofthieves.common.registry.FOTEarlyItems;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

@Mixin(Ocelot.class)
public class MixinOcelot
{
    @Shadow
    @Final
    @Mutable
    static Ingredient TEMPT_INGREDIENT;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void fishofthieves$addFoods(CallbackInfo info)
    {
        TEMPT_INGREDIENT = Ingredient.of(ObjectArrays.concat(TEMPT_INGREDIENT.getItems(), FOTEarlyItems.CAT_FOODS, ItemStack.class));
    }
}