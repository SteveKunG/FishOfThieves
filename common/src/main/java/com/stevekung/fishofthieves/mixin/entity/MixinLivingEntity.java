package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity
{
    MixinLivingEntity()
    {
        super(null, null);
    }

    @Inject(method = "dropFromLootTable", at = @At("TAIL"))
    private void fishofthieves$dropFishBone(DamageSource damageSource, boolean hitByPlayer, CallbackInfo info, @Local LootParams.Builder builder)
    {
        if (this.getType().is(FOTTags.EntityTypes.FISH_BONE_DROP))
        {
            var fishBoneDropLootTable = this.level().getServer().getLootData().getLootTable(FOTLootTables.Entities.FISH_BONE_DROP);
            fishBoneDropLootTable.getRandomItems(builder.create(LootContextParamSets.ENTITY), this::spawnAtLocation);
        }
    }
}