package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class BiomeTagsProvider extends FabricTagProvider<Biome>
{
    public BiomeTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, Registries.BIOME, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_SPLASHTAILS).forceAddTag(BiomeTags.IS_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_PONDIES).forceAddTag(BiomeTags.IS_RIVER).forceAddTag(BiomeTags.IS_FOREST);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_ISLEHOPPERS).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_BEACH).forceAddTag(BiomeTags.IS_JUNGLE).forceAddTag(BiomeTags.HAS_CLOSER_WATER_FOG).add(Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_ANCIENTSCALES).add(Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_PLENTIFINS).add(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_WILDSPLASH).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_BEACH).forceAddTag(BiomeTags.IS_JUNGLE).forceAddTag(BiomeTags.HAS_CLOSER_WATER_FOG).add(Biomes.LUSH_CAVES, Biomes.WARM_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_DEVILFISH).forceAddTag(BiomeTags.IS_OVERWORLD);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_BATTLEGILLS).forceAddTag(BiomeTags.IS_OVERWORLD);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_WRECKERS).forceAddTag(BiomeTags.IS_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_STORMFISH).forceAddTag(BiomeTags.IS_OCEAN).add(Biomes.SPARSE_JUNGLE);

        this.getOrCreateTagBuilder(FOTTags.Biomes.DEVILFISH_CANNOT_SPAWN).add(Biomes.LUSH_CAVES, Biomes.DEEP_DARK);
        this.getOrCreateTagBuilder(FOTTags.Biomes.ALWAYS_DROP_LEECHES).add(Biomes.MANGROVE_SWAMP);
        this.getOrCreateTagBuilder(FOTTags.Biomes.HAS_SEAPOST).add(Biomes.OCEAN, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Biomes.ISLEHOPPER_SPAWN_AT_COAST).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_BEACH);
        this.getOrCreateTagBuilder(FOTTags.Biomes.HAS_FISH_BONE).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_RIVER).add(Biomes.SWAMP, Biomes.MANGROVE_SWAMP);
    }
}