package com.stevekung.fishofthieves.fabric.datagen;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.commons.lang3.ArrayUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.fabric.datagen.variant.*;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import com.stevekung.fishofthieves.loot.SetRandomFireworkFunction;
import com.stevekung.fishofthieves.predicates.FOTLocationCheck;
import com.stevekung.fishofthieves.predicates.FOTLocationPredicate;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.DevilfishVariants;
import com.stevekung.fishofthieves.trigger.ItemUsedOnBlockWithNearbyEntityTrigger;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class FOTDataGeneratorEntrypoint implements DataGeneratorEntrypoint
{
    //@formatter:off
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
    //@formatter:on

    // Fabric Tags
    private static final TagKey<Item> RAW_FISHES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("fabric", "raw_fishes"));
    private static final TagKey<Item> COOKED_FISHES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("fabric", "cooked_fishes"));

    // Croptopia
    private static final TagKey<Item> CROPTOPIA_FISHES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("croptopia", "fishes"));

    // Immersive Weathering
    private static final TagKey<EntityType<?>> FREEZING_WATER_IMMUNE = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("immersive_weathering", "freezing_water_immune"));

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator)
    {
        dataGenerator.addProvider(ModelProvider::new);

        dataGenerator.addProvider(RecipeProvider::new);
        dataGenerator.addProvider(BlockLootProvider::new);
        dataGenerator.addProvider(EntityLootProvider::new);
        dataGenerator.addProvider(ChestLootProvider::new);
        dataGenerator.addProvider(BlockTagsProvider::new);
        dataGenerator.addProvider(ItemTagsProvider::new);
        dataGenerator.addProvider(EntityTagsProvider::new);
        dataGenerator.addProvider(BiomeTagsProvider::new);
        dataGenerator.addProvider(StructureTagsProvider::new);
        dataGenerator.addProvider(AdvancementProvider::new);

        dataGenerator.addProvider(SplashtailVariantTagsProvider::new);
        dataGenerator.addProvider(PondieVariantTagsProvider::new);
        dataGenerator.addProvider(IslehopperVariantTagsProvider::new);
        dataGenerator.addProvider(AncientscaleVariantTagsProvider::new);
        dataGenerator.addProvider(PlentifinVariantTagsProvider::new);
        dataGenerator.addProvider(WildsplashVariantTagsProvider::new);
        dataGenerator.addProvider(DevilfishVariantTagsProvider::new);
        dataGenerator.addProvider(BattlegillVariantTagsProvider::new);
        dataGenerator.addProvider(WreckerVariantTagsProvider::new);
        dataGenerator.addProvider(StormfishVariantTagsProvider::new);
    }

    private static class ModelProvider extends FabricModelProvider
    {
        private static final ModelTemplate SPAWN_EGG = ModelTemplates.createItem("template_spawn_egg");

        private ModelProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator);
        }

        @Override
        public void generateItemModels(ItemModelGenerators generator)
        {
            generator.generateFlatItem(FOTItems.EARTHWORMS, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.GRUBS, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.LEECHES, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.SPLASHTAIL, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.COOKED_SPLASHTAIL, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.SPLASHTAIL_BUCKET, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.SPLASHTAIL_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.PONDIE, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.COOKED_PONDIE, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.PONDIE_BUCKET, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.PONDIE_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.ISLEHOPPER, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.COOKED_ISLEHOPPER, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.ISLEHOPPER_BUCKET, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.ISLEHOPPER_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.ANCIENTSCALE, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.COOKED_ANCIENTSCALE, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.ANCIENTSCALE_BUCKET, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.ANCIENTSCALE_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.PLENTIFIN, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.COOKED_PLENTIFIN, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.PLENTIFIN_BUCKET, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.PLENTIFIN_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.WILDSPLASH, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.COOKED_WILDSPLASH, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.WILDSPLASH_BUCKET, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.WILDSPLASH_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.DEVILFISH, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.COOKED_DEVILFISH, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.DEVILFISH_BUCKET, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.DEVILFISH_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.BATTLEGILL, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.COOKED_BATTLEGILL, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.BATTLEGILL_BUCKET, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.BATTLEGILL_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.WRECKER, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.COOKED_WRECKER, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.WRECKER_BUCKET, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.WRECKER_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.STORMFISH, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.COOKED_STORMFISH, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.STORMFISH_BUCKET, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.STORMFISH_SPAWN_EGG, SPAWN_EGG);

            generator.generateFlatItem(FOTBlocks.FISH_BONE.asItem(), ModelTemplates.FLAT_ITEM);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators generator)
        {
            var fishBone = FOTBlocks.FISH_BONE;
            generator.skipAutoItemBlock(fishBone);
            generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(fishBone, ModelLocationUtils.getModelLocation(fishBone)).with(BlockModelGenerators.createHorizontalFacingDispatchAlt()));
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
            ShapelessRecipeBuilder.shapeless(Items.BONE_MEAL, 4).requires(FOTBlocks.FISH_BONE).group("bonemeal").unlockedBy(getHasName(FOTBlocks.FISH_BONE), has(FOTBlocks.FISH_BONE)).save(consumer, FishOfThieves.MOD_RESOURCES + "bonemeals_from_fish_bone");

            addCookingRecipes(consumer, 0.3F, FOTItems.SPLASHTAIL, FOTItems.COOKED_SPLASHTAIL);
            addCookingRecipes(consumer, 0.25F, FOTItems.PONDIE, FOTItems.COOKED_PONDIE);
            addCookingRecipes(consumer, 0.3F, FOTItems.ISLEHOPPER, FOTItems.COOKED_ISLEHOPPER);
            addCookingRecipes(consumer, 0.3F, FOTItems.ANCIENTSCALE, FOTItems.COOKED_ANCIENTSCALE);
            addCookingRecipes(consumer, 0.3F, FOTItems.PLENTIFIN, FOTItems.COOKED_PLENTIFIN);
            addCookingRecipes(consumer, 0.4F, FOTItems.WILDSPLASH, FOTItems.COOKED_WILDSPLASH);
            addCookingRecipes(consumer, 0.4F, FOTItems.DEVILFISH, FOTItems.COOKED_DEVILFISH);
            addCookingRecipes(consumer, 0.45F, FOTItems.BATTLEGILL, FOTItems.COOKED_BATTLEGILL);
            addCookingRecipes(consumer, 0.5F, FOTItems.WRECKER, FOTItems.COOKED_WRECKER);
            addCookingRecipes(consumer, 0.6F, FOTItems.STORMFISH, FOTItems.COOKED_STORMFISH);
        }

        private static void addCookingRecipes(Consumer<FinishedRecipe> consumer, float xp, ItemLike rawFood, ItemLike cookedFood)
        {
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(rawFood), cookedFood, xp, 200).unlockedBy(getHasName(rawFood), has(rawFood)).save(consumer);
            simpleCookingRecipe(consumer, "smoking", RecipeSerializer.SMOKING_RECIPE, 100, rawFood, cookedFood, xp);
            simpleCookingRecipe(consumer, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600, rawFood, cookedFood, xp);
        }
    }

    private static class BlockLootProvider extends SimpleFabricLootTableProvider
    {
        private BlockLootProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator, LootContextParamSets.BLOCK);
        }

        //@formatter:off
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
        {
            var waterPredicate = LocationPredicate.Builder.location().setFluid(FluidPredicate.Builder.fluid().of(FluidTags.WATER).build());
            var waterSurrounded = LocationCheck.checkLocation(waterPredicate, new BlockPos(1, 0, 0))
                    .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(-1, 0, 0)))
                    .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 0, 1)))
                    .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 0, -1)))
                    .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 1, 0)));

            consumer.accept(FOTLootManager.EARTHWORMS_DROPS, LootTable.lootTable().withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(FOTItems.EARTHWORMS)
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                    .when(BlockLoot.HAS_NO_SILK_TOUCH)
                    .when(waterSurrounded.invert())
            ));

            consumer.accept(FOTLootManager.GRUBS_DROPS, LootTable.lootTable().withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(FOTItems.GRUBS)
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                    .when(BlockLoot.HAS_NO_SILK_TOUCH)
                    .when(waterSurrounded.invert())
            ));

            consumer.accept(FOTLootManager.LEECHES_DROPS, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .add(LootItem.lootTableItem(FOTItems.LEECHES)
                                    .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                            .when(BlockLoot.HAS_NO_SILK_TOUCH)
                            .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiome(BiomeTags.IS_BEACH).setContinentalness(Continentalness.COAST)).or(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiome(BiomeTags.IS_RIVER))))
                            .when(waterSurrounded))
                    .withPool(LootPool.lootPool()
                            .add(LootItem.lootTableItem(FOTItems.LEECHES)
                                    .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                            .when(BlockLoot.HAS_NO_SILK_TOUCH)
                            .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiome(FOTTags.Biomes.ALWAYS_DROP_LEECHES)))));

            consumer.accept(FOTBlocks.FISH_BONE.getLootTable(), BlockLoot.createSingleItemTable(FOTBlocks.FISH_BONE));
        }
        //@formatter:on
    }

    private static class EntityLootProvider extends SimpleFabricLootTableProvider
    {
        private static final EntityPredicate.Builder TROPHY = EntityPredicate.Builder.entity().nbt(new NbtPredicate(Util.make(new CompoundTag(), tag -> tag.putBoolean(ThievesFish.TROPHY_TAG, true))));

        private EntityLootProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator, LootContextParamSets.ENTITY);
        }

        //@formatter:off
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
        {
            consumer.accept(FOTLootManager.FISH_BONE_DROP, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));

            consumer.accept(FOTEntities.SPLASHTAIL.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.SPLASHTAIL)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
            consumer.accept(FOTEntities.PONDIE.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.PONDIE)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
            consumer.accept(FOTEntities.ISLEHOPPER.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.ISLEHOPPER)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
            consumer.accept(FOTEntities.ANCIENTSCALE.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
            consumer.accept(FOTEntities.PLENTIFIN.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.PLENTIFIN)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
            consumer.accept(FOTEntities.WILDSPLASH.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.WILDSPLASH)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
            consumer.accept(FOTEntities.DEVILFISH.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.DEVILFISH)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
            consumer.accept(FOTEntities.BATTLEGILL.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.BATTLEGILL)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
            consumer.accept(FOTEntities.WRECKER.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.WRECKER)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
            consumer.accept(FOTEntities.STORMFISH.getDefaultLootTable(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTItems.STORMFISH)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
        }
    }
    //@formatter:on

    private static class ChestLootProvider extends SimpleFabricLootTableProvider
    {
        private ChestLootProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator, LootContextParamSets.CHEST);
        }

        //@formatter:off
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
        {
            consumer.accept(FOTLootManager.SEAPOST_BARREL_SUPPLY, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(4.0F, 12.0F))
                            .add(LootItem.lootTableItem(Items.SUSPICIOUS_STEW).setWeight(10)
                                    .apply(SetStewEffectFunction.stewEffect()
                                            .withEffect(MobEffects.NIGHT_VISION, UniformGenerator.between(7.0F, 10.0F))
                                            .withEffect(MobEffects.JUMP, UniformGenerator.between(7.0F, 10.0F))
                                            .withEffect(MobEffects.WEAKNESS, UniformGenerator.between(6.0F, 8.0F))
                                            .withEffect(MobEffects.BLINDNESS, UniformGenerator.between(5.0F, 7.0F))
                                            .withEffect(MobEffects.POISON, UniformGenerator.between(10.0F, 20.0F))
                                            .withEffect(MobEffects.SATURATION, UniformGenerator.between(7.0F, 10.0F))))
                            .add(LootItem.lootTableItem(Items.APPLE).setWeight(9)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                            .add(LootItem.lootTableItem(Items.CARROT).setWeight(9)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                            .add(LootItem.lootTableItem(Items.POTATO).setWeight(9)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                            .add(LootItem.lootTableItem(Items.OAK_LOG).setWeight(8)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                            .add(LootItem.lootTableItem(Items.BAMBOO).setWeight(7)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 24.0F))))
                            .add(TagEntry.expandTag(FOTTags.Items.WORMS).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 16.0F))))
                            .add(TagEntry.expandTag(FOTTags.Items.THIEVES_FISH).setWeight(3)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                            .add(LootItem.lootTableItem(Items.MAP).setWeight(1)
                                    .apply(ExplorationMapFunction.makeExplorationMap()
                                            .setDestination(StructureTags.ON_TREASURE_MAPS)
                                            .setMapDecoration(MapDecoration.Type.RED_X)
                                            .setZoom((byte)1)
                                            .setSkipKnownStructures(false)))));

            consumer.accept(FOTLootManager.SEAPOST_BARREL_COMBAT, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(2.0F, 6.0F))
                            .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                            .add(LootItem.lootTableItem(Items.FIRE_CHARGE).setWeight(4)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                            .add(LootItem.lootTableItem(Items.TNT).setWeight(2)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));

            consumer.accept(FOTLootManager.SEAPOST_BARREL_FIREWORK, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(2.0F, 4.0F))
                            .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET).setWeight(3)
                                    .apply(SetRandomFireworkFunction.builder()
                                            .withColor(DyeColor.RED)
                                            .withColor(DyeColor.ORANGE)
                                            .withColor(DyeColor.YELLOW)
                                            .withColor(DyeColor.LIME)
                                            .withColor(DyeColor.BLUE)
                                            .withColor(DyeColor.CYAN)
                                            .withColor(DyeColor.LIGHT_BLUE)
                                            .withColor(DyeColor.PURPLE)
                                            .withColor(DyeColor.MAGENTA)
                                            .withColor(DyeColor.WHITE)
                                            .withColor(6942120) // athena
                                    )
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))));
        }
        //@formatter:on
    }

    private static class BlockTagsProvider extends FabricTagProvider.BlockTagProvider
    {
        private BlockTagsProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator);
        }

        @Override
        protected void generateTags()
        {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(FOTBlocks.FISH_BONE);

            this.getOrCreateTagBuilder(FOTTags.Blocks.FIRELIGHT_DEVILFISH_WARM_BLOCKS).add(Blocks.MAGMA_BLOCK);
            this.getOrCreateTagBuilder(FOTTags.Blocks.CORAL_WILDSPLASH_SPAWNABLE_ON).forceAddTag(BlockTags.CORALS).forceAddTag(BlockTags.CORAL_BLOCKS).forceAddTag(BlockTags.WALL_CORALS);
            this.getOrCreateTagBuilder(FOTTags.Blocks.EARTHWORMS_DROPS).forceAddTag(BlockTags.DIRT);
            this.getOrCreateTagBuilder(FOTTags.Blocks.GRUBS_DROPS).forceAddTag(BlockTags.SAND);
            this.getOrCreateTagBuilder(FOTTags.Blocks.LEECHES_DROPS).forceAddTag(BlockTags.SAND).add(Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS);
            this.getOrCreateTagBuilder(FOTTags.Blocks.EARTHWORMS_DROP_BLACKLIST).add(Blocks.MOSS_BLOCK, Blocks.COARSE_DIRT, Blocks.MYCELIUM, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS);
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
            var rawFishes = new Item[] {FOTItems.SPLASHTAIL, FOTItems.PONDIE, FOTItems.ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.WILDSPLASH, FOTItems.DEVILFISH, FOTItems.BATTLEGILL, FOTItems.WRECKER, FOTItems.STORMFISH};
            var cookedFishes = new Item[] {FOTItems.COOKED_SPLASHTAIL, FOTItems.COOKED_PONDIE, FOTItems.COOKED_ISLEHOPPER, FOTItems.COOKED_ANCIENTSCALE, FOTItems.COOKED_PLENTIFIN, FOTItems.COOKED_WILDSPLASH, FOTItems.COOKED_DEVILFISH, FOTItems.COOKED_BATTLEGILL, FOTItems.COOKED_WRECKER, FOTItems.COOKED_STORMFISH};

            this.getOrCreateTagBuilder(ItemTags.AXOLOTL_TEMPT_ITEMS).add(FISH_BUCKETS).forceAddTag(FOTTags.Items.WORMS);
            this.getOrCreateTagBuilder(ItemTags.FISHES).forceAddTag(FOTTags.Items.THIEVES_FISH).forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH);

            this.getOrCreateTagBuilder(FOTTags.Items.THIEVES_FISH_BUCKET).add(FISH_BUCKETS);
            this.getOrCreateTagBuilder(FOTTags.Items.THIEVES_FISH).add(rawFishes);
            this.getOrCreateTagBuilder(FOTTags.Items.COOKED_THIEVES_FISH).add(cookedFishes);
            this.getOrCreateTagBuilder(FOTTags.Items.WORMS).forceAddTag(FOTTags.Items.EARTHWORMS_FOOD).forceAddTag(FOTTags.Items.GRUBS_FOOD).forceAddTag(FOTTags.Items.LEECHES_FOOD);
            this.getOrCreateTagBuilder(FOTTags.Items.EARTHWORMS_FOOD).add(FOTItems.EARTHWORMS);
            this.getOrCreateTagBuilder(FOTTags.Items.GRUBS_FOOD).add(FOTItems.GRUBS);
            this.getOrCreateTagBuilder(FOTTags.Items.LEECHES_FOOD).add(FOTItems.LEECHES);

            // Fabric
            this.getOrCreateTagBuilder(RAW_FISHES).forceAddTag(FOTTags.Items.THIEVES_FISH);
            this.getOrCreateTagBuilder(COOKED_FISHES).forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH);

            // Croptopia compatibility
            this.getOrCreateTagBuilder(CROPTOPIA_FISHES).forceAddTag(FOTTags.Items.THIEVES_FISH);
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
            var neutralFishes = new EntityType<?>[] {FOTEntities.DEVILFISH, FOTEntities.BATTLEGILL, FOTEntities.WRECKER};
            var fishes = new EntityType<?>[] {FOTEntities.SPLASHTAIL, FOTEntities.PONDIE, FOTEntities.ISLEHOPPER, FOTEntities.ANCIENTSCALE, FOTEntities.PLENTIFIN, FOTEntities.WILDSPLASH, FOTEntities.STORMFISH};
            this.tag(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add(ArrayUtils.removeElements(fishes, neutralFishes));
            this.getOrCreateTagBuilder(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE).add(ArrayUtils.addAll(fishes, neutralFishes));
            this.getOrCreateTagBuilder(FOTTags.EntityTypes.FISH_BONE_DROP).add(EntityType.COD, EntityType.SALMON, EntityType.TROPICAL_FISH);

            // Immersive Weathering compatibility
            this.getOrCreateTagBuilder(FREEZING_WATER_IMMUNE).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
            this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        }
    }

    private static class BiomeTagsProvider extends FabricTagProvider.DynamicRegistryTagProvider<Biome>
    {
        public BiomeTagsProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator, Registry.BIOME_REGISTRY);
        }

        @Override
        protected void generateTags()
        {
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_SPLASHTAILS).forceAddTag(BiomeTags.IS_OCEAN);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_PONDIES).forceAddTag(BiomeTags.IS_RIVER).forceAddTag(BiomeTags.IS_FOREST);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_ISLEHOPPERS).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_BEACH).forceAddTag(BiomeTags.IS_JUNGLE).forceAddTag(BiomeTags.HAS_CLOSER_WATER_FOG).add(Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_ANCIENTSCALES).add(Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_PLENTIFINS).add(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_WILDSPLASH).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_BEACH).forceAddTag(BiomeTags.IS_JUNGLE).forceAddTag(BiomeTags.HAS_CLOSER_WATER_FOG).add(Biomes.LUSH_CAVES, Biomes.WARM_OCEAN);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_DEVILFISH).forceAddTag(BiomeTags.IS_OVERWORLD);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_BATTLEGILLS).forceAddTag(BiomeTags.IS_OVERWORLD);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_WRECKERS).forceAddTag(BiomeTags.IS_OCEAN);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_STORMFISH).forceAddTag(BiomeTags.IS_OCEAN).add(Biomes.SPARSE_JUNGLE);

            this.getOrCreateTagBuilder(FOTTags.Biomes.DEVILFISH_CANNOT_SPAWN).add(Biomes.LUSH_CAVES, Biomes.DEEP_DARK);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_SAND_BATTLEGILLS).add(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_MOSS_ISLEHOPPERS).forceAddTag(BiomeTags.IS_JUNGLE).forceAddTag(BiomeTags.HAS_CLOSER_WATER_FOG).add(Biomes.LUSH_CAVES);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_WILD_STORMFISH).add(Biomes.SPARSE_JUNGLE);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_CORAL_WILDSPLASH).add(Biomes.WARM_OCEAN);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_SNOW_WRECKERS).add(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_SANDY_WILDSPLASH).forceAddTag(BiomeTags.IS_BEACH);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_OCEAN_WILDSPLASH).forceAddTag(BiomeTags.IS_OCEAN);
            this.getOrCreateTagBuilder(FOTTags.Biomes.SPAWNS_MUDDY_WILDSPLASH).forceAddTag(BiomeTags.HAS_CLOSER_WATER_FOG);
            this.getOrCreateTagBuilder(FOTTags.Biomes.ALWAYS_DROP_LEECHES).add(Biomes.MANGROVE_SWAMP);
            this.getOrCreateTagBuilder(FOTTags.Biomes.HAS_SEAPOST).add(Biomes.OCEAN, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN);
        }
    }

    private static class StructureTagsProvider extends FabricTagProvider.DynamicRegistryTagProvider<Structure>
    {
        public StructureTagsProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator, Registry.STRUCTURE_REGISTRY);
        }

        @Override
        protected void generateTags()
        {
            this.getOrCreateTagBuilder(FOTTags.Structures.BONE_ANCIENTSCALES_SPAWN_IN).add(BuiltinStructures.STRONGHOLD).forceAddTag(StructureTags.MINESHAFT);
            this.getOrCreateTagBuilder(FOTTags.Structures.BONEDUST_PLENTIFINS_SPAWN_IN).add(BuiltinStructures.STRONGHOLD).forceAddTag(StructureTags.MINESHAFT);
            this.getOrCreateTagBuilder(FOTTags.Structures.BATTLEGILLS_SPAWN_IN).add(BuiltinStructures.OCEAN_MONUMENT, BuiltinStructures.PILLAGER_OUTPOST);
            this.getOrCreateTagBuilder(FOTTags.Structures.ANCIENTSCALES_SPAWN_IN).forceAddTag(StructureTags.OCEAN_RUIN).add(BuiltinStructures.STRONGHOLD).forceAddTag(StructureTags.MINESHAFT);
            this.getOrCreateTagBuilder(FOTTags.Structures.PLENTIFINS_SPAWN_IN).add(BuiltinStructures.STRONGHOLD).forceAddTag(StructureTags.MINESHAFT);
            this.getOrCreateTagBuilder(FOTTags.Structures.WRECKERS_SPAWN_IN).add(BuiltinStructures.SHIPWRECK, BuiltinStructures.RUINED_PORTAL_OCEAN);
            this.getOrCreateTagBuilder(FOTTags.Structures.WRECKERS_LOCATED).add(BuiltinStructures.SHIPWRECK, BuiltinStructures.RUINED_PORTAL_OCEAN);
        }
    }

    private static class AdvancementProvider extends FabricAdvancementProvider
    {
        private static final Map<Item, Registry<?>> BUCKET_TO_VARIANTS_MAP = Util.make(Maps.newHashMap(), map ->
        {
            map.put(FOTItems.SPLASHTAIL_BUCKET, FOTRegistry.SPLASHTAIL_VARIANT);
            map.put(FOTItems.PONDIE_BUCKET, FOTRegistry.PONDIE_VARIANT);
            map.put(FOTItems.ISLEHOPPER_BUCKET, FOTRegistry.ISLEHOPPER_VARIANT);
            map.put(FOTItems.ANCIENTSCALE_BUCKET, FOTRegistry.ANCIENTSCALE_VARIANT);
            map.put(FOTItems.PLENTIFIN_BUCKET, FOTRegistry.PLENTIFIN_VARIANT);
            map.put(FOTItems.WILDSPLASH_BUCKET, FOTRegistry.WILDSPLASH_VARIANT);
            map.put(FOTItems.DEVILFISH_BUCKET, FOTRegistry.DEVILFISH_VARIANT);
            map.put(FOTItems.BATTLEGILL_BUCKET, FOTRegistry.BATTLEGILL_VARIANT);
            map.put(FOTItems.WRECKER_BUCKET, FOTRegistry.WRECKER_VARIANT);
            map.put(FOTItems.STORMFISH_BUCKET, FOTRegistry.STORMFISH_VARIANT);
        });

        private AdvancementProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator);
        }

        //@formatter:off
        @Override
        public void generateAdvancement(Consumer<Advancement> consumer)
        {
            var sallyName = Util.make(new CompoundTag(), compound ->
            {
                var displayCompound = new CompoundTag();
                displayCompound.putString(ItemStack.TAG_DISPLAY_NAME, Component.Serializer.toJson(Component.literal("Sally")));
                compound.put(ItemStack.TAG_DISPLAY, displayCompound);
            });

            var advancement = Advancement.Builder.advancement()
                    .display(FOTItems.SPLASHTAIL,
                            Component.translatable("advancements.fot.root.title"),
                            Component.translatable("advancements.fot.root.description"),
                            new ResourceLocation("textures/block/tube_coral_block.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion("in_water", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location()
                                    .setFluid(FluidPredicate.Builder.fluid()
                                            .of(FluidTags.WATER).build()).build()))
                    .save(consumer, this.mod("root"));

            var advancement2 = this.addFishBuckets(Advancement.Builder.advancement().parent(advancement))
                    .display(FOTItems.SPLASHTAIL_BUCKET,
                            Component.translatable("advancements.fot.fish_collectors.title"),
                            Component.translatable("advancements.fot.fish_collectors.description"),
                            null, FrameType.CHALLENGE, true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(250))
                    .save(consumer, this.mod("fish_collectors"));

            this.addFishVariantsBuckets(Advancement.Builder.advancement().parent(advancement2), false)
                    .display(FOTItems.SPLASHTAIL_BUCKET,
                            Component.translatable("advancements.fot.master_fish_collectors.title"),
                            Component.translatable("advancements.fot.master_fish_collectors.description"),
                            null, FrameType.CHALLENGE, true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(1000))
                    .save(consumer, this.mod("master_fish_collectors"));

            this.addFishVariantsBuckets(Advancement.Builder.advancement().parent(advancement2), true)
                    .display(FOTItems.SPLASHTAIL_BUCKET,
                            Component.translatable("advancements.fot.legendary_fish_collectors.title"),
                            Component.translatable("advancements.fot.legendary_fish_collectors.description"),
                            null, FrameType.CHALLENGE, true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(2000))
                    .save(consumer, this.mod("legendary_fish_collectors"));

            Advancement.Builder.advancement().parent(advancement).addCriterion(Registry.ITEM.getKey(FOTItems.DEVILFISH_BUCKET).getPath(),
                            PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(EntityPredicate.Composite.ANY,
                                    ItemPredicate.Builder.item().of(FOTItems.DEVILFISH_BUCKET).hasNbt(Util.make(new CompoundTag(), compound -> compound.putString(ThievesFish.VARIANT_TAG, FOTRegistry.DEVILFISH_VARIANT.getKey(DevilfishVariants.LAVA).toString()))),
                                    EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(EntityType.AXOLOTL).build())))
                    .display(FOTItems.DEVILFISH,
                            Component.translatable("advancements.fot.feed_axolotl_with_lava_devilfish.title"),
                            Component.translatable("advancements.fot.feed_axolotl_with_lava_devilfish.description"),
                            null, FrameType.TASK, true, true, false)
                    .save(consumer, this.mod("feed_axolotl_with_lava_devilfish"));

            var battlegill = Registry.ITEM.getKey(FOTItems.BATTLEGILL).getPath();
            Advancement.Builder.advancement().parent(advancement).requirements(RequirementsStrategy.OR)
                    .addCriterion(battlegill + "_village_plains",
                            FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inStructure(BuiltinStructures.VILLAGE_PLAINS)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                    .addCriterion(battlegill + "_village_desert",
                            FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inStructure(BuiltinStructures.VILLAGE_DESERT)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                    .addCriterion(battlegill + "_village_savanna",
                            FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inStructure(BuiltinStructures.VILLAGE_SAVANNA)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                    .addCriterion(battlegill + "_village_snowy",
                            FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inStructure(BuiltinStructures.VILLAGE_SNOWY)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                    .addCriterion(battlegill + "_village_taiga",
                            FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inStructure(BuiltinStructures.VILLAGE_TAIGA)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                    .display(FOTItems.BATTLEGILL,
                            Component.translatable("advancements.fot.so_chill.title"),
                            Component.translatable("advancements.fot.so_chill.description"),
                            null, FrameType.TASK, true, true, false)
                    .save(consumer, this.mod("so_chill"));

            Advancement.Builder.advancement().parent(advancement)
                    .display(FOTItems.STORMFISH,
                            Component.translatable("advancements.fot.lightning_straight_to_my_fish.title"),
                            Component.translatable("advancements.fot.lightning_straight_to_my_fish.description"),
                            null, FrameType.TASK, true, true, false)
                    .addCriterion("lightning_strike_at_stormfish", LightningStrikeTrigger.TriggerInstance.lighthingStrike(EntityPredicate.Builder.entity().distance(DistancePredicate.absolute(MinMaxBounds.Doubles.atMost(16.0))).subPredicate(LighthingBoltPredicate.blockSetOnFire(MinMaxBounds.Ints.exactly(0))).build(), EntityPredicate.Builder.entity().of(FOTEntities.STORMFISH).build()))
                    .save(consumer, this.mod("lightning_straight_to_my_fish"));

            Advancement.Builder.advancement().parent(advancement)
                    .display(Items.SPYGLASS,
                            Component.translatable("advancements.fot.spyglass_at_plentifins.title"),
                            Component.translatable("advancements.fot.spyglass_at_plentifins.description"),
                            null, FrameType.TASK, true, true, false)
                    .addCriterion("spyglass_at_plentifins", UsingItemTrigger.TriggerInstance.lookingAt(EntityPredicate.Builder.entity().subPredicate(PlayerPredicate.Builder.player().setLookingAt(EntityPredicate.Builder.entity().of(FOTEntities.PLENTIFIN).build()).build()), ItemPredicate.Builder.item().of(Items.SPYGLASS)))
                    .save(consumer, this.mod("spyglass_at_plentifins"));

            Advancement.Builder.advancement().parent(advancement).requirements(RequirementsStrategy.OR)
                    .display(Items.JUKEBOX,
                            Component.translatable("advancements.fot.play_jukebox_near_fish.title"),
                            Component.translatable("advancements.fot.play_jukebox_near_fish.description"),
                            null, FrameType.TASK, true, true, true)
                    .addCriterion("play_jukebox_near_thieves_fish", ItemUsedOnBlockWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location()
                                    .setBlock(BlockPredicate.Builder.block().of(Blocks.JUKEBOX).build()),
                            ItemPredicate.Builder.item().of(ItemTags.MUSIC_DISCS),
                            EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE).build())))
                    .addCriterion("play_jukebox_near_fish", ItemUsedOnBlockWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location()
                                    .setBlock(BlockPredicate.Builder.block().of(Blocks.JUKEBOX).build()),
                            ItemPredicate.Builder.item().of(ItemTags.MUSIC_DISCS),
                            EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(EntityTypeTags.AXOLOTL_HUNT_TARGETS).build())))
                    .save(consumer, this.mod("play_jukebox_near_fish"));

            Advancement.Builder.advancement().parent(advancement).requirements(RequirementsStrategy.OR)
                    .addCriterion(Registry.ITEM.getKey(Items.NAME_TAG).getPath(), PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(EntityPredicate.Composite.ANY,
                            ItemPredicate.Builder.item().of(Items.NAME_TAG).hasNbt(sallyName),
                            EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(EntityType.SALMON).build())))
                    .addCriterion(Registry.ITEM.getKey(Items.SALMON_BUCKET).getPath(), new PlacedBlockTrigger.TriggerInstance(EntityPredicate.Composite.ANY, Blocks.WATER, StatePropertiesPredicate.ANY, LocationPredicate.ANY, ItemPredicate.Builder.item().of(Items.SALMON_BUCKET).hasNbt(sallyName).build()))
                    .display(Items.SALMON,
                            Component.translatable("advancements.fot.lost_sally.title"),
                            Component.translatable("advancements.fot.lost_sally.description"),
                            null, FrameType.TASK, true, true, true)
                    .save(consumer, this.mod("lost_sally"));
        }
        //@formatter:on

        private String mod(String name)
        {
            return FishOfThieves.MOD_RESOURCES + name;
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
                for (var variant : Sets.newTreeSet(BUCKET_TO_VARIANTS_MAP.get(item).keySet()))
                {
                    builder.addCriterion(variant.getPath() + "_" + Registry.ITEM.getKey(item).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(item).hasNbt(Util.make(new CompoundTag(), compound ->
                    {
                        compound.putString(ThievesFish.VARIANT_TAG, variant.toString());
                        compound.putBoolean(ThievesFish.TROPHY_TAG, trophy);

                        if (trophy)
                        {
                            compound.putBoolean(ThievesFish.HAS_FED_TAG, false);
                        }
                    })).build()));
                }
            }
            return builder;
        }
    }
}