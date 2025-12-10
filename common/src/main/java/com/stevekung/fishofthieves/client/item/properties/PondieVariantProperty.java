package com.stevekung.fishofthieves.client.item.properties;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.entity.variant.PondieVariant;
import com.stevekung.fishofthieves.registry.FOTDataComponentTypes;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record PondieVariantProperty() implements SelectItemModelProperty<ResourceKey<PondieVariant>>
{
    public static final Codec<ResourceKey<PondieVariant>> VALUE_CODEC = ResourceKey.codec(FOTRegistries.PONDIE_VARIANT);
    public static final Type<PondieVariantProperty, ResourceKey<PondieVariant>> TYPE = Type.create(MapCodec.unit(new PondieVariantProperty()), VALUE_CODEC);

    @Override
    @Nullable
    public ResourceKey<PondieVariant> get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed, ItemDisplayContext itemDisplayContext)
    {
        if (itemStack.has(FOTDataComponentTypes.PONDIE_VARIANT))
        {
            return itemStack.get(FOTDataComponentTypes.PONDIE_VARIANT).unwrapKey().orElse(null);
        }
        return null;
    }

    @Override
    public Type<PondieVariantProperty, ResourceKey<PondieVariant>> type()
    {
        return TYPE;
    }

    @Override
    public Codec<ResourceKey<PondieVariant>> valueCodec()
    {
        return VALUE_CODEC;
    }
}