package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class FOTTags
{
    public static final Tag.Named<Item> THIEVES_FISH_BUCKET = TagFactory.ITEM.create(new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish_bucket"));

    public static final Tag.Named<EntityType<?>> THIEVES_FISH = TagFactory.ENTITY_TYPE.create(new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish"));
}