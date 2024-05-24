package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public interface FOTTags
{
    interface Items
    {
        TagKey<Item> THIEVES_FISH_BUCKET = TagKey.create(Registries.ITEM, FishOfThieves.res("thieves_fish_bucket"));
        TagKey<Item> THIEVES_FISH = TagKey.create(Registries.ITEM, FishOfThieves.res("thieves_fish"));
        TagKey<Item> COOKED_THIEVES_FISH = TagKey.create(Registries.ITEM, FishOfThieves.res("cooked_thieves_fish"));
        TagKey<Item> WORMS = TagKey.create(Registries.ITEM, FishOfThieves.res("worms"));
        TagKey<Item> EARTHWORMS_FOOD = TagKey.create(Registries.ITEM, FishOfThieves.res("earthworms_food"));
        TagKey<Item> GRUBS_FOOD = TagKey.create(Registries.ITEM, FishOfThieves.res("grubs_food"));
        TagKey<Item> LEECHES_FOOD = TagKey.create(Registries.ITEM, FishOfThieves.res("leeches_food"));
        TagKey<Item> FISH_PLAQUE_BUCKET_BLACKLIST = TagKey.create(Registries.ITEM, FishOfThieves.res("fish_plaque_bucket_blacklist"));
        TagKey<Item> WOODEN_FISH_PLAQUE = TagKey.create(Registries.ITEM, FishOfThieves.res("wooden_fish_plaque"));
        TagKey<Item> IRON_FRAME_FISH_PLAQUE = TagKey.create(Registries.ITEM, FishOfThieves.res("iron_frame_fish_plaque"));
        TagKey<Item> GOLDEN_FRAME_FISH_PLAQUE = TagKey.create(Registries.ITEM, FishOfThieves.res("golden_frame_fish_plaque"));
        TagKey<Item> GILDED_FRAME_FISH_PLAQUE = TagKey.create(Registries.ITEM, FishOfThieves.res("gilded_frame_fish_plaque"));
    }

    interface Blocks
    {
        TagKey<Block> FIRELIGHT_DEVILFISH_WARM_BLOCKS = TagKey.create(Registries.BLOCK, FishOfThieves.res("firelight_devilfish_warm_blocks"));
        TagKey<Block> CORAL_WILDSPLASH_SPAWNABLE_ON = TagKey.create(Registries.BLOCK, FishOfThieves.res("coral_wildsplash_spawnable_on"));
        TagKey<Block> AMETHYST_ISLEHOPPER_SPAWNABLE_ON = TagKey.create(Registries.BLOCK, FishOfThieves.res("amethyst_islehopper_spawnable_on"));
        TagKey<Block> EARTHWORMS_DROPS = TagKey.create(Registries.BLOCK, FishOfThieves.res("earthworms_drops"));
        TagKey<Block> GRUBS_DROPS = TagKey.create(Registries.BLOCK, FishOfThieves.res("grubs_drops"));
        TagKey<Block> LEECHES_DROPS = TagKey.create(Registries.BLOCK, FishOfThieves.res("leeches_drops"));
        TagKey<Block> EARTHWORMS_DROP_BLACKLIST = TagKey.create(Registries.BLOCK, FishOfThieves.res("earthworms_drop_blacklist"));
        TagKey<Block> FISH_REPELLENTS = TagKey.create(Registries.BLOCK, FishOfThieves.res("fish_repellents"));
        TagKey<Block> FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.res("fish_plaque"));
        TagKey<Block> WOODEN_FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.res("wooden_fish_plaque"));
        TagKey<Block> IRON_FRAME_FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.res("iron_frame_fish_plaque"));
        TagKey<Block> GOLDEN_FRAME_FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.res("golden_frame_fish_plaque"));
        TagKey<Block> GILDED_FRAME_FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.res("gilded_frame_fish_plaque"));
    }

    interface EntityTypes
    {
        TagKey<EntityType<?>> THIEVES_FISH_ENTITY_TYPE = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.res("thieves_fish"));
        TagKey<EntityType<?>> FISH_BONE_DROP = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.res("fish_bone_drop"));
        TagKey<EntityType<?>> HORIZONTAL_MOB_RENDER = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.res("horizontal_mob_render"));
        TagKey<EntityType<?>> BATTLEGILL_ATTACKABLE = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.res("battlegill_attackable"));
        TagKey<EntityType<?>> DEVILFISH_ATTACKABLE = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.res("devilfish_attackable"));
        TagKey<EntityType<?>> WRECKER_ATTACKABLE = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.res("wrecker_attackable"));
    }

    interface Structures
    {
        TagKey<Structure> BONE_ANCIENTSCALES_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.res("bone_ancientscales_spawn_in"));
        TagKey<Structure> BONEDUST_PLENTIFINS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.res("bonedust_plentifins_spawn_in"));
        TagKey<Structure> BATTLEGILLS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.res("battlegills_spawn_in"));
        TagKey<Structure> ANCIENTSCALES_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.res("ancientscales_spawn_in"));
        TagKey<Structure> PLENTIFINS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.res("plentifins_spawn_in"));
        TagKey<Structure> WRECKERS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.res("wreckers_spawn_in"));
        TagKey<Structure> WRECKERS_LOCATED = TagKey.create(Registries.STRUCTURE, FishOfThieves.res("wreckers_located"));
    }

    interface Biomes
    {
        TagKey<Biome> SPAWNS_SPLASHTAILS = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_splashtails"));
        TagKey<Biome> SPAWNS_PONDIES = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_pondies"));
        TagKey<Biome> SPAWNS_ISLEHOPPERS = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_islehoppers"));
        TagKey<Biome> SPAWNS_ANCIENTSCALES = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_ancientscales"));
        TagKey<Biome> SPAWNS_PLENTIFINS = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_plentifins"));
        TagKey<Biome> SPAWNS_WILDSPLASH = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_wildsplash"));
        TagKey<Biome> SPAWNS_DEVILFISH = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_devilfish"));
        TagKey<Biome> SPAWNS_BATTLEGILLS = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_battlegills"));
        TagKey<Biome> SPAWNS_WRECKERS = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_wreckers"));
        TagKey<Biome> SPAWNS_STORMFISH = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_stormfish"));
        TagKey<Biome> DEVILFISH_CANNOT_SPAWN = TagKey.create(Registries.BIOME, FishOfThieves.res("devilfish_cannot_spawn"));
        TagKey<Biome> SPAWNS_SAND_BATTLEGILLS = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_sand_battlegills"));
        TagKey<Biome> SPAWNS_MOSS_ISLEHOPPERS = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_moss_islehoppers"));
        TagKey<Biome> SPAWNS_WILD_STORMFISH = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_wild_stormfish"));
        TagKey<Biome> SPAWNS_CORAL_WILDSPLASH = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_coral_wildsplash"));
        TagKey<Biome> SPAWNS_SNOW_WRECKERS = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_snow_wreckers"));
        TagKey<Biome> SPAWNS_SANDY_WILDSPLASH = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_sandy_wildsplash"));
        TagKey<Biome> SPAWNS_OCEAN_WILDSPLASH = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_ocean_wildsplash"));
        TagKey<Biome> SPAWNS_MUDDY_WILDSPLASH = TagKey.create(Registries.BIOME, FishOfThieves.res("spawns_muddy_wildsplash"));
        TagKey<Biome> ALWAYS_DROP_LEECHES = TagKey.create(Registries.BIOME, FishOfThieves.res("always_drop_leeches"));
        TagKey<Biome> HAS_SEAPOST = TagKey.create(Registries.BIOME, FishOfThieves.res("has_seapost"));
        TagKey<Biome> ISLEHOPPER_SPAWN_AT_COAST = TagKey.create(Registries.BIOME, FishOfThieves.res("islehopper_spawn_at_coast"));
        TagKey<Biome> HAS_FISH_BONE = TagKey.create(Registries.BIOME, FishOfThieves.res("has_fish_bone"));
    }
}