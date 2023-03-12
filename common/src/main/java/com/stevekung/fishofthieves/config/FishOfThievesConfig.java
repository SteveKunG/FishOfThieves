package com.stevekung.fishofthieves.config;

import com.stevekung.fishofthieves.FishOfThieves;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = FishOfThieves.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/prismarine_bricks.png")
public final class FishOfThievesConfig implements ConfigData
{
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public General general;

    @ConfigEntry.Category("spawn_rate")
    @ConfigEntry.Gui.TransitiveObject
    public SpawnRate spawnRate;

    public FishOfThievesConfig()
    {
        this.general = new General();
        this.spawnRate = new SpawnRate();
    }

    public static class General
    {
        public float trophyMaxHealth = 5.0F;

        public boolean enableEarthwormsDrop = true;

        public boolean enableGrubsDrop = true;

        public boolean enableLeechesDrop = true;

        @ConfigEntry.Gui.RequiresRestart
        @Comment("Average distance between Seapost generation.\nLower value = More closer.")
        public int seapostSpacing = 32;

        @ConfigEntry.Gui.RequiresRestart
        @Comment("Minimum distance (in chunks) between Seapost.\nAnd be not bigger than 'Seapost Spacing'.")
        public int seapostSeparation = 16;

        @ConfigEntry.Gui.RequiresRestart
        @Comment("Allows for easy spawning of all fish variants, regardless of their special conditions.\nIf enabled, spawning will always occur, including for rare and night variants.\nNote: night time and rare variant probability settings still apply.")
        public boolean simpleSpawningCondition = false;

        @Comment("Enable neutral behavior for fishes.\nThis includes Battlegill, Devilfish, Islehopper and Wrecker.")
        public boolean neutralFishBehavior = false;

        @Comment("Display all fish variant in Creative Tab. Including Raw Fishes and Buckets")
        public boolean displayAllFishVariantInCreativeTab = false;

        @Comment("All fish will drop item with their variant, when picked up will applies to bucket and it also applies to loot tables.")
        public boolean dropAndPickFishesWithVariant = false;
    }

    public static class SpawnRate
    {
        @Comment("Chance to spawn Trophy fish size.")
        public float trophyProbability = 0.15F;

        @Comment("Change fish variants probability.")
        @ConfigEntry.Gui.CollapsibleObject
        public Variant variant = new Variant();

        @Comment("Change fish spawn weight.")
        @ConfigEntry.Gui.CollapsibleObject
        public FishWeight fishWeight = new FishWeight();

        public static class Variant
        {
            @Comment("Chance to spawn Umber variant of Splashtails.")
            public float umberSplashtailProbability = 0.1F;

            @Comment("Chance to spawn Bright variant of Pondies.")
            public float brightPondieProbability = 0.1F;

            @Comment("Chance to spawn Raven variant of Islehoppers.")
            public float ravenIslehopperProbability = 0.1F;

            @Comment("Chance to spawn Bone variant of Ancientscales.")
            public float boneAncientscaleProbability = 0.1F;

            @Comment("Chance to spawn Bonedust variant of Plentifins.")
            public float bonedustPlentifinProbability = 0.1F;

            @Comment("Chance to spawn Muddy variant of Wildsplashes.")
            public float muddyWildsplashProbability = 0.1F;

            @Comment("Chance to spawn Forsaken variant of Devilfishes.")
            public float forsakenDevilfishProbability = 0.1F;

            @Comment("Chance to spawn Sand variant of Battlegills.")
            public float sandBattlegillProbability = 0.1F;

            @Comment("Chance to spawn Snow variant of Wreckers.")
            public float snowWreckerProbability = 0.1F;

            @Comment("Chance to spawn Shadow variant of Stormfishes.")
            public float shadowStormfishProbability = 0.1F;
        }

        public static class FishWeight
        {
            @Comment("Weight of Splashtails spawning.")
            @ConfigEntry.Gui.RequiresRestart
            public int splashtail = 15;

            @Comment("Weight of Pondies spawning.")
            @ConfigEntry.Gui.RequiresRestart
            public int pondie = 15;

            @Comment("Weight of Islehoppers spawning.")
            @ConfigEntry.Gui.RequiresRestart
            public int islehopper = 8;

            @Comment("Weight of Ancientscales spawning.")
            @ConfigEntry.Gui.RequiresRestart
            public int ancientscale = 8;

            @Comment("Weight of Plentifins spawning.")
            @ConfigEntry.Gui.RequiresRestart
            public int plentifin = 12;

            @Comment("Weight of Wildsplashes spawning.")
            @ConfigEntry.Gui.RequiresRestart
            public int wildsplash = 10;

            @Comment("Weight of Devilfishes spawning.")
            @ConfigEntry.Gui.RequiresRestart
            public int devilfish = 4;

            @Comment("Weight of Battlegills spawning.")
            @ConfigEntry.Gui.RequiresRestart
            public int battlegill = 5;

            @Comment("Weight of Wreckers spawning.")
            @ConfigEntry.Gui.RequiresRestart
            public int wrecker = 50;

            @Comment("Weight of Stormfishes spawning.")
            @ConfigEntry.Gui.RequiresRestart
            public int stormfish = 12;
        }
    }
}