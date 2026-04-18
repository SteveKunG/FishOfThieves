package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class FOTItemIds
{
    public static final ResourceKey<Item> STORMFISH_POTTERY_SHERD = create("stormfish_pottery_sherd");
    public static final ResourceKey<Item> KRAKEN_POTTERY_SHERD = create("kraken_pottery_sherd");
    public static final ResourceKey<Item> MEGALODON_POTTERY_SHERD = create("megalodon_pottery_sherd");
    public static final ResourceKey<Item> GREAT_MOUTH_POTTERY_SHERD = create("great_mouth_pottery_sherd");

    private static ResourceKey<Item> create(String name)
    {
        return ResourceKey.create(Registries.ITEM, FishOfThieves.id(name));
    }
}