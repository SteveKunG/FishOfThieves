package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FOTItem extends Item
{
    public FOTItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items)
    {
        super.fillItemCategory(tab, items);

        if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            for (var i = 1; i < 5; i++)
            {
                items.add(create(this, i));
            }
        }
    }

    private static ItemStack create(Item item, int index)
    {
        var itemStack = new ItemStack(item);
        itemStack.getOrCreateTag().putInt("CustomModelData", index);
        return itemStack;
    }
}