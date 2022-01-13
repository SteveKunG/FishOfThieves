package com.stevekung.fishofthieves.datagen;

import com.stevekung.fishofthieves.FOTItems;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockStateDefinitionProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

public class FOTDataGeneratorEntrypoint implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator)
    {
        dataGenerator.addProvider(ModelProvider::new);
    }

    private static class ModelProvider extends FabricBlockStateDefinitionProvider
    {
        private ModelProvider(FabricDataGenerator generator)
        {
            super(generator);
        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerator)
        {
            itemModelGenerator.generateFlatItem(FOTItems.SPLASHTAIL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FOTItems.SPLASHTAIL_BUCKET, ModelTemplates.FLAT_ITEM);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {}
    }
}