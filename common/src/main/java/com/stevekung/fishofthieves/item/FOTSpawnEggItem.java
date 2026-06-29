package com.stevekung.fishofthieves.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class FOTSpawnEggItem extends SpawnEggItem
{
    private final ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey;

    public FOTSpawnEggItem(EntityType<? extends Mob> defaultType, int backgroundColor, int highlightColor, ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey, Item.Properties properties)
    {
        super(defaultType, backgroundColor, highlightColor, properties);
        this.resourceKey = resourceKey;
    }

    @Override
    public int getColor(int tintIndex)
    {
        return -1;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced)
    {
        if (level != null && itemStack.hasTag())
        {
            var entityTag = itemStack.getTagElement("EntityTag");
            var trophy = entityTag.getBoolean(ThievesFish.TROPHY_TAG);
            var treasured = false;

            for (var entry : level.registryAccess().registryOrThrow(this.resourceKey).holders().toList())
            {
                if (entry.value().isTreasured().isPresent() && entityTag.getString(ThievesFish.VARIANT_TAG).equals(entry.key().location().toString()))
                {
                    tooltipComponents.add(Component.translatable(this.getType(null).getDescriptionId() + "." + entry.key().location().getPath()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GOLD));
                    treasured = true;
                }
            }
            if (!treasured)
            {
                tooltipComponents.add(Component.translatable(trophy ? "entity.fishofthieves.trophy" : "entity.fishofthieves.non_trophy").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
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

        var spawnEgg = (FOTSpawnEggItem) item;

        for (var entry : itemDisplayParameters.holders().lookupOrThrow(spawnEgg.resourceKey).listElements().filter(holder -> holder.value().isTreasured().isPresent()).toList())
        {
            output.accept(createTreasured(item, entry.key().location().toString()));
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

    private static ItemStack createTreasured(Item item, String variant)
    {
        var itemStack = new ItemStack(item);
        var compound = new CompoundTag();
        compound.putBoolean(ThievesFish.TROPHY_TAG, true);
        compound.putString(ThievesFish.VARIANT_TAG, variant);
        itemStack.getOrCreateTag().put("EntityTag", compound);
        return itemStack;
    }
}