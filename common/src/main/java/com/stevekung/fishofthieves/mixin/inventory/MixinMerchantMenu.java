package com.stevekung.fishofthieves.mixin.inventory;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;

@Mixin(MerchantMenu.class)
public class MixinMerchantMenu
{
    @WrapOperation(method = "moveFromInventoryToPaymentSlot", at = @At(value = "INVOKE", target = "net/minecraft/world/item/ItemStack.isSameItemSameTags(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"))
    private boolean fishofthieves$fixTradeFishItemWithVariant(ItemStack stack, ItemStack other, Operation<Boolean> operation)
    {
        if (ItemStack.isSameItem(stack, other) && stack.is(FOTTags.Items.THIEVES_FISH) && other.is(FOTTags.Items.THIEVES_FISH))
        {
            return true;
        }
        return operation.call(stack, other);
    }
}