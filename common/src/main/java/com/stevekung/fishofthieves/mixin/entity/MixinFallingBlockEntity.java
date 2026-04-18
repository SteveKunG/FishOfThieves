package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;

@Mixin(FallingBlockEntity.class)
public abstract class MixinFallingBlockEntity extends Entity
{
    @Shadow
    BlockState blockState;

    MixinFallingBlockEntity()
    {
        super(null, null);
    }

    @Inject(method = "causeFallDamage", at = @At(value = "RETURN", ordinal = 2))
    private void fishofthieves$crushPomegranateToRedDye(double fallDistance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> info)
    {
        if (this.blockState.is(BlockTags.ANVIL))
        {
            var crush = false;
            var itemStack = ItemStack.EMPTY;
            var itemEntities = this.level().getEntities(this, this.getBoundingBox(), ItemEntity.class::isInstance).stream().map(ItemEntity.class::cast).filter(itemEntity -> itemEntity.getItem().is(FOTItems.POMEGRANATE)).toList();

            for (var itemEntity : itemEntities)
            {
                var itemStackFromEntity = itemEntity.getItem().copy();
                itemStack = itemStackFromEntity;
                var count = itemStackFromEntity.getCount();
                itemEntity.setItem(new ItemStack(Items.DYE.red(), count));
                crush = true;
            }

            if (crush)
            {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), FOTSoundEvents.CRUSH_POMEGRANATE, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (!itemStack.isEmpty())
                {
                    for (var serverPlayer : this.level().getEntities(EntityTypeTest.forClass(Player.class), new AABB(this.blockPosition()).inflate(8), ServerPlayer.class::isInstance).stream().map(ServerPlayer.class::cast).toList())
                    {
                        FOTCriteriaTriggers.FALLING_ANVIL_CRUSH_ITEM.trigger(serverPlayer, itemStack);
                    }
                }
            }
        }
    }
}