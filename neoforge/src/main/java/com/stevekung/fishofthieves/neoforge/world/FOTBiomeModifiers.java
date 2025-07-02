package com.stevekung.fishofthieves.neoforge.world;

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
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.Weighted;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@EventBusSubscriber(modid = FishOfThieves.MOD_ID)
public class FOTBiomeModifiers
{
    //@formatter:off
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, FOTFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, FOTPlacements::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, context ->
            {
                context.register(key("add_splashtails"), spawn(context, FOTTags.Biomes.SPAWNS_SPLASHTAILS, new Weighted<>(new MobSpawnSettings.SpawnerData(FOTEntities.SPLASHTAIL, 4, 8), FishOfThieves.CONFIG.spawnRate.fishWeight.splashtail)));
                context.register(key("add_pondies"), spawn(context, FOTTags.Biomes.SPAWNS_PONDIES, new Weighted<>(new MobSpawnSettings.SpawnerData(FOTEntities.PONDIE, 2, 4), FishOfThieves.CONFIG.spawnRate.fishWeight.pondie)));
                context.register(key("add_islehoppers"), spawn(context, FOTTags.Biomes.SPAWNS_ISLEHOPPERS, new Weighted<>(new MobSpawnSettings.SpawnerData(FOTEntities.ISLEHOPPER, 2, 4), FishOfThieves.CONFIG.spawnRate.fishWeight.islehopper)));
                context.register(key("add_ancientscales"), spawn(context, FOTTags.Biomes.SPAWNS_ANCIENTSCALES, new Weighted<>(new MobSpawnSettings.SpawnerData(FOTEntities.ANCIENTSCALE, 4, 8), FishOfThieves.CONFIG.spawnRate.fishWeight.ancientscale)));
                context.register(key("add_plentifins"), spawn(context, FOTTags.Biomes.SPAWNS_PLENTIFINS, new Weighted<>(new MobSpawnSettings.SpawnerData(FOTEntities.PLENTIFIN, 4, 8), FishOfThieves.CONFIG.spawnRate.fishWeight.plentifin)));
                context.register(key("add_wildsplash"), spawn(context, FOTTags.Biomes.SPAWNS_WILDSPLASH, new Weighted<>(new MobSpawnSettings.SpawnerData(FOTEntities.WILDSPLASH, 2, 4), FishOfThieves.CONFIG.spawnRate.fishWeight.wildsplash)));
                context.register(key("add_devilfish"), spawn(context, FOTTags.Biomes.SPAWNS_DEVILFISH, new Weighted<>(new MobSpawnSettings.SpawnerData(FOTEntities.DEVILFISH, 1, 2), FishOfThieves.CONFIG.spawnRate.fishWeight.devilfish)));
                context.register(key("add_battlegills"), spawn(context, FOTTags.Biomes.SPAWNS_BATTLEGILLS, new Weighted<>(new MobSpawnSettings.SpawnerData(FOTEntities.BATTLEGILL, 2, 4), FishOfThieves.CONFIG.spawnRate.fishWeight.battlegill)));
                context.register(key("add_wreckers"), spawn(context, FOTTags.Biomes.SPAWNS_WRECKERS, new Weighted<>(new MobSpawnSettings.SpawnerData(FOTEntities.WRECKER, 4, 8), FishOfThieves.CONFIG.spawnRate.fishWeight.wrecker)));
                context.register(key("add_stormfish"), spawn(context, FOTTags.Biomes.SPAWNS_STORMFISH, new Weighted<>(new MobSpawnSettings.SpawnerData(FOTEntities.STORMFISH, 4, 8), FishOfThieves.CONFIG.spawnRate.fishWeight.stormfish)));

                context.register(key("add_fish_bone"), addBiomeFeature(context, FOTTags.Biomes.HAS_FISH_BONE, FOTPlacements.FISH_BONE, GenerationStep.Decoration.VEGETAL_DECORATION));
                context.register(key("add_coconut_tree"), addBiomeFeature(context, Biomes.BEACH, FOTPlacements.TREES_COCONUT, GenerationStep.Decoration.VEGETAL_DECORATION));

                context.register(key("add_sparse_jungle_tropical_flower"), addBiomeFeature(context, Biomes.SPARSE_JUNGLE, FOTPlacements.SPARSE_JUNGLE_TROPICAL_FLOWER, GenerationStep.Decoration.VEGETAL_DECORATION));
                context.register(key("add_sparse_jungle_fruit_trees"), addBiomeFeature(context, Biomes.SPARSE_JUNGLE, FOTPlacements.SPARSE_JUNGLE_FRUIT_TREES, GenerationStep.Decoration.VEGETAL_DECORATION));
                context.register(key("add_sparse_jungle_patch_wild_pineapple"), addBiomeFeature(context, Biomes.SPARSE_JUNGLE, FOTPlacements.SPARSE_JUNGLE_PATCH_WILD_PINEAPPLE, GenerationStep.Decoration.VEGETAL_DECORATION));
                context.register(key("add_sparse_jungle_patch_wild_pomegranate"), addBiomeFeature(context, Biomes.SPARSE_JUNGLE, FOTPlacements.SPARSE_JUNGLE_PATCH_WILD_POMEGRANATE, GenerationStep.Decoration.VEGETAL_DECORATION));
                context.register(key("add_sparse_jungle_patch_tropical_bush"), addBiomeFeature(context, Biomes.SPARSE_JUNGLE, FOTPlacements.SPARSE_JUNGLE_PATCH_TROPICAL_BUSH, GenerationStep.Decoration.VEGETAL_DECORATION));
            });
    //@formatter:on

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Server event)
    {
        event.getGenerator().addProvider(true, (DataProvider.Factory<ModBiomeModifiers>) output -> new ModBiomeModifiers(output, event.getLookupProvider()));
    }

    private static BiomeModifiers.AddFeaturesBiomeModifier addBiomeFeature(BootstrapContext<BiomeModifier> context, TagKey<Biome> biomeTagKey, ResourceKey<PlacedFeature> placedFeatureKey, GenerationStep.Decoration decoration)
    {
        return new BiomeModifiers.AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomeTagKey), HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedFeatureKey)), decoration);
    }

    private static BiomeModifiers.AddFeaturesBiomeModifier addBiomeFeature(BootstrapContext<BiomeModifier> context, ResourceKey<Biome> biomeKey, ResourceKey<PlacedFeature> placedFeatureKey, GenerationStep.Decoration decoration)
    {
        return new BiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biomeKey)), HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedFeatureKey)), decoration);
    }

    private static class ModBiomeModifiers extends DatapackBuiltinEntriesProvider
    {
        public ModBiomeModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
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
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, FishOfThieves.id(key));
    }

    private static BiomeModifier spawn(BootstrapContext<BiomeModifier> context, TagKey<Biome> tagKey, Weighted<MobSpawnSettings.SpawnerData> spawnerData)
    {
        var tag = context.lookup(Registries.BIOME).getOrThrow(tagKey);
        return BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(tag, spawnerData);
    }
}