package com.stevekung.fishofthieves.config;

import com.stevekung.fishofthieves.FishOfThieves;

import net.fabricmc.loader.api.FabricLoader;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

public class FishOfThievesConfig
{
    public static ConfigClassHandler<FishOfThievesConfig> HANDLER = ConfigClassHandler.createBuilder(FishOfThievesConfig.class)
            .id(FishOfThieves.id("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("fishofthieves.json"))
                    .build())
            .build();

    @SerialEntry
    public General general;

    @SerialEntry
    public Biome biome;

    @SerialEntry
    public SpawnRate spawnRate;

    @SerialEntry
    public Shoal shoal;

    @SerialEntry
    public Debug debug;

    public FishOfThievesConfig()
    {
        this.general = new General();
        this.biome = new Biome();
        this.spawnRate = new SpawnRate();
        this.shoal = new Shoal();
        this.debug = new Debug();
    }

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
        @ConfigEntry.Gui.Tooltip(count = 2)
        public boolean enableWormsAttachedHook = true;

        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean simpleSpawningCondition = false;

        @ConfigEntry.Gui.Tooltip(count = 2)
        public boolean neutralFishBehavior = false;

        @ConfigEntry.Gui.Tooltip(count = 2)
        public boolean enableFishItemDropWithVariant = false;

        @ConfigEntry.Gui.Tooltip
        public boolean displayAllFishVariantInCreativeTab = false;

        @ConfigEntry.Gui.Tooltip
        public boolean displayTrophySpawnEggInCreativeTab = false;

        @ConfigEntry.Gui.Tooltip
        public boolean displayTrophyBucketInCreativeTab = false;
    }

    public static class Biome
    {
        @ConfigEntry.Gui.RequiresRestart
        public boolean tropicalIslandBiomeGeneration = true;

        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public float tropicalIslandMinimumWeirdness = -1.2f;

        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public float tropicalIslandMaximumWeirdness = 1.2f;
    }

    public static class SpawnRate
    {
        @ConfigEntry.Gui.Tooltip
        public float trophyProbability = 0.15F;

        @ConfigEntry.Gui.CollapsibleObject
        @ConfigEntry.Gui.Tooltip
        public Variant variant = new Variant();

        @ConfigEntry.Gui.CollapsibleObject
        @ConfigEntry.Gui.Tooltip
        public FishWeight fishWeight = new FishWeight();

        public static class Variant
        {
            @ConfigEntry.Gui.Tooltip
            public float umberSplashtailProbability = 0.1F;

            @ConfigEntry.Gui.Tooltip
            public float brightPondieProbability = 0.1F;

            @ConfigEntry.Gui.Tooltip
            public float ravenIslehopperProbability = 0.1F;

            @ConfigEntry.Gui.Tooltip
            public float boneAncientscaleProbability = 0.1F;

            @ConfigEntry.Gui.Tooltip
            public float bonedustPlentifinProbability = 0.1F;

            @ConfigEntry.Gui.Tooltip
            public float muddyWildsplashProbability = 0.1F;

            @ConfigEntry.Gui.Tooltip
            public float forsakenDevilfishProbability = 0.1F;

            @ConfigEntry.Gui.Tooltip
            public float sandBattlegillProbability = 0.1F;

            @ConfigEntry.Gui.Tooltip
            public float snowWreckerProbability = 0.1F;

            @ConfigEntry.Gui.Tooltip
            public float shadowStormfishProbability = 0.1F;
        }

        public static class FishWeight
        {
            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            public int splashtail = 15;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            public int pondie = 15;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            public int islehopper = 8;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            public int ancientscale = 8;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            public int plentifin = 12;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            public int wildsplash = 10;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            public int devilfish = 4;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            public int battlegill = 5;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            public int wrecker = 50;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            public int stormfish = 12;
        }
    }

    public static class Shoal
    {
        @ConfigEntry.Gui.Tooltip
        public int chance = 2000;

        @ConfigEntry.Gui.Tooltip
        public int chanceRaining = 250;

        @ConfigEntry.Gui.Tooltip
        public int spreadDistance = 64;

        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int maxLifetimeDay = 3;

        @ConfigEntry.Gui.CollapsibleObject
        @ConfigEntry.Gui.Tooltip
        public Weight weight = new Weight();

        public static class Weight
        {
            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int riverOrBeach = 96;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int coast = 88;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int tropicalIsland = 92;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int ocean = 10;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int deepOcean = 5;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
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