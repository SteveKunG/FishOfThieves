package com.stevekung.fishofthieves.item;

import java.util.function.Consumer;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.material.Fluid;

public class FOTMobBucketItem extends MobBucketItem implements ResourceKeyHolder
{
    private final EntityType<? extends Mob> entityType;
    private final ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey;
    private final DataComponentType<?> dataComponentType;

    public FOTMobBucketItem(EntityType<? extends Mob> entityType, Fluid fluid, SoundEvent soundEvent, ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey, DataComponentType<?> dataComponentType, Item.Properties properties)
    {
        super(entityType, fluid, soundEvent, properties);
        this.entityType = entityType;
        this.resourceKey = resourceKey;
        this.dataComponentType = dataComponentType;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity entity, EquipmentSlot equipmentSlot)
    {
        var registryKeyTag = this.resourceKey.location().getPath();

        // Item contains CustomModelData component
        if (itemStack.has(DataComponents.CUSTOM_MODEL_DATA))
        {
            if (FishOfThieves.CONFIG.general.enableFishItemDropWithVariant)
            {
                var bucketEntityData = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
                CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag ->
                {
                    compoundTag.putString(registryKeyTag, bucketEntityData.getUnsafe().getString(ThievesFish.VARIANT_TAG).orElseThrow());
                    compoundTag.remove(ThievesFish.VARIANT_TAG);
                });
                itemStack.remove(DataComponents.CUSTOM_MODEL_DATA);
            }
        }
        // Item does not have any component
        else if (!itemStack.has(DataComponents.BUCKET_ENTITY_DATA))
        {
            var variant = level.registryAccess().lookupOrThrow(this.resourceKey).listElements().sorted(AbstractFishVariant.COMPARATOR).toList().getFirst().key().location().toString();
            itemStack.set(DataComponents.BUCKET_ENTITY_DATA, FOTItem.createCustomData(registryKeyTag, variant));
        }
        else if (itemStack.has(DataComponents.CUSTOM_DATA))
        {
            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> compoundTag.merge(itemStack.get(DataComponents.CUSTOM_DATA).copyTag()));
            itemStack.remove(DataComponents.CUSTOM_DATA);
        }
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        if (this.entityType.is(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE))
        {
            MutableComponent component = null;
            var fishData = (Holder<? extends AbstractFishVariant>) itemStack.get(this.dataComponentType);

            if (fishData != null)
            {
//                type = this.createTooltip(fishData.value().name());TODO Apply component instead of bucket_entity_data
            }

            var customData = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);

            if (context.registries() != null && !customData.isEmpty())
            {
                var compoundTag = customData.copyTag();

                for (var entry : context.registries().lookupOrThrow(this.resourceKey).listElements().sorted(AbstractFishVariant.COMPARATOR).toList())
                {
                    var key = entry.key().registry().getPath();
                    var variant = entry.key().location();

                    if (compoundTag.getStringOr(key, "").equals(variant.toString()))
                    {
                        var treasured = entry.value().treasured().isPresent();
                        var type = this.createTooltip(variant.getPath(), treasured);

                        if (!treasured && compoundTag.getBooleanOr(ThievesFish.TROPHY_TAG, false))
                        {
                            type.append(", ").append(Component.translatable("entity.fishofthieves.trophy"));
                        }
                        component = type;
                    }
                }

                if (component != null)
                {
                    consumer.accept(component);
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
        var list = itemDisplayParameters.holders().lookupOrThrow(((FOTMobBucketItem) item).resourceKey).listElements().sorted(AbstractFishVariant.COMPARATOR).toList();

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
            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> compoundTag.putString(key, variant.toString()));

            if (FishOfThieves.CONFIG.general.displayTrophyBucketInCreativeTab && entry.value().treasured().isEmpty())
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
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> compoundTag.putString(registryPath, variant));

        if (!FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> compoundTag.putBoolean(ThievesFish.CREATIVE_TAG, true));
        }
        if (trophy != null)
        {
            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> compoundTag.putBoolean(ThievesFish.TROPHY_TAG, trophy));
        }
        return itemStack;
    }

    public static ItemStack createRandomBucket(Item item)
    {
        var itemStack = new ItemStack(item);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> compoundTag.putBoolean(ThievesFish.CREATIVE_TAG, true));
        return itemStack;
    }
}