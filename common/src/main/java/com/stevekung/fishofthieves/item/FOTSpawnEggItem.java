package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

public class FOTSpawnEggItem extends SpawnEggItem
{
    public FOTSpawnEggItem(EntityType<? extends Mob> defaultType, int backgroundColor, int highlightColor, Item.Properties properties)
    {
        super(defaultType, backgroundColor, highlightColor, properties);
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items)
    {
        if (this.allowedIn(tab))
        {
            if (FishOfThieves.CONFIG.general.displayTrophySpawnEggInCreativeTab)
            {
                items.add(create(this, false));
                items.add(create(this, true));
            }
            else
            {
                super.fillItemCategory(tab, items);
            }
        }
    }

    @Override
    public Component getName(ItemStack itemStack)
    {
        var name = super.getName(itemStack).copy();

        if (FishOfThieves.CONFIG.general.displayTrophySpawnEggInCreativeTab && itemStack.hasTag() && itemStack.getTagElement("EntityTag").getBoolean(ThievesFish.TROPHY_TAG))
        {
            return name.append(" (").append(Component.translatable("entity.fishofthieves.trophy")).append(")");
        }
        return name;
    }

    private static ItemStack create(Item item, boolean trophy)
    {
        var itemStack = new ItemStack(item);
        var compound = new CompoundTag();
        compound.putBoolean(ThievesFish.TROPHY_TAG, trophy);
        itemStack.getOrCreateTag().put("EntityTag", compound);
        return itemStack;
    }
}