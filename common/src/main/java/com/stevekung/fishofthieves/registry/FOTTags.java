package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public interface FOTTags
{
    Item[] FISH_BUCKETS = { FOTItems.SPLASHTAIL_BUCKET, FOTItems.PONDIE_BUCKET, FOTItems.ISLEHOPPER_BUCKET, FOTItems.ANCIENTSCALE_BUCKET, FOTItems.PLENTIFIN_BUCKET, FOTItems.WILDSPLASH_BUCKET, FOTItems.DEVILFISH_BUCKET, FOTItems.BATTLEGILL_BUCKET, FOTItems.WRECKER_BUCKET, FOTItems.STORMFISH_BUCKET };

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
        TagKey<Item> COPPER_FRAME_FISH_PLAQUE = TagKey.create(Registries.ITEM, FishOfThieves.id("copper_frame_fish_plaque"));
        TagKey<Item> GOLDEN_FRAME_FISH_PLAQUE = TagKey.create(Registries.ITEM, FishOfThieves.id("golden_frame_fish_plaque"));
        TagKey<Item> GILDED_FRAME_FISH_PLAQUE = TagKey.create(Registries.ITEM, FishOfThieves.id("gilded_frame_fish_plaque"));
        TagKey<Item> COCONUT_LOGS = TagKey.create(Registries.ITEM, FishOfThieves.id("coconut_logs"));
        TagKey<Item> BANANA_CLUSTERS = TagKey.create(Registries.ITEM, FishOfThieves.id("banana_clusters"));

        TagKey<Item> SERENE_SEASONS_SPRING_CROPS = TagKey.create(Registries.ITEM, ResourceLocation.tryParse("sereneseasons:spring_crops"));
        TagKey<Item> SERENE_SEASONS_SUMMER_CROPS = TagKey.create(Registries.ITEM, ResourceLocation.tryParse("sereneseasons:summer_crops"));
        TagKey<Item> SERENE_SEASONS_AUTUMN_CROPS = TagKey.create(Registries.ITEM, ResourceLocation.tryParse("sereneseasons:autumn_crops"));
        TagKey<Item> SERENE_SEASONS_YEAR_ROUND_CROPS = TagKey.create(Registries.ITEM, ResourceLocation.tryParse("sereneseasons:year_round_crops"));
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
        TagKey<Block> COPPER_FRAME_FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.id("copper_frame_fish_plaque"));
        TagKey<Block> GOLDEN_FRAME_FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.id("golden_frame_fish_plaque"));
        TagKey<Block> GILDED_FRAME_FISH_PLAQUE = TagKey.create(Registries.BLOCK, FishOfThieves.id("gilded_frame_fish_plaque"));
        TagKey<Block> NON_FULL_LOGS = TagKey.create(Registries.BLOCK, FishOfThieves.id("non_full_logs"));
        TagKey<Block> COCONUT_LOGS = TagKey.create(Registries.BLOCK, FishOfThieves.id("coconut_logs"));
        TagKey<Block> SMALL_COCONUT_LOGS = TagKey.create(Registries.BLOCK, FishOfThieves.id("small_coconut_logs"));
        TagKey<Block> BANANA_STEMS = TagKey.create(Registries.BLOCK, FishOfThieves.id("banana_stems"));
        TagKey<Block> BANANA_CLUSTER_PLANTS = TagKey.create(Registries.BLOCK, FishOfThieves.id("banana_cluster_plants"));
        TagKey<Block> BANANA_CLUSTERS = TagKey.create(Registries.BLOCK, FishOfThieves.id("banana_clusters"));
        TagKey<Block> BANANA_SHOOTS_PLACEABLE_ON = TagKey.create(Registries.BLOCK, FishOfThieves.id("banana_shoots_placeable_on"));
        TagKey<Block> MANGO_FRUITS = TagKey.create(Registries.BLOCK, FishOfThieves.id("mango_fruits"));

        TagKey<Block> SERENE_SEASONS_SPRING_CROPS = TagKey.create(Registries.BLOCK, ResourceLocation.tryParse("sereneseasons:spring_crops"));
        TagKey<Block> SERENE_SEASONS_SUMMER_CROPS = TagKey.create(Registries.BLOCK, ResourceLocation.tryParse("sereneseasons:summer_crops"));
        TagKey<Block> SERENE_SEASONS_AUTUMN_CROPS = TagKey.create(Registries.BLOCK, ResourceLocation.tryParse("sereneseasons:autumn_crops"));
        TagKey<Block> SERENE_SEASONS_YEAR_ROUND_CROPS = TagKey.create(Registries.BLOCK, ResourceLocation.tryParse("sereneseasons:year_round_crops"));
        TagKey<Block> SERENE_SEASONS_UNBREAKABLE_INFERTILE_CROPS = TagKey.create(Registries.BLOCK, ResourceLocation.tryParse("sereneseasons:unbreakable_infertile_crops"));

    }

    interface EntityTypes
    {
        TagKey<EntityType<?>> THIEVES_FISH_ENTITY_TYPE = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("thieves_fish"));
        TagKey<EntityType<?>> FISH_BONE_DROP = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("fish_bone_drop"));
        TagKey<EntityType<?>> FISH_PLAQUE_HORIZONTAL_RENDER = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("fish_plaque_horizontal_render"));
        TagKey<EntityType<?>> FISH_PLAQUE_HORIZONTAL_RENDER_ON_POWERED = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("fish_plaque_horizontal_render_on_powered"));
        TagKey<EntityType<?>> BATTLEGILL_ATTACKABLE = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("battlegill_attackable"));
        TagKey<EntityType<?>> DEVILFISH_ATTACKABLE = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("devilfish_attackable"));
        TagKey<EntityType<?>> WRECKER_ATTACKABLE = TagKey.create(Registries.ENTITY_TYPE, FishOfThieves.id("wrecker_attackable"));
    }

    interface Structures
    {
        TagKey<Structure> BATTLEGILLS_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.id("battlegills_spawn_in"));
        TagKey<Structure> ANCIENTSCALES_SPAWN_IN = TagKey.create(Registries.STRUCTURE, FishOfThieves.id("ancientscales_spawn_in"));
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
        TagKey<Biome> ALWAYS_DROP_LEECHES = TagKey.create(Registries.BIOME, FishOfThieves.id("always_drop_leeches"));
        TagKey<Biome> HAS_SEAPOST = TagKey.create(Registries.BIOME, FishOfThieves.id("has_seapost"));
        TagKey<Biome> HAS_FISH_BONE = TagKey.create(Registries.BIOME, FishOfThieves.id("has_fish_bone"));
        TagKey<Biome> SPAWNS_SHOAL = TagKey.create(Registries.BIOME, FishOfThieves.id("spawns_shoal"));
        TagKey<Biome> SHOAL_CANNOT_SPAWN = TagKey.create(Registries.BIOME, FishOfThieves.id("shoal_cannot_spawn"));

        TagKey<Biome> SERENE_SEASONS_TROPICAL_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.tryParse("sereneseasons:tropical_biomes"));
        TagKey<Biome> SERENE_SEASONS_LESSER_COLOR_CHANGE_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.tryParse("sereneseasons:lesser_color_change_biomes"));
    }

    interface DamageTypes
    {
        TagKey<DamageType> IS_MANGO = TagKey.create(Registries.DAMAGE_TYPE, FishOfThieves.id("is_mango"));
    }

    interface Enchantments
    {
        TagKey<Enchantment> DROP_PINEAPPLE_BLOCK_WHEN_MINING = TagKey.create(Registries.ENCHANTMENT, FishOfThieves.id("drop_pineapple_block_when_mining"));
    }

    interface PoiTypes
    {
        TagKey<PoiType> SHOAL = TagKey.create(Registries.POINT_OF_INTEREST_TYPE, FishOfThieves.id("shoal"));
    }
}