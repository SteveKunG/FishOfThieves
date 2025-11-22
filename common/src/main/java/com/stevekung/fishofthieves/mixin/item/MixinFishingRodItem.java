package com.stevekung.fishofthieves.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.entity.FishingHookBait;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(FishingRodItem.class)
public class MixinFishingRodItem
{
    @Inject(method = "use", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/projectile/FishingHook.retrieve(Lnet/minecraft/world/item/ItemStack;)I"))
    private void fishofthieves$addBaitOnRetrieve(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResult> info, @Local ItemStack itemStack)
    {
        var baitStack = player.fishing.fishofthieves$getBaitStack();

        if (!baitStack.isEmpty() && !player.getAbilities().instabuild && player.fishing.nibble == 0)
        {
            if (itemStack.isEmpty())
            {
                player.setItemInHand(usedHand, baitStack);
            }
            else if (!player.addItem(baitStack))
            {
                player.drop(baitStack, false);
            }
        }
    }

    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/projectile/Projectile.spawnProjectile(Lnet/minecraft/world/entity/projectile/Projectile;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/projectile/Projectile;"))
    private <T extends Projectile> T fishofthieves$setBaitOnCast(T projectile, ServerLevel level, ItemStack spawnedFrom, Operation<T> operation, @Local(argsOnly = true) Player player)
    {
        var baitStack = FishingHookBait.getBait(player);

        if (projectile instanceof FishingHook fishingHook && !baitStack.isEmpty())
        {
            fishingHook.fishofthieves$setBaitStack(baitStack.copyWithCount(1));

            if (!player.getAbilities().instabuild)
            {
                baitStack.shrink(1);

                if (baitStack.isEmpty())
                {
                    player.getInventory().removeItem(baitStack);
                }
            }
            else
            {
                fishingHook.fishofthieves$setIsCreative();
            }
        }
        return operation.call(projectile, level, spawnedFrom);
    }
}