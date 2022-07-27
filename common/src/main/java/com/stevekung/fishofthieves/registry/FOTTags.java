package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.core.FishOfThieves;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class FOTTags
{
    public static final TagKey<Item> THIEVES_FISH_BUCKET = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish_bucket"));
    public static final TagKey<Item> COOKED_THIEVES_FISH = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "cooked_thieves_fish"));

    public static final TagKey<EntityType<?>> THIEVES_FISH = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish"));

    public static final TagKey<Block> FIRELIGHT_DEVILFISH_WARM_BLOCKS = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "firelight_devilfish_warm_blocks"));
    public static final TagKey<Block> EARTHWORMS_DROPS = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "earthworms_drops"));
    public static final TagKey<Block> GRUBS_DROPS = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "grubs_drops"));
    public static final TagKey<Block> LEECHES_DROPS = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "leeches_drops"));

    public static final TagKey<Biome> IS_CAVES = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "is_caves"));

    public static final TagKey<Biome> SPAWNS_SPLASHTAILS = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_splashtails"));
    public static final TagKey<Biome> SPAWNS_PONDIES = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_pondies"));
    public static final TagKey<Biome> SPAWNS_ISLEHOPPERS = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_islehoppers"));
    public static final TagKey<Biome> SPAWNS_ANCIENTSCALES = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_ancientscales"));
    public static final TagKey<Biome> SPAWNS_PLENTIFINS = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_plentifins"));
    public static final TagKey<Biome> SPAWNS_WILDSPLASH = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_wildsplash"));
    public static final TagKey<Biome> SPAWNS_DEVILFISH = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_devilfish"));
    public static final TagKey<Biome> SPAWNS_BATTLEGILLS = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_battlegills"));
    public static final TagKey<Biome> SPAWNS_WRECKERS = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_wreckers"));
    public static final TagKey<Biome> SPAWNS_STORMFISH = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_stormfish"));

    public static final TagKey<Biome> DEVILFISH_CANNOT_SPAWN = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "devilfish_cannot_spawn"));
    public static final TagKey<Biome> SPAWNS_SAND_BATTLEGILLS = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_sand_battlegills"));
    public static final TagKey<Biome> SPAWNS_MOSS_ISLEHOPPERS = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_moss_islehoppers"));
    public static final TagKey<Biome> SPAWNS_WILD_STORMFISH = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_wild_stormfish"));
    public static final TagKey<Biome> SPAWNS_CORAL_WILDSPLASH = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_coral_wildsplash"));
    public static final TagKey<Biome> SPAWNS_SNOW_WRECKERS = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "spawns_snow_wreckers"));
}