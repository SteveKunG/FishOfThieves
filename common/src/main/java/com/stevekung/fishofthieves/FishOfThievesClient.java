package com.stevekung.fishofthieves;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.stevekung.fishofthieves.client.FOTModelLayers;
import com.stevekung.fishofthieves.client.model.*;
import com.stevekung.fishofthieves.client.renderer.blockentity.FishPlaqueRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.*;
import com.stevekung.fishofthieves.registry.FOTBlockEntityTypes;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTEntities;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.core.registries.BuiltInClientRegistries;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FishOfThievesClient
{
    public static List<ModelLayerEntry> getModelLayers()
    {
        return List.of(
                new ModelLayerEntry(SplashtailModel.LAYER, SplashtailModel::createBodyLayer),
                new ModelLayerEntry(PondieModel.LAYER, PondieModel::createBodyLayer),
                new ModelLayerEntry(IslehopperModel.LAYER, IslehopperModel::createBodyLayer),
                new ModelLayerEntry(AncientscaleModel.LAYER, AncientscaleModel::createBodyLayer),
                new ModelLayerEntry(PlentifinModel.LAYER, PlentifinModel::createBodyLayer),
                new ModelLayerEntry(WildsplashModel.LAYER, WildsplashModel::createBodyLayer),
                new ModelLayerEntry(DevilfishModel.LAYER, DevilfishModel::createBodyLayer),
                new ModelLayerEntry(BattlegillModel.LAYER, BattlegillModel::createBodyLayer),
                new ModelLayerEntry(WreckerModel.LAYER, WreckerModel::createBodyLayer),
                new ModelLayerEntry(StormfishModel.LAYER, StormfishModel::createBodyLayer),

                new ModelLayerEntry(FOTModelLayers.COCONUT_BOAT, BoatModel::createBoatModel),
                new ModelLayerEntry(FOTModelLayers.COCONUT_CHEST_BOAT, BoatModel::createChestBoatModel),

                new ModelLayerEntry(HeadphoneModel.LAYER, HeadphoneModel::createBodyLayer)
        );
    }

    @SuppressWarnings("unchecked")
    public static List<EntityRendererEntry<Entity>> getEntityRenderers()
    {
        return Util.make(new ArrayList<EntityRendererEntry<?>>(), list ->
        {
            list.add(new EntityRendererEntry<>(FOTEntities.SPLASHTAIL, SplashtailRenderer::new));
            list.add(new EntityRendererEntry<>(FOTEntities.PONDIE, PondieRenderer::new));
            list.add(new EntityRendererEntry<>(FOTEntities.ISLEHOPPER, IslehopperRenderer::new));
            list.add(new EntityRendererEntry<>(FOTEntities.ANCIENTSCALE, AncientscaleRenderer::new));
            list.add(new EntityRendererEntry<>(FOTEntities.PLENTIFIN, PlentifinRenderer::new));
            list.add(new EntityRendererEntry<>(FOTEntities.WILDSPLASH, WildsplashRenderer::new));
            list.add(new EntityRendererEntry<>(FOTEntities.DEVILFISH, DevilfishRenderer::new));
            list.add(new EntityRendererEntry<>(FOTEntities.BATTLEGILL, BattlegillRenderer::new));
            list.add(new EntityRendererEntry<>(FOTEntities.WRECKER, WreckerRenderer::new));
            list.add(new EntityRendererEntry<>(FOTEntities.STORMFISH, StormfishRenderer::new));

            list.add(new EntityRendererEntry<>(FOTEntities.COCONUT_BOAT, context -> new BoatRenderer(context, FOTModelLayers.COCONUT_BOAT)));
            list.add(new EntityRendererEntry<>(FOTEntities.COCONUT_CHEST_BOAT, context -> new BoatRenderer(context, FOTModelLayers.COCONUT_CHEST_BOAT)));
            list.add(new EntityRendererEntry<>(FOTEntities.SHOAL, ShoalRenderer::new));
        }).stream().map(entry -> (EntityRendererEntry<Entity>) entry).toList();
    }

    public static void registerBlockEntityRenderers()
    {
        BlockEntityRenderers.register(BuiltInClientRegistries.BLOCK_ENTITY_RENDERER, FOTBlockEntityTypes.FISH_PLAQUE, FishPlaqueRenderer::new);
        BlockEntityRenderers.register(BuiltInClientRegistries.BLOCK_ENTITY_RENDERER, FOTBlockEntityTypes.SIGN, StandingSignRenderer::new);
        BlockEntityRenderers.register(BuiltInClientRegistries.BLOCK_ENTITY_RENDERER, FOTBlockEntityTypes.HANGING_SIGN, HangingSignRenderer::new);
    }

    @SuppressWarnings("unchecked")
    public static List<HeadphoneEntry<LivingEntity, LivingEntityRenderState>> getHeadphone()
    {
        return Util.make(new ArrayList<HeadphoneEntry<?, ?>>(), list ->
        {
            list.add(new HeadphoneEntry<>(EntityTypes.COD, HeadphoneModel.Scaleable.COD));
            list.add(new HeadphoneEntry<>(EntityTypes.SALMON, HeadphoneModel.Scaleable.SALMON));
            list.add(new HeadphoneEntry<>(EntityTypes.PUFFERFISH, HeadphoneModel.Scaleable.PUFFERFISH));
            list.add(new HeadphoneEntry<>(EntityTypes.TROPICAL_FISH, HeadphoneModel.Scaleable.TROPICAL_FISH));
            list.add(new HeadphoneEntry<>(EntityTypes.TADPOLE, HeadphoneModel.Scaleable.TADPOLE));
        }).stream().map(entry -> (HeadphoneEntry<LivingEntity, LivingEntityRenderState>) entry).toList();
    }

    public static List<BlockColorEntry> getBlockColors()
    {
        return Util.make(new ArrayList<>(), list ->
        {
            list.add(new BlockColorEntry(List.of(BlockTintSources.foliage()), FOTBlocks.MANGO_LEAVES));
            list.add(new BlockColorEntry(List.of(mangoFruit()), FOTBlocks.MANGO_FRUIT, FOTBlocks.HANGING_MANGO_FRUIT));
            list.add(new BlockColorEntry(List.of(BlockTintSources.water()), FOTBlocks.SHOAL));
        });
    }

    public static BlockTintSource mangoFruit()
    {
        return new BlockTintSource()
        {
            @Override
            public int color(BlockState state)
            {
                return -12012264;
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos)
            {
                return BiomeColors.getAverageFoliageColor(level, pos);
            }

            @Override
            public int colorAsTerrainParticle(BlockState state, BlockAndTintGetter level, BlockPos pos)
            {
                return -1;
            }
        };
    }

    public record ModelLayerEntry(ModelLayerLocation layerLocation, Supplier<LayerDefinition> supplier) {}

    public record EntityRendererEntry<E extends Entity>(EntityType<? extends E> entityType, EntityRendererProvider<E> factory) {}

    public record HeadphoneEntry<E extends LivingEntity, S extends LivingEntityRenderState>(EntityType<? extends E> entityType, HeadphoneModel.Scaleable<S> scaleable) {}

    public record BlockColorEntry(List<BlockTintSource> blockColors, Block... blocks) {}
}