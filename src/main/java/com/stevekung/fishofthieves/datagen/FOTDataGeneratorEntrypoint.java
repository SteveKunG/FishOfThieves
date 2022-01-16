package com.stevekung.fishofthieves.datagen;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.stevekung.fishofthieves.FOTEntities;
import com.stevekung.fishofthieves.FOTItems;
import com.stevekung.fishofthieves.FOTLootTables;
import com.stevekung.fishofthieves.entity.ThievesFish;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockStateDefinitionProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class FOTDataGeneratorEntrypoint implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator)
    {
        dataGenerator.addProvider(ModelProvider::new);

        dataGenerator.addProvider(RecipeProvider::new);
        dataGenerator.addProvider(LootProvider::new);
        dataGenerator.addProvider(EntityTagsProvider::new);
    }

    private static class ModelProvider extends FabricBlockStateDefinitionProvider
    {
        private static final ModelTemplate TEMPLATE_SPAWN_EGG = createItem("template_spawn_egg");

        private ModelProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator);
        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerator)
        {
            itemModelGenerator.generateFlatItem(FOTItems.SPLASHTAIL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.COOKED_SPLASHTAIL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.SPLASHTAIL_BUCKET, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.SPLASHTAIL_SPAWN_EGG, TEMPLATE_SPAWN_EGG);
            itemModelGenerator.generateFlatItem(FOTItems.PONDIE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.COOKED_PONDIE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.PONDIE_BUCKET, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.PONDIE_SPAWN_EGG, TEMPLATE_SPAWN_EGG);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {}

        private static ModelTemplate createItem(String itemModelLocation)
        {
            return new ModelTemplate(Optional.of(new ResourceLocation("item/" + itemModelLocation)), Optional.empty());
        }
    }

    private static class RecipeProvider extends FabricRecipesProvider
    {
        private RecipeProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator);
        }

        @Override
        protected void generateRecipes(Consumer<FinishedRecipe> consumer)
        {
            simpleCookingRecipe(consumer, "smoking", RecipeSerializer.SMOKING_RECIPE, 100, FOTItems.SPLASHTAIL, FOTItems.COOKED_SPLASHTAIL, 0.35F);
            simpleCookingRecipe(consumer, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600, FOTItems.SPLASHTAIL, FOTItems.COOKED_SPLASHTAIL, 0.35F);
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(FOTItems.SPLASHTAIL), FOTItems.COOKED_SPLASHTAIL, 0.35F, 200).unlockedBy(getHasName(FOTItems.SPLASHTAIL), has(FOTItems.SPLASHTAIL)).save(consumer);
            simpleCookingRecipe(consumer, "smoking", RecipeSerializer.SMOKING_RECIPE, 100, FOTItems.PONDIE, FOTItems.COOKED_PONDIE, 0.35F);
            simpleCookingRecipe(consumer, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600, FOTItems.PONDIE, FOTItems.COOKED_PONDIE, 0.35F);
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(FOTItems.PONDIE), FOTItems.COOKED_PONDIE, 0.35F, 200).unlockedBy(getHasName(FOTItems.PONDIE), has(FOTItems.PONDIE)).save(consumer);
        }
    }

    private static class LootProvider extends SimpleFabricLootTableProvider
    {
        private static final EntityPredicate.Builder TROPHY = EntityPredicate.Builder.entity().nbt(new NbtPredicate(Util.make(new CompoundTag(), tag -> tag.putBoolean("Trophy", true))));

        private LootProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator, LootContextParamSets.ENTITY);
        }

        @Override
        public void accept(BiConsumer<ResourceLocation, Builder> consumer)
        {
            consumer.accept(FOTLootTables.SPLASHTAIL, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.SPLASHTAIL)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F))));
            consumer.accept(FOTLootTables.PONDIE, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.PONDIE)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F))));
        }
    }

    private static class EntityTagsProvider extends FabricTagProvider.EntityTypeTagProvider
    {
        private EntityTagsProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator);
        }

        @Override
        protected void generateTags()
        {
            this.tag(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add(FOTEntities.SPLASHTAIL, FOTEntities.PONDIE);
            this.getOrCreateTagBuilder(ThievesFish.THIEVES_FISH).add(FOTEntities.SPLASHTAIL, FOTEntities.PONDIE);
        }
    }
}