package com.stevekung.fishofthieves.fabric.datagen.client.provider;

import static net.minecraft.client.data.models.BlockModelGenerators.condition;
import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import com.google.common.collect.Lists;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.block.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.fabric.datagen.client.FOTModelTemplates;
import com.stevekung.fishofthieves.registry.FOTBlockFamilies;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.CustomModelDataProperty;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class ModelProvider extends FabricModelProvider
{
    private final HolderLookup.Provider provider;

    public ModelProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput);
        this.provider = provider.join();
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator)
    {
        this.generateFlatItemWithFishVariant(FOTItems.SPLASHTAIL, FOTRegistries.SPLASHTAIL_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.PONDIE, FOTRegistries.PONDIE_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.ISLEHOPPER, FOTRegistries.ISLEHOPPER_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.ANCIENTSCALE, FOTRegistries.ANCIENTSCALE_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.PLENTIFIN, FOTRegistries.PLENTIFIN_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.WILDSPLASH, FOTRegistries.WILDSPLASH_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.DEVILFISH, FOTRegistries.DEVILFISH_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.BATTLEGILL, FOTRegistries.BATTLEGILL_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.WRECKER, FOTRegistries.WRECKER_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.STORMFISH, FOTRegistries.STORMFISH_VARIANT, generator);

        this.generateFlatItemWithFishVariant(FOTItems.SPLASHTAIL_BUCKET, FOTRegistries.SPLASHTAIL_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.PONDIE_BUCKET, FOTRegistries.PONDIE_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.ISLEHOPPER_BUCKET, FOTRegistries.ISLEHOPPER_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.ANCIENTSCALE_BUCKET, FOTRegistries.ANCIENTSCALE_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.PLENTIFIN_BUCKET, FOTRegistries.PLENTIFIN_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.WILDSPLASH_BUCKET, FOTRegistries.WILDSPLASH_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.DEVILFISH_BUCKET, FOTRegistries.DEVILFISH_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.BATTLEGILL_BUCKET, FOTRegistries.BATTLEGILL_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.WRECKER_BUCKET, FOTRegistries.WRECKER_VARIANT, generator);
        this.generateFlatItemWithFishVariant(FOTItems.STORMFISH_BUCKET, FOTRegistries.STORMFISH_VARIANT, generator);

        generator.generateFlatItem(FOTItems.EARTHWORMS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.GRUBS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.LEECHES, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.COOKED_SPLASHTAIL, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.SPLASHTAIL_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.COOKED_PONDIE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.PONDIE_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.COOKED_ISLEHOPPER, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.ISLEHOPPER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.COOKED_ANCIENTSCALE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.ANCIENTSCALE_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.COOKED_PLENTIFIN, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.PLENTIFIN_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.COOKED_WILDSPLASH, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.WILDSPLASH_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.COOKED_DEVILFISH, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.DEVILFISH_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.COOKED_BATTLEGILL, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.BATTLEGILL_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.COOKED_WRECKER, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.WRECKER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.COOKED_STORMFISH, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.STORMFISH_SPAWN_EGG, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(FOTItems.COCONUT, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.BANANA, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.COCONUT_BOAT, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.COCONUT_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.HALF_PINEAPPLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.PINEAPPLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.CROWNLESS_PINEAPPLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.PINEAPPLE_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.PINEAPPLE_CROWN, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.MANGO, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.RAW_MANGO, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.MANGO_PIT, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.POMEGRANATE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.POMEGRANATE_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.STORMFISH_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.KRAKEN_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.MEGALODON_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FOTItems.GUARDIAN_FRUIT, ModelTemplates.FLAT_ITEM);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator)
    {
        this.createFishBone(generator);

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
        this.createFishPlaque(FOTBlocks.COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_PLANKS, FOTModelTemplates.WOODEN_FISH_PLAQUE, generator);

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
        this.createFishPlaque(FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_PLANKS, FOTModelTemplates.IRON_FRAME_FISH_PLAQUE, generator);

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
        this.createFishPlaque(FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_PLANKS, FOTModelTemplates.GOLDEN_FRAME_FISH_PLAQUE, generator);

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
        this.createFishPlaque(FOTBlocks.GILDED_COCONUT_FISH_PLAQUE, FOTBlocks.COCONUT_PLANKS, FOTModelTemplates.GILDED_FISH_PLAQUE, generator);

        generator.createPlantWithDefaultItem(FOTBlocks.PINK_PLUMERIA, FOTBlocks.POTTED_PINK_PLUMERIA, BlockModelGenerators.PlantType.NOT_TINTED);
        generator.createPlantWithDefaultItem(FOTBlocks.LIGHT_BLUE_PLUMERIA, FOTBlocks.POTTED_LIGHT_BLUE_PLUMERIA, BlockModelGenerators.PlantType.NOT_TINTED);
        generator.createPlantWithDefaultItem(FOTBlocks.WHITE_PLUMERIA, FOTBlocks.POTTED_WHITE_PLUMERIA, BlockModelGenerators.PlantType.NOT_TINTED);
        generator.woodProvider(FOTBlocks.COCONUT_LOG).logWithHorizontal(FOTBlocks.COCONUT_LOG).wood(FOTBlocks.COCONUT_WOOD);
        generator.woodProvider(FOTBlocks.STRIPPED_COCONUT_LOG).logWithHorizontal(FOTBlocks.STRIPPED_COCONUT_LOG).wood(FOTBlocks.STRIPPED_COCONUT_WOOD);
        this.createSmallCoconutLog(generator);
        this.createCoconutFruitGrowableLog(generator);
        this.createSmallTopCoconutLog(generator);
        this.createSmallLog(generator, FOTBlocks.SMALL_COCONUT_WOOD, ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG), ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG));
        this.createMediumLog(generator, FOTBlocks.MEDIUM_COCONUT_LOG, ModelLocationUtils.getModelLocation(FOTBlocks.MEDIUM_COCONUT_LOG, "_top"), ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG));
        this.createMediumLog(generator, FOTBlocks.MEDIUM_COCONUT_WOOD, ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG), ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG));
        this.createMediumLog(generator, FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG, ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG, "_top"), ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_COCONUT_LOG));
        this.createMediumLog(generator, FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD, ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_COCONUT_LOG), ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_COCONUT_LOG));
        this.createSmallLog(generator, FOTBlocks.STRIPPED_SMALL_COCONUT_LOG, ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_SMALL_COCONUT_LOG, "_top"), ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_COCONUT_LOG));
        this.createSmallLog(generator, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD, ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_COCONUT_LOG), ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_COCONUT_LOG));
        this.generateRotatedExistedModel(generator, FOTBlocks.COCONUT_SAPLING);
        this.createCoconutFruit(generator);
        this.createCoconutFronds(generator);
        this.createBananaLeaves(generator);
        this.createBananaStem(generator);
        this.createBananaClusterGrowableStem(generator);
        this.createVerticalLeaves(generator, FOTBlocks.VERTICAL_BANANA_LEAVES);
        this.createVerticalLeaves(generator, FOTBlocks.VERTICAL_COCONUT_FRONDS);
        generator.family(FOTBlocks.COCONUT_PLANKS).generateFor(FOTBlockFamilies.COCONUT_PLANKS);
        generator.createHangingSign(FOTBlocks.STRIPPED_COCONUT_LOG, FOTBlocks.COCONUT_HANGING_SIGN, FOTBlocks.COCONUT_WALL_HANGING_SIGN);
        this.createBananaShootsPlant(generator);
        this.createBananaBlossom(generator);
        this.createBananaBlossomPlant(generator);
        this.createUnderripeBananaCluster(generator);
        this.generateRotatedExistedModel(generator, FOTBlocks.UNDERRIPE_BANANA_CLUSTER);
        this.createBananaCluster(generator, FOTBlocks.BARELY_RIPE_BANANA_CLUSTER);
        this.createBananaCluster(generator, FOTBlocks.RIPE_BANANA_CLUSTER);
        this.createBananaClusterPlant(generator, FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT, FOTBlocks.BARELY_RIPE_BANANA_CLUSTER);
        this.createBananaClusterPlant(generator, FOTBlocks.RIPE_BANANA_CLUSTER_PLANT, FOTBlocks.RIPE_BANANA_CLUSTER);
        generator.createPlantWithDefaultItem(FOTBlocks.BANANA_SHOOTS, FOTBlocks.POTTED_BANANA_SHOOTS, BlockModelGenerators.PlantType.NOT_TINTED);
        this.createPineappleCrop(generator);
        this.generateRotatedExistedModel(generator, FOTBlocks.RIPE_PINEAPPLE_BLOCK);
        this.generateRotatedExistedModel(generator, FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK);
        this.generateRotatedExistedModel(generator, FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK);
        generator.createTintedLeaves(FOTBlocks.MANGO_LEAVES, TexturedModel.LEAVES, FoliageColor.FOLIAGE_DEFAULT);
        this.createMangoFruit(generator);
        this.createHangingMangoFruit(generator);
        this.generateRotatedExistedModel(generator, FOTBlocks.MANGO_PIT);
        generator.createPlantWithDefaultItem(FOTBlocks.MANGO_SAPLING, FOTBlocks.POTTED_MANGO_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
        this.createPottedMangoPit(generator);
        this.createPomegranatePlant(generator);
        this.createTallPomegranatePlant(generator);
        this.createPottedPomegranatePlant(generator);
        this.createPlant(generator, FOTBlocks.POMEGRANATE_SAPLING, FOTBlocks.POTTED_POMEGRANATE_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
        this.createTropicalRedFern(generator);
        this.createTropicalMonstera(generator);
        generator.woodProvider(FOTBlocks.PRISMARIZED_LOG).logWithHorizontal(FOTBlocks.PRISMARIZED_LOG);
        this.createBuddingPrismarizedLog(generator);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(FOTBlocks.GUARDIAN_FRUIT, plainVariant(ModelLocationUtils.getModelLocation(FOTBlocks.GUARDIAN_FRUIT))));
    }

    private void createBuddingPrismarizedLog(BlockModelGenerators generator)
    {
        var block = FOTBlocks.BUDDING_PRISMARIZED_LOG;
        var textureMapping = new TextureMapping().put(TextureSlot.SIDE, TextureMapping.getBlockTexture(FOTBlocks.PRISMARIZED_LOG)).put(TextureSlot.END, TextureMapping.getBlockTexture(FOTBlocks.PRISMARIZED_LOG, "_top"));
        var buddingModel = ModelTemplates.SINGLE_FACE.create(ModelLocationUtils.getModelLocation(block, "_budding"),
                TextureMapping.defaultTexture(FOTBlocks.BUDDING_PRISMARIZED_LOG), generator.modelOutput);
        var logModel = FOTModelTemplates.CUBE_NO_BOTTOM.create(ModelLocationUtils.getModelLocation(block),
                textureMapping, generator.modelOutput);
        var logHorizontalModel = FOTModelTemplates.CUBE_NO_BOTTOM_HORIZONTAL.create(ModelLocationUtils.getModelLocation(block, "_horizontal"),
                textureMapping, generator.modelOutput);
        var logTopModel = ModelTemplates.SINGLE_FACE.create(ModelLocationUtils.getModelLocation(block, "_top"),
                TextureMapping.defaultTexture(TextureMapping.getBlockTexture(FOTBlocks.PRISMARIZED_LOG, "_top")), generator.modelOutput);
        var logSideModel = ModelTemplates.SINGLE_FACE.create(ModelLocationUtils.getModelLocation(block, "_side"),
                TextureMapping.defaultTexture(TextureMapping.getBlockTexture(FOTBlocks.PRISMARIZED_LOG)), generator.modelOutput);

        generator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                // Main Model
                .with(
                        condition()
                                .term(BlockStateProperties.AXIS, Direction.Axis.Y),
                        plainVariant(logModel)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.AXIS, Direction.Axis.Z),
                        plainVariant(logHorizontalModel).with(BlockModelGenerators.X_ROT_90)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.AXIS, Direction.Axis.X),
                        plainVariant(logHorizontalModel)
                                .with(BlockModelGenerators.X_ROT_90)
                                .with(BlockModelGenerators.Y_ROT_90)
                )

                // Top Texture Axis Y
                .with(
                        condition()
                                .term(BlockStateProperties.AXIS, Direction.Axis.Y)
                                .term(BuddingPrismarizedLogBlock.BUD, false),
                        plainVariant(logTopModel)
                                .with(BlockModelGenerators.X_ROT_90)
                                .with(BlockModelGenerators.UV_LOCK)
                )

                // Budding Texture Axis Y
                .with(
                        condition()
                                .term(BlockStateProperties.AXIS, Direction.Axis.Y)
                                .term(BuddingPrismarizedLogBlock.BUD, true),
                        plainVariant(buddingModel)
                                .with(BlockModelGenerators.X_ROT_90)
                                .with(BlockModelGenerators.UV_LOCK)
                )

                // Top Texture Axis X/Z
                .with(
                        condition()
                                .term(BlockStateProperties.AXIS, Direction.Axis.Z)
                                .term(BuddingPrismarizedLogBlock.BUD, false),
                        plainVariant(logSideModel)
                                .with(BlockModelGenerators.X_ROT_90)
                                .with(BlockModelGenerators.UV_LOCK)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.AXIS, Direction.Axis.X)
                                .term(BuddingPrismarizedLogBlock.BUD, false),
                        plainVariant(logSideModel)
                                .with(BlockModelGenerators.X_ROT_90)
                                .with(BlockModelGenerators.Y_ROT_90)
                                .with(BlockModelGenerators.UV_LOCK)
                )

                // Budding Texture Axis X/Z
                .with(
                        condition()
                                .term(BlockStateProperties.AXIS, Direction.Axis.Z)
                                .term(BuddingPrismarizedLogBlock.BUD, true),
                        plainVariant(buddingModel)
                                .with(BlockModelGenerators.X_ROT_90)
                                .with(BlockModelGenerators.UV_LOCK)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.AXIS, Direction.Axis.X)
                                .term(BuddingPrismarizedLogBlock.BUD, true),
                        plainVariant(buddingModel)
                                .with(BlockModelGenerators.X_ROT_90)
                                .with(BlockModelGenerators.Y_ROT_90)
                                .with(BlockModelGenerators.UV_LOCK)
                )
        );

        generator.registerSimpleItemModel(block, ModelTemplates.CUBE_ALL.create(ModelLocationUtils.getModelLocation(block, "_inventory"),
                TextureMapping.cube(FOTBlocks.BUDDING_PRISMARIZED_LOG), generator.modelOutput));
    }

    private void createTropicalMonstera(BlockModelGenerators generator)
    {
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(FOTBlocks.TROPICAL_MONSTERA, plainVariant(ModelLocationUtils.getModelLocation(FOTBlocks.TROPICAL_MONSTERA))));
        generator.registerSimpleFlatItemModel(FOTBlocks.TROPICAL_MONSTERA, "_leaf_1");

        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(FOTBlocks.POTTED_TROPICAL_MONSTERA, plainVariant(ModelLocationUtils.getModelLocation(FOTBlocks.POTTED_TROPICAL_MONSTERA))));
    }

    private void createTropicalRedFern(BlockModelGenerators generator)
    {
        this.createVerticalLeaves(generator, FOTBlocks.TROPICAL_RED_FERN);
        generator.registerSimpleFlatItemModel(FOTBlocks.TROPICAL_RED_FERN, "_tip");

        var textureMapping = TextureMapping.plant(TextureMapping.getBlockTexture(FOTBlocks.TROPICAL_RED_FERN, "_tip"));
        var resourceLocation = BlockModelGenerators.PlantType.NOT_TINTED.getCrossPot().create(FOTBlocks.POTTED_TROPICAL_RED_FERN, textureMapping, generator.modelOutput);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(FOTBlocks.POTTED_TROPICAL_RED_FERN, plainVariant(resourceLocation)));
    }

    private void createPlant(BlockModelGenerators generator, Block plantBlock, Block pottedPlantBlock, BlockModelGenerators.PlantType tintState)
    {
        generator.createCrossBlock(plantBlock, tintState);
        var textureMapping = TextureMapping.plant(plantBlock);
        var resourceLocation = tintState.getCrossPot().create(pottedPlantBlock, textureMapping, generator.modelOutput);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedPlantBlock, plainVariant(resourceLocation)));
    }

    private void createFishBone(BlockModelGenerators generator)
    {
        var fishBone = FOTBlocks.FISH_BONE;
        generator.registerSimpleFlatItemModel(fishBone.asItem());
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(fishBone, plainVariant(ModelLocationUtils.getModelLocation(fishBone))).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT));
    }

    private void createPottedPomegranatePlant(BlockModelGenerators generator)
    {
        var block = FOTBlocks.POTTED_POMEGRANATE_PLANT;
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(ModelTemplates.POTTED_AZALEA.create(block, new TextureMapping().put(TextureSlot.PLANT, TextureMapping.getBlockTexture(block)).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side")).put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top")), generator.modelOutput))));
    }

    private void createPomegranatePlant(BlockModelGenerators generator)
    {
        var block = FOTBlocks.POMEGRANATE_PLANT;
        var textureMapping = new TextureMapping().put(TextureSlot.PLANT, TextureMapping.getBlockTexture(block)).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side")).put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"));
        var textureMappingFlowering = new TextureMapping().put(TextureSlot.PLANT, TextureMapping.getBlockTexture(block, "_flowering")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side_flowering")).put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top_flowering"));
        var stage0Model = FOTModelTemplates.POMEGRANATE_PLANT.create(ModelLocationUtils.getModelLocation(block), textureMapping, generator.modelOutput);
        var stage1Model = FOTModelTemplates.POMEGRANATE_PLANT.create(ModelLocationUtils.getModelLocation(block, "_flowering"), textureMappingFlowering, generator.modelOutput);

        generator.registerSimpleItemModel(block, stage0Model);
        generator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                // Age 0
                .with(
                        condition()
                                .term(PomegranatePlantBlock.AGE, 0, 2, 3),
                        this.createRotatedVariants(stage0Model)
                )

                // Age 1 Flowering
                .with(
                        condition()
                                .term(PomegranatePlantBlock.AGE, 1),
                        this.createRotatedVariants(stage1Model)
                )

                // Age 2 Fruiting
                .with(
                        condition()
                                .term(PomegranatePlantBlock.AGE, 2),
                        this.createRotatedVariants(ModelLocationUtils.getModelLocation(block, "_fruiting"))
                )

                // Age 3 Fruit
                .with(
                        condition()
                                .term(PomegranatePlantBlock.AGE, 3),
                        this.createRotatedVariants(ModelLocationUtils.getModelLocation(block, "_fruit"))
                )
        );
    }

    private void createTallPomegranatePlant(BlockModelGenerators generator)
    {
        var block = FOTBlocks.TALL_POMEGRANATE_PLANT;
        var textureMapping = new TextureMapping().put(TextureSlot.PLANT, TextureMapping.getBlockTexture(block)).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side")).put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"));
        var textureMappingFlowering = new TextureMapping().put(TextureSlot.PLANT, TextureMapping.getBlockTexture(block, "_flowering")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side_flowering")).put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top_flowering"));
        var upperModel = FOTModelTemplates.TALL_POMEGRANATE_PLANT_UPPER.create(ModelLocationUtils.getModelLocation(block, "_upper"), textureMapping, generator.modelOutput);
        var upperFloweringModel = FOTModelTemplates.TALL_POMEGRANATE_PLANT_UPPER.create(ModelLocationUtils.getModelLocation(block, "_upper_flowering"), textureMappingFlowering, generator.modelOutput);

        generator.registerSimpleItemModel(block, upperModel);
        generator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                // Upper
                .with(
                        condition()
                                .term(TallPomegranatePlantBlock.AGE, 0, 2, 3)
                                .term(TallPomegranatePlantBlock.HALF, DoubleBlockHalf.UPPER),
                        this.createRotatedVariants(upperModel)
                )

                // Upper
                .with(
                        condition()
                                .term(TallPomegranatePlantBlock.AGE, 1)
                                .term(TallPomegranatePlantBlock.HALF, DoubleBlockHalf.UPPER),
                        this.createRotatedVariants(upperFloweringModel)
                )

                // Fruiting
                .with(
                        condition()
                                .term(TallPomegranatePlantBlock.AGE, 2)
                                .term(TallPomegranatePlantBlock.HALF, DoubleBlockHalf.UPPER),
                        this.createRotatedVariants(ModelLocationUtils.getModelLocation(block, "_fruiting"))
                )

                // Fruit
                .with(
                        condition()
                                .term(TallPomegranatePlantBlock.AGE, 3)
                                .term(TallPomegranatePlantBlock.HALF, DoubleBlockHalf.UPPER),
                        this.createRotatedVariants(ModelLocationUtils.getModelLocation(block, "_fruit"))
                )

                // Lower
                .with(
                        condition()
                                .term(TallPomegranatePlantBlock.HALF, DoubleBlockHalf.LOWER),
                        plainVariant(ModelLocationUtils.getModelLocation(block, "_lower"))
                )
        );
    }

    private void createPottedMangoPit(BlockModelGenerators generator)
    {
        var textureMapping = TextureMapping.plant(ModelLocationUtils.getModelLocation(FOTBlocks.MANGO_PIT, "_plant"));
        var resourceLocation = BlockModelGenerators.PlantType.NOT_TINTED.getCrossPot().create(FOTBlocks.POTTED_MANGO_PIT, textureMapping, generator.modelOutput);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(FOTBlocks.POTTED_MANGO_PIT, plainVariant(resourceLocation)));
    }

    private void createHangingMangoFruit(BlockModelGenerators generator)
    {
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(FOTBlocks.HANGING_MANGO_FRUIT)
                .with(PropertyDispatch.initial(HangingMangoFruitBlock.AGE).generate(age ->
                {
                    var model = ModelLocationUtils.getModelLocation(FOTBlocks.HANGING_MANGO_FRUIT, "_stage_" + age);
                    var textureMapping = new TextureMapping().put(FOTModelTemplates.FRUIT, ModelLocationUtils.getModelLocation(FOTBlocks.MANGO_FRUIT, "_stage_" + age));

                    if (age == 0)
                    {
                        return this.createRotatedVariants(model);
                    }
                    else
                    {
                        return this.createRotatedVariants(FOTModelTemplates.HANGING_MANGO_FRUIT.create(model, textureMapping, generator.modelOutput));
                    }
                })));
    }

    private void generateRotatedExistedModel(BlockModelGenerators generator, Block block)
    {
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, this.createRotatedVariants(ModelLocationUtils.getModelLocation(block))));
    }

    private void createMangoFruit(BlockModelGenerators generator)
    {
        var block = FOTBlocks.MANGO_FRUIT;
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(MangoFruitBlock.AGE).generate(age ->
                {
                    var model = ModelLocationUtils.getModelLocation(block, "_stage_" + age);
                    var mirroredModel = ModelLocationUtils.getModelLocation(block, "_stage_" + age + "_mirrored");
                    var textureMapping = new TextureMapping().put(FOTModelTemplates.FRUIT, model);

                    if (age == 0)
                    {
                        return this.createMirroredVariants(model, mirroredModel);
                    }
                    else
                    {
                        return this.createMirroredVariants(
                                FOTModelTemplates.MANGO_FRUIT.create(model, textureMapping, generator.modelOutput),
                                FOTModelTemplates.MANGO_FRUIT_MIRRORED.create(mirroredModel, textureMapping, generator.modelOutput));
                    }
                }))
                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    private void createPineappleCrop(BlockModelGenerators generator)
    {
        var block = FOTBlocks.PINEAPPLE_CROP;
        var fullStageModel = BlockModelGenerators.PlantType.NOT_TINTED.getCross().create(ModelLocationUtils.getModelLocation(block, "_lower_stage_full"), TextureMapping.cross(ModelLocationUtils.getModelLocation(block, "_lower_stage_full")), generator.modelOutput);
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(PineappleCropBlock.AGE, PineappleCropBlock.HALF).generate((age, half) ->
                        {
                            if (half == DoubleBlockHalf.LOWER)
                            {
                                if (age <= 2)
                                {
                                    return plainVariant(BlockModelGenerators.PlantType.NOT_TINTED.getCross().create(ModelLocationUtils.getModelLocation(block, "_" + half + "_stage_" + age), TextureMapping.cross(ModelLocationUtils.getModelLocation(block, "_" + half + "_stage_" + age)), generator.modelOutput));
                                }
                                else if (age > 3)
                                {
                                    return plainVariant(fullStageModel);
                                }
                                else
                                {
                                    return plainVariant(ModelLocationUtils.getModelLocation(block, "_lower_stage_3"));
                                }
                            }
                            else
                            {
                                if (age < 4)
                                {
                                    return plainVariant(ModelLocationUtils.getModelLocation(block, "_lower_stage_0"));
                                }
                                else
                                {
                                    return plainVariant(ModelLocationUtils.getModelLocation(block, "_" + half + "_stage_" + age));
                                }
                            }
                        })
                ));
    }

    private void createBananaStem(BlockModelGenerators generator)
    {
        var block = FOTBlocks.BANANA_STEM;
        var modelLocation = ModelLocationUtils.getModelLocation(block);
        this.createRotatedPillarWithHorizontalVariant(generator, block, modelLocation);
    }

    private void createBananaClusterGrowableStem(BlockModelGenerators generator)
    {
        var block = FOTBlocks.BANANA_CLUSTER_GROWABLE_STEM;
        var topModelLocation = ModelLocationUtils.getModelLocation(FOTBlocks.BANANA_STEM, "_top");
        this.createRotatedPillarWithHorizontalVariant(generator, block, topModelLocation);
    }

    private void createSmallCoconutLog(BlockModelGenerators generator)
    {
        var block = FOTBlocks.SMALL_COCONUT_LOG;
        var textureMapping1 = new TextureMapping().put(TextureSlot.END, ModelLocationUtils.getModelLocation(block, "_top")).copySlot(TextureSlot.END, TextureSlot.TOP).put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG));
        var modelLocation = FOTModelTemplates.SMALL_LOG.create(block, textureMapping1, generator.modelOutput);
        this.createRotatedPillarWithHorizontalVariant(generator, block, modelLocation);
    }

    private void createSmallTopCoconutLog(BlockModelGenerators generator)
    {
        var block = FOTBlocks.SMALL_TOP_COCONUT_LOG;
        var textureMapping = new TextureMapping().put(TextureSlot.END, ModelLocationUtils.getModelLocation(FOTBlocks.SMALL_COCONUT_LOG, "_top")).put(TextureSlot.TOP, ModelLocationUtils.getModelLocation(FOTBlocks.SMALL_COCONUT_LOG, "_trunk")).put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG));
        var topModelLocation = FOTModelTemplates.SMALL_LOG.create(ModelLocationUtils.getModelLocation(block, "_trunk"), textureMapping, generator.modelOutput);
        this.createRotatedPillarWithHorizontalVariant(generator, block, topModelLocation);
    }

    private void createCoconutFruitGrowableLog(BlockModelGenerators generator)
    {
        var block = FOTBlocks.COCONUT_FRUIT_GROWABLE_LOG;
        var textureMapping = new TextureMapping().put(TextureSlot.END, ModelLocationUtils.getModelLocation(FOTBlocks.SMALL_COCONUT_LOG, "_top")).copySlot(TextureSlot.END, TextureSlot.TOP).put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_FRUIT_GROWABLE_LOG));
        var growableModelLocation = FOTModelTemplates.SMALL_LOG.create(ModelLocationUtils.getModelLocation(block), textureMapping, generator.modelOutput);
        this.createRotatedPillarWithHorizontalVariant(generator, block, growableModelLocation);
    }

    private void createRotatedPillarWithHorizontalVariant(BlockModelGenerators generator, Block block, Identifier modelLocation)
    {
        var multiVariant = plainVariant(modelLocation);
        generator.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(block, multiVariant, multiVariant));
    }

    private void createBananaLeaves(BlockModelGenerators generator)
    {
        var block = FOTBlocks.BANANA_LEAVES;

        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block), TextureMapping.layer0(ModelLocationUtils.getModelLocation(block, "_tail")), generator.modelOutput);

        var stemLower = ModelLocationUtils.getModelLocation(block, "_stem_lower");
        var stem2Lower = ModelLocationUtils.getModelLocation(block, "_stem_lower_2");
        var tailLower = ModelLocationUtils.getModelLocation(block, "_tail_lower");
        var tail2Lower = ModelLocationUtils.getModelLocation(block, "_tail_lower_2");

        var stemUpper = ModelLocationUtils.getModelLocation(block, "_stem_upper");
        var stem2Upper = ModelLocationUtils.getModelLocation(block, "_stem_upper_2");
        var tailUpper = ModelLocationUtils.getModelLocation(block, "_tail_upper");
        var tail2Upper = ModelLocationUtils.getModelLocation(block, "_tail_upper_2");

        generator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                // Stem Lower
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(stemLower)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(stemLower)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(stemLower)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(stemLower)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Stem Lower 2
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(stem2Lower)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(stem2Lower)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(stem2Lower)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(stem2Lower)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Tail Lower
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(tailLower)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(tailLower)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(tailLower)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(tailLower)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Tail Lower 2
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(tail2Lower)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(tail2Lower)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(tail2Lower)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.LOWER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(tail2Lower)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Stem Upper
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(stemUpper)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(stemUpper)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(stemUpper)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(stemUpper)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Stem Upper 2
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(stem2Upper)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(stem2Upper)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(stem2Upper)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(stem2Upper)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Tail Upper
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(tailUpper)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(tailUpper)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(tailUpper)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(tailUpper)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Tail Upper 2
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(tail2Upper)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(tail2Upper)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(tail2Upper)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BananaLeavesBlock.COUNT, 2)
                                .term(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)
                                .term(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(tail2Upper)
                                .with(BlockModelGenerators.Y_ROT_270)
                )
        );
    }

    private void createVerticalLeaves(BlockModelGenerators generator, Block block)
    {
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(VerticalLeavesBlock.CEILING)
                        .select(true, plainVariant(ModelLocationUtils.getModelLocation(block)).with(BlockModelGenerators.X_ROT_180))
                        .select(false, plainVariant(ModelLocationUtils.getModelLocation(block)))));
    }

    private void createSmallLog(BlockModelGenerators generator, Block block, Identifier endTexture, Identifier sideTexture)
    {
        var textureMapping = new TextureMapping().put(TextureSlot.END, endTexture).copySlot(TextureSlot.END, TextureSlot.TOP).put(TextureSlot.SIDE, sideTexture);
        var modelLocation = FOTModelTemplates.SMALL_LOG.create(block, textureMapping, generator.modelOutput);
        this.createRotatedPillarWithHorizontalVariant(generator, block, modelLocation);
    }

    private void createMediumLog(BlockModelGenerators generator, Block block, Identifier endTexture, Identifier sideTexture)
    {
        var textureMapping = new TextureMapping().put(TextureSlot.END, endTexture).put(TextureSlot.SIDE, sideTexture);
        var modelLocation = FOTModelTemplates.MEDIUM_LOG.create(block, textureMapping, generator.modelOutput);
        this.createRotatedPillarWithHorizontalVariant(generator, block, modelLocation);
    }

    private void createCoconutFronds(BlockModelGenerators generator)
    {
        var block = FOTBlocks.COCONUT_FRONDS;
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(CoconutFrondsBlock.PART)
                        .generate(part ->
                                plainVariant(ModelLocationUtils.getModelLocation(block, "_" + part.name().toLowerCase(Locale.ROOT)))))
                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT));
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block), TextureMapping.layer0(ModelLocationUtils.getModelLocation(block, "_single")), generator.modelOutput);
    }

    private void createCoconutFruit(BlockModelGenerators generator)
    {
        var block = FOTBlocks.COCONUT_FRUIT;
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(CoconutFruitBlock.AGE)
                        .generate(age -> plainVariant(ModelLocationUtils.getModelLocation(block, "_stage_" + age))))
                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    private void createBananaShootsPlant(BlockModelGenerators generator)
    {
        var block = FOTBlocks.BANANA_SHOOTS_PLANT;
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(ModelLocationUtils.getModelLocation(block)))
                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    private void createBananaBlossom(BlockModelGenerators generator)
    {
        var block = FOTBlocks.BANANA_BLOSSOM;
        generator.registerSimpleFlatItemModel(block.asItem());
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(ModelLocationUtils.getModelLocation(block))));
    }

    private void createBananaBlossomPlant(BlockModelGenerators generator)
    {
        var block = FOTBlocks.BANANA_BLOSSOM_PLANT;
        var clusterModel = ModelLocationUtils.getModelLocation(block, "_cluster");
        var smallCluster = ModelLocationUtils.getModelLocation(block, "_small_cluster");
        var stemCluster = ModelLocationUtils.getModelLocation(block, "_stem");

        generator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                // Normal Cluster
                .with(
                        condition()
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(clusterModel)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(clusterModel)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(clusterModel)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(clusterModel)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Small Cluster
                .with(
                        condition()
                                .term(BananaBlossomPlantBlock.HANGING, BananaHangingType.SMALL_CLUSTER)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(smallCluster)
                )
                .with(
                        condition()
                                .term(BananaBlossomPlantBlock.HANGING, BananaHangingType.SMALL_CLUSTER)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(smallCluster)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BananaBlossomPlantBlock.HANGING, BananaHangingType.SMALL_CLUSTER)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(smallCluster)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BananaBlossomPlantBlock.HANGING, BananaHangingType.SMALL_CLUSTER)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(smallCluster)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Stem Cluster
                .with(
                        condition()
                                .term(BananaBlossomPlantBlock.HANGING, BananaHangingType.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(stemCluster)
                )
                .with(
                        condition()
                                .term(BananaBlossomPlantBlock.HANGING, BananaHangingType.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(stemCluster)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BananaBlossomPlantBlock.HANGING, BananaHangingType.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(stemCluster)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BananaBlossomPlantBlock.HANGING, BananaHangingType.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(stemCluster)
                                .with(BlockModelGenerators.Y_ROT_270)
                )
        );
    }

    private void createBananaCluster(BlockModelGenerators generator, Block block)
    {
        var textureMapping = new TextureMapping().put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(block, "_side")).put(TextureSlot.TOP, ModelLocationUtils.getModelLocation(block, "_top")).put(TextureSlot.BOTTOM, ModelLocationUtils.getModelLocation(block, "_bottom"));
        var normalCluster = FOTModelTemplates.BANANA_CLUSTER.create(block, textureMapping, generator.modelOutput);
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, this.createRotatedVariants(normalCluster)));
    }

    private void createBananaClusterPlant(BlockModelGenerators generator, Block block, Block base)
    {
        var textureMapping = new TextureMapping().put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(base, "_side")).put(TextureSlot.TOP, ModelLocationUtils.getModelLocation(base, "_top")).put(TextureSlot.BOTTOM, ModelLocationUtils.getModelLocation(base, "_bottom"));
        var normalCluster = FOTModelTemplates.BANANA_CLUSTER_PLANT.create(block, textureMapping, generator.modelOutput);
        var smallCluster = FishOfThieves.id("block/").withSuffix("banana_cluster_plant_small_cluster");
        var stemCluster = FishOfThieves.id("block/").withSuffix("banana_cluster_plant_stem");

        generator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                // Normal Cluster
                .with(
                        condition()
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(normalCluster)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(normalCluster)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(normalCluster)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(normalCluster)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Small Cluster
                .with(
                        condition()
                                .term(BananaClusterPlantBlock.HANGING, BananaClusterPlantBlock.HangingType.SMALL_CLUSTER)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(smallCluster)
                )
                .with(
                        condition()
                                .term(BananaClusterPlantBlock.HANGING, BananaClusterPlantBlock.HangingType.SMALL_CLUSTER)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(smallCluster)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BananaClusterPlantBlock.HANGING, BananaClusterPlantBlock.HangingType.SMALL_CLUSTER)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(smallCluster)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BananaClusterPlantBlock.HANGING, BananaClusterPlantBlock.HangingType.SMALL_CLUSTER)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(smallCluster)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Stem Cluster
                .with(
                        condition()
                                .term(BananaClusterPlantBlock.HANGING, BananaClusterPlantBlock.HangingType.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(stemCluster)
                )
                .with(
                        condition()
                                .term(BananaClusterPlantBlock.HANGING, BananaClusterPlantBlock.HangingType.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(stemCluster)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BananaClusterPlantBlock.HANGING, BananaClusterPlantBlock.HangingType.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(stemCluster)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BananaClusterPlantBlock.HANGING, BananaClusterPlantBlock.HangingType.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(stemCluster)
                                .with(BlockModelGenerators.Y_ROT_270)
                )
        );
    }

    private void createUnderripeBananaCluster(BlockModelGenerators generator)
    {
        var block = FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT;
        var normalCluster = ModelLocationUtils.getModelLocation(block);
        var smallCluster = FishOfThieves.id("block/").withSuffix("banana_cluster_plant_small_cluster");
        var stemCluster = FishOfThieves.id("block/").withSuffix("banana_cluster_plant_stem");

        generator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                // Normal Cluster
                .with(
                        condition()
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(normalCluster)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(normalCluster)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(normalCluster)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(normalCluster)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Small Cluster
                .with(
                        condition()
                                .term(UnderripeBananaClusterPlantBlock.HANGING, BananaHangingType.SMALL_CLUSTER)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(smallCluster)
                )
                .with(
                        condition()
                                .term(UnderripeBananaClusterPlantBlock.HANGING, BananaHangingType.SMALL_CLUSTER)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(smallCluster)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(UnderripeBananaClusterPlantBlock.HANGING, BananaHangingType.SMALL_CLUSTER)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(smallCluster)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(UnderripeBananaClusterPlantBlock.HANGING, BananaHangingType.SMALL_CLUSTER)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(smallCluster)
                                .with(BlockModelGenerators.Y_ROT_270)
                )

                // Stem Cluster
                .with(
                        condition()
                                .term(UnderripeBananaClusterPlantBlock.HANGING, BananaHangingType.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                        plainVariant(stemCluster)
                )
                .with(
                        condition()
                                .term(UnderripeBananaClusterPlantBlock.HANGING, BananaHangingType.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                        plainVariant(stemCluster)
                                .with(BlockModelGenerators.Y_ROT_90)
                )
                .with(
                        condition()
                                .term(UnderripeBananaClusterPlantBlock.HANGING, BananaHangingType.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH),
                        plainVariant(stemCluster)
                                .with(BlockModelGenerators.Y_ROT_180)
                )
                .with(
                        condition()
                                .term(UnderripeBananaClusterPlantBlock.HANGING, BananaHangingType.STEM)
                                .term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST),
                        plainVariant(stemCluster)
                                .with(BlockModelGenerators.Y_ROT_270)
                )
        );
    }

    private void createFishPlaque(Block block, Block planks, ModelTemplate template, BlockModelGenerators generator)
    {
        var textureMapping = this.planks(planks);
        var resourceLocation = template.create(block, textureMapping, generator.modelOutput);
        generator.registerSimpleFlatItemModel(block.asItem());
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(resourceLocation)).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    private TextureMapping planks(Block planks)
    {
        return new TextureMapping().put(FOTModelTemplates.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks));
    }

    private <T extends AbstractFishVariant> void generateFlatItemWithFishVariant(Item item, ResourceKey<Registry<T>> registryKey, ItemModelGenerators generator)
    {
        var unbaked = ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(ModelLocationUtils.getModelLocation(item)), generator.modelOutput));
        generator.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(new CustomModelDataProperty(0), unbaked, this.createFishVariantModel(item, registryKey, generator)));
    }

    private <T extends AbstractFishVariant> List<RangeSelectItemModel.Entry> createFishVariantModel(Item item, ResourceKey<Registry<T>> registryKey, ItemModelGenerators generator)
    {
        var variants = this.provider.lookupOrThrow(registryKey).listElements().map(Holder.Reference::value).sorted(Comparator.comparing(AbstractFishVariant::customModelData)).skip(1).toList();
        var suffixes = "_" + BuiltInRegistries.ITEM.getKey(item).getPath();
        var list = Lists.<RangeSelectItemModel.Entry>newArrayList();

        for (var variant : variants)
        {
            var fishModel = FishOfThieves.id(variant.name()).withPath(modelName -> "item/" + modelName + suffixes);
            list.add(ItemModelUtils.override(ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(fishModel, TextureMapping.layer0(fishModel), generator.modelOutput)), (float) variant.customModelData()));
        }
        return list;
    }

    private MultiVariant createRotatedVariants(Identifier modelLocation)
    {
        return new MultiVariant(WeightedList.of(
                new Weighted<>(BlockModelGenerators.plainModel(modelLocation), 1),
                new Weighted<>(BlockModelGenerators.plainModel(modelLocation).with(BlockModelGenerators.Y_ROT_90), 1),
                new Weighted<>(BlockModelGenerators.plainModel(modelLocation).with(BlockModelGenerators.Y_ROT_180), 1),
                new Weighted<>(BlockModelGenerators.plainModel(modelLocation).with(BlockModelGenerators.Y_ROT_270), 1)
        ));
    }

    private MultiVariant createMirroredVariants(Identifier modelLocation, Identifier mirroredModelLocation)
    {
        return new MultiVariant(WeightedList.of(
                new Weighted<>(BlockModelGenerators.plainModel(modelLocation), 1),
                new Weighted<>(BlockModelGenerators.plainModel(mirroredModelLocation), 1)
        ));
    }
}