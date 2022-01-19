package com.stevekung.fishofthieves.mixin.animal;

import java.util.stream.Stream;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.base.Suppliers;
import com.stevekung.fishofthieves.FOTItems;

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
    private static void addFoods(CallbackInfo info)
    {
        var catFoods = Suppliers.<Stream<ItemStack>>memoize(() -> Stream.of(FOTItems.CAT_FOODS));
        TEMPT_INGREDIENT = Ingredient.of(Stream.<ItemStack>concat(Stream.of(TEMPT_INGREDIENT.getItems()), catFoods.get()));
    }
}