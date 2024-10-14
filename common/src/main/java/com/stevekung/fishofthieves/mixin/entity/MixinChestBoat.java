package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.stevekung.fishofthieves.registry.FOTBoatTypes;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;

@Mixin(ChestBoat.class)
public class MixinChestBoat
{
    @Inject(method = "getDropItem", cancellable = true, at = @At("TAIL"))
    private void fishofthieves$getBoatDrop(CallbackInfoReturnable<Item> info)
    {
        if (Boat.class.cast(this).getVariant() == FOTBoatTypes.COCONUT)
        {
            info.setReturnValue(FOTItems.COCONUT_CHEST_BOAT);
        }
    }
}