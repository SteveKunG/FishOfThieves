package com.stevekung.fishofthieves.neoforge;

import com.stevekung.fishofthieves.neoforge.mixin.accessor.CropBlockAccessor;
import com.stevekung.fishofthieves.network.ReceiveFishingHookBaitPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;

public class FOTPlatformImpl
{
    public static boolean isModLoaded(String modId)
    {
        return ModList.get().isLoaded(modId);
    }

    public static void addComposting(ItemLike item, float value)
    {
        CompostableList.COMPOSTABLES.put(item, value);
    }

    public static void addFlammableBlock(Block block, int encouragement, int flammability)
    {
        var fireBlock = (FireBlock) Blocks.FIRE;
        fireBlock.setFlammable(block, encouragement, flammability);
    }

    public static float getGrowthSpeedFromCropBlock(BlockState state, ServerLevel level, BlockPos pos)
    {
        return CropBlockAccessor.callGetGrowthSpeed(state, level, pos);
    }

    public static void registerSerializer(String name, EntityDataSerializer<?> serializer)
    {
        FishOfThievesNeoForge.ENTITY_DATA_SERIALIZERS.register(name, () -> serializer);
    }

    public static void sendFishingHookBait(Player player, int entityId, ItemStack itemStack)
    {
        if (player.level() instanceof ServerLevel serverLevel)
        {
            for (var serverPlayer : serverLevel.getPlayers(serverPlayer -> serverPlayer.isAlive() && serverPlayer.distanceTo(player.fishing) < 256.0F))
            {
                serverPlayer.connection.send(new ClientboundCustomPayloadPacket(new ReceiveFishingHookBaitPacket(entityId, itemStack)));
            }
        }
    }
}