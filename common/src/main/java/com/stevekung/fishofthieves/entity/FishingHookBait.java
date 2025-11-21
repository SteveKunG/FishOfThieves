package com.stevekung.fishofthieves.entity;

import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface FishingHookBait
{
    static ItemStack getBait(Player player)
    {
        var itemStack = getHeldBait(player);

        if (!itemStack.isEmpty())
        {
            return itemStack;
        }
        else
        {
            for (var index = 0; index < player.getInventory().getContainerSize(); index++)
            {
                var invStack = player.getInventory().getItem(index);

                if (invStack.is(FOTTags.Items.WORMS))
                {
                    return invStack;
                }
            }
            return ItemStack.EMPTY;
        }
    }

    static ItemStack getHeldBait(Player player)
    {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(FOTTags.Items.WORMS))
        {
            return player.getItemInHand(InteractionHand.OFF_HAND);
        }
        else
        {
            return player.getItemInHand(InteractionHand.MAIN_HAND).is(FOTTags.Items.WORMS) ? player.getItemInHand(InteractionHand.MAIN_HAND) : ItemStack.EMPTY;
        }
    }

    default void fishofthieves$setBaitStack(ItemStack itemStack)
    {
        throw new AssertionError("Implemented via mixin");
    }

    default ItemStack fishofthieves$getBaitStack()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void fishofthieves$setIsCreative()
    {
        throw new AssertionError("Implemented via mixin");
    }
}