package com.stevekung.fishofthieves.fabric;

import com.chocohead.mm.api.ClassTinkerers;
import com.mojang.serialization.Lifecycle;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.Shoal;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

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

    public static void addComposting(Item item, float value)
    {
        CompostingChanceRegistry.INSTANCE.add(item, value);
    }

    public static void addFlammableBlock(Block block, int encouragement, int flammability)
    {
        FlammableBlockRegistry.getDefaultInstance().add(block, encouragement, flammability);
    }

    public static EntityType<?> getMobInBucketItem(MobBucketItem bucket)
    {
        return bucket.type;
    }

    public static SoundEvent getEmptySoundInBucketItem(MobBucketItem bucket)
    {
        return bucket.emptySound;
    }

    public static BiomeSpecialEffects.GrassColorModifier getTropicalIslandGrassColor()
    {
        return ClassTinkerers.getEnum(BiomeSpecialEffects.GrassColorModifier.class, "FISHOFTHIEVES_TROPICAL_ISLAND");
    }

    public static CreativeModeTab.Builder getCreativeTabBuilder()
    {
        return FabricItemGroup.builder();
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
        return FabricEntityTypeBuilder.create(MobCategory.WATER_AMBIENT, entityFactory).dimensions(dimensions).trackRangeBlocks(4).build();
    }

    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, MobCategory mobCategory, EntityDimensions dimensions, int clientTrackingRange)
    {
        return FabricEntityTypeBuilder.create(mobCategory, entityFactory).dimensions(dimensions).trackRangeBlocks(clientTrackingRange).build();
    }

    public static <T extends BlockEntity> void registerBlockEntity(String key, BlockEntityType<T> type)
    {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, FishOfThieves.id(key), type);
    }

    public static <T extends Entity> void registerEntityType(String key, EntityType<T> type)
    {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, FishOfThieves.id(key), type);
    }

    public static void registerBlock(String key, Block block)
    {
        Registry.register(BuiltInRegistries.BLOCK, FishOfThieves.id(key), block);
    }

    public static void registerBlockWithItem(String key, Block block)
    {
        registerBlock(key, block);
        registerItem(key, new BlockItem(block, new Item.Properties()));
    }

    public static void registerItem(String key, Item item)
    {
        Registry.register(BuiltInRegistries.ITEM, FishOfThieves.id(key), item);
    }

    public static void registerSoundEvent(SoundEvent soundEvent)
    {
        Registry.register(BuiltInRegistries.SOUND_EVENT, soundEvent.getLocation().getPath(), soundEvent);
    }

    public static void registerFeature(String key, Feature<?> feature)
    {
        Registry.register(BuiltInRegistries.FEATURE, FishOfThieves.id(key), feature);
    }

    public static <U extends Sensor<?>> void registerSensorType(String key, SensorType<U> sensorType)
    {
        Registry.register(BuiltInRegistries.SENSOR_TYPE, FishOfThieves.id(key), sensorType);
    }

    public static void registerMemoryModuleType(String key, MemoryModuleType<?> type)
    {
        Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, FishOfThieves.id(key), type);
    }

    public static <P extends TreeDecorator> void registerTreeDecoratorType(String key, TreeDecoratorType<P> type)
    {
        Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, FishOfThieves.id(key), type);
    }

    public static <P extends FoliagePlacer> void registerFoliagePlacerType(String key, FoliagePlacerType<P> type)
    {
        Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, FishOfThieves.id(key), type);
    }

    public static <P extends BlockStateProvider> void registerBlockStateProviderType(String key, BlockStateProviderType<P> type)
    {
        Registry.register(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, FishOfThieves.id(key), type);
    }

    public static <T extends GameRules.Value<T>> GameRules.Key<T> registerGameRule(String name, GameRules.Category category, GameRules.Type<T> type)
    {
        return GameRuleRegistry.register(FishOfThieves.MOD_RESOURCES + name, category, type);
    }

    public static GameRules.Type<GameRules.BooleanValue> getGameRuleBoolean(boolean defaultValue)
    {
        return GameRuleFactory.createBooleanRule(defaultValue);
    }

    public static void registerMobEffect(int id, String key, MobEffect mobEffect)
    {
        ((WritableRegistry<MobEffect>) BuiltInRegistries.MOB_EFFECT).registerMapping(id, ResourceKey.create(Registries.MOB_EFFECT, FishOfThieves.id(key)), mobEffect, Lifecycle.stable());
    }

    public static void sendFishingHookBait(FishingHook fishingHook)
    {
        var buff = PacketByteBufs.create();
        buff.writeVarInt(fishingHook.getId());
        buff.writeItem(fishingHook.fishofthieves$getBaitStack());

        for (var serverPlayer : PlayerLookup.tracking(fishingHook))
        {
            if (ServerPlayNetworking.canSend(serverPlayer, FishOfThieves.RECEIVE_FISHING_HOOK_BAIT))
            {
                ServerPlayNetworking.send(serverPlayer, FishOfThieves.RECEIVE_FISHING_HOOK_BAIT, buff);
            }
        }
    }

    public static void syncClientShoalFish(Shoal shoal, boolean forcedUpdate)
    {
        if (shoal.getShoalFish().isEmpty())
        {
            return;
        }

        var buff = PacketByteBufs.create();
        buff.writeVarInt(shoal.getId());
        buff.writeCollection(shoal.getShoalFish(), (buf, shoalFish) ->
        {
            buf.writeUtf(shoalFish.id());
            buf.writeUUID(shoalFish.uuid());
            buf.writeNbt(shoalFish.data());
        });
        buff.writeBoolean(forcedUpdate);

        for (var serverPlayer : PlayerLookup.tracking(shoal))
        {
            if (ServerPlayNetworking.canSend(serverPlayer, FishOfThieves.SYNC_CLIENT_SHOAL_FISH))
            {
                ServerPlayNetworking.send(serverPlayer, FishOfThieves.SYNC_CLIENT_SHOAL_FISH, buff);
            }
        }
    }

    public static void requestServerShoalFish(Shoal shoal)
    {
        var buff = PacketByteBufs.create();
        buff.writeVarInt(shoal.getId());

        if (ClientPlayNetworking.canSend(FishOfThieves.REQUEST_SERVER_SHOAL_FISH))
        {
            ClientPlayNetworking.send(FishOfThieves.REQUEST_SERVER_SHOAL_FISH, buff);
        }
    }
}