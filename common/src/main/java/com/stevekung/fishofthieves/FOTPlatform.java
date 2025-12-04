package com.stevekung.fishofthieves;

import java.util.Set;

import com.stevekung.fishofthieves.entity.shoal.Shoal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class FOTPlatform
{
    @ExpectPlatform
    public static boolean isModLoaded(String modId)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isDevelopment()
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addComposting(ItemLike item, float value)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addFlammableBlock(Block block, int encouragement, int flammability)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static CreativeModeTab.Builder getCreativeTabBuilder()
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static float getGrowthSpeedFromCropBlock(BlockState state, ServerLevel level, BlockPos pos)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerSerializer(String name, EntityDataSerializer<?> serializer)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void sendFishingHookBait(FishingHook fishingHook)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void syncClientShoalFish(Shoal shoal, boolean forcedUpdate)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void requestServerShoalFish(Shoal shoal)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerPoiBlockStates(Holder<PoiType> poi, Set<BlockState> states)
    {
        throw new AssertionError();
    }
}