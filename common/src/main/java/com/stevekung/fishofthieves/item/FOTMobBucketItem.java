package com.stevekung.fishofthieves.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public class FOTMobBucketItem extends MobBucketItem implements ResourceKeyHolder
{
    private final EntityType<?> entityType;
    private final ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey;

    public FOTMobBucketItem(EntityType<?> entityType, Fluid fluid, SoundEvent soundEvent, ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey, Item.Properties properties)
    {
        super(entityType, fluid, soundEvent, properties);
        this.entityType = entityType;
        this.resourceKey = resourceKey;
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
            if (itemStack.getTag().contains("CustomModelData") && (itemStack.getTag().getInt("CustomModelData") == 0 || FishOfThieves.CONFIG.general.enableFishItemDropWithVariant && itemStack.getTag().contains(ThievesFish.VARIANT_TAG)))
            {
                var variantOld = itemStack.getTag().getString(ThievesFish.VARIANT_TAG);
                itemStack.getOrCreateTag().putString(registryKeyTag, variantOld);
                itemStack.getOrCreateTag().remove("CustomModelData");
                itemStack.getOrCreateTag().remove(ThievesFish.VARIANT_TAG);
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
                    var treasured = entry.value().isTreasured().isPresent();
                    var type = this.createTooltip(variant.getPath(), treasured);

                    if (!treasured && compoundTag.getBoolean(ThievesFish.TROPHY_TAG))
                    {
                        type.append(", ").append(Component.translatable("entity.fishofthieves.trophy"));
                    }
                    tooltipComponents.add(type);
                }
            }
        }
    }

    @Override
    public ResourceKey<? extends Registry<? extends AbstractFishVariant>> getResourceKey()
    {
        return this.resourceKey;
    }

    public static void addFishVariantsBucket(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, Item item)
    {
        var list = itemDisplayParameters.holders().lookupOrThrow(((FOTMobBucketItem) item).resourceKey).listElements().toList();

        for (var i = 0; i < list.size(); i++)
        {
            if (!FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab && i > 0)
            {
                break;
            }

            var entry = list.get(i);
            var key = entry.key().registry().getPath();
            var variant = entry.key().location();

            var itemStack = new ItemStack(item);
            itemStack.getOrCreateTag().putString(key, variant.toString());

            if (FishOfThieves.CONFIG.general.displayTrophyBucketInCreativeTab && entry.value().isTreasured().isEmpty())
            {
                output.accept(create(item, key, variant.toString(), false));
                output.accept(create(item, key, variant.toString(), true));
            }
            else
            {
                output.accept(create(item, key, variant.toString(), null));
            }
        }
    }

    private MutableComponent createTooltip(String variant, boolean treasured)
    {
        return Component.translatable("entity.fishofthieves.%s.%s".formatted(BuiltInRegistries.ENTITY_TYPE.getKey(this.entityType).getPath(), ResourceLocation.tryParse(variant).getPath())).withStyle(ChatFormatting.ITALIC, treasured ? ChatFormatting.GOLD : ChatFormatting.GRAY);
    }

    private static ItemStack create(Item item, String registryPath, String variant, Boolean trophy)
    {
        var itemStack = new ItemStack(item);
        itemStack.getOrCreateTag().putString(registryPath, variant);

        if (!FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            itemStack.getOrCreateTag().putBoolean(ThievesFish.CREATIVE_TAG, true);
        }
        if (trophy != null)
        {
            itemStack.getOrCreateTag().putBoolean(ThievesFish.TROPHY_TAG, trophy);
        }
        return itemStack;
    }

    public static ItemStack createRandomBucket(Item item)
    {
        var itemStack = new ItemStack(item);
        itemStack.getOrCreateTag().putBoolean(ThievesFish.CREATIVE_TAG, true);
        return itemStack;
    }
}