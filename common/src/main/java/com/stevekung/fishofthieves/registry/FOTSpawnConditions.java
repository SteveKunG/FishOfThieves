package com.stevekung.fishofthieves.registry;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.variant.SpawnCondition;

@SuppressWarnings("unused")
public interface FOTSpawnConditions
{
    MapCodec<? extends SpawnCondition> ALL_OF = register("all_of", AllOfCondition.CODEC);
    MapCodec<? extends SpawnCondition> ANY_OF = register("any_of", AnyOfCondition.CODEC);
    MapCodec<? extends SpawnCondition> INVERTED = register("inverted", InvertedSpawnCondition.CODEC);

    MapCodec<? extends SpawnCondition> IS_DAY = register("is_day", DayCondition.CODEC);
    MapCodec<? extends SpawnCondition> IS_NIGHT = register("is_night", NightCondition.CODEC);
    MapCodec<? extends SpawnCondition> IS_RAINING = register("is_raining", RainingCondition.CODEC);
    MapCodec<? extends SpawnCondition> SEE_SKY = register("see_sky", SeeSkyCondition.CODEC);
    MapCodec<? extends SpawnCondition> CONTINENTALNESS = register("continentalness", ContinentalnessCondition.CODEC);
    MapCodec<? extends SpawnCondition> PROBABILITY = register("probability", ProbabilityCondition.CODEC);
    MapCodec<? extends SpawnCondition> MATCH_BLOCKS_IN_RANGE = register("match_blocks_in_range", MatchBlocksInRangeCondition.CODEC);
    MapCodec<? extends SpawnCondition> MATCH_MINIMUM_BLOCKS_IN_RANGE = register("match_minimum_blocks_in_range", MatchMinimumBlocksInRangeCondition.CODEC);
    MapCodec<? extends SpawnCondition> HAS_BEEHIVE = register("has_beehive", HasBeehiveCondition.CODEC);
    MapCodec<? extends SpawnCondition> HEIGHT = register("height", HeightCondition.CODEC);
    MapCodec<? extends SpawnCondition> RANDOM_CHANCE = register("random_chance", RandomChanceCondition.CODEC);
    MapCodec<? extends SpawnCondition> TIME_OF_DAY = register("time_of_day", TimeOfDayCondition.CODEC);
    MapCodec<? extends SpawnCondition> SKY_BRIGHTNESS = register("sky_brightness", SkyBrightnessCondition.CODEC);
    MapCodec<? extends SpawnCondition> SKY_DARKEN = register("sky_darken", SkyDarkenCondition.CODEC);

    static void init()
    {
        FishOfThieves.LOGGER.info("Registering Spawn Condition");
    }

    private static MapCodec<? extends SpawnCondition> register(String name, MapCodec<? extends SpawnCondition> codec)
    {
        return Registry.register(BuiltInRegistries.SPAWN_CONDITION_TYPE, FishOfThieves.id(name), codec);
    }
}