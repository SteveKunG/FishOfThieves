package com.stevekung.fishofthieves.utils.forge;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.forge.FishOfThievesForge;
import com.stevekung.fishofthieves.forge.mixin.MobBucketItemAccessor;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.fml.ModList;

public class FOTPlatformImpl
{
    public static boolean isModLoaded(String modId)
    {
        return ModList.get().isLoaded(modId);
    }

    public static EntityType<?> getMobInBucketItem(MobBucketItem bucket)
    {
        return ((MobBucketItemAccessor) bucket).invokeGetFishType();
    }

    public static SoundEvent getEmptySoundInBucketItem(MobBucketItem bucket)
    {
        return ((MobBucketItemAccessor) bucket).invokeGetEmptySound();
    }

    public static CreativeModeTab createCreativeTab()
    {
        return new CreativeModeTab("fishofthieves.main")
        {
            @Override
            public ItemStack makeIcon()
            {
                return new ItemStack(FOTItems.SPLASHTAIL);
            }
        };
    }

    public static void registerCriteriaTriggers(CriterionTrigger<?> trigger)
    {
        CriteriaTriggers.register(trigger);
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityType.BlockEntitySupplier<? extends T> factory, Block... validBlocks)
    {
        return BlockEntityType.Builder.<T>of(factory, validBlocks).build(null);
    }

    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
    {
        return EntityType.Builder.of(entityFactory, MobCategory.WATER_AMBIENT).sized(dimensions.width, dimensions.height).clientTrackingRange(4).build("");
    }

    public static <T extends BlockEntity> void registerBlockEntity(String key, BlockEntityType<T> type)
    {
        FishOfThievesForge.BLOCK_ENTITY_TYPE.register(key, () -> type);
    }

    public static <T extends Entity> void registerEntityType(String key, EntityType<T> type)
    {
        FishOfThievesForge.ENTITY.register(key, () -> type);
    }

    public static void registerBlock(String key, Block block)
    {
        FishOfThievesForge.BLOCK.register(key, () -> block);
        registerItem(key, new BlockItem(block, new Item.Properties().tab(FishOfThieves.FOT_TAB)));
    }

    public static void registerItem(String key, Item item)
    {
        FishOfThievesForge.ITEM.register(key, () -> item);
    }

    public static void registerSoundEvent(SoundEvent soundEvent)
    {
        FishOfThievesForge.SOUND_EVENTS.register(soundEvent.getLocation().getPath(), () -> soundEvent);
    }

    public static void registerFeature(String key, Feature<?> feature)
    {
        FishOfThievesForge.FEATURES.register(key, () -> feature);
    }
}