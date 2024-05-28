package com.stevekung.fishofthieves.common.item;

import com.stevekung.fishofthieves.common.FishOfThieves;
import com.stevekung.fishofthieves.common.entity.ThievesFish;
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
    public Component getName(ItemStack itemStack)
    {
        var name = super.getName(itemStack).copy();

        if (FishOfThieves.CONFIG.general.displayTrophySpawnEggInCreativeTab && itemStack.hasTag() && itemStack.getTagElement("EntityTag").getBoolean(ThievesFish.TROPHY_TAG))
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
        var compound = new CompoundTag();
        compound.putBoolean(ThievesFish.TROPHY_TAG, trophy);
        itemStack.getOrCreateTag().put("EntityTag", compound);
        return itemStack;
    }
}