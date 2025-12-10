package com.stevekung.fishofthieves.client.item.properties;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.FOTDataComponentTypes;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record AncientscaleVariantProperty() implements SelectItemModelProperty<ResourceKey<AncientscaleVariant>>
{
    public static final Codec<ResourceKey<AncientscaleVariant>> VALUE_CODEC = ResourceKey.codec(FOTRegistries.ANCIENTSCALE_VARIANT);
    public static final Type<AncientscaleVariantProperty, ResourceKey<AncientscaleVariant>> TYPE = Type.create(MapCodec.unit(new AncientscaleVariantProperty()), VALUE_CODEC);

    @Override
    @Nullable
    public ResourceKey<AncientscaleVariant> get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed, ItemDisplayContext itemDisplayContext)
    {
        if (itemStack.has(FOTDataComponentTypes.ANCIENTSCALE_VARIANT))
        {
            return itemStack.get(FOTDataComponentTypes.ANCIENTSCALE_VARIANT).unwrapKey().orElse(null);
        }
        return null;
    }

    @Override
    public Type<AncientscaleVariantProperty, ResourceKey<AncientscaleVariant>> type()
    {
        return TYPE;
    }

    @Override
    public Codec<ResourceKey<AncientscaleVariant>> valueCodec()
    {
        return VALUE_CODEC;
    }
}