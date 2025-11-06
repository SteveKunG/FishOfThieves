package com.stevekung.fishofthieves.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.entity.FishingHookBait;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.level.Level;

@Mixin(FishingRodItem.class)
public class MixinFishingRodItem
{
    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "net/minecraft/world/level/Level.addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean fishofthieves$setBaitOnCast(Level level, Entity entity, Operation<Boolean> operation, @Local(argsOnly = true) Player player)
    {
        var baitStack = FishingHookBait.getBait(player);

        if (entity instanceof FishingHook fishingHook && !baitStack.isEmpty())
        {
            fishingHook.fishofthieves$setBaitStack(baitStack);
        }
        return operation.call(level, entity);
    }
}