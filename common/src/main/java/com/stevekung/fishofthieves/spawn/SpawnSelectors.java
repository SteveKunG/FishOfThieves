package com.stevekung.fishofthieves.spawn;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Predicate;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

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
        return context -> context.isThundering() && context.seeSkyInWater();
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

    @SafeVarargs
    public static Predicate<SpawnConditionContext> features(ResourceKey<ConfiguredStructureFeature<?, ?>>... structureFeatures)
    {
        return context ->
        {
            for (var structureFeature : structureFeatures)
            {
                if (TerrainUtils.isInFeature(context.level(), context.blockPos(), structureFeature))
                {
                    return true;
                }
            }
            return false;
        };
    }

    @SafeVarargs
    public static Predicate<SpawnConditionContext> tags(TagKey<Biome>... keys)
    {
        return context ->
        {
            for (var key : keys)
            {
                return context.level().getBiome(context.blockPos()).is(key);
            }
            return false;
        };
    }

    public static Predicate<SpawnConditionContext> categories(Biome.BiomeCategory... categories)
    {
        Set<Biome.BiomeCategory> categorySet = EnumSet.noneOf(Biome.BiomeCategory.class);
        Collections.addAll(categorySet, categories);
        return context -> categorySet.stream().anyMatch(category -> context.biomeCategory() == category);
    }

    public static Predicate<SpawnConditionContext> continentalness(Continentalness... continentalnesses)
    {
        Set<Continentalness> continentalnessSet = EnumSet.noneOf(Continentalness.class);
        Collections.addAll(continentalnessSet, continentalnesses);
        return context -> continentalnessSet.stream().anyMatch(continentalness -> context.continentalness() == continentalness);
    }

    public static SpawnConditionContext get(LivingEntity livingEntity)
    {
        var level = (ServerLevel) livingEntity.level;
        var blockPos = livingEntity.blockPosition();
        return new SpawnConditionContext(level, blockPos, livingEntity.getRandom(), level.isDay(), level.isNight(), level.isRaining(), level.isThundering(), level.canSeeSkyFromBelowWater(blockPos), TerrainUtils.getBiomeCategory(level, blockPos), TerrainUtils.getContinentalness(level, blockPos));
    }
}