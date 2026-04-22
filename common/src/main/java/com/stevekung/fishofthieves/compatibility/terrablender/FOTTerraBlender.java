package com.stevekung.fishofthieves.compatibility.terrablender;

import com.stevekung.fishofthieves.FishOfThieves;

import terrablender.api.Regions;

public class FOTTerraBlender
{
    public static void init()
    {
        Regions.register(new TropicalIslandRegion(FishOfThieves.id("tropical_island"), FishOfThieves.CONFIG.biome.tropicalIslandRegionWeight));

        //TODO
//        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, FishOfThieves.MOD_ID, FOTSurfaceRuleData.overworld());
    }
}