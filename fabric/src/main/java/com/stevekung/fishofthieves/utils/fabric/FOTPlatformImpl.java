package com.stevekung.fishofthieves.utils.fabric;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTDisplayItems;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.CriteriaAccessor;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class FOTPlatformImpl
{
    public static CreativeModeTab createCreativeTab()
    {
        return FabricItemGroup.builder(new ResourceLocation(FishOfThieves.MOD_ID, "main")).icon(() -> new ItemStack(FOTItems.SPLASHTAIL)).displayItems(FOTDisplayItems::displayItems).build();
    }

    public static void registerCriteriaTriggers(CriterionTrigger<?> trigger)
    {
        CriteriaAccessor.callRegister(trigger);
    }

    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
    {
        return FabricEntityTypeBuilder.create(MobCategory.WATER_AMBIENT, entityFactory).dimensions(dimensions).trackRangeBlocks(4).build();
    }

    public static <T extends Entity> void registerEntityType(String key, EntityType<T> type)
    {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(FishOfThieves.MOD_ID, key), type);
    }

    public static void registerBlock(String key, Block block)
    {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(FishOfThieves.MOD_ID, key), block);
        registerItem(key, new BlockItem(block, new Item.Properties()));
    }

    public static void registerItem(String key, Item item)
    {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, key), item);
    }

    public static void registerSoundEvent(SoundEvent soundEvent)
    {
        Registry.register(BuiltInRegistries.SOUND_EVENT, soundEvent.getLocation().getPath(), soundEvent);
    }
}