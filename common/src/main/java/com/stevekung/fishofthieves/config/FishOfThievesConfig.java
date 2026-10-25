package com.stevekung.fishofthieves.config;

import com.google.gson.FieldNamingPolicy;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.mixin.client.MixinCreativeModeTabs;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.controllers.slider.FloatSliderController;
import dev.isxander.yacl3.platform.YACLPlatform;

public class FishOfThievesConfig
{
    public static final ConfigClassHandler<FishOfThievesConfig> HANDLER = ConfigClassHandler.createBuilder(FishOfThievesConfig.class)
            .id(FishOfThieves.id("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .appendGsonBuilder(builder -> builder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY))
                    .setPath(YACLPlatform.getConfigDir().resolve("fishofthieves.json"))
                    .build())
            .build();

    static
    {
        HANDLER.load();
    }

    private static void rebuildCreativeTab(Minecraft minecraft)
    {
        var player = minecraft.player;

        if (player != null)
        {
            MixinCreativeModeTabs.setCACHED_PARAMETERS(new CreativeModeTab.ItemDisplayParameters(player.connection.enabledFeatures(), player.canUseGameMasterBlocks(), player.registryAccess()));
            MixinCreativeModeTabs.invokeBuildAllTabContents(MixinCreativeModeTabs.getCACHED_PARAMETERS());
        }
    }

    public static YetAnotherConfigLib build()
    {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.fishofthieves.title"))
                .category(general())
                .category(biome())
                .category(spawnRate())
                .category(shoal())
                .category(debug())
                .save(HANDLER::save)
                .build();
    }

    public static ConfigCategory general()
    {
        return ConfigCategory.createBuilder().name(Component.translatable("config.category.fishofthieves.general.title"))
                .option(Option.<Float>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.general.trophyMaxHealth"))
                        .binding(
                                HANDLER.defaults().general.trophyMaxHealth,
                                () -> HANDLER.instance().general.trophyMaxHealth,
                                val -> HANDLER.instance().general.trophyMaxHealth = val
                        )
                        .controller(opt -> FloatFieldControllerBuilder.create(opt).min(0f))
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.general.enableEarthwormsDrop"))
                        .binding(
                                HANDLER.defaults().general.enableEarthwormsDrop,
                                () -> HANDLER.instance().general.enableEarthwormsDrop,
                                val -> HANDLER.instance().general.enableEarthwormsDrop = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.general.enableGrubsDrop"))
                        .binding(
                                HANDLER.defaults().general.enableGrubsDrop,
                                () -> HANDLER.instance().general.enableGrubsDrop,
                                val -> HANDLER.instance().general.enableGrubsDrop = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.general.enableLeechesDrop"))
                        .binding(
                                HANDLER.defaults().general.enableLeechesDrop,
                                () -> HANDLER.instance().general.enableLeechesDrop,
                                val -> HANDLER.instance().general.enableLeechesDrop = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.general.enableWormsAttachedHook"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.general.enableWormsAttachedHook.tooltip0"), Component.translatable("config.option.fishofthieves.general.enableWormsAttachedHook.tooltip1")))
                        .binding(
                                HANDLER.defaults().general.enableWormsAttachedHook,
                                () -> HANDLER.instance().general.enableWormsAttachedHook,
                                val -> HANDLER.instance().general.enableWormsAttachedHook = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.general.neutralFishBehavior"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.general.neutralFishBehavior.tooltip0"), Component.translatable("config.option.fishofthieves.general.neutralFishBehavior.tooltip1")))
                        .binding(
                                HANDLER.defaults().general.neutralFishBehavior,
                                () -> HANDLER.instance().general.neutralFishBehavior,
                                val -> HANDLER.instance().general.neutralFishBehavior = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.general.enableFishItemDropWithVariant"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.general.enableFishItemDropWithVariant.tooltip0"), Component.translatable("config.option.fishofthieves.general.enableFishItemDropWithVariant.tooltip1")))
                        .binding(
                                HANDLER.defaults().general.enableFishItemDropWithVariant,
                                () -> HANDLER.instance().general.enableFishItemDropWithVariant,
                                val -> HANDLER.instance().general.enableFishItemDropWithVariant = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.general.displayAllFishVariantInCreativeTab"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.general.displayAllFishVariantInCreativeTab.tooltip")))
                        .binding(
                                HANDLER.defaults().general.displayAllFishVariantInCreativeTab,
                                () -> HANDLER.instance().general.displayAllFishVariantInCreativeTab,
                                val -> HANDLER.instance().general.displayAllFishVariantInCreativeTab = val
                        )
                        .flag(FishOfThievesConfig::rebuildCreativeTab)
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.general.displayTrophySpawnEggInCreativeTab"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.general.displayTrophySpawnEggInCreativeTab.tooltip")))
                        .binding(
                                HANDLER.defaults().general.displayTrophySpawnEggInCreativeTab,
                                () -> HANDLER.instance().general.displayTrophySpawnEggInCreativeTab,
                                val -> HANDLER.instance().general.displayTrophySpawnEggInCreativeTab = val
                        )
                        .flag(FishOfThievesConfig::rebuildCreativeTab)
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.general.displayTrophyBucketInCreativeTab"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.general.displayTrophyBucketInCreativeTab.tooltip")))
                        .binding(
                                HANDLER.defaults().general.displayTrophyBucketInCreativeTab,
                                () -> HANDLER.instance().general.displayTrophyBucketInCreativeTab,
                                val -> HANDLER.instance().general.displayTrophyBucketInCreativeTab = val
                        )
                        .flag(FishOfThievesConfig::rebuildCreativeTab)
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .build();
    }

    public static ConfigCategory biome()
    {
        return ConfigCategory.createBuilder().name(Component.translatable("config.category.fishofthieves.biome.title"))
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.biome.tropicalIslandBiomeGeneration"))
                        .binding(
                                HANDLER.defaults().biome.tropicalIslandBiomeGeneration,
                                () -> HANDLER.instance().biome.tropicalIslandBiomeGeneration,
                                val -> HANDLER.instance().biome.tropicalIslandBiomeGeneration = val
                        )
                        .flag(OptionFlag.GAME_RESTART)
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Float>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.biome.tropicalIslandMinimumWeirdness"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.biome.tropicalIslandMinimumWeirdness.tooltip")))
                        .binding(
                                HANDLER.defaults().biome.tropicalIslandMinimumWeirdness,
                                () -> HANDLER.instance().biome.tropicalIslandMinimumWeirdness,
                                val -> HANDLER.instance().biome.tropicalIslandMinimumWeirdness = val
                        )
                        .flag(OptionFlag.GAME_RESTART)
                        .customController(opt -> FloatSliderController.createInternal(opt,
                                -2f,
                                2f,
                                0.1f,
                                val -> Component.literal(String.valueOf(val))))
                        .build()
                )
                .option(Option.<Float>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.biome.tropicalIslandMaximumWeirdness"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.biome.tropicalIslandMaximumWeirdness.tooltip")))
                        .binding(
                                HANDLER.defaults().biome.tropicalIslandMaximumWeirdness,
                                () -> HANDLER.instance().biome.tropicalIslandMaximumWeirdness,
                                val -> HANDLER.instance().biome.tropicalIslandMaximumWeirdness = val
                        )
                        .flag(OptionFlag.GAME_RESTART)
                        .customController(opt -> FloatSliderController.createInternal(opt,
                                -2f,
                                2f,
                                0.1f,
                                val -> Component.literal(String.valueOf(val))))
                        .build()
                )
                .build();
    }

    public static ConfigCategory spawnRate()
    {
        return ConfigCategory.createBuilder().name(Component.translatable("config.category.fishofthieves.spawn_rate.title"))
                .option(Option.<Float>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.spawnRate.trophyProbability"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.spawnRate.trophyProbability.tooltip")))
                        .binding(
                                HANDLER.defaults().spawnRate.trophyProbability,
                                () -> HANDLER.instance().spawnRate.trophyProbability,
                                val -> HANDLER.instance().spawnRate.trophyProbability = val
                        )
                        .controller(opt -> FloatFieldControllerBuilder.create(opt).min(0f).max(1f))
                        .build()
                )
                .group(OptionGroup.createBuilder()
                        .collapsed(true)
                        .name(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.tooltip")))
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.splashtail"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.splashtail.tooltip")))
                                .binding(
                                        HANDLER.defaults().spawnRate.fishWeight.splashtail,
                                        () -> HANDLER.instance().spawnRate.fishWeight.splashtail,
                                        val -> HANDLER.instance().spawnRate.fishWeight.splashtail = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.pondie"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.pondie.tooltip")))
                                .binding(
                                        HANDLER.defaults().spawnRate.fishWeight.pondie,
                                        () -> HANDLER.instance().spawnRate.fishWeight.pondie,
                                        val -> HANDLER.instance().spawnRate.fishWeight.pondie = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.islehopper"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.islehopper.tooltip")))
                                .binding(
                                        HANDLER.defaults().spawnRate.fishWeight.islehopper,
                                        () -> HANDLER.instance().spawnRate.fishWeight.islehopper,
                                        val -> HANDLER.instance().spawnRate.fishWeight.islehopper = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.ancientscale"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.ancientscale.tooltip")))
                                .binding(
                                        HANDLER.defaults().spawnRate.fishWeight.ancientscale,
                                        () -> HANDLER.instance().spawnRate.fishWeight.ancientscale,
                                        val -> HANDLER.instance().spawnRate.fishWeight.ancientscale = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.plentifin"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.plentifin.tooltip")))
                                .binding(
                                        HANDLER.defaults().spawnRate.fishWeight.plentifin,
                                        () -> HANDLER.instance().spawnRate.fishWeight.plentifin,
                                        val -> HANDLER.instance().spawnRate.fishWeight.plentifin = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.wildsplash"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.wildsplash.tooltip")))
                                .binding(
                                        HANDLER.defaults().spawnRate.fishWeight.wildsplash,
                                        () -> HANDLER.instance().spawnRate.fishWeight.wildsplash,
                                        val -> HANDLER.instance().spawnRate.fishWeight.wildsplash = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.devilfish"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.devilfish.tooltip")))
                                .binding(
                                        HANDLER.defaults().spawnRate.fishWeight.devilfish,
                                        () -> HANDLER.instance().spawnRate.fishWeight.devilfish,
                                        val -> HANDLER.instance().spawnRate.fishWeight.devilfish = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.battlegill"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.battlegill.tooltip")))
                                .binding(
                                        HANDLER.defaults().spawnRate.fishWeight.battlegill,
                                        () -> HANDLER.instance().spawnRate.fishWeight.battlegill,
                                        val -> HANDLER.instance().spawnRate.fishWeight.battlegill = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.wrecker"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.wrecker.tooltip")))
                                .binding(
                                        HANDLER.defaults().spawnRate.fishWeight.wrecker,
                                        () -> HANDLER.instance().spawnRate.fishWeight.wrecker,
                                        val -> HANDLER.instance().spawnRate.fishWeight.wrecker = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.stormfish"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.spawnRate.fishWeight.stormfish.tooltip")))
                                .binding(
                                        HANDLER.defaults().spawnRate.fishWeight.stormfish,
                                        () -> HANDLER.instance().spawnRate.fishWeight.stormfish,
                                        val -> HANDLER.instance().spawnRate.fishWeight.stormfish = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build())
                        .build())
                .build();
    }

    public static ConfigCategory shoal()
    {
        return ConfigCategory.createBuilder().name(Component.translatable("config.category.fishofthieves.shoal.title"))
                .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.shoal.chance"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.shoal.chance.tooltip")))
                        .binding(
                                HANDLER.defaults().shoal.chance,
                                () -> HANDLER.instance().shoal.chance,
                                val -> HANDLER.instance().shoal.chance = val
                        )
                        .controller(IntegerFieldControllerBuilder::create)
                        .build()
                )
                .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.shoal.chanceRaining"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.shoal.chanceRaining.tooltip")))
                        .binding(
                                HANDLER.defaults().shoal.chanceRaining,
                                () -> HANDLER.instance().shoal.chanceRaining,
                                val -> HANDLER.instance().shoal.chanceRaining = val
                        )
                        .controller(IntegerFieldControllerBuilder::create)
                        .build()
                )
                .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.shoal.spreadDistance"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.shoal.spreadDistance.tooltip")))
                        .binding(
                                HANDLER.defaults().shoal.spreadDistance,
                                () -> HANDLER.instance().shoal.spreadDistance,
                                val -> HANDLER.instance().shoal.spreadDistance = val
                        )
                        .controller(IntegerFieldControllerBuilder::create)
                        .build()
                )
                .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.shoal.maxLifetimeDay"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.shoal.maxLifetimeDay.tooltip0"), Component.translatable("config.option.fishofthieves.shoal.maxLifetimeDay.tooltip1")))
                        .binding(
                                HANDLER.defaults().shoal.maxLifetimeDay,
                                () -> HANDLER.instance().shoal.maxLifetimeDay,
                                val -> HANDLER.instance().shoal.maxLifetimeDay = val
                        )
                        .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                        .build()
                )
                .group(OptionGroup.createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.shoal.weight"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.shoal.weight.tooltip")))
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.shoal.weight.riverOrBeach"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.shoal.weight.riverOrBeach.tooltip")))
                                .binding(
                                        HANDLER.defaults().shoal.weight.riverOrBeach,
                                        () -> HANDLER.instance().shoal.weight.riverOrBeach,
                                        val -> HANDLER.instance().shoal.weight.riverOrBeach = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build()
                        )
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.shoal.weight.coast"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.shoal.weight.coast.tooltip")))
                                .binding(
                                        HANDLER.defaults().shoal.weight.coast,
                                        () -> HANDLER.instance().shoal.weight.coast,
                                        val -> HANDLER.instance().shoal.weight.coast = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build()
                        )
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.shoal.weight.tropicalIsland"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.shoal.weight.tropicalIsland.tooltip")))
                                .binding(
                                        HANDLER.defaults().shoal.weight.tropicalIsland,
                                        () -> HANDLER.instance().shoal.weight.tropicalIsland,
                                        val -> HANDLER.instance().shoal.weight.tropicalIsland = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build()
                        )
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.shoal.weight.ocean"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.shoal.weight.ocean.tooltip")))
                                .binding(
                                        HANDLER.defaults().shoal.weight.ocean,
                                        () -> HANDLER.instance().shoal.weight.ocean,
                                        val -> HANDLER.instance().shoal.weight.ocean = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build()
                        )
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.shoal.weight.deepOcean"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.shoal.weight.deepOcean.tooltip")))
                                .binding(
                                        HANDLER.defaults().shoal.weight.deepOcean,
                                        () -> HANDLER.instance().shoal.weight.deepOcean,
                                        val -> HANDLER.instance().shoal.weight.deepOcean = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build()
                        )
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.option.fishofthieves.shoal.weight.swamp"))
                                .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.shoal.weight.swamp.tooltip")))
                                .binding(
                                        HANDLER.defaults().shoal.weight.swamp,
                                        () -> HANDLER.instance().shoal.weight.swamp,
                                        val -> HANDLER.instance().shoal.weight.swamp = val
                                )
                                .flag(OptionFlag.GAME_RESTART)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1).max(100))
                                .build()
                        )
                        .build())
                .build();
    }

    public static ConfigCategory debug()
    {
        return ConfigCategory.createBuilder().name(Component.literal("Debug"))
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.debug.enableStructureCenterPosRender"))
                        .binding(
                                HANDLER.defaults().debug.enableStructureCenterPosRender,
                                () -> HANDLER.instance().debug.enableStructureCenterPosRender,
                                val -> HANDLER.instance().debug.enableStructureCenterPosRender = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.debug.displayStructureCenterPosInfo"))
                        .binding(
                                HANDLER.defaults().debug.displayStructureCenterPosInfo,
                                () -> HANDLER.instance().debug.displayStructureCenterPosInfo,
                                val -> HANDLER.instance().debug.displayStructureCenterPosInfo = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.debug.structureCenterPosRangeLimit"))
                        .binding(
                                HANDLER.defaults().debug.structureCenterPosRangeLimit,
                                () -> HANDLER.instance().debug.structureCenterPosRangeLimit,
                                val -> HANDLER.instance().debug.structureCenterPosRangeLimit = val
                        )
                        .controller(IntegerFieldControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.option.fishofthieves.debug.spawnBeaconAtShoal"))
                        .binding(
                                HANDLER.defaults().debug.spawnBeaconAtShoal,
                                () -> HANDLER.instance().debug.spawnBeaconAtShoal,
                                val -> HANDLER.instance().debug.spawnBeaconAtShoal = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .build();
    }

    @SerialEntry
    public General general = new General();

    @SerialEntry
    public Biome biome = new Biome();

    @SerialEntry
    public SpawnRate spawnRate = new SpawnRate();

    @SerialEntry
    public Shoal shoal = new Shoal();

    @SerialEntry
    public Debug debug = new Debug();

    public static class General
    {
        @SerialEntry
        public float trophyMaxHealth = 5.0F;

        @SerialEntry
        public boolean enableEarthwormsDrop = true;

        @SerialEntry
        public boolean enableGrubsDrop = true;

        @SerialEntry
        public boolean enableLeechesDrop = true;

        @SerialEntry
        public boolean enableWormsAttachedHook = true;

        @SerialEntry
        public boolean neutralFishBehavior = false;

        @SerialEntry
        public boolean enableFishItemDropWithVariant = false;

        @SerialEntry
        public boolean displayAllFishVariantInCreativeTab = false;

        @SerialEntry
        public boolean displayTrophySpawnEggInCreativeTab = false;

        @SerialEntry
        public boolean displayTrophyBucketInCreativeTab = false;
    }

    public static class Biome
    {
        @SerialEntry
        public boolean tropicalIslandBiomeGeneration = true;

        @SerialEntry
        public float tropicalIslandMinimumWeirdness = -1.2f;

        @SerialEntry
        public float tropicalIslandMaximumWeirdness = 1.2f;
    }

    public static class SpawnRate
    {
        @SerialEntry
        public float trophyProbability = 0.15F;

        @SerialEntry
        public FishWeight fishWeight = new FishWeight();

        public static class FishWeight
        {
            @SerialEntry
            public int splashtail = 15;

            @SerialEntry
            public int pondie = 15;

            @SerialEntry
            public int islehopper = 8;

            @SerialEntry
            public int ancientscale = 8;

            @SerialEntry
            public int plentifin = 12;

            @SerialEntry
            public int wildsplash = 10;

            @SerialEntry
            public int devilfish = 4;

            @SerialEntry
            public int battlegill = 5;

            @SerialEntry
            public int wrecker = 50;

            @SerialEntry
            public int stormfish = 12;
        }
    }

    public static class Shoal
    {
        @SerialEntry
        public int chance = 2000;

        @SerialEntry
        public int chanceRaining = 250;

        @SerialEntry
        public int spreadDistance = 64;

        @SerialEntry
        public int maxLifetimeDay = 3;

        @SerialEntry
        public Weight weight = new Weight();

        public static class Weight
        {
            @SerialEntry
            public int riverOrBeach = 96;

            @SerialEntry
            public int coast = 88;

            @SerialEntry
            public int tropicalIsland = 92;

            @SerialEntry
            public int ocean = 10;

            @SerialEntry
            public int deepOcean = 5;

            @SerialEntry
            public int swamp = 80;
        }
    }

    public static class Debug
    {
        @SerialEntry
        public boolean enableStructureCenterPosRender = true;

        @SerialEntry
        public boolean displayStructureCenterPosInfo = true;

        @SerialEntry
        public int structureCenterPosRangeLimit = 64;

        @SerialEntry
        public boolean spawnBeaconAtShoal = false;
    }
}