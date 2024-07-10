package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.fabric.datagen.FOTModelTemplates;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModelProvider extends FabricModelProvider
{
    private static final ModelTemplate SPAWN_EGG = ModelTemplates.createItem("template_spawn_egg");
    private final HolderLookup.Provider provider;

    public ModelProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput);
        this.provider = provider.join();
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator)
    {
        this.generateFlatItemWithFishVariant(FOTItems.SPLASHTAIL, FOTRegistries.SPLASHTAIL_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.PONDIE, FOTRegistries.PONDIE_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.ISLEHOPPER, FOTRegistries.ISLEHOPPER_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.ANCIENTSCALE, FOTRegistries.ANCIENTSCALE_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.PLENTIFIN, FOTRegistries.PLENTIFIN_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.WILDSPLASH, FOTRegistries.WILDSPLASH_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.DEVILFISH, FOTRegistries.DEVILFISH_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.BATTLEGILL, FOTRegistries.BATTLEGILL_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.WRECKER, FOTRegistries.WRECKER_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.STORMFISH, FOTRegistries.STORMFISH_VARIANT, generator.output);

        this.generateFlatItemWithFishVariant(FOTItems.SPLASHTAIL_BUCKET, FOTRegistries.SPLASHTAIL_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.PONDIE_BUCKET, FOTRegistries.PONDIE_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.ISLEHOPPER_BUCKET, FOTRegistries.ISLEHOPPER_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.ANCIENTSCALE_BUCKET, FOTRegistries.ANCIENTSCALE_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.PLENTIFIN_BUCKET, FOTRegistries.PLENTIFIN_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.WILDSPLASH_BUCKET, FOTRegistries.WILDSPLASH_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.DEVILFISH_BUCKET, FOTRegistries.DEVILFISH_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.BATTLEGILL_BUCKET, FOTRegistries.BATTLEGILL_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.WRECKER_BUCKET, FOTRegistries.WRECKER_VARIANT, generator.output);
        this.generateFlatItemWithFishVariant(FOTItems.STORMFISH_BUCKET, FOTRegistries.STORMFISH_VARIANT, generator.output);

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

    private <T extends AbstractFishVariant> void generateFlatItemWithFishVariant(Item item, ResourceKey<Registry<T>> registryKey, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput)
    {
        var overrides = this.provider.lookupOrThrow(registryKey).listElements().map(Holder.Reference::value).sorted(Comparator.comparing(AbstractFishVariant::customModelData)).map(AbstractFishVariant::name).skip(1).toList();
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