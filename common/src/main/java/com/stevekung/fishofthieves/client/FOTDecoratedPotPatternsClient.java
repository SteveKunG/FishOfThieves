package com.stevekung.fishofthieves.client;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTDecoratedPotPatterns;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;

public class FOTDecoratedPotPatternsClient
{
    public static void init()
    {
        Sheets.DECORATED_POT_MATERIALS.put(FOTDecoratedPotPatterns.STORMFISH, register(FOTDecoratedPotPatterns.STORMFISH_NAME));
        Sheets.DECORATED_POT_MATERIALS.put(FOTDecoratedPotPatterns.KRAKEN, register(FOTDecoratedPotPatterns.KRAKEN_NAME));
        Sheets.DECORATED_POT_MATERIALS.put(FOTDecoratedPotPatterns.MEGALODON, register(FOTDecoratedPotPatterns.MEGALODON_NAME));
        Sheets.DECORATED_POT_MATERIALS.put(FOTDecoratedPotPatterns.GREAT_MOUTH, register(FOTDecoratedPotPatterns.GREAT_MOUTH_NAME));
    }

    private static Material register(String name)
    {
        return new Material(Sheets.DECORATED_POT_SHEET, FishOfThieves.id(name).withPrefix("entity/decorated_pot/"));
    }
}