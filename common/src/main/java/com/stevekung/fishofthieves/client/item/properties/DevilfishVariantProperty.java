package com.stevekung.fishofthieves.client.item.properties;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.entity.variant.DevilfishVariant;
import com.stevekung.fishofthieves.registry.FOTDataComponentTypes;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record DevilfishVariantProperty() implements SelectItemModelProperty<ResourceKey<DevilfishVariant>>
{
    public static final Codec<ResourceKey<DevilfishVariant>> VALUE_CODEC = ResourceKey.codec(FOTRegistries.DEVILFISH_VARIANT);
    public static final Type<DevilfishVariantProperty, ResourceKey<DevilfishVariant>> TYPE = Type.create(MapCodec.unit(new DevilfishVariantProperty()), VALUE_CODEC);

    @Override
    @Nullable
    public ResourceKey<DevilfishVariant> get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed, ItemDisplayContext itemDisplayContext)
    {
        if (itemStack.has(FOTDataComponentTypes.DEVILFISH_VARIANT))
        {
            return itemStack.get(FOTDataComponentTypes.DEVILFISH_VARIANT).unwrapKey().orElse(null);
        }
        return null;
    }

    @Override
    public Type<DevilfishVariantProperty, ResourceKey<DevilfishVariant>> type()
    {
        return TYPE;
    }

    @Override
    public Codec<ResourceKey<DevilfishVariant>> valueCodec()
    {
        return VALUE_CODEC;
    }
}