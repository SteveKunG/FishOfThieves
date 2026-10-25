package com.stevekung.fishofthieves.fabric.modmenu;

import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return parent -> FishOfThievesConfig.build().generateScreen(parent);
    }
}