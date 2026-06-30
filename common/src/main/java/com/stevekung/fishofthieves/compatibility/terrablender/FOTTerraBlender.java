//package com.stevekung.fishofthieves.compatibility.terrablender;
//
//import com.stevekung.fishofthieves.FishOfThieves;
//
//import net.minecraft.core.registries.Registries;
//import net.minecraft.data.registries.VanillaRegistries;
//
//import terrablender.api.Regions;
//import terrablender.api.SurfaceRuleManager;
//
//public class FOTTerraBlender TODO
//{
//    public static void init()
//    {
//        Regions.register(new TropicalIslandRegion(FishOfThieves.id("tropical_island"), FishOfThieves.CONFIG.biome.tropicalIslandRegionWeight));
//
//        var biomes = VanillaRegistries.createLookup().lookupOrThrow(Registries.BIOME);
//        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, FishOfThieves.MOD_ID, FOTSurfaceRuleData.overworld(biomes));
//    }
//}