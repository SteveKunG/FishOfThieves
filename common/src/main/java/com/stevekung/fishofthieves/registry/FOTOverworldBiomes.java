package com.stevekung.fishofthieves.registry;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FOTOverworldBiomes extends OverworldBiomes
{
    public static Biome tropicalIsland(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers)
    {
        var mobSpawnBuilder = new MobSpawnSettings.Builder();
        var biomeSettingsBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);

        islandSpawns(mobSpawnBuilder);

        OverworldBiomes.globalOverworldGeneration(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeSettingsBuilder);
        BiomeDefaultFeatures.addJungleGrass(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeSettingsBuilder);

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE);

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_WARM);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE);
        BiomeDefaultFeatures.addLukeWarmKelp(biomeSettingsBuilder);

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.PATCH_MELON_TROPICAL);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.TREES_TROPICAL_ISLAND);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.TREES_COCONUT_TROPICAL_ISLAND);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.TROPICAL_FLOWER);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.PATCH_TROPICAL_BUSH);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.PATCH_WILD_PINEAPPLE);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.PATCH_WILD_POMEGRANATE);

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FOTPlacements.TROPICAL_ISLAND_ROCK);

        return biome(true, 0.9F, 1.0F, 54489, 38295, null, 1495563, mobSpawnBuilder, biomeSettingsBuilder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SPARSE_JUNGLE));
    }

    private static Biome biome(boolean hasPrecipitation, float temperature, float downfall, int waterColor, int waterFogColor, @Nullable Integer grassColorOverride, @Nullable Integer foliageColorOverride, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music backgroundMusic)
    {
        var builder = new BiomeSpecialEffects.Builder().grassColorModifier(FOTGrassColorModifier.TROPICAL_ISLAND).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(10409707).skyColor(4568554).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(backgroundMusic);

        if (grassColorOverride != null)
        {
            builder.grassColorOverride(grassColorOverride);
        }
        if (foliageColorOverride != null)
        {
            builder.foliageColorOverride(foliageColorOverride);
        }

        return new Biome.BiomeBuilder().hasPrecipitation(hasPrecipitation).temperature(temperature).downfall(downfall).specialEffects(builder.build()).mobSpawnSettings(mobSpawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    public static void islandSpawns(MobSpawnSettings.Builder builder)
    {
        builder.addSpawn(MobCategory.CREATURE, 40, new MobSpawnSettings.SpawnerData(EntityType.PIG, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, 40, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, 30, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, 20, new MobSpawnSettings.SpawnerData(EntityType.PARROT, 1, 2));
        builder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 1, 3));
        BiomeDefaultFeatures.commonSpawns(builder);
    }
}