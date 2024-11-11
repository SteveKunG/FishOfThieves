package com.stevekung.fishofthieves.fabric.datagen.client;

import com.stevekung.fishofthieves.fabric.datagen.client.provider.ModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ClientDataGenerator implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator)
    {
        var pack = dataGenerator.createPack();
        pack.addProvider(ModelProvider::new);
    }
}