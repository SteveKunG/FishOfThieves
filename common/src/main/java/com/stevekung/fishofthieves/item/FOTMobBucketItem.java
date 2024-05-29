package com.stevekung.fishofthieves.item;

import java.util.List;

import com.google.common.collect.BiMap;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
    private final BiMap<String, Integer> variantToCustomModelData;

    public FOTMobBucketItem(EntityType<?> entityType, Fluid fluid, SoundEvent soundEvent, BiMap<String, Integer> variantToCustomModelData, Item.Properties properties)
    {
        super(entityType, fluid, soundEvent, properties);
        this.entityType = entityType;
        this.variantToCustomModelData = variantToCustomModelData;
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
            else
            {
                if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
                {
                    tooltipComponents.add(this.createTooltip(this.variantToCustomModelData.keySet().stream().findFirst().get()));
                }
            }
        }
    }

    public static void addFishVariantsBucket(CreativeModeTab.Output output, Item item)
    {
        output.accept(item);

        if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            for (var i = 1; i < 5; i++)
            {
                output.accept(create(item, ((FOTMobBucketItem) item).variantToCustomModelData, i));
            }
        }
    }

    private MutableComponent createTooltip(String location)
    {
        return Component.translatable("entity.fishofthieves.%s.%s".formatted(BuiltInRegistries.ENTITY_TYPE.getKey(this.entityType).getPath(), ResourceLocation.tryParse(location).getPath())).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY);
    }

    private static ItemStack create(Item item, BiMap<String, Integer> variantToCustomModelData, int index)
    {
        var itemStack = new ItemStack(item);
        itemStack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(index));
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> compoundTag.putString(ThievesFish.VARIANT_TAG, variantToCustomModelData.inverse().get(index)));
        return itemStack;
    }
}