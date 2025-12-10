package com.stevekung.fishofthieves.neoforge;

import java.util.Set;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.neoforge.mixin.accessor.CropBlockAccessor;
import com.stevekung.fishofthieves.network.ReceiveFishingHookBaitPacket;
import com.stevekung.fishofthieves.network.RequestServerShoalFishPacket;
import com.stevekung.fishofthieves.network.SyncClientShoalFishPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.GameRules;
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

    public static CreativeModeTab.Builder getCreativeTabBuilder()
    {
        return CreativeModeTab.builder();
    }

    public static <T extends GameRules.Value<T>> GameRules.Key<T> registerGameRule(String name, GameRules.Category category, GameRules.Type<T> type)
    {
        return GameRules.register(FishOfThieves.MOD_RESOURCES + name, category, type);
    }

    public static GameRules.Type<GameRules.BooleanValue> getGameRuleBoolean(boolean defaultValue)
    {
        return GameRules.BooleanValue.create(defaultValue);
    }

    public static void sendFishingHookBait(FishingHook fishingHook)
    {
        if (fishingHook.level() instanceof ServerLevel serverLevel)
        {
            for (var serverPlayer : serverLevel.getPlayers(serverPlayer -> serverPlayer.isAlive() && serverPlayer.distanceTo(fishingHook) < 1024f))
            {
                serverPlayer.connection.send(new ClientboundCustomPayloadPacket(new ReceiveFishingHookBaitPacket(fishingHook.getId(), fishingHook.fishofthieves$getBaitStack())));
            }
        }
    }

    public static void syncClientShoalFish(Shoal shoal, boolean forcedUpdate)
    {
        if (shoal.level() instanceof ServerLevel serverLevel)
        {
            for (var serverPlayer : serverLevel.getPlayers(serverPlayer -> serverPlayer.isAlive() && serverPlayer.distanceTo(shoal) < 1024f))
            {
                serverPlayer.connection.send(new ClientboundCustomPayloadPacket(new SyncClientShoalFishPacket(shoal.getId(), shoal.getShoalFish(), forcedUpdate)));
            }
        }
    }

    public static void requestServerShoalFish(Shoal shoal)
    {
        var connection = Minecraft.getInstance().getConnection();

        if (connection != null)
        {
            connection.send(new ServerboundCustomPayloadPacket(new RequestServerShoalFishPacket(shoal.getId())));
        }
    }

    public static void registerPoiBlockStates(Holder<PoiType> poi, Set<BlockState> states)
    {
        // no-op
    }
}