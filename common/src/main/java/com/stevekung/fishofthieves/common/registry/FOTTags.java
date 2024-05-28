package com.stevekung.fishofthieves.common.registry;

import com.stevekung.fishofthieves.common.FishOfThieves;
import com.stevekung.fishofthieves.common.entity.variant.*;
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
        TagKey<Item> THIEVES_FISH_BUCKET = TagKey.create(Registries.ITEM, FishOfThieves.id("thieves_fish_bucket"));
        TagKey<Item> THIEVES_FISH = TagKey.create(Registries.ITEM, FishOfThieves.id("thieves_fish"));
        TagKey<Item> COOKED_THIEVES_FISH = TagKey.create(Registries.ITEM, FishOfThieves.id("cooked_thieves_fish"));
        TagKey<Item> WORMS = TagKey.create(Registries.ITEM, FishOfThieves.id("worms"));
        TagKey<Item> EARTHWORMS_FOOD = TagKey.create(Registries.ITEM, FishOfThieves.id("earthworms_food"));
        TagKey<Item> GRUBS_FOOD = TagKey.create(Registries.ITEM, FishOfThieves.id("grubs_food"));
        TagKey<Item> LEECHES_FOOD = TagKey.create(Registries.ITEM, FishOfThieves.id("leeches_food"));
        TagKey<Item> FISH_PLAQUE_BUCKET_BLACKLIST = TagKey.create(Registries.ITEM, FishOfThieves.id("fish_plaque_bucket_blacklist"));
        TagKey<Item> WOODEN_FISH_PLAQUE = TagKey.create(Registries.ITEM, FishOfThieves.id("wooden_fish_plaque"));
        TagKey<Item> IRON_FRAME_FISH_PLAQUE = TagKey.create(Registries.ITEM, FishOfThieves.id("iron_frame_fish_plaque"));
        TagKey<Item> GOLDEN_FRAME_FISH_PLAQUE = TagKey.create(Registries.ITEM, FishOfThieves.id("golden_frame_fish_plaque"));
        TagKey<Item> GILDED_FRAME_FISH_PLAQUE = TagKey.create(Registries.ITEM, FishOfThieves.id("gilded_frame_fish_plaque"));
    }

    interface Blocks
    {
        TagKey<Block> FIRELIGHT_DEVILFISH_WARM_BLOCKS = TagKey.create(Registries.BLOCK, FishOfThieves.id("firelight_devilfish_warm_blocks"));
        TagKey<Block> CORAL_WILDSPLASH_SPAWNABLE_ON = TagKey.create(Registries.BLOCK, FishOfThieves.id("coral_wildsplash_spawnable_on"));
        TagKey<Block> AMETHYST_ISLEHOPPER_SPAWNABLE_ON = TagKey.create(Registries.BLOCK, FishOfThieves.id("amethyst_islehopper_spawnable_on"));
        TagKey<Block> EARTHWORMS_DROPS = TagKey.create(Registries.BLOCK, FishOfThieves.id("earthworms_drops"));
        TagKey<Block> GRUBS_DROPS = TagKey.create(Registries.BLOCK, FishOfThieves.id("grubs_drops"));
        TagKey<Block> LEECHES_DROPS = TagKey.create(Registries.BLOCK, FishOfThieves.id("leeches_drops"));
        TagKey<Block> EARTHWORMS_DROP_BLACKLIST = TagKey.create(Registries.BLOCK, FishOfThieves.id("earthworms_drop_blacklist"));
        TagKey<Block> FISH_REPELLENTS = TagKey.create(Registries.BLOCK, FishOfThieves.id("fish_repellents"));
        TagKey<Block> FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.id("fish_plaque"));
        TagKey<Block> WOODEN_FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.id("wooden_fish_plaque"));
        TagKey<Block> IRON_FRAME_FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.id("iron_frame_fish_plaque"));
        TagKey<Block> GOLDEN_FRAME_FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.id("golden_frame_fish_plaque"));
        TagKey<Block> GILDED_FRAME_FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.id("gilded_frame_fish_plaque"));
    }

    interface EntityTypes
    {
        TagKey<EntityType<?>> THIEVES_FISH_ENTITY_TYPE = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("thieves_fish"));
        TagKey<EntityType<?>> FISH_BONE_DROP = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("fish_bone_drop"));
        TagKey<EntityType<?>> HORIZONTAL_MOB_RENDER = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("horizontal_mob_render"));
        TagKey<EntityType<?>> BATTLEGILL_ATTACKABLE = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("battlegill_attackable"));
        TagKey<EntityType<?>> DEVILFISH_ATTACKABLE = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("devilfish_attackable"));
        TagKey<EntityType<?>> WRECKER_ATTACKABLE = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("wrecker_attackable"));
    }

    interface Structures
    {
        TagKey<Structure> BONE_ANCIENTSCALES_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.id("bone_ancientscales_spawn_in"));
        TagKey<Structure> BONEDUST_PLENTIFINS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.id("bonedust_plentifins_spawn_in"));
        TagKey<Structure> BATTLEGILLS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.id("battlegills_spawn_in"));
        TagKey<Structure> ANCIENTSCALES_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.id("ancientscales_spawn_in"));
        TagKey<Structure> PLENTIFINS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.id("plentifins_spawn_in"));
        TagKey<Structure> WRECKERS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.id("wreckers_spawn_in"));
        TagKey<Structure> WRECKERS_LOCATED = TagKey.create(Registries.STRUCTURE, FishOfThieves.id("wreckers_located"));
    }

    interface Biomes
    {
        TagKey<Biome> SPAWNS_SPLASHTAILS = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_splashtails"));
        TagKey<Biome> SPAWNS_PONDIES = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_pondies"));
        TagKey<Biome> SPAWNS_ISLEHOPPERS = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_islehoppers"));
        TagKey<Biome> SPAWNS_ANCIENTSCALES = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_ancientscales"));
        TagKey<Biome> SPAWNS_PLENTIFINS = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_plentifins"));
        TagKey<Biome> SPAWNS_WILDSPLASH = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_wildsplash"));
        TagKey<Biome> SPAWNS_DEVILFISH = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_devilfish"));
        TagKey<Biome> SPAWNS_BATTLEGILLS = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_battlegills"));
        TagKey<Biome> SPAWNS_WRECKERS = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_wreckers"));
        TagKey<Biome> SPAWNS_STORMFISH = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_stormfish"));
        TagKey<Biome> DEVILFISH_CANNOT_SPAWN = TagKey.create(Registries.BIOME, FishOfThieves.id("devilfish_cannot_spawn"));
        TagKey<Biome> SPAWNS_SAND_BATTLEGILLS = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_sand_battlegills"));
        TagKey<Biome> SPAWNS_MOSS_ISLEHOPPERS = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_moss_islehoppers"));
        TagKey<Biome> SPAWNS_WILD_STORMFISH = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_wild_stormfish"));
        TagKey<Biome> SPAWNS_CORAL_WILDSPLASH = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_coral_wildsplash"));
        TagKey<Biome> SPAWNS_SNOW_WRECKERS = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_snow_wreckers"));
        TagKey<Biome> SPAWNS_SANDY_WILDSPLASH = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_sandy_wildsplash"));
        TagKey<Biome> SPAWNS_OCEAN_WILDSPLASH = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_ocean_wildsplash"));
        TagKey<Biome> SPAWNS_MUDDY_WILDSPLASH = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_muddy_wildsplash"));
        TagKey<Biome> ALWAYS_DROP_LEECHES = TagKey.create(Registries.BIOME, FishOfThieves.id("always_drop_leeches"));
        TagKey<Biome> HAS_SEAPOST = TagKey.create(Registries.BIOME, FishOfThieves.id("has_seapost"));
        TagKey<Biome> ISLEHOPPER_SPAWN_AT_COAST = TagKey.create(Registries.BIOME, FishOfThieves.id("islehopper_spawn_at_coast"));
        TagKey<Biome> HAS_FISH_BONE = TagKey.create(Registries.BIOME, FishOfThieves.id("has_fish_bone"));
    }

    interface FishVariant
    {
        TagKey<SplashtailVariant> DEFAULT_SPLASHTAIL_SPAWNS = TagKey.create(FOTRegistries.SPLASHTAIL_VARIANT_REGISTRY, FishOfThieves.id("default_splashtail_spawns"));
        TagKey<PondieVariant> DEFAULT_PONDIE_SPAWNS = TagKey.create(FOTRegistries.PONDIE_VARIANT_REGISTRY, FishOfThieves.id("default_pondie_spawns"));
        TagKey<IslehopperVariant> DEFAULT_ISLEHOPPER_SPAWNS = TagKey.create(FOTRegistries.ISLEHOPPER_VARIANT_REGISTRY, FishOfThieves.id("default_islehopper_spawns"));
        TagKey<AncientscaleVariant> DEFAULT_ANCIENTSCALE_SPAWNS = TagKey.create(FOTRegistries.ANCIENTSCALE_VARIANT_REGISTRY, FishOfThieves.id("default_ancientscale_spawns"));
        TagKey<PlentifinVariant> DEFAULT_PLENTIFIN_SPAWNS = TagKey.create(FOTRegistries.PLENTIFIN_VARIANT_REGISTRY, FishOfThieves.id("default_plentifin_spawns"));
        TagKey<WildsplashVariant> DEFAULT_WILDSPLASH_SPAWNS = TagKey.create(FOTRegistries.WILDSPLASH_VARIANT_REGISTRY, FishOfThieves.id("default_wildsplash_spawns"));
        TagKey<DevilfishVariant> DEFAULT_DEVILFISH_SPAWNS = TagKey.create(FOTRegistries.DEVILFISH_VARIANT_REGISTRY, FishOfThieves.id("default_devilfish_spawns"));
        TagKey<BattlegillVariant> DEFAULT_BATTLEGILL_SPAWNS = TagKey.create(FOTRegistries.BATTLEGILL_VARIANT_REGISTRY, FishOfThieves.id("default_battlegill_spawns"));
        TagKey<WreckerVariant> DEFAULT_WRECKER_SPAWNS = TagKey.create(FOTRegistries.WRECKER_VARIANT_REGISTRY, FishOfThieves.id("default_wrecker_spawns"));
        TagKey<StormfishVariant> DEFAULT_STORMFISH_SPAWNS = TagKey.create(FOTRegistries.STORMFISH_VARIANT_REGISTRY, FishOfThieves.id("default_stormfish_spawns"));
    }
}