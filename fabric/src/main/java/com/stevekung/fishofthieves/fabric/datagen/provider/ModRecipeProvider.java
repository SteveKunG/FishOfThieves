package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class ModRecipeProvider extends RecipeProvider
{
    protected ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput)
    {
        super(provider, recipeOutput);
    }

    @Override
    public void buildRecipes()
    {
        this.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 4).requires(FOTBlocks.FISH_BONE).group("bonemeal").unlockedBy(getHasName(FOTBlocks.FISH_BONE), this.has(FOTBlocks.FISH_BONE)).save(this.output, FishOfThieves.MOD_RESOURCES + "bonemeals_from_fish_bone");

        this.addWoodenFishPlaqueRecipe(FOTBlocks.OAK_FISH_PLAQUE, Items.OAK_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.SPRUCE_FISH_PLAQUE, Items.SPRUCE_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.BIRCH_FISH_PLAQUE, Items.BIRCH_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.JUNGLE_FISH_PLAQUE, Items.JUNGLE_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.ACACIA_FISH_PLAQUE, Items.ACACIA_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.DARK_OAK_FISH_PLAQUE, Items.DARK_OAK_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.MANGROVE_FISH_PLAQUE, Items.MANGROVE_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.CHERRY_FISH_PLAQUE, Items.CHERRY_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.BAMBOO_FISH_PLAQUE, Items.BAMBOO_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.CRIMSON_FISH_PLAQUE, Items.CRIMSON_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.WARPED_FISH_PLAQUE, Items.WARPED_PLANKS);

        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE);

        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE);

        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE);

        this.addCookingRecipes(this.output, 0.3F, FOTItems.SPLASHTAIL, FOTItems.COOKED_SPLASHTAIL);
        this.addCookingRecipes(this.output, 0.25F, FOTItems.PONDIE, FOTItems.COOKED_PONDIE);
        this.addCookingRecipes(this.output, 0.3F, FOTItems.ISLEHOPPER, FOTItems.COOKED_ISLEHOPPER);
        this.addCookingRecipes(this.output, 0.3F, FOTItems.ANCIENTSCALE, FOTItems.COOKED_ANCIENTSCALE);
        this.addCookingRecipes(this.output, 0.3F, FOTItems.PLENTIFIN, FOTItems.COOKED_PLENTIFIN);
        this.addCookingRecipes(this.output, 0.4F, FOTItems.WILDSPLASH, FOTItems.COOKED_WILDSPLASH);
        this.addCookingRecipes(this.output, 0.4F, FOTItems.DEVILFISH, FOTItems.COOKED_DEVILFISH);
        this.addCookingRecipes(this.output, 0.45F, FOTItems.BATTLEGILL, FOTItems.COOKED_BATTLEGILL);
        this.addCookingRecipes(this.output, 0.5F, FOTItems.WRECKER, FOTItems.COOKED_WRECKER);
        this.addCookingRecipes(this.output, 0.6F, FOTItems.STORMFISH, FOTItems.COOKED_STORMFISH);
    }

    private void addWoodenFishPlaqueRecipe(Block block, ItemLike baseMaterial)
    {
        this.shaped(RecipeCategory.DECORATIONS, block, 6).define('P', baseMaterial).define('F', Items.ITEM_FRAME).pattern("PPP").pattern("PFP").pattern("PPP").group("wooden_fish_plaque").unlockedBy(getHasName(Items.ITEM_FRAME), this.has(Items.ITEM_FRAME)).save(this.output);
    }

    private void addIronFrameFishPlaqueRecipe(Block block, ItemLike fishPlaque)
    {
        this.shaped(RecipeCategory.DECORATIONS, block).define('N', Items.IRON_NUGGET).define('F', fishPlaque).pattern("NNN").pattern("NFN").pattern("NNN").group("iron_frame_fish_plaque").unlockedBy(getHasName(fishPlaque), this.has(fishPlaque)).save(this.output);
    }

    private void addGoldenFrameFishPlaqueRecipe(Block block, ItemLike fishPlaque)
    {
        this.shaped(RecipeCategory.DECORATIONS, block).define('G', Items.GOLD_NUGGET).define('F', fishPlaque).pattern("GGG").pattern("GFG").pattern("GGG").group("golden_frame_fish_plaque").unlockedBy(getHasName(fishPlaque), this.has(fishPlaque)).save(this.output);
    }

    private void addGildedFishPlaqueRecipe(Block block, ItemLike fishPlaque)
    {
        this.shaped(RecipeCategory.DECORATIONS, block).define('G', Items.GOLD_INGOT).define('E', Items.EMERALD).define('R', Items.REDSTONE).define('F', fishPlaque).pattern("GEG").pattern("RFR").pattern("GEG").group("gilded_fish_plaque").unlockedBy(getHasName(fishPlaque), this.has(fishPlaque)).save(this.output);
    }

    private void addCookingRecipes(RecipeOutput output, float xp, ItemLike rawFood, ItemLike cookedFood)
    {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(rawFood), RecipeCategory.FOOD, cookedFood, xp, 200).unlockedBy(getHasName(rawFood), this.has(rawFood)).save(output);
        this.simpleCookingRecipe("smoking", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100, rawFood, cookedFood, xp);
        this.simpleCookingRecipe("campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600, rawFood, cookedFood, xp);
    }

    public static class Runner extends RecipeProvider.Runner
    {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture)
        {
            super(packOutput, completableFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput)
        {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName()
        {
            return "FOT Recipes";
        }
    }
}