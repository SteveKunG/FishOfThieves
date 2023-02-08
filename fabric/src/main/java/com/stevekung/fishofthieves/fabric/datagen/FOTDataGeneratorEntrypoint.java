package com.stevekung.fishofthieves.fabric.datagen;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.commons.lang3.ArrayUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.fabric.datagen.variant.*;
import com.stevekung.fishofthieves.loot.function.FOTLocationCheck;
import com.stevekung.fishofthieves.loot.function.FOTTagEntry;
import com.stevekung.fishofthieves.loot.function.FishVariantLootConfigCondition;
import com.stevekung.fishofthieves.loot.function.SetRandomFireworkFunction;
import com.stevekung.fishofthieves.loot.predicate.FOTLocationPredicate;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.*;
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
import net.minecraft.data.models.model.*;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.*;
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
        dataGenerator.addProvider(CustomBlockLootProvider::new);
        dataGenerator.addProvider(EntityLootProvider::new);
        dataGenerator.addProvider(ChestLootProvider::new);
        var blockTagsProvider = new BlockTagsProvider(dataGenerator);
        dataGenerator.addProvider(blockTagsProvider);
        dataGenerator.addProvider(provider -> new ItemTagsProvider(dataGenerator, blockTagsProvider));
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
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.SPLASHTAIL, List.of("sunny", "indigo", "umber", "seafoam"), "_splashtail", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.PONDIE, List.of("orchid", "bronze", "bright", "moonsky"), "_pondie", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.ISLEHOPPER, List.of("moss", "honey", "raven", "amethyst"), "_islehopper", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.ANCIENTSCALE, List.of("sapphire", "smoke", "bone", "starshine"), "_ancientscale", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.PLENTIFIN, List.of("amber", "cloudy", "bonedust", "watery"), "_plentifin", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.WILDSPLASH, List.of("sandy", "ocean", "muddy", "coral"), "_wildsplash", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.DEVILFISH, List.of("seashell", "lava", "forsaken", "firelight"), "_devilfish", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.BATTLEGILL, List.of("sky", "rum", "sand", "bittersweet"), "_battlegill", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.WRECKER, List.of("sun", "blackcloud", "snow", "moon"), "_wrecker", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.STORMFISH, List.of("shores", "wild", "shadow", "twilight"), "_stormfish", generator.output);

            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.SPLASHTAIL_BUCKET, List.of("sunny", "indigo", "umber", "seafoam"), "_splashtail_bucket", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.PONDIE_BUCKET, List.of("orchid", "bronze", "bright", "moonsky"), "_pondie_bucket", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.ISLEHOPPER_BUCKET, List.of("moss", "honey", "raven", "amethyst"), "_islehopper_bucket", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.ANCIENTSCALE_BUCKET, List.of("sapphire", "smoke", "bone", "starshine"), "_ancientscale_bucket", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.PLENTIFIN_BUCKET, List.of("amber", "cloudy", "bonedust", "watery"), "_plentifin_bucket", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.WILDSPLASH_BUCKET, List.of("sandy", "ocean", "muddy", "coral"), "_wildsplash_bucket", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.DEVILFISH_BUCKET, List.of("seashell", "lava", "forsaken", "firelight"), "_devilfish_bucket", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.BATTLEGILL_BUCKET, List.of("sky", "rum", "sand", "bittersweet"), "_battlegill_bucket", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.WRECKER_BUCKET, List.of("sun", "blackcloud", "snow", "moon"), "_wrecker_bucket", generator.output);
            ExtendedModelTemplate.generateFlatItemWithCustomModelData(FOTItems.STORMFISH_BUCKET, List.of("shores", "wild", "shadow", "twilight"), "_stormfish_bucket", generator.output);

            generator.generateFlatItem(FOTItems.EARTHWORMS, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.GRUBS, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.LEECHES, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.COOKED_SPLASHTAIL, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.SPLASHTAIL_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.COOKED_PONDIE, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.PONDIE_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.COOKED_ISLEHOPPER, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.ISLEHOPPER_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.COOKED_ANCIENTSCALE, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.ANCIENTSCALE_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.COOKED_PLENTIFIN, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.PLENTIFIN_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.COOKED_WILDSPLASH, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.WILDSPLASH_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.COOKED_DEVILFISH, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.DEVILFISH_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.COOKED_BATTLEGILL, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.BATTLEGILL_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.COOKED_WRECKER, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.WRECKER_SPAWN_EGG, SPAWN_EGG);
            generator.generateFlatItem(FOTItems.COOKED_STORMFISH, ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTItems.STORMFISH_SPAWN_EGG, SPAWN_EGG);

            generator.generateFlatItem(FOTBlocks.FISH_BONE.asItem(), ModelTemplates.FLAT_ITEM);

            generator.generateFlatItem(FOTBlocks.OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.SPRUCE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.BIRCH_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.JUNGLE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.ACACIA_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.DARK_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.MANGROVE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.CRIMSON_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.WARPED_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);

            generator.generateFlatItem(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);

            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);

            generator.generateFlatItem(FOTBlocks.GILDED_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_WARPED_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators generator)
        {
            var fishBone = FOTBlocks.FISH_BONE;
            generator.skipAutoItemBlock(fishBone);
            generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(fishBone, ModelLocationUtils.getModelLocation(fishBone)).with(BlockModelGenerators.createHorizontalFacingDispatchAlt()));

            this.createFishPlaque(FOTBlocks.OAK_FISH_PLAQUE, Blocks.OAK_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.SPRUCE_FISH_PLAQUE, Blocks.SPRUCE_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.BIRCH_FISH_PLAQUE, Blocks.BIRCH_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.JUNGLE_FISH_PLAQUE, Blocks.JUNGLE_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.ACACIA_FISH_PLAQUE, Blocks.ACACIA_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.DARK_OAK_FISH_PLAQUE, Blocks.DARK_OAK_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.MANGROVE_FISH_PLAQUE, Blocks.MANGROVE_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.CRIMSON_FISH_PLAQUE, Blocks.CRIMSON_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.WARPED_FISH_PLAQUE, Blocks.WARPED_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);

            this.createFishPlaque(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, Blocks.OAK_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, Blocks.SPRUCE_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, Blocks.BIRCH_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, Blocks.JUNGLE_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, Blocks.ACACIA_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, Blocks.DARK_OAK_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, Blocks.MANGROVE_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, Blocks.CRIMSON_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, Blocks.WARPED_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);

            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, Blocks.OAK_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, Blocks.SPRUCE_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, Blocks.BIRCH_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, Blocks.JUNGLE_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, Blocks.ACACIA_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, Blocks.DARK_OAK_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, Blocks.MANGROVE_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, Blocks.CRIMSON_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, Blocks.WARPED_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);

            this.createFishPlaque(FOTBlocks.GILDED_OAK_FISH_PLAQUE, Blocks.OAK_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, Blocks.SPRUCE_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, Blocks.BIRCH_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, Blocks.JUNGLE_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, Blocks.ACACIA_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, Blocks.DARK_OAK_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, Blocks.MANGROVE_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, Blocks.CRIMSON_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_WARPED_FISH_PLAQUE, Blocks.WARPED_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
        }

        private void createFishPlaque(Block block, Block planks, ModelTemplate template, BlockModelGenerators generator)
        {
            generator.skipAutoItemBlock(block);
            var textureMapping = this.planks(planks);
            var resourceLocation = template.create(block, textureMapping, generator.modelOutput);
            generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, resourceLocation).with(BlockModelGenerators.createHorizontalFacingDispatch()));
        }

        private TextureMapping planks(Block planks)
        {
            return new TextureMapping().put(FOTModelTemplates.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks));
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

            addWoodenFishPlaqueRecipe(FOTBlocks.OAK_FISH_PLAQUE, Items.OAK_PLANKS, consumer);
            addWoodenFishPlaqueRecipe(FOTBlocks.SPRUCE_FISH_PLAQUE, Items.SPRUCE_PLANKS, consumer);
            addWoodenFishPlaqueRecipe(FOTBlocks.BIRCH_FISH_PLAQUE, Items.BIRCH_PLANKS, consumer);
            addWoodenFishPlaqueRecipe(FOTBlocks.JUNGLE_FISH_PLAQUE, Items.JUNGLE_PLANKS, consumer);
            addWoodenFishPlaqueRecipe(FOTBlocks.ACACIA_FISH_PLAQUE, Items.ACACIA_PLANKS, consumer);
            addWoodenFishPlaqueRecipe(FOTBlocks.DARK_OAK_FISH_PLAQUE, Items.DARK_OAK_PLANKS, consumer);
            addWoodenFishPlaqueRecipe(FOTBlocks.MANGROVE_FISH_PLAQUE, Items.MANGROVE_PLANKS, consumer);
            addWoodenFishPlaqueRecipe(FOTBlocks.CRIMSON_FISH_PLAQUE, Items.CRIMSON_PLANKS, consumer);
            addWoodenFishPlaqueRecipe(FOTBlocks.WARPED_FISH_PLAQUE, Items.WARPED_PLANKS, consumer);

            addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE, consumer);
            addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, consumer);
            addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, consumer);
            addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, consumer);
            addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, consumer);
            addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, consumer);
            addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, consumer);
            addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, consumer);
            addIronFrameFishPlaqueRecipe(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE, consumer);

            addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE, consumer);
            addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, consumer);
            addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, consumer);
            addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, consumer);
            addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, consumer);
            addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, consumer);
            addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, consumer);
            addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, consumer);
            addGoldenFrameFishPlaqueRecipe(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE, consumer);

            addGildedFishPlaqueRecipe(FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.OAK_FISH_PLAQUE, consumer);
            addGildedFishPlaqueRecipe(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, consumer);
            addGildedFishPlaqueRecipe(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, consumer);
            addGildedFishPlaqueRecipe(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, consumer);
            addGildedFishPlaqueRecipe(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, consumer);
            addGildedFishPlaqueRecipe(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, consumer);
            addGildedFishPlaqueRecipe(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, consumer);
            addGildedFishPlaqueRecipe(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, consumer);
            addGildedFishPlaqueRecipe(FOTBlocks.GILDED_WARPED_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE, consumer);

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

        private static void addWoodenFishPlaqueRecipe(Block block, ItemLike baseMaterial, Consumer<FinishedRecipe> consumer)
        {
            ShapedRecipeBuilder.shaped(block, 6).define('P', baseMaterial).define('F', Items.ITEM_FRAME).pattern("PPP").pattern("PFP").pattern("PPP").group("wooden_fish_plaque").unlockedBy(getHasName(Items.ITEM_FRAME), has(Items.ITEM_FRAME)).save(consumer);
        }

        private static void addIronFrameFishPlaqueRecipe(Block block, ItemLike fishPlaque, Consumer<FinishedRecipe> consumer)
        {
            ShapedRecipeBuilder.shaped(block).define('N', Items.IRON_NUGGET).define('F', fishPlaque).pattern("NNN").pattern("NFN").pattern("NNN").group("iron_frame_fish_plaque").unlockedBy(getHasName(fishPlaque), has(fishPlaque)).save(consumer);
        }

        private static void addGoldenFrameFishPlaqueRecipe(Block block, ItemLike fishPlaque, Consumer<FinishedRecipe> consumer)
        {
            ShapedRecipeBuilder.shaped(block).define('G', Items.GOLD_NUGGET).define('F', fishPlaque).pattern("GGG").pattern("GFG").pattern("GGG").group("golden_frame_fish_plaque").unlockedBy(getHasName(fishPlaque), has(fishPlaque)).save(consumer);
        }

        private static void addGildedFishPlaqueRecipe(Block block, ItemLike fishPlaque, Consumer<FinishedRecipe> consumer)
        {
            ShapedRecipeBuilder.shaped(block).define('G', Items.GOLD_INGOT).define('E', Items.EMERALD).define('R', Items.REDSTONE).define('F', fishPlaque).pattern("GEG").pattern("RFR").pattern("GEG").group("gilded_fish_plaque").unlockedBy(getHasName(fishPlaque), has(fishPlaque)).save(consumer);
        }

        private static void addCookingRecipes(Consumer<FinishedRecipe> consumer, float xp, ItemLike rawFood, ItemLike cookedFood)
        {
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(rawFood), cookedFood, xp, 200).unlockedBy(getHasName(rawFood), has(rawFood)).save(consumer);
            simpleCookingRecipe(consumer, "smoking", RecipeSerializer.SMOKING_RECIPE, 100, rawFood, cookedFood, xp);
            simpleCookingRecipe(consumer, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600, rawFood, cookedFood, xp);
        }
    }

    private static class BlockLootProvider extends FabricBlockLootTableProvider
    {
        private BlockLootProvider(FabricDataGenerator dataGenerator)
        {
            super(dataGenerator);
        }

        @Override
        protected void generateBlockLootTables()
        {
            this.add(FOTBlocks.FISH_BONE, createSingleItemTable(FOTBlocks.FISH_BONE));

            this.add(FOTBlocks.OAK_FISH_PLAQUE, createSingleItemTable(FOTBlocks.OAK_FISH_PLAQUE));
            this.add(FOTBlocks.SPRUCE_FISH_PLAQUE, createSingleItemTable(FOTBlocks.SPRUCE_FISH_PLAQUE));
            this.add(FOTBlocks.BIRCH_FISH_PLAQUE, createSingleItemTable(FOTBlocks.BIRCH_FISH_PLAQUE));
            this.add(FOTBlocks.JUNGLE_FISH_PLAQUE, createSingleItemTable(FOTBlocks.JUNGLE_FISH_PLAQUE));
            this.add(FOTBlocks.ACACIA_FISH_PLAQUE, createSingleItemTable(FOTBlocks.ACACIA_FISH_PLAQUE));
            this.add(FOTBlocks.DARK_OAK_FISH_PLAQUE, createSingleItemTable(FOTBlocks.DARK_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.MANGROVE_FISH_PLAQUE, createSingleItemTable(FOTBlocks.MANGROVE_FISH_PLAQUE));
            this.add(FOTBlocks.CRIMSON_FISH_PLAQUE, createSingleItemTable(FOTBlocks.CRIMSON_FISH_PLAQUE));
            this.add(FOTBlocks.WARPED_FISH_PLAQUE, createSingleItemTable(FOTBlocks.WARPED_FISH_PLAQUE));

            this.add(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, createSingleItemTable(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, createSingleItemTable(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, createSingleItemTable(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, createSingleItemTable(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, createSingleItemTable(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, createSingleItemTable(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, createSingleItemTable(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, createSingleItemTable(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, createSingleItemTable(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE));

            this.add(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE));

            this.add(FOTBlocks.GILDED_OAK_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GILDED_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_WARPED_FISH_PLAQUE, createSingleItemTable(FOTBlocks.GILDED_WARPED_FISH_PLAQUE));
        }
    }

    private static class CustomBlockLootProvider extends SimpleFabricLootTableProvider
    {
        private CustomBlockLootProvider(FabricDataGenerator dataGenerator)
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

            consumer.accept(FOTLootTables.Blocks.EARTHWORMS_DROPS, LootTable.lootTable().withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(FOTItems.EARTHWORMS)
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                    .when(BlockLoot.HAS_NO_SILK_TOUCH)
                    .when(waterSurrounded.invert())
            ));

            consumer.accept(FOTLootTables.Blocks.GRUBS_DROPS, LootTable.lootTable().withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(FOTItems.GRUBS)
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                    .when(BlockLoot.HAS_NO_SILK_TOUCH)
                    .when(waterSurrounded.invert())
            ));

            consumer.accept(FOTLootTables.Blocks.LEECHES_DROPS, LootTable.lootTable()
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
            consumer.accept(FOTLootTables.Entities.FISH_BONE_DROP, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));

            simpleFishLoot(FOTEntities.SPLASHTAIL, FOTItems.SPLASHTAIL, consumer,
                    FOTEntitySubPredicate.variant(SplashtailVariants.RUBY),
                    FOTEntitySubPredicate.variant(SplashtailVariants.SUNNY),
                    FOTEntitySubPredicate.variant(SplashtailVariants.INDIGO),
                    FOTEntitySubPredicate.variant(SplashtailVariants.UMBER),
                    FOTEntitySubPredicate.variant(SplashtailVariants.SEAFOAM));

            simpleFishLoot(FOTEntities.PONDIE, FOTItems.PONDIE, consumer,
                    FOTEntitySubPredicate.variant(PondieVariants.CHARCOAL),
                    FOTEntitySubPredicate.variant(PondieVariants.ORCHID),
                    FOTEntitySubPredicate.variant(PondieVariants.BRONZE),
                    FOTEntitySubPredicate.variant(PondieVariants.BRIGHT),
                    FOTEntitySubPredicate.variant(PondieVariants.MOONSKY));

            simpleFishLoot(FOTEntities.ISLEHOPPER, FOTItems.ISLEHOPPER, consumer,
                    FOTEntitySubPredicate.variant(IslehopperVariants.STONE),
                    FOTEntitySubPredicate.variant(IslehopperVariants.MOSS),
                    FOTEntitySubPredicate.variant(IslehopperVariants.HONEY),
                    FOTEntitySubPredicate.variant(IslehopperVariants.RAVEN),
                    FOTEntitySubPredicate.variant(IslehopperVariants.AMETHYST));

            simpleFishLoot(FOTEntities.ANCIENTSCALE, FOTItems.ANCIENTSCALE, consumer,
                    FOTEntitySubPredicate.variant(AncientscaleVariants.ALMOND),
                    FOTEntitySubPredicate.variant(AncientscaleVariants.SAPPHIRE),
                    FOTEntitySubPredicate.variant(AncientscaleVariants.SMOKE),
                    FOTEntitySubPredicate.variant(AncientscaleVariants.BONE),
                    FOTEntitySubPredicate.variant(AncientscaleVariants.STARSHINE));

            simpleFishLoot(FOTEntities.PLENTIFIN, FOTItems.PLENTIFIN, consumer,
                    FOTEntitySubPredicate.variant(PlentifinVariants.OLIVE),
                    FOTEntitySubPredicate.variant(PlentifinVariants.AMBER),
                    FOTEntitySubPredicate.variant(PlentifinVariants.CLOUDY),
                    FOTEntitySubPredicate.variant(PlentifinVariants.BONEDUST),
                    FOTEntitySubPredicate.variant(PlentifinVariants.WATERY));

            simpleFishLoot(FOTEntities.WILDSPLASH, FOTItems.WILDSPLASH, consumer,
                    FOTEntitySubPredicate.variant(WildsplashVariants.RUSSET),
                    FOTEntitySubPredicate.variant(WildsplashVariants.SANDY),
                    FOTEntitySubPredicate.variant(WildsplashVariants.OCEAN),
                    FOTEntitySubPredicate.variant(WildsplashVariants.MUDDY),
                    FOTEntitySubPredicate.variant(WildsplashVariants.CORAL));

            simpleFishLoot(FOTEntities.DEVILFISH, FOTItems.DEVILFISH, consumer,
                    FOTEntitySubPredicate.variant(DevilfishVariants.ASHEN),
                    FOTEntitySubPredicate.variant(DevilfishVariants.SEASHELL),
                    FOTEntitySubPredicate.variant(DevilfishVariants.LAVA),
                    FOTEntitySubPredicate.variant(DevilfishVariants.FORSAKEN),
                    FOTEntitySubPredicate.variant(DevilfishVariants.FIRELIGHT));

            simpleFishLoot(FOTEntities.BATTLEGILL, FOTItems.BATTLEGILL, consumer,
                    FOTEntitySubPredicate.variant(BattlegillVariants.JADE),
                    FOTEntitySubPredicate.variant(BattlegillVariants.SKY),
                    FOTEntitySubPredicate.variant(BattlegillVariants.RUM),
                    FOTEntitySubPredicate.variant(BattlegillVariants.SAND),
                    FOTEntitySubPredicate.variant(BattlegillVariants.BITTERSWEET));

            simpleFishLoot(FOTEntities.WRECKER, FOTItems.WRECKER, consumer,
                    FOTEntitySubPredicate.variant(WreckerVariants.ROSE),
                    FOTEntitySubPredicate.variant(WreckerVariants.SUN),
                    FOTEntitySubPredicate.variant(WreckerVariants.BLACKCLOUD),
                    FOTEntitySubPredicate.variant(WreckerVariants.SNOW),
                    FOTEntitySubPredicate.variant(WreckerVariants.MOON));

            simpleFishLoot(FOTEntities.STORMFISH, FOTItems.STORMFISH, consumer,
                    FOTEntitySubPredicate.variant(StormfishVariants.ANCIENT),
                    FOTEntitySubPredicate.variant(StormfishVariants.SHORES),
                    FOTEntitySubPredicate.variant(StormfishVariants.WILD),
                    FOTEntitySubPredicate.variant(StormfishVariants.SHADOW),
                    FOTEntitySubPredicate.variant(StormfishVariants.TWILIGHT));
        }

        private static void simpleFishLoot(EntityType<?> entityType, Item item, BiConsumer<ResourceLocation, LootTable.Builder> consumer, EntitySubPredicate... subPredicate)
        {
            consumer.accept(entityType.getDefaultLootTable(), simpleFishLoot(item, entityType, subPredicate));
        }

        private static LootTable.Builder simpleFishLoot(Item item, EntityType<?> entityType, EntitySubPredicate... subPredicate)
        {
            return LootTable.lootTable().withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(item)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))
                                    .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(entityType).subPredicate(subPredicate[0])))
                            )
                            .add(dropWithVariant(item, entityType, 1, subPredicate[1]))
                            .add(dropWithVariant(item, entityType, 2, subPredicate[2]))
                            .add(dropWithVariant(item, entityType, 3, subPredicate[3]))
                            .add(dropWithVariant(item, entityType, 4, subPredicate[4]))
                    )
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F)));
        }

        @SuppressWarnings("deprecation")
        private static LootPoolSingletonContainer.Builder<?> dropWithVariant(Item item, EntityType<?> entityType, int variant, EntitySubPredicate subPredicate)
        {
            return LootItem.lootTableItem(item)
                    .apply(SmeltItemFunction.smelted()
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLoot.ENTITY_ON_FIRE)))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, TROPHY)))
                    .apply(SetNbtFunction.setTag(Util.make(new CompoundTag(), tag -> tag.putInt("CustomModelData", variant)))
                            .when(FishVariantLootConfigCondition.configEnabled()))
                    .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(entityType).subPredicate(subPredicate)));
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
            consumer.accept(FOTLootTables.Chests.SEAPOST_BARREL_SUPPLY, LootTable.lootTable()
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
                            .add(FOTTagEntry.expandTag(FOTTags.Items.THIEVES_FISH).setWeight(3)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                            .add(LootItem.lootTableItem(Items.MAP).setWeight(1)
                                    .apply(ExplorationMapFunction.makeExplorationMap()
                                            .setDestination(StructureTags.ON_TREASURE_MAPS)
                                            .setMapDecoration(MapDecoration.Type.RED_X)
                                            .setZoom((byte)1)
                                            .setSkipKnownStructures(false)))));

            consumer.accept(FOTLootTables.Chests.SEAPOST_BARREL_COMBAT, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(2.0F, 6.0F))
                            .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                            .add(LootItem.lootTableItem(Items.FIRE_CHARGE).setWeight(4)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                            .add(LootItem.lootTableItem(Items.TNT).setWeight(2)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));

            consumer.accept(FOTLootTables.Chests.SEAPOST_BARREL_FIREWORK, LootTable.lootTable()
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
            this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).forceAddTag(FOTTags.Blocks.FISH_PLAQUE);

            this.getOrCreateTagBuilder(FOTTags.Blocks.FIRELIGHT_DEVILFISH_WARM_BLOCKS).add(Blocks.MAGMA_BLOCK);
            this.getOrCreateTagBuilder(FOTTags.Blocks.CORAL_WILDSPLASH_SPAWNABLE_ON).forceAddTag(BlockTags.CORALS).forceAddTag(BlockTags.CORAL_BLOCKS).forceAddTag(BlockTags.WALL_CORALS);
            this.getOrCreateTagBuilder(FOTTags.Blocks.AMETHYST_ISLEHOPPER_SPAWNABLE_ON).forceAddTag(BlockTags.CRYSTAL_SOUND_BLOCKS);
            this.getOrCreateTagBuilder(FOTTags.Blocks.EARTHWORMS_DROPS).forceAddTag(BlockTags.DIRT);
            this.getOrCreateTagBuilder(FOTTags.Blocks.GRUBS_DROPS).forceAddTag(BlockTags.SAND);
            this.getOrCreateTagBuilder(FOTTags.Blocks.LEECHES_DROPS).forceAddTag(BlockTags.SAND).add(Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS);
            this.getOrCreateTagBuilder(FOTTags.Blocks.EARTHWORMS_DROP_BLACKLIST).add(Blocks.MOSS_BLOCK, Blocks.COARSE_DIRT, Blocks.MYCELIUM, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS);

            this.getOrCreateTagBuilder(FOTTags.Blocks.WOODEN_FISH_PLAQUE).add(FOTBlocks.OAK_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE);
            this.getOrCreateTagBuilder(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE).add(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE);
            this.getOrCreateTagBuilder(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE).add(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE);
            this.getOrCreateTagBuilder(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE).add(FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, FOTBlocks.GILDED_WARPED_FISH_PLAQUE);
            this.getOrCreateTagBuilder(FOTTags.Blocks.FISH_PLAQUE).forceAddTag(FOTTags.Blocks.WOODEN_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE);
        }
    }

    private static class ItemTagsProvider extends FabricTagProvider.ItemTagProvider
    {
        private ItemTagsProvider(FabricDataGenerator dataGenerator, FabricTagProvider.BlockTagProvider blockTagProvider)
        {
            super(dataGenerator, blockTagProvider);
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
            this.getOrCreateTagBuilder(FOTTags.Items.FISH_PLAQUE_BUCKET_BLACKLIST);

            this.getOrCreateTagBuilder(FOTTags.Items.WOODEN_FISH_PLAQUE).add(FOTBlocks.OAK_FISH_PLAQUE.asItem(), FOTBlocks.SPRUCE_FISH_PLAQUE.asItem(), FOTBlocks.BIRCH_FISH_PLAQUE.asItem(), FOTBlocks.JUNGLE_FISH_PLAQUE.asItem(), FOTBlocks.ACACIA_FISH_PLAQUE.asItem(), FOTBlocks.DARK_OAK_FISH_PLAQUE.asItem(), FOTBlocks.MANGROVE_FISH_PLAQUE.asItem());
            this.copy(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE, FOTTags.Items.IRON_FRAME_FISH_PLAQUE);
            this.copy(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE, FOTTags.Items.GOLDEN_FRAME_FISH_PLAQUE);
            this.copy(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE, FOTTags.Items.GILDED_FRAME_FISH_PLAQUE);

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
            this.getOrCreateTagBuilder(FOTTags.EntityTypes.HORIZONTAL_MOB_RENDER).add(EntityType.PUFFERFISH, EntityType.TADPOLE, EntityType.AXOLOTL);

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
            this.getOrCreateTagBuilder(FOTTags.Biomes.ISLEHOPPER_SPAWN_AT_COAST).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_BEACH);
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