package com.stevekung.fishofthieves.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FOTPlatformImpl
{
    public static boolean isModLoaded(String modId)
    {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityType.BlockEntitySupplier<? extends T> factory, Block... validBlocks)
    {
        return BlockEntityType.Builder.<T>of(factory, validBlocks).build(null);
    }

    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
    {
        return EntityType.Builder.of(entityFactory, MobCategory.WATER_AMBIENT).sized(dimensions.width(), dimensions.height()).clientTrackingRange(4).build();
    }
}