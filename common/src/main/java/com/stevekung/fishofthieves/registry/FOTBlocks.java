package com.stevekung.fishofthieves.registry;

import java.util.function.Function;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.block.*;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

@SuppressWarnings("deprecation")
public class FOTBlocks
{
    public static final Block FISH_BONE = register("fish_bone", FishBoneBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.SAND).pushReaction(PushReaction.DESTROY).strength(0.25f).dynamicShape().offsetType(BlockBehaviour.OffsetType.XYZ).sound(SoundType.BONE_BLOCK));
    public static final Block SHOAL = register("shoal", ShoalBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable().noCollision().noLootTable().pushReaction(PushReaction.DESTROY).randomTicks().liquid().sound(SoundType.EMPTY));

    public static final Block OAK_FISH_PLAQUE = register("oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(Blocks.OAK_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(SoundType.WOOD));
    public static final Block SPRUCE_FISH_PLAQUE = register("spruce_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(Blocks.SPRUCE_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(SoundType.WOOD));
    public static final Block BIRCH_FISH_PLAQUE = register("birch_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(Blocks.BIRCH_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(SoundType.WOOD));
    public static final Block JUNGLE_FISH_PLAQUE = register("jungle_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(Blocks.JUNGLE_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(SoundType.WOOD));
    public static final Block ACACIA_FISH_PLAQUE = register("acacia_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(Blocks.ACACIA_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(SoundType.WOOD));
    public static final Block DARK_OAK_FISH_PLAQUE = register("dark_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(Blocks.DARK_OAK_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(SoundType.WOOD));
    public static final Block MANGROVE_FISH_PLAQUE = register("mangrove_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(Blocks.MANGROVE_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(SoundType.WOOD));
    public static final Block CHERRY_FISH_PLAQUE = register("cherry_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(Blocks.CHERRY_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(SoundType.CHERRY_WOOD));
    public static final Block PALE_OAK_FISH_PLAQUE = register("pale_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(Blocks.PALE_OAK_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(Blocks.PALE_OAK_PLANKS.defaultBlockState().getSoundType()));
    public static final Block BAMBOO_FISH_PLAQUE = register("bamboo_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(Blocks.BAMBOO_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(SoundType.BAMBOO_WOOD));
    public static final Block CRIMSON_FISH_PLAQUE = register("crimson_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(Blocks.CRIMSON_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(SoundType.NETHER_WOOD));
    public static final Block WARPED_FISH_PLAQUE = register("warped_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(Blocks.WARPED_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(SoundType.NETHER_WOOD));

    public static final Block IRON_FRAME_OAK_FISH_PLAQUE = register("iron_frame_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(OAK_FISH_PLAQUE));
    public static final Block IRON_FRAME_SPRUCE_FISH_PLAQUE = register("iron_frame_spruce_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(SPRUCE_FISH_PLAQUE));
    public static final Block IRON_FRAME_BIRCH_FISH_PLAQUE = register("iron_frame_birch_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(BIRCH_FISH_PLAQUE));
    public static final Block IRON_FRAME_JUNGLE_FISH_PLAQUE = register("iron_frame_jungle_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(JUNGLE_FISH_PLAQUE));
    public static final Block IRON_FRAME_ACACIA_FISH_PLAQUE = register("iron_frame_acacia_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(ACACIA_FISH_PLAQUE));
    public static final Block IRON_FRAME_DARK_OAK_FISH_PLAQUE = register("iron_frame_dark_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(DARK_OAK_FISH_PLAQUE));
    public static final Block IRON_FRAME_MANGROVE_FISH_PLAQUE = register("iron_frame_mangrove_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(MANGROVE_FISH_PLAQUE));
    public static final Block IRON_FRAME_CHERRY_FISH_PLAQUE = register("iron_frame_cherry_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(CHERRY_FISH_PLAQUE));
    public static final Block IRON_FRAME_PALE_OAK_FISH_PLAQUE = register("iron_frame_pale_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(PALE_OAK_FISH_PLAQUE));
    public static final Block IRON_FRAME_BAMBOO_FISH_PLAQUE = register("iron_frame_bamboo_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(BAMBOO_FISH_PLAQUE));
    public static final Block IRON_FRAME_CRIMSON_FISH_PLAQUE = register("iron_frame_crimson_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(CRIMSON_FISH_PLAQUE));
    public static final Block IRON_FRAME_WARPED_FISH_PLAQUE = register("iron_frame_warped_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(WARPED_FISH_PLAQUE));

    public static final Block GOLDEN_FRAME_OAK_FISH_PLAQUE = register("golden_frame_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(OAK_FISH_PLAQUE));
    public static final Block GOLDEN_FRAME_SPRUCE_FISH_PLAQUE = register("golden_frame_spruce_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(SPRUCE_FISH_PLAQUE));
    public static final Block GOLDEN_FRAME_BIRCH_FISH_PLAQUE = register("golden_frame_birch_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(BIRCH_FISH_PLAQUE));
    public static final Block GOLDEN_FRAME_JUNGLE_FISH_PLAQUE = register("golden_frame_jungle_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(JUNGLE_FISH_PLAQUE));
    public static final Block GOLDEN_FRAME_ACACIA_FISH_PLAQUE = register("golden_frame_acacia_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(ACACIA_FISH_PLAQUE));
    public static final Block GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE = register("golden_frame_dark_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(DARK_OAK_FISH_PLAQUE));
    public static final Block GOLDEN_FRAME_MANGROVE_FISH_PLAQUE = register("golden_frame_mangrove_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(MANGROVE_FISH_PLAQUE));
    public static final Block GOLDEN_FRAME_CHERRY_FISH_PLAQUE = register("golden_frame_cherry_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(CHERRY_FISH_PLAQUE));
    public static final Block GOLDEN_FRAME_PALE_OAK_FISH_PLAQUE = register("golden_frame_pale_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(PALE_OAK_FISH_PLAQUE));
    public static final Block GOLDEN_FRAME_BAMBOO_FISH_PLAQUE = register("golden_frame_bamboo_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(BAMBOO_FISH_PLAQUE));
    public static final Block GOLDEN_FRAME_CRIMSON_FISH_PLAQUE = register("golden_frame_crimson_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(CRIMSON_FISH_PLAQUE));
    public static final Block GOLDEN_FRAME_WARPED_FISH_PLAQUE = register("golden_frame_warped_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(WARPED_FISH_PLAQUE));

    public static final Block COPPER_FRAME_OAK_FISH_PLAQUE = register("copper_frame_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(OAK_FISH_PLAQUE));
    public static final Block COPPER_FRAME_SPRUCE_FISH_PLAQUE = register("copper_frame_spruce_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(SPRUCE_FISH_PLAQUE));
    public static final Block COPPER_FRAME_BIRCH_FISH_PLAQUE = register("copper_frame_birch_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(BIRCH_FISH_PLAQUE));
    public static final Block COPPER_FRAME_JUNGLE_FISH_PLAQUE = register("copper_frame_jungle_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(JUNGLE_FISH_PLAQUE));
    public static final Block COPPER_FRAME_ACACIA_FISH_PLAQUE = register("copper_frame_acacia_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(ACACIA_FISH_PLAQUE));
    public static final Block COPPER_FRAME_DARK_OAK_FISH_PLAQUE = register("copper_frame_dark_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(DARK_OAK_FISH_PLAQUE));
    public static final Block COPPER_FRAME_MANGROVE_FISH_PLAQUE = register("copper_frame_mangrove_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(MANGROVE_FISH_PLAQUE));
    public static final Block COPPER_FRAME_CHERRY_FISH_PLAQUE = register("copper_frame_cherry_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(CHERRY_FISH_PLAQUE));
    public static final Block COPPER_FRAME_PALE_OAK_FISH_PLAQUE = register("copper_frame_pale_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(PALE_OAK_FISH_PLAQUE));
    public static final Block COPPER_FRAME_BAMBOO_FISH_PLAQUE = register("copper_frame_bamboo_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(BAMBOO_FISH_PLAQUE));
    public static final Block COPPER_FRAME_CRIMSON_FISH_PLAQUE = register("copper_frame_crimson_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(CRIMSON_FISH_PLAQUE));
    public static final Block COPPER_FRAME_WARPED_FISH_PLAQUE = register("copper_frame_warped_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(WARPED_FISH_PLAQUE));

    public static final Block GILDED_OAK_FISH_PLAQUE = register("gilded_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(OAK_FISH_PLAQUE));
    public static final Block GILDED_SPRUCE_FISH_PLAQUE = register("gilded_spruce_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(SPRUCE_FISH_PLAQUE));
    public static final Block GILDED_BIRCH_FISH_PLAQUE = register("gilded_birch_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(BIRCH_FISH_PLAQUE));
    public static final Block GILDED_JUNGLE_FISH_PLAQUE = register("gilded_jungle_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(JUNGLE_FISH_PLAQUE));
    public static final Block GILDED_ACACIA_FISH_PLAQUE = register("gilded_acacia_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(ACACIA_FISH_PLAQUE));
    public static final Block GILDED_DARK_OAK_FISH_PLAQUE = register("gilded_dark_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(DARK_OAK_FISH_PLAQUE));
    public static final Block GILDED_MANGROVE_FISH_PLAQUE = register("gilded_mangrove_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(MANGROVE_FISH_PLAQUE));
    public static final Block GILDED_CHERRY_FISH_PLAQUE = register("gilded_cherry_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(CHERRY_FISH_PLAQUE));
    public static final Block GILDED_PALE_OAK_FISH_PLAQUE = register("gilded_pale_oak_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(PALE_OAK_FISH_PLAQUE));
    public static final Block GILDED_BAMBOO_FISH_PLAQUE = register("gilded_bamboo_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(BAMBOO_FISH_PLAQUE));
    public static final Block GILDED_CRIMSON_FISH_PLAQUE = register("gilded_crimson_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(CRIMSON_FISH_PLAQUE));
    public static final Block GILDED_WARPED_FISH_PLAQUE = register("gilded_warped_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(WARPED_FISH_PLAQUE));

    public static final Block SMALL_COCONUT_LOG = register("small_coconut_log", SmallRotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.STONE).randomTicks().noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block COCONUT_FRUIT_GROWABLE_LOG = register("coconut_fruit_growable_log", CoconutFruitGrowableLogBlock::new, BlockBehaviour.Properties.of().overrideLootTable(SMALL_COCONUT_LOG.getLootTable()).mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.STONE).randomTicks().noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block SMALL_TOP_COCONUT_LOG = register("small_top_coconut_log", properties -> new SmallRotatedPillarBlock(SMALL_COCONUT_LOG.defaultBlockState(), properties), BlockBehaviour.Properties.of().overrideLootTable(SMALL_COCONUT_LOG.getLootTable()).mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.STONE).randomTicks().noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block SMALL_COCONUT_WOOD = register("small_coconut_wood", SmallRotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block MEDIUM_COCONUT_LOG = register("medium_coconut_log", MediumRotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.STONE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block MEDIUM_COCONUT_WOOD = register("medium_coconut_wood", MediumRotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block COCONUT_LOG = register("coconut_log", FOTRotatedPillarBlock::new, logProperties(MapColor.COLOR_ORANGE, MapColor.STONE, SoundType.WOOD));
    public static final Block COCONUT_WOOD = register("coconut_wood", FOTRotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block STRIPPED_COCONUT_LOG = register("stripped_coconut_log", FOTRotatedPillarBlock::new, logProperties(MapColor.COLOR_ORANGE, MapColor.COLOR_ORANGE, SoundType.WOOD));
    public static final Block STRIPPED_COCONUT_WOOD = register("stripped_coconut_wood", FOTRotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block STRIPPED_MEDIUM_COCONUT_LOG = register("stripped_medium_coconut_log", MediumRotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block STRIPPED_MEDIUM_COCONUT_WOOD = register("stripped_medium_coconut_wood", MediumRotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block STRIPPED_SMALL_COCONUT_LOG = register("stripped_small_coconut_log", SmallRotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block STRIPPED_SMALL_COCONUT_WOOD = register("stripped_small_coconut_wood", SmallRotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block COCONUT_SAPLING = register("coconut_sapling", properties -> new CoconutSaplingBlock(FOTTreeGrowers.COCONUT, FOTTreeGrowers.OLD_COCONUT, properties), BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.FLOWERING_AZALEA).pushReaction(PushReaction.DESTROY));
    public static final Block COCONUT_FRUIT = register("coconut_fruit", CoconutFruitBlock::new, BlockBehaviour.Properties.of().noOcclusion().sound(SoundType.WOOD).strength(1.0F).pushReaction(PushReaction.DESTROY));
    public static final Block COCONUT_FRONDS = register("coconut_fronds", CoconutFrondsBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().ignitedByLava().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block BANANA_STEM = register("banana_stem", BananaStemBlock::new, BlockBehaviour.Properties.of().mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_GREEN : MapColor.COLOR_BROWN).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(1.5F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block BANANA_CLUSTER_GROWABLE_STEM = register("banana_cluster_growable_stem", BananaClusterGrowableStemBlock::new, BlockBehaviour.Properties.of().overrideLootTable(BANANA_STEM.getLootTable()).mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_GREEN : MapColor.COLOR_BROWN).randomTicks().noOcclusion().instrument(NoteBlockInstrument.BASS).strength(1.5F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block BANANA_LEAVES = register("banana_leaves", BananaLeavesBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().ignitedByLava().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block VERTICAL_BANANA_LEAVES = register("vertical_banana_leaves", VerticalBananaLeavesBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).ignitedByLava().noCollision().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block VERTICAL_COCONUT_FRONDS = register("vertical_coconut_fronds", VerticalCoconutFrondsBlock::new, BlockBehaviour.Properties.of().overrideLootTable(COCONUT_FRONDS.getLootTable()).mapColor(MapColor.PLANT).ignitedByLava().noCollision().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block BANANA_SHOOTS = register("banana_shoots", properties -> new BananaShootsBlock(FOTTreeGrowers.BANANA, properties), BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).offsetType(BlockBehaviour.OffsetType.XYZ).noCollision().randomTicks().instabreak().sound(SoundType.FLOWERING_AZALEA).pushReaction(PushReaction.DESTROY));
    public static final Block BANANA_SHOOTS_PLANT = register("banana_shoots_plant", BananaShootsPlantBlock::new, BlockBehaviour.Properties.of().overrideLootTable(BANANA_SHOOTS.getLootTable()).mapColor(MapColor.PLANT).offsetType(BlockBehaviour.OffsetType.XYZ).ignitedByLava().noCollision().noOcclusion().instabreak().sound(SoundType.FLOWERING_AZALEA).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block BANANA_BLOSSOM = register("banana_blossom", BananaBlossomBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).ignitedByLava().noCollision().noOcclusion().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().sound(SoundType.AZALEA).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block BANANA_BLOSSOM_PLANT = register("banana_blossom_plant", BananaBlossomPlantBlock::new, BlockBehaviour.Properties.of().overrideLootTable(BANANA_BLOSSOM.getLootTable()).mapColor(MapColor.COLOR_PURPLE).ignitedByLava().noCollision().noOcclusion().instabreak().sound(SoundType.AZALEA).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block UNDERRIPE_BANANA_CLUSTER_PLANT = register("underripe_banana_cluster_plant", UnderripeBananaClusterPlantBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).ignitedByLava().randomTicks().noOcclusion().strength(1.0f).sound(SoundType.WOOD).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block BARELY_RIPE_BANANA_CLUSTER_PLANT = register("barely_ripe_banana_cluster_plant", properties -> new BananaClusterPlantBlock(BananaClusterBlock.Type.BARELY_RIPE, properties), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).randomTicks().ignitedByLava().noOcclusion().strength(1.0f).sound(SoundType.WOOD).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block RIPE_BANANA_CLUSTER_PLANT = register("ripe_banana_cluster_plant", properties -> new BananaClusterPlantBlock(BananaClusterBlock.Type.RIPE, properties), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).ignitedByLava().noOcclusion().strength(1.0f).sound(SoundType.WOOD).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block UNDERRIPE_BANANA_CLUSTER = register("underripe_banana_cluster", properties -> new BananaClusterBlock(BananaClusterBlock.Type.UNDERRIPE, properties), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).ignitedByLava().randomTicks().noOcclusion().strength(1.0f).sound(SoundType.WOOD).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block BARELY_RIPE_BANANA_CLUSTER = register("barely_ripe_banana_cluster", properties -> new BananaClusterBlock(BananaClusterBlock.Type.BARELY_RIPE, properties), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).ignitedByLava().randomTicks().noOcclusion().strength(1.0f).sound(SoundType.WOOD).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block RIPE_BANANA_CLUSTER = register("ripe_banana_cluster", properties -> new BananaClusterBlock(BananaClusterBlock.Type.RIPE, properties), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).ignitedByLava().noOcclusion().strength(1.0f).sound(SoundType.WOOD).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block PINEAPPLE_CROP = register("pineapple_crop", PineappleCropBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).ignitedByLava().noCollision().noOcclusion().instabreak().sound(SoundType.AZALEA).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block RIPE_PINEAPPLE_BLOCK = register("ripe_pineapple_block", properties -> new PineappleBlock(PineappleBlock.Type.RIPE, properties), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.WOOD).ignitedByLava().noOcclusion().isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block CROWNLESS_RIPE_PINEAPPLE_BLOCK = register("crownless_ripe_pineapple_block", properties -> new PineappleBlock(PineappleBlock.Type.CROWNLESS, properties), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.WOOD).ignitedByLava().noOcclusion().isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block UNDERRIPE_PINEAPPLE_BLOCK = register("underripe_pineapple_block", properties -> new PineappleBlock(PineappleBlock.Type.UNDERRIPE, properties), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.6F).sound(SoundType.WOOD).ignitedByLava().noOcclusion().isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block MANGO_LEAVES = register("mango_leaves", MangoLeavesBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(FOTBlocks::ocelotOrParrot).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(FOTBlocks::never));
    public static final Block MANGO_FRUIT = register("mango_fruit", MangoFruitBlock::new, BlockBehaviour.Properties.of().offsetType(BlockBehaviour.OffsetType.XYZ).dynamicShape().noOcclusion().randomTicks().sound(SoundType.WOOD).instabreak().pushReaction(PushReaction.DESTROY));
    public static final Block HANGING_MANGO_FRUIT = register("hanging_mango_fruit", HangingMangoFruitBlock::new, BlockBehaviour.Properties.of().offsetType(BlockBehaviour.OffsetType.XYZ).dynamicShape().randomTicks().noOcclusion().sound(SoundType.WOOD).instabreak().pushReaction(PushReaction.DESTROY));
    public static final Block TALL_POMEGRANATE_PLANT = register("tall_pomegranate_plant", TallPomegranatePlantBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().sound(SoundType.AZALEA).ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final Block PRISMARIZED_LOG = register("prismarized_log", FOTRotatedPillarBlock::new, BlockBehaviour.Properties.of().lightLevel(blockState -> 3).mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.NETHER_WOOD).ignitedByLava().emissiveRendering(FOTBlocks::always));
    public static final Block BUDDING_PRISMARIZED_LOG = register("budding_prismarized_log", BuddingPrismarizedLogBlock::new, BlockBehaviour.Properties.of().lightLevel(blockState -> 3).mapColor(MapColor.COLOR_LIGHT_BLUE).randomTicks().strength(2.0F).instrument(NoteBlockInstrument.BASS).sound(SoundType.NETHER_WOOD).pushReaction(PushReaction.DESTROY).ignitedByLava().emissiveRendering(FOTBlocks::always));
    public static final Block GUARDIAN_FRUIT = register("guardian_fruit", GuardianFruitBlock::new, BlockBehaviour.Properties.of().lightLevel(blockState -> 3).noOcclusion().sound(SoundType.WOOD).instabreak().pushReaction(PushReaction.DESTROY).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).emissiveRendering(FOTBlocks::always));

    public static final Block PINK_PLUMERIA = register("pink_plumeria", properties -> new FlowerBlock(MobEffects.REGENERATION, 5, properties), plumeriaProperties());
    public static final Block LIGHT_BLUE_PLUMERIA = register("light_blue_plumeria", properties -> new FlowerBlock(MobEffects.SPEED, 5, properties), plumeriaProperties());
    public static final Block WHITE_PLUMERIA = register("white_plumeria", properties -> new FlowerBlock(MobEffects.SLOW_FALLING, 5, properties), plumeriaProperties());

    public static final Block MANGO_PIT = register("mango_pit", MangoPitBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollision().randomTicks().offsetType(BlockBehaviour.OffsetType.XYZ).instabreak().sound(SoundType.FLOWERING_AZALEA).pushReaction(PushReaction.DESTROY));
    public static final Block MANGO_SAPLING = register("mango_sapling", properties -> new MangoSaplingBlock(FOTTreeGrowers.MANGO, properties), BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.CHERRY_SAPLING).pushReaction(PushReaction.DESTROY));
    public static final Block POMEGRANATE_PLANT = register("pomegranate_plant", PomegranatePlantBlock::new, BlockBehaviour.Properties.of().forceSolidOff().mapColor(MapColor.PLANT).randomTicks().sound(SoundType.AZALEA).pushReaction(PushReaction.DESTROY));
    public static final Block POMEGRANATE_SAPLING = register("pomegranate_sapling", PomegranateSaplingBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).noCollision().randomTicks().instabreak().sound(SoundType.CHERRY_SAPLING).pushReaction(PushReaction.DESTROY));
    public static final Block TROPICAL_RED_FERN = register("tropical_red_fern", TropicalRedFernBlock::new, BlockBehaviour.Properties.of().offsetType(BlockBehaviour.OffsetType.XYZ).mapColor(MapColor.COLOR_RED).ignitedByLava().noCollision().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block TROPICAL_MONSTERA = register("tropical_monstera", TropicalMonsteraBlock::new, BlockBehaviour.Properties.of().offsetType(BlockBehaviour.OffsetType.XYZ).mapColor(MapColor.COLOR_GREEN).ignitedByLava().noCollision().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));

    public static final Block POTTED_PINK_PLUMERIA = register("potted_pink_plumeria", properties -> new FlowerPotBlock(PINK_PLUMERIA, properties), flowerPotProperties());
    public static final Block POTTED_LIGHT_BLUE_PLUMERIA = register("potted_light_blue_plumeria", properties -> new FlowerPotBlock(LIGHT_BLUE_PLUMERIA, properties), flowerPotProperties());
    public static final Block POTTED_WHITE_PLUMERIA = register("potted_white_plumeria", properties -> new FlowerPotBlock(WHITE_PLUMERIA, properties), flowerPotProperties());
    public static final Block POTTED_BANANA_SHOOTS = register("potted_banana_shoots", properties -> new FlowerPotBlock(BANANA_SHOOTS, properties), flowerPotProperties());
    public static final Block POTTED_MANGO_PIT = register("potted_mango_pit", properties -> new FlowerPotBlock(MANGO_PIT, properties), flowerPotProperties());
    public static final Block POTTED_MANGO_SAPLING = register("potted_mango_sapling", properties -> new FlowerPotBlock(MANGO_SAPLING, properties), flowerPotProperties());
    public static final Block POTTED_POMEGRANATE_PLANT = register("potted_pomegranate_plant", properties -> new FlowerPotBlock(POMEGRANATE_PLANT, properties), flowerPotProperties());
    public static final Block POTTED_POMEGRANATE_SAPLING = register("potted_pomegranate_sapling", properties -> new FlowerPotBlock(POMEGRANATE_SAPLING, properties), flowerPotProperties());
    public static final Block POTTED_TROPICAL_RED_FERN = register("potted_tropical_red_fern", properties -> new FlowerPotBlock(TROPICAL_RED_FERN, properties), flowerPotProperties());
    public static final Block POTTED_TROPICAL_MONSTERA = register("potted_tropical_monstera", properties -> new FlowerPotBlock(TROPICAL_MONSTERA, properties), flowerPotProperties());

    public static final Block COCONUT_PLANKS = register("coconut_planks", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block COCONUT_BUTTON = register("coconut_button", properties -> new ButtonBlock(FOTBlockSetTypes.COCONUT, 30, properties), buttonProperties());
    public static final Block COCONUT_FENCE = register("coconut_fence", FenceBlock::new, BlockBehaviour.Properties.of().mapColor(COCONUT_PLANKS.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block COCONUT_FENCE_GATE = register("coconut_fence_gate", properties -> new FenceGateBlock(FOTWoodTypes.COCONUT, properties), BlockBehaviour.Properties.of().mapColor(COCONUT_PLANKS.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava());
    public static final Block COCONUT_PRESSURE_PLATE = register("coconut_pressure_plate", properties -> new PressurePlateBlock(FOTBlockSetTypes.COCONUT, properties), BlockBehaviour.Properties.of().mapColor(COCONUT_PLANKS.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final Block COCONUT_SLAB = register("coconut_slab", SlabBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block COCONUT_STAIRS = register("coconut_stairs", properties -> new StairBlock(COCONUT_PLANKS.defaultBlockState(), properties), BlockBehaviour.Properties.ofLegacyCopy(COCONUT_PLANKS));
    public static final Block COCONUT_TRAPDOOR = register("coconut_trapdoor", properties -> new TrapDoorBlock(FOTBlockSetTypes.COCONUT, properties), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(FOTBlocks::never).ignitedByLava());
    public static final Block COCONUT_DOOR = register("coconut_door", properties -> new DoorBlock(FOTBlockSetTypes.COCONUT, properties), BlockBehaviour.Properties.of().mapColor(COCONUT_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final Block COCONUT_SIGN = register("coconut_sign", properties -> new FOTStandingSignBlock(properties, FOTWoodTypes.COCONUT), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava());
    public static final Block COCONUT_WALL_SIGN = register("coconut_wall_sign", properties -> new FOTWallSignBlock(properties, FOTWoodTypes.COCONUT), BlockBehaviour.Properties.of().overrideLootTable(COCONUT_SIGN.getLootTable()).mapColor(MapColor.COLOR_ORANGE).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava());
    public static final Block COCONUT_HANGING_SIGN = register("coconut_hanging_sign", properties -> new FOTCeilingHangingSignBlock(properties, FOTWoodTypes.COCONUT), BlockBehaviour.Properties.of().mapColor(COCONUT_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava());
    public static final Block COCONUT_WALL_HANGING_SIGN = register("coconut_wall_hanging_sign", properties -> new FOTWallHangingSignBlock(properties, FOTWoodTypes.COCONUT), BlockBehaviour.Properties.of().overrideLootTable(COCONUT_HANGING_SIGN.getLootTable()).mapColor(COCONUT_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava());
    public static final Block COCONUT_FISH_PLAQUE = register("coconut_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.WOODEN), BlockBehaviour.Properties.of().mapColor(COCONUT_PLANKS.defaultMapColor()).noCollision().strength(1.0F).sound(SoundType.WOOD));
    public static final Block IRON_FRAME_COCONUT_FISH_PLAQUE = register("iron_frame_coconut_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.IRON), BlockBehaviour.Properties.ofLegacyCopy(COCONUT_FISH_PLAQUE));
    public static final Block COPPER_FRAME_COCONUT_FISH_PLAQUE = register("copper_frame_coconut_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(COCONUT_FISH_PLAQUE));
    public static final Block GOLDEN_FRAME_COCONUT_FISH_PLAQUE = register("golden_frame_coconut_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GOLDEN), BlockBehaviour.Properties.ofLegacyCopy(COCONUT_FISH_PLAQUE));
    public static final Block GILDED_COCONUT_FISH_PLAQUE = register("gilded_coconut_fish_plaque", properties -> new FishPlaqueBlock(properties, FishPlaqueBlock.Type.GILDED), BlockBehaviour.Properties.ofLegacyCopy(COCONUT_FISH_PLAQUE));
    public static final Block COCONUT_SHELF = register("coconut_shelf", ShelfBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).sound(SoundType.SHELF).ignitedByLava().strength(2.0F, 3.0F));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Block");
    }

    private static Block register(String key, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties)
    {
        var block = function.apply(properties.setId(blockId(key)));
        return Registry.register(BuiltInRegistries.BLOCK, FishOfThieves.id(key), block);
    }

    private static Block register(String key, BlockBehaviour.Properties properties)
    {
        var block = new Block(properties.setId(blockId(key)));
        return Registry.register(BuiltInRegistries.BLOCK, FishOfThieves.id(key), block);
    }

    private static ResourceKey<Block> blockId(String key)
    {
        return ResourceKey.create(Registries.BLOCK, FishOfThieves.id(key));
    }

    private static BlockBehaviour.Properties logProperties(MapColor sideColor, MapColor topColor, SoundType sound)
    {
        return BlockBehaviour.Properties.of().mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? sideColor : topColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(sound).ignitedByLava();
    }

    private static BlockBehaviour.Properties flowerPotProperties()
    {
        return BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties buttonProperties()
    {
        return BlockBehaviour.Properties.of().noCollision().strength(0.5F).pushReaction(PushReaction.DESTROY);
    }

    public static BlockBehaviour.Properties plumeriaProperties()
    {
        return BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollision().instabreak().sound(SoundType.CHERRY_LEAVES).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);
    }

    private static boolean ocelotOrParrot(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entity)
    {
        return entity == EntityTypes.OCELOT || entity == EntityTypes.PARROT;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos)
    {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entity)
    {
        return false;
    }

    private static boolean always(BlockState state)
    {
        return true;
    }
}