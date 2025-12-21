package com.stevekung.fishofthieves.client.item.properties;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.entity.variant.PlentifinVariant;
import com.stevekung.fishofthieves.registry.FOTDataComponentTypes;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record PlentifinVariantProperty() implements SelectItemModelProperty<ResourceKey<PlentifinVariant>>
{
    public static final Codec<ResourceKey<PlentifinVariant>> VALUE_CODEC = ResourceKey.codec(FOTRegistries.PLENTIFIN_VARIANT);
    public static final Type<PlentifinVariantProperty, ResourceKey<PlentifinVariant>> TYPE = Type.create(MapCodec.unit(new PlentifinVariantProperty()), VALUE_CODEC);

    @Override
    @Nullable
    public ResourceKey<PlentifinVariant> get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed, ItemDisplayContext itemDisplayContext)
    {
        if (itemStack.has(FOTDataComponentTypes.PLENTIFIN_VARIANT))
        {
            return itemStack.get(FOTDataComponentTypes.PLENTIFIN_VARIANT).unwrapKey().orElse(null);
        }
        return null;
    }

    @Override
    public Type<PlentifinVariantProperty, ResourceKey<PlentifinVariant>> type()
    {
        return TYPE;
    }

    @Override
    public Codec<ResourceKey<PlentifinVariant>> valueCodec()
    {
        return VALUE_CODEC;
    }
}