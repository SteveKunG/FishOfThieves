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
    MapCodec<? extends SpawnCondition> ALL_OF = register("all_of", AllConditionCheck.CODEC);
    MapCodec<? extends SpawnCondition> ANY_OF = register("any_of", AnyConditionCheck.CODEC);
    MapCodec<? extends SpawnCondition> INVERTED = register("inverted", InvertedCondition.CODEC);

    MapCodec<? extends SpawnCondition> IS_DAY = register("is_day", DayCheck.CODEC);
    MapCodec<? extends SpawnCondition> IS_NIGHT = register("is_night", NightCheck.CODEC);
    MapCodec<? extends SpawnCondition> IS_RAINING = register("is_raining", RainingCheck.CODEC);
    MapCodec<? extends SpawnCondition> NEVER = register("never", NeverCheck.CODEC);
    MapCodec<? extends SpawnCondition> SEE_SKY = register("see_sky", SeeSkyCheck.CODEC);
    MapCodec<? extends SpawnCondition> CONTINENTALNESS = register("continentalness", ContinentalnessCheck.CODEC);
    MapCodec<? extends SpawnCondition> PROBABILITY = register("probability", ProbabilityCheck.CODEC);
    MapCodec<? extends SpawnCondition> BLOCK_IN_RANGE = register("block_range", BlockRangeCheck.CODEC);
    MapCodec<? extends SpawnCondition> MINIMUM_BLOCK_IN_RANGE = register("minimum_block_range", MinimumBlockRangeCheck.CODEC);
    MapCodec<? extends SpawnCondition> HAS_BEEHIVE = register("has_beehive", HasBeehiveCheck.CODEC);
    MapCodec<? extends SpawnCondition> HEIGHT = register("height", HeightCheck.CODEC);
    MapCodec<? extends SpawnCondition> RANDOM_CHANCE = register("random_chance", RandomChanceCheck.CODEC);
    MapCodec<? extends SpawnCondition> TIME_OF_DAY = register("time_of_day", TimeOfDayCheck.CODEC);
    MapCodec<? extends SpawnCondition> SKY_BRIGHTNESS = register("sky_brightness", SkyBrightnessCheck.CODEC);
    MapCodec<? extends SpawnCondition> SKY_DARKEN = register("sky_darken", SkyDarkenCheck.CODEC);
    MapCodec<? extends SpawnCondition> LIVING_ENTITY_HAS_EFFECT = register("living_entity_has_effect", LivingEntityHasEffectCondition.CODEC);

    static void init()
    {
        FishOfThieves.LOGGER.info("Registering Spawn Condition");
    }

    private static MapCodec<? extends SpawnCondition> register(String name, MapCodec<? extends SpawnCondition> codec)
    {
        return Registry.register(BuiltInRegistries.SPAWN_CONDITION_TYPE, FishOfThieves.id(name), codec);
    }
}