package com.stevekung.fishofthieves.fabric;

import java.util.ArrayList;

import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.compatibility.biolith.FOTBiolith;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.*;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;

public class FishOfThievesFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        FishOfThieves.init();
        FOTBlocks.init();
        FOTBlocks.initFabric();
        FOTItems.init();
        FOTItems.initFabric();
        FOTBlockEntityTypes.init();
        FOTEntities.init();
        FOTSensorTypes.init();
        FOTMemoryModuleTypes.init();
        FOTFeatures.init();
        FOTTrunkPlacerTypes.init();
        FOTTreeDecoratorTypes.init();
        FOTFoliagePlacerTypes.init();
        FOTBlockStateProviderTypes.init();
        FishOfThieves.initCommon();
        FOTLootItemConditions.init();
        FOTMobEffects.init();
        FOTPlacementModifiers.init();
        FOTSurfaceRuleConditionSources.init();
        FOTPoiTypes.init();

        if (FOTPlatform.isModLoaded("biolith"))
        {
            FOTBiolith.init();
        }

        FOTDecoratedPotPatterns.init();
        FOTDecoratedPotPatterns.putItemsToPotTexture();

        SplashtailVariants.init();
        PondieVariants.init();
        IslehopperVariants.init();
        AncientscaleVariants.init();
        PlentifinVariants.init();
        WildsplashVariants.init();
        DevilfishVariants.init();
        BattlegillVariants.init();
        WreckerVariants.init();
        StormfishVariants.init();

        FOTCreativeTabs.init();

        StrippableBlockRegistry.register(FOTBlocks.COCONUT_LOG, FOTBlocks.STRIPPED_COCONUT_LOG);
        StrippableBlockRegistry.register(FOTBlocks.COCONUT_WOOD, FOTBlocks.STRIPPED_COCONUT_WOOD);

        FuelRegistry.INSTANCE.add(FOTTags.Items.WOODEN_FISH_PLAQUE, 300);

        FishOfThieves.getFishermanTrades().forEach((level, factories) -> TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, level, factories::apply));
        FishOfThieves.getFarmerTrades().forEach((level, factories) -> TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, level, factories::apply));

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) ->
        {
            FOTLootManager.getInjectedLootPoolMap().forEach((resourceKey, function) ->
            {
                if (id.equals(resourceKey))
                {
                    tableBuilder.withPool(function.apply(LootPool.lootPool()));
                }
            });
            tableBuilder.modifyPools(builder -> FOTLootManager.getInjectedLootTableMap().forEach((resourceKey, function) ->
            {
                if (id.equals(resourceKey))
                {
                    function.apply(builder);
                }
            }));
        });

        FishOfThieves.getEntityAttributes().forEach(FabricDefaultAttributeRegistry::register);

        FishOfThieves.getSpawnPlacements().forEach(entry -> SpawnPlacements.register(entry.type(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, entry.spawnPredicate()));

        BiomeModifications.addFeature(BiomeSelectors.tag(FOTTags.Biomes.HAS_FISH_BONE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.FISH_BONE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BEACH), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.TREES_COCONUT);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.SPARSE_JUNGLE_TROPICAL_FLOWER);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.SPARSE_JUNGLE_FRUIT_TREES);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.SPARSE_JUNGLE_PATCH_WILD_PINEAPPLE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.SPARSE_JUNGLE_PATCH_WILD_POMEGRANATE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.SPARSE_JUNGLE_PATCH_TROPICAL_BUSH);

        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_SPLASHTAILS), FOTEntities.SPLASHTAIL.getCategory(), FOTEntities.SPLASHTAIL, FishOfThieves.CONFIG.spawnRate.fishWeight.splashtail, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_PONDIES), FOTEntities.PONDIE.getCategory(), FOTEntities.PONDIE, FishOfThieves.CONFIG.spawnRate.fishWeight.pondie, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_ISLEHOPPERS), FOTEntities.ISLEHOPPER.getCategory(), FOTEntities.ISLEHOPPER, FishOfThieves.CONFIG.spawnRate.fishWeight.islehopper, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_ANCIENTSCALES), FOTEntities.ANCIENTSCALE.getCategory(), FOTEntities.ANCIENTSCALE, FishOfThieves.CONFIG.spawnRate.fishWeight.ancientscale, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_PLENTIFINS), FOTEntities.PLENTIFIN.getCategory(), FOTEntities.PLENTIFIN, FishOfThieves.CONFIG.spawnRate.fishWeight.plentifin, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_WILDSPLASH), FOTEntities.WILDSPLASH.getCategory(), FOTEntities.WILDSPLASH, FishOfThieves.CONFIG.spawnRate.fishWeight.wildsplash, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_DEVILFISH), FOTEntities.DEVILFISH.getCategory(), FOTEntities.DEVILFISH, FishOfThieves.CONFIG.spawnRate.fishWeight.devilfish, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_BATTLEGILLS), FOTEntities.BATTLEGILL.getCategory(), FOTEntities.BATTLEGILL, FishOfThieves.CONFIG.spawnRate.fishWeight.battlegill, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_WRECKERS), FOTEntities.WRECKER.getCategory(), FOTEntities.WRECKER, FishOfThieves.CONFIG.spawnRate.fishWeight.wrecker, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_STORMFISH), FOTEntities.STORMFISH.getCategory(), FOTEntities.STORMFISH, FishOfThieves.CONFIG.spawnRate.fishWeight.stormfish, 4, 8);

        ServerChunkEvents.CHUNK_LOAD.register((level, chunk) ->
        {
            level.getBaitPreserve().spawnBaitOnLoad(level);

            if (FOTPlatform.isDevelopment())
            {
                sendStructurePosDebugPacket(level, chunk.getPos());
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(FishOfThieves.REQUEST_SERVER_SHOAL_FISH, FishOfThievesFabric::requestServerShoalFish);
    }

    private static void sendStructurePosDebugPacket(ServerLevel level, ChunkPos chunkPos)
    {
        for (var serverPlayer : PlayerLookup.world(level))
        {
            var structureRefMap = level.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.STRUCTURE_STARTS).getAllReferences();
            var structurePosList = new ArrayList<Pair<BlockPos, ResourceLocation>>();

            structureRefMap.keySet().stream().findAny().ifPresent(structure ->
            {
                for (var structureStart : level.structureManager().startsForStructure(SectionPos.of(chunkPos, 0), structure))
                {
                    var optional = structureStart.getPieces().stream().map(structurePiece -> structurePiece.getBoundingBox().getCenter()).findAny();
                    optional.ifPresent(blockPos -> structurePosList.add(Pair.of(blockPos, level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(structureStart.getStructure()))));
                }
            });

            if (!structurePosList.isEmpty())
            {
                var buff = PacketByteBufs.create();
                buff.writeCollection(structurePosList, (buf, pair) ->
                {
                    buf.writeBlockPos(pair.getFirst());
                    buf.writeResourceLocation(pair.getSecond());
                });

                if (ServerPlayNetworking.canSend(serverPlayer, FishOfThieves.STRUCTURE_CENTER_POS_DEBUG))
                {
                    ServerPlayNetworking.send(serverPlayer, FishOfThieves.STRUCTURE_CENTER_POS_DEBUG, buff);
                }
            }
        }
    }

    public static void requestServerShoalFish(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender)
    {
        var entityId = buf.readVarInt();

        server.execute(() ->
        {
            var shoal = (Shoal) player.level().getEntity(entityId);

            if (shoal != null)
            {
                FOTPlatform.syncClientShoalFish(shoal, false);
            }
        });
    }
}