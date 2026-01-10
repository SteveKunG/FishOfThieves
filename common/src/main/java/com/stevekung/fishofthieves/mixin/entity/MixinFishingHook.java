package com.stevekung.fishofthieves.mixin.entity;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.entity.FishingHookBait;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

@Mixin(FishingHook.class)
public abstract class MixinFishingHook extends Projectile implements FishingHookBait
{
    @Shadow
    int timeUntilLured;

    @Unique
    private ItemStack baitStack = ItemStack.EMPTY;

    @Unique
    private boolean isCreative;

    @Unique
    private boolean saved;

    @Shadow
    abstract Player getPlayerOwner();

    MixinFishingHook()
    {
        super(null, null);
    }

    @Override
    public boolean save(ValueOutput output)
    {
        if (!this.isCreative && !this.saved && this.level() instanceof ServerLevel serverLevel)
        {
            var baitPreserveSavedData = serverLevel.getBaitPreserve();
            baitPreserveSavedData.getBaitStorage().putIfAbsent(this.position(), this.baitStack);
            baitPreserveSavedData.setDirty();
            this.saved = true;
        }
        return super.save(output);
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
            FOTPlatform.sendFishingHookBait(FishingHook.class.cast(this));
        }
    }

    @Inject(method = "retrieve", at = @At(
            value = "INVOKE",
            target = "net/minecraft/advancements/criterion/FishingRodHookedTrigger.trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/projectile/FishingHook;Ljava/util/Collection;)V",
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

    @Inject(
            method = "catchingFish",
            at = @At(
                    value = "FIELD",
                    target = "net/minecraft/world/entity/projectile/FishingHook.timeUntilLured:I",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 1,
                    shift = At.Shift.AFTER),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "net/minecraft/util/Mth.nextInt(Lnet/minecraft/util/RandomSource;II)I",
                            ordinal = 1))
    )
    private void fishofthieves$increaseLureByBait(BlockPos pos, CallbackInfo info)
    {
        if (this.baitStack.is(FOTTags.Items.WORMS))
        {
            // Increase lure speed by 10%
            this.timeUntilLured = Mth.floor(this.timeUntilLured * 0.9);
        }
    }

    @Inject(method = "retrieve", cancellable = true, at = @At(
            value = "INVOKE",
            target = "net/minecraft/advancements/criterion/FishingRodHookedTrigger.trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/projectile/FishingHook;Ljava/util/Collection;)V",
            shift = At.Shift.AFTER,
            ordinal = 1))
    private void fishofthieves$fishUpShoal(ItemStack itemStack, CallbackInfoReturnable<Integer> info, @Local Player player)
    {
        var shoals = this.level().getEntitiesOfClass(Shoal.class, this.getBoundingBox().inflate(1), Entity::isAlive);

        if (!shoals.isEmpty())
        {
            var shoal = shoals.getFirst();
            var intersects = shoal.getBoundingBox().intersects(this.getBoundingBox().inflate(1d));

            if (intersects)
            {
                var randomFish = shoal.getRandomFishInShoal();

                if (randomFish == null)
                {
                    return;
                }
                else
                {
                    shoal.addParticipatePlayer(player.getUUID());
                }

                var dx = player.getX() - this.getX();
                var dy = player.getY() - this.getY();
                var dz = player.getZ() - this.getZ();
                var power = 0.15;
                var gravity = 0.12;
                randomFish.snapTo(this.blockPosition(), -player.getYRot(), -player.getXRot());
                randomFish.setDeltaMovement(dx * power, dy * power + Math.sqrt(Math.sqrt(dx * dx + dy * dy + dz * dz)) * gravity, dz * power);
                randomFish.setAirSupply(500);
                this.level().addFreshEntity(randomFish);

                player.level().addFreshEntity(new ExperienceOrb(player.level(), player.getX() + 0.5, player.getY() + 0.5, player.getZ() + 0.5, this.random.nextInt(8) + 2));
                this.discard();
                info.setReturnValue(4);
            }
        }
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

    @Override
    public void fishofthieves$setIsCreative()
    {
        this.isCreative = true;
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
                FOTPlatform.sendFishingHookBait(FishingHook.class.cast(this));
            }
        }
    }

    @Unique
    private void dropBait()
    {
        if (!this.isCreative && this.level() instanceof ServerLevel serverLevel)
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