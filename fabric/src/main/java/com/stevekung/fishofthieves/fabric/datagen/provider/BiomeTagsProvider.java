package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTBiomes;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class BiomeTagsProvider extends FabricTagProvider<Biome>
{
    private static final TagKey<Biome> C_IS_LUSH = cTag("is_lush");

        public BiomeTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, Registries.BIOME, provider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.builder(FOTTags.Biomes.SPAWNS_SPLASHTAILS).forceAddTag(BiomeTags.IS_OCEAN);
        this.builder(FOTTags.Biomes.SPAWNS_PONDIES).forceAddTag(BiomeTags.IS_RIVER).forceAddTag(BiomeTags.IS_FOREST);
        this.builder(FOTTags.Biomes.SPAWNS_ISLEHOPPERS).forceAddTag(BiomeTags.IS_OVERWORLD);
        this.builder(FOTTags.Biomes.SPAWNS_ANCIENTSCALES).add(Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
        this.builder(FOTTags.Biomes.SPAWNS_PLENTIFINS).add(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
        this.builder(FOTTags.Biomes.SPAWNS_WILDSPLASH).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_BEACH).forceAddTag(BiomeTags.IS_JUNGLE).add(Biomes.LUSH_CAVES, Biomes.WARM_OCEAN, Biomes.SWAMP, Biomes.MANGROVE_SWAMP);
        this.builder(FOTTags.Biomes.SPAWNS_DEVILFISH).forceAddTag(BiomeTags.IS_OVERWORLD);
        this.builder(FOTTags.Biomes.SPAWNS_BATTLEGILLS).forceAddTag(BiomeTags.IS_OVERWORLD);
        this.builder(FOTTags.Biomes.SPAWNS_WRECKERS).forceAddTag(BiomeTags.IS_OCEAN);
        this.builder(FOTTags.Biomes.SPAWNS_STORMFISH).forceAddTag(BiomeTags.IS_OCEAN).add(Biomes.SPARSE_JUNGLE, FOTBiomes.TROPICAL_ISLAND);

        this.builder(FOTTags.Biomes.DEVILFISH_CANNOT_SPAWN).add(Biomes.LUSH_CAVES, Biomes.DEEP_DARK);
        this.builder(FOTTags.Biomes.ALWAYS_DROP_LEECHES).add(Biomes.MANGROVE_SWAMP);
        this.builder(FOTTags.Biomes.HAS_SEAPOST).add(Biomes.OCEAN, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN);
        this.builder(FOTTags.Biomes.HAS_FISH_BONE).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_RIVER).add(Biomes.SWAMP, Biomes.MANGROVE_SWAMP);

        this.builder(BiomeTags.IS_OVERWORLD).add(FOTBiomes.TROPICAL_ISLAND);
        this.builder(BiomeTags.IS_JUNGLE).add(FOTBiomes.TROPICAL_ISLAND);
        this.builder(BiomeTags.HAS_JUNGLE_TEMPLE).add(FOTBiomes.TROPICAL_ISLAND);

        this.builder(ConventionalBiomeTags.IS_TEMPERATE_OVERWORLD).add(FOTBiomes.TROPICAL_ISLAND);
        this.builder(ConventionalBiomeTags.IS_VEGETATION_SPARSE_OVERWORLD).add(FOTBiomes.TROPICAL_ISLAND);
        this.builder(ConventionalBiomeTags.IS_JUNGLE_TREE).add(FOTBiomes.TROPICAL_ISLAND);

        this.builder(C_IS_LUSH).add(FOTBiomes.TROPICAL_ISLAND);

        this.builder(FOTTags.Biomes.SERENE_SEASONS_TROPICAL_BIOMES).add(FOTBiomes.TROPICAL_ISLAND);
        this.builder(FOTTags.Biomes.SERENE_SEASONS_LESSER_COLOR_CHANGE_BIOMES).add(FOTBiomes.TROPICAL_ISLAND);
    }

    private static TagKey<Biome> cTag(String name)
    {
        return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath("c", name));
    }
}