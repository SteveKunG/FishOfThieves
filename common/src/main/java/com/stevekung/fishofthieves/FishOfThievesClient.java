package com.stevekung.fishofthieves;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.stevekung.fishofthieves.client.model.*;
import com.stevekung.fishofthieves.client.renderer.blockentity.FishPlaqueRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.*;
import com.stevekung.fishofthieves.registry.FOTBlockEntityTypes;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTEntities;

import net.minecraft.Util;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

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
            list.add(new EntityRendererEntry<>(FOTEntities.SHOAL, ShoalRenderer::new));
        }).stream().map(entry -> (EntityRendererEntry<Entity>) entry).toList();
    }

    public static void registerBlockEntityRenderers()
    {
        BlockEntityRenderers.register(FOTBlockEntityTypes.FISH_PLAQUE, FishPlaqueRenderer::new);
        BlockEntityRenderers.register(FOTBlockEntityTypes.SIGN, SignRenderer::new);
        BlockEntityRenderers.register(FOTBlockEntityTypes.HANGING_SIGN, HangingSignRenderer::new);
    }

    @SuppressWarnings("unchecked")
    public static List<HeadphoneEntry<LivingEntity>> getHeadphone()
    {
        return Util.make(new ArrayList<HeadphoneEntry<?>>(), list ->
        {
            list.add(new HeadphoneEntry<>(EntityType.COD, HeadphoneModel.Scaleable.COD));
            list.add(new HeadphoneEntry<>(EntityType.SALMON, HeadphoneModel.Scaleable.SALMON));
            list.add(new HeadphoneEntry<>(EntityType.PUFFERFISH, HeadphoneModel.Scaleable.PUFFERFISH));
            list.add(new HeadphoneEntry<>(EntityType.TROPICAL_FISH, HeadphoneModel.Scaleable.TROPICAL_FISH));
            list.add(new HeadphoneEntry<>(EntityType.TADPOLE, HeadphoneModel.Scaleable.TADPOLE));
        }).stream().map(entry -> (HeadphoneEntry<LivingEntity>) entry).toList();
    }

    public static List<BlockColorEntry> getBlockColors()
    {
        return Util.make(new ArrayList<>(), list ->
        {
            list.add(new BlockColorEntry((blockState, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor(), FOTBlocks.MANGO_LEAVES));
            list.add(new BlockColorEntry((blockState, level, pos, tintIndex) -> level != null && pos != null && tintIndex == 1 ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor(), FOTBlocks.MANGO_FRUIT, FOTBlocks.HANGING_MANGO_FRUIT));
            list.add(new BlockColorEntry((blockState, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageWaterColor(level, pos) : -1, FOTBlocks.SHOAL));
        });
    }

    public static List<ItemColorEntry> getItemColors()
    {
        return Util.make(new ArrayList<>(), list ->
        {
            list.add(new ItemColorEntry((itemStack, tintIndex) -> FoliageColor.getDefaultColor(), FOTBlocks.MANGO_LEAVES));
        });
    }

    public record ModelLayerEntry(ModelLayerLocation layerLocation, Supplier<LayerDefinition> supplier) {}

    public record EntityRendererEntry<E extends Entity>(EntityType<? extends E> entityType, EntityRendererProvider<E> factory) {}

    public record HeadphoneEntry<E extends LivingEntity>(EntityType<? extends E> entityType, HeadphoneModel.Scaleable<E> scaleable) {}

    public record BlockColorEntry(BlockColor blockColor, Block... blocks) {}

    public record ItemColorEntry(ItemColor itemColor, ItemLike... items) {}
}