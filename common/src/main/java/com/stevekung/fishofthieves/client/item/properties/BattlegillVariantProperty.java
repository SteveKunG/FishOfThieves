package com.stevekung.fishofthieves.client.item.properties;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.entity.variant.BattlegillVariant;
import com.stevekung.fishofthieves.registry.FOTDataComponentTypes;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record BattlegillVariantProperty() implements SelectItemModelProperty<ResourceKey<BattlegillVariant>>
{
    public static final Codec<ResourceKey<BattlegillVariant>> VALUE_CODEC = ResourceKey.codec(FOTRegistries.BATTLEGILL_VARIANT);
    public static final Type<BattlegillVariantProperty, ResourceKey<BattlegillVariant>> TYPE = Type.create(MapCodec.unit(new BattlegillVariantProperty()), VALUE_CODEC);

    @Override
    @Nullable
    public ResourceKey<BattlegillVariant> get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed, ItemDisplayContext itemDisplayContext)
    {
        if (itemStack.has(FOTDataComponentTypes.BATTLEGILL_VARIANT))
        {
            return itemStack.get(FOTDataComponentTypes.BATTLEGILL_VARIANT).unwrapKey().orElse(null);
        }
        return null;
    }

    @Override
    public Type<BattlegillVariantProperty, ResourceKey<BattlegillVariant>> type()
    {
        return TYPE;
    }

    @Override
    public Codec<ResourceKey<BattlegillVariant>> valueCodec()
    {
        return VALUE_CODEC;
    }
}