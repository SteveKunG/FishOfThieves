package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class FOTTags
{
    public static final Tag.Named<Item> THIEVES_FISH_BUCKET = FOTPlatform.createItemTag("thieves_fish_bucket");

    public static final Tag.Named<EntityType<?>> THIEVES_FISH = FOTPlatform.createEntityTypeTag("thieves_fish");
}