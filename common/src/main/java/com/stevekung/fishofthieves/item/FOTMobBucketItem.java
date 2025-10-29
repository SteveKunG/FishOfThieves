package com.stevekung.fishofthieves.item;

import java.util.Comparator;
import java.util.function.Consumer;

import org.jspecify.annotations.Nullable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
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

public class FOTMobBucketItem extends MobBucketItem
{
    private final EntityType<? extends Mob> entityType;
    private final ResourceLocation registryKey;
    private final DataComponentType<?> dataComponentType;

    public FOTMobBucketItem(EntityType<? extends Mob> entityType, Fluid fluid, SoundEvent soundEvent, ResourceKey<?> registryKey, DataComponentType<?> dataComponentType, Item.Properties properties)
    {
        super(entityType, fluid, soundEvent, properties);
        this.entityType = entityType;
        this.registryKey = registryKey.location();
        this.dataComponentType = dataComponentType;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot)
    {
        if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab && !itemStack.has(DataComponents.CUSTOM_MODEL_DATA))
        {
            // item without a custom model data component is always 0 if enable all fish variants
            itemStack.set(DataComponents.CUSTOM_MODEL_DATA, FOTItem.createCustomModelData(0));
        }
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        if (this.entityType.is(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE))
        {
            MutableComponent type = null;
            var fishData = (Holder<? extends AbstractFishVariant>) itemStack.get(this.dataComponentType);

            if (fishData != null)
            {
                type = this.createTooltip(fishData.value().name());
            }

            var customData = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);

            if (type != null)
            {
                if (!customData.isEmpty())
                {
                    var compoundTag = customData.copyTag();

                    if (compoundTag.getBooleanOr(ThievesFish.TROPHY_TAG, false))
                    {
                        type.append(", ").append(Component.translatable("entity.fishofthieves.trophy"));
                    }
                }
                consumer.accept(type);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void addFishVariantsBucket(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, Item item)
    {
        if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            if (item instanceof FOTMobBucketItem fotItem)
            {
                Comparator<Holder<? extends AbstractFishVariant>> comparator = Comparator.comparing(Holder::value, Comparator.comparingInt(AbstractFishVariant::customModelData));
                var registryKey = ResourceKey.<AbstractFishVariant>createRegistryKey(fotItem.getRegistryKey());
                itemDisplayParameters.holders().lookup(registryKey)
                        .ifPresent(lookup -> lookup.listElements()
                                .sorted(comparator)
                                .forEach(holder -> output.accept(create(item, (DataComponentType<Holder<? extends AbstractFishVariant>>) fotItem.getDataComponentType(), holder))));
            }
        }
        else
        {
            output.accept(item);
        }
    }

    public ResourceLocation getRegistryKey()
    {
        return this.registryKey;
    }

    public DataComponentType<?> getDataComponentType()
    {
        return this.dataComponentType;
    }

    private MutableComponent createTooltip(String location)
    {
        return Component.translatable("entity.fishofthieves.%s.%s".formatted(BuiltInRegistries.ENTITY_TYPE.getKey(this.entityType).getPath(), ResourceLocation.tryParse(location).getPath())).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY);
    }

    private static ItemStack create(Item item, DataComponentType<Holder<? extends AbstractFishVariant>> dataComponentType, Holder<? extends AbstractFishVariant> holder)
    {
        var itemStack = new ItemStack(item);
        itemStack.set(DataComponents.CUSTOM_MODEL_DATA, FOTItem.createCustomModelData(holder.value().customModelData()));
        itemStack.set(dataComponentType, holder);
        return itemStack;
    }
}