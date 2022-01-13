package com.stevekung.fishofthieves.mixin;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.fishofthieves.FOTEntities;
import com.stevekung.fishofthieves.entity.Splashtail;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

@Mixin(MobBucketItem.class)
public class MixinMobBucketItem
{
    @Shadow
    @Final
    private EntityType<?> type;

    @Inject(method = "appendHoverText", at = @At("TAIL"))
    private void appendCustomFishType(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced, CallbackInfo info)
    {
        var compoundTag = stack.getTag();

        if (this.type == FOTEntities.SPLASHTAIL && stack.hasTag() && compoundTag.contains(Splashtail.VARIANT_TAG, 3))
        {
            var i = compoundTag.getInt(Splashtail.VARIANT_TAG);
            tooltipComponents.add(new TranslatableComponent("entity.fishofthieves.splashtail." + Splashtail.Variant.BY_ID[i].getName()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        }
    }
}