package com.stevekung.fishofthieves.mixin.trading;

import org.spongepowered.asm.mixin.*;

import com.stevekung.fishofthieves.item.trade.TreasuredFishMapRestock;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.VillagerTrade;

@Mixin(MerchantOffer.class)
public class MixinMerchantOffer implements TreasuredFishMapRestock
{
    @Shadow
    @Final
    @Mutable
    ItemStack result;

    @Unique
    private boolean isTreasuredFishMap;

    @Unique
    private ResourceKey<VillagerTrade> villagerTradeKey;

    @Override
    public void fishofthieves$setResult(ItemStack result)
    {
        this.result = result;
    }

    @Override
    public void fishofthieves$setIsTreasuredFishMap()
    {
        this.isTreasuredFishMap = true;
    }

    @Override
    public boolean fishofthieves$isTreasuredFishMap()
    {
        return this.isTreasuredFishMap;
    }

    @Override
    public void fishofthieves$setResourceKey(ResourceKey<VillagerTrade> key)
    {
        this.villagerTradeKey = key;
    }

    @Override
    public ResourceKey<VillagerTrade> fishofthieves$getResourceKey()
    {
        return this.villagerTradeKey;
    }
}