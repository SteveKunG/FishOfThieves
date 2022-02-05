package com.stevekung.fishofthieves.utils.forge;

import com.stevekung.fishofthieves.forge.config.FishOfThievesConfig;

public class PlatformConfigImpl
{
    public static float trophyMaxHealth()
    {
        return FishOfThievesConfig.GENERAL.trophyMaxHealth.get();
    }

    public static float trophyProbability()
    {
        return FishOfThievesConfig.SPAWN_RATE.trophyProbability.get();
    }

    public static float umberSplashtailProbability()
    {
        return FishOfThievesConfig.SPAWN_RATE.umberSplashtailProbability.get();
    }

    public static float brightPondieProbability()
    {
        return FishOfThievesConfig.SPAWN_RATE.brightPondieProbability.get();
    }

    public static float ravenIslehopperProbability()
    {
        return FishOfThievesConfig.SPAWN_RATE.ravenIslehopperProbability.get();
    }

    public static float boneAncientscaleProbability()
    {
        return FishOfThievesConfig.SPAWN_RATE.boneAncientscaleProbability.get();
    }

    public static float bonedustPlentifinProbability()
    {
        return FishOfThievesConfig.SPAWN_RATE.bonedustPlentifinProbability.get();
    }

    public static float muddyWildsplashProbability()
    {
        return FishOfThievesConfig.SPAWN_RATE.muddyWildsplashProbability.get();
    }

    public static float forsakenDevilfishProbability()
    {
        return FishOfThievesConfig.SPAWN_RATE.forsakenDevilfishProbability.get();
    }

    public static float sandBattlegillProbability()
    {
        return FishOfThievesConfig.SPAWN_RATE.sandBattlegillProbability.get();
    }

    public static float snowWreckerProbability()
    {
        return FishOfThievesConfig.SPAWN_RATE.snowWreckerProbability.get();
    }

    public static float shadowStormfishProbability()
    {
        return FishOfThievesConfig.SPAWN_RATE.shadowStormfishProbability.get();
    }
}