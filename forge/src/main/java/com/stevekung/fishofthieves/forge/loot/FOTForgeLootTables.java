package com.stevekung.fishofthieves.forge.loot;

import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;
import com.stevekung.fishofthieves.common.FishOfThieves;
import net.minecraft.resources.ResourceLocation;

public class FOTForgeLootTables
{
    private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
    private static final Set<ResourceLocation> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);

    public interface Entity
    {
        ResourceLocation POLAR_BEAR = register("entities/polar_bear");
    }

    public interface Chest
    {
        ResourceLocation VILLAGE_FISHER = register("chests/village/village_fisher");
        ResourceLocation BURIED_TREASURE = register("chests/buried_treasure");
    }

    public interface Archaeology
    {
        ResourceLocation OCEAN_RUINS = register("archaeology/ocean_ruins");
    }

    public interface Gift
    {
        ResourceLocation FISHERMAN_GIFT = register("gameplay/hero_of_the_village/fisherman_gift");
    }

    public interface Fishing
    {
        ResourceLocation FISHING_FISH = register("gameplay/fishing/fish");
    }

    public static Set<ResourceLocation> all()
    {
        return IMMUTABLE_LOCATIONS;
    }

    private static ResourceLocation register(String id)
    {
        return register(FishOfThieves.id(id));
    }

    private static ResourceLocation register(ResourceLocation id)
    {
        if (LOCATIONS.add(id))
        {
            return id;
        }
        else
        {
            throw new IllegalArgumentException(id + " is already a registered loot table");
        }
    }
}