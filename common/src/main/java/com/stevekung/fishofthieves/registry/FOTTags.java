package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.core.FishOfThieves;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class FOTTags
{
    public static final TagKey<Item> THIEVES_FISH_BUCKET = TagKey.create(Registries.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish_bucket"));
    public static final TagKey<Item> THIEVES_FISH = TagKey.create(Registries.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish"));
    public static final TagKey<Item> COOKED_THIEVES_FISH = TagKey.create(Registries.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, "cooked_thieves_fish"));
    public static final TagKey<Item> WORMS = TagKey.create(Registries.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, "worms"));
    public static final TagKey<Item> EARTHWORMS_FOOD = TagKey.create(Registries.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, "earthworms_food"));
    public static final TagKey<Item> GRUBS_FOOD = TagKey.create(Registries.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, "grubs_food"));
    public static final TagKey<Item> LEECHES_FOOD = TagKey.create(Registries.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, "leeches_food"));

    public static final TagKey<EntityType<?>> THIEVES_FISH_ENTITY_TYPE = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish"));
    public static final TagKey<EntityType<?>> FISH_BONE_DROP = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(FishOfThieves.MOD_ID, "fish_bone_drop"));

    public static final TagKey<Block> FIRELIGHT_DEVILFISH_WARM_BLOCKS = TagKey.create(Registries.BLOCK, new ResourceLocation(FishOfThieves.MOD_ID, "firelight_devilfish_warm_blocks"));
    public static final TagKey<Block> CORAL_WILDSPLASH_SPAWNABLE_ON = TagKey.create(Registries.BLOCK, new ResourceLocation(FishOfThieves.MOD_ID, "coral_wildsplash_spawnable_on"));
    public static final TagKey<Block> EARTHWORMS_DROPS = TagKey.create(Registries.BLOCK, new ResourceLocation(FishOfThieves.MOD_ID, "earthworms_drops"));
    public static final TagKey<Block> GRUBS_DROPS = TagKey.create(Registries.BLOCK, new ResourceLocation(FishOfThieves.MOD_ID, "grubs_drops"));
    public static final TagKey<Block> LEECHES_DROPS = TagKey.create(Registries.BLOCK, new ResourceLocation(FishOfThieves.MOD_ID, "leeches_drops"));
    public static final TagKey<Block> EARTHWORMS_DROP_BLACKLIST = TagKey.create(Registries.BLOCK, new ResourceLocation(FishOfThieves.MOD_ID, "earthworms_drop_blacklist"));

    public static final TagKey<Structure> BONE_ANCIENTSCALES_SPAWN_IN = TagKey.create(Registries.STRUCTURE, new ResourceLocation(FishOfThieves.MOD_ID, "bone_ancientscales_spawn_in"));
    public static final TagKey<Structure> BONEDUST_PLENTIFINS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, new ResourceLocation(FishOfThieves.MOD_ID, "bonedust_plentifins_spawn_in"));
    public static final TagKey<Structure> BATTLEGILLS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, new ResourceLocation(FishOfThieves.MOD_ID, "battlegills_spawn_in"));
    public static final TagKey<Structure> ANCIENTSCALES_SPAWN_IN = TagKey.create(Registries.STRUCTURE, new ResourceLocation(FishOfThieves.MOD_ID, "ancientscales_spawn_in"));
    public static final TagKey<Structure> PLENTIFINS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, new ResourceLocation(FishOfThieves.MOD_ID, "plentifins_spawn_in"));
    public static final TagKey<Structure> WRECKERS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, new ResourceLocation(FishOfThieves.MOD_ID, "wreckers_spawn_in"));
    public static final TagKey<Structure> WRECKERS_LOCATED = TagKey.create(Registries.STRUCTURE, new ResourceLocation(FishOfThieves.MOD_ID, "wreckers_located"));

    public static final TagKey<Biome> SPAWNS_SPLASHTAILS = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_splashtails"));
    public static final TagKey<Biome> SPAWNS_PONDIES = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_pondies"));
    public static final TagKey<Biome> SPAWNS_ISLEHOPPERS = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_islehoppers"));
    public static final TagKey<Biome> SPAWNS_ANCIENTSCALES = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_ancientscales"));
    public static final TagKey<Biome> SPAWNS_PLENTIFINS = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_plentifins"));
    public static final TagKey<Biome> SPAWNS_WILDSPLASH = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_wildsplash"));
    public static final TagKey<Biome> SPAWNS_DEVILFISH = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_devilfish"));
    public static final TagKey<Biome> SPAWNS_BATTLEGILLS = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_battlegills"));
    public static final TagKey<Biome> SPAWNS_WRECKERS = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_wreckers"));
    public static final TagKey<Biome> SPAWNS_STORMFISH = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_stormfish"));

    public static final TagKey<Biome> DEVILFISH_CANNOT_SPAWN = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "devilfish_cannot_spawn"));
    public static final TagKey<Biome> SPAWNS_SAND_BATTLEGILLS = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_sand_battlegills"));
    public static final TagKey<Biome> SPAWNS_MOSS_ISLEHOPPERS = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_moss_islehoppers"));
    public static final TagKey<Biome> SPAWNS_WILD_STORMFISH = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_wild_stormfish"));
    public static final TagKey<Biome> SPAWNS_CORAL_WILDSPLASH = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_coral_wildsplash"));
    public static final TagKey<Biome> SPAWNS_SNOW_WRECKERS = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_snow_wreckers"));
    public static final TagKey<Biome> SPAWNS_SANDY_WILDSPLASH = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_sandy_wildsplash"));
    public static final TagKey<Biome> SPAWNS_OCEAN_WILDSPLASH = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_ocean_wildsplash"));
    public static final TagKey<Biome> SPAWNS_MUDDY_WILDSPLASH = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_muddy_wildsplash"));

    public static final TagKey<Biome> ALWAYS_DROP_LEECHES = TagKey.create(Registries.BIOME, new ResourceLocation(FishOfThieves.MOD_ID, "always_drop_leeches"));
}