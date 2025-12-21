package com.stevekung.fishofthieves.item;

import java.util.function.Consumer;

import org.jspecify.annotations.Nullable;

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
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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

public class FOTMobBucketItem<T extends AbstractFishVariant> extends MobBucketItem implements ResourceKeyHolder
{
    private final EntityType<? extends Mob> entityType;
    private final ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey;
    private final DataComponentType<Holder<T>> dataComponentType;

    public FOTMobBucketItem(EntityType<? extends Mob> entityType, Fluid fluid, SoundEvent soundEvent, ResourceKey<? extends Registry<? extends AbstractFishVariant>> resourceKey, DataComponentType<Holder<T>> dataComponentType, Item.Properties properties)
    {
        super(entityType, fluid, soundEvent, properties);
        this.entityType = entityType;
        this.resourceKey = resourceKey;
        this.dataComponentType = dataComponentType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity entity, @Nullable EquipmentSlot equipmentSlot)
    {
        // Item contains CustomModelData component
        if (itemStack.has(DataComponents.CUSTOM_MODEL_DATA))
        {
            var customModelData = itemStack.get(DataComponents.CUSTOM_MODEL_DATA).getFloat(0);

            if (FishOfThieves.CONFIG.general.enableFishItemDropWithVariant || customModelData == 0)
            {
                var variant = level.registryAccess().lookupOrThrow(this.resourceKey).listElements().sorted(AbstractFishVariant.COMPARATOR).filter(holder -> holder.value().order() == customModelData).findFirst().get();
                itemStack.set(this.dataComponentType, (Holder<T>) variant);
                itemStack.remove(DataComponents.CUSTOM_MODEL_DATA);
            }
        }
        // Remove custom data component
        else if (itemStack.has(DataComponents.CUSTOM_DATA))
        {
            var customData = itemStack.get(DataComponents.CUSTOM_DATA).copyTag();
            var variant = level.registryAccess().lookupOrThrow(this.resourceKey).listElements().sorted(AbstractFishVariant.COMPARATOR).filter(holder -> holder.is(Identifier.tryParse(customData.getStringOr(this.resourceKey.identifier().getPath(), "")))).toList().getFirst();
            itemStack.set(this.dataComponentType, (Holder<T>) variant);
            itemStack.remove(DataComponents.CUSTOM_DATA);
        }
        // Item does not have any component
        else if (!itemStack.has(this.dataComponentType))
        {
            var variant = level.registryAccess().lookupOrThrow(this.resourceKey).listElements().sorted(AbstractFishVariant.COMPARATOR).toList().getFirst();
            itemStack.set(this.dataComponentType, (Holder<T>) variant);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        if (this.entityType.is(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE))
        {
            var customData = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
            MutableComponent component = null;

            if (!itemStack.has(this.dataComponentType))
            {
                return;
            }

            var fishVariantComponent = (Holder<? extends AbstractFishVariant>) itemStack.get(this.dataComponentType);

            if (context.registries() != null && fishVariantComponent.unwrapKey().isPresent())
            {
                var fishVariantKey = fishVariantComponent.unwrapKey().get().identifier();

                for (var entry : context.registries().lookupOrThrow(this.resourceKey).listElements().sorted(AbstractFishVariant.COMPARATOR).toList())
                {
                    var variant = entry.key().identifier();

                    if (fishVariantKey.equals(variant))
                    {
                        var treasured = entry.value().treasured().isPresent();
                        var type = this.createTooltip(variant.getPath(), treasured);

                        if (!treasured && customData.copyTag().getBooleanOr(ThievesFish.TROPHY_TAG, false))
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
        var list = itemDisplayParameters.holders().lookupOrThrow(((FOTMobBucketItem<?>) item).resourceKey).listElements().sorted(AbstractFishVariant.COMPARATOR).toList();

        for (var i = 0; i < list.size(); i++)
        {
            if (!FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab && i > 0)
            {
                break;
            }

            var entry = list.get(i);

            if (FishOfThieves.CONFIG.general.displayTrophyBucketInCreativeTab && entry.value().treasured().isEmpty())
            {
                if (entry.value().treasured().isEmpty())
                {
                    output.accept(create(item, entry, false));
                }
                output.accept(create(item, entry, true));
            }
            else
            {
                output.accept(create(item, entry, null));
            }
        }
    }

    private MutableComponent createTooltip(String variant, boolean treasured)
    {
        return Component.translatable("entity.fishofthieves.%s.%s".formatted(BuiltInRegistries.ENTITY_TYPE.getKey(this.entityType).getPath(), Identifier.tryParse(variant).getPath())).withStyle(ChatFormatting.ITALIC, treasured ? ChatFormatting.GOLD : ChatFormatting.GRAY);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static ItemStack create(Item item, Holder<?> holder, Boolean trophy)
    {
        var itemStack = new ItemStack(item);
        itemStack.set(((FOTMobBucketItem<?>) item).dataComponentType, (Holder) holder);

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