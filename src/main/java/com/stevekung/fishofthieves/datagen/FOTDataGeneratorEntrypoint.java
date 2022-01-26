package com.stevekung.fishofthieves.datagen;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.commons.lang3.ArrayUtils;

import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;

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
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
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
        dataGenerator.addProvider(ItemTagsProvider::new);
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
            itemModelGenerator.generateFlatItem(FOTItems.ISLEHOPPER, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.COOKED_ISLEHOPPER, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.ISLEHOPPER_BUCKET, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.ISLEHOPPER_SPAWN_EGG, TEMPLATE_SPAWN_EGG);
            itemModelGenerator.generateFlatItem(FOTItems.ANCIENTSCALE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.COOKED_ANCIENTSCALE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.ANCIENTSCALE_BUCKET, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.ANCIENTSCALE_SPAWN_EGG, TEMPLATE_SPAWN_EGG);
            itemModelGenerator.generateFlatItem(FOTItems.PLENTIFIN, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.COOKED_PLENTIFIN, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.PLENTIFIN_BUCKET, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.PLENTIFIN_SPAWN_EGG, TEMPLATE_SPAWN_EGG);
            itemModelGenerator.generateFlatItem(FOTItems.WILDSPLASH, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.COOKED_WILDSPLASH, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.WILDSPLASH_BUCKET, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.WILDSPLASH_SPAWN_EGG, TEMPLATE_SPAWN_EGG);
            itemModelGenerator.generateFlatItem(FOTItems.DEVILFISH, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.COOKED_DEVILFISH, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.DEVILFISH_BUCKET, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.DEVILFISH_SPAWN_EGG, TEMPLATE_SPAWN_EGG);
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
            addCookingRecipes(consumer, 200, 100, 600, 0.35F, FOTItems.SPLASHTAIL, FOTItems.COOKED_SPLASHTAIL);
            addCookingRecipes(consumer, 200, 100, 600, 0.25F, FOTItems.PONDIE, FOTItems.COOKED_PONDIE);
            addCookingRecipes(consumer, 200, 100, 600, 0.4F, FOTItems.ISLEHOPPER, FOTItems.COOKED_ISLEHOPPER);
            addCookingRecipes(consumer, 200, 100, 600, 0.45F, FOTItems.ANCIENTSCALE, FOTItems.COOKED_ANCIENTSCALE);
            addCookingRecipes(consumer, 200, 100, 600, 0.4F, FOTItems.PLENTIFIN, FOTItems.COOKED_PLENTIFIN);
            addCookingRecipes(consumer, 200, 100, 600, 0.45F, FOTItems.WILDSPLASH, FOTItems.COOKED_WILDSPLASH);
            addCookingRecipes(consumer, 200, 100, 600, 0.5F, FOTItems.DEVILFISH, FOTItems.COOKED_DEVILFISH);
        }

        private static void addCookingRecipes(Consumer<FinishedRecipe> consumer, int smeltingTime, int smokingTime, int campfireTime, float xp, ItemLike rawFood, ItemLike cookedFood)
        {
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(rawFood), cookedFood, xp, smeltingTime).unlockedBy(getHasName(rawFood), has(rawFood)).save(consumer);
            simpleCookingRecipe(consumer, "smoking", RecipeSerializer.SMOKING_RECIPE, smokingTime, rawFood, cookedFood, xp);
            simpleCookingRecipe(consumer, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, campfireTime, rawFood, cookedFood, xp);
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
            consumer.accept(FOTEntities.SPLASHTAIL.getDefaultLootTable(), LootTable.lootTable()
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
            consumer.accept(FOTEntities.PONDIE.getDefaultLootTable(), LootTable.lootTable()
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
            consumer.accept(FOTEntities.ISLEHOPPER.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.ISLEHOPPER)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F))));
            consumer.accept(FOTEntities.ANCIENTSCALE.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F))));
            consumer.accept(FOTEntities.PLENTIFIN.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.PLENTIFIN)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F))));
            consumer.accept(FOTEntities.WILDSPLASH.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.WILDSPLASH)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F))));
            consumer.accept(FOTEntities.DEVILFISH.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.DEVILFISH)
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

    private static class ItemTagsProvider extends FabricTagProvider.ItemTagProvider
    {
        private ItemTagsProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator);
        }

        @Override
        protected void generateTags()
        {
            this.tag(ItemTags.AXOLOTL_TEMPT_ITEMS).add(FOTItems.SPLASHTAIL_BUCKET, FOTItems.PONDIE_BUCKET, FOTItems.ISLEHOPPER_BUCKET, FOTItems.ANCIENTSCALE_BUCKET, FOTItems.PLENTIFIN_BUCKET, FOTItems.WILDSPLASH_BUCKET, FOTItems.DEVILFISH_BUCKET);
            this.tag(ItemTags.FISHES).add(FOTItems.SPLASHTAIL, FOTItems.COOKED_SPLASHTAIL, FOTItems.PONDIE, FOTItems.COOKED_PONDIE, FOTItems.ISLEHOPPER, FOTItems.COOKED_ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.COOKED_ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.COOKED_PLENTIFIN,
                    FOTItems.WILDSPLASH, FOTItems.COOKED_WILDSPLASH, FOTItems.DEVILFISH, FOTItems.COOKED_DEVILFISH);
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
            var fishes = new EntityType<?>[] {FOTEntities.SPLASHTAIL, FOTEntities.PONDIE, FOTEntities.ISLEHOPPER, FOTEntities.ANCIENTSCALE, FOTEntities.PLENTIFIN, FOTEntities.WILDSPLASH, FOTEntities.DEVILFISH};
            this.tag(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add(ArrayUtils.removeElements(fishes, FOTEntities.DEVILFISH));
            this.getOrCreateTagBuilder(ThievesFish.THIEVES_FISH).add(fishes);
        }
    }
}