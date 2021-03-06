package com.stevekung.fishofthieves.utils.fabric;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.CriteriaAccessor;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;

public class FOTPlatformImpl
{
    public static CreativeModeTab createCreativeTab()
    {
        return FabricItemGroupBuilder.build(new ResourceLocation(FishOfThieves.MOD_ID, "main"), () -> new ItemStack(FOTItems.SPLASHTAIL_BUCKET));
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
        Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(FishOfThieves.MOD_ID, key), type);
    }

    public static void registerItem(String key, Item item)
    {
        Registry.register(Registry.ITEM, new ResourceLocation(FishOfThieves.MOD_ID, key), item);
    }

    public static void registerSoundEvent(SoundEvent soundEvent)
    {
        Registry.register(Registry.SOUND_EVENT, soundEvent.getLocation().getPath(), soundEvent);
    }

    public static <T extends Mob> void registerSpawnPlacements(EntityType<T> type, SpawnPlacements.Type location, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> spawnPredicate)
    {
        SpawnRestrictionAccessor.callRegister(type, location, heightmap, spawnPredicate);
    }
}