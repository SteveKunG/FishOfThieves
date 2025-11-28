package com.stevekung.fishofthieves;

import java.util.Set;

import com.stevekung.fishofthieves.entity.shoal.Shoal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityType.BlockEntitySupplier<? extends T> factory, Block... validBlocks)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, MobCategory mobCategory, EntityDimensions dimensions, int clientTrackingRange)
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
    public static <T extends GameRules.Value<T>> GameRules.Key<T> registerGameRule(String name, GameRules.Category category, GameRules.Type<T> type)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static GameRules.Type<GameRules.BooleanValue> getGameRuleBoolean(boolean defaultValue)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void sendFishingHookBait(Player player, int entityId, ItemStack itemStack)
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