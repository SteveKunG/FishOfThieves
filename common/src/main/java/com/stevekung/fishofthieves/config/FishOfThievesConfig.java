package com.stevekung.fishofthieves.config;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.mixin.client.MixinCreativeModeTabs;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;

public class FishOfThievesConfig
{
    private static final OptionEventListener<Boolean> ON_FISH_ITEM_CHANGED = (option, event) ->
    {
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;

        if (player != null && event == OptionEventListener.Event.STATE_CHANGE)
        {
            MixinCreativeModeTabs.setCACHED_PARAMETERS(new CreativeModeTab.ItemDisplayParameters(player.connection.enabledFeatures(), player.canUseGameMasterBlocks(), player.level().registryAccess()));
            MixinCreativeModeTabs.invokeBuildAllTabContents(MixinCreativeModeTabs.getCACHED_PARAMETERS());
        }
    };
    public static final ConfigClassHandler<FishOfThievesConfig> HANDLER = ConfigClassHandler.createBuilder(FishOfThievesConfig.class)
            .id(FishOfThieves.id("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("fishofthieves.json"))
                    .build())
            .build();

    static
    {
        HANDLER.load();
    }

    public static YetAnotherConfigLib build()
    {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.fishofthieves.title"))
                .category(general())
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
                        .name(Component.translatable("config.option.fishofthieves.general.simpleSpawningCondition"))
                        .description(OptionDescription.of(Component.translatable("config.option.fishofthieves.general.simpleSpawningCondition.tooltip0"), Component.translatable("config.option.fishofthieves.general.simpleSpawningCondition.tooltip1"), Component.translatable("config.option.fishofthieves.general.simpleSpawningCondition.tooltip2")))
                        .binding(
                                HANDLER.defaults().general.simpleSpawningCondition,
                                () -> HANDLER.instance().general.simpleSpawningCondition,
                                val -> HANDLER.instance().general.simpleSpawningCondition = val
                        )
                        .flag(OptionFlag.GAME_RESTART)
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
                        .addListener(ON_FISH_ITEM_CHANGED)
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
                        .addListener(ON_FISH_ITEM_CHANGED)
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
                        .addListener(ON_FISH_ITEM_CHANGED)
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
        public boolean simpleSpawningCondition = false;

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
//        @ConfigEntry.Gui.RequiresRestart
        public boolean tropicalIslandBiomeGeneration = true;

        @SerialEntry
//        @ConfigEntry.Gui.RequiresRestart
//        @ConfigEntry.Gui.Tooltip
        public float tropicalIslandMinimumWeirdness = -1.2f;

        @SerialEntry
//        @ConfigEntry.Gui.RequiresRestart
//        @ConfigEntry.Gui.Tooltip
        public float tropicalIslandMaximumWeirdness = 1.2f;
    }

    public static class SpawnRate
    {
        @SerialEntry
//        @ConfigEntry.Gui.Tooltip
        public float trophyProbability = 0.15F;

        @SerialEntry
//        @ConfigEntry.Gui.Tooltip
        public Variant variant = new Variant();

        @SerialEntry
//        @ConfigEntry.Gui.Tooltip
        public FishWeight fishWeight = new FishWeight();

        public static class Variant
        {
            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
            public float umberSplashtailProbability = 0.1F;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
            public float brightPondieProbability = 0.1F;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
            public float ravenIslehopperProbability = 0.1F;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
            public float boneAncientscaleProbability = 0.1F;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
            public float bonedustPlentifinProbability = 0.1F;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
            public float muddyWildsplashProbability = 0.1F;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
            public float forsakenDevilfishProbability = 0.1F;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
            public float sandBattlegillProbability = 0.1F;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
            public float snowWreckerProbability = 0.1F;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
            public float shadowStormfishProbability = 0.1F;
        }

        public static class FishWeight
        {
            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
            public int splashtail = 15;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
            public int pondie = 15;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
            public int islehopper = 8;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
            public int ancientscale = 8;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
            public int plentifin = 12;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
            public int wildsplash = 10;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
            public int devilfish = 4;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
            public int battlegill = 5;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
            public int wrecker = 50;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
            public int stormfish = 12;
        }
    }

    public static class Shoal
    {
        @SerialEntry
//        @ConfigEntry.Gui.Tooltip
        public int chance = 2000;

        @SerialEntry
//        @ConfigEntry.Gui.Tooltip
        public int chanceRaining = 250;

        @SerialEntry
//        @ConfigEntry.Gui.Tooltip
        public int spreadDistance = 64;

        @SerialEntry
//        @ConfigEntry.Gui.Tooltip(count = 2)
//        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int maxLifetimeDay = 3;

        @SerialEntry
//        @ConfigEntry.Gui.Tooltip
        public Weight weight = new Weight();

        public static class Weight
        {
            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
//            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int riverOrBeach = 96;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
//            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int coast = 88;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
//            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int tropicalIsland = 92;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
//            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int ocean = 10;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
//            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int deepOcean = 5;

            @SerialEntry
//            @ConfigEntry.Gui.Tooltip
//            @ConfigEntry.Gui.RequiresRestart
//            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
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