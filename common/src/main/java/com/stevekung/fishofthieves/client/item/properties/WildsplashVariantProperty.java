package com.stevekung.fishofthieves.client.item.properties;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.entity.variant.WildsplashVariant;
import com.stevekung.fishofthieves.registry.FOTDataComponentTypes;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record WildsplashVariantProperty() implements SelectItemModelProperty<ResourceKey<WildsplashVariant>>
{
    public static final Codec<ResourceKey<WildsplashVariant>> VALUE_CODEC = ResourceKey.codec(FOTRegistries.WILDSPLASH_VARIANT);
    public static final Type<WildsplashVariantProperty, ResourceKey<WildsplashVariant>> TYPE = Type.create(MapCodec.unit(new WildsplashVariantProperty()), VALUE_CODEC);

    @Override
    @Nullable
    public ResourceKey<WildsplashVariant> get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed, ItemDisplayContext itemDisplayContext)
    {
        if (itemStack.has(FOTDataComponentTypes.WILDSPLASH_VARIANT))
        {
            return itemStack.get(FOTDataComponentTypes.WILDSPLASH_VARIANT).unwrapKey().orElse(null);
        }
        return null;
    }

    @Override
    public Type<WildsplashVariantProperty, ResourceKey<WildsplashVariant>> type()
    {
        return TYPE;
    }

    @Override
    public Codec<ResourceKey<WildsplashVariant>> valueCodec()
    {
        return VALUE_CODEC;
    }
}