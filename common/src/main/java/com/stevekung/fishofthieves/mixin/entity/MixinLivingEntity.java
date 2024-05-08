package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity
{
    MixinLivingEntity()
    {
        super(null, null);
    }

    @Inject(method = "dropFromLootTable", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void fishofthieves$dropFishBone(DamageSource damageSource, boolean hitByPlayer, CallbackInfo info, ResourceLocation resourceLocation, LootTable lootTable, LootParams.Builder builder, LootParams lootParams)
    {
        if (this.getType().is(FOTTags.EntityTypes.FISH_BONE_DROP))
        {
            var fishBoneDropLootTable = this.level().getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, FOTLootTables.Entities.FISH_BONE_DROP));
            fishBoneDropLootTable.getRandomItems(builder.create(LootContextParamSets.ENTITY), this::spawnAtLocation);
        }
    }
}