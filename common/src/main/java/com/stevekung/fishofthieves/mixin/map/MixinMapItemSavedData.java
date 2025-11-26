package com.stevekung.fishofthieves.mixin.map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;

import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

@Mixin(MapItemSavedData.class)
public class MixinMapItemSavedData
{
    @Inject(method = "isExplorationMap", cancellable = true, at = @At(value = "FIELD", target = "net/minecraft/world/level/saveddata/maps/MapDecoration$Type.MANSION:Lnet/minecraft/world/level/saveddata/maps/MapDecoration$Type;"))
    private void fishofthieves$isExplorationMap(CallbackInfoReturnable<Boolean> info, @Local MapDecoration mapDecoration)
    {
        if (mapDecoration.getType() == FOTMapDecorationTypes.TREASURED_FISH)
        {
            info.setReturnValue(true);
        }
    }
}