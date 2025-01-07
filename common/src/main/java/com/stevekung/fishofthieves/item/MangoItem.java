package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MangoItem extends Item
{
    public MangoItem(Properties properties)
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
            return new ItemStack(FOTItems.MANGO_SEED);
        }
        else
        {
            if (livingEntity instanceof Player player && !player.getAbilities().instabuild)
            {
                var itemStack1 = new ItemStack(FOTItems.MANGO_SEED);

                if (!player.getInventory().add(itemStack1))
                {
                    player.drop(itemStack1, false);
                }
            }
            return itemStack;
        }
    }
}