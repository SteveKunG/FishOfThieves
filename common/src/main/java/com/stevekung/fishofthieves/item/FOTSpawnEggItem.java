package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

public class FOTSpawnEggItem extends SpawnEggItem
{
    public FOTSpawnEggItem(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public Component getName(ItemStack itemStack)
    {
        var name = super.getName(itemStack).copy();

        if (FishOfThieves.CONFIG.general.displayTrophySpawnEggInCreativeTab)
        {
            var entityData = itemStack.get(DataComponents.ENTITY_DATA);

            if (entityData != null)
            {
                if (entityData.copyTagWithoutId().getBooleanOr(ThievesFish.TROPHY_TAG, false))
                {
                    return name.append(" (").append(Component.translatable("entity.fishofthieves.trophy")).append(")");
                }
            }
        }
        return name;
    }

    public static void addTrophySpawnEgg(CreativeModeTab.Output output, Item item)
    {
        if (FishOfThieves.CONFIG.general.displayTrophySpawnEggInCreativeTab)
        {
            output.accept(create(item, false));
            output.accept(create(item, true));
        }
        else
        {
            output.accept(item);
        }
    }

    @SuppressWarnings("deprecation")
    private static ItemStack create(Item item, boolean trophy)
    {
        var itemStack = new ItemStack(item);
        itemStack.get(DataComponents.ENTITY_DATA).getUnsafe().putBoolean(ThievesFish.TROPHY_TAG, trophy);
        return itemStack;
    }
}