package com.stevekung.fishofthieves.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ReturnedOnConsumeItem extends Item
{
    private final Item returnedItem;
    private final float chance;

    public ReturnedOnConsumeItem(Properties properties, Item returnedItem, float chance)
    {
        super(properties);
        this.returnedItem = returnedItem;
        this.chance = chance;
    }

    public ReturnedOnConsumeItem(Properties properties, Item returnedItem)
    {
        this(properties, returnedItem, 1.0f);
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

        var hasChance = level.getRandom().nextFloat() <= this.chance;

        if (itemStack.isEmpty())
        {
            return hasChance ? new ItemStack(this.returnedItem) : itemStack;
        }
        else
        {
            if (livingEntity instanceof Player player && !player.getAbilities().instabuild)
            {
                if (hasChance)
                {
                    var itemStack1 = new ItemStack(this.returnedItem);

                    if (!player.getInventory().add(itemStack1))
                    {
                        player.drop(itemStack1, false);
                    }
                }
            }
            return itemStack;
        }
    }
}