package com.stevekung.fishofthieves.forge.loot;

import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;
import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class FOTForgeLootTables
{
    private static final Set<ResourceKey<LootTable>> LOCATIONS = Sets.newHashSet();
    private static final Set<ResourceKey<LootTable>> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);

    public interface Entity
    {
        ResourceKey<LootTable> POLAR_BEAR = register("entities/polar_bear");
    }

    public interface Chest
    {
        ResourceKey<LootTable> VILLAGE_FISHER = register("chests/village/village_fisher");
        ResourceKey<LootTable> BURIED_TREASURE = register("chests/buried_treasure");
    }

    public interface Archaeology
    {
        ResourceKey<LootTable> OCEAN_RUINS = register("archaeology/ocean_ruins");
    }

    public interface Gift
    {
        ResourceKey<LootTable> FISHERMAN_GIFT = register("gameplay/hero_of_the_village/fisherman_gift");
    }

    public interface Fishing
    {
        ResourceKey<LootTable> FISHING_FISH = register("gameplay/fishing/fish");
    }

    public static Set<ResourceKey<LootTable>> all()
    {
        return IMMUTABLE_LOCATIONS;
    }

    private static ResourceKey<LootTable> register(String id)
    {
        return register(ResourceKey.create(Registries.LOOT_TABLE, FishOfThieves.id(id)));
    }

    private static ResourceKey<LootTable> register(ResourceKey<LootTable> id)
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