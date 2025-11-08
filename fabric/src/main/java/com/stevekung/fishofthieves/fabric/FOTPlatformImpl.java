package com.stevekung.fishofthieves.fabric;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.network.ReceiveFishingHookBaitPacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FOTPlatformImpl
{
    public static boolean isModLoaded(String modId)
    {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static void addComposting(ItemLike item, float value)
    {
        CompostingChanceRegistry.INSTANCE.add(item, value);
    }

    public static void addFlammableBlock(Block block, int encouragement, int flammability)
    {
        FlammableBlockRegistry.getDefaultInstance().add(block, encouragement, flammability);
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityType.BlockEntitySupplier<? extends T> factory, Block... validBlocks)
    {
        return BlockEntityType.Builder.<T>of(factory, validBlocks).build(null);
    }

    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
    {
        return EntityType.Builder.of(entityFactory, MobCategory.WATER_AMBIENT).sized(dimensions.width(), dimensions.height()).clientTrackingRange(4).build();
    }

    public static float getGrowthSpeedFromCropBlock(BlockState state, ServerLevel level, BlockPos pos)
    {
        return CropBlock.getGrowthSpeed(state.getBlock(), level, pos);
    }

    public static void registerSerializer(String name, EntityDataSerializer<?> serializer)
    {
        EntityDataSerializers.registerSerializer(serializer);
    }

    public static void sendFishingHookBait(Player player, int entityId, ItemStack itemStack)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            if (ServerPlayNetworking.canSend(serverPlayer, FishOfThieves.RECEIVE_FISHING_HOOK_BAIT))
            {
                ServerPlayNetworking.send(serverPlayer, new ReceiveFishingHookBaitPacket(entityId, itemStack));
            }
        }
    }
}