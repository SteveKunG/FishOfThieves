package com.stevekung.fishofthieves.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

@Mixin(RecordItem.class)
public class MixinRecordItem
{
    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/player/Player.awardStat(Lnet/minecraft/resources/ResourceLocation;)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void fishofthieves$triggerCustomAdvancement(UseOnContext context, CallbackInfoReturnable<InteractionResult> info, Level level, BlockPos blockPos, BlockState blockState, ItemStack itemStack, Player player)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            var list = level.getEntitiesOfClass(Entity.class, new AABB(blockPos).inflate(8.0D));

            for (var entity : list)
            {
                FOTCriteriaTriggers.ITEM_USED_ON_BLOCK_WITH_NEARBY_ENTITY.trigger(serverPlayer, blockPos, itemStack, entity);
            }
        }
    }
}