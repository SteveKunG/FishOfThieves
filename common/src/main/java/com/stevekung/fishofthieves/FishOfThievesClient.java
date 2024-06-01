package com.stevekung.fishofthieves;

import java.util.Map;

import com.google.common.collect.Maps;
import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.mixin.client.MixinCreativeModeTabs;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;

public class FishOfThievesClient
{
    private static final Map<String, Object> PREVIOUS_CONFIG_VALUES = Maps.newHashMap();

    public static void init()
    {
        onConfigLoad();
        AutoConfig.getConfigHolder(FishOfThievesConfig.class).registerSaveListener((holder, config) -> onConfigChanged(config));
    }

    private static void onConfigLoad()
    {
        PREVIOUS_CONFIG_VALUES.put("displayAllFishVariantInCreativeTab", FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab);
        PREVIOUS_CONFIG_VALUES.put("displayTrophySpawnEggInCreativeTab", FishOfThieves.CONFIG.general.displayTrophySpawnEggInCreativeTab);
    }

    private static InteractionResult onConfigChanged(FishOfThievesConfig config)
    {
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;

        if (player != null && (isConfigChanged(config, "displayAllFishVariantInCreativeTab") || isConfigChanged(config, "displayTrophySpawnEggInCreativeTab")))
        {
            MixinCreativeModeTabs.setCACHED_PARAMETERS(new CreativeModeTab.ItemDisplayParameters(player.connection.enabledFeatures(), player.canUseGameMasterBlocks(), player.level().registryAccess()));
            MixinCreativeModeTabs.invokeBuildAllTabContents(MixinCreativeModeTabs.getCACHED_PARAMETERS());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private static boolean isConfigChanged(FishOfThievesConfig config, String configName)
    {
        var previousValue = PREVIOUS_CONFIG_VALUES.get(configName);
        Object currentValue = switch (configName)
        {
            case "displayAllFishVariantInCreativeTab" -> config.general.displayAllFishVariantInCreativeTab;
            case "displayTrophySpawnEggInCreativeTab" -> config.general.displayTrophySpawnEggInCreativeTab;
            default ->
            {
                FishOfThieves.LOGGER.error("Unknown configuration parameter: {}", configName);
                yield false;
            }
        };

        if (previousValue == null || previousValue != currentValue)
        {
            PREVIOUS_CONFIG_VALUES.put(configName, currentValue);
            return true;
        }
        return false;
    }
}