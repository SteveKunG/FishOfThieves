package com.stevekung.fishofthieves.mixin.animal;

import java.util.function.Supplier;
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

import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

@Mixin(Cat.class)
public class MixinCat
{
    @Shadow
    @Final
    @Mutable
    static Ingredient TEMPT_INGREDIENT;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addFoods(CallbackInfo info)
    {
        Supplier<Stream<ItemStack>> catFoods = Suppliers.memoize(() -> Stream.of(new ItemStack(FOTItems.SPLASHTAIL), new ItemStack(FOTItems.PONDIE)));
        TEMPT_INGREDIENT = Ingredient.of(Stream.<ItemStack>concat(Stream.of(TEMPT_INGREDIENT.getItems()), catFoods.get()));
    }
}