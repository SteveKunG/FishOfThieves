package com.stevekung.fishofthieves.registry;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.item.*;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;

public class FOTItems
{
    // Block Item
    public static final Item FISH_BONE = registerBlock(FOTBlocks.FISH_BONE);

    public static final Item OAK_FISH_PLAQUE = registerBlock(FOTBlocks.OAK_FISH_PLAQUE);
    public static final Item SPRUCE_FISH_PLAQUE = registerBlock(FOTBlocks.SPRUCE_FISH_PLAQUE);
    public static final Item BIRCH_FISH_PLAQUE = registerBlock(FOTBlocks.BIRCH_FISH_PLAQUE);
    public static final Item JUNGLE_FISH_PLAQUE = registerBlock(FOTBlocks.JUNGLE_FISH_PLAQUE);
    public static final Item ACACIA_FISH_PLAQUE = registerBlock(FOTBlocks.ACACIA_FISH_PLAQUE);
    public static final Item DARK_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.DARK_OAK_FISH_PLAQUE);
    public static final Item MANGROVE_FISH_PLAQUE = registerBlock(FOTBlocks.MANGROVE_FISH_PLAQUE);
    public static final Item CHERRY_FISH_PLAQUE = registerBlock(FOTBlocks.CHERRY_FISH_PLAQUE);
    public static final Item BAMBOO_FISH_PLAQUE = registerBlock(FOTBlocks.BAMBOO_FISH_PLAQUE);
    public static final Item CRIMSON_FISH_PLAQUE = registerBlock(FOTBlocks.CRIMSON_FISH_PLAQUE);
    public static final Item WARPED_FISH_PLAQUE = registerBlock(FOTBlocks.WARPED_FISH_PLAQUE);

    public static final Item IRON_FRAME_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE);
    public static final Item IRON_FRAME_SPRUCE_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE);
    public static final Item IRON_FRAME_BIRCH_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE);
    public static final Item IRON_FRAME_JUNGLE_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE);
    public static final Item IRON_FRAME_ACACIA_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE);
    public static final Item IRON_FRAME_DARK_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE);
    public static final Item IRON_FRAME_MANGROVE_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE);
    public static final Item IRON_FRAME_CHERRY_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE);
    public static final Item IRON_FRAME_BAMBOO_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE);
    public static final Item IRON_FRAME_CRIMSON_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE);
    public static final Item IRON_FRAME_WARPED_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE);

    public static final Item GOLDEN_FRAME_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_SPRUCE_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_BIRCH_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_JUNGLE_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_ACACIA_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_MANGROVE_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_CHERRY_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_BAMBOO_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_CRIMSON_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_WARPED_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE);

    public static final Item GILDED_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_OAK_FISH_PLAQUE);
    public static final Item GILDED_SPRUCE_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE);
    public static final Item GILDED_BIRCH_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE);
    public static final Item GILDED_JUNGLE_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE);
    public static final Item GILDED_ACACIA_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE);
    public static final Item GILDED_DARK_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE);
    public static final Item GILDED_MANGROVE_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE);
    public static final Item GILDED_CHERRY_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_CHERRY_FISH_PLAQUE);
    public static final Item GILDED_BAMBOO_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE);
    public static final Item GILDED_CRIMSON_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE);
    public static final Item GILDED_WARPED_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_WARPED_FISH_PLAQUE);

    public static final Item SMALL_COCONUT_LOG = registerBlock(FOTBlocks.SMALL_COCONUT_LOG);
    public static final Item SMALL_COCONUT_WOOD = registerBlock(FOTBlocks.SMALL_COCONUT_WOOD);
    public static final Item MEDIUM_COCONUT_LOG = registerBlock(FOTBlocks.MEDIUM_COCONUT_LOG);
    public static final Item MEDIUM_COCONUT_WOOD = registerBlock(FOTBlocks.MEDIUM_COCONUT_WOOD);
    public static final Item COCONUT_LOG = registerBlock(FOTBlocks.COCONUT_LOG);
    public static final Item COCONUT_WOOD = registerBlock(FOTBlocks.COCONUT_WOOD);
    public static final Item STRIPPED_COCONUT_LOG = registerBlock(FOTBlocks.STRIPPED_COCONUT_LOG);
    public static final Item STRIPPED_COCONUT_WOOD = registerBlock(FOTBlocks.STRIPPED_COCONUT_WOOD);
    public static final Item STRIPPED_MEDIUM_COCONUT_LOG = registerBlock(FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG);
    public static final Item STRIPPED_MEDIUM_COCONUT_WOOD = registerBlock(FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD);
    public static final Item STRIPPED_SMALL_COCONUT_LOG = registerBlock(FOTBlocks.STRIPPED_SMALL_COCONUT_LOG);
    public static final Item STRIPPED_SMALL_COCONUT_WOOD = registerBlock(FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD);
    public static final Item COCONUT_FRONDS = registerBlock(FOTBlocks.COCONUT_FRONDS);
    public static final Item BANANA_STEM = registerBlock(FOTBlocks.BANANA_STEM);
    public static final Item BANANA_LEAVES = registerBlock(FOTBlocks.BANANA_LEAVES);
    public static final Item BANANA_BLOSSOM = registerBlock(FOTBlocks.BANANA_BLOSSOM);
    public static final Item UNDERRIPE_BANANA_CLUSTER = registerBlock(FOTBlocks.UNDERRIPE_BANANA_CLUSTER);
    public static final Item BARELY_RIPE_BANANA_CLUSTER = registerBlock(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER);
    public static final Item RIPE_BANANA_CLUSTER = registerBlock(FOTBlocks.RIPE_BANANA_CLUSTER);
    public static final Item RIPE_PINEAPPLE_BLOCK = registerBlock(FOTBlocks.RIPE_PINEAPPLE_BLOCK, properties -> properties.component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD).setSwappable(false).build()));
    public static final Item CROWNLESS_RIPE_PINEAPPLE_BLOCK = registerBlock(FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK, properties -> properties.component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD).setSwappable(false).build()));
    public static final Item UNDERRIPE_PINEAPPLE_BLOCK = registerBlock(FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK, properties -> properties.component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD).setSwappable(false).build()));
    public static final Item MANGO_LEAVES = registerBlock(FOTBlocks.MANGO_LEAVES);
    public static final Item TALL_POMEGRANATE_PLANT = register("tall_pomegranate_plant", properties -> new DoubleHighBlockItem(FOTBlocks.TALL_POMEGRANATE_PLANT, properties));
    public static final Item PRISMARIZED_LOG = registerBlock(FOTBlocks.PRISMARIZED_LOG);

    public static final Item PINK_PLUMERIA = registerBlock(FOTBlocks.PINK_PLUMERIA);
    public static final Item LIGHT_BLUE_PLUMERIA = registerBlock(FOTBlocks.LIGHT_BLUE_PLUMERIA);
    public static final Item WHITE_PLUMERIA = registerBlock(FOTBlocks.WHITE_PLUMERIA);
    public static final Item BANANA_SHOOTS = registerBlock(FOTBlocks.BANANA_SHOOTS);
    public static final Item MANGO_PIT = registerBlock(FOTBlocks.MANGO_PIT, Item.Properties::useItemDescriptionPrefix);
    public static final Item MANGO_SAPLING = registerBlock(FOTBlocks.MANGO_SAPLING);
    public static final Item POMEGRANATE_PLANT = registerBlock(FOTBlocks.POMEGRANATE_PLANT);
    public static final Item TROPICAL_RED_FERN = registerBlock(FOTBlocks.TROPICAL_RED_FERN);
    public static final Item TROPICAL_MONSTERA = registerBlock(FOTBlocks.TROPICAL_MONSTERA);

    public static final Item COCONUT_PLANKS = registerBlock(FOTBlocks.COCONUT_PLANKS);
    public static final Item COCONUT_BUTTON = registerBlock(FOTBlocks.COCONUT_BUTTON);
    public static final Item COCONUT_FENCE = registerBlock(FOTBlocks.COCONUT_FENCE);
    public static final Item COCONUT_FENCE_GATE = registerBlock(FOTBlocks.COCONUT_FENCE_GATE);
    public static final Item COCONUT_PRESSURE_PLATE = registerBlock(FOTBlocks.COCONUT_PRESSURE_PLATE);
    public static final Item COCONUT_SLAB = registerBlock(FOTBlocks.COCONUT_SLAB);
    public static final Item COCONUT_STAIRS = registerBlock(FOTBlocks.COCONUT_STAIRS);
    public static final Item COCONUT_TRAPDOOR = registerBlock(FOTBlocks.COCONUT_TRAPDOOR);
    public static final Item COCONUT_DOOR = register("coconut_door", properties -> new DoubleHighBlockItem(FOTBlocks.COCONUT_DOOR, properties));
    public static final Item COCONUT_SIGN = register("coconut_sign", properties -> new FOTSignItem(properties.stacksTo(16), FOTBlocks.COCONUT_SIGN, FOTBlocks.COCONUT_WALL_SIGN));
    public static final Item COCONUT_HANGING_SIGN = register("coconut_hanging_sign", properties -> new FOTHangingSignItem(FOTBlocks.COCONUT_HANGING_SIGN, FOTBlocks.COCONUT_WALL_HANGING_SIGN, properties.stacksTo(16)));
    public static final Item COCONUT_FISH_PLAQUE = registerBlock(FOTBlocks.COCONUT_FISH_PLAQUE);
    public static final Item IRON_FRAME_COCONUT_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_COCONUT_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE);
    public static final Item GILDED_COCONUT_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_COCONUT_FISH_PLAQUE);

    // Item
    public static final Item EARTHWORMS = register("earthworms", new Item.Properties().food(FOTFoodProperties.WORMS, FOTConsumables.WORMS));
    public static final Item GRUBS = register("grubs", new Item.Properties().food(FOTFoodProperties.WORMS, FOTConsumables.WORMS));
    public static final Item LEECHES = register("leeches", new Item.Properties().food(FOTFoodProperties.WORMS, FOTConsumables.WORMS));

    public static final Item SPLASHTAIL = register("splashtail", properties -> new FOTItem(properties.food(FOTFoodProperties.SPLASHTAIL), FOTEntities.SPLASHTAIL, FOTRegistries.SPLASHTAIL_VARIANT));
    public static final Item PONDIE = register("pondie", properties -> new FOTItem(properties.food(FOTFoodProperties.PONDIE), FOTEntities.PONDIE, FOTRegistries.PONDIE_VARIANT));
    public static final Item ISLEHOPPER = register("islehopper", properties -> new FOTItem(properties.food(FOTFoodProperties.ISLEHOPPER), FOTEntities.ISLEHOPPER, FOTRegistries.ISLEHOPPER_VARIANT));
    public static final Item ANCIENTSCALE = register("ancientscale", properties -> new FOTItem(properties.food(FOTFoodProperties.ANCIENTSCALE), FOTEntities.ANCIENTSCALE, FOTRegistries.ANCIENTSCALE_VARIANT));
    public static final Item PLENTIFIN = register("plentifin", properties -> new FOTItem(properties.food(FOTFoodProperties.PLENTIFIN), FOTEntities.PLENTIFIN, FOTRegistries.PLENTIFIN_VARIANT));
    public static final Item WILDSPLASH = register("wildsplash", properties -> new FOTItem(properties.food(FOTFoodProperties.WILDSPLASH), FOTEntities.WILDSPLASH, FOTRegistries.WILDSPLASH_VARIANT));
    public static final Item DEVILFISH = register("devilfish", properties -> new FOTItem(properties.food(FOTFoodProperties.DEVILFISH, FOTConsumables.DEVILFISH), FOTEntities.DEVILFISH, FOTRegistries.DEVILFISH_VARIANT));
    public static final Item BATTLEGILL = register("battlegill", properties -> new FOTItem(properties.food(FOTFoodProperties.BATTLEGILL), FOTEntities.BATTLEGILL, FOTRegistries.BATTLEGILL_VARIANT));
    public static final Item WRECKER = register("wrecker", properties -> new FOTItem(properties.food(FOTFoodProperties.WRECKER), FOTEntities.WRECKER, FOTRegistries.WRECKER_VARIANT));
    public static final Item STORMFISH = register("stormfish", properties -> new FOTItem(properties.food(FOTFoodProperties.STORMFISH), FOTEntities.STORMFISH, FOTRegistries.STORMFISH_VARIANT));

    public static final Item COOKED_SPLASHTAIL = register("cooked_splashtail", new Item.Properties().food(FOTFoodProperties.COOKED_SPLASHTAIL));
    public static final Item COOKED_PONDIE = register("cooked_pondie", new Item.Properties().food(FOTFoodProperties.COOKED_PONDIE));
    public static final Item COOKED_ISLEHOPPER = register("cooked_islehopper", new Item.Properties().food(FOTFoodProperties.COOKED_ISLEHOPPER));
    public static final Item COOKED_ANCIENTSCALE = register("cooked_ancientscale", new Item.Properties().food(FOTFoodProperties.COOKED_ANCIENTSCALE));
    public static final Item COOKED_PLENTIFIN = register("cooked_plentifin", new Item.Properties().food(FOTFoodProperties.COOKED_PLENTIFIN, FOTConsumables.COOKED_PLENTIFIN));
    public static final Item COOKED_WILDSPLASH = register("cooked_wildsplash", new Item.Properties().food(FOTFoodProperties.COOKED_WILDSPLASH));
    public static final Item COOKED_DEVILFISH = register("cooked_devilfish", new Item.Properties().food(FOTFoodProperties.COOKED_DEVILFISH));
    public static final Item COOKED_BATTLEGILL = register("cooked_battlegill", new Item.Properties().food(FOTFoodProperties.COOKED_BATTLEGILL, FOTConsumables.COOKED_BATTLEGILL));
    public static final Item COOKED_WRECKER = register("cooked_wrecker", new Item.Properties().food(FOTFoodProperties.COOKED_WRECKER));
    public static final Item COOKED_STORMFISH = register("cooked_stormfish", new Item.Properties().food(FOTFoodProperties.COOKED_STORMFISH));

    public static final Item SPLASHTAIL_BUCKET = register("splashtail_bucket", properties -> new FOTMobBucketItem(FOTEntities.SPLASHTAIL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.SPLASHTAIL_VARIANT, properties.stacksTo(1)));
    public static final Item PONDIE_BUCKET = register("pondie_bucket", properties -> new FOTMobBucketItem(FOTEntities.PONDIE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.PONDIE_VARIANT, properties.stacksTo(1)));
    public static final Item ISLEHOPPER_BUCKET = register("islehopper_bucket", properties -> new FOTMobBucketItem(FOTEntities.ISLEHOPPER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.ISLEHOPPER_VARIANT, properties.stacksTo(1)));
    public static final Item ANCIENTSCALE_BUCKET = register("ancientscale_bucket", properties -> new FOTMobBucketItem(FOTEntities.ANCIENTSCALE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.ANCIENTSCALE_VARIANT, properties.stacksTo(1)));
    public static final Item PLENTIFIN_BUCKET = register("plentifin_bucket", properties -> new FOTMobBucketItem(FOTEntities.PLENTIFIN, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.PLENTIFIN_VARIANT, properties.stacksTo(1)));
    public static final Item WILDSPLASH_BUCKET = register("wildsplash_bucket", properties -> new FOTMobBucketItem(FOTEntities.WILDSPLASH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.WILDSPLASH_VARIANT, properties.stacksTo(1)));
    public static final Item DEVILFISH_BUCKET = register("devilfish_bucket", properties -> new FOTMobBucketItem(FOTEntities.DEVILFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.DEVILFISH_VARIANT, properties.stacksTo(1)));
    public static final Item BATTLEGILL_BUCKET = register("battlegill_bucket", properties -> new FOTMobBucketItem(FOTEntities.BATTLEGILL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.BATTLEGILL_VARIANT, properties.stacksTo(1)));
    public static final Item WRECKER_BUCKET = register("wrecker_bucket", properties -> new FOTMobBucketItem(FOTEntities.WRECKER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.WRECKER_VARIANT, properties.stacksTo(1)));
    public static final Item STORMFISH_BUCKET = register("stormfish_bucket", properties -> new FOTMobBucketItem(FOTEntities.STORMFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.STORMFISH_VARIANT, properties.stacksTo(1)));

    public static final Item SPLASHTAIL_SPAWN_EGG = register("splashtail_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.SPLASHTAIL, properties));
    public static final Item PONDIE_SPAWN_EGG = register("pondie_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.PONDIE, properties));
    public static final Item ISLEHOPPER_SPAWN_EGG = register("islehopper_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.ISLEHOPPER, properties));
    public static final Item ANCIENTSCALE_SPAWN_EGG = register("ancientscale_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.ANCIENTSCALE, properties));
    public static final Item PLENTIFIN_SPAWN_EGG = register("plentifin_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.PLENTIFIN, properties));
    public static final Item WILDSPLASH_SPAWN_EGG = register("wildsplash_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.WILDSPLASH, properties));
    public static final Item DEVILFISH_SPAWN_EGG = register("devilfish_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.DEVILFISH, properties));
    public static final Item BATTLEGILL_SPAWN_EGG = register("battlegill_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.BATTLEGILL, properties));
    public static final Item WRECKER_SPAWN_EGG = register("wrecker_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.WRECKER, properties));
    public static final Item STORMFISH_SPAWN_EGG = register("stormfish_spawn_egg", properties -> new FOTSpawnEggItem(FOTEntities.STORMFISH, properties));

    public static final Item COCONUT = register("coconut", properties -> new BlockItem(FOTBlocks.COCONUT_SAPLING, properties.useItemDescriptionPrefix().food(FOTFoodProperties.COCONUT)));
    public static final Item BANANA = register("banana", new Item.Properties().food(FOTFoodProperties.BANANA));
    public static final Item HALF_PINEAPPLE = register("half_pineapple", new Item.Properties().food(FOTFoodProperties.HALF_PINEAPPLE));
    public static final Item PINEAPPLE = register("pineapple", properties -> new PineappleItem(properties.food(FOTFoodProperties.PINEAPPLE)));
    public static final Item CROWNLESS_PINEAPPLE = register("crownless_pineapple", properties -> new PineappleItem(properties.food(FOTFoodProperties.PINEAPPLE)));
    public static final Item PINEAPPLE_SEEDS = register("pineapple_seeds", properties -> new PineappleBlockItem(false, properties.useItemDescriptionPrefix()));
    public static final Item PINEAPPLE_CROWN = register("pineapple_crown", properties -> new PineappleBlockItem(true, properties.useItemDescriptionPrefix()));
    public static final Item MANGO = register("mango", properties -> new MangoItem(properties.food(FOTFoodProperties.MANGO)));
    public static final Item RAW_MANGO = register("raw_mango", properties -> new MangoItem(properties.food(FOTFoodProperties.RAW_MANGO, FOTConsumables.RAW_MANGO_CONSUMABLE)));
    public static final Item POMEGRANATE = register("pomegranate", properties -> new PomegranateItem(properties.food(FOTFoodProperties.POMEGRANATE)));
    public static final Item POMEGRANATE_SEEDS = register("pomegranate_seeds", properties -> new BlockItem(FOTBlocks.POMEGRANATE_SAPLING, properties.useItemDescriptionPrefix()));

    public static final Item STORMFISH_POTTERY_SHERD = register("stormfish_pottery_sherd");
    public static final Item KRAKEN_POTTERY_SHERD = register("kraken_pottery_sherd");
    public static final Item MEGALODON_POTTERY_SHERD = register("megalodon_pottery_sherd");

    public static final Item COCONUT_BOAT = register("coconut_boat", properties -> new BoatItem(FOTEntities.COCONUT_BOAT, properties.stacksTo(1)));
    public static final Item COCONUT_CHEST_BOAT = register("coconut_chest_boat", properties -> new BoatItem(FOTEntities.COCONUT_CHEST_BOAT, properties.stacksTo(1)));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Item");
    }

    public static Item register(String key)
    {
        return register(key, Item::new, new Item.Properties());
    }

    public static Item register(String key, Item.Properties properties)
    {
        return register(key, Item::new, properties);
    }

    public static Item register(String key, Function<Item.Properties, Item> function)
    {
        return register(key, function, new Item.Properties());
    }

    public static Item register(String key, Function<Item.Properties, Item> function, Item.Properties properties)
    {
        return registerItem(ResourceKey.create(Registries.ITEM, FishOfThieves.id(key)), function, properties);
    }

    public static Item registerBlock(Block block, UnaryOperator<Item.Properties> propertiesModifier)
    {
        return registerBlock(block, (blockx, properties) -> new BlockItem(blockx, propertiesModifier.apply(properties)));
    }

    public static Item registerBlock(Block block, BiFunction<Block, Item.Properties, Item> factory)
    {
        return registerBlock(block, factory, new Item.Properties());
    }

    @SuppressWarnings("deprecation")
    public static Item registerBlock(Block block, BiFunction<Block, Item.Properties, Item> factory, Item.Properties properties)
    {
        return registerItem(blockIdToItemId(block.builtInRegistryHolder().key()), propertiesx -> factory.apply(block, propertiesx), properties.useBlockDescriptionPrefix());
    }

    private static ResourceKey<Item> blockIdToItemId(ResourceKey<Block> blockId)
    {
        return ResourceKey.create(Registries.ITEM, blockId.location());
    }

    public static Item registerBlock(Block block)
    {
        return registerBlock(block, BlockItem::new);
    }

    public static Item registerItem(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties properties)
    {
        var item = factory.apply(properties.setId(key));

        if (item instanceof BlockItem blockItem)
        {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }
}