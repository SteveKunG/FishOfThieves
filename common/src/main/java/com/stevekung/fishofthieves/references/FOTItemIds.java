package com.stevekung.fishofthieves.references;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class FOTItemIds
{
    public static final ResourceKey<Item> EARTHWORMS = create("earthworms");
    public static final ResourceKey<Item> GRUBS = create("grubs");
    public static final ResourceKey<Item> LEECHES = create("leeches");
    public static final ResourceKey<Item> SPLASHTAIL = create("splashtail");
    public static final ResourceKey<Item> PONDIE = create("pondie");
    public static final ResourceKey<Item> ISLEHOPPER = create("islehopper");
    public static final ResourceKey<Item> ANCIENTSCALE = create("ancientscale");
    public static final ResourceKey<Item> PLENTIFIN = create("plentifin");
    public static final ResourceKey<Item> WILDSPLASH = create("wildsplash");
    public static final ResourceKey<Item> DEVILFISH = create("devilfish");
    public static final ResourceKey<Item> BATTLEGILL = create("battlegill");
    public static final ResourceKey<Item> WRECKER = create("wrecker");
    public static final ResourceKey<Item> STORMFISH = create("stormfish");
    public static final ResourceKey<Item> COOKED_SPLASHTAIL = create("cooked_splashtail");
    public static final ResourceKey<Item> COOKED_PONDIE = create("cooked_pondie");
    public static final ResourceKey<Item> COOKED_ISLEHOPPER = create("cooked_islehopper");
    public static final ResourceKey<Item> COOKED_ANCIENTSCALE = create("cooked_ancientscale");
    public static final ResourceKey<Item> COOKED_PLENTIFIN = create("cooked_plentifin");
    public static final ResourceKey<Item> COOKED_WILDSPLASH = create("cooked_wildsplash");
    public static final ResourceKey<Item> COOKED_DEVILFISH = create("cooked_devilfish");
    public static final ResourceKey<Item> COOKED_BATTLEGILL = create("cooked_battlegill");
    public static final ResourceKey<Item> COOKED_WRECKER = create("cooked_wrecker");
    public static final ResourceKey<Item> COOKED_STORMFISH = create("cooked_stormfish");
    public static final ResourceKey<Item> SPLASHTAIL_BUCKET = create("splashtail_bucket");
    public static final ResourceKey<Item> PONDIE_BUCKET = create("pondie_bucket");
    public static final ResourceKey<Item> ISLEHOPPER_BUCKET = create("islehopper_bucket");
    public static final ResourceKey<Item> ANCIENTSCALE_BUCKET = create("ancientscale_bucket");
    public static final ResourceKey<Item> PLENTIFIN_BUCKET = create("plentifin_bucket");
    public static final ResourceKey<Item> WILDSPLASH_BUCKET = create("wildsplash_bucket");
    public static final ResourceKey<Item> DEVILFISH_BUCKET = create("devilfish_bucket");
    public static final ResourceKey<Item> BATTLEGILL_BUCKET = create("battlegill_bucket");
    public static final ResourceKey<Item> WRECKER_BUCKET = create("wrecker_bucket");
    public static final ResourceKey<Item> STORMFISH_BUCKET = create("stormfish_bucket");
    public static final ResourceKey<Item> COCONUT = create("coconut");
    public static final ResourceKey<Item> BANANA = create("banana");
    public static final ResourceKey<Item> HALF_PINEAPPLE = create("half_pineapple");
    public static final ResourceKey<Item> PINEAPPLE = create("pineapple");
    public static final ResourceKey<Item> CROWNLESS_PINEAPPLE = create("crownless_pineapple");
    public static final ResourceKey<Item> PINEAPPLE_SEEDS = create("pineapple_seeds");
    public static final ResourceKey<Item> PINEAPPLE_CROWN = create("pineapple_crown");
    public static final ResourceKey<Item> MANGO = create("mango");
    public static final ResourceKey<Item> RAW_MANGO = create("raw_mango");
    public static final ResourceKey<Item> POMEGRANATE = create("pomegranate");
    public static final ResourceKey<Item> POMEGRANATE_SEEDS = create("pomegranate_seeds");
    public static final ResourceKey<Item> GUARDIAN_FRUIT = create("guardian_fruit");
    public static final ResourceKey<Item> STORMFISH_POTTERY_SHERD = create("stormfish_pottery_sherd");
    public static final ResourceKey<Item> KRAKEN_POTTERY_SHERD = create("kraken_pottery_sherd");
    public static final ResourceKey<Item> MEGALODON_POTTERY_SHERD = create("megalodon_pottery_sherd");
    public static final ResourceKey<Item> GREAT_MOUTH_POTTERY_SHERD = create("great_mouth_pottery_sherd");
    public static final ResourceKey<Item> COCONUT_BOAT = create("coconut_boat");
    public static final ResourceKey<Item> COCONUT_CHEST_BOAT = create("coconut_chest_boat");

    public static final List<ResourceKey<Item>> FISH_BUCKETS = List.of(SPLASHTAIL_BUCKET, PONDIE_BUCKET, ISLEHOPPER_BUCKET, ANCIENTSCALE_BUCKET, PLENTIFIN_BUCKET, WILDSPLASH_BUCKET, DEVILFISH_BUCKET, BATTLEGILL_BUCKET, WRECKER_BUCKET, STORMFISH_BUCKET);

    private static ResourceKey<Item> create(String name)
    {
        return ResourceKey.create(Registries.ITEM, FishOfThieves.id(name));
    }
}