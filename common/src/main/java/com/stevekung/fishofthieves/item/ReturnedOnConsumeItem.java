package com.stevekung.fishofthieves.item;

import java.util.function.Supplier;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Prediction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ReturnedOnConsumeItem extends Item
{
    private final Supplier<Item> returnedItem;
    private final float chance;

    public ReturnedOnConsumeItem(Properties properties, Supplier<Item> returnedItem, float chance)
    {
        super(properties);
        this.returnedItem = returnedItem;
        this.chance = chance;
    }

    public ReturnedOnConsumeItem(Properties properties, Supplier<Item> returnedItem)
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

        if (!level.isClientSide())
        {
            var hasChance = level.getRandom().nextFloat() <= this.chance;

            if (hasChance)
            {
                if (itemStack.isEmpty())
                {
                    return new ItemStack(this.returnedItem.get());
                }
                else
                {
                    if (livingEntity instanceof Player player && !player.getAbilities().instabuild)
                    {
                        var itemStack1 = new ItemStack(this.returnedItem.get());

                        if (!player.getInventory().add(itemStack1))
                        {
                            player.drop(itemStack1, false, Prediction.PREDICTED);
                        }
                    }
                    return itemStack;
                }
            }
        }
        return itemStack;
    }
}