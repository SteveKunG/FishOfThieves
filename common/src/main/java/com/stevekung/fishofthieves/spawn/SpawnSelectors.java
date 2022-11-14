package com.stevekung.fishofthieves.spawn;

import java.util.Collections;
import java.util.EnumSet;
import java.util.function.Predicate;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

public final class SpawnSelectors
{
    public static Predicate<SpawnConditionContext> always()
    {
        return context -> true;
    }

    public static Predicate<SpawnConditionContext> dayAndSeeSky()
    {
        return context -> context.isDay() && context.seeSkyInWater();
    }

    public static Predicate<SpawnConditionContext> nightAndSeeSky()
    {
        return context -> context.isNight() && context.seeSkyInWater();
    }

    public static Predicate<SpawnConditionContext> rainingAndSeeSky()
    {
        return context -> context.isRaining() && context.seeSkyInWater();
    }

    public static Predicate<SpawnConditionContext> thunderingAndSeeSky()
    {
        return context -> context.isRaining() && context.isThundering() && context.seeSkyInWater();
    }

    public static Predicate<SpawnConditionContext> probability(float probability)
    {
        return context -> context.random().nextFloat() < probability;
    }

    public static Predicate<SpawnConditionContext> simpleSpawn(Predicate<SpawnConditionContext> complex)
    {
        return simpleSpawn(false, complex);
    }

    public static Predicate<SpawnConditionContext> simpleSpawn(boolean nightTime, Predicate<SpawnConditionContext> complex)
    {
        return FishOfThieves.CONFIG.general.simpleSpawningCondition ? nightTime ? SpawnSelectors.nightAndSeeSky() : SpawnSelectors.always() : complex;
    }

    public static Predicate<SpawnConditionContext> simpleSpawn(float probability, Predicate<SpawnConditionContext> complex)
    {
        return FishOfThieves.CONFIG.general.simpleSpawningCondition ? probability(probability) : complex;
    }

    public static Predicate<SpawnConditionContext> structureTag(TagKey<Structure> tagKey)
    {
        return context -> TerrainUtils.isInFeature(context.level(), context.blockPos(), tagKey);
    }

    public static Predicate<SpawnConditionContext> biomeTag(TagKey<Biome> tagKey)
    {
        return context -> context.biomeTag().is(tagKey);
    }

    public static Predicate<SpawnConditionContext> continentalness(Continentalness... continentalnesses)
    {
        var continentalnessSet = EnumSet.noneOf(Continentalness.class);
        Collections.addAll(continentalnessSet, continentalnesses);
        return context -> continentalnessSet.stream().anyMatch(continentalness -> context.continentalness() == continentalness);
    }

    public static SpawnConditionContext get(LivingEntity livingEntity)
    {
        var level = (ServerLevel) livingEntity.level;
        var blockPos = livingEntity.blockPosition();
        return new SpawnConditionContext(level, blockPos, livingEntity.getRandom(), level.isDay(), level.isNight(), level.isRaining(), level.isThundering(), level.canSeeSkyFromBelowWater(blockPos), level.getBiome(blockPos), TerrainUtils.getContinentalness(level, blockPos));
    }
}