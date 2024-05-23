package com.stevekung.fishofthieves.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

@Mixin(PotionBrewing.class)
public class MixinPotionBrewing
{
    @Inject(method = "addVanillaMixes", at = @At("TAIL"))
    private static void fishofthieves$addVanillaMixes(PotionBrewing.Builder builder, CallbackInfo info)
    {
        builder.addMix(Potions.AWKWARD, FOTItems.PLENTIFIN, Potions.LUCK);
        builder.addMix(Potions.AWKWARD, FOTItems.ISLEHOPPER, Potions.WATER_BREATHING);
    }
}