package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.block.*;
import com.stevekung.fishofthieves.fabric.datagen.FOTModelTemplates;
import com.stevekung.fishofthieves.registry.FOTBlockFamilies;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class ModelProvider extends FabricModelProvider
{
    private static final ModelTemplate SPAWN_EGG = ModelTemplates.createItem("template_spawn_egg");

    public ModelProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator)
    {
        this.generateFlatItemWithFishVariant(FOTItems.SPLASHTAIL, List.of("sunny", "indigo", "umber", "seafoam"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.PONDIE, List.of("orchid", "bronze", "bright", "moonsky"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.ISLEHOPPER, List.of("moss", "honey", "raven", "amethyst"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.ANCIENTSCALE, List.of("sapphire", "smoke", "bone", "starshine"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.PLENTIFIN, List.of("amber", "cloudy", "bonedust", "watery"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.WILDSPLASH, List.of("sandy", "ocean", "muddy", "coral"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.DEVILFISH, List.of("seashell", "lava", "forsaken", "firelight"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.BATTLEGILL, List.of("sky", "rum", "sand", "bittersweet"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.WRECKER, List.of("sun", "blackcloud", "snow", "moon"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.STORMFISH, List.of("shores", "wild", "shadow", "twilight"), generator.output);

        this.generateFlatItemWithFishVariant(FOTItems.SPLASHTAIL_BUCKET, List.of("sunny", "indigo", "umber", "seafoam"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.PONDIE_BUCKET, List.of("orchid", "bronze", "bright", "moonsky"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.ISLEHOPPER_BUCKET, List.of("moss", "honey", "raven", "amethyst"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.ANCIENTSCALE_BUCKET, List.of("sapphire", "smoke", "bone", "starshine"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.PLENTIFIN_BUCKET, List.of("amber", "cloudy", "bonedust", "watery"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.WILDSPLASH_BUCKET, List.of("sandy", "ocean", "muddy", "coral"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.DEVILFISH_BUCKET, List.of("seashell", "lava", "forsaken", "firelight"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.BATTLEGILL_BUCKET, List.of("sky", "rum", "sand", "bittersweet"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.WRECKER_BUCKET, List.of("sun", "blackcloud", "snow", "moon"), generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.STORMFISH_BUCKET, List.of("shores", "wild", "shadow", "twilight"), generator.output);

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
        generator.generateFlatItem(FOTBlocks.BANANA_BLOSSOM.asItem(), ModelTemplates.FLAT_ITEM);

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
        generator.generateFlatItem(FOTBlocks.COCONUT_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);

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
        generator.generateFlatItem(FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);

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
        generator.generateFlatItem(FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);

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
        generator.generateFlatItem(FOTBlocks.GILDED_COCONUT_FISH_PLAQUE.asItem(), ModelTemplates.FLAT_ITEM);

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
        generator.generateFlatItem(FOTItems.MANGO_SEED, ModelTemplates.FLAT_ITEM);
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

        generator.createPlant(FOTBlocks.PINK_PLUMERIA, FOTBlocks.POTTED_PINK_PLUMERIA, BlockModelGenerators.TintState.NOT_TINTED);
        generator.woodProvider(FOTBlocks.COCONUT_LOG).logWithHorizontal(FOTBlocks.COCONUT_LOG).wood(FOTBlocks.COCONUT_WOOD);
        generator.woodProvider(FOTBlocks.STRIPPED_COCONUT_LOG).logWithHorizontal(FOTBlocks.STRIPPED_COCONUT_LOG).wood(FOTBlocks.STRIPPED_COCONUT_WOOD);
        this.createSmallCoconutLog(generator);
        this.createGrowableSmallCoconutLog(generator);
        this.createTopSmallCoconutLog(generator);
        this.createSmallLog(generator, FOTBlocks.SMALL_COCONUT_WOOD, ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG), ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG));
        this.createMediumLog(generator, FOTBlocks.MEDIUM_COCONUT_LOG, ModelLocationUtils.getModelLocation(FOTBlocks.MEDIUM_COCONUT_LOG, "_top"), ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG));
        this.createMediumLog(generator, FOTBlocks.MEDIUM_COCONUT_WOOD, ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG), ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG));
        this.createMediumLog(generator, FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG, ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG, "_top"), ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_COCONUT_LOG));
        this.createMediumLog(generator, FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD, ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_COCONUT_LOG), ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_COCONUT_LOG));
        this.createSmallLog(generator, FOTBlocks.STRIPPED_SMALL_COCONUT_LOG, ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_SMALL_COCONUT_LOG, "_top"), ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_COCONUT_LOG));
        this.createSmallLog(generator, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD, ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_COCONUT_LOG), ModelLocationUtils.getModelLocation(FOTBlocks.STRIPPED_COCONUT_LOG));
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(FOTBlocks.COCONUT_SAPLING, ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_SAPLING)));
        this.createCoconutFruit(generator);
        this.createCoconutFronds(generator);
        this.createBananaLeaves(generator);
        this.createBananaStem(generator);
        this.createGrowableBananaStem(generator);
        this.createTopBananaStem(generator);
        this.createVerticalLeaves(generator, FOTBlocks.VERTICAL_BANANA_LEAVES);
        this.createVerticalLeaves(generator, FOTBlocks.VERTICAL_COCONUT_FRONDS);
        generator.family(FOTBlocks.COCONUT_PLANKS).generateFor(FOTBlockFamilies.COCONUT_PLANKS);
        generator.createHangingSign(FOTBlocks.STRIPPED_COCONUT_LOG, FOTBlocks.COCONUT_HANGING_SIGN, FOTBlocks.COCONUT_WALL_HANGING_SIGN);
        this.createBananaShootsPlant(generator);
        this.createBananaBlossom(generator);
        this.createBananaBlossomPlant(generator);
        this.createUnderripeBananaCluster(generator);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(FOTBlocks.UNDERRIPE_BANANA_CLUSTER, ModelLocationUtils.getModelLocation(FOTBlocks.UNDERRIPE_BANANA_CLUSTER)));
        this.createBananaCluster(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER, generator);
        this.createBananaCluster(FOTBlocks.RIPE_BANANA_CLUSTER, generator);
        this.createBananaClusterPlant(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT, FOTBlocks.BARELY_RIPE_BANANA_CLUSTER, generator);
        this.createBananaClusterPlant(FOTBlocks.RIPE_BANANA_CLUSTER_PLANT, FOTBlocks.RIPE_BANANA_CLUSTER, generator);
        generator.createPlant(FOTBlocks.BANANA_SHOOTS, FOTBlocks.POTTED_BANANA_SHOOTS, BlockModelGenerators.TintState.NOT_TINTED);
        this.createPineappleCrop(generator);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(FOTBlocks.RIPE_PINEAPPLE_BLOCK, ModelLocationUtils.getModelLocation(FOTBlocks.RIPE_PINEAPPLE_BLOCK)));
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK, ModelLocationUtils.getModelLocation(FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK)));
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK, ModelLocationUtils.getModelLocation(FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK)));
        generator.createTrivialBlock(FOTBlocks.MANGO_LEAVES, TexturedModel.LEAVES);
        this.createMangoFruit(generator);
        this.createHangingMangoFruit(generator);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(FOTBlocks.MANGO_SEED, ModelLocationUtils.getModelLocation(FOTBlocks.MANGO_SEED)));
        generator.createPlant(FOTBlocks.MANGO_SAPLING, FOTBlocks.POTTED_MANGO_SAPLING, BlockModelGenerators.TintState.NOT_TINTED);
        this.createPottedMangoSeed(generator);
    }

    private void createPottedMangoSeed(BlockModelGenerators generator)
    {
        var textureMapping = TextureMapping.plant(ModelLocationUtils.getModelLocation(FOTBlocks.MANGO_SEED, "_plant"));
        var resourceLocation = BlockModelGenerators.TintState.NOT_TINTED.getCrossPot().create(FOTBlocks.POTTED_MANGO_SEED, textureMapping, generator.modelOutput);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(FOTBlocks.POTTED_MANGO_SEED, resourceLocation));
    }

    private void createHangingMangoFruit(BlockModelGenerators generator)
    {
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(FOTBlocks.HANGING_MANGO_FRUIT)
                .with(PropertyDispatch.property(HangingMangoFruitBlock.AGE).generate(age ->
                {
                    var model = ModelLocationUtils.getModelLocation(FOTBlocks.HANGING_MANGO_FRUIT, "_stage_" + age);
                    var textureMapping = new TextureMapping().put(FOTModelTemplates.FRUIT, ModelLocationUtils.getModelLocation(FOTBlocks.MANGO_FRUIT, "_stage_" + age));

                    if (age == 0)
                    {
                        return Variant.variant().with(VariantProperties.MODEL, model);
                    }
                    else
                    {
                        return Variant.variant().with(VariantProperties.MODEL, FOTModelTemplates.HANGING_MANGO_FRUIT.create(model, textureMapping, generator.modelOutput));
                    }
                })));
    }

    private void createMangoFruit(BlockModelGenerators generator)
    {
        var block = FOTBlocks.MANGO_FRUIT;
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(BlockModelGenerators.createHorizontalFacingDispatch())
                .with(PropertyDispatch.property(MangoFruitBlock.AGE).generate(age ->
                {
                    var model = ModelLocationUtils.getModelLocation(block, "_stage_" + age);
                    var textureMapping = new TextureMapping().put(FOTModelTemplates.FRUIT, model);

                    if (age == 0)
                    {
                        return Variant.variant().with(VariantProperties.MODEL, model);
                    }
                    else
                    {
                        return Variant.variant().with(VariantProperties.MODEL, FOTModelTemplates.MANGO_FRUIT.create(model, textureMapping, generator.modelOutput));
                    }
                })));
    }

    private void createPineappleCrop(BlockModelGenerators generator)
    {
        var block = FOTBlocks.PINEAPPLE_CROP;
        var fullStageModel = BlockModelGenerators.TintState.NOT_TINTED.getCross().create(ModelLocationUtils.getModelLocation(block, "_lower_stage_full"), TextureMapping.cross(ModelLocationUtils.getModelLocation(block, "_lower_stage_full")), generator.modelOutput);
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.properties(PineappleCropBlock.AGE, PineappleCropBlock.HALF).generate((age, half) ->
                        {
                            if (half == DoubleBlockHalf.LOWER)
                            {
                                if (age <= 2)
                                {
                                    return Variant.variant().with(VariantProperties.MODEL, BlockModelGenerators.TintState.NOT_TINTED.getCross().create(ModelLocationUtils.getModelLocation(block, "_" + half + "_stage_" + age), TextureMapping.cross(ModelLocationUtils.getModelLocation(block, "_" + half + "_stage_" + age)), generator.modelOutput));
                                }
                                else if (age > 3)
                                {
                                    return Variant.variant().with(VariantProperties.MODEL, fullStageModel);
                                }
                                else
                                {
                                    return Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_lower_stage_3"));
                                }
                            }
                            else
                            {
                                if (age < 4)
                                {
                                    return Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_lower_stage_0"));
                                }
                                else
                                {
                                    return Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_" + half + "_stage_" + age));
                                }
                            }
                        })
                ));
    }

    private void createBananaStem(BlockModelGenerators generator)
    {
        var block = FOTBlocks.BANANA_STEM;
        var modelLocation = ModelLocationUtils.getModelLocation(block);
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(BlockStateProperties.AXIS)
                        .select(Direction.Axis.Y, Variant.variant().with(VariantProperties.MODEL, modelLocation))
                        .select(Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, modelLocation)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, modelLocation)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                ));
    }

    private void createGrowableBananaStem(BlockModelGenerators generator)
    {
        var block = FOTBlocks.GROWABLE_BANANA_STEM;
        var modelLocation = ModelLocationUtils.getModelLocation(FOTBlocks.BANANA_STEM);
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(BlockStateProperties.AXIS)
                        .select(Direction.Axis.Y, Variant.variant().with(VariantProperties.MODEL, modelLocation))
                        .select(Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, modelLocation)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, modelLocation)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                ));
    }

    private void createTopBananaStem(BlockModelGenerators generator)
    {
        var block = FOTBlocks.TOP_BANANA_STEM;
        var topModelLocation = ModelLocationUtils.getModelLocation(FOTBlocks.BANANA_STEM, "_top");
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(BlockStateProperties.AXIS)
                        .select(Direction.Axis.Y, Variant.variant().with(VariantProperties.MODEL, topModelLocation))
                        .select(Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, topModelLocation)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, topModelLocation)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                ));
    }

    private void createSmallCoconutLog(BlockModelGenerators generator)
    {
        var block = FOTBlocks.SMALL_COCONUT_LOG;
        var textureMapping1 = new TextureMapping().put(TextureSlot.END, ModelLocationUtils.getModelLocation(block, "_top")).copySlot(TextureSlot.END, TextureSlot.TOP).put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG));
        var modelLocation = FOTModelTemplates.SMALL_LOG.create(block, textureMapping1, generator.modelOutput);
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(BlockStateProperties.AXIS)
                        .select(Direction.Axis.Y, Variant.variant().with(VariantProperties.MODEL, modelLocation))
                        .select(Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, modelLocation)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, modelLocation)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                ));
    }

    private void createTopSmallCoconutLog(BlockModelGenerators generator)
    {
        var block = FOTBlocks.TOP_SMALL_COCONUT_LOG;
        var textureMapping = new TextureMapping().put(TextureSlot.END, ModelLocationUtils.getModelLocation(FOTBlocks.SMALL_COCONUT_LOG, "_top")).put(TextureSlot.TOP, ModelLocationUtils.getModelLocation(FOTBlocks.SMALL_COCONUT_LOG, "_trunk")).put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG));
        var topModelLocation = FOTModelTemplates.SMALL_LOG.create(ModelLocationUtils.getModelLocation(block, "_trunk"), textureMapping, generator.modelOutput);
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(BlockStateProperties.AXIS)
                        .select(Direction.Axis.Y, Variant.variant().with(VariantProperties.MODEL, topModelLocation))
                        .select(Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, topModelLocation)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, topModelLocation)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                ));
    }

    private void createGrowableSmallCoconutLog(BlockModelGenerators generator)
    {
        var block = FOTBlocks.GROWABLE_SMALL_COCONUT_LOG;
        var textureMapping = new TextureMapping().put(TextureSlot.END, ModelLocationUtils.getModelLocation(FOTBlocks.SMALL_COCONUT_LOG, "_top")).copySlot(TextureSlot.END, TextureSlot.TOP).put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(FOTBlocks.COCONUT_LOG, "_growable"));
        var growableModelLocation = FOTModelTemplates.SMALL_LOG.create(ModelLocationUtils.getModelLocation(block, "_growable"), textureMapping, generator.modelOutput);
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(BlockStateProperties.AXIS)
                        .select(Direction.Axis.Y, Variant.variant().with(VariantProperties.MODEL, growableModelLocation))
                        .select(Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, growableModelLocation)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, growableModelLocation)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                ));
    }

    private void createBananaLeaves(BlockModelGenerators generator)
    {
        var block = FOTBlocks.BANANA_LEAVES;
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(BlockModelGenerators.createHorizontalFacingDispatchAlt())
                .with(PropertyDispatch.properties(BananaLeavesBlock.TYPE, BananaLeavesBlock.PART, BananaLeavesBlock.COUNT)
                        .select(BananaLeavesBlock.Type.LOWER, BananaLeavesBlock.Part.STEM, 1, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_stem_lower")))
                        .select(BananaLeavesBlock.Type.LOWER, BananaLeavesBlock.Part.TAIL, 1, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_tail_lower")))
                        .select(BananaLeavesBlock.Type.UPPER, BananaLeavesBlock.Part.STEM, 1, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_stem_upper")))
                        .select(BananaLeavesBlock.Type.UPPER, BananaLeavesBlock.Part.TAIL, 1, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_tail_upper")))
                        .select(BananaLeavesBlock.Type.LOWER, BananaLeavesBlock.Part.STEM, 2, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_stem_lower_2")))
                        .select(BananaLeavesBlock.Type.LOWER, BananaLeavesBlock.Part.TAIL, 2, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_tail_lower_2")))
                        .select(BananaLeavesBlock.Type.UPPER, BananaLeavesBlock.Part.STEM, 2, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_stem_upper_2")))
                        .select(BananaLeavesBlock.Type.UPPER, BananaLeavesBlock.Part.TAIL, 2, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_tail_upper_2")))
                ));
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block), TextureMapping.layer0(ModelLocationUtils.getModelLocation(block, "_tail")), generator.modelOutput);
    }

    private void createVerticalLeaves(BlockModelGenerators generator, Block block)
    {
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(VerticalLeavesBlock.CEILING)
                        .select(true, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block)))
                        .select(false, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block)))));
    }

    private void createSmallLog(BlockModelGenerators generator, Block block, ResourceLocation endTexture, ResourceLocation sideTexture)
    {
        var textureMapping = new TextureMapping().put(TextureSlot.END, endTexture).copySlot(TextureSlot.END, TextureSlot.TOP).put(TextureSlot.SIDE, sideTexture);
        var resourceLocation = FOTModelTemplates.SMALL_LOG.create(block, textureMapping, generator.modelOutput);
        generator.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(block, resourceLocation, resourceLocation));
    }

    private void createMediumLog(BlockModelGenerators generator, Block block, ResourceLocation endTexture, ResourceLocation sideTexture)
    {
        var textureMapping = new TextureMapping().put(TextureSlot.END, endTexture).put(TextureSlot.SIDE, sideTexture);
        var resourceLocation = FOTModelTemplates.MEDIUM_LOG.create(block, textureMapping, generator.modelOutput);
        generator.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(block, resourceLocation, resourceLocation));
    }

    private void createCoconutFronds(BlockModelGenerators generator)
    {
        var block = FOTBlocks.COCONUT_FRONDS;
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(BlockModelGenerators.createHorizontalFacingDispatchAlt())
                .with(PropertyDispatch.property(CoconutFrondsBlock.PART)
                        .generate(part -> Variant.variant()
                                .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_" + part.name().toLowerCase(Locale.ROOT))))));
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block), TextureMapping.layer0(ModelLocationUtils.getModelLocation(block, "_single")), generator.modelOutput);
    }

    private void createCoconutFruit(BlockModelGenerators generator)
    {
        var block = FOTBlocks.COCONUT_FRUIT;
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(BlockModelGenerators.createHorizontalFacingDispatch())
                .with(PropertyDispatch.property(CoconutFruitBlock.AGE)
                        .generate(age -> Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_stage_" + age)))));
    }

    private void createBananaShootsPlant(BlockModelGenerators generator)
    {
        var block = FOTBlocks.BANANA_SHOOTS_PLANT;
        generator.skipAutoItemBlock(block);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, ModelLocationUtils.getModelLocation(block))
                .with(BlockModelGenerators.createHorizontalFacingDispatch()));
    }

    private void createBananaBlossom(BlockModelGenerators generator)
    {
        var block = FOTBlocks.BANANA_BLOSSOM;
        generator.skipAutoItemBlock(block);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, ModelLocationUtils.getModelLocation(block)));
    }

    private void createBananaBlossomPlant(BlockModelGenerators generator)
    {
        var block = FOTBlocks.BANANA_BLOSSOM_PLANT;
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, ModelLocationUtils.getModelLocation(block))
                .with(BlockModelGenerators.createHorizontalFacingDispatch())
                .with(PropertyDispatch.property(BananaBlossomPlantBlock.HANGING)
                        .generate(hanging -> Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_hanging_" + hanging.getSerializedName())))));
    }

    private void createBananaCluster(Block block, BlockModelGenerators generator)
    {
        var textureMapping = new TextureMapping().put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(block, "_side")).put(TextureSlot.TOP, ModelLocationUtils.getModelLocation(block, "_top")).put(TextureSlot.BOTTOM, ModelLocationUtils.getModelLocation(block, "_bottom"));
        var normalCluster = FOTModelTemplates.BANANA_CLUSTER.create(block, textureMapping, generator.modelOutput);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, normalCluster));
    }

    private void createBananaClusterPlant(Block block, Block base, BlockModelGenerators generator)
    {
        var textureMapping = new TextureMapping().put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(base, "_side")).put(TextureSlot.TOP, ModelLocationUtils.getModelLocation(base, "_top")).put(TextureSlot.BOTTOM, ModelLocationUtils.getModelLocation(base, "_bottom"));
        var normalCluster = FOTModelTemplates.BANANA_CLUSTER_PLANT.create(block, textureMapping, generator.modelOutput);
        var smallCluster = FOTModelTemplates.BANANA_CLUSTER_PLANT_SMALL_CLUSTER.create(ModelLocationUtils.getModelLocation(block, "_small_cluster"), textureMapping, generator.modelOutput);
        var stemCluster = FOTModelTemplates.BANANA_CLUSTER_PLANT_STEM.create(ModelLocationUtils.getModelLocation(block, "_stem"), textureMapping, generator.modelOutput);

        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(BlockModelGenerators.createHorizontalFacingDispatch())
                .with(PropertyDispatch.property(BananaClusterPlantBlock.HANGING)
                        .select(BananaClusterPlantBlock.HangingType.NONE, Variant.variant()
                                .with(VariantProperties.MODEL, normalCluster))
                        .select(BananaClusterPlantBlock.HangingType.SMALL_CLUSTER, Variant.variant()
                                .with(VariantProperties.MODEL, smallCluster))
                        .select(BananaClusterPlantBlock.HangingType.STEM, Variant.variant()
                                .with(VariantProperties.MODEL, stemCluster))
                ));
    }

    private void createUnderripeBananaCluster(BlockModelGenerators generator)
    {
        var block = FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT;
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, ModelLocationUtils.getModelLocation(block))
                .with(BlockModelGenerators.createHorizontalFacingDispatch())
                .with(PropertyDispatch.property(UnderripeBananaClusterPlantBlock.HANGING)
                        .select(BananaHangingType.CLUSTER, Variant.variant()
                                .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block)))
                        .select(BananaHangingType.SMALL_CLUSTER, Variant.variant()
                                .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_small_cluster")))
                        .select(BananaHangingType.STEM, Variant.variant()
                                .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block, "_stem")))
                ));
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

    private void generateFlatItemWithFishVariant(Item item, List<String> overrides, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput)
    {
        var suffixes = "_" + BuiltInRegistries.ITEM.getKey(item).getPath();

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