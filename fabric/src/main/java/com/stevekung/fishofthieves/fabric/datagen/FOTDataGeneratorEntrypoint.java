package com.stevekung.fishofthieves.fabric.datagen;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.commons.lang3.ArrayUtils;
import com.google.common.collect.Maps;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.FishVariant;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.trigger.ItemUsedOnBlockWithNearbyEntityTrigger;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
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
    private static final Item[] FISH_BUCKETS = {
            FOTItems.SPLASHTAIL_BUCKET,
            FOTItems.PONDIE_BUCKET,
            FOTItems.ISLEHOPPER_BUCKET,
            FOTItems.ANCIENTSCALE_BUCKET,
            FOTItems.PLENTIFIN_BUCKET,
            FOTItems.WILDSPLASH_BUCKET,
            FOTItems.DEVILFISH_BUCKET,
            FOTItems.BATTLEGILL_BUCKET,
            FOTItems.WRECKER_BUCKET,
            FOTItems.STORMFISH_BUCKET
    };

    // Fabric Tags
    private static final TagKey<Item> RAW_FISHES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("fabric", "raw_fishes"));
    private static final TagKey<Item> COOKED_FISHES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("fabric", "cooked_fishes"));

    // Croptopia
    private static final TagKey<Item> CROPTOPIA_FISHES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("croptopia", "fishes"));

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator)
    {
        dataGenerator.addProvider(ModelProvider::new);

        dataGenerator.addProvider(RecipeProvider::new);
        dataGenerator.addProvider(LootProvider::new);
        dataGenerator.addProvider(ItemTagsProvider::new);
        dataGenerator.addProvider(EntityTagsProvider::new);
        dataGenerator.addProvider(BiomeTagsProvider::new);
        dataGenerator.addProvider(AdvancementProvider::new);
    }

    private static class ModelProvider extends FabricModelProvider
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
            itemModelGenerator.generateFlatItem(FOTItems.BATTLEGILL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.COOKED_BATTLEGILL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.BATTLEGILL_BUCKET, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.BATTLEGILL_SPAWN_EGG, TEMPLATE_SPAWN_EGG);
            itemModelGenerator.generateFlatItem(FOTItems.WRECKER, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.COOKED_WRECKER, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.WRECKER_BUCKET, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.WRECKER_SPAWN_EGG, TEMPLATE_SPAWN_EGG);
            itemModelGenerator.generateFlatItem(FOTItems.STORMFISH, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.COOKED_STORMFISH, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.STORMFISH_BUCKET, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.STORMFISH_SPAWN_EGG, TEMPLATE_SPAWN_EGG);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {}

        private static ModelTemplate createItem(String itemModelLocation)
        {
            return new ModelTemplate(Optional.of(new ResourceLocation("item/" + itemModelLocation)), Optional.empty());
        }
    }

    private static class RecipeProvider extends FabricRecipeProvider
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
            addCookingRecipes(consumer, 200, 100, 600, 0.5F, FOTItems.BATTLEGILL, FOTItems.COOKED_BATTLEGILL);
            addCookingRecipes(consumer, 200, 100, 600, 0.5F, FOTItems.WRECKER, FOTItems.COOKED_WRECKER);
            addCookingRecipes(consumer, 200, 100, 600, 0.6F, FOTItems.STORMFISH, FOTItems.COOKED_STORMFISH);
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
        private static final EntityPredicate.Builder TROPHY = EntityPredicate.Builder.entity().nbt(new NbtPredicate(Util.make(new CompoundTag(), tag -> tag.putBoolean(ThievesFish.TROPHY_TAG, true))));

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
            consumer.accept(FOTEntities.BATTLEGILL.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.BATTLEGILL)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F))));
            consumer.accept(FOTEntities.WRECKER.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.WRECKER)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F))));
            consumer.accept(FOTEntities.STORMFISH.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.STORMFISH)
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
            var rawFishes = new Item[] { FOTItems.SPLASHTAIL, FOTItems.PONDIE, FOTItems.ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.WILDSPLASH, FOTItems.DEVILFISH, FOTItems.BATTLEGILL, FOTItems.WRECKER, FOTItems.STORMFISH };
            var cookedFishes = new Item[] { FOTItems.COOKED_SPLASHTAIL, FOTItems.COOKED_PONDIE, FOTItems.COOKED_ISLEHOPPER, FOTItems.COOKED_ANCIENTSCALE, FOTItems.COOKED_PLENTIFIN, FOTItems.COOKED_WILDSPLASH, FOTItems.COOKED_DEVILFISH, FOTItems.COOKED_BATTLEGILL, FOTItems.COOKED_WRECKER, FOTItems.COOKED_STORMFISH };

            this.tag(ItemTags.AXOLOTL_TEMPT_ITEMS).add(FISH_BUCKETS);
            this.tag(ItemTags.FISHES).add(FOTItems.SPLASHTAIL, FOTItems.COOKED_SPLASHTAIL, FOTItems.PONDIE, FOTItems.COOKED_PONDIE, FOTItems.ISLEHOPPER, FOTItems.COOKED_ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.COOKED_ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.COOKED_PLENTIFIN,
                    FOTItems.WILDSPLASH, FOTItems.COOKED_WILDSPLASH, FOTItems.DEVILFISH, FOTItems.COOKED_DEVILFISH, FOTItems.BATTLEGILL, FOTItems.COOKED_BATTLEGILL, FOTItems.WRECKER, FOTItems.COOKED_WRECKER, FOTItems.STORMFISH, FOTItems.COOKED_STORMFISH);
            this.getOrCreateTagBuilder(FOTTags.THIEVES_FISH_BUCKET).add(FISH_BUCKETS);

            // Fabric
            this.getOrCreateTagBuilder(RAW_FISHES).add(rawFishes);
            this.getOrCreateTagBuilder(COOKED_FISHES).add(cookedFishes);

            // Croptopia compatibility
            this.getOrCreateTagBuilder(CROPTOPIA_FISHES).add(rawFishes);
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
            var neutralFishes = new EntityType<?>[] { FOTEntities.DEVILFISH, FOTEntities.BATTLEGILL, FOTEntities.WRECKER };
            var fishes = new EntityType<?>[] { FOTEntities.SPLASHTAIL, FOTEntities.PONDIE, FOTEntities.ISLEHOPPER, FOTEntities.ANCIENTSCALE, FOTEntities.PLENTIFIN, FOTEntities.WILDSPLASH, FOTEntities.STORMFISH };
            this.tag(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add(ArrayUtils.removeElements(fishes, neutralFishes));
            this.getOrCreateTagBuilder(FOTTags.THIEVES_FISH).add(ArrayUtils.addAll(fishes, neutralFishes));
        }
    }

    private static class BiomeTagsProvider extends FabricTagProvider.DynamicRegistryTagProvider<Biome>
    {
        public BiomeTagsProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator, BuiltinRegistries.BIOME.key(), "worldgen/biome", "Biome Tags");
        }

        @Override
        protected void generateTags()
        {
            this.getOrCreateTagBuilder(FOTTags.IS_CAVES).add(Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);

            this.getOrCreateTagBuilder(FOTTags.SPAWNS_SPLASHTAILS).forceAddTag(BiomeTags.IS_OCEAN);
            this.getOrCreateTagBuilder(FOTTags.SPAWNS_PONDIES).forceAddTag(BiomeTags.IS_RIVER).forceAddTag(BiomeTags.IS_FOREST);
            this.getOrCreateTagBuilder(FOTTags.SPAWNS_ISLEHOPPERS).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_BEACH).forceAddTag(BiomeTags.IS_JUNGLE).add(Biomes.SWAMP, Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
            this.getOrCreateTagBuilder(FOTTags.SPAWNS_ANCIENTSCALES).add(Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN);
            this.getOrCreateTagBuilder(FOTTags.SPAWNS_PLENTIFINS).add(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
            this.getOrCreateTagBuilder(FOTTags.SPAWNS_WILDSPLASH).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_BEACH).forceAddTag(BiomeTags.IS_JUNGLE).add(Biomes.SWAMP, Biomes.LUSH_CAVES, Biomes.WARM_OCEAN);
            this.addOverworldBiomesToTag(FOTTags.SPAWNS_DEVILFISH);
            this.addOverworldBiomesToTag(FOTTags.SPAWNS_BATTLEGILLS);
            this.getOrCreateTagBuilder(FOTTags.SPAWNS_WRECKERS).forceAddTag(BiomeTags.IS_OCEAN);
            this.getOrCreateTagBuilder(FOTTags.SPAWNS_STORMFISH).forceAddTag(BiomeTags.IS_OCEAN).add(Biomes.SPARSE_JUNGLE);

            this.getOrCreateTagBuilder(FOTTags.DEVILFISH_CANNOT_SPAWN).add(Biomes.LUSH_CAVES);
        }

        @Override
        protected Path getPath(ResourceLocation id)
        {
            return this.generator.getOutputFolder().resolve("data/%s/%s/%s.json".formatted(id.getNamespace(), "tags/worldgen/biome", id.getPath()));
        }

        private void addOverworldBiomesToTag(TagKey<Biome> tag)
        {
            this.getOrCreateTagBuilder(tag)
                    .add(Biomes.RIVER).add(Biomes.FROZEN_RIVER)
                    .add(Biomes.COLD_OCEAN).add(Biomes.DEEP_COLD_OCEAN)
                    .add(Biomes.DEEP_FROZEN_OCEAN).add(Biomes.DEEP_OCEAN)
                    .add(Biomes.DEEP_LUKEWARM_OCEAN).add(Biomes.WARM_OCEAN).add(Biomes.LUKEWARM_OCEAN)
                    .add(Biomes.FROZEN_OCEAN).add(Biomes.OCEAN)
                    .add(Biomes.BEACH).add(Biomes.PLAINS)
                    .add(Biomes.SUNFLOWER_PLAINS).add(Biomes.SNOWY_PLAINS)
                    .add(Biomes.ICE_SPIKES).add(Biomes.DESERT)
                    .add(Biomes.FOREST).add(Biomes.FLOWER_FOREST)
                    .add(Biomes.BIRCH_FOREST).add(Biomes.DARK_FOREST)
                    .add(Biomes.OLD_GROWTH_BIRCH_FOREST).add(Biomes.OLD_GROWTH_PINE_TAIGA)
                    .add(Biomes.OLD_GROWTH_SPRUCE_TAIGA).add(Biomes.TAIGA)
                    .add(Biomes.SNOWY_TAIGA).add(Biomes.SAVANNA)
                    .add(Biomes.SAVANNA_PLATEAU).add(Biomes.WINDSWEPT_HILLS)
                    .add(Biomes.WINDSWEPT_GRAVELLY_HILLS).add(Biomes.WINDSWEPT_FOREST)
                    .add(Biomes.WINDSWEPT_SAVANNA).add(Biomes.JUNGLE).add(Biomes.SPARSE_JUNGLE)
                    .add(Biomes.BAMBOO_JUNGLE).add(Biomes.BADLANDS).add(Biomes.ERODED_BADLANDS)
                    .add(Biomes.WOODED_BADLANDS).add(Biomes.MEADOW).add(Biomes.GROVE)
                    .add(Biomes.SNOWY_SLOPES).add(Biomes.FROZEN_PEAKS).add(Biomes.JAGGED_PEAKS)
                    .add(Biomes.STONY_PEAKS).add(Biomes.MUSHROOM_FIELDS).add(Biomes.DRIPSTONE_CAVES)
                    .add(Biomes.LUSH_CAVES).add(Biomes.SNOWY_BEACH).add(Biomes.SWAMP).add(Biomes.STONY_SHORE);
        }
    }

    private static class AdvancementProvider extends FabricAdvancementProvider
    {
        private static final Map<Item, FishVariant[]> BUCKET_TO_VARIANTS_MAP = Util.make(Maps.newHashMap(), map ->
        {
            map.put(FOTItems.SPLASHTAIL_BUCKET, Splashtail.Variant.BY_ID);
            map.put(FOTItems.PONDIE_BUCKET, Pondie.Variant.BY_ID);
            map.put(FOTItems.ISLEHOPPER_BUCKET, Islehopper.Variant.BY_ID);
            map.put(FOTItems.ANCIENTSCALE_BUCKET, Ancientscale.Variant.BY_ID);
            map.put(FOTItems.PLENTIFIN_BUCKET, Plentifin.Variant.BY_ID);
            map.put(FOTItems.WILDSPLASH_BUCKET, Wildsplash.Variant.BY_ID);
            map.put(FOTItems.DEVILFISH_BUCKET, Devilfish.Variant.BY_ID);
            map.put(FOTItems.BATTLEGILL_BUCKET, Battlegill.Variant.BY_ID);
            map.put(FOTItems.WRECKER_BUCKET, Wrecker.Variant.BY_ID);
            map.put(FOTItems.STORMFISH_BUCKET, Stormfish.Variant.BY_ID);
        });

        private AdvancementProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator);
        }

        @Override
        public void generateAdvancement(Consumer<Advancement> consumer)
        {
            var advancement = Advancement.Builder.advancement()
                    .display(FOTItems.SPLASHTAIL,
                            new TranslatableComponent("advancements.fot.root.title"),
                            new TranslatableComponent("advancements.fot.root.description"),
                            new ResourceLocation("textures/block/tube_coral_block.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion("in_water", LocationTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location()
                                    .setFluid(FluidPredicate.Builder.fluid()
                                            .of(FluidTags.WATER).build()).build()))
                    .save(consumer, this.get("root"));

            var advancement2 = this.addFishBuckets(Advancement.Builder.advancement().parent(advancement))
                    .display(FOTItems.SPLASHTAIL_BUCKET,
                            new TranslatableComponent("advancements.fot.fish_collectors.title"),
                            new TranslatableComponent("advancements.fot.fish_collectors.description"),
                            null, FrameType.CHALLENGE, true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(250))
                    .save(consumer, this.get("fish_collectors"));

            this.addFishVariantsBuckets(Advancement.Builder.advancement().parent(advancement2), false)
                    .display(FOTItems.SPLASHTAIL_BUCKET,
                            new TranslatableComponent("advancements.fot.master_fish_collectors.title"),
                            new TranslatableComponent("advancements.fot.master_fish_collectors.description"),
                            null, FrameType.CHALLENGE, true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(1000))
                    .save(consumer, this.get("master_fish_collectors"));

            this.addFishVariantsBuckets(Advancement.Builder.advancement().parent(advancement2), true)
                    .display(FOTItems.SPLASHTAIL_BUCKET,
                            new TranslatableComponent("advancements.fot.legendary_fish_collectors.title"),
                            new TranslatableComponent("advancements.fot.legendary_fish_collectors.description"),
                            null, FrameType.CHALLENGE, true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(2000))
                    .save(consumer, this.get("legendary_fish_collectors"));

            Advancement.Builder.advancement().parent(advancement).addCriterion(Registry.ITEM.getKey(FOTItems.DEVILFISH_BUCKET).getPath(),
                            PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(EntityPredicate.Composite.create(),
                                    ItemPredicate.Builder.item().of(FOTItems.DEVILFISH_BUCKET).hasNbt(Util.make(new CompoundTag(), compound ->
                                    {
                                        compound.putInt(ThievesFish.VARIANT_TAG, 2);
                                        compound.putString(ThievesFish.NAME_TAG, "lava");
                                    })),
                                    EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(EntityType.AXOLOTL).build())))
                    .display(FOTItems.DEVILFISH,
                            new TranslatableComponent("advancements.fot.feed_axolotl_with_lava_devilfish.title"),
                            new TranslatableComponent("advancements.fot.feed_axolotl_with_lava_devilfish.description"),
                            null, FrameType.TASK, true, true, false)
                    .save(consumer, this.get("feed_axolotl_with_lava_devilfish"));

            var battlegill = Registry.ITEM.getKey(FOTItems.BATTLEGILL).getPath();
            Advancement.Builder.advancement().parent(advancement).requirements(RequirementsStrategy.OR)
                    .addCriterion(battlegill + "_village_plains",
                            FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inFeature(BuiltinStructures.VILLAGE_PLAINS)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                    .addCriterion(battlegill + "_village_desert",
                            FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inFeature(BuiltinStructures.VILLAGE_DESERT)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                    .addCriterion(battlegill + "_village_savanna",
                            FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inFeature(BuiltinStructures.VILLAGE_SAVANNA)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                    .addCriterion(battlegill + "_village_snowy",
                            FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inFeature(BuiltinStructures.VILLAGE_SNOWY)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                    .addCriterion(battlegill + "_village_taiga",
                            FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inFeature(BuiltinStructures.VILLAGE_TAIGA)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                    .display(FOTItems.BATTLEGILL,
                            new TranslatableComponent("advancements.fot.so_chill.title"),
                            new TranslatableComponent("advancements.fot.so_chill.description"),
                            null, FrameType.TASK, true, true, false)
                    .save(consumer, this.get("so_chill"));

            Advancement.Builder.advancement().parent(advancement)
                    .display(FOTItems.STORMFISH,
                            new TranslatableComponent("advancements.fot.lightning_straight_to_my_fish.title"),
                            new TranslatableComponent("advancements.fot.lightning_straight_to_my_fish.description"),
                            null, FrameType.TASK, true, true, false)
                    .addCriterion("lightning_strike_at_stormfish", fireCountAndBystander(MinMaxBounds.Ints.exactly(0),
                            EntityPredicate.Builder.entity().of(FOTEntities.STORMFISH).build()))
                    .save(consumer, this.get("lightning_straight_to_my_fish"));

            Advancement.Builder.advancement().parent(advancement)
                    .display(Items.SPYGLASS,
                            new TranslatableComponent("advancements.fot.spyglass_at_plentifins.title"),
                            new TranslatableComponent("advancements.fot.spyglass_at_plentifins.description"),
                            null, FrameType.TASK, true, true, false)
                    .addCriterion("spyglass_at_plentifins", lookAtThroughItem(FOTEntities.PLENTIFIN, Items.SPYGLASS))
                    .save(consumer, this.get("spyglass_at_plentifins"));

            Advancement.Builder.advancement().parent(advancement).requirements(RequirementsStrategy.OR)
                    .display(Items.JUKEBOX,
                            new TranslatableComponent("advancements.fot.play_jukebox_near_fish.title"),
                            new TranslatableComponent("advancements.fot.play_jukebox_near_fish.description"),
                            null, FrameType.TASK, true, true, true)
                    .addCriterion("play_jukebox_near_thieves_fish", ItemUsedOnBlockWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location()
                                    .setBlock(BlockPredicate.Builder.block().of(Blocks.JUKEBOX).build()),
                            ItemPredicate.Builder.item().of(ItemTags.MUSIC_DISCS),
                            EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(FOTTags.THIEVES_FISH).build())))
                    .addCriterion("play_jukebox_near_fish", ItemUsedOnBlockWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location()
                                    .setBlock(BlockPredicate.Builder.block().of(Blocks.JUKEBOX).build()),
                            ItemPredicate.Builder.item().of(ItemTags.MUSIC_DISCS),
                            EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(EntityTypeTags.AXOLOTL_HUNT_TARGETS).build())))
                    .save(consumer, this.get("play_jukebox_near_fish"));

            Advancement.Builder.advancement().parent(advancement).addCriterion(Registry.ITEM.getKey(Items.NAME_TAG).getPath(),
                            PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(EntityPredicate.Composite.create(),
                                    ItemPredicate.Builder.item().of(Items.NAME_TAG).hasNbt(Util.make(new CompoundTag(), compound ->
                                    {
                                        var displayCompound = new CompoundTag();
                                        displayCompound.putString(ItemStack.TAG_DISPLAY_NAME, Component.Serializer.toJson(new TextComponent("Sally")));
                                        compound.put(ItemStack.TAG_DISPLAY, displayCompound);
                                    })),
                                    EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(EntityType.SALMON).build())))
                    .display(Items.SALMON,
                            new TranslatableComponent("advancements.fot.lost_sally.title"),
                            new TranslatableComponent("advancements.fot.lost_sally.description"),
                            null, FrameType.TASK, true, true, true)
                    .save(consumer, this.get("lost_sally"));
        }

        private String get(String name)
        {
            return FishOfThieves.MOD_ID + ":" + name;
        }

        private Advancement.Builder addFishBuckets(Advancement.Builder builder)
        {
            for (var item : FISH_BUCKETS)
            {
                builder.addCriterion(Registry.ITEM.getKey(item).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(item).build()));
            }
            return builder;
        }

        private Advancement.Builder addFishVariantsBuckets(Advancement.Builder builder, boolean trophy)
        {
            for (var item : FISH_BUCKETS)
            {
                for (var variant : BUCKET_TO_VARIANTS_MAP.get(item))
                {
                    builder.addCriterion(variant.getName() + "_" + Registry.ITEM.getKey(item).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(item).hasNbt(Util.make(new CompoundTag(), compound ->
                    {
                        compound.putInt(ThievesFish.VARIANT_TAG, variant.getId());
                        compound.putString(ThievesFish.NAME_TAG, variant.getName());
                        compound.putBoolean(ThievesFish.TROPHY_TAG, trophy);
                    })).build()));
                }
            }
            return builder;
        }

        private static LightningStrikeTrigger.TriggerInstance fireCountAndBystander(MinMaxBounds.Ints blocksSetOnFire, EntityPredicate bystander)
        {
            return LightningStrikeTrigger.TriggerInstance.lighthingStrike(EntityPredicate.Builder.entity().distance(DistancePredicate.absolute(MinMaxBounds.Doubles.atMost(16.0))).lighthingBolt(LighthingBoltPredicate.blockSetOnFire(blocksSetOnFire)).build(), bystander);
        }

        private static UsingItemTrigger.TriggerInstance lookAtThroughItem(EntityType<?> lookedAtEntityType, Item lookedThroughItem)
        {
            return UsingItemTrigger.TriggerInstance.lookingAt(EntityPredicate.Builder.entity().player(PlayerPredicate.Builder.player().setLookingAt(EntityPredicate.Builder.entity().of(lookedAtEntityType).build()).build()), ItemPredicate.Builder.item().of(lookedThroughItem));
        }
    }
}