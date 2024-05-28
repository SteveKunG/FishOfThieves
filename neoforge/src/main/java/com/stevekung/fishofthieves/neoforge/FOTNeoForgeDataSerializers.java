package com.stevekung.fishofthieves.neoforge;

import com.stevekung.fishofthieves.common.registry.FOTDataSerializers;

public class FOTNeoForgeDataSerializers
{
    public static void init()
    {
        FishOfThievesNeoForge.ENTITY_DATA_SERIALIZERS.register("splashtail_variant", () -> FOTDataSerializers.SPLASHTAIL_VARIANT);
        FishOfThievesNeoForge.ENTITY_DATA_SERIALIZERS.register("pondie_variant", () -> FOTDataSerializers.PONDIE_VARIANT);
        FishOfThievesNeoForge.ENTITY_DATA_SERIALIZERS.register("islehopper_variant", () -> FOTDataSerializers.ISLEHOPPER_VARIANT);
        FishOfThievesNeoForge.ENTITY_DATA_SERIALIZERS.register("ancientscale_variant", () -> FOTDataSerializers.ANCIENTSCALE_VARIANT);
        FishOfThievesNeoForge.ENTITY_DATA_SERIALIZERS.register("plentifin_variant", () -> FOTDataSerializers.PLENTIFIN_VARIANT);
        FishOfThievesNeoForge.ENTITY_DATA_SERIALIZERS.register("wildsplash_variant", () -> FOTDataSerializers.WILDSPLASH_VARIANT);
        FishOfThievesNeoForge.ENTITY_DATA_SERIALIZERS.register("devilfish_variant", () -> FOTDataSerializers.DEVILFISH_VARIANT);
        FishOfThievesNeoForge.ENTITY_DATA_SERIALIZERS.register("battlegill_variant", () -> FOTDataSerializers.BATTLEGILL_VARIANT);
        FishOfThievesNeoForge.ENTITY_DATA_SERIALIZERS.register("wrecker_variant", () -> FOTDataSerializers.WRECKER_VARIANT);
        FishOfThievesNeoForge.ENTITY_DATA_SERIALIZERS.register("stormfish_variant", () -> FOTDataSerializers.STORMFISH_VARIANT);
    }
}