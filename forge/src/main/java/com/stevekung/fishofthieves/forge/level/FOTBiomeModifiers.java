package com.stevekung.fishofthieves.forge.level;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTFeatures;
import com.stevekung.fishofthieves.registry.FOTPlacements;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class FOTBiomeModifiers
{
    //@formatter:off
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, FOTFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, FOTPlacements::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, context ->
            {
                context.register(key("add_splashtails"), spawn(context, FOTTags.Biomes.SPAWNS_SPLASHTAILS, new MobSpawnSettings.SpawnerData(FOTEntities.SPLASHTAIL, FishOfThieves.CONFIG.spawnRate.fishWeight.splashtail, 4, 8)));
                context.register(key("add_pondies"), spawn(context, FOTTags.Biomes.SPAWNS_PONDIES, new MobSpawnSettings.SpawnerData(FOTEntities.PONDIE, FishOfThieves.CONFIG.spawnRate.fishWeight.pondie, 2, 4)));
                context.register(key("add_islehoppers"), spawn(context, FOTTags.Biomes.SPAWNS_ISLEHOPPERS, new MobSpawnSettings.SpawnerData(FOTEntities.ISLEHOPPER, FishOfThieves.CONFIG.spawnRate.fishWeight.islehopper, 2, 4)));
                context.register(key("add_ancientscales"), spawn(context, FOTTags.Biomes.SPAWNS_ANCIENTSCALES, new MobSpawnSettings.SpawnerData(FOTEntities.ANCIENTSCALE, FishOfThieves.CONFIG.spawnRate.fishWeight.ancientscale, 4, 8)));
                context.register(key("add_plentifins"), spawn(context, FOTTags.Biomes.SPAWNS_PLENTIFINS, new MobSpawnSettings.SpawnerData(FOTEntities.PLENTIFIN, FishOfThieves.CONFIG.spawnRate.fishWeight.plentifin, 4, 8)));
                context.register(key("add_wildsplash"), spawn(context, FOTTags.Biomes.SPAWNS_WILDSPLASH, new MobSpawnSettings.SpawnerData(FOTEntities.WILDSPLASH, FishOfThieves.CONFIG.spawnRate.fishWeight.wildsplash, 2, 4)));
                context.register(key("add_devilfish"), spawn(context, FOTTags.Biomes.SPAWNS_DEVILFISH, new MobSpawnSettings.SpawnerData(FOTEntities.DEVILFISH, FishOfThieves.CONFIG.spawnRate.fishWeight.devilfish, 1, 2)));
                context.register(key("add_battlegills"), spawn(context, FOTTags.Biomes.SPAWNS_BATTLEGILLS, new MobSpawnSettings.SpawnerData(FOTEntities.BATTLEGILL, FishOfThieves.CONFIG.spawnRate.fishWeight.battlegill, 2, 4)));
                context.register(key("add_wreckers"), spawn(context, FOTTags.Biomes.SPAWNS_WRECKERS, new MobSpawnSettings.SpawnerData(FOTEntities.WRECKER, FishOfThieves.CONFIG.spawnRate.fishWeight.wrecker, 4, 8)));
                context.register(key("add_stormfish"), spawn(context, FOTTags.Biomes.SPAWNS_STORMFISH, new MobSpawnSettings.SpawnerData(FOTEntities.STORMFISH, FishOfThieves.CONFIG.spawnRate.fishWeight.stormfish, 4, 8)));

                context.register(key("add_fish_bone"), addBiomeFeature(context, FOTTags.Biomes.HAS_FISH_BONE, FOTPlacements.FISH_BONE, GenerationStep.Decoration.VEGETAL_DECORATION));
                context.register(key("add_coconut_tree"), addBiomeFeature(context, Biomes.BEACH, FOTPlacements.TREES_COCONUT, GenerationStep.Decoration.VEGETAL_DECORATION));

                context.register(key("add_sparse_jungle_tropical_flower"), addBiomeFeature(context, Biomes.SPARSE_JUNGLE, FOTPlacements.SPARSE_JUNGLE_TROPICAL_FLOWER, GenerationStep.Decoration.VEGETAL_DECORATION));
                context.register(key("add_sparse_jungle_fruit_trees"), addBiomeFeature(context, Biomes.SPARSE_JUNGLE, FOTPlacements.SPARSE_JUNGLE_FRUIT_TREES, GenerationStep.Decoration.VEGETAL_DECORATION));
                context.register(key("add_sparse_jungle_patch_wild_pineapple"), addBiomeFeature(context, Biomes.SPARSE_JUNGLE, FOTPlacements.SPARSE_JUNGLE_PATCH_WILD_PINEAPPLE, GenerationStep.Decoration.VEGETAL_DECORATION));
                context.register(key("add_sparse_jungle_patch_wild_pomegranate"), addBiomeFeature(context, Biomes.SPARSE_JUNGLE, FOTPlacements.SPARSE_JUNGLE_PATCH_WILD_POMEGRANATE, GenerationStep.Decoration.VEGETAL_DECORATION));
                context.register(key("add_sparse_jungle_patch_tropical_bush"), addBiomeFeature(context, Biomes.SPARSE_JUNGLE, FOTPlacements.SPARSE_JUNGLE_PATCH_TROPICAL_BUSH, GenerationStep.Decoration.VEGETAL_DECORATION));
            });
    //@formatter:on

    public static void generateBiomeModifiers(GatherDataEvent event)
    {
        event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<BiomeModifiers>) output -> new BiomeModifiers(output, event.getLookupProvider()));
    }

    private static ForgeBiomeModifiers.AddFeaturesBiomeModifier addBiomeFeature(BootstapContext<BiomeModifier> context, TagKey<Biome> biomeTagKey, ResourceKey<PlacedFeature> placedFeatureKey, GenerationStep.Decoration decoration)
    {
        return new ForgeBiomeModifiers.AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomeTagKey), HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedFeatureKey)), decoration);
    }

    private static ForgeBiomeModifiers.AddFeaturesBiomeModifier addBiomeFeature(BootstapContext<BiomeModifier> context, ResourceKey<Biome> biomeKey, ResourceKey<PlacedFeature> placedFeatureKey, GenerationStep.Decoration decoration)
    {
        return new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biomeKey)), HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedFeatureKey)), decoration);
    }

    private static class BiomeModifiers extends DatapackBuiltinEntriesProvider
    {
        public BiomeModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
        {
            super(output, registries, BUILDER, Set.of(FishOfThieves.MOD_ID));
        }

        @Override
        public String getName()
        {
            return "Biome Modifier Registries: " + FishOfThieves.MOD_ID;
        }
    }

    private static ResourceKey<BiomeModifier> key(String key)
    {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, FishOfThieves.id(key));
    }

    private static BiomeModifier spawn(BootstapContext<BiomeModifier> context, TagKey<Biome> tagKey, MobSpawnSettings.SpawnerData spawnerData)
    {
        var tag = context.lookup(Registries.BIOME).getOrThrow(tagKey);
        return ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(tag, spawnerData);
    }
}