package com.stevekung.fishofthieves.client.item.properties;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.entity.variant.IslehopperVariant;
import com.stevekung.fishofthieves.registry.FOTDataComponentTypes;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record IslehopperVariantProperty() implements SelectItemModelProperty<ResourceKey<IslehopperVariant>>
{
    public static final Codec<ResourceKey<IslehopperVariant>> VALUE_CODEC = ResourceKey.codec(FOTRegistries.ISLEHOPPER_VARIANT);
    public static final Type<IslehopperVariantProperty, ResourceKey<IslehopperVariant>> TYPE = Type.create(MapCodec.unit(new IslehopperVariantProperty()), VALUE_CODEC);

    @Override
    @Nullable
    public ResourceKey<IslehopperVariant> get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed, ItemDisplayContext itemDisplayContext)
    {
        if (itemStack.has(FOTDataComponentTypes.ISLEHOPPER_VARIANT))
        {
            return itemStack.get(FOTDataComponentTypes.ISLEHOPPER_VARIANT).unwrapKey().orElse(null);
        }
        return null;
    }

    @Override
    public Type<IslehopperVariantProperty, ResourceKey<IslehopperVariant>> type()
    {
        return TYPE;
    }

    @Override
    public Codec<ResourceKey<IslehopperVariant>> valueCodec()
    {
        return VALUE_CODEC;
    }
}