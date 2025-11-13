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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.entity.FishingHookBait;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

@Mixin(FishingHook.class)
public abstract class MixinFishingHook extends Projectile implements FishingHookBait
{
    @Shadow
    int timeUntilLured;

    @Unique
    private ItemStack baitStack = ItemStack.EMPTY;

    @Unique
    private boolean isCreative;

    @Shadow
    abstract Player getPlayerOwner();

    MixinFishingHook()
    {
        super(null, null);
    }

    @Override
    public boolean save(CompoundTag compound)
    {
        if (!this.isCreative && this.level() instanceof ServerLevel serverLevel)
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

    @WrapOperation(method = "retrieve", at = @At(value = "INVOKE", target = "net/minecraft/world/level/storage/loot/LootTable.getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;"))
    private ObjectArrayList<ItemStack> test(LootTable lootTable, LootParams lootParams, Operation<ObjectArrayList<ItemStack>> operation)
    {
        var lsit = this.level().getEntities(EntityTypeTest.forClass(Interaction.class), this.getBoundingBox().inflate(0.5d), interaction -> true);

        if (!lsit.isEmpty())
        {
            var inter = lsit.get(0);
            var test = inter.getBoundingBox().intersects(this.getBoundingBox().inflate(1d));
            System.out.println(inter);
            System.out.println(test);

            if (test)
            {
                var lootTable2 = this.level().getServer().getLootData().getLootTable(BuiltInLootTables.END_CITY_TREASURE);
                return lootTable2.getRandomItems(lootParams);
            }
        }
        return operation.call(lootTable, lootParams);
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
                FOTPlatform.sendFishingHookBait(player, this.getId(), this.baitStack);
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