package com.stevekung.fishofthieves.fabric.terrablender;

import com.stevekung.fishofthieves.compatibility.terrablender.FOTTerraBlender;

import terrablender.api.TerraBlenderApi;

public class FishOfThievesTerraBlenderFabric implements TerraBlenderApi
{
    @Override
    public void onTerraBlenderInitialized()
    {
        FOTTerraBlender.init();
    }
}