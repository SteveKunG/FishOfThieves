package com.stevekung.fishofthieves.item;

import java.util.function.Consumer;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TypedEntityData;

public class FOTSpawnEggItem<T extends AbstractFishVariant> extends SpawnEggItem
{
    private final ResourceKey<Registry<T>> resourceKey;
    private final DataComponentType<Holder<T>> dataComponentType;

    public FOTSpawnEggItem(ResourceKey<Registry<T>> resourceKey, DataComponentType<Holder<T>> dataComponentType,  Item.Properties properties)
    {
        super(properties);
        this.resourceKey = resourceKey;
        this.dataComponentType = dataComponentType;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        if (context.registries() != null)
        {
            var entityData = itemStack.get(DataComponents.ENTITY_DATA);

            if (entityData == null)
            {
                return;
            }

            var entityTag = entityData.copyTagWithoutId();
            var trophy = entityTag.getBoolean(ThievesFish.TROPHY_TAG);
            var treasured = false;
            var spawnEgg = (FOTSpawnEggItem<?>) itemStack.getItem();

            if (itemStack.has(spawnEgg.dataComponentType))
            {
                var component = itemStack.get(spawnEgg.dataComponentType).unwrapKey();

                if (component.isPresent())
                {
                    for (var entry : context.registries().lookupOrThrow(this.resourceKey).listElements().toList())
                    {
                        if (entry.value().treasured().isPresent() && itemStack.get(spawnEgg.dataComponentType).is(component.get().location()))
                        {
                            consumer.accept(Component.translatable(this.getType(itemStack).getDescriptionId() + "." + entry.key().location().getPath()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GOLD));
                            treasured = true;
                        }
                    }
                }
            }
            if (trophy.isPresent() && !treasured)
            {
                consumer.accept(Component.translatable(trophy.get() ? "entity.fishofthieves.trophy" : "entity.fishofthieves.non_trophy").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            }
        }
    }

    public static void addSpawnEgg(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, Item item)
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

        var spawnEgg = (FOTSpawnEggItem<?>) item;

        for (var entry : itemDisplayParameters.holders().lookupOrThrow(spawnEgg.resourceKey).listElements().filter(holder -> holder.value().treasured().isPresent()).toList())
        {
            output.accept(createTreasured(item, entry));
        }
    }

    private static ItemStack create(Item item, boolean trophy)
    {
        var itemStack = new ItemStack(item);
        itemStack.set(DataComponents.ENTITY_DATA, TypedEntityData.of(itemStack.get(DataComponents.ENTITY_DATA).type(), Util.make(new CompoundTag(), compoundTag -> compoundTag.putBoolean(ThievesFish.TROPHY_TAG, trophy))));
        return itemStack;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static ItemStack createTreasured(Item item, Holder<?> holder)
    {
        var itemStack = new ItemStack(item);
        var spawnEgg = (FOTSpawnEggItem<?>) item;
        itemStack.set(DataComponents.ENTITY_DATA, TypedEntityData.of(itemStack.get(DataComponents.ENTITY_DATA).type(), Util.make(new CompoundTag(), compoundTag -> compoundTag.putBoolean(ThievesFish.TROPHY_TAG, true))));
        itemStack.set(spawnEgg.dataComponentType, (Holder) holder);
        return itemStack;
    }
}