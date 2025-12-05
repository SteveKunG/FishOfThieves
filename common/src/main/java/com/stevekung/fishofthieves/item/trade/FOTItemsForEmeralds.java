package com.stevekung.fishofthieves.item.trade;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;

public record FOTItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp, float priceMultiplier) implements VillagerTrades.ItemListing
{
    public FOTItemsForEmeralds(Block block, int emeraldCost, int numberOfItems, int maxUses, int villagerXp)
    {
        this(new ItemStack(block), emeraldCost, numberOfItems, maxUses, villagerXp);
    }

    public FOTItemsForEmeralds(Item item, int emeraldCost, int numberOfItems, int villagerXp)
    {
        this(new ItemStack(item), emeraldCost, numberOfItems, 12, villagerXp);
    }

    public FOTItemsForEmeralds(Item item, int emeraldCost, int numberOfItems, int maxUses, int villagerXp)
    {
        this(new ItemStack(item), emeraldCost, numberOfItems, maxUses, villagerXp);
    }

    public FOTItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp)
    {
        this(itemStack, emeraldCost, numberOfItems, maxUses, villagerXp, 0.05F);
    }

    @Override
    public MerchantOffer getOffer(Entity trader, RandomSource random)
    {
        var itemStack = this.itemStack;
        itemStack.setCount(this.numberOfItems);
        return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), itemStack, this.maxUses, this.villagerXp, this.priceMultiplier);
    }
}