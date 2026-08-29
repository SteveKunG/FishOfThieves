package com.stevekung.fishofthieves.registry;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.item.*;
import com.stevekung.fishofthieves.registry.variant.*;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public class FOTItems
{
    // Block Item
    public static final Item FISH_BONE = registerBlock(FOTBlocks.FISH_BONE);

    public static final Item OAK_FISH_PLAQUE = registerBlock(FOTBlocks.OAK_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item SPRUCE_FISH_PLAQUE = registerBlock(FOTBlocks.SPRUCE_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item BIRCH_FISH_PLAQUE = registerBlock(FOTBlocks.BIRCH_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item JUNGLE_FISH_PLAQUE = registerBlock(FOTBlocks.JUNGLE_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item ACACIA_FISH_PLAQUE = registerBlock(FOTBlocks.ACACIA_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item DARK_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.DARK_OAK_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item MANGROVE_FISH_PLAQUE = registerBlock(FOTBlocks.MANGROVE_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item CHERRY_FISH_PLAQUE = registerBlock(FOTBlocks.CHERRY_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item PALE_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.PALE_OAK_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item POPLAR_FISH_PLAQUE = registerBlock(FOTBlocks.POPLAR_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item BAMBOO_FISH_PLAQUE = registerBlock(FOTBlocks.BAMBOO_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item CRIMSON_FISH_PLAQUE = registerBlock(FOTBlocks.CRIMSON_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item WARPED_FISH_PLAQUE = registerBlock(FOTBlocks.WARPED_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));

    public static final Item IRON_FRAME_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE);
    public static final Item IRON_FRAME_SPRUCE_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE);
    public static final Item IRON_FRAME_BIRCH_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE);
    public static final Item IRON_FRAME_JUNGLE_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE);
    public static final Item IRON_FRAME_ACACIA_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE);
    public static final Item IRON_FRAME_DARK_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE);
    public static final Item IRON_FRAME_MANGROVE_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE);
    public static final Item IRON_FRAME_CHERRY_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE);
    public static final Item IRON_FRAME_PALE_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_PALE_OAK_FISH_PLAQUE);
    public static final Item IRON_FRAME_POPLAR_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_POPLAR_FISH_PLAQUE);
    public static final Item IRON_FRAME_BAMBOO_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE);
    public static final Item IRON_FRAME_CRIMSON_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE);
    public static final Item IRON_FRAME_WARPED_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE);

    public static final Item COPPER_FRAME_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_OAK_FISH_PLAQUE);
    public static final Item COPPER_FRAME_SPRUCE_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_SPRUCE_FISH_PLAQUE);
    public static final Item COPPER_FRAME_BIRCH_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_BIRCH_FISH_PLAQUE);
    public static final Item COPPER_FRAME_JUNGLE_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_JUNGLE_FISH_PLAQUE);
    public static final Item COPPER_FRAME_ACACIA_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_ACACIA_FISH_PLAQUE);
    public static final Item COPPER_FRAME_DARK_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_DARK_OAK_FISH_PLAQUE);
    public static final Item COPPER_FRAME_MANGROVE_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_MANGROVE_FISH_PLAQUE);
    public static final Item COPPER_FRAME_CHERRY_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_CHERRY_FISH_PLAQUE);
    public static final Item COPPER_FRAME_PALE_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_PALE_OAK_FISH_PLAQUE);
    public static final Item COPPER_FRAME_POPLAR_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_POPLAR_FISH_PLAQUE);
    public static final Item COPPER_FRAME_BAMBOO_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_BAMBOO_FISH_PLAQUE);
    public static final Item COPPER_FRAME_CRIMSON_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_CRIMSON_FISH_PLAQUE);
    public static final Item COPPER_FRAME_WARPED_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_WARPED_FISH_PLAQUE);

    public static final Item GOLDEN_FRAME_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_SPRUCE_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_BIRCH_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_JUNGLE_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_ACACIA_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_MANGROVE_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_CHERRY_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_PALE_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_PALE_OAK_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_POPLAR_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_POPLAR_FISH_PLAQUE);
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
    public static final Item GILDED_PALE_OAK_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_PALE_OAK_FISH_PLAQUE);
    public static final Item GILDED_POPLAR_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_POPLAR_FISH_PLAQUE);
    public static final Item GILDED_BAMBOO_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE);
    public static final Item GILDED_CRIMSON_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE);
    public static final Item GILDED_WARPED_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_WARPED_FISH_PLAQUE);

    public static final Item SMALL_COCONUT_LOG = registerBlock(FOTBlocks.SMALL_COCONUT_LOG, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item SMALL_COCONUT_WOOD = registerBlock(FOTBlocks.SMALL_COCONUT_WOOD, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item MEDIUM_COCONUT_LOG = registerBlock(FOTBlocks.MEDIUM_COCONUT_LOG, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item MEDIUM_COCONUT_WOOD = registerBlock(FOTBlocks.MEDIUM_COCONUT_WOOD, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item COCONUT_LOG = registerBlock(FOTBlocks.COCONUT_LOG, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item COCONUT_WOOD = registerBlock(FOTBlocks.COCONUT_WOOD, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item STRIPPED_COCONUT_LOG = registerBlock(FOTBlocks.STRIPPED_COCONUT_LOG, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item STRIPPED_COCONUT_WOOD = registerBlock(FOTBlocks.STRIPPED_COCONUT_WOOD, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item STRIPPED_MEDIUM_COCONUT_LOG = registerBlock(FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item STRIPPED_MEDIUM_COCONUT_WOOD = registerBlock(FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item STRIPPED_SMALL_COCONUT_LOG = registerBlock(FOTBlocks.STRIPPED_SMALL_COCONUT_LOG, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item STRIPPED_SMALL_COCONUT_WOOD = registerBlock(FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item COCONUT_FRONDS = registerBlock(FOTBlocks.COCONUT_FRONDS, properties -> properties.compostable(FOTNumberProviders.COMPOSTABLE_MEDIUM_HIGH_80));
    public static final Item BANANA_STEM = registerBlock(FOTBlocks.BANANA_STEM, properties -> properties.compostable(FOTNumberProviders.COMPOSTABLE_MEDIUM_HIGH_80).cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item BANANA_LEAVES = registerBlock(FOTBlocks.BANANA_LEAVES, properties -> properties.compostable(FOTNumberProviders.COMPOSTABLE_MEDIUM_HIGH_80));
    public static final Item BANANA_BLOSSOM = registerBlock(FOTBlocks.BANANA_BLOSSOM, properties -> properties.compostable(NumberProviders.COMPOSTABLE_LOW));
    public static final Item UNDERRIPE_BANANA_CLUSTER = registerBlock(FOTBlocks.UNDERRIPE_BANANA_CLUSTER, properties -> properties.compostable(NumberProviders.COMPOSTABLE_LOW_MEDIUM));
    public static final Item BARELY_RIPE_BANANA_CLUSTER = registerBlock(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER, properties -> properties.compostable(NumberProviders.COMPOSTABLE_MEDIUM_HIGH));
    public static final Item RIPE_BANANA_CLUSTER = registerBlock(FOTBlocks.RIPE_BANANA_CLUSTER, properties -> properties.compostable(NumberProviders.COMPOSTABLE_MEDIUM_HIGH));
    public static final Item RIPE_PINEAPPLE_BLOCK = registerBlock(FOTBlocks.RIPE_PINEAPPLE_BLOCK, properties -> properties.component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD).setSwappable(false).build()).compostable(FOTNumberProviders.COMPOSTABLE_MEDIUM_HIGH_75));
    public static final Item CROWNLESS_RIPE_PINEAPPLE_BLOCK = registerBlock(FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK, properties -> properties.component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD).setSwappable(false).build()).compostable(FOTNumberProviders.COMPOSTABLE_MEDIUM_70));
    public static final Item UNDERRIPE_PINEAPPLE_BLOCK = registerBlock(FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK, properties -> properties.component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD).setSwappable(false).build()).compostable(FOTNumberProviders.COMPOSTABLE_MEDIUM_60));
    public static final Item MANGO_LEAVES = registerBlock(FOTBlocks.MANGO_LEAVES, properties -> properties.compostable(NumberProviders.COMPOSTABLE_LOW));
    public static final Item TALL_POMEGRANATE_PLANT = register("tall_pomegranate_plant", properties -> new DoubleHighBlockItem(FOTBlocks.TALL_POMEGRANATE_PLANT, properties.useBlockDescriptionPrefix().compostable(FOTNumberProviders.COMPOSTABLE_LOW_40)));
    public static final Item PRISMARIZED_LOG = registerBlock(FOTBlocks.PRISMARIZED_LOG, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item BUDDING_PRISMARIZED_LOG = registerBlock(FOTBlocks.BUDDING_PRISMARIZED_LOG, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));

    public static final Item PINK_PLUMERIA = registerBlock(FOTBlocks.PINK_PLUMERIA, properties -> properties.compostable(FOTNumberProviders.COMPOSTABLE_MEDIUM_60));
    public static final Item LIGHT_BLUE_PLUMERIA = registerBlock(FOTBlocks.LIGHT_BLUE_PLUMERIA, properties -> properties.compostable(FOTNumberProviders.COMPOSTABLE_MEDIUM_60));
    public static final Item WHITE_PLUMERIA = registerBlock(FOTBlocks.WHITE_PLUMERIA, properties -> properties.compostable(FOTNumberProviders.COMPOSTABLE_MEDIUM_60));
    public static final Item BANANA_SHOOTS = registerBlock(FOTBlocks.BANANA_SHOOTS, properties -> properties.compostable(FOTNumberProviders.COMPOSTABLE_VERY_LOW_25).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS));
    public static final Item MANGO_PIT = registerBlock(FOTBlocks.MANGO_PIT, properties -> properties.useItemDescriptionPrefix().compostable(FOTNumberProviders.COMPOSTABLE_VERY_LOW_20).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS));
    public static final Item MANGO_SAPLING = registerBlock(FOTBlocks.MANGO_SAPLING, properties -> properties.compostable(FOTNumberProviders.COMPOSTABLE_VERY_LOW_25).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS));
    public static final Item POMEGRANATE_PLANT = registerBlock(FOTBlocks.POMEGRANATE_PLANT, properties -> properties.compostable(FOTNumberProviders.COMPOSTABLE_VERY_LOW_25).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS));
    public static final Item TROPICAL_RED_FERN = registerBlock(FOTBlocks.TROPICAL_RED_FERN, properties -> properties.compostable(FOTNumberProviders.COMPOSTABLE_LOW_40));
    public static final Item TROPICAL_MONSTERA = registerBlock(FOTBlocks.TROPICAL_MONSTERA, properties -> properties.compostable(NumberProviders.COMPOSTABLE_LOW));

    public static final Item COCONUT_PLANKS = registerBlock(FOTBlocks.COCONUT_PLANKS, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item COCONUT_BUTTON = registerBlock(FOTBlocks.COCONUT_BUTTON, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_EXTRA_SMALL));
    public static final Item COCONUT_FENCE = registerBlock(FOTBlocks.COCONUT_FENCE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item COCONUT_FENCE_GATE = registerBlock(FOTBlocks.COCONUT_FENCE_GATE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item COCONUT_PRESSURE_PLATE = registerBlock(FOTBlocks.COCONUT_PRESSURE_PLATE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item COCONUT_SLAB = registerBlock(FOTBlocks.COCONUT_SLAB, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item COCONUT_STAIRS = registerBlock(FOTBlocks.COCONUT_STAIRS, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item COCONUT_TRAPDOOR = registerBlock(FOTBlocks.COCONUT_TRAPDOOR, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item COCONUT_DOOR = register("coconut_door", properties -> new DoubleHighBlockItem(FOTBlocks.COCONUT_DOOR, properties.useBlockDescriptionPrefix().cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)));
    public static final Item COCONUT_SIGN = register("coconut_sign", properties -> new StandingAndWallBlockItem(FOTBlocks.COCONUT_SIGN, FOTBlocks.COCONUT_WALL_SIGN, Direction.DOWN, properties.stacksTo(16).useBlockDescriptionPrefix().signText().cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_LARGE)));
    public static final Item COCONUT_HANGING_SIGN = register("coconut_hanging_sign", properties -> new FOTHangingSignItem(FOTBlocks.COCONUT_HANGING_SIGN, FOTBlocks.COCONUT_WALL_HANGING_SIGN, properties.stacksTo(16).useBlockDescriptionPrefix().cookingFuel(NumberProviders.COOKING_TIME_HANGING_SIGNS).signText()));

    public static final Item COCONUT_FISH_PLAQUE = registerBlock(FOTBlocks.COCONUT_FISH_PLAQUE, properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));
    public static final Item IRON_FRAME_COCONUT_FISH_PLAQUE = registerBlock(FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE);
    public static final Item COPPER_FRAME_COCONUT_FISH_PLAQUE = registerBlock(FOTBlocks.COPPER_FRAME_COCONUT_FISH_PLAQUE);
    public static final Item GOLDEN_FRAME_COCONUT_FISH_PLAQUE = registerBlock(FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE);
    public static final Item GILDED_COCONUT_FISH_PLAQUE = registerBlock(FOTBlocks.GILDED_COCONUT_FISH_PLAQUE);
    public static final Item COCONUT_SHELF = registerBlock(FOTBlocks.COCONUT_SHELF, properties -> properties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS));

    // Item
    public static final Item EARTHWORMS = register("earthworms", properties -> new FOTWormItem(properties.food(FOTFoodProperties.WORMS, FOTConsumables.WORMS).compostable(FOTNumberProviders.COMPOSTABLE_LOW_40)));
    public static final Item GRUBS = register("grubs", properties -> new FOTWormItem(properties.food(FOTFoodProperties.WORMS, FOTConsumables.WORMS).compostable(FOTNumberProviders.COMPOSTABLE_LOW_40)));
    public static final Item LEECHES = register("leeches", properties -> new FOTWormItem(properties.food(FOTFoodProperties.WORMS, FOTConsumables.WORMS).compostable(FOTNumberProviders.COMPOSTABLE_LOW_40)));

    public static final Item SPLASHTAIL = register("splashtail", properties -> new FOTItem(properties.food(FOTFoodProperties.SPLASHTAIL).component(DataComponents.CUSTOM_DATA, FOTItem.createDefaultCustomData(FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants.RUBY)), FOTEntities.SPLASHTAIL, FOTRegistries.SPLASHTAIL_VARIANT));
    public static final Item PONDIE = register("pondie", properties -> new FOTItem(properties.food(FOTFoodProperties.PONDIE).component(DataComponents.CUSTOM_DATA, FOTItem.createDefaultCustomData(FOTRegistries.PONDIE_VARIANT, PondieVariants.CHARCOAL)), FOTEntities.PONDIE, FOTRegistries.PONDIE_VARIANT));
    public static final Item ISLEHOPPER = register("islehopper", properties -> new FOTItem(properties.food(FOTFoodProperties.ISLEHOPPER).component(DataComponents.CUSTOM_DATA, FOTItem.createDefaultCustomData(FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.STONE)), FOTEntities.ISLEHOPPER, FOTRegistries.ISLEHOPPER_VARIANT));
    public static final Item ANCIENTSCALE = register("ancientscale", properties -> new FOTItem(properties.food(FOTFoodProperties.ANCIENTSCALE).component(DataComponents.CUSTOM_DATA, FOTItem.createDefaultCustomData(FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants.ALMOND)), FOTEntities.ANCIENTSCALE, FOTRegistries.ANCIENTSCALE_VARIANT));
    public static final Item PLENTIFIN = register("plentifin", properties -> new FOTItem(properties.food(FOTFoodProperties.PLENTIFIN).component(DataComponents.CUSTOM_DATA, FOTItem.createDefaultCustomData(FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.OLIVE)), FOTEntities.PLENTIFIN, FOTRegistries.PLENTIFIN_VARIANT));
    public static final Item WILDSPLASH = register("wildsplash", properties -> new FOTItem(properties.food(FOTFoodProperties.WILDSPLASH).component(DataComponents.CUSTOM_DATA, FOTItem.createDefaultCustomData(FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.RUSSET)), FOTEntities.WILDSPLASH, FOTRegistries.WILDSPLASH_VARIANT));
    public static final Item DEVILFISH = register("devilfish", properties -> new FOTItem(properties.food(FOTFoodProperties.DEVILFISH, FOTConsumables.DEVILFISH).component(DataComponents.CUSTOM_DATA, FOTItem.createDefaultCustomData(FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants.ASHEN)), FOTEntities.DEVILFISH, FOTRegistries.DEVILFISH_VARIANT));
    public static final Item BATTLEGILL = register("battlegill", properties -> new FOTItem(properties.food(FOTFoodProperties.BATTLEGILL).component(DataComponents.CUSTOM_DATA, FOTItem.createDefaultCustomData(FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants.JADE)), FOTEntities.BATTLEGILL, FOTRegistries.BATTLEGILL_VARIANT));
    public static final Item WRECKER = register("wrecker", properties -> new FOTItem(properties.food(FOTFoodProperties.WRECKER).component(DataComponents.CUSTOM_DATA, FOTItem.createDefaultCustomData(FOTRegistries.WRECKER_VARIANT, WreckerVariants.ROSE)), FOTEntities.WRECKER, FOTRegistries.WRECKER_VARIANT));
    public static final Item STORMFISH = register("stormfish", properties -> new FOTItem(properties.food(FOTFoodProperties.STORMFISH).component(DataComponents.CUSTOM_DATA, FOTItem.createDefaultCustomData(FOTRegistries.STORMFISH_VARIANT, StormfishVariants.ANCIENT)), FOTEntities.STORMFISH, FOTRegistries.STORMFISH_VARIANT));

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

    public static final Item SPLASHTAIL_BUCKET = register("splashtail_bucket", properties -> new FOTMobBucketItem<>(FOTEntities.SPLASHTAIL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.SPLASHTAIL_VARIANT, FOTDataComponentTypes.SPLASHTAIL_VARIANT, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, FOTItem.createDefaultCustomData(FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants.RUBY))));
    public static final Item PONDIE_BUCKET = register("pondie_bucket", properties -> new FOTMobBucketItem<>(FOTEntities.PONDIE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.PONDIE_VARIANT, FOTDataComponentTypes.PONDIE_VARIANT, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, FOTItem.createDefaultCustomData(FOTRegistries.PONDIE_VARIANT, PondieVariants.CHARCOAL))));
    public static final Item ISLEHOPPER_BUCKET = register("islehopper_bucket", properties -> new FOTMobBucketItem<>(FOTEntities.ISLEHOPPER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.ISLEHOPPER_VARIANT, FOTDataComponentTypes.ISLEHOPPER_VARIANT, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, FOTItem.createDefaultCustomData(FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.STONE))));
    public static final Item ANCIENTSCALE_BUCKET = register("ancientscale_bucket", properties -> new FOTMobBucketItem<>(FOTEntities.ANCIENTSCALE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.ANCIENTSCALE_VARIANT, FOTDataComponentTypes.ANCIENTSCALE_VARIANT, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, FOTItem.createDefaultCustomData(FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants.ALMOND))));
    public static final Item PLENTIFIN_BUCKET = register("plentifin_bucket", properties -> new FOTMobBucketItem<>(FOTEntities.PLENTIFIN, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.PLENTIFIN_VARIANT, FOTDataComponentTypes.PLENTIFIN_VARIANT, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, FOTItem.createDefaultCustomData(FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.OLIVE))));
    public static final Item WILDSPLASH_BUCKET = register("wildsplash_bucket", properties -> new FOTMobBucketItem<>(FOTEntities.WILDSPLASH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.WILDSPLASH_VARIANT, FOTDataComponentTypes.WILDSPLASH_VARIANT, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, FOTItem.createDefaultCustomData(FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.RUSSET))));
    public static final Item DEVILFISH_BUCKET = register("devilfish_bucket", properties -> new FOTMobBucketItem<>(FOTEntities.DEVILFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.DEVILFISH_VARIANT, FOTDataComponentTypes.DEVILFISH_VARIANT, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, FOTItem.createDefaultCustomData(FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants.ASHEN))));
    public static final Item BATTLEGILL_BUCKET = register("battlegill_bucket", properties -> new FOTMobBucketItem<>(FOTEntities.BATTLEGILL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.BATTLEGILL_VARIANT, FOTDataComponentTypes.BATTLEGILL_VARIANT, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, FOTItem.createDefaultCustomData(FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants.JADE))));
    public static final Item WRECKER_BUCKET = register("wrecker_bucket", properties -> new FOTMobBucketItem<>(FOTEntities.WRECKER, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.WRECKER_VARIANT, FOTDataComponentTypes.WRECKER_VARIANT, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, FOTItem.createDefaultCustomData(FOTRegistries.WRECKER_VARIANT, WreckerVariants.ROSE))));
    public static final Item STORMFISH_BUCKET = register("stormfish_bucket", properties -> new FOTMobBucketItem<>(FOTEntities.STORMFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, FOTRegistries.STORMFISH_VARIANT, FOTDataComponentTypes.STORMFISH_VARIANT, properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, FOTItem.createDefaultCustomData(FOTRegistries.STORMFISH_VARIANT, StormfishVariants.ANCIENT))));

    public static final Item SPLASHTAIL_SPAWN_EGG = register("splashtail_spawn_egg", properties -> new FOTSpawnEggItem<>(FOTRegistries.SPLASHTAIL_VARIANT, FOTDataComponentTypes.SPLASHTAIL_VARIANT, properties.spawnEgg(FOTEntities.SPLASHTAIL)));
    public static final Item PONDIE_SPAWN_EGG = register("pondie_spawn_egg", properties -> new FOTSpawnEggItem<>(FOTRegistries.PONDIE_VARIANT, FOTDataComponentTypes.PONDIE_VARIANT, properties.spawnEgg(FOTEntities.PONDIE)));
    public static final Item ISLEHOPPER_SPAWN_EGG = register("islehopper_spawn_egg", properties -> new FOTSpawnEggItem<>(FOTRegistries.ISLEHOPPER_VARIANT, FOTDataComponentTypes.ISLEHOPPER_VARIANT, properties.spawnEgg(FOTEntities.ISLEHOPPER)));
    public static final Item ANCIENTSCALE_SPAWN_EGG = register("ancientscale_spawn_egg", properties -> new FOTSpawnEggItem<>(FOTRegistries.ANCIENTSCALE_VARIANT, FOTDataComponentTypes.ANCIENTSCALE_VARIANT, properties.spawnEgg(FOTEntities.ANCIENTSCALE)));
    public static final Item PLENTIFIN_SPAWN_EGG = register("plentifin_spawn_egg", properties -> new FOTSpawnEggItem<>(FOTRegistries.PLENTIFIN_VARIANT, FOTDataComponentTypes.PLENTIFIN_VARIANT, properties.spawnEgg(FOTEntities.PLENTIFIN)));
    public static final Item WILDSPLASH_SPAWN_EGG = register("wildsplash_spawn_egg", properties -> new FOTSpawnEggItem<>(FOTRegistries.WILDSPLASH_VARIANT, FOTDataComponentTypes.WILDSPLASH_VARIANT, properties.spawnEgg(FOTEntities.WILDSPLASH)));
    public static final Item DEVILFISH_SPAWN_EGG = register("devilfish_spawn_egg", properties -> new FOTSpawnEggItem<>(FOTRegistries.DEVILFISH_VARIANT, FOTDataComponentTypes.DEVILFISH_VARIANT, properties.spawnEgg(FOTEntities.DEVILFISH)));
    public static final Item BATTLEGILL_SPAWN_EGG = register("battlegill_spawn_egg", properties -> new FOTSpawnEggItem<>(FOTRegistries.BATTLEGILL_VARIANT, FOTDataComponentTypes.BATTLEGILL_VARIANT, properties.spawnEgg(FOTEntities.BATTLEGILL)));
    public static final Item WRECKER_SPAWN_EGG = register("wrecker_spawn_egg", properties -> new FOTSpawnEggItem<>(FOTRegistries.WRECKER_VARIANT, FOTDataComponentTypes.WRECKER_VARIANT, properties.spawnEgg(FOTEntities.WRECKER)));
    public static final Item STORMFISH_SPAWN_EGG = register("stormfish_spawn_egg", properties -> new FOTSpawnEggItem<>(FOTRegistries.STORMFISH_VARIANT, FOTDataComponentTypes.STORMFISH_VARIANT, properties.spawnEgg(FOTEntities.STORMFISH)));

    public static final Item COCONUT = register("coconut", properties -> new BlockItem(FOTBlocks.COCONUT_SAPLING, properties.useItemDescriptionPrefix().food(FOTFoodProperties.COCONUT).compostable(NumberProviders.COMPOSTABLE_LOW_MEDIUM)));
    public static final Item BANANA = register("banana", new Item.Properties().food(FOTFoodProperties.BANANA).compostable(NumberProviders.COMPOSTABLE_LOW));
    public static final Item HALF_PINEAPPLE = register("half_pineapple", new Item.Properties().food(FOTFoodProperties.HALF_PINEAPPLE).compostable(FOTNumberProviders.COMPOSTABLE_LOW_35));
    public static final Item PINEAPPLE = register("pineapple", properties -> new ReturnedOnConsumeItem(properties.food(FOTFoodProperties.PINEAPPLE).compostable(FOTNumberProviders.COMPOSTABLE_MEDIUM_70), () -> FOTItems.HALF_PINEAPPLE));
    public static final Item CROWNLESS_PINEAPPLE = register("crownless_pineapple", properties -> new ReturnedOnConsumeItem(properties.food(FOTFoodProperties.PINEAPPLE).compostable(NumberProviders.COMPOSTABLE_MEDIUM), () -> FOTItems.HALF_PINEAPPLE));
    public static final Item PINEAPPLE_SEEDS = register("pineapple_seeds", properties -> new PineappleBlockItem(false, properties.useItemDescriptionPrefix().compostable(FOTNumberProviders.COMPOSTABLE_VERY_LOW_10)));
    public static final Item PINEAPPLE_CROWN = register("pineapple_crown", properties -> new PineappleBlockItem(true, properties.useItemDescriptionPrefix().compostable(FOTNumberProviders.COMPOSTABLE_VERY_LOW_15)));
    public static final Item MANGO = register("mango", properties -> new ReturnedOnConsumeItem(properties.food(FOTFoodProperties.MANGO).compostable(NumberProviders.COMPOSTABLE_LOW), () -> FOTItems.MANGO_PIT, 0.2f));
    public static final Item RAW_MANGO = register("raw_mango", properties -> new ReturnedOnConsumeItem(properties.food(FOTFoodProperties.RAW_MANGO, FOTConsumables.RAW_MANGO_CONSUMABLE).compostable(NumberProviders.COMPOSTABLE_LOW), () -> FOTItems.MANGO_PIT, 0.2f));
    public static final Item POMEGRANATE = register("pomegranate", properties -> new ReturnedOnConsumeItem(properties.food(FOTFoodProperties.POMEGRANATE).compostable(FOTNumberProviders.COMPOSTABLE_VERY_LOW_20), () -> FOTItems.POMEGRANATE_SEEDS, 0.6f));
    public static final Item POMEGRANATE_SEEDS = register("pomegranate_seeds", properties -> new BlockItem(FOTBlocks.POMEGRANATE_SAPLING, properties.useItemDescriptionPrefix().compostable(FOTNumberProviders.COMPOSTABLE_VERY_LOW_10)));
    public static final Item GUARDIAN_FRUIT = register("guardian_fruit", properties -> new GuardianFruitItem(properties.food(FOTFoodProperties.GUARDIAN_FRUIT, FOTConsumables.GUARDIAN_FRUIT).compostable(NumberProviders.COMPOSTABLE_LOW)));

    public static final Item STORMFISH_POTTERY_SHERD = register("stormfish_pottery_sherd", new Item.Properties().rarity(Rarity.UNCOMMON).potPattern(FOTDecoratedPotPatterns.STORMFISH));
    public static final Item KRAKEN_POTTERY_SHERD = register("kraken_pottery_sherd", new Item.Properties().rarity(Rarity.UNCOMMON).potPattern(FOTDecoratedPotPatterns.KRAKEN));
    public static final Item MEGALODON_POTTERY_SHERD = register("megalodon_pottery_sherd", new Item.Properties().rarity(Rarity.UNCOMMON).potPattern(FOTDecoratedPotPatterns.MEGALODON));
    public static final Item GREAT_MOUTH_POTTERY_SHERD = register("great_mouth_pottery_sherd", new Item.Properties().rarity(Rarity.UNCOMMON).potPattern(FOTDecoratedPotPatterns.GREAT_MOUTH));

    public static final Item COCONUT_BOAT = register("coconut_boat", properties -> new BoatItem(FOTEntities.COCONUT_BOAT, properties.stacksTo(1).cookingFuel(NumberProviders.COOKING_TIME_BOATS)));
    public static final Item COCONUT_CHEST_BOAT = register("coconut_chest_boat", properties -> new BoatItem(FOTEntities.COCONUT_CHEST_BOAT, properties.stacksTo(1).cookingFuel(NumberProviders.COOKING_TIME_BOATS)));

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
        return ResourceKey.create(Registries.ITEM, blockId.identifier());
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