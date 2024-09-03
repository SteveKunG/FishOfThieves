package com.stevekung.fishofthieves.mixin.level.block.state;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

@Mixin(BlockBehaviour.class)
public class MixinBlockBehaviour
{
    @Inject(method = "getDrops", at = @At("TAIL"))
    private void fishofthieves$addWormDrops(BlockState blockState, LootParams.Builder builder, CallbackInfoReturnable<List<ItemStack>> info, @Local LootParams lootParams, @Local ServerLevel serverLevel)
    {
        FOTLootManager.dropWorms(info.getReturnValue(), blockState, serverLevel.getServer().getLootData(), lootParams);
    }
}