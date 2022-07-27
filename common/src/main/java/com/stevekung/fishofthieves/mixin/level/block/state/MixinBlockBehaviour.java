package com.stevekung.fishofthieves.mixin.level.block.state;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;

@Mixin(BlockBehaviour.class)
public class MixinBlockBehaviour
{
    @Inject(method = "getDrops", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void fishofthieves$addWormDrops(BlockState blockState, LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> info, ResourceLocation lootTableId, LootContext lootContext, ServerLevel serverLevel, LootTable lootTable)
    {
        FOTLootManager.dropWorms(info.getReturnValue(), blockState, lootContext);
    }
}