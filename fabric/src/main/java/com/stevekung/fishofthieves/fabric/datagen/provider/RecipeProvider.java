package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBlockFamilies;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class RecipeProvider extends FabricRecipeProvider
{
    public RecipeProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider);
    }

    @Override
    public void buildRecipes(RecipeOutput output)
    {
        generateForFOTBlockFamilies(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 4).requires(FOTBlocks.FISH_BONE).group("bonemeal").unlockedBy(getHasName(FOTBlocks.FISH_BONE), has(FOTBlocks.FISH_BONE)).save(output, FishOfThieves.MOD_RESOURCES + "bonemeals_from_fish_bone");

        addWoodenFishPlaqueRecipe(FOTBlocks.OAK_FISH_PLAQUE, Items.OAK_PLANKS, output);
        addWoodenFishPlaqueRecipe(FOTBlocks.SPRUCE_FISH_PLAQUE, Items.SPRUCE_PLANKS, output);
        addWoodenFishPlaqueRecipe(FOTBlocks.BIRCH_FISH_PLAQUE, Items.BIRCH_PLANKS, output);
        addWoodenFishPlaqueRecipe(FOTBlocks.JUNGLE_FISH_PLAQUE, Items.JUNGLE_PLANKS, output);
        addWoodenFishPlaqueRecipe(FOTBlocks.ACACIA_FISH_PLAQUE, Items.ACACIA_PLANKS, output);
        addWoodenFishPlaqueRecipe(FOTBlocks.DARK_OAK_FISH_PLAQUE, Items.DARK_OAK_PLANKS, output);
        addWoodenFishPlaqueRecipe(FOTBlocks.MANGROVE_FISH_PLAQUE, Items.MANGROVE_PLANKS, output);
        addWoodenFishPlaqueRecipe(FOTBlocks.CHERRY_FISH_PLAQUE, Items.CHERRY_PLANKS, output);
        addWoodenFishPlaqueRecipe(FOTBlocks.BAMBOO_FISH_PLAQUE, Items.BAMBOO_PLANKS, output);
        addWoodenFishPlaqueRecipe(FOTBlocks.CRIMSON_FISH_PLAQUE, Items.CRIMSON_PLANKS, output);
        addWoodenFishPlaqueRecipe(FOTBlocks.WARPED_FISH_PLAQUE, Items.WARPED_PLANKS, output);
        addWoodenFishPlaqueRecipe(FOTBlocks.COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_PLANKS, output);

        addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE, output);
        addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, output);
        addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, output);
        addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, output);
        addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, output);
        addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, output);
        addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, output);
        addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE, output);
        addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE, output);
        addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, output);
        addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE, output);
        addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_FISH_PLAQUE, output);

        addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE, output);
        addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, output);
        addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, output);
        addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, output);
        addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, output);
        addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, output);
        addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, output);
        addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE, output);
        addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE, output);
        addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, output);
        addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE, output);
        addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_FISH_PLAQUE, output);

        addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE, output);
        addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, output);
        addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, output);
        addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, output);
        addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, output);
        addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, output);
        addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, output);
        addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE, output);
        addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE, output);
        addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, output);
        addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE, output);
        addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_FISH_PLAQUE, output);

        addGildedFishPlaqueRecipe(FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE, output);
        addGildedFishPlaqueRecipe(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, output);
        addGildedFishPlaqueRecipe(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, output);
        addGildedFishPlaqueRecipe(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, output);
        addGildedFishPlaqueRecipe(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, output);
        addGildedFishPlaqueRecipe(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, output);
        addGildedFishPlaqueRecipe(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, output);
        addGildedFishPlaqueRecipe(FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE, output);
        addGildedFishPlaqueRecipe(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE, output);
        addGildedFishPlaqueRecipe(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, output);
        addGildedFishPlaqueRecipe(FOTBlocks.GILDED_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE, output);
        addGildedFishPlaqueRecipe(FOTBlocks.GILDED_COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_FISH_PLAQUE, output);

        addCookingRecipes(output, 0.3F, FOTItems.SPLASHTAIL, FOTItems.COOKED_SPLASHTAIL);
        addCookingRecipes(output, 0.25F, FOTItems.PONDIE, FOTItems.COOKED_PONDIE);
        addCookingRecipes(output, 0.3F, FOTItems.ISLEHOPPER, FOTItems.COOKED_ISLEHOPPER);
        addCookingRecipes(output, 0.3F, FOTItems.ANCIENTSCALE, FOTItems.COOKED_ANCIENTSCALE);
        addCookingRecipes(output, 0.3F, FOTItems.PLENTIFIN, FOTItems.COOKED_PLENTIFIN);
        addCookingRecipes(output, 0.4F, FOTItems.WILDSPLASH, FOTItems.COOKED_WILDSPLASH);
        addCookingRecipes(output, 0.4F, FOTItems.DEVILFISH, FOTItems.COOKED_DEVILFISH);
        addCookingRecipes(output, 0.45F, FOTItems.BATTLEGILL, FOTItems.COOKED_BATTLEGILL);
        addCookingRecipes(output, 0.5F, FOTItems.WRECKER, FOTItems.COOKED_WRECKER);
        addCookingRecipes(output, 0.6F, FOTItems.STORMFISH, FOTItems.COOKED_STORMFISH);

        oneToOneConversionRecipe(output, Items.PINK_DYE, FOTBlocks.PINK_PLUMERIA, "pink_dye");
        oneToOneConversionRecipe(output, Items.LIGHT_BLUE_DYE, FOTBlocks.LIGHT_BLUE_PLUMERIA, "light_blue_dye");
        oneToOneConversionRecipe(output, Items.WHITE_DYE, FOTBlocks.WHITE_PLUMERIA, "white_dye");
        oneToOneConversionRecipe(output, Items.PURPLE_DYE, FOTBlocks.BANANA_BLOSSOM, "purple_dye");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FOTItems.PINEAPPLE_SEEDS, 4).requires(Ingredient.of(FOTItems.PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE)).unlockedBy(getHasName(FOTItems.PINEAPPLE), inventoryTrigger(ItemPredicate.Builder.item().of(FOTItems.PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE).build())).save(output, getConversionRecipeName(FOTItems.PINEAPPLE_SEEDS, FOTItems.PINEAPPLE));
        woodFromLogs(output, FOTBlocks.COCONUT_WOOD, FOTBlocks.COCONUT_LOG);
        woodFromLogs(output, FOTBlocks.SMALL_COCONUT_WOOD, FOTBlocks.SMALL_COCONUT_LOG);
        woodFromLogs(output, FOTBlocks.MEDIUM_COCONUT_WOOD, FOTBlocks.MEDIUM_COCONUT_LOG);
        woodFromLogs(output, FOTBlocks.STRIPPED_COCONUT_WOOD, FOTBlocks.STRIPPED_COCONUT_LOG);
        woodFromLogs(output, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD, FOTBlocks.STRIPPED_SMALL_COCONUT_LOG);
        woodFromLogs(output, FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD, FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG);
        planksFromLogs(output, FOTBlocks.COCONUT_PLANKS, FOTTags.Items.COCONUT_LOGS, 4);
        woodenBoat(output, FOTItems.COCONUT_BOAT, FOTBlocks.COCONUT_PLANKS);
        chestBoat(output, FOTItems.COCONUT_CHEST_BOAT, FOTItems.COCONUT_BOAT);
        hangingSign(output, FOTBlocks.COCONUT_HANGING_SIGN, FOTBlocks.STRIPPED_COCONUT_LOG);
    }

    private static void generateForFOTBlockFamilies(RecipeOutput output)
    {
        FOTBlockFamilies.getAllFamilies().forEach(blockFamily -> generateRecipes(output, blockFamily, FeatureFlagSet.of(FeatureFlags.VANILLA)));
    }

    private static void addWoodenFishPlaqueRecipe(Block block, ItemLike baseMaterial, RecipeOutput output)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 6).define('P', baseMaterial).define('F', Items.ITEM_FRAME).pattern("PPP").pattern("PFP").pattern("PPP").group("wooden_fish_plaque").unlockedBy(getHasName(Items.ITEM_FRAME), has(Items.ITEM_FRAME)).save(output);
    }

    private static void addIronFrameFishPlaqueRecipe(Block block, ItemLike fishPlaque, RecipeOutput output)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block).define('N', Items.IRON_NUGGET).define('F', fishPlaque).pattern("NNN").pattern("NFN").pattern("NNN").group("iron_frame_fish_plaque").unlockedBy(getHasName(fishPlaque), has(fishPlaque)).save(output);
    }

    private static void addGoldenFrameFishPlaqueRecipe(Block block, ItemLike fishPlaque, RecipeOutput output)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block).define('G', Items.GOLD_NUGGET).define('F', fishPlaque).pattern("GGG").pattern("GFG").pattern("GGG").group("golden_frame_fish_plaque").unlockedBy(getHasName(fishPlaque), has(fishPlaque)).save(output);
    }

    // TODO Switch to use copper nugget and change recipe in 1.21.10
    private static void addCopperFrameFishPlaqueRecipe(Block block, ItemLike fishPlaque, RecipeOutput output)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block).define('N', Items.COPPER_INGOT).define('F', fishPlaque).pattern(" N ").pattern("NFN").pattern(" N ").group("copper_frame_fish_plaque").unlockedBy(getHasName(fishPlaque), has(fishPlaque)).save(output);
    }

    private static void addGildedFishPlaqueRecipe(Block block, ItemLike fishPlaque, RecipeOutput output)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block).define('G', Items.GOLD_INGOT).define('E', Items.EMERALD).define('R', Items.REDSTONE).define('F', fishPlaque).pattern("GEG").pattern("RFR").pattern("GEG").group("gilded_fish_plaque").unlockedBy(getHasName(fishPlaque), has(fishPlaque)).save(output);
    }

    private static void addCookingRecipes(RecipeOutput output, float xp, ItemLike rawFood, ItemLike cookedFood)
    {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(rawFood), RecipeCategory.FOOD, cookedFood, xp, 200).unlockedBy(getHasName(rawFood), has(rawFood)).save(output);
        simpleCookingRecipe(output, "smoking", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100, rawFood, cookedFood, xp);
        simpleCookingRecipe(output, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600, rawFood, cookedFood, xp);
    }
}