package com.stevekung.fishofthieves.utils.fabric;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.CriteriaAccessor;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FOTPlatformImpl
{
    public static EntityType<?> getMobInBucketItem(MobBucketItem bucket)
    {
        return bucket.type;
    }

    public static SoundEvent getEmptySoundInBucketItem(MobBucketItem bucket)
    {
        return bucket.emptySound;
    }

    public static CreativeModeTab createCreativeTab()
    {
        return FabricItemGroupBuilder.build(new ResourceLocation(FishOfThieves.MOD_ID, "main"), () -> new ItemStack(FOTItems.SPLASHTAIL));
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void registerCriteriaTriggers(CriterionTrigger<?> trigger)
    {
        CriteriaAccessor.callRegister(trigger);
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityType.BlockEntitySupplier<? extends T> factory, Block... validBlocks)
    {
        return BlockEntityType.Builder.<T>of(factory, validBlocks).build(null);
    }

    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
    {
        return FabricEntityTypeBuilder.create(MobCategory.WATER_AMBIENT, entityFactory).dimensions(dimensions).trackRangeBlocks(4).build();
    }

    public static <T extends BlockEntity> void registerBlockEntity(String key, BlockEntityType<T> type)
    {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(FishOfThieves.MOD_ID, key), type);
    }

    public static <T extends Entity> void registerEntityType(String key, EntityType<T> type)
    {
        Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(FishOfThieves.MOD_ID, key), type);
    }

    public static void registerBlock(String key, Block block)
    {
        Registry.register(Registry.BLOCK, new ResourceLocation(FishOfThieves.MOD_ID, key), block);
        registerItem(key, new BlockItem(block, new Item.Properties().tab(FishOfThieves.FOT_TAB)));
    }

    public static void registerItem(String key, Item item)
    {
        Registry.register(Registry.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, key), item);
    }

    public static void registerSoundEvent(SoundEvent soundEvent)
    {
        Registry.register(Registry.SOUND_EVENT, soundEvent.getLocation().getPath(), soundEvent);
    }
}