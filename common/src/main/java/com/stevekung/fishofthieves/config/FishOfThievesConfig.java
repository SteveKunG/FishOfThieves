package com.stevekung.fishofthieves.config;

import com.stevekung.fishofthieves.FishOfThieves;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = FishOfThieves.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/prismarine_bricks.png")
public final class FishOfThievesConfig implements ConfigData
{
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public General general;

    @ConfigEntry.Category("biome")
    @ConfigEntry.Gui.TransitiveObject
    public Biome biome;

    @ConfigEntry.Category("spawn_rate")
    @ConfigEntry.Gui.TransitiveObject
    public SpawnRate spawnRate;

    @ConfigEntry.Category("shoal")
    @ConfigEntry.Gui.TransitiveObject
    public Shoal shoal;

    @ConfigEntry.Category("debug")
    @ConfigEntry.Gui.TransitiveObject
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
        public float trophyMaxHealth = 5.0F;

        public boolean enableEarthwormsDrop = true;

        public boolean enableGrubsDrop = true;

        public boolean enableLeechesDrop = true;

        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean simpleSpawningCondition = false;

        @ConfigEntry.Gui.Tooltip(count = 2)
        public boolean neutralFishBehavior = false;

        @ConfigEntry.Gui.Tooltip
        public boolean displayAllFishVariantInCreativeTab = false;

        @ConfigEntry.Gui.Tooltip(count = 2)
        public boolean enableFishItemWithAllVariant = false;

        @ConfigEntry.Gui.Tooltip
        public boolean displayTrophySpawnEggInCreativeTab = false;
    }

    public static class Biome
    {
        @ConfigEntry.Gui.RequiresRestart
        public int tropicalIslandRegionWeight = 8;
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
            public int riverOrBeach = 4;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int coast = 12;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int ocean = 90;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int deepOcean = 95;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.Gui.RequiresRestart
            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int swamp = 20;
        }
    }

    public static class Debug
    {
        public boolean enableStructureCenterPosRender = true;
        public boolean displayStructureCenterPosInfo = true;
        public int structureCenterPosRangeLimit = 64;
        public boolean spawnBeaconAtShoal = false;
    }
}