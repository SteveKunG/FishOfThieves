package com.stevekung.fishofthieves.client.item.properties;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.entity.variant.SplashtailVariant;
import com.stevekung.fishofthieves.registry.FOTDataComponentTypes;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record SplashtailVariantProperty() implements SelectItemModelProperty<ResourceKey<SplashtailVariant>>
{
    public static final Codec<ResourceKey<SplashtailVariant>> VALUE_CODEC = ResourceKey.codec(FOTRegistries.SPLASHTAIL_VARIANT);
    public static final SelectItemModelProperty.Type<SplashtailVariantProperty, ResourceKey<SplashtailVariant>> TYPE = SelectItemModelProperty.Type.create(MapCodec.unit(new SplashtailVariantProperty()), VALUE_CODEC);

    @Override
    @Nullable
    public ResourceKey<SplashtailVariant> get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed, ItemDisplayContext itemDisplayContext)
    {
        if (itemStack.has(FOTDataComponentTypes.SPLASHTAIL_VARIANT))
        {
            return itemStack.get(FOTDataComponentTypes.SPLASHTAIL_VARIANT).unwrapKey().orElse(null);
        }
        return null;
    }

    @Override
    public SelectItemModelProperty.Type<SplashtailVariantProperty, ResourceKey<SplashtailVariant>> type()
    {
        return TYPE;
    }

    @Override
    public Codec<ResourceKey<SplashtailVariant>> valueCodec()
    {
        return VALUE_CODEC;
    }
}