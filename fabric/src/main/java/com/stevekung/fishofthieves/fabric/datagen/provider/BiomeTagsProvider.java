package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTBiomes;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class BiomeTagsProvider extends FabricTagProvider<Biome>
{
    private static final TagKey<Biome> FORGE_IS_SPARSE_OVERWORLD = forgeTag("is_sparse/overworld");
    private static final TagKey<Biome> FORGE_IS_LUSH = forgeTag("is_lush");

    public BiomeTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, Registries.BIOME, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_SPLASHTAILS).forceAddTag(BiomeTags.IS_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_PONDIES).forceAddTag(BiomeTags.IS_RIVER).forceAddTag(BiomeTags.IS_FOREST);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_ISLEHOPPERS).forceAddTag(BiomeTags.IS_OVERWORLD);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_ANCIENTSCALES).add(Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_PLENTIFINS).add(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_WILDSPLASH).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_BEACH).forceAddTag(BiomeTags.IS_JUNGLE).forceAddTag(BiomeTags.HAS_CLOSER_WATER_FOG).add(Biomes.LUSH_CAVES, Biomes.WARM_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_DEVILFISH).forceAddTag(BiomeTags.IS_OVERWORLD);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_BATTLEGILLS).forceAddTag(BiomeTags.IS_OVERWORLD);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_WRECKERS).forceAddTag(BiomeTags.IS_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_STORMFISH).forceAddTag(BiomeTags.IS_OCEAN).add(Biomes.SPARSE_JUNGLE);

        this.getOrCreateTagBuilder(FOTTags.Biomes.DEVILFISH_CANNOT_SPAWN).add(Biomes.LUSH_CAVES, Biomes.DEEP_DARK);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_SAND_BATTLEGILLS).add(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_MOSS_ISLEHOPPERS).forceAddTag(BiomeTags.IS_JUNGLE).forceAddTag(BiomeTags.HAS_CLOSER_WATER_FOG).add(Biomes.LUSH_CAVES);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_WILD_STORMFISH).add(Biomes.SPARSE_JUNGLE);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_CORAL_WILDSPLASH).add(Biomes.WARM_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_SNOW_WRECKERS).add(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_SANDY_WILDSPLASH).forceAddTag(BiomeTags.IS_BEACH);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_OCEAN_WILDSPLASH).forceAddTag(BiomeTags.IS_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_MUDDY_WILDSPLASH).forceAddTag(BiomeTags.HAS_CLOSER_WATER_FOG);
        this.getOrCreateTagBuilder(FOTTags.Biomes.ALWAYS_DROP_LEECHES).add(Biomes.MANGROVE_SWAMP);
        this.getOrCreateTagBuilder(FOTTags.Biomes.HAS_SEAPOST).add(Biomes.OCEAN, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Biomes.HAS_FISH_BONE).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_RIVER).add(Biomes.SWAMP, Biomes.MANGROVE_SWAMP);

        this.getOrCreateTagBuilder(BiomeTags.IS_JUNGLE).add(FOTBiomes.TROPICAL_ISLAND);
        this.getOrCreateTagBuilder(BiomeTags.HAS_JUNGLE_TEMPLE).add(FOTBiomes.TROPICAL_ISLAND);
        this.getOrCreateTagBuilder(BiomeTags.INCREASED_FIRE_BURNOUT).add(FOTBiomes.TROPICAL_ISLAND);

        this.getOrCreateTagBuilder(ConventionalBiomeTags.JUNGLE).add(FOTBiomes.TROPICAL_ISLAND);
        this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_TEMPERATE).add(FOTBiomes.TROPICAL_ISLAND);
        this.getOrCreateTagBuilder(ConventionalBiomeTags.VEGETATION_SPARSE).add(FOTBiomes.TROPICAL_ISLAND);
        this.getOrCreateTagBuilder(ConventionalBiomeTags.TREE_JUNGLE).add(FOTBiomes.TROPICAL_ISLAND);

        this.getOrCreateTagBuilder(FORGE_IS_SPARSE_OVERWORLD).add(FOTBiomes.TROPICAL_ISLAND);
        this.getOrCreateTagBuilder(FORGE_IS_LUSH).add(FOTBiomes.TROPICAL_ISLAND);
    }

    private static TagKey<Biome> forgeTag(String name)
    {
        return TagKey.create(Registries.BIOME, new ResourceLocation("forge", name));
    }
}