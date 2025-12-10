package com.stevekung.fishofthieves.mixin.client.item.properties;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.client.item.properties.*;

import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;

@Mixin(SelectItemModelProperties.class)
public class MixinSelectItemModelProperties
{
    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void fishofthieves$registerItemModelProperties(CallbackInfo info)
    {
        SelectItemModelProperties.ID_MAPPER.put(FishOfThieves.id("splashtail_variant"), SplashtailVariantProperty.TYPE);
        SelectItemModelProperties.ID_MAPPER.put(FishOfThieves.id("pondie_variant"), PondieVariantProperty.TYPE);
        SelectItemModelProperties.ID_MAPPER.put(FishOfThieves.id("islehopper_variant"), IslehopperVariantProperty.TYPE);
        SelectItemModelProperties.ID_MAPPER.put(FishOfThieves.id("ancientscale_variant"), AncientscaleVariantProperty.TYPE);
        SelectItemModelProperties.ID_MAPPER.put(FishOfThieves.id("plentifin_variant"), PlentifinVariantProperty.TYPE);
        SelectItemModelProperties.ID_MAPPER.put(FishOfThieves.id("wildsplash_variant"), WildsplashVariantProperty.TYPE);
        SelectItemModelProperties.ID_MAPPER.put(FishOfThieves.id("devilfish_variant"), DevilfishVariantProperty.TYPE);
        SelectItemModelProperties.ID_MAPPER.put(FishOfThieves.id("battlegill_variant"), BattlegillVariantProperty.TYPE);
        SelectItemModelProperties.ID_MAPPER.put(FishOfThieves.id("wrecker_variant"), WreckerVariantProperty.TYPE);
        SelectItemModelProperties.ID_MAPPER.put(FishOfThieves.id("stormfish_variant"), StormfishVariantProperty.TYPE);
    }
}