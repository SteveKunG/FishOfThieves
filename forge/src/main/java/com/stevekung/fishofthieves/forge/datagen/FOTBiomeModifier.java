package com.stevekung.fishofthieves.forge.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class FOTBiomeModifier
{
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(ForgeRegistries.Keys.BIOME_MODIFIERS, context ->
    {
        context.register(key("add_splashtails"), spawn(context, FOTTags.Biomes.SPAWNS_SPLASHTAILS, new MobSpawnSettings.SpawnerData(FOTEntities.SPLASHTAIL, 15, 4, 8)));
        context.register(key("add_pondies"), spawn(context, FOTTags.Biomes.SPAWNS_PONDIES, new MobSpawnSettings.SpawnerData(FOTEntities.PONDIE, 15, 2, 4)));
        context.register(key("add_islehoppers"), spawn(context, FOTTags.Biomes.SPAWNS_ISLEHOPPERS, new MobSpawnSettings.SpawnerData(FOTEntities.ISLEHOPPER, 8, 2, 4)));
        context.register(key("add_ancientscales"), spawn(context, FOTTags.Biomes.SPAWNS_ANCIENTSCALES, new MobSpawnSettings.SpawnerData(FOTEntities.ANCIENTSCALE, 8, 4, 8)));
        context.register(key("add_plentifins"), spawn(context, FOTTags.Biomes.SPAWNS_PLENTIFINS, new MobSpawnSettings.SpawnerData(FOTEntities.PLENTIFIN, 12, 4, 8)));
        context.register(key("add_wildsplash"), spawn(context, FOTTags.Biomes.SPAWNS_WILDSPLASH, new MobSpawnSettings.SpawnerData(FOTEntities.WILDSPLASH, 10, 2, 4)));
        context.register(key("add_devilfish"), spawn(context, FOTTags.Biomes.SPAWNS_DEVILFISH, new MobSpawnSettings.SpawnerData(FOTEntities.DEVILFISH, 4, 1, 2)));
        context.register(key("add_battlegills"), spawn(context, FOTTags.Biomes.SPAWNS_BATTLEGILLS, new MobSpawnSettings.SpawnerData(FOTEntities.BATTLEGILL, 5, 2, 4)));
        context.register(key("add_wreckers"), spawn(context, FOTTags.Biomes.SPAWNS_WRECKERS, new MobSpawnSettings.SpawnerData(FOTEntities.WRECKER, 50, 4, 8)));
        context.register(key("add_stormfish"), spawn(context, FOTTags.Biomes.SPAWNS_STORMFISH, new MobSpawnSettings.SpawnerData(FOTEntities.STORMFISH, 12, 4, 8)));
    });

    public static void generateBiomeModifiers(GatherDataEvent event)
    {
        event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<BiomeModifiers>) output -> new BiomeModifiers(output, event.getLookupProvider()));
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
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(FishOfThieves.MOD_ID, key));
    }

    private static BiomeModifier spawn(BootstapContext<BiomeModifier> context, TagKey<Biome> tagKey, MobSpawnSettings.SpawnerData spawnerData)
    {
        var tag = context.lookup(Registries.BIOME).getOrThrow(tagKey);
        return ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(tag, spawnerData);
    }
}