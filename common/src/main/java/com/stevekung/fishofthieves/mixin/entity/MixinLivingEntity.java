package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity
{
    @Shadow
    abstract LootContext.Builder createLootContext(boolean hitByPlayer, DamageSource damageSource);

    MixinLivingEntity()
    {
        super(null, null);
    }

    @Inject(method = "dropFromLootTable", at = @At("TAIL"))
    private void fishofthieves$dropFishBone(DamageSource damageSource, boolean hitByPlayer, CallbackInfo info)
    {
        if (this.getType().is(FOTTags.EntityTypes.FISH_BONE_DROP))
        {
            var lootTable = this.level.getServer().getLootTables().get(FOTLootTables.Entities.FISH_BONE_DROP);
            var builder = this.createLootContext(hitByPlayer, damageSource);
            lootTable.getRandomItems(builder.create(LootContextParamSets.ENTITY), this::spawnAtLocation);
        }
    }
}