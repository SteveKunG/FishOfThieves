package com.stevekung.fishofthieves.common.mixin.animal;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.common.registry.FOTTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Mixin(Axolotl.class)
public class MixinAxolotl
{
    @Inject(method = "usePlayerItem", at = @At("HEAD"))
    private void fishofthieves$fixReturnBucketItem(Player player, InteractionHand interactionHand, ItemStack itemStack, CallbackInfo info)
    {
        if (itemStack.is(FOTTags.Items.THIEVES_FISH_BUCKET) && !player.getAbilities().instabuild)
        {
            player.setItemInHand(interactionHand, new ItemStack(Items.WATER_BUCKET));
        }
    }
}