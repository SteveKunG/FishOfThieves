package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.entity.FishingHookBait;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;

@Mixin(FishingHook.class)
public abstract class MixinFishingHook extends Projectile implements FishingHookBait
{
    @Unique
    private ItemStack baitStack = ItemStack.EMPTY;

    @Shadow
    abstract Player getPlayerOwner();

    MixinFishingHook()
    {
        super(null, null);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void fishofthieves$sendPacketOnFirstTick(CallbackInfo info)
    {
        if (this.level() instanceof ServerLevel && this.tickCount == 1)
        {
            FOTPlatform.sendFishingHookBait(this.getPlayerOwner(), this.getId(), this.baitStack);
        }
    }

    @Inject(method = "retrieve", at = @At(
            value = "INVOKE",
            target = "net/minecraft/advancements/critereon/FishingRodHookedTrigger.trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/projectile/FishingHook;Ljava/util/Collection;)V",
            shift = At.Shift.AFTER,
            ordinal = 1))
    private void fishofthieves$onRetrieve(ItemStack stack, CallbackInfoReturnable<Integer> info)
    {
        this.shrinkBait(false);
    }

    @Inject(method = "catchingFish", at = @At(
            value = "INVOKE",
            target = "net/minecraft/network/syncher/SynchedEntityData.set(Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)V",
            ordinal = 0))
    private void fishofthieves$shrinkBait(BlockPos pos, CallbackInfo info)
    {
        this.shrinkBait(true);
    }

    @Override
    public void fishofthieves$setBaitStack(ItemStack itemStack)
    {
        this.baitStack = itemStack;
    }

    @Override
    public ItemStack fishofthieves$getBaitStack()
    {
        return this.baitStack;
    }

    @Unique
    private void shrinkBait(boolean sendPacket)
    {
        var player = this.getPlayerOwner();

        if (player != null && !this.baitStack.isEmpty() && !player.getAbilities().instabuild)
        {
            this.baitStack.shrink(1);
            player.awardStat(Stats.ITEM_USED.get(this.baitStack.getItem()));

            if (sendPacket)
            {
                FOTPlatform.sendFishingHookBait(player, this.getId(), this.baitStack);
            }
        }
    }
}