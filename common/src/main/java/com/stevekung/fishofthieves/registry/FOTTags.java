package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.core.FishOfThieves;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class FOTTags
{
    public static final TagKey<Item> THIEVES_FISH_BUCKET = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish_bucket"));

    public static final TagKey<EntityType<?>> THIEVES_FISH = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish"));
}