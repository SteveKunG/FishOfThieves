package com.stevekung.fishofthieves.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.SpawnConditionContext;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.item.component.CustomData;
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
        if (item instanceof ResourceKeyHolder keyHolder)
        {
            var list = itemDisplayParameters.holders().lookupOrThrow(keyHolder.getResourceKey()).listElements().sorted(AbstractFishVariant.COMPARATOR).toList();

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
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slotId, boolean isSelected)
    {
        var registryKeyTag = this.resourceKey.location().getPath();

        // Item contains CustomModelData component
        if (itemStack.has(DataComponents.CUSTOM_MODEL_DATA))
        {
            var customModelData = itemStack.get(DataComponents.CUSTOM_MODEL_DATA).value();

            if (FishOfThieves.CONFIG.general.enableFishItemDropWithVariant || customModelData == 0)
            {
                var variant = level.registryAccess().registryOrThrow(this.resourceKey).holders().sorted(AbstractFishVariant.COMPARATOR).filter(holder -> holder.value().order() == customModelData).findFirst().get().key().location().toString();
                itemStack.set(DataComponents.CUSTOM_DATA, createCustomData(registryKeyTag, variant));
                itemStack.remove(DataComponents.CUSTOM_MODEL_DATA);
            }
        }
        // Item does not have any component
        else if (!itemStack.has(DataComponents.CUSTOM_DATA))
        {
            var variant = level.registryAccess().registryOrThrow(this.resourceKey).holders().sorted(AbstractFishVariant.COMPARATOR).toList().getFirst().key().location().toString();
            itemStack.set(DataComponents.CUSTOM_DATA, createCustomData(registryKeyTag, variant));
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        var customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

        if (context.registries() != null)
        {
            for (var entry : context.registries().lookupOrThrow(this.resourceKey).listElements().sorted(AbstractFishVariant.COMPARATOR).toList())
            {
                var key = entry.key().registry().getPath();
                var variant = entry.key().location();

                if (customData.copyTag().getString(key).equals(variant.toString()))
                {
                    tooltipComponents.add(Component.translatable(this.entityType.getDescriptionId() + "." + variant.getPath()).withStyle(ChatFormatting.ITALIC, entry.value().treasured().isPresent() ? ChatFormatting.GOLD : ChatFormatting.GRAY));
                }
            }
        }
    }

    @Override
    public ResourceKey<? extends Registry<? extends AbstractFishVariant>> getResourceKey()
    {
        return this.resourceKey;
    }

    public static ItemStack generateRandomFishVariantLootItem(ItemStack itemStack, @Nullable Entity entity, ServerLevel level, @Nullable Vec3 vec3, RandomSource randomSource)
    {
        if (FishOfThieves.CONFIG.general.enableFishItemDropWithVariant && itemStack.getItem() instanceof ResourceKeyHolder keyHolder)
        {
            if (vec3 != null)
            {
                var context = new SpawnConditionContext(level, entity, level.registryAccess(), BlockPos.containing(vec3.x, vec3.y, vec3.z), randomSource);
                Util.getRandomSafe(level.registryAccess()
                                .registryOrThrow(keyHolder.getResourceKey())
                                .holders()
                                .filter(holder -> holder.value().spawnSettings().fishing().isPresent() ? Util.allOf(holder.value().spawnSettings().fishing().get()).test(context) : Util.allOf(holder.value().spawnSettings().entity()).test(context)).toList(), randomSource)
                        .ifPresent(holder -> itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(Util.make(new CompoundTag(), compoundTag -> compoundTag.putString(keyHolder.getResourceKey().location().getPath(), holder.key().location().toString())))));
            }
        }
        return itemStack;
    }

    public static ItemStack create(Item item, String registryPath, String variant)
    {
        var itemStack = new ItemStack(item);
        itemStack.set(DataComponents.CUSTOM_DATA, createCustomData(registryPath, variant));
        return itemStack;
    }

    public static CustomData createCustomData(String registryPath, String variant)
    {
        return CustomData.of(Util.make(new CompoundTag(), compoundTag -> compoundTag.putString(registryPath, variant)));
    }

    public static CustomData createDefaultCustomData(ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey, ResourceKey<? extends AbstractFishVariant> variant)
    {
        return CustomData.of(Util.make(new CompoundTag(), compoundTag -> compoundTag.putString(resourceKey.location().getPath(), variant.location().toString())));
    }
}