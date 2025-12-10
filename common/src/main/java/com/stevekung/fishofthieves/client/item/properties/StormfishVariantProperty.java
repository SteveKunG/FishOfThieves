package com.stevekung.fishofthieves.client.item.properties;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.entity.variant.StormfishVariant;
import com.stevekung.fishofthieves.registry.FOTDataComponentTypes;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record StormfishVariantProperty() implements SelectItemModelProperty<ResourceKey<StormfishVariant>>
{
    public static final Codec<ResourceKey<StormfishVariant>> VALUE_CODEC = ResourceKey.codec(FOTRegistries.STORMFISH_VARIANT);
    public static final Type<StormfishVariantProperty, ResourceKey<StormfishVariant>> TYPE = Type.create(MapCodec.unit(new StormfishVariantProperty()), VALUE_CODEC);

    @Override
    @Nullable
    public ResourceKey<StormfishVariant> get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed, ItemDisplayContext itemDisplayContext)
    {
        if (itemStack.has(FOTDataComponentTypes.STORMFISH_VARIANT))
        {
            return itemStack.get(FOTDataComponentTypes.STORMFISH_VARIANT).unwrapKey().orElse(null);
        }
        return null;
    }

    @Override
    public Type<StormfishVariantProperty, ResourceKey<StormfishVariant>> type()
    {
        return TYPE;
    }

    @Override
    public Codec<ResourceKey<StormfishVariant>> valueCodec()
    {
        return VALUE_CODEC;
    }
}