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
        @Comment("Chance to spawn Muddy variant of Wildsplash.")
        public float muddyWildsplashProbability = 0.1F;
        @Comment("Chance to spawn Forsaken variant of Devilfish.")
        public float forsakenDevilfishProbability = 0.1F;
        @Comment("Chance to spawn Sand variant of Battlegills.")
        public float sandBattlegillProbability = 0.1F;
        @Comment("Chance to spawn Snow variant of Wreckers.")
        public float snowWreckerProbability = 0.1F;
        @Comment("Chance to spawn Shadow variant of Stormfish.")
        public float shadowStormfishProbability = 0.1F;
    }
}