package com.stevekung.fishofthieves.mixin.entity;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.entity.PartyFish;
import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements PartyFish
{
    @Unique
    private boolean dancing;

    @Nullable
    @Unique
    private BlockPos jukeboxPos;

    MixinLivingEntity()
    {
        super(null, null);
    }

    @Inject(method = "dropFromLootTable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;ZLnet/minecraft/resources/ResourceKey;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "net/minecraft/world/level/storage/loot/LootTable.getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;JLjava/util/function/Consumer;)V"))
    private void fishofthieves$dropFishBone(ServerLevel serverLevel, DamageSource damageSource, boolean hitByPlayer, ResourceKey<LootTable> resourceKey, Consumer<ItemStack> consumer, CallbackInfo info, @Local LootParams.Builder builder)
    {
        if (this.getType().is(FOTTags.EntityTypes.FISH_BONE_DROP))
        {
            var fishBoneDropLootTable = serverLevel.getServer().reloadableRegistries().getLootTable(FOTLootTables.Entities.FISH_BONE_DROP);
            fishBoneDropLootTable.getRandomItems(builder.create(LootContextParamSets.ENTITY), itemStack -> this.spawnAtLocation(serverLevel, itemStack));
        }
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void fishofthieves$checkNearbyJukebox(CallbackInfo info)
    {
        if (this.jukeboxPos == null || !this.jukeboxPos.closerToCenterThan(this.position(), GameEvent.JUKEBOX_PLAY.value().notificationRadius()) || !this.level().getBlockState(this.jukeboxPos).is(Blocks.JUKEBOX))
        {
            this.dancing = false;
            this.jukeboxPos = null;
        }
    }

    @Inject(method = "setRecordPlayingNearby", at = @At("HEAD"))
    private void fishofthieves$setRecordPlayingNearby(BlockPos jukeboxPos, boolean jukeboxPlaying, CallbackInfo info)
    {
        this.dancing = jukeboxPlaying;
        this.jukeboxPos = jukeboxPos;
    }

    @Override
    public boolean fishofthieves$isDancing()
    {
        return this.dancing;
    }
}