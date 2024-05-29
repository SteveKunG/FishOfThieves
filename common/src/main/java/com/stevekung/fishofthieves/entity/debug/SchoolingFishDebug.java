package com.stevekung.fishofthieves.entity.debug;

import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class SchoolingFishDebug
{
    public static final boolean ENABLE = false;

    public static void tick(AbstractSchoolingThievesFish<?> fish)
    {
        if (!ENABLE)
        {
            return;
        }

        if (!fish.level().isClientSide())
        {
            var text = "";

            if (fish.hasFollowers())
            {
                text += "leader: " + color(fish.hasFollowers()) + " ";
                text += ChatFormatting.RESET;
            }
            if (fish.isFollower())
            {
                text += "follower: " + color(fish.isFollower()) + " ";
                text += ChatFormatting.RESET;
            }

            text += "schoolSize: " + ChatFormatting.GOLD + fish.getSchoolSize() + " ";
            text += ChatFormatting.RESET;
            text += "uuid: " + fish.getUUID().toString().split("-")[0];

            if (fish.hasLeader())
            {
                text += " leader: " + ChatFormatting.GOLD + fish.getLeader().getUUID().toString().split("-")[0];
                text += ChatFormatting.RESET;
            }

            fish.setCustomNameVisible(true);
            fish.setCustomName(Component.literal(text));
        }
    }

    private static String color(boolean isTrue)
    {
        var color = isTrue ? ChatFormatting.GREEN : ChatFormatting.RED;
        return color.toString() + isTrue;
    }
}