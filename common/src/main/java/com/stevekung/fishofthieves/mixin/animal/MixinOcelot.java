package com.stevekung.fishofthieves.mixin.animal;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.ObjectArrays;
import com.stevekung.fishofthieves.registry.FOTEarlyItems;

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

    @Unique
    private static boolean MODIFIED;

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void fishofthieves$addFoods(CallbackInfo info)
    {
        // Modifying tempt items right after creating this entity instance
        // This fixes compatibility with other mods. Entity food item tag doesn't exist in this version yet, so I had no choice...
        if (!MODIFIED)
        {
            TEMPT_INGREDIENT = Ingredient.of(ObjectArrays.concat(TEMPT_INGREDIENT.getItems(), FOTEarlyItems.Cat.CAT_FOODS, ItemStack.class));
            MODIFIED = true;
        }
    }
}