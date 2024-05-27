package com.stevekung.fishofthieves.fabric.datagen;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.commons.lang3.ArrayUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.api.block.fish_plaque.FishPlaqueInteractions;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.loot.function.FOTLocationCheck;
import com.stevekung.fishofthieves.loot.function.FOTTagEntry;
import com.stevekung.fishofthieves.loot.function.FishVariantLootConfigCondition;
import com.stevekung.fishofthieves.loot.predicate.FOTLocationPredicate;
import com.stevekung.fishofthieves.loot.predicate.TrophyFishPredicate;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.*;
import com.stevekung.fishofthieves.trigger.ItemUsedOnLocationWithNearbyEntityTrigger;
import com.stevekung.fishofthieves.utils.Continentalness;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.Util;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.*;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
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
    private static final TagKey<Item> RAW_FISHES = TagKey.create(Registries.ITEM, new ResourceLocation("fabric", "raw_fishes"));
    private static final TagKey<Item> COOKED_FISHES = TagKey.create(Registries.ITEM, new ResourceLocation("fabric", "cooked_fishes"));

    // Croptopia
    private static final TagKey<Item> CROPTOPIA_FISHES = TagKey.create(Registries.ITEM, new ResourceLocation("croptopia", "fishes"));

    // Immersive Weathering
    private static final TagKey<EntityType<?>> FREEZING_WATER_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("immersive_weathering", "freezing_water_immune"));

    @Override
    public void buildRegistry(RegistrySetBuilder builder)
    {
        builder.add(Registries.STRUCTURE, FOTStructures::bootstrap);
        builder.add(Registries.STRUCTURE_SET, FOTStructures.Sets::bootstrap);
        builder.add(Registries.CONFIGURED_FEATURE, FOTFeatures::bootstrap);
        builder.add(Registries.PLACED_FEATURE, FOTPlacements::bootstrap);
        builder.add(FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants::bootstrap);
        builder.add(FOTRegistries.PONDIE_VARIANT, PondieVariants::bootstrap);
        builder.add(FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants::bootstrap);
        builder.add(FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants::bootstrap);
        builder.add(FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants::bootstrap);
        builder.add(FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants::bootstrap);
        builder.add(FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants::bootstrap);
        builder.add(FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants::bootstrap);
        builder.add(FOTRegistries.WRECKER_VARIANT, WreckerVariants::bootstrap);
        builder.add(FOTRegistries.STORMFISH_VARIANT, StormfishVariants::bootstrap);
        builder.add(FOTRegistries.FISH_PLAQUE_INTERACTION, FishPlaqueInteractions::bootstrap);
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator)
    {
        var pack = dataGenerator.createPack();
        pack.addProvider(ModelProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(BlockLootProvider::new);
        pack.addProvider(CustomBlockLootProvider::new);
        pack.addProvider(EntityLootProvider::new);
        pack.addProvider(ChestLootProvider::new);
        pack.addProvider(AdvancementRewardProvider::new);
        var blockTagsProvider = pack.addProvider(BlockTagsProvider::new);
        pack.addProvider((dataOutput, provider) -> new ItemTagsProvider(dataOutput, provider, blockTagsProvider));
        pack.addProvider(EntityTagsProvider::new);
        pack.addProvider(BiomeTagsProvider::new);
        pack.addProvider(StructureTagsProvider::new);
//        pack.addProvider(AdvancementProvider::new);
        pack.addProvider(DynamicRegistryProvider::new);
    }

    private static class ModelProvider extends FabricModelProvider
    {
        private static final ModelTemplate SPAWN_EGG = ModelTemplates.createItem("template_spawn_egg");

        private ModelProvider(FabricDataOutput dataOutput)
        {
            super(dataOutput);
        }

        @Override
        public void generateItemModels(ItemModelGenerators generator)
        {
            this.generateFlatItemWithFishVariant(FOTItems.SPLASHTAIL, List.of("sunny", "indigo", "umber", "seafoam"), "_splashtail", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.PONDIE, List.of("orchid", "bronze", "bright", "moonsky"), "_pondie", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.ISLEHOPPER, List.of("moss", "honey", "raven", "amethyst"), "_islehopper", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.ANCIENTSCALE, List.of("sapphire", "smoke", "bone", "starshine"), "_ancientscale", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.PLENTIFIN, List.of("amber", "cloudy", "bonedust", "watery"), "_plentifin", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.WILDSPLASH, List.of("sandy", "ocean", "muddy", "coral"), "_wildsplash", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.DEVILFISH, List.of("seashell", "lava", "forsaken", "firelight"), "_devilfish", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.BATTLEGILL, List.of("sky", "rum", "sand", "bittersweet"), "_battlegill", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.WRECKER, List.of("sun", "blackcloud", "snow", "moon"), "_wrecker", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.STORMFISH, List.of("shores", "wild", "shadow", "twilight"), "_stormfish", generator.output);

            this.generateFlatItemWithFishVariant(FOTItems.SPLASHTAIL_BUCKET, List.of("sunny", "indigo", "umber", "seafoam"), "_splashtail_bucket", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.PONDIE_BUCKET, List.of("orchid", "bronze", "bright", "moonsky"), "_pondie_bucket", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.ISLEHOPPER_BUCKET, List.of("moss", "honey", "raven", "amethyst"), "_islehopper_bucket", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.ANCIENTSCALE_BUCKET, List.of("sapphire", "smoke", "bone", "starshine"), "_ancientscale_bucket", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.PLENTIFIN_BUCKET, List.of("amber", "cloudy", "bonedust", "watery"), "_plentifin_bucket", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.WILDSPLASH_BUCKET, List.of("sandy", "ocean", "muddy", "coral"), "_wildsplash_bucket", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.DEVILFISH_BUCKET, List.of("seashell", "lava", "forsaken", "firelight"), "_devilfish_bucket", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.BATTLEGILL_BUCKET, List.of("sky", "rum", "sand", "bittersweet"), "_battlegill_bucket", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.WRECKER_BUCKET, List.of("sun", "blackcloud", "snow", "moon"), "_wrecker_bucket", generator.output);
            this.generateFlatItemWithFishVariant(FOTItems.STORMFISH_BUCKET, List.of("shores", "wild", "shadow", "twilight"), "_stormfish_bucket", generator.output);

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
            generator.generateFlatItem(FOTBlocks.CHERRY_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.BAMBOO_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.CRIMSON_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.WARPED_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);

            generator.generateFlatItem(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);

            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);

            generator.generateFlatItem(FOTBlocks.GILDED_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_CHERRY_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
            generator.generateFlatItem(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);
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
            this.createFishPlaque(FOTBlocks.CHERRY_FISH_PLAQUE, Blocks.CHERRY_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.BAMBOO_FISH_PLAQUE, Blocks.BAMBOO_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.CRIMSON_FISH_PLAQUE, Blocks.CRIMSON_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.WARPED_FISH_PLAQUE, Blocks.WARPED_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);

            this.createFishPlaque(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, Blocks.OAK_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, Blocks.SPRUCE_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, Blocks.BIRCH_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, Blocks.JUNGLE_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, Blocks.ACACIA_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, Blocks.DARK_OAK_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, Blocks.MANGROVE_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, Blocks.CHERRY_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, Blocks.BAMBOO_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, Blocks.CRIMSON_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, Blocks.WARPED_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);

            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, Blocks.OAK_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, Blocks.SPRUCE_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, Blocks.BIRCH_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, Blocks.JUNGLE_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, Blocks.ACACIA_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, Blocks.DARK_OAK_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, Blocks.MANGROVE_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, Blocks.CHERRY_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, Blocks.BAMBOO_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, Blocks.CRIMSON_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, Blocks.WARPED_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);

            this.createFishPlaque(FOTBlocks.GILDED_OAK_FISH_PLAQUE, Blocks.OAK_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, Blocks.SPRUCE_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, Blocks.BIRCH_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, Blocks.JUNGLE_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, Blocks.ACACIA_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, Blocks.DARK_OAK_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, Blocks.MANGROVE_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, Blocks.CHERRY_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
            this.createFishPlaque(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, Blocks.BAMBOO_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);
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

        private void generateFlatItemWithFishVariant(Item item, List<String> overrides, String suffixes, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput)
        {
            ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), modelOutput, (resourceLocation, map) ->
            {
                var jsonObject = ModelTemplates.FLAT_ITEM.createBaseTemplate(resourceLocation, map);
                var overridesArray = new JsonArray();
                var index = 1;

                for (var override : overrides)
                {
                    var customModelDataPredicate = new JsonObject();
                    var customModelData = new JsonObject();
                    var customModel = this.getCustomModelLocation(resourceLocation, override + suffixes);
                    customModelData.addProperty("custom_model_data", index++);
                    customModelDataPredicate.add("predicate", customModelData);
                    customModelDataPredicate.addProperty("model", customModel.toString());
                    overridesArray.add(customModelDataPredicate);
                }

                jsonObject.add("overrides", overridesArray);
                return jsonObject;
            });

            for (var override : overrides)
            {
                var customModel = this.getCustomModelLocation(ModelLocationUtils.getModelLocation(item), override + suffixes);
                ModelTemplates.FLAT_ITEM.create(customModel, TextureMapping.layer0(customModel), modelOutput);
            }
        }

        private ResourceLocation getCustomModelLocation(ResourceLocation resourceLocation, String item)
        {
            return new ResourceLocation(resourceLocation.getNamespace(), "item/" + item);
        }
    }

    private static class RecipeProvider extends FabricRecipeProvider
    {
        private RecipeProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
        {
            super(dataOutput, provider);
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

    private static class BlockLootProvider extends FabricBlockLootTableProvider
    {
        private BlockLootProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
        {
            super(dataOutput, provider);
        }

        @Override
        public void generate()
        {
            this.add(FOTBlocks.FISH_BONE, this.createSingleItemTable(FOTBlocks.FISH_BONE));

            this.add(FOTBlocks.OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.OAK_FISH_PLAQUE));
            this.add(FOTBlocks.SPRUCE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.SPRUCE_FISH_PLAQUE));
            this.add(FOTBlocks.BIRCH_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.BIRCH_FISH_PLAQUE));
            this.add(FOTBlocks.JUNGLE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.JUNGLE_FISH_PLAQUE));
            this.add(FOTBlocks.ACACIA_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.ACACIA_FISH_PLAQUE));
            this.add(FOTBlocks.DARK_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.DARK_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.MANGROVE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.MANGROVE_FISH_PLAQUE));
            this.add(FOTBlocks.CHERRY_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.CHERRY_FISH_PLAQUE));
            this.add(FOTBlocks.BAMBOO_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.BAMBOO_FISH_PLAQUE));
            this.add(FOTBlocks.CRIMSON_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.CRIMSON_FISH_PLAQUE));
            this.add(FOTBlocks.WARPED_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.WARPED_FISH_PLAQUE));

            this.add(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE));
            this.add(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE));

            this.add(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE));
            this.add(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE));

            this.add(FOTBlocks.GILDED_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_CHERRY_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE));
            this.add(FOTBlocks.GILDED_WARPED_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_WARPED_FISH_PLAQUE));
        }
    }

    private static class CustomBlockLootProvider extends SimpleFabricLootTableProvider
    {
        private CustomBlockLootProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
        {
            super(dataOutput, provider, LootContextParamSets.BLOCK);
        }

        //@formatter:off
        @Override
        public void generate(HolderLookup.Provider registries, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
        {
            var waterPredicate = LocationPredicate.Builder.location().setFluid(FluidPredicate.Builder.fluid().of(Fluids.WATER));
            var waterSurrounded = LocationCheck.checkLocation(waterPredicate, new BlockPos(1, 0, 0))
                    .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(-1, 0, 0)))
                    .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 0, 1)))
                    .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 0, -1)))
                    .or(LocationCheck.checkLocation(waterPredicate, new BlockPos(0, 1, 0)));

            consumer.accept(getLootTableKey(FOTLootTables.Blocks.EARTHWORMS_DROPS), LootTable.lootTable().withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(FOTItems.EARTHWORMS)
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                    .when(BlockLootSubProvider.HAS_NO_SILK_TOUCH)
                    .when(waterSurrounded.invert())
            ));

            consumer.accept(getLootTableKey(FOTLootTables.Blocks.GRUBS_DROPS), LootTable.lootTable().withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(FOTItems.GRUBS)
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                    .when(BlockLootSubProvider.HAS_NO_SILK_TOUCH)
                    .when(waterSurrounded.invert())
            ));

            consumer.accept(getLootTableKey(FOTLootTables.Blocks.LEECHES_DROPS), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .add(LootItem.lootTableItem(FOTItems.LEECHES)
                                    .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                            .when(BlockLootSubProvider.HAS_NO_SILK_TOUCH)
                            .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(registries.lookupOrThrow(Registries.BIOME).getOrThrow(BiomeTags.IS_BEACH))).and(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setContinentalness(Continentalness.COAST))).or(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(registries.lookupOrThrow(Registries.BIOME).getOrThrow(BiomeTags.IS_RIVER)))))
                            .when(waterSurrounded))
                    .withPool(LootPool.lootPool()
                            .add(LootItem.lootTableItem(FOTItems.LEECHES)
                                    .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.FORTUNE, 0.1f, 0.14285715f, 0.25f, 0.5f)))
                            .when(BlockLootSubProvider.HAS_NO_SILK_TOUCH)
                            .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(registries.lookupOrThrow(Registries.BIOME).getOrThrow(FOTTags.Biomes.ALWAYS_DROP_LEECHES))))));
        }
        //@formatter:on

        private static ResourceKey<LootTable> getLootTableKey(ResourceLocation lootTable)
        {
            return ResourceKey.create(Registries.LOOT_TABLE, lootTable);
        }
    }

    private static class EntityLootProvider extends SimpleFabricLootTableProvider
    {
        private EntityLootProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
        {
            super(dataOutput, provider, LootContextParamSets.ENTITY);
        }

        //@formatter:off
        @Override
        public void generate(HolderLookup.Provider provider, BiConsumer<ResourceKey<LootTable>,LootTable.Builder> consumer)
        {
            consumer.accept(getLootTableKey(FOTLootTables.Entities.FISH_BONE_DROP), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));

            simpleFishLoot(FOTEntities.SPLASHTAIL, FOTItems.SPLASHTAIL, consumer,
                    FOTEntitySubPredicate.splashtail(getValue(provider, FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants.RUBY)),
                    FOTEntitySubPredicate.splashtail(getValue(provider, FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants.SUNNY)),
                    FOTEntitySubPredicate.splashtail(getValue(provider, FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants.INDIGO)),
                    FOTEntitySubPredicate.splashtail(getValue(provider, FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants.UMBER)),
                    FOTEntitySubPredicate.splashtail(getValue(provider, FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants.SEAFOAM)));
//
//            simpleFishLoot(FOTEntities.PONDIE, FOTItems.PONDIE, consumer,
//                    FOTEntitySubPredicate.pondie(getValue(provider, FOTRegistries.PONDIE_VARIANT, PondieVariants.CHARCOAL)),
//                    FOTEntitySubPredicate.pondie(getValue(provider, FOTRegistries.PONDIE_VARIANT, PondieVariants.ORCHID)),
//                    FOTEntitySubPredicate.pondie(getValue(provider, FOTRegistries.PONDIE_VARIANT, PondieVariants.BRONZE)),
//                    FOTEntitySubPredicate.pondie(getValue(provider, FOTRegistries.PONDIE_VARIANT, PondieVariants.BRIGHT)),
//                    FOTEntitySubPredicate.pondie(getValue(provider, FOTRegistries.PONDIE_VARIANT, PondieVariants.MOONSKY)));

//            simpleFishLoot(FOTEntities.ISLEHOPPER, FOTItems.ISLEHOPPER, consumer,
//                    FOTEntitySubPredicate.islehopper(getValue(provider, FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.STONE)),
//                    FOTEntitySubPredicate.islehopper(getValue(provider, FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.MOSS)),
//                    FOTEntitySubPredicate.islehopper(getValue(provider, FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.HONEY)),
//                    FOTEntitySubPredicate.islehopper(getValue(provider, FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.RAVEN)),
//                    FOTEntitySubPredicate.islehopper(getValue(provider, FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants.AMETHYST)));
//
//            simpleFishLoot(FOTEntities.ANCIENTSCALE, FOTItems.ANCIENTSCALE, consumer,
//                    FOTEntitySubPredicate.ancientscale(getValue(provider, FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants.ALMOND)),
//                    FOTEntitySubPredicate.ancientscale(getValue(provider, FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants.SAPPHIRE)),
//                    FOTEntitySubPredicate.ancientscale(getValue(provider, FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants.SMOKE)),
//                    FOTEntitySubPredicate.ancientscale(getValue(provider, FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants.BONE)),
//                    FOTEntitySubPredicate.ancientscale(getValue(provider, FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants.STARSHINE)));
//
//            simpleFishLoot(FOTEntities.PLENTIFIN, FOTItems.PLENTIFIN, consumer,
//                    FOTEntitySubPredicate.plentifin(getValue(provider, FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.OLIVE)),
//                    FOTEntitySubPredicate.plentifin(getValue(provider, FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.AMBER)),
//                    FOTEntitySubPredicate.plentifin(getValue(provider, FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.CLOUDY)),
//                    FOTEntitySubPredicate.plentifin(getValue(provider, FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.BONEDUST)),
//                    FOTEntitySubPredicate.plentifin(getValue(provider, FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants.WATERY)));
//
//            simpleFishLoot(FOTEntities.WILDSPLASH, FOTItems.WILDSPLASH, consumer,
//                    FOTEntitySubPredicate.wildsplash(getValue(provider, FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.RUSSET)),
//                    FOTEntitySubPredicate.wildsplash(getValue(provider, FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.SANDY)),
//                    FOTEntitySubPredicate.wildsplash(getValue(provider, FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.OCEAN)),
//                    FOTEntitySubPredicate.wildsplash(getValue(provider, FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.MUDDY)),
//                    FOTEntitySubPredicate.wildsplash(getValue(provider, FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants.CORAL)));
//
//            simpleFishLoot(FOTEntities.DEVILFISH, FOTItems.DEVILFISH, consumer,
//                    FOTEntitySubPredicate.devilfish(getValue(provider, FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants.ASHEN)),
//                    FOTEntitySubPredicate.devilfish(getValue(provider, FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants.SEASHELL)),
//                    FOTEntitySubPredicate.devilfish(getValue(provider, FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants.LAVA)),
//                    FOTEntitySubPredicate.devilfish(getValue(provider, FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants.FORSAKEN)),
//                    FOTEntitySubPredicate.devilfish(getValue(provider, FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants.FIRELIGHT)));
//
//            simpleFishLoot(FOTEntities.BATTLEGILL, FOTItems.BATTLEGILL, consumer,
//                    FOTEntitySubPredicate.battlegill(getValue(provider, FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants.JADE)),
//                    FOTEntitySubPredicate.battlegill(getValue(provider, FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants.SKY)),
//                    FOTEntitySubPredicate.battlegill(getValue(provider, FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants.RUM)),
//                    FOTEntitySubPredicate.battlegill(getValue(provider, FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants.SAND)),
//                    FOTEntitySubPredicate.battlegill(getValue(provider, FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants.BITTERSWEET)));
//
//            simpleFishLoot(FOTEntities.WRECKER, FOTItems.WRECKER, consumer,
//                    FOTEntitySubPredicate.wrecker(getValue(provider, FOTRegistries.WRECKER_VARIANT, WreckerVariants.ROSE)),
//                    FOTEntitySubPredicate.wrecker(getValue(provider, FOTRegistries.WRECKER_VARIANT, WreckerVariants.SUN)),
//                    FOTEntitySubPredicate.wrecker(getValue(provider, FOTRegistries.WRECKER_VARIANT, WreckerVariants.BLACKCLOUD)),
//                    FOTEntitySubPredicate.wrecker(getValue(provider, FOTRegistries.WRECKER_VARIANT, WreckerVariants.SNOW)),
//                    FOTEntitySubPredicate.wrecker(getValue(provider, FOTRegistries.WRECKER_VARIANT, WreckerVariants.MOON)));
//
//            simpleFishLoot(FOTEntities.STORMFISH, FOTItems.STORMFISH, consumer,
//                    FOTEntitySubPredicate.stormfish(getValue(provider, FOTRegistries.STORMFISH_VARIANT, StormfishVariants.ANCIENT)),
//                    FOTEntitySubPredicate.stormfish(getValue(provider, FOTRegistries.STORMFISH_VARIANT, StormfishVariants.SHORES)),
//                    FOTEntitySubPredicate.stormfish(getValue(provider, FOTRegistries.STORMFISH_VARIANT, StormfishVariants.WILD)),
//                    FOTEntitySubPredicate.stormfish(getValue(provider, FOTRegistries.STORMFISH_VARIANT, StormfishVariants.SHADOW)),
//                    FOTEntitySubPredicate.stormfish(getValue(provider, FOTRegistries.STORMFISH_VARIANT, StormfishVariants.TWILIGHT)));
        }
        //@formatter:on

        private static <T> HolderSet<T> getValue(HolderLookup.Provider provider, ResourceKey<? extends Registry<? extends T>> registryKey, ResourceKey<T> resourceKey)
        {
            return HolderSet.direct(provider.lookupOrThrow(registryKey).getOrThrow(resourceKey));
        }

        private static void simpleFishLoot(EntityType<?> entityType, Item item, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer, EntitySubPredicate... subPredicate)
        {
            consumer.accept(entityType.getDefaultLootTable(), simpleFishLoot(item, entityType, subPredicate));
        }

        //@formatter:off
        private static LootTable.Builder simpleFishLoot(Item item, EntityType<?> entityType, EntitySubPredicate... subPredicate)
        {
            return LootTable.lootTable().withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(item)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(new TrophyFishPredicate(true)))))
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

        private static LootPoolSingletonContainer.Builder<?> dropWithVariant(Item item, EntityType<?> entityType, int variant, EntitySubPredicate subPredicate)
        {
            return LootItem.lootTableItem(item)
                    .apply(SmeltItemFunction.smelted()
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(new TrophyFishPredicate(true)))))
                    .apply(hasCustomModelData(variant)
                            .when(FishVariantLootConfigCondition.configEnabled()))
                    .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(entityType).subPredicate(subPredicate)));
        }
        //@formatter:on

        private static LootItemConditionalFunction.Builder<?> hasCustomModelData(int data)
        {
            return LootItemConditionalFunction.simpleBuilder(lootItemConditions -> new SetCustomModelDataFunction(lootItemConditions, ConstantValue.exactly(data)));
        }

        private static ResourceKey<LootTable> getLootTableKey(ResourceLocation lootTable)
        {
            return ResourceKey.create(Registries.LOOT_TABLE, lootTable);
        }
    }

    private static class ChestLootProvider extends SimpleFabricLootTableProvider
    {
        //@formatter:off
        private static final IntList FIREWORK_COLORS = IntList.of(
                DyeColor.RED.getFireworkColor(),
                DyeColor.ORANGE.getFireworkColor(),
                DyeColor.YELLOW.getFireworkColor(),
                DyeColor.LIME.getFireworkColor(),
                DyeColor.BLUE.getFireworkColor(),
                DyeColor.CYAN.getFireworkColor(),
                DyeColor.LIGHT_BLUE.getFireworkColor(),
                DyeColor.PURPLE.getFireworkColor(),
                DyeColor.MAGENTA.getFireworkColor(),
                DyeColor.WHITE.getFireworkColor(),
                6942120 // athena
        );
        //@formatter:on

        private ChestLootProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
        {
            super(dataOutput, provider, LootContextParamSets.CHEST);
        }

        //@formatter:off
        @Override
        public void generate(HolderLookup.Provider provider, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
        {
            consumer.accept(getLootTableKey(FOTLootTables.Chests.SEAPOST_BARREL_SUPPLY), LootTable.lootTable()
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
                            .add(LootItem.lootTableItem(Items.MAP)
                                    .apply(ExplorationMapFunction.makeExplorationMap()
                                            .setDestination(StructureTags.ON_TREASURE_MAPS)
                                            .setMapDecoration(MapDecorationTypes.RED_X)
                                            .setZoom((byte)1)
                                            .setSkipKnownStructures(false)))));

            consumer.accept(getLootTableKey(FOTLootTables.Chests.SEAPOST_BARREL_COMBAT), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(2.0F, 6.0F))
                            .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                            .add(LootItem.lootTableItem(Items.FIRE_CHARGE).setWeight(4)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                            .add(LootItem.lootTableItem(Items.TNT).setWeight(2)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));

            consumer.accept(getLootTableKey(FOTLootTables.Chests.SEAPOST_BARREL_FIREWORK), LootTable.lootTable().withPool(buildFirework(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 4.0F)))));
        }
        //@formatter:on

        //@formatter:off
        private static LootPool.Builder buildFirework(LootPool.Builder builder)
        {
            var random = RandomSource.create(69420);

            for (var color : FIREWORK_COLORS)
            {
                builder.add(LootItem.lootTableItem(Items.FIREWORK_ROCKET).setWeight(1)
                        .apply(setFirework(new ListOperation.StandAlone<>(List.of(new FireworkExplosion(Util.getRandom(FireworkExplosion.Shape.values(), random), IntList.of(color), IntList.of(), random.nextBoolean(), random.nextBoolean())), ListOperation.Append.INSTANCE), Optional.of(1)))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))));
            }
            return builder;
        }
        //@formatter:on

        private static LootItemConditionalFunction.Builder<?> setFirework(ListOperation.StandAlone<FireworkExplosion> explosions, Optional<Integer> flightDuration)
        {
            return LootItemConditionalFunction.simpleBuilder(lootItemConditions -> new SetFireworksFunction(lootItemConditions, Optional.of(explosions), flightDuration));
        }

        private static ResourceKey<LootTable> getLootTableKey(ResourceLocation lootTable)
        {
            return ResourceKey.create(Registries.LOOT_TABLE, lootTable);
        }
    }

    private static class AdvancementRewardProvider extends SimpleFabricLootTableProvider
    {
        private AdvancementRewardProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
        {
            super(dataOutput, provider, LootContextParamSets.ADVANCEMENT_REWARD);
        }

        //@formatter:off
        @Override
        public void generate(HolderLookup.Provider provider, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
        {
            consumer.accept(getLootTableKey(FOTLootTables.Advancements.FISH_COLLECTORS), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(2.0F, 4.0F))
                            .add(TagEntry.expandTag(FOTTags.Items.WOODEN_FISH_PLAQUE))));

            consumer.accept(getLootTableKey(FOTLootTables.Advancements.MASTER_FISH_COLLECTORS), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(2.0F, 4.0F))
                            .add(TagEntry.expandTag(FOTTags.Items.IRON_FRAME_FISH_PLAQUE)))
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(4.0F, 8.0F))
                            .add(TagEntry.expandTag(FOTTags.Items.GOLDEN_FRAME_FISH_PLAQUE))));

            consumer.accept(getLootTableKey(FOTLootTables.Advancements.LEGENDARY_FISH_COLLECTORS), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(4.0F, 8.0F))
                            .add(TagEntry.expandTag(FOTTags.Items.GILDED_FRAME_FISH_PLAQUE))));
        }
        //@formatter:on

        private static ResourceKey<LootTable> getLootTableKey(ResourceLocation lootTable)
        {
            return ResourceKey.create(Registries.LOOT_TABLE, lootTable);
        }
    }

    private static class BlockTagsProvider extends FabricTagProvider.BlockTagProvider
    {
        private BlockTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
        {
            super(dataOutput, provider);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider)
        {
            this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(FOTBlocks.FISH_BONE);
            this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).forceAddTag(FOTTags.Blocks.FISH_PLAQUE);
            this.getOrCreateTagBuilder(FOTTags.Blocks.FIRELIGHT_DEVILFISH_WARM_BLOCKS).add(Blocks.MAGMA_BLOCK);
            this.getOrCreateTagBuilder(FOTTags.Blocks.CORAL_WILDSPLASH_SPAWNABLE_ON).forceAddTag(BlockTags.CORALS).forceAddTag(BlockTags.CORAL_BLOCKS).forceAddTag(BlockTags.WALL_CORALS);
            this.getOrCreateTagBuilder(FOTTags.Blocks.AMETHYST_ISLEHOPPER_SPAWNABLE_ON).forceAddTag(BlockTags.CRYSTAL_SOUND_BLOCKS);
            this.getOrCreateTagBuilder(FOTTags.Blocks.EARTHWORMS_DROPS).forceAddTag(BlockTags.DIRT);
            this.getOrCreateTagBuilder(FOTTags.Blocks.GRUBS_DROPS).forceAddTag(BlockTags.SAND);
            this.getOrCreateTagBuilder(FOTTags.Blocks.LEECHES_DROPS).forceAddTag(BlockTags.SAND).add(Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS);
            this.getOrCreateTagBuilder(FOTTags.Blocks.EARTHWORMS_DROP_BLACKLIST).add(Blocks.MOSS_BLOCK, Blocks.COARSE_DIRT, Blocks.MYCELIUM, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS);
            this.getOrCreateTagBuilder(FOTTags.Blocks.WOODEN_FISH_PLAQUE).add(FOTBlocks.OAK_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE);
            this.getOrCreateTagBuilder(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE).add(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE);
            this.getOrCreateTagBuilder(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE).add(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE);
            this.getOrCreateTagBuilder(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE).add(FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, FOTBlocks.GILDED_WARPED_FISH_PLAQUE);
            this.getOrCreateTagBuilder(FOTTags.Blocks.FISH_REPELLENTS).add(Blocks.MAGMA_BLOCK, Blocks.BUBBLE_COLUMN);
            this.getOrCreateTagBuilder(FOTTags.Blocks.FISH_PLAQUE).forceAddTag(FOTTags.Blocks.WOODEN_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE);
        }
    }

    private static class ItemTagsProvider extends FabricTagProvider.ItemTagProvider
    {
        private ItemTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider, FabricTagProvider.BlockTagProvider blockTagProvider)
        {
            super(dataOutput, provider, blockTagProvider);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider)
        {
            var rawFishes = new Item[] { FOTItems.SPLASHTAIL, FOTItems.PONDIE, FOTItems.ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.WILDSPLASH, FOTItems.DEVILFISH, FOTItems.BATTLEGILL, FOTItems.WRECKER, FOTItems.STORMFISH };
            var cookedFishes = new Item[] { FOTItems.COOKED_SPLASHTAIL, FOTItems.COOKED_PONDIE, FOTItems.COOKED_ISLEHOPPER, FOTItems.COOKED_ANCIENTSCALE, FOTItems.COOKED_PLENTIFIN, FOTItems.COOKED_WILDSPLASH, FOTItems.COOKED_DEVILFISH, FOTItems.COOKED_BATTLEGILL, FOTItems.COOKED_WRECKER, FOTItems.COOKED_STORMFISH };

            this.getOrCreateTagBuilder(ItemTags.AXOLOTL_FOOD).forceAddTag(FOTTags.Items.WORMS).forceAddTag(FOTTags.Items.THIEVES_FISH_BUCKET);
            this.getOrCreateTagBuilder(ItemTags.CAT_FOOD).forceAddTag(FOTTags.Items.THIEVES_FISH);
            this.getOrCreateTagBuilder(ItemTags.CHICKEN_FOOD).forceAddTag(FOTTags.Items.WORMS);
            this.getOrCreateTagBuilder(ItemTags.OCELOT_FOOD).forceAddTag(FOTTags.Items.THIEVES_FISH);
            this.getOrCreateTagBuilder(ItemTags.FISHES).forceAddTag(FOTTags.Items.THIEVES_FISH).forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH);

            this.getOrCreateTagBuilder(FOTTags.Items.THIEVES_FISH_BUCKET).add(FISH_BUCKETS);
            this.getOrCreateTagBuilder(FOTTags.Items.THIEVES_FISH).add(rawFishes);
            this.getOrCreateTagBuilder(FOTTags.Items.COOKED_THIEVES_FISH).add(cookedFishes);
            this.getOrCreateTagBuilder(FOTTags.Items.WORMS).forceAddTag(FOTTags.Items.EARTHWORMS_FOOD).forceAddTag(FOTTags.Items.GRUBS_FOOD).forceAddTag(FOTTags.Items.LEECHES_FOOD);
            this.getOrCreateTagBuilder(FOTTags.Items.EARTHWORMS_FOOD).add(FOTItems.EARTHWORMS);
            this.getOrCreateTagBuilder(FOTTags.Items.GRUBS_FOOD).add(FOTItems.GRUBS);
            this.getOrCreateTagBuilder(FOTTags.Items.LEECHES_FOOD).add(FOTItems.LEECHES);
            this.getOrCreateTagBuilder(FOTTags.Items.FISH_PLAQUE_BUCKET_BLACKLIST);
            this.getOrCreateTagBuilder(FOTTags.Items.WOODEN_FISH_PLAQUE).add(FOTBlocks.OAK_FISH_PLAQUE.asItem(), FOTBlocks.SPRUCE_FISH_PLAQUE.asItem(), FOTBlocks.BIRCH_FISH_PLAQUE.asItem(), FOTBlocks.JUNGLE_FISH_PLAQUE.asItem(), FOTBlocks.ACACIA_FISH_PLAQUE.asItem(), FOTBlocks.DARK_OAK_FISH_PLAQUE.asItem(), FOTBlocks.MANGROVE_FISH_PLAQUE.asItem(), FOTBlocks.CHERRY_FISH_PLAQUE.asItem(), FOTBlocks.BAMBOO_FISH_PLAQUE.asItem());
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
        private EntityTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
        {
            super(dataOutput, provider);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider)
        {
            var neutralFishes = new EntityType<?>[] { FOTEntities.DEVILFISH, FOTEntities.BATTLEGILL, FOTEntities.WRECKER };
            var fishes = new EntityType<?>[] { FOTEntities.SPLASHTAIL, FOTEntities.PONDIE, FOTEntities.ISLEHOPPER, FOTEntities.ANCIENTSCALE, FOTEntities.PLENTIFIN, FOTEntities.WILDSPLASH, FOTEntities.STORMFISH };
            this.getOrCreateTagBuilder(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add(ArrayUtils.removeElements(fishes, neutralFishes));
            this.getOrCreateTagBuilder(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE).add(ArrayUtils.addAll(fishes, neutralFishes));
            this.getOrCreateTagBuilder(FOTTags.EntityTypes.FISH_BONE_DROP).add(EntityType.COD, EntityType.SALMON, EntityType.TROPICAL_FISH);
            this.getOrCreateTagBuilder(FOTTags.EntityTypes.HORIZONTAL_MOB_RENDER).add(EntityType.PUFFERFISH, EntityType.TADPOLE, EntityType.AXOLOTL);
            this.getOrCreateTagBuilder(FOTTags.EntityTypes.BATTLEGILL_ATTACKABLE).add(EntityType.DROWNED, EntityType.ZOMBIE, EntityType.SKELETON, EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN, EntityType.PILLAGER);
            this.getOrCreateTagBuilder(FOTTags.EntityTypes.DEVILFISH_ATTACKABLE).add(EntityType.DROWNED, EntityType.ZOMBIE, EntityType.SKELETON, EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN);
            this.getOrCreateTagBuilder(FOTTags.EntityTypes.WRECKER_ATTACKABLE).add(EntityType.PLAYER, EntityType.DROWNED, EntityType.ZOMBIE, EntityType.SKELETON, EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN);

            // Immersive Weathering compatibility
            this.getOrCreateTagBuilder(FREEZING_WATER_IMMUNE).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
            this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        }
    }

    private static class BiomeTagsProvider extends FabricTagProvider<Biome>
    {
        public BiomeTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
        {
            super(dataOutput, Registries.BIOME, provider);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider)
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
            this.getOrCreateTagBuilder(FOTTags.Biomes.HAS_FISH_BONE).forceAddTag(BiomeTags.IS_OCEAN).forceAddTag(BiomeTags.IS_RIVER).add(Biomes.SWAMP, Biomes.MANGROVE_SWAMP);
        }
    }

    private static class StructureTagsProvider extends FabricTagProvider<Structure>
    {
        public StructureTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
        {
            super(dataOutput, Registries.STRUCTURE, provider);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider)
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
//            map.put(FOTItems.SPLASHTAIL_BUCKET, BBFOTBuiltInRegistries.SPLASHTAIL_VARIANT);
//            map.put(FOTItems.PONDIE_BUCKET, BBFOTBuiltInRegistries.PONDIE_VARIANT);
//            map.put(FOTItems.ISLEHOPPER_BUCKET, BBFOTBuiltInRegistries.ISLEHOPPER_VARIANT);
//            map.put(FOTItems.ANCIENTSCALE_BUCKET, BBFOTBuiltInRegistries.ANCIENTSCALE_VARIANT);
//            map.put(FOTItems.PLENTIFIN_BUCKET, BBFOTBuiltInRegistries.PLENTIFIN_VARIANT);
//            map.put(FOTItems.WILDSPLASH_BUCKET, BBFOTBuiltInRegistries.WILDSPLASH_VARIANT);
//            map.put(FOTItems.DEVILFISH_BUCKET, BBFOTBuiltInRegistries.DEVILFISH_VARIANT);
//            map.put(FOTItems.BATTLEGILL_BUCKET, BBFOTBuiltInRegistries.BATTLEGILL_VARIANT);
//            map.put(FOTItems.WRECKER_BUCKET, BBFOTBuiltInRegistries.WRECKER_VARIANT);
//            map.put(FOTItems.STORMFISH_BUCKET, BBFOTBuiltInRegistries.STORMFISH_VARIANT);
        });

        private AdvancementProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
        {
            super(dataOutput, provider);
        }

        //@formatter:off
        @Override
        public void generateAdvancement(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer)
        {
            var sallyName = Component.literal("Sally");

            var advancement = Advancement.Builder.advancement()
                    .display(FOTItems.SPLASHTAIL,
                            Component.translatable("advancements.fot.root.title"),
                            Component.translatable("advancements.fot.root.description"),
                            new ResourceLocation("textures/block/tube_coral_block.png"),
                            AdvancementType.TASK, false, false, false)
                    .addCriterion("in_water", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location()
                                    .setFluid(FluidPredicate.Builder.fluid()
                                            .of(Fluids.WATER))))
                    .save(consumer, this.mod("root"));

            var advancement2 = this.addFishBuckets(Advancement.Builder.advancement().parent(advancement))
                    .display(FOTItems.SPLASHTAIL_BUCKET,
                            Component.translatable("advancements.fot.fish_collectors.title"),
                            Component.translatable("advancements.fot.fish_collectors.description"),
                            null, AdvancementType.CHALLENGE, true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(250).addLootTable(getLootTableKey(FOTLootTables.Advancements.FISH_COLLECTORS)))
                    .save(consumer, this.mod("fish_collectors"));

            this.addFishVariantsBuckets(Advancement.Builder.advancement().parent(advancement2), false)
                    .display(FOTItems.SPLASHTAIL_BUCKET,
                            Component.translatable("advancements.fot.master_fish_collectors.title"),
                            Component.translatable("advancements.fot.master_fish_collectors.description"),
                            null, AdvancementType.CHALLENGE, true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(1000).addLootTable(getLootTableKey(FOTLootTables.Advancements.MASTER_FISH_COLLECTORS)))
                    .save(consumer, this.mod("master_fish_collectors"));

            this.addFishVariantsBuckets(Advancement.Builder.advancement().parent(advancement2), true)
                    .display(FOTItems.SPLASHTAIL_BUCKET,
                            Component.translatable("advancements.fot.legendary_fish_collectors.title"),
                            Component.translatable("advancements.fot.legendary_fish_collectors.description"),
                            null, AdvancementType.CHALLENGE, true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(2000).addLootTable(getLootTableKey(FOTLootTables.Advancements.LEGENDARY_FISH_COLLECTORS)))
                    .save(consumer, this.mod("legendary_fish_collectors"));

            Advancement.Builder.advancement().parent(advancement).addCriterion(BuiltInRegistries.ITEM.getKey(FOTItems.DEVILFISH_BUCKET).getPath(),
                            PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(Optional.empty(),
                                    ItemPredicate.Builder.item().of(FOTItems.DEVILFISH_BUCKET).hasComponents(DataComponentPredicate.builder()
                                            .expect(DataComponents.CUSTOM_DATA, CustomData.of(Util.make(new CompoundTag(), compoundTag -> compoundTag.putString(ThievesFish.VARIANT_TAG, DevilfishVariants.LAVA.location().toString())))).build()
                                    ), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.AXOLOTL).build()))))
                    .display(FOTItems.DEVILFISH,
                            Component.translatable("advancements.fot.feed_axolotl_with_lava_devilfish.title"),
                            Component.translatable("advancements.fot.feed_axolotl_with_lava_devilfish.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .save(consumer, this.mod("feed_axolotl_with_lava_devilfish"));

            var battlegill = BuiltInRegistries.ITEM.getKey(FOTItems.BATTLEGILL).getPath();
            Advancement.Builder.advancement().parent(advancement).requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion(battlegill + "_village",
                            FishingRodHookedTrigger.TriggerInstance.fishedItem(Optional.empty(), Optional.of(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.location().setStructures(provider.lookupOrThrow(Registries.STRUCTURE).getOrThrow(StructureTags.VILLAGE))).build()), Optional.of(ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build())))
                    .display(FOTItems.BATTLEGILL,
                            Component.translatable("advancements.fot.so_chill.title"),
                            Component.translatable("advancements.fot.so_chill.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .save(consumer, this.mod("so_chill"));

            Advancement.Builder.advancement().parent(advancement)
                    .display(FOTItems.STORMFISH,
                            Component.translatable("advancements.fot.lightning_straight_to_my_fish.title"),
                            Component.translatable("advancements.fot.lightning_straight_to_my_fish.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("lightning_strike_at_stormfish", LightningStrikeTrigger.TriggerInstance.lightningStrike(Optional.of(EntityPredicate.Builder.entity().distance(DistancePredicate.absolute(MinMaxBounds.Doubles.atMost(16.0))).subPredicate(LightningBoltPredicate.blockSetOnFire(MinMaxBounds.Ints.exactly(0))).build()), Optional.of(EntityPredicate.Builder.entity().of(FOTEntities.STORMFISH).build())))
                    .save(consumer, this.mod("lightning_straight_to_my_fish"));

            Advancement.Builder.advancement().parent(advancement)
                    .display(Items.SPYGLASS,
                            Component.translatable("advancements.fot.spyglass_at_plentifins.title"),
                            Component.translatable("advancements.fot.spyglass_at_plentifins.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("spyglass_at_plentifins", UsingItemTrigger.TriggerInstance.lookingAt(EntityPredicate.Builder.entity().subPredicate(PlayerPredicate.Builder.player().setLookingAt(EntityPredicate.Builder.entity().of(FOTEntities.PLENTIFIN)).build()), ItemPredicate.Builder.item().of(Items.SPYGLASS)))
                    .save(consumer, this.mod("spyglass_at_plentifins"));

            Advancement.Builder.advancement().parent(advancement).requirements(AdvancementRequirements.Strategy.OR)
                    .display(Items.JUKEBOX,
                            Component.translatable("advancements.fot.play_jukebox_near_fish.title"),
                            Component.translatable("advancements.fot.play_jukebox_near_fish.description"),
                            null, AdvancementType.TASK, true, true, true)
                    .addCriterion("play_jukebox_near_thieves_fish", ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location()
                                    .setBlock(BlockPredicate.Builder.block().of(Blocks.JUKEBOX)),
                            ItemPredicate.Builder.item().of(ItemTags.MUSIC_DISCS),
                            EntityPredicate.Builder.entity().of(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE)))
                    .addCriterion("play_jukebox_near_fish", ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location()
                                    .setBlock(BlockPredicate.Builder.block().of(Blocks.JUKEBOX)),
                            ItemPredicate.Builder.item().of(ItemTags.MUSIC_DISCS),
                            EntityPredicate.Builder.entity().of(EntityTypeTags.AXOLOTL_HUNT_TARGETS)))
                    .save(consumer, this.mod("play_jukebox_near_fish"));

            Advancement.Builder.advancement().parent(advancement).requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion(BuiltInRegistries.ITEM.getKey(Items.NAME_TAG).getPath(), PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(Optional.empty(),
                            ItemPredicate.Builder.item().of(Items.NAME_TAG).hasComponents(DataComponentPredicate.builder().expect(DataComponents.CUSTOM_NAME, sallyName).build()),
                            Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.SALMON)))))
                    .addCriterion(BuiltInRegistries.ITEM.getKey(Items.SALMON_BUCKET).getPath(), ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(Blocks.WATER)), ItemPredicate.Builder.item().of(Items.SALMON_BUCKET).hasComponents(DataComponentPredicate.builder().expect(DataComponents.CUSTOM_NAME, sallyName).build())))
                    .display(Items.SALMON,
                            Component.translatable("advancements.fot.lost_sally.title"),
                            Component.translatable("advancements.fot.lost_sally.description"),
                            null, AdvancementType.TASK, true, true, true)
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
                builder.addCriterion(BuiltInRegistries.ITEM.getKey(item).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(item)));
            }
            return builder;
        }

        private Advancement.Builder addFishVariantsBuckets(Advancement.Builder builder, boolean trophy)
        {
            for (var item : FISH_BUCKETS)
            {
                for (var variant : Sets.newTreeSet(BUCKET_TO_VARIANTS_MAP.get(item).keySet()))
                {
                    builder.addCriterion(variant.getPath() + "_" + BuiltInRegistries.ITEM.getKey(item).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(item).hasComponents(DataComponentPredicate.builder().expect(DataComponents.CUSTOM_DATA, CustomData.of(Util.make(new CompoundTag(), compoundTag ->
                    {
                        compoundTag.putString(ThievesFish.VARIANT_TAG, variant.toString());

                        if (trophy)
                        {
                            compoundTag.putBoolean(ThievesFish.TROPHY_TAG, true);
                            compoundTag.putBoolean(ThievesFish.HAS_FED_TAG, false);
                        }
                    }))).build())));
                }
            }
            return builder;
        }

        private static ResourceKey<LootTable> getLootTableKey(ResourceLocation lootTable)
        {
            return ResourceKey.create(Registries.LOOT_TABLE, lootTable);
        }
    }

    private static class DynamicRegistryProvider extends FabricDynamicRegistryProvider
    {
        public DynamicRegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
        {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(HolderLookup.Provider registries, Entries entries)
        {
            entries.addAll(registries.lookupOrThrow(Registries.STRUCTURE));
            entries.addAll(registries.lookupOrThrow(Registries.STRUCTURE_SET));
            entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
            entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.SPLASHTAIL_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.PONDIE_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.ISLEHOPPER_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.ANCIENTSCALE_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.PLENTIFIN_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.WILDSPLASH_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.DEVILFISH_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.BATTLEGILL_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.WRECKER_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.STORMFISH_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.FISH_PLAQUE_INTERACTION));
        }

        @Override
        public String getName()
        {
            return "Fish of Thieves Dynamic Registries";
        }
    }
}