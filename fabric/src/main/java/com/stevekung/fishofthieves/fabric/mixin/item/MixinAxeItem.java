//package com.stevekung.fishofthieves.fabric.mixin.item;
//
//import java.util.Optional;
//
//import org.jspecify.annotations.Nullable;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import com.stevekung.fishofthieves.fabric.CustomStrippables;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.AxeItem;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.state.BlockState;
//
//@Mixin(AxeItem.class)
//public class MixinAxeItem TODO
//{
//    @Inject(method = "evaluateNewBlockState", cancellable = true, at = @At("HEAD"))
//    private void fishofthieves$stripNonFullCoconutLog(Level level, BlockPos pos, @Nullable Player player, BlockState state, CallbackInfoReturnable<Optional<BlockState>> info)
//    {
//        var block = state.getBlock();
//
//        if (CustomStrippables.CUSTOM_STRIPPABLES.containsKey(block))
//        {
//            level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
//            info.setReturnValue(Optional.of(CustomStrippables.CUSTOM_STRIPPABLES.get(block).withPropertiesOf(state)));
//        }
//    }
//}