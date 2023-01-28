package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FOTItem extends Item
{
    public FOTItem(Properties properties)
    {
        super(properties);
    }

    public static void addFishVariants(CreativeModeTab.Output output, Item item)
    {
        output.accept(item);

        if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            for (var i = 1; i < 5; i++)
            {
                output.accept(create(item, i));
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