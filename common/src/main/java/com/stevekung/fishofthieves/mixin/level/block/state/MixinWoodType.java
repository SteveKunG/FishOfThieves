package com.stevekung.fishofthieves.mixin.level.block.state;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.fishofthieves.registry.FOTBlockSetTypes;
import com.stevekung.fishofthieves.registry.FOTWoodTypes;

import net.minecraft.world.level.block.state.properties.WoodType;

@Mixin(WoodType.class)
public class MixinWoodType
{
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void fishofthieves$initWoodTypeEarly(CallbackInfo info)
    {
        FOTWoodTypes.COCONUT = WoodType.register(new WoodType("fot_coconut", FOTBlockSetTypes.COCONUT));
    }
}