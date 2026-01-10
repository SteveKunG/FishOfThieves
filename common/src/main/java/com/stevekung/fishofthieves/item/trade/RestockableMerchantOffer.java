package com.stevekung.fishofthieves.item.trade;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

public class RestockableMerchantOffer extends MerchantOffer implements TreasuredFishMapRestock
{
    public RestockableMerchantOffer(int tier, ItemStack baseCostA, ItemStack costB, ItemStack result, int maxUses, int xp, float priceMultiplier)
    {
        super(baseCostA, costB, result, 0, maxUses, xp, priceMultiplier);
        this.fishofthieves$setIsTreasuredFishMap(true);
        this.fishofthieves$setTier(tier);
    }
}