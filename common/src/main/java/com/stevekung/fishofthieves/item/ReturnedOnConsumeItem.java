package com.stevekung.fishofthieves.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class ReturnedOnConsumeItem extends Item
{
    public ReturnedOnConsumeItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity)
    {
        super.finishUsingItem(itemStack, level, livingEntity);

        if (livingEntity instanceof ServerPlayer serverPlayer)
        {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (itemStack.isEmpty())
        {
            return new ItemStack(this.getReturnedItem());
        }
        else
        {
            if (livingEntity instanceof Player player && !player.getAbilities().instabuild)
            {
                var itemStack1 = new ItemStack(this.getReturnedItem());

                if (!player.getInventory().add(itemStack1))
                {
                    player.drop(itemStack1, false);
                }
            }
            return itemStack;
        }
    }

    protected abstract Item getReturnedItem();
}