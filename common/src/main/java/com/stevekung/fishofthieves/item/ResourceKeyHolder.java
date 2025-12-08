package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface ResourceKeyHolder
{
    ResourceKey<? extends Registry<? extends AbstractFishVariant>> getResourceKey();
}