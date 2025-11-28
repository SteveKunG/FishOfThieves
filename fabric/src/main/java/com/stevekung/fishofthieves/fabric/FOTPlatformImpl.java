package com.stevekung.fishofthieves.fabric;

import java.util.Set;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.network.ReceiveFishingHookBaitPacket;
import com.stevekung.fishofthieves.network.RequestServerShoalFishPacket;
import com.stevekung.fishofthieves.network.SyncClientShoalFishPacket;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FOTPlatformImpl
{
    public static boolean isModLoaded(String modId)
    {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static boolean isDevelopment()
    {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public static void addComposting(ItemLike item, float value)
    {
        CompostingChanceRegistry.INSTANCE.add(item, value);
    }

    public static void addFlammableBlock(Block block, int encouragement, int flammability)
    {
        FlammableBlockRegistry.getDefaultInstance().add(block, encouragement, flammability);
    }

    public static float getGrowthSpeedFromCropBlock(BlockState state, ServerLevel level, BlockPos pos)
    {
        return CropBlock.getGrowthSpeed(state.getBlock(), level, pos);
    }

    public static void registerSerializer(String name, EntityDataSerializer<?> serializer)
    {
        FabricTrackedDataRegistry.register(FishOfThieves.id(name), serializer);
    }

    public static <T extends GameRules.Value<T>> GameRules.Key<T> registerGameRule(String name, GameRules.Category category, GameRules.Type<T> type)
    {
        return GameRuleRegistry.register(FishOfThieves.MOD_RESOURCES + name, category, type);
    }

    public static GameRules.Type<GameRules.BooleanValue> getGameRuleBoolean(boolean defaultValue)
    {
        return GameRuleFactory.createBooleanRule(defaultValue);
    }

    public static void sendFishingHookBait(FishingHook fishingHook)
    {
        for (var serverPlayer : PlayerLookup.tracking(fishingHook))
        {
            if (ServerPlayNetworking.canSend(serverPlayer, FishOfThieves.RECEIVE_FISHING_HOOK_BAIT))
            {
                ServerPlayNetworking.send(serverPlayer, new ReceiveFishingHookBaitPacket(fishingHook.getId(), fishingHook.fishofthieves$getBaitStack()));
            }
        }
    }

    public static void syncClientShoalFish(Shoal shoal, boolean forcedUpdate)
    {
        if (shoal.getShoalFish().isEmpty())
        {
            return;
        }

        for (var serverPlayer : PlayerLookup.tracking(shoal))
        {
            if (ServerPlayNetworking.canSend(serverPlayer, FishOfThieves.SYNC_CLIENT_SHOAL_FISH))
            {
                ServerPlayNetworking.send(serverPlayer, new SyncClientShoalFishPacket(shoal.getId(), shoal.getShoalFish(), forcedUpdate));
            }
        }
    }

    public static void requestServerShoalFish(Shoal shoal)
    {
        if (ClientPlayNetworking.canSend(FishOfThieves.REQUEST_SERVER_SHOAL_FISH))
        {
            ClientPlayNetworking.send(new RequestServerShoalFishPacket(shoal.getId()));
        }
    }

    public static void registerPoiBlockStates(Holder<PoiType> poi, Set<BlockState> states)
    {
        PoiTypes.registerBlockStates(poi, states);
    }
}