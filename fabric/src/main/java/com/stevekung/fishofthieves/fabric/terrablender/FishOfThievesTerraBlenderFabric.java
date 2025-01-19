package com.stevekung.fishofthieves.fabric.terrablender;

import com.stevekung.fishofthieves.compatibility.terrablender.FOTTerraBlenderBiomeBuilder;

import terrablender.api.TerraBlenderApi;

public class FishOfThievesTerraBlenderFabric implements TerraBlenderApi
{
    @Override
    public void onTerraBlenderInitialized()
    {
        FOTTerraBlenderBiomeBuilder.build();
    }
}