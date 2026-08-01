package com.stevekung.fishofthieves.mixin.compat.tide;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import com.stevekung.fishofthieves.entity.shoal.Shoal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;

@Mixin(targets = "com.li64.tide.registries.entities.misc.fishing.TideFishingHook")
@Pseudo
@IfModLoaded(value = "tide", minVersion = "2.1")
public class MixinTideFishingHook
{
    @Inject(method = "retrieve(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/player/Player;)I", cancellable = true, at = @At(
            value = "INVOKE",
            target = "net/minecraft/advancements/critereon/FishingRodHookedTrigger.trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/projectile/FishingHook;Ljava/util/Collection;)V",
            shift = At.Shift.AFTER,
            ordinal = 1))
    private void fishofthieves$fishUpShoal(ItemStack rod, ServerLevel level, Player player, CallbackInfoReturnable<Integer> info)
    {
        var durability = Shoal.fishUpShoal(Projectile.class.cast(this), player);

        if (durability > 0)
        {
            info.setReturnValue(durability);
        }
    }
}