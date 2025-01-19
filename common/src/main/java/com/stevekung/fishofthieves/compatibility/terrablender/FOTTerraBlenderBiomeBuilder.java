package com.stevekung.fishofthieves.compatibility.terrablender;

import com.stevekung.fishofthieves.FishOfThieves;

import terrablender.api.Regions;

public class FOTTerraBlenderBiomeBuilder
{
    public static void build()
    {
        Regions.register(new TropicalIslandsRegion(FishOfThieves.id("tropical_islands"), 2));
    }
}