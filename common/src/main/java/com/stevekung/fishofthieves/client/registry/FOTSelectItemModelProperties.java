package com.stevekung.fishofthieves.client.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.client.item.properties.*;

import net.minecraft.client.core.registries.BuiltInClientRegistries;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.core.Registry;

public class FOTSelectItemModelProperties
{
    public static void init()
    {
        register("splashtail_variant", SplashtailVariantProperty.TYPE);
        register("pondie_variant", PondieVariantProperty.TYPE);
        register("islehopper_variant", IslehopperVariantProperty.TYPE);
        register("ancientscale_variant", AncientscaleVariantProperty.TYPE);
        register("plentifin_variant", PlentifinVariantProperty.TYPE);
        register("wildsplash_variant", WildsplashVariantProperty.TYPE);
        register("devilfish_variant", DevilfishVariantProperty.TYPE);
        register("battlegill_variant", BattlegillVariantProperty.TYPE);
        register("wrecker_variant", WreckerVariantProperty.TYPE);
        register("stormfish_variant", StormfishVariantProperty.TYPE);
    }

    private static void register(String key, SelectItemModelProperty.Type<?, ?> type)
    {
        Registry.register(BuiltInClientRegistries.SELECT_ITEM_MODEL_PROPERTY_TYPE, FishOfThieves.id(key), type);
    }
}