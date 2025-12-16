package com.stevekung.fishofthieves.item;

import java.util.function.Consumer;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;

import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;

public class FOTSpawnEggItem extends SpawnEggItem
{
    private final ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey;

    public FOTSpawnEggItem(EntityType<? extends Mob> defaultType, ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey, Item.Properties properties)
    {
        super(defaultType, properties);
        this.resourceKey = resourceKey;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        if (context.registries() != null)
        {
            var entityTag = itemStack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY).copyTag();
            var trophy = entityTag.getBooleanOr(ThievesFish.TROPHY_TAG, false);
            var treasured = false;

            for (var entry : context.registries().lookupOrThrow(this.resourceKey).listElements().toList())
            {
                if (entry.value().treasured().isPresent() && entityTag.getStringOr(ThievesFish.VARIANT_TAG, "").equals(entry.key().location().toString()))
                {
                    consumer.accept(Component.translatable(this.getType(context.registries(), itemStack).getDescriptionId() + "." + entry.key().location().getPath()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GOLD));
                    treasured = true;
                }
            }
            if (!treasured)
            {
                consumer.accept(Component.translatable(trophy ? "entity.fishofthieves.trophy" : "entity.fishofthieves.non_trophy").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            }
        }
    }

    public static void addSpawnEgg(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, Item item)
    {
        if (FishOfThieves.CONFIG.general.displayTrophySpawnEggInCreativeTab)
        {
            output.accept(create(itemDisplayParameters.holders(), item, false));
            output.accept(create(itemDisplayParameters.holders(), item, true));
        }
        else
        {
            output.accept(item);
        }

        var spawnEgg = (FOTSpawnEggItem) item;

        for (var entry : itemDisplayParameters.holders().lookupOrThrow(spawnEgg.resourceKey).listElements().filter(holder -> holder.value().treasured().isPresent()).toList())
        {
            output.accept(createTreasured(item, entry.key().location().toString()));
        }
    }

    private static ItemStack create(HolderLookup.Provider provider, Item item, boolean trophy)
    {
        var itemStack = new ItemStack(item);
        CustomData.update(DataComponents.ENTITY_DATA, itemStack, compoundTag ->
        {
            compoundTag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(((SpawnEggItem) item).getType(provider, itemStack)).toString());
            compoundTag.putBoolean(ThievesFish.TROPHY_TAG, trophy);
        });
        return itemStack;
    }

    private static ItemStack createTreasured(Item item, String variant)
    {
        var itemStack = new ItemStack(item);
        CustomData.update(DataComponents.ENTITY_DATA, itemStack, compoundTag ->
        {
            compoundTag.putBoolean(ThievesFish.TROPHY_TAG, true);
            compoundTag.putString(ThievesFish.VARIANT_TAG, variant);
        });
        return itemStack;
    }
}