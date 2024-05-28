package com.stevekung.fishofthieves.common.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.BiMap;
import com.stevekung.fishofthieves.common.FishOfThieves;
import net.minecraft.ChatFormatting;
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
    private final BiMap<String, Integer> variantToCustomModelData;

    public FOTItem(Properties properties, EntityType<?> entityType, BiMap<String, Integer> variantToCustomModelData)
    {
        super(properties);
        this.entityType = entityType;
        this.variantToCustomModelData = variantToCustomModelData;
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
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced)
    {
        var compoundTag = itemStack.getTag();

        if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            String variant;

            if (itemStack.hasTag() && compoundTag.contains("CustomModelData", Tag.TAG_INT))
            {
                var data = compoundTag.getInt("CustomModelData");
                variant = this.variantToCustomModelData.inverse().get(data);
            }
            else
            {
                variant = this.variantToCustomModelData.inverse().get(0);
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