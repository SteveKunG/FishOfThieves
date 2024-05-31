package com.stevekung.fishofthieves.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;

public class FOTSpawnConditions
{
    private static final Codec<SpawnCondition> TYPED_CODEC = FOTBuiltInRegistries.SPAWN_CONDITION_TYPE.byNameCodec().dispatch("condition", SpawnCondition::getType, SpawnConditionType::codec);
    public static final Codec<SpawnCondition> DIRECT_CODEC = Codec.lazyInitialized(() -> Codec.withAlternative(TYPED_CODEC, AllOfCondition.INLINE_CODEC));
    public static final Codec<Holder<SpawnCondition>> CODEC = RegistryFileCodec.create(FOTRegistries.SPAWN_CONDITION, DIRECT_CODEC);

    public static final SpawnConditionType INVERTED = register("inverted", InvertedSpawnCondition.CODEC);
    public static final SpawnConditionType ANY_OF = register("any_of", AnyOfCondition.CODEC);
    public static final SpawnConditionType ALL_OF = register("all_of", AllOfCondition.CODEC);

    public static final SpawnConditionType IS_DAY = register("is_day", DayCondition.CODEC);
    public static final SpawnConditionType IS_NIGHT = register("is_night", NightCondition.CODEC);
    public static final SpawnConditionType IS_RAINING = register("is_raining", RainingCondition.CODEC);
    public static final SpawnConditionType SEE_SKY_IN_WATER = register("see_sky_in_water", SeeSkyInWaterCondition.CODEC);
    public static final SpawnConditionType CONTINENTALNESS = register("continentalness", ContinentalnessCondition.CODEC);
    public static final SpawnConditionType PROBABILITY = register("probability", ProbabilityCondition.CODEC);
    public static final SpawnConditionType MATCH_BIOME = register("match_biome", MatchBiomeCondition.CODEC);
    public static final SpawnConditionType MATCH_STRUCTURE = register("match_structure", MatchStructureCondition.CODEC);
    public static final SpawnConditionType MATCH_BLOCKS_IN_RANGE = register("match_blocks_in_range", MatchBlocksInRangeCondition.CODEC);
    public static final SpawnConditionType MATCH_MINIMUM_BLOCKS_IN_RANGE = register("match_minimum_blocks_in_range", MatchMinimumBlocksInRangeCondition.CODEC);
    public static final SpawnConditionType HAS_BEEHIVE = register("has_beehive", HasBeehiveCondition.CODEC);
    public static final SpawnConditionType HEIGHT = register("height", HeightCondition.CODEC);
    public static final SpawnConditionType RANDOM_CHANCE = register("random_chance", RandomChanceCondition.CODEC);
    public static final SpawnConditionType MOON_BRIGHTNESS = register("moon_brightness", MoonBrightnessCondition.CODEC);
    public static final SpawnConditionType TIME_OF_DAY = register("time_of_day", TimeOfDayCondition.CODEC);
    public static final SpawnConditionType SKY_BRIGHTNESS = register("sky_brightness", SkyBrightnessCondition.CODEC);
    public static final SpawnConditionType SKY_DARKEN = register("sky_darken", SkyDarkenCondition.CODEC);

    private static SpawnConditionType register(String name, MapCodec<? extends SpawnCondition> codec)
    {
        return Registry.register(FOTBuiltInRegistries.SPAWN_CONDITION_TYPE, FishOfThieves.id(name), new SpawnConditionType(codec));
    }
}