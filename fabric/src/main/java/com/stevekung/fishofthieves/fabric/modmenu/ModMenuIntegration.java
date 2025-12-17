package com.stevekung.fishofthieves.fabric.modmenu;

import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.autoconfig.AutoConfigClient;

public class ModMenuIntegration implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return parent -> AutoConfigClient.getConfigScreen(FishOfThievesConfig.class, parent).get();
    }
}