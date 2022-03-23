package com.stevekung.fishofthieves.forge.compatibility;

import com.stevekung.fishofthieves.registry.FOTItems;
import com.teammetallurgy.aquaculture.api.AquacultureAPI;

public class Aquaculture2
{
    public static void init()
    {
        AquacultureAPI.FISH_DATA.add(FOTItems.SPLASHTAIL, 1.5, 50, 5);
        AquacultureAPI.FISH_DATA.add(FOTItems.PONDIE, 1, 50, 5);
        AquacultureAPI.FISH_DATA.add(FOTItems.ISLEHOPPER, 1, 75, 8);
        AquacultureAPI.FISH_DATA.add(FOTItems.ANCIENTSCALE, 2, 75, 8);
        AquacultureAPI.FISH_DATA.add(FOTItems.PLENTIFIN, 1, 50, 5);
        AquacultureAPI.FISH_DATA.add(FOTItems.WILDSPLASH, 1, 100, 8);
        AquacultureAPI.FISH_DATA.add(FOTItems.DEVILFISH, 1, 150, 12);
        AquacultureAPI.FISH_DATA.add(FOTItems.BATTLEGILL, 1, 150, 12);
        AquacultureAPI.FISH_DATA.add(FOTItems.WRECKER, 1, 180, 15);
        AquacultureAPI.FISH_DATA.add(FOTItems.STORMFISH, 1, 200, 20);
    }
}