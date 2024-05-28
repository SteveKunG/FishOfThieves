package com.stevekung.fishofthieves.common.mixin.animal;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.stevekung.fishofthieves.common.registry.FOTTags;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

@Mixin(Chicken.class)
public abstract class MixinChicken extends Animal
{
    private static final Ingredient WORM_FOOD_ITEMS = Ingredient.of(FOTTags.Items.WORMS);

    MixinChicken()
    {
        super(null, null);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void fishofthieves$addNewTempt(CallbackInfo info)
    {
        this.goalSelector.addGoal(3, new TemptGoal(Chicken.class.cast(this), 1.0, WORM_FOOD_ITEMS, false));
    }

    @Inject(method = "isFood", cancellable = true, at = @At("HEAD"))
    private void fishofthieves$isWorms(ItemStack itemStack, CallbackInfoReturnable<Boolean> info)
    {
        if (WORM_FOOD_ITEMS.test(itemStack))
        {
            info.setReturnValue(true);
        }
    }
}