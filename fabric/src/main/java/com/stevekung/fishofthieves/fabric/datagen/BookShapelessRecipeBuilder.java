package com.stevekung.fishofthieves.fabric.datagen;

import java.util.List;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.CraftingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import vazkii.patchouli.common.item.PatchouliItems;
import vazkii.patchouli.common.recipe.ShapelessBookRecipe;

public class BookShapelessRecipeBuilder extends CraftingRecipeBuilder implements RecipeBuilder
{
    private final RecipeCategory category;
    private final String bookId;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    @Nullable
    private String group;

    public BookShapelessRecipeBuilder(RecipeCategory category, String bookId)
    {
        this.category = category;
        this.bookId = bookId;
    }

    /**
     * Creates a new builder for a shapeless recipe.
     */
    public static BookShapelessRecipeBuilder shapeless(RecipeCategory category, String bookId)
    {
        return new BookShapelessRecipeBuilder(category, bookId);
    }

    /**
     * Adds an ingredient that can be any item in the given tag.
     */
    public BookShapelessRecipeBuilder requires(TagKey<Item> tag)
    {
        return this.requires(Ingredient.of(tag));
    }

    /**
     * Adds an ingredient of the given item.
     */
    public BookShapelessRecipeBuilder requires(ItemLike item)
    {
        return this.requires(item, 1);
    }

    /**
     * Adds the given ingredient multiple times.
     */
    public BookShapelessRecipeBuilder requires(ItemLike item, int quantity)
    {
        for (var i = 0; i < quantity; i++)
        {
            this.requires(Ingredient.of(item));
        }

        return this;
    }

    /**
     * Adds an ingredient.
     */
    public BookShapelessRecipeBuilder requires(Ingredient ingredient)
    {
        return this.requires(ingredient, 1);
    }

    /**
     * Adds an ingredient multiple times.
     */
    public BookShapelessRecipeBuilder requires(Ingredient ingredient, int quantity)
    {
        for (var i = 0; i < quantity; i++)
        {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    @Override
    public BookShapelessRecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger)
    {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public BookShapelessRecipeBuilder group(@Nullable String groupName)
    {
        this.group = groupName;
        return this;
    }

    @Override
    public Item getResult()
    {
        return BuiltInRegistries.ITEM.get(PatchouliItems.BOOK_ID);
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId)
    {
        this.ensureValid(recipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new BookShapelessRecipeBuilder.Result(recipeId, this.bookId, this.group == null ? "" : this.group, determineBookCategory(this.category), this.ingredients, this.advancement, recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    /**
     * Makes sure that this recipe is valid and obtainable.
     */
    private void ensureValid(ResourceLocation id)
    {
        if (this.advancement.getCriteria().isEmpty())
        {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static class Result extends CraftingRecipeBuilder.CraftingResult
    {
        private final ResourceLocation id;
        private final String bookId;
        private final String group;
        private final List<Ingredient> ingredients;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, String bookId, String group, CraftingBookCategory category, List<Ingredient> ingredients, Advancement.Builder advancement, ResourceLocation advancementId)
        {
            super(category);
            this.id = id;
            this.bookId = bookId;
            this.group = group;
            this.ingredients = ingredients;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json)
        {
            super.serializeRecipeData(json);

            if (!this.group.isEmpty())
            {
                json.addProperty("group", this.group);
            }

            var jsonArray = new JsonArray();

            for (var ingredient : this.ingredients)
            {
                jsonArray.add(ingredient.toJson());
            }

            json.add("ingredients", jsonArray);

            json.addProperty("book", this.bookId);
        }

        @Override
        public RecipeSerializer<?> getType()
        {
            return ShapelessBookRecipe.SERIALIZER;
        }

        @Override
        public ResourceLocation getId()
        {
            return this.id;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement()
        {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId()
        {
            return this.advancementId;
        }
    }
}