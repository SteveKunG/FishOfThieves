package com.stevekung.fishofthieves.fabric.mixin.client;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.nbt.Tag;
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
    EntityType<?> type;

    @Inject(method = "appendHoverText", at = @At("TAIL"))
    private void fishofthieves$appendCustomFishTooltip(ItemStack itemStack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced, CallbackInfo info)
    {
        var compoundTag = itemStack.getTag();

        if (this.type.is(FOTTags.THIEVES_FISH) && itemStack.hasTag() && compoundTag.contains(ThievesFish.VARIANT_TAG, Tag.TAG_INT))
        {
            var type = new TranslatableComponent("entity.fishofthieves.%s.%s".formatted(Registry.ENTITY_TYPE.getKey(this.type).getPath(), compoundTag.getString(ThievesFish.NAME_TAG))).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY);

            if (compoundTag.getBoolean(ThievesFish.TROPHY_TAG))
            {
                type.append(", ").append(new TranslatableComponent("entity.fishofthieves.trophy"));
            }
            tooltipComponents.add(type);
        }
    }
}