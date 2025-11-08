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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

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

    @Override
    public boolean save(CompoundTag compound)
    {
        if (this.level() instanceof ServerLevel serverLevel)
        {
            var baitPreserveSavedData = serverLevel.getBaitPreserve();
            baitPreserveSavedData.getBaitStorage().putIfAbsent(this.position(), this.baitStack);
            baitPreserveSavedData.setDirty();
        }
        return super.save(compound);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/projectile/FishingHook.discard()V"))
    private void fishofthieves$dropBaitOnDiscardTick(CallbackInfo info)
    {
        this.dropBait();
    }

    @Inject(method = "shouldStopFishing", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/projectile/FishingHook.discard()V"))
    private void fishofthieves$dropBaitOnDiscardRemoved(Player player, CallbackInfoReturnable<Boolean> info)
    {
        this.dropBait();
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

    @Unique
    private void dropBait()
    {
        if (this.level() instanceof ServerLevel serverLevel)
        {
            var vec3 = Vec3.atLowerCornerWithOffset(this.blockPosition(), 0.5, 0.25, 0.5).offsetRandom(this.random, 0.3F);
            var itemEntity = new ItemEntity(this.level(), vec3.x(), vec3.y(), vec3.z(), this.baitStack);
            itemEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(itemEntity);

            var baitPreserveSavedData = serverLevel.getBaitPreserve();
            baitPreserveSavedData.getBaitStorage().remove(this.position());
            baitPreserveSavedData.setDirty();
        }
    }
}