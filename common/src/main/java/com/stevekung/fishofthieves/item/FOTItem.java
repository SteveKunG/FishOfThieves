package com.stevekung.fishofthieves.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FOTItem extends Item implements ResourceKeyHolder
{
    private final EntityType<?> entityType;
    private final ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey;

    public FOTItem(Properties properties, EntityType<?> entityType, ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey)
    {
        super(properties);
        this.entityType = entityType;
        this.resourceKey = resourceKey;
    }

    public static void addFishVariants(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, Item item)
    {
        var list = itemDisplayParameters.holders().lookupOrThrow(((FOTItem) item).resourceKey).listElements().toList();

        for (var i = 0; i < list.size(); i++)
        {
            if (!FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab && i > 0)
            {
                break;
            }

            var entry = list.get(i);
            var key = entry.key().registry().getPath();
            var variant = entry.key().location();

            output.accept(create(item, key, variant.toString()));
        }
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slotId, boolean isSelected)
    {
        var registryKeyTag = this.resourceKey.location().getPath();

        // Item does not have any tag
        if (!itemStack.hasTag())
        {
            var variant = level.registryAccess().registryOrThrow(this.resourceKey).holders().toList().get(0).key().location().toString();
            itemStack.getOrCreateTag().putString(registryKeyTag, variant);
        }
        // Item contains CustomModelData tag
        else
        {
            if (FishOfThieves.CONFIG.general.enableFishItemDropWithVariant && itemStack.getTag().contains("CustomModelData"))
            {
                var customModelData = itemStack.getTag().getInt("CustomModelData");
                var variant = level.registryAccess().registryOrThrow(this.resourceKey).holders().toList().get(customModelData).key().location().toString();
                itemStack.getOrCreateTag().putString(registryKeyTag, variant);
                itemStack.getOrCreateTag().remove("CustomModelData");
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced)
    {
        var compoundTag = itemStack.getTag();

        if (level != null && itemStack.hasTag())
        {
            for (var entry : level.registryAccess().registryOrThrow(this.resourceKey).holders().toList())
            {
                var key = entry.key().registry().getPath();
                var variant = entry.key().location();

                if (compoundTag.getString(key).equals(variant.toString()))
                {
                    tooltipComponents.add(Component.translatable(this.entityType.getDescriptionId() + "." + variant.getPath()).withStyle(ChatFormatting.ITALIC, entry.value().isTreasured().isPresent() ? ChatFormatting.GOLD : ChatFormatting.GRAY));
                }
            }
        }
    }

    @Override
    public ResourceKey<? extends Registry<? extends AbstractFishVariant>> getResourceKey()
    {
        return this.resourceKey;
    }

    public static ItemStack create(Item item, String registryPath, String variant)
    {
        var itemStack = new ItemStack(item);
        itemStack.getOrCreateTag().putString(registryPath, variant);
        return itemStack;
    }

    public static ItemStack generateRandomFishVariantLootItem(ItemStack itemStack, ServerLevel level, @Nullable Vec3 vec3, RandomSource randomSource)
    {
        if (itemStack.getItem() instanceof ResourceKeyHolder keyHolder)
        {
            if (FishOfThieves.CONFIG.general.enableFishItemDropWithVariant)
            {
                if (vec3 != null)
                {
                    // Variant items
                    var context = SpawnSelectors.get(level, BlockPos.containing(vec3), randomSource);
                    Util.getRandomSafe(level.registryAccess().registryOrThrow(keyHolder.getResourceKey()).holders().filter(holder -> holder.value().getCondition().test(context)).toList(), randomSource).ifPresent(holder -> itemStack.getOrCreateTag().putString(holder.key().registry().getPath(), holder.key().location().toString()));
                }
            }
            else
            {
                // Default variant item
                var holder = level.registryAccess().registryOrThrow(keyHolder.getResourceKey()).holders().toList().get(0);
                itemStack.getOrCreateTag().putString(holder.key().registry().getPath(), holder.key().location().toString());
            }
        }
        return itemStack;
    }
}