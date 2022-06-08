package com.stevekung.fishofthieves.spawn;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Predicate;

import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.resources.ResourceKey;
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
        return context -> context.isThundering() && context.seeSkyInWater();
    }

    public static Predicate<SpawnConditionContext> probability(float probability)
    {
        return context -> context.random().nextFloat() < probability;
    }

    @SafeVarargs
    public static Predicate<SpawnConditionContext> features(TagKey<Structure>... structures)
    {
        return context ->
        {
            for (TagKey<Structure> structure : structures)
            {
                return TerrainUtils.isInFeature(context.level(), context.blockPos(), structure);
            }
            return false;
        };
    }

    @SafeVarargs
    public static Predicate<SpawnConditionContext> includeByKey(ResourceKey<Biome>... keys)
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

    @SafeVarargs
    public static Predicate<SpawnConditionContext> biomes(TagKey<Biome>... biomes)
    {
        return context ->
        {
            for (TagKey<Biome> biome : biomes)
            {
                return context.biomeTag().is(biome);
            }
            return false;
        };
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
        return new SpawnConditionContext(level, blockPos, livingEntity.getRandom(), level.isDay(), level.isNight(), level.isRaining(), level.isThundering(), level.canSeeSkyFromBelowWater(blockPos), level.getBiome(blockPos), TerrainUtils.getContinentalness(level, blockPos));
    }
}