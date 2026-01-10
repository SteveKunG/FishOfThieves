package com.stevekung.fishofthieves.mixin.trading;

import org.spongepowered.asm.mixin.*;

import com.stevekung.fishofthieves.item.trade.TreasuredFishMapRestock;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

@Mixin(MerchantOffer.class)
public class MixinMerchantOffer implements TreasuredFishMapRestock
{
    @Shadow
    @Final
    @Mutable
    ItemStack result;

    @Override
    public void fishofthieves$setResult(ItemStack result)
    {
        this.result = result;
    }
}