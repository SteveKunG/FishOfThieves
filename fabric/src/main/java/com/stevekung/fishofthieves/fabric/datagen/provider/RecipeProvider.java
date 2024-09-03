package com.stevekung.fishofthieves.fabric.datagen.provider;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class RecipeProvider extends FabricRecipeProvider
{
    public RecipeProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput);
    }

    @Override
    public void buildRecipes(RecipeOutput output)
    {
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