package com.stevekung.fishofthieves.mixin.animal;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import com.google.common.collect.ObjectArrays;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

@Mixin(Chicken.class)
public class MixinChicken
{
    @Shadow
    @Final
    @Mutable
    static Ingredient FOOD_ITEMS;

    @ModifyArg(method = "registerGoals", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/ai/goal/TemptGoal.<init>(Lnet/minecraft/world/entity/PathfinderMob;DLnet/minecraft/world/item/crafting/Ingredient;Z)V"), index = 2)
    private Ingredient fishofthieves$addNewTempt(Ingredient original)
    {
        return FOOD_ITEMS = Ingredient.of(ObjectArrays.concat(FOOD_ITEMS.getItems(), Ingredient.of(FOTTags.Items.WORMS).getItems(), ItemStack.class));
    }
}