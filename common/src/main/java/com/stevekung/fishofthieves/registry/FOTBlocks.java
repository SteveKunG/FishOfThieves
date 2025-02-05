package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.block.*;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

@SuppressWarnings("deprecation")
public class FOTBlocks
{
    public static final Block FISH_BONE = register("fish_bone", new FishBoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).pushReaction(PushReaction.DESTROY).strength(0.25f).dynamicShape().offsetType(BlockBehaviour.OffsetType.XYZ).sound(SoundType.BONE_BLOCK)));

    public static final Block OAK_FISH_PLAQUE = register("oak_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.OAK_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN));
    public static final Block SPRUCE_FISH_PLAQUE = register("spruce_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.SPRUCE_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN));
    public static final Block BIRCH_FISH_PLAQUE = register("birch_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.BIRCH_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN));
    public static final Block JUNGLE_FISH_PLAQUE = register("jungle_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.JUNGLE_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN));
    public static final Block ACACIA_FISH_PLAQUE = register("acacia_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.ACACIA_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN));
    public static final Block DARK_OAK_FISH_PLAQUE = register("dark_oak_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.DARK_OAK_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN));
    public static final Block MANGROVE_FISH_PLAQUE = register("mangrove_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.MANGROVE_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN));
    public static final Block CHERRY_FISH_PLAQUE = register("cherry_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.CHERRY_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD), FishPlaqueBlock.Type.WOODEN));
    public static final Block BAMBOO_FISH_PLAQUE = register("bamboo_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.BAMBOO_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.BAMBOO_WOOD), FishPlaqueBlock.Type.WOODEN));
    public static final Block CRIMSON_FISH_PLAQUE = register("crimson_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.CRIMSON_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.NETHER_WOOD), FishPlaqueBlock.Type.WOODEN));
    public static final Block WARPED_FISH_PLAQUE = register("warped_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.WARPED_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.NETHER_WOOD), FishPlaqueBlock.Type.WOODEN));

    public static final Block IRON_FRAME_OAK_FISH_PLAQUE = register("iron_frame_oak_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(OAK_FISH_PLAQUE), FishPlaqueBlock.Type.IRON));
    public static final Block IRON_FRAME_SPRUCE_FISH_PLAQUE = register("iron_frame_spruce_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(SPRUCE_FISH_PLAQUE), FishPlaqueBlock.Type.IRON));
    public static final Block IRON_FRAME_BIRCH_FISH_PLAQUE = register("iron_frame_birch_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(BIRCH_FISH_PLAQUE), FishPlaqueBlock.Type.IRON));
    public static final Block IRON_FRAME_JUNGLE_FISH_PLAQUE = register("iron_frame_jungle_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(JUNGLE_FISH_PLAQUE), FishPlaqueBlock.Type.IRON));
    public static final Block IRON_FRAME_ACACIA_FISH_PLAQUE = register("iron_frame_acacia_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(ACACIA_FISH_PLAQUE), FishPlaqueBlock.Type.IRON));
    public static final Block IRON_FRAME_DARK_OAK_FISH_PLAQUE = register("iron_frame_dark_oak_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(DARK_OAK_FISH_PLAQUE), FishPlaqueBlock.Type.IRON));
    public static final Block IRON_FRAME_MANGROVE_FISH_PLAQUE = register("iron_frame_mangrove_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(MANGROVE_FISH_PLAQUE), FishPlaqueBlock.Type.IRON));
    public static final Block IRON_FRAME_CHERRY_FISH_PLAQUE = register("iron_frame_cherry_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(CHERRY_FISH_PLAQUE), FishPlaqueBlock.Type.IRON));
    public static final Block IRON_FRAME_BAMBOO_FISH_PLAQUE = register("iron_frame_bamboo_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(BAMBOO_FISH_PLAQUE), FishPlaqueBlock.Type.IRON));
    public static final Block IRON_FRAME_CRIMSON_FISH_PLAQUE = register("iron_frame_crimson_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(CRIMSON_FISH_PLAQUE), FishPlaqueBlock.Type.IRON));
    public static final Block IRON_FRAME_WARPED_FISH_PLAQUE = register("iron_frame_warped_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(WARPED_FISH_PLAQUE), FishPlaqueBlock.Type.IRON));

    public static final Block GOLDEN_FRAME_OAK_FISH_PLAQUE = register("golden_frame_oak_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(OAK_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN));
    public static final Block GOLDEN_FRAME_SPRUCE_FISH_PLAQUE = register("golden_frame_spruce_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(SPRUCE_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN));
    public static final Block GOLDEN_FRAME_BIRCH_FISH_PLAQUE = register("golden_frame_birch_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(BIRCH_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN));
    public static final Block GOLDEN_FRAME_JUNGLE_FISH_PLAQUE = register("golden_frame_jungle_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(JUNGLE_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN));
    public static final Block GOLDEN_FRAME_ACACIA_FISH_PLAQUE = register("golden_frame_acacia_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(ACACIA_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN));
    public static final Block GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE = register("golden_frame_dark_oak_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(DARK_OAK_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN));
    public static final Block GOLDEN_FRAME_MANGROVE_FISH_PLAQUE = register("golden_frame_mangrove_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(MANGROVE_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN));
    public static final Block GOLDEN_FRAME_CHERRY_FISH_PLAQUE = register("golden_frame_cherry_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(CHERRY_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN));
    public static final Block GOLDEN_FRAME_BAMBOO_FISH_PLAQUE = register("golden_frame_bamboo_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(BAMBOO_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN));
    public static final Block GOLDEN_FRAME_CRIMSON_FISH_PLAQUE = register("golden_frame_crimson_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(CRIMSON_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN));
    public static final Block GOLDEN_FRAME_WARPED_FISH_PLAQUE = register("golden_frame_warped_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(WARPED_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN));

    public static final Block GILDED_OAK_FISH_PLAQUE = register("gilded_oak_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(OAK_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED));
    public static final Block GILDED_SPRUCE_FISH_PLAQUE = register("gilded_spruce_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(SPRUCE_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED));
    public static final Block GILDED_BIRCH_FISH_PLAQUE = register("gilded_birch_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(BIRCH_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED));
    public static final Block GILDED_JUNGLE_FISH_PLAQUE = register("gilded_jungle_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(JUNGLE_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED));
    public static final Block GILDED_ACACIA_FISH_PLAQUE = register("gilded_acacia_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(ACACIA_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED));
    public static final Block GILDED_DARK_OAK_FISH_PLAQUE = register("gilded_dark_oak_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(DARK_OAK_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED));
    public static final Block GILDED_MANGROVE_FISH_PLAQUE = register("gilded_mangrove_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(MANGROVE_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED));
    public static final Block GILDED_CHERRY_FISH_PLAQUE = register("gilded_cherry_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(CHERRY_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED));
    public static final Block GILDED_BAMBOO_FISH_PLAQUE = register("gilded_bamboo_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(BAMBOO_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED));
    public static final Block GILDED_CRIMSON_FISH_PLAQUE = register("gilded_crimson_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(CRIMSON_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED));
    public static final Block GILDED_WARPED_FISH_PLAQUE = register("gilded_warped_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(WARPED_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED));

    public static final Block SMALL_COCONUT_LOG = register("small_coconut_log", new SmallRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.STONE).randomTicks().noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block GROWABLE_SMALL_COCONUT_LOG = register("growable_small_coconut_log", new GrowableCoconutLogBlock(BlockBehaviour.Properties.of().dropsLike(SMALL_COCONUT_LOG).mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.STONE).randomTicks().noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block TOP_SMALL_COCONUT_LOG = register("top_small_coconut_log", new SmallRotatedPillarBlock(SMALL_COCONUT_LOG.defaultBlockState(), BlockBehaviour.Properties.of().dropsLike(SMALL_COCONUT_LOG).mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.STONE).randomTicks().noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block SMALL_COCONUT_WOOD = register("small_coconut_wood", new SmallRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block MEDIUM_COCONUT_LOG = register("medium_coconut_log", new MediumRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.STONE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block MEDIUM_COCONUT_WOOD = register("medium_coconut_wood", new MediumRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block COCONUT_LOG = register("coconut_log", log(MapColor.COLOR_ORANGE, MapColor.STONE));
    public static final Block COCONUT_WOOD = register("coconut_wood", new FOTRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block STRIPPED_COCONUT_LOG = register("stripped_coconut_log", log(MapColor.COLOR_ORANGE, MapColor.COLOR_ORANGE));
    public static final Block STRIPPED_COCONUT_WOOD = register("stripped_coconut_wood", new FOTRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block STRIPPED_MEDIUM_COCONUT_LOG = register("stripped_medium_coconut_log", new MediumRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block STRIPPED_MEDIUM_COCONUT_WOOD = register("stripped_medium_coconut_wood", new MediumRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block STRIPPED_SMALL_COCONUT_LOG = register("stripped_small_coconut_log", new SmallRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block STRIPPED_SMALL_COCONUT_WOOD = register("stripped_small_coconut_wood", new SmallRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block COCONUT_SAPLING = register("coconut_sapling", new CoconutSaplingBlock(FOTTreeGrowers.COCONUT, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.FLOWERING_AZALEA).pushReaction(PushReaction.DESTROY)));
    public static final Block COCONUT_FRUIT = register("coconut_fruit", new CoconutFruitBlock(BlockBehaviour.Properties.of().noOcclusion().sound(SoundType.WOOD).strength(1.0F).pushReaction(PushReaction.DESTROY)));
    public static final Block COCONUT_FRONDS = register("coconut_fronds", new CoconutFrondsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().ignitedByLava().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block BANANA_STEM = register("banana_stem", new BananaStemBlock(BlockBehaviour.Properties.of().mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_GREEN : MapColor.COLOR_BROWN).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block TOP_BANANA_STEM = register("top_banana_stem", new GrowableBananaClusterStemBlock(BlockBehaviour.Properties.of().dropsLike(BANANA_STEM).mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_GREEN : MapColor.COLOR_BROWN).randomTicks().noOcclusion().instrument(NoteBlockInstrument.BASS).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block GROWABLE_BANANA_STEM = register("growable_banana_stem", new GrowableBananaStemBlock(BlockBehaviour.Properties.of().dropsLike(BANANA_STEM).mapColor(MapColor.COLOR_BROWN).randomTicks().noOcclusion().instrument(NoteBlockInstrument.BASS).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block BANANA_LEAVES = register("banana_leaves", new BananaLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().ignitedByLava().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block VERTICAL_BANANA_LEAVES = register("vertical_banana_leaves", new VerticalBananaLeavesBlock(BlockBehaviour.Properties.of().dropsLike(BANANA_LEAVES).mapColor(MapColor.PLANT).ignitedByLava().noCollission().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block VERTICAL_COCONUT_FRONDS = register("vertical_coconut_fronds", new VerticalCoconutFrondsBlock(BlockBehaviour.Properties.of().dropsLike(COCONUT_FRONDS).mapColor(MapColor.PLANT).ignitedByLava().noCollission().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block BANANA_SHOOTS = register("banana_shoots", new BananaShootsBlock(FOTTreeGrowers.BANANA, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).offsetType(BlockBehaviour.OffsetType.XYZ).noCollission().randomTicks().instabreak().sound(SoundType.FLOWERING_AZALEA).pushReaction(PushReaction.DESTROY)));
    public static final Block BANANA_SHOOTS_PLANT = register("banana_shoots_plant", new BananaShootsPlantBlock(BlockBehaviour.Properties.of().dropsLike(BANANA_SHOOTS).mapColor(MapColor.PLANT).offsetType(BlockBehaviour.OffsetType.XYZ).ignitedByLava().noCollission().noOcclusion().instabreak().sound(SoundType.FLOWERING_AZALEA).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block BANANA_BLOSSOM = register("banana_blossom", new BananaBlossomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).ignitedByLava().noCollission().noOcclusion().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().sound(SoundType.AZALEA).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block BANANA_BLOSSOM_PLANT = register("banana_blossom_plant", new BananaBlossomPlantBlock(BlockBehaviour.Properties.of().dropsLike(BANANA_BLOSSOM).mapColor(MapColor.COLOR_PURPLE).ignitedByLava().noCollission().noOcclusion().instabreak().sound(SoundType.AZALEA).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block UNDERRIPE_BANANA_CLUSTER_PLANT = register("underripe_banana_cluster_plant", new UnderripeBananaClusterPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).ignitedByLava().randomTicks().noOcclusion().strength(1.0f).sound(SoundType.WOOD).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block BARELY_RIPE_BANANA_CLUSTER_PLANT = register("barely_ripe_banana_cluster_plant", new BananaClusterPlantBlock(BananaClusterBlock.Type.BARELY_RIPE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).randomTicks().ignitedByLava().noOcclusion().strength(1.0f).sound(SoundType.WOOD).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block RIPE_BANANA_CLUSTER_PLANT = register("ripe_banana_cluster_plant", new BananaClusterPlantBlock(BananaClusterBlock.Type.RIPE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).ignitedByLava().noOcclusion().strength(1.0f).sound(SoundType.WOOD).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block UNDERRIPE_BANANA_CLUSTER = register("underripe_banana_cluster", new BananaClusterBlock(BananaClusterBlock.Type.UNDERRIPE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).ignitedByLava().randomTicks().noOcclusion().strength(1.0f).sound(SoundType.WOOD).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block BARELY_RIPE_BANANA_CLUSTER = register("barely_ripe_banana_cluster", new BananaClusterBlock(BananaClusterBlock.Type.BARELY_RIPE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).ignitedByLava().randomTicks().noOcclusion().strength(1.0f).sound(SoundType.WOOD).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block RIPE_BANANA_CLUSTER = register("ripe_banana_cluster", new BananaClusterBlock(BananaClusterBlock.Type.RIPE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).ignitedByLava().noOcclusion().strength(1.0f).sound(SoundType.WOOD).isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block PINEAPPLE_CROP = register("pineapple_crop", new PineappleCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).ignitedByLava().noCollission().noOcclusion().instabreak().sound(SoundType.AZALEA).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block RIPE_PINEAPPLE_BLOCK = register("ripe_pineapple_block", new PineappleBlock(PineappleBlock.Type.RIPE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.WOOD).ignitedByLava().noOcclusion().isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block CROWNLESS_RIPE_PINEAPPLE_BLOCK = register("crownless_ripe_pineapple_block", new PineappleBlock(PineappleBlock.Type.CROWNLESS, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.WOOD).ignitedByLava().noOcclusion().isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block UNDERRIPE_PINEAPPLE_BLOCK = register("underripe_pineapple_block", new PineappleBlock(PineappleBlock.Type.UNDERRIPE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.6F).sound(SoundType.WOOD).ignitedByLava().noOcclusion().isSuffocating(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block MANGO_LEAVES = register("mango_leaves", new MangoLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(FOTBlocks::ocelotOrParrot).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(FOTBlocks::never)));
    public static final Block MANGO_FRUIT = register("mango_fruit", new MangoFruitBlock(BlockBehaviour.Properties.of().offsetType(BlockBehaviour.OffsetType.XYZ).dynamicShape().noOcclusion().randomTicks().sound(SoundType.WOOD).instabreak().pushReaction(PushReaction.DESTROY)));
    public static final Block HANGING_MANGO_FRUIT = register("hanging_mango_fruit", new HangingMangoFruitBlock(BlockBehaviour.Properties.of().offsetType(BlockBehaviour.OffsetType.XYZ).dynamicShape().randomTicks().noOcclusion().sound(SoundType.WOOD).instabreak().pushReaction(PushReaction.DESTROY)));
    public static final Block TALL_POMEGRANATE_PLANT = register("tall_pomegranate_plant", new TallPomegranatePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().sound(SoundType.AZALEA).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Block PRISMARIZED_LOG = register("prismarized_log", new FOTRotatedPillarBlock(BlockBehaviour.Properties.of().lightLevel(blockState -> 3).mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.NETHER_WOOD).ignitedByLava().emissiveRendering(FOTBlocks::always)));

    public static final Block PINK_PLUMERIA = register("pink_plumeria", getPlumeria(MobEffects.REGENERATION));
    public static final Block LIGHT_BLUE_PLUMERIA = register("light_blue_plumeria", getPlumeria(MobEffects.MOVEMENT_SPEED));
    public static final Block WHITE_PLUMERIA = register("white_plumeria", getPlumeria(MobEffects.SLOW_FALLING));

    public static final Block MANGO_PIT = register("mango_pit", new MangoPitBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().randomTicks().offsetType(BlockBehaviour.OffsetType.XYZ).instabreak().sound(SoundType.FLOWERING_AZALEA).pushReaction(PushReaction.DESTROY)));
    public static final Block MANGO_SAPLING = register("mango_sapling", new MangoSaplingBlock(FOTTreeGrowers.MANGO, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CHERRY_SAPLING).pushReaction(PushReaction.DESTROY)));
    public static final Block POMEGRANATE_PLANT = register("pomegranate_plant", new PomegranatePlantBlock(BlockBehaviour.Properties.of().forceSolidOff().mapColor(MapColor.PLANT).randomTicks().sound(SoundType.AZALEA).pushReaction(PushReaction.DESTROY)));
    public static final Block POMEGRANATE_SAPLING = register("pomegranate_sapling", new PomegranateSaplingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).noCollission().randomTicks().instabreak().sound(SoundType.CHERRY_SAPLING).pushReaction(PushReaction.DESTROY)));
    public static final Block TROPICAL_RED_FERN = register("tropical_red_fern", new TropicalRedFernBlock(BlockBehaviour.Properties.of().offsetType(BlockBehaviour.OffsetType.XYZ).mapColor(MapColor.COLOR_RED).ignitedByLava().noCollission().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));
    public static final Block TROPICAL_MONSTERA = register("tropical_monstera", new TropicalMonsteraBlock(BlockBehaviour.Properties.of().offsetType(BlockBehaviour.OffsetType.XYZ).mapColor(MapColor.COLOR_GREEN).ignitedByLava().noCollission().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY)));

    public static final Block POTTED_PINK_PLUMERIA = register("potted_pink_plumeria", flowerPot(PINK_PLUMERIA));
    public static final Block POTTED_LIGHT_BLUE_PLUMERIA = register("potted_light_blue_plumeria", flowerPot(LIGHT_BLUE_PLUMERIA));
    public static final Block POTTED_WHITE_PLUMERIA = register("potted_white_plumeria", flowerPot(WHITE_PLUMERIA));
    public static final Block POTTED_BANANA_SHOOTS = register("potted_banana_shoots", flowerPot(BANANA_SHOOTS));
    public static final Block POTTED_MANGO_PIT = register("potted_mango_pit", flowerPot(MANGO_PIT));
    public static final Block POTTED_MANGO_SAPLING = register("potted_mango_sapling", flowerPot(MANGO_SAPLING));
    public static final Block POTTED_POMEGRANATE_PLANT = register("potted_pomegranate_plant", flowerPot(POMEGRANATE_PLANT));
    public static final Block POTTED_POMEGRANATE_SAPLING = register("potted_pomegranate_sapling", flowerPot(POMEGRANATE_SAPLING));
    public static final Block POTTED_TROPICAL_RED_FERN = register("potted_tropical_red_fern", flowerPot(TROPICAL_RED_FERN));
    public static final Block POTTED_TROPICAL_MONSTERA = register("potted_tropical_monstera", flowerPot(TROPICAL_MONSTERA));

    public static final Block COCONUT_PLANKS = register("coconut_planks", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block COCONUT_BUTTON = register("coconut_button", woodenButton(FOTBlockSetTypes.COCONUT));
    public static final Block COCONUT_FENCE = register("coconut_fence", new FenceBlock(BlockBehaviour.Properties.of().mapColor(COCONUT_PLANKS.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block COCONUT_FENCE_GATE = register("coconut_fence_gate", new FenceGateBlock(FOTWoodTypes.COCONUT, BlockBehaviour.Properties.of().mapColor(COCONUT_PLANKS.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava()));
    public static final Block COCONUT_PRESSURE_PLATE = register("coconut_pressure_plate", new PressurePlateBlock(FOTBlockSetTypes.COCONUT, BlockBehaviour.Properties.of().mapColor(COCONUT_PLANKS.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Block COCONUT_SLAB = register("coconut_slab", new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final Block COCONUT_STAIRS = register("coconut_stairs", new StairBlock(COCONUT_PLANKS.defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(COCONUT_PLANKS)));
    public static final Block COCONUT_TRAPDOOR = register("coconut_trapdoor", new TrapDoorBlock(FOTBlockSetTypes.COCONUT, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(FOTBlocks::never).ignitedByLava()));
    public static final Block COCONUT_DOOR = register("coconut_door", new DoorBlock(FOTBlockSetTypes.COCONUT, BlockBehaviour.Properties.of().mapColor(COCONUT_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Block COCONUT_SIGN = register("coconut_sign", new FOTStandingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), FOTWoodTypes.COCONUT));
    public static final Block COCONUT_WALL_SIGN = register("coconut_wall_sign", new FOTWallSignBlock(BlockBehaviour.Properties.of().dropsLike(COCONUT_SIGN).mapColor(MapColor.COLOR_ORANGE).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), FOTWoodTypes.COCONUT));
    public static final Block COCONUT_HANGING_SIGN = register("coconut_hanging_sign", new FOTCeilingHangingSignBlock(BlockBehaviour.Properties.of().mapColor(COCONUT_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), FOTWoodTypes.COCONUT));
    public static final Block COCONUT_WALL_HANGING_SIGN = register("coconut_wall_hanging_sign", new FOTWallHangingSignBlock(BlockBehaviour.Properties.of().dropsLike(COCONUT_HANGING_SIGN).mapColor(COCONUT_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), FOTWoodTypes.COCONUT));
    public static final Block COCONUT_FISH_PLAQUE = register("coconut_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(COCONUT_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN));
    public static final Block IRON_FRAME_COCONUT_FISH_PLAQUE = register("iron_frame_coconut_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(COCONUT_FISH_PLAQUE), FishPlaqueBlock.Type.IRON));
    public static final Block GOLDEN_FRAME_COCONUT_FISH_PLAQUE = register("golden_frame_coconut_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(COCONUT_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN));
    public static final Block GILDED_COCONUT_FISH_PLAQUE = register("gilded_coconut_fish_plaque", new FishPlaqueBlock(BlockBehaviour.Properties.ofLegacyCopy(COCONUT_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Block");
    }

    private static Block register(String key, Block block)
    {
        return Registry.register(BuiltInRegistries.BLOCK, FishOfThieves.id(key), block);
    }

    private static FOTRotatedPillarBlock log(MapColor topMapColor, MapColor sideMapColor)
    {
        return new FOTRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    }

    public static FlowerPotBlock flowerPot(Block content)
    {
        var properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        return new FlowerPotBlock(content, properties);
    }

    private static ButtonBlock woodenButton(BlockSetType setType)
    {
        return new ButtonBlock(setType, 30, BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY));
    }

    public static Block getPlumeria(Holder<MobEffect> effect)
    {
        return new FlowerBlock(effect, 5, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.CHERRY_LEAVES).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY));
    }

    private static boolean ocelotOrParrot(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entity)
    {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos)
    {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entity)
    {
        return false;
    }

    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos)
    {
        return true;
    }
}