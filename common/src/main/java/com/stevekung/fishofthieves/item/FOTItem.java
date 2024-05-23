package com.stevekung.fishofthieves.item;

import java.util.List;
import java.util.function.Consumer;

import com.stevekung.fishofthieves.FishOfThieves;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomModelData;

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

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            var variantMap = Util.make(new Int2ObjectOpenHashMap<>(), this.dataFixMap);
            String variant;

            if (itemStack.has(DataComponents.CUSTOM_MODEL_DATA))
            {
                var data = itemStack.get(DataComponents.CUSTOM_MODEL_DATA).value();
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
        itemStack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(index));
        return itemStack;
    }
}