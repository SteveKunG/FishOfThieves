package com.stevekung.fishofthieves.client.item.properties;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.entity.variant.WreckerVariant;
import com.stevekung.fishofthieves.registry.FOTDataComponentTypes;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record WreckerVariantProperty() implements SelectItemModelProperty<ResourceKey<WreckerVariant>>
{
    public static final Codec<ResourceKey<WreckerVariant>> VALUE_CODEC = ResourceKey.codec(FOTRegistries.WRECKER_VARIANT);
    public static final Type<WreckerVariantProperty, ResourceKey<WreckerVariant>> TYPE = Type.create(MapCodec.unit(new WreckerVariantProperty()), VALUE_CODEC);

    @Override
    @Nullable
    public ResourceKey<WreckerVariant> get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed, ItemDisplayContext itemDisplayContext)
    {
        if (itemStack.has(FOTDataComponentTypes.WRECKER_VARIANT))
        {
            return itemStack.get(FOTDataComponentTypes.WRECKER_VARIANT).unwrapKey().orElse(null);
        }
        return null;
    }

    @Override
    public Type<WreckerVariantProperty, ResourceKey<WreckerVariant>> type()
    {
        return TYPE;
    }

    @Override
    public Codec<ResourceKey<WreckerVariant>> valueCodec()
    {
        return VALUE_CODEC;
    }
}