package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomData;

public class FOTSpawnEggItem extends SpawnEggItem
{
    public FOTSpawnEggItem(EntityType<? extends Mob> defaultType, int backgroundColor, int highlightColor, Item.Properties properties)
    {
        super(defaultType, backgroundColor, highlightColor, properties);
    }

    @Override
    public Component getName(ItemStack itemStack)
    {
        var name = super.getName(itemStack).copy();
        var customData = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);

        if (customData.isEmpty())
        {
            return name;
        }

        var compoundTag = customData.copyTag();

        if (FishOfThieves.CONFIG.general.displayTrophySpawnEggInCreativeTab && compoundTag.getBoolean(ThievesFish.TROPHY_TAG))
        {
            return name.append(" (").append(Component.translatable("entity.fishofthieves.trophy")).append(")");
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

    private static ItemStack create(Item item, boolean trophy)
    {
        var itemStack = new ItemStack(item);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> compoundTag.putBoolean(ThievesFish.TROPHY_TAG, trophy));
        return itemStack;
    }
}