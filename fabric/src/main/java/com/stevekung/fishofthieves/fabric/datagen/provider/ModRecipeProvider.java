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
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class ModRecipeProvider extends RecipeProvider
{
    private final HolderGetter<Item> items;

    protected ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput)
    {
        super(provider, recipeOutput);
        this.items = provider.lookupOrThrow(Registries.ITEM);
    }

    @Override
    public void buildRecipes()
    {
        this.generateForFOTBlockFamilies();
        this.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 4).requires(FOTBlocks.FISH_BONE).group("bonemeal").unlockedBy(getHasName(FOTBlocks.FISH_BONE), this.has(FOTBlocks.FISH_BONE)).save(this.output, FishOfThieves.MOD_RESOURCES + "bonemeals_from_fish_bone");

        this.addWoodenFishPlaqueRecipe(FOTBlocks.OAK_FISH_PLAQUE, Items.OAK_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.SPRUCE_FISH_PLAQUE, Items.SPRUCE_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.BIRCH_FISH_PLAQUE, Items.BIRCH_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.JUNGLE_FISH_PLAQUE, Items.JUNGLE_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.ACACIA_FISH_PLAQUE, Items.ACACIA_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.DARK_OAK_FISH_PLAQUE, Items.DARK_OAK_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.MANGROVE_FISH_PLAQUE, Items.MANGROVE_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.CHERRY_FISH_PLAQUE, Items.CHERRY_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.PALE_OAK_FISH_PLAQUE, Items.PALE_OAK_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.BAMBOO_FISH_PLAQUE, Items.BAMBOO_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.CRIMSON_FISH_PLAQUE, Items.CRIMSON_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.WARPED_FISH_PLAQUE, Items.WARPED_PLANKS);
        this.addWoodenFishPlaqueRecipe(FOTBlocks.COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_PLANKS);

        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_PALE_OAK_FISH_PLAQUE, FOTBlocks.PALE_OAK_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE);
        this.addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_FISH_PLAQUE);

        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE);
        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE);
        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE);
        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE);
        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE);
        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE);
        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE);
        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE);
        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_PALE_OAK_FISH_PLAQUE, FOTBlocks.PALE_OAK_FISH_PLAQUE);
        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE);
        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE);
        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE);
        this.addCopperFrameFishPlaqueRecipe(FOTBlocks.COPPER_FRAME_COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_FISH_PLAQUE);

        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_PALE_OAK_FISH_PLAQUE, FOTBlocks.PALE_OAK_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE);
        this.addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_FISH_PLAQUE);

        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_PALE_OAK_FISH_PLAQUE, FOTBlocks.PALE_OAK_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE);
        this.addGildedFishPlaqueRecipe(FOTBlocks.GILDED_COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_FISH_PLAQUE);

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

        this.oneToOneConversionRecipe(Items.PINK_DYE, FOTBlocks.PINK_PLUMERIA, "pink_dye");
        this.oneToOneConversionRecipe(Items.LIGHT_BLUE_DYE, FOTBlocks.LIGHT_BLUE_PLUMERIA, "light_blue_dye");
        this.oneToOneConversionRecipe(Items.WHITE_DYE, FOTBlocks.WHITE_PLUMERIA, "white_dye");
        this.oneToOneConversionRecipe(Items.PURPLE_DYE, FOTBlocks.BANANA_BLOSSOM, "purple_dye");
        this.shapeless(RecipeCategory.MISC, FOTItems.PINEAPPLE_SEEDS, 4).requires(Ingredient.of(FOTItems.PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE)).unlockedBy(getHasName(FOTItems.PINEAPPLE), inventoryTrigger(ItemPredicate.Builder.item().of(this.items, FOTItems.PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE).build())).save(this.output, getConversionRecipeName(FOTItems.PINEAPPLE_SEEDS, FOTItems.PINEAPPLE));
        this.woodFromLogs(FOTBlocks.COCONUT_WOOD, FOTBlocks.COCONUT_LOG);
        this.woodFromLogs(FOTBlocks.SMALL_COCONUT_WOOD, FOTBlocks.SMALL_COCONUT_LOG);
        this.woodFromLogs(FOTBlocks.MEDIUM_COCONUT_WOOD, FOTBlocks.MEDIUM_COCONUT_LOG);
        this.woodFromLogs(FOTBlocks.STRIPPED_COCONUT_WOOD, FOTBlocks.STRIPPED_COCONUT_LOG);
        this.woodFromLogs(FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD, FOTBlocks.STRIPPED_SMALL_COCONUT_LOG);
        this.woodFromLogs(FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD, FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG);
        this.planksFromLogs(FOTBlocks.COCONUT_PLANKS, FOTTags.Items.COCONUT_LOGS, 4);
        this.woodenBoat(FOTItems.COCONUT_BOAT, FOTBlocks.COCONUT_PLANKS);
        this.chestBoat(FOTItems.COCONUT_CHEST_BOAT, FOTItems.COCONUT_BOAT);
        this.hangingSign(FOTBlocks.COCONUT_HANGING_SIGN, FOTBlocks.STRIPPED_COCONUT_LOG);
        this.shelf(FOTBlocks.COCONUT_SHELF, FOTBlocks.STRIPPED_COCONUT_LOG);
    }

    private void generateForFOTBlockFamilies()
    {
        FOTBlockFamilies.getAllFamilies().forEach(blockFamily -> this.generateRecipes(blockFamily, FeatureFlagSet.of(FeatureFlags.VANILLA)));
    }

    private void addWoodenFishPlaqueRecipe(Block block, ItemLike baseMaterial)
    {
        this.shaped(RecipeCategory.DECORATIONS, block, 6).define('P', baseMaterial).define('F', Items.ITEM_FRAME).pattern("PPP").pattern("PFP").pattern("PPP").group("wooden_fish_plaque").unlockedBy(getHasName(Items.ITEM_FRAME), this.has(Items.ITEM_FRAME)).save(this.output);
    }

    private void addIronFrameFishPlaqueRecipe(Block block, ItemLike fishPlaque)
    {
        this.shaped(RecipeCategory.DECORATIONS, block).define('N', Items.IRON_NUGGET).define('F', fishPlaque).pattern("NNN").pattern("NFN").pattern("NNN").group("iron_frame_fish_plaque").unlockedBy(getHasName(fishPlaque), this.has(fishPlaque)).save(this.output);
    }

    private void addCopperFrameFishPlaqueRecipe(Block block, ItemLike fishPlaque)
    {
        this.shaped(RecipeCategory.DECORATIONS, block).define('N', Items.COPPER_NUGGET).define('F', fishPlaque).pattern("NNN").pattern("NFN").pattern("NNN").group("copper_frame_fish_plaque").unlockedBy(getHasName(fishPlaque), this.has(fishPlaque)).save(this.output);
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

    public static class Runner extends FabricRecipeProvider
    {
        public Runner(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture)
        {
            super(output, completableFuture);
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