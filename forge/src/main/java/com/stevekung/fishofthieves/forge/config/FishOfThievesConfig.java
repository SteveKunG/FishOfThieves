package com.stevekung.fishofthieves.forge.config;

import com.stevekung.fishofthieves.core.FishOfThieves;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class FishOfThievesConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final FishOfThievesConfig.General GENERAL = new FishOfThievesConfig.General(FishOfThievesConfig.BUILDER);
    public static final FishOfThievesConfig.SpawnRate SPAWN_RATE = new FishOfThievesConfig.SpawnRate(FishOfThievesConfig.BUILDER);

    static
    {
        SPEC = BUILDER.build();
    }

    public static class General
    {
        public final ForgeConfigSpec.ConfigValue<Float> trophyMaxHealth;

        General(ForgeConfigSpec.Builder builder)
        {
            builder.comment("General settings").push("general");

            this.trophyMaxHealth = builder.translation("text.autoconfig.fish_of_thieves.option.general.trophyMaxHealth").define("trophyMaxHealth", 5.0F);

            builder.pop();
        }
    }

    public static class SpawnRate
    {
        public final ForgeConfigSpec.ConfigValue<Float> trophyProbability;
        public final ForgeConfigSpec.ConfigValue<Float> umberSplashtailProbability;
        public final ForgeConfigSpec.ConfigValue<Float> brightPondieProbability;
        public final ForgeConfigSpec.ConfigValue<Float> ravenIslehopperProbability;
        public final ForgeConfigSpec.ConfigValue<Float> boneAncientscaleProbability;
        public final ForgeConfigSpec.ConfigValue<Float> bonedustPlentifinProbability;
        public final ForgeConfigSpec.ConfigValue<Float> muddyWildsplashProbability;
        public final ForgeConfigSpec.ConfigValue<Float> forsakenDevilfishProbability;
        public final ForgeConfigSpec.ConfigValue<Float> sandBattlegillProbability;
        public final ForgeConfigSpec.ConfigValue<Float> snowWreckerProbability;
        public final ForgeConfigSpec.ConfigValue<Float> shadowStormfishProbability;

        SpawnRate(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Spawn rate settings").push("spawn_rate");

            this.trophyProbability = builder.comment("Chance of spawning trophy.").translation("text.autoconfig.fish_of_thieves.option.spawn_rate.trophyProbability").define("trophyProbability", 0.15F);
            this.umberSplashtailProbability = builder.comment("Chance of spawning Umber variant of Splashtails.").translation("text.autoconfig.fish_of_thieves.option.spawn_rate.umberSplashtailProbability").define("umberSplashtailProbability", 0.1F);
            this.brightPondieProbability = builder.comment("Chance of spawning Bright variant of Pondies.").translation("text.autoconfig.fish_of_thieves.option.spawn_rate.brightPondieProbability").define("brightPondieProbability", 0.1F);
            this.ravenIslehopperProbability = builder.comment("Chance of spawning Raven variant of Islehoppers.").translation("text.autoconfig.fish_of_thieves.option.spawn_rate.ravenIslehopperProbability").define("ravenIslehopperProbability", 0.1F);
            this.boneAncientscaleProbability = builder.comment("Chance of spawning Bone variant of Ancientscales.").translation("text.autoconfig.fish_of_thieves.option.spawn_rate.boneAncientscaleProbability").define("boneAncientscaleProbability", 0.1F);
            this.bonedustPlentifinProbability = builder.comment("Chance of spawning Bonedust variant of Plentifins.").translation("text.autoconfig.fish_of_thieves.option.spawn_rate.bonedustPlentifinProbability").define("bonedustPlentifinProbability", 0.1F);
            this.muddyWildsplashProbability = builder.comment("Chance of spawning Muddy variant of Wildsplashes.").translation("text.autoconfig.fish_of_thieves.option.spawn_rate.muddyWildsplashProbability").define("muddyWildsplashProbability", 0.1F);
            this.forsakenDevilfishProbability = builder.comment("Chance of spawning Forsaken variant of Devilfishes.").translation("text.autoconfig.fish_of_thieves.option.spawn_rate.forsakenDevilfishProbability").define("forsakenDevilfishProbability", 0.1F);
            this.sandBattlegillProbability = builder.comment("Chance of spawning Sand variant of Battlegills.").translation("text.autoconfig.fish_of_thieves.option.spawn_rate.sandBattlegillProbability").define("sandBattlegillProbability", 0.1F);
            this.snowWreckerProbability = builder.comment("Chance of spawning Snow variant of Wreckers.").translation("text.autoconfig.fish_of_thieves.option.spawn_rate.snowWreckerProbability").define("snowWreckerProbability", 0.1F);
            this.shadowStormfishProbability = builder.comment("Chance of spawning Shadow variant of Stormfishes.").translation("text.autoconfig.fish_of_thieves.option.spawn_rate.shadowStormfishProbability").define("shadowStormfishProbability", 0.1F);

            builder.pop();
        }
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event)
    {
        FishOfThieves.LOGGER.info("Loaded config file {}", event.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(ModConfigEvent.Reloading event)
    {
        FishOfThieves.LOGGER.info("Fish of Thieves config just got changed on the file system");
    }
}