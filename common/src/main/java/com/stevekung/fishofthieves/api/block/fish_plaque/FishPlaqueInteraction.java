package com.stevekung.fishofthieves.api.block.fish_plaque;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record FishPlaqueInteraction(ResourceLocation entityType, Item item)
{
}