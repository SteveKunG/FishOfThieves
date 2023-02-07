package com.stevekung.fishofthieves.item;

import java.util.List;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.FishOfThieves;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class FOTItem extends Item
{
    private final EntityType<?> entityType;
    private final Consumer<Int2ObjectOpenHashMap<String>> dataFixMap;

    public FOTItem(Properties properties, EntityType<?> entityType, Consumer<Int2ObjectOpenHashMap<String>> dataFixMap)
    {
        super(properties);
        this.entityType = entityType;
        this.dataFixMap = dataFixMap;
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items)
    {
        super.fillItemCategory(tab, items);

        if (this.allowedIn(tab) && FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            for (var i = 1; i < 5; i++)
            {
                items.add(create(this, i));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced)
    {
        var compoundTag = itemStack.getTag();

        if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            var variantMap = Util.make(new Int2ObjectOpenHashMap<>(), this.dataFixMap);
            String variant;

            if (itemStack.hasTag() && compoundTag.contains("CustomModelData", Tag.TAG_INT))
            {
                var data = compoundTag.getInt("CustomModelData");
                variant = variantMap.get(data);
            }
            else
            {
                variant = variantMap.get(0);
            }
            tooltipComponents.add(Component.translatable(this.entityType.getDescriptionId() + "." + ResourceLocation.tryParse(variant).getPath()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        }
    }

    private static ItemStack create(Item item, int index)
    {
        var itemStack = new ItemStack(item);
        itemStack.getOrCreateTag().putInt("CustomModelData", index);
        return itemStack;
    }
}