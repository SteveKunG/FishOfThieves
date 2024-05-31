package com.stevekung.fishofthieves.item;

import java.util.Comparator;
import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.material.Fluid;

public class FOTMobBucketItem extends MobBucketItem
{
    private final EntityType<?> entityType;
    private final ResourceLocation registryKey;

    public FOTMobBucketItem(EntityType<?> entityType, Fluid fluid, SoundEvent soundEvent, ResourceKey<?> registryKey, Item.Properties properties)
    {
        super(entityType, fluid, soundEvent, properties);
        this.entityType = entityType;
        this.registryKey = registryKey.location();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (this.entityType.is(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE))
        {
            var customData = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);

            if (!customData.isEmpty())
            {
                var compoundTag = customData.copyTag();

                if (compoundTag.contains(ThievesFish.VARIANT_TAG, Tag.TAG_STRING))
                {
                    var type = this.createTooltip(compoundTag.getString(ThievesFish.VARIANT_TAG));

                    if (compoundTag.getBoolean(ThievesFish.TROPHY_TAG))
                    {
                        type.append(", ").append(Component.translatable("entity.fishofthieves.trophy"));
                    }
                    tooltipComponents.add(type);
                }
            }
        }
    }

    public static void addFishVariantsBucket(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, Item item)
    {
        if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            if (item instanceof FOTMobBucketItem fotItem)
            {
                Comparator<Holder<? extends AbstractFishVariant>> comparator = Comparator.comparing(Holder::value, Comparator.comparingInt(AbstractFishVariant::customModelData));
                var registryKey = ResourceKey.<AbstractFishVariant>createRegistryKey(fotItem.getRegistryKey());
                itemDisplayParameters.holders().lookup(registryKey).ifPresent(lookup -> lookup.listElements().sorted(comparator).forEach(holder -> output.accept(create(item, holder))));
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

    private MutableComponent createTooltip(String location)
    {
        return Component.translatable("entity.fishofthieves.%s.%s".formatted(BuiltInRegistries.ENTITY_TYPE.getKey(this.entityType).getPath(), ResourceLocation.tryParse(location).getPath())).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY);
    }

    private static ItemStack create(Item item, Holder.Reference<AbstractFishVariant> holder)
    {
        var itemStack = new ItemStack(item);
        itemStack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(holder.value().customModelData()));
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> compoundTag.putString(ThievesFish.VARIANT_TAG, FishOfThieves.id(holder.value().name()).toString()));
        return itemStack;
    }
}