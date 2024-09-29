package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class FOTBlocks
{
    public static final Block FISH_BONE = new FishBoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).pushReaction(PushReaction.DESTROY).strength(0.25f).dynamicShape().offsetType(BlockBehaviour.OffsetType.XYZ).sound(SoundType.BONE_BLOCK));

    public static final Block OAK_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.OAK_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN);
    public static final Block SPRUCE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.SPRUCE_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN);
    public static final Block BIRCH_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.BIRCH_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN);
    public static final Block JUNGLE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.JUNGLE_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN);
    public static final Block ACACIA_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.ACACIA_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN);
    public static final Block DARK_OAK_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.DARK_OAK_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN);
    public static final Block MANGROVE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.MANGROVE_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), FishPlaqueBlock.Type.WOODEN);
    public static final Block CHERRY_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.CHERRY_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD), FishPlaqueBlock.Type.WOODEN);
    public static final Block BAMBOO_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.BAMBOO_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.BAMBOO_WOOD), FishPlaqueBlock.Type.WOODEN);
    public static final Block CRIMSON_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.CRIMSON_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.NETHER_WOOD), FishPlaqueBlock.Type.WOODEN);
    public static final Block WARPED_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of().mapColor(Blocks.WARPED_PLANKS.defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.NETHER_WOOD), FishPlaqueBlock.Type.WOODEN);

    public static final Block IRON_FRAME_OAK_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(OAK_FISH_PLAQUE), FishPlaqueBlock.Type.IRON);
    public static final Block IRON_FRAME_SPRUCE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(SPRUCE_FISH_PLAQUE), FishPlaqueBlock.Type.IRON);
    public static final Block IRON_FRAME_BIRCH_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(BIRCH_FISH_PLAQUE), FishPlaqueBlock.Type.IRON);
    public static final Block IRON_FRAME_JUNGLE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(JUNGLE_FISH_PLAQUE), FishPlaqueBlock.Type.IRON);
    public static final Block IRON_FRAME_ACACIA_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(ACACIA_FISH_PLAQUE), FishPlaqueBlock.Type.IRON);
    public static final Block IRON_FRAME_DARK_OAK_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(DARK_OAK_FISH_PLAQUE), FishPlaqueBlock.Type.IRON);
    public static final Block IRON_FRAME_MANGROVE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(MANGROVE_FISH_PLAQUE), FishPlaqueBlock.Type.IRON);
    public static final Block IRON_FRAME_CHERRY_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(CHERRY_FISH_PLAQUE), FishPlaqueBlock.Type.IRON);
    public static final Block IRON_FRAME_BAMBOO_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(BAMBOO_FISH_PLAQUE), FishPlaqueBlock.Type.IRON);
    public static final Block IRON_FRAME_CRIMSON_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(CRIMSON_FISH_PLAQUE), FishPlaqueBlock.Type.IRON);
    public static final Block IRON_FRAME_WARPED_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(WARPED_FISH_PLAQUE), FishPlaqueBlock.Type.IRON);

    public static final Block GOLDEN_FRAME_OAK_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(OAK_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN);
    public static final Block GOLDEN_FRAME_SPRUCE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(SPRUCE_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN);
    public static final Block GOLDEN_FRAME_BIRCH_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(BIRCH_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN);
    public static final Block GOLDEN_FRAME_JUNGLE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(JUNGLE_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN);
    public static final Block GOLDEN_FRAME_ACACIA_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(ACACIA_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN);
    public static final Block GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(DARK_OAK_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN);
    public static final Block GOLDEN_FRAME_MANGROVE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(MANGROVE_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN);
    public static final Block GOLDEN_FRAME_CHERRY_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(CHERRY_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN);
    public static final Block GOLDEN_FRAME_BAMBOO_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(BAMBOO_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN);
    public static final Block GOLDEN_FRAME_CRIMSON_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(CRIMSON_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN);
    public static final Block GOLDEN_FRAME_WARPED_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(WARPED_FISH_PLAQUE), FishPlaqueBlock.Type.GOLDEN);

    public static final Block GILDED_OAK_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(OAK_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED);
    public static final Block GILDED_SPRUCE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(SPRUCE_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED);
    public static final Block GILDED_BIRCH_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(BIRCH_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED);
    public static final Block GILDED_JUNGLE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(JUNGLE_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED);
    public static final Block GILDED_ACACIA_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(ACACIA_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED);
    public static final Block GILDED_DARK_OAK_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(DARK_OAK_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED);
    public static final Block GILDED_MANGROVE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(MANGROVE_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED);
    public static final Block GILDED_CHERRY_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(CHERRY_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED);
    public static final Block GILDED_BAMBOO_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(BAMBOO_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED);
    public static final Block GILDED_CRIMSON_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(CRIMSON_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED);
    public static final Block GILDED_WARPED_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.copy(WARPED_FISH_PLAQUE), FishPlaqueBlock.Type.GILDED);

    public static final Block PINK_PLUMERIA = new FlowerBlock(MobEffects.REGENERATION, 5, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.CHERRY_LEAVES).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_PINK_PLUMERIA = flowerPot(PINK_PLUMERIA);

    public static final Block SMALL_COCONUT_LOG = new CoconutGrowableLogBlock(BlockBehaviour.Properties.of().mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.STONE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block SMALL_COCONUT_WOOD = new SmallRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block MEDIUM_COCONUT_LOG = new MediumRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.STONE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block MEDIUM_COCONUT_WOOD = new MediumRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block COCONUT_LOG = log(MapColor.COLOR_ORANGE, MapColor.STONE);
    public static final Block COCONUT_WOOD = new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block COCONUT_SAPLING = new CoconutSaplingBlock(new OakTreeGrower(), BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.FLOWERING_AZALEA).pushReaction(PushReaction.DESTROY));
    public static final Block COCONUT_FRUIT = new CoconutFruitBlock(BlockBehaviour.Properties.of().noOcclusion().sound(SoundType.WOOD).strength(1.0F).pushReaction(PushReaction.DESTROY));
    public static final Block COCONUT_FRONDS = new CoconutFrondsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().ignitedByLava().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block BANANA_STEM = new BananaStemBlock(BlockBehaviour.Properties.of().mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_GREEN : MapColor.COLOR_BROWN).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(1.5F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block BANANA_LEAVES = new BananaLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().ignitedByLava().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block VERTICAL_BANANA_LEAVES = new VerticalBananaLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).ignitedByLava().noCollission().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));
    public static final Block VERTICAL_COCONUT_FRONDS = new VerticalCoconutFrondsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).ignitedByLava().noCollission().noOcclusion().instabreak().sound(SoundType.CHERRY_LEAVES).isSuffocating(FOTBlocks::never).isViewBlocking(FOTBlocks::never).isRedstoneConductor(FOTBlocks::never).isValidSpawn(FOTBlocks::never).pushReaction(PushReaction.DESTROY));

    public static void init()
    {
        register("fish_bone", FISH_BONE);

        register("oak_fish_plaque", OAK_FISH_PLAQUE);
        register("spruce_fish_plaque", SPRUCE_FISH_PLAQUE);
        register("birch_fish_plaque", BIRCH_FISH_PLAQUE);
        register("jungle_fish_plaque", JUNGLE_FISH_PLAQUE);
        register("acacia_fish_plaque", ACACIA_FISH_PLAQUE);
        register("dark_oak_fish_plaque", DARK_OAK_FISH_PLAQUE);
        register("mangrove_fish_plaque", MANGROVE_FISH_PLAQUE);
        register("cherry_fish_plaque", CHERRY_FISH_PLAQUE);
        register("bamboo_fish_plaque", BAMBOO_FISH_PLAQUE);
        register("crimson_fish_plaque", CRIMSON_FISH_PLAQUE);
        register("warped_fish_plaque", WARPED_FISH_PLAQUE);

        register("iron_frame_oak_fish_plaque", IRON_FRAME_OAK_FISH_PLAQUE);
        register("iron_frame_spruce_fish_plaque", IRON_FRAME_SPRUCE_FISH_PLAQUE);
        register("iron_frame_birch_fish_plaque", IRON_FRAME_BIRCH_FISH_PLAQUE);
        register("iron_frame_jungle_fish_plaque", IRON_FRAME_JUNGLE_FISH_PLAQUE);
        register("iron_frame_acacia_fish_plaque", IRON_FRAME_ACACIA_FISH_PLAQUE);
        register("iron_frame_dark_oak_fish_plaque", IRON_FRAME_DARK_OAK_FISH_PLAQUE);
        register("iron_frame_mangrove_fish_plaque", IRON_FRAME_MANGROVE_FISH_PLAQUE);
        register("iron_frame_cherry_fish_plaque", IRON_FRAME_CHERRY_FISH_PLAQUE);
        register("iron_frame_bamboo_fish_plaque", IRON_FRAME_BAMBOO_FISH_PLAQUE);
        register("iron_frame_crimson_fish_plaque", IRON_FRAME_CRIMSON_FISH_PLAQUE);
        register("iron_frame_warped_fish_plaque", IRON_FRAME_WARPED_FISH_PLAQUE);

        register("golden_frame_oak_fish_plaque", GOLDEN_FRAME_OAK_FISH_PLAQUE);
        register("golden_frame_spruce_fish_plaque", GOLDEN_FRAME_SPRUCE_FISH_PLAQUE);
        register("golden_frame_birch_fish_plaque", GOLDEN_FRAME_BIRCH_FISH_PLAQUE);
        register("golden_frame_jungle_fish_plaque", GOLDEN_FRAME_JUNGLE_FISH_PLAQUE);
        register("golden_frame_acacia_fish_plaque", GOLDEN_FRAME_ACACIA_FISH_PLAQUE);
        register("golden_frame_dark_oak_fish_plaque", GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE);
        register("golden_frame_mangrove_fish_plaque", GOLDEN_FRAME_MANGROVE_FISH_PLAQUE);
        register("golden_frame_cherry_fish_plaque", GOLDEN_FRAME_CHERRY_FISH_PLAQUE);
        register("golden_frame_bamboo_fish_plaque", GOLDEN_FRAME_BAMBOO_FISH_PLAQUE);
        register("golden_frame_crimson_fish_plaque", GOLDEN_FRAME_CRIMSON_FISH_PLAQUE);
        register("golden_frame_warped_fish_plaque", GOLDEN_FRAME_WARPED_FISH_PLAQUE);

        register("gilded_oak_fish_plaque", GILDED_OAK_FISH_PLAQUE);
        register("gilded_spruce_fish_plaque", GILDED_SPRUCE_FISH_PLAQUE);
        register("gilded_birch_fish_plaque", GILDED_BIRCH_FISH_PLAQUE);
        register("gilded_jungle_fish_plaque", GILDED_JUNGLE_FISH_PLAQUE);
        register("gilded_acacia_fish_plaque", GILDED_ACACIA_FISH_PLAQUE);
        register("gilded_dark_oak_fish_plaque", GILDED_DARK_OAK_FISH_PLAQUE);
        register("gilded_mangrove_fish_plaque", GILDED_MANGROVE_FISH_PLAQUE);
        register("gilded_cherry_fish_plaque", GILDED_CHERRY_FISH_PLAQUE);
        register("gilded_bamboo_fish_plaque", GILDED_BAMBOO_FISH_PLAQUE);
        register("gilded_crimson_fish_plaque", GILDED_CRIMSON_FISH_PLAQUE);
        register("gilded_warped_fish_plaque", GILDED_WARPED_FISH_PLAQUE);

        register("pink_plumeria", PINK_PLUMERIA);
        register("potted_pink_plumeria", POTTED_PINK_PLUMERIA);

        register("coconut_log", COCONUT_LOG);
        register("coconut_wood", COCONUT_WOOD);
        register("small_coconut_log", SMALL_COCONUT_LOG);
        register("small_coconut_wood", SMALL_COCONUT_WOOD);
        register("medium_coconut_log", MEDIUM_COCONUT_LOG);
        register("medium_coconut_wood", MEDIUM_COCONUT_WOOD);
        registerNoItem("coconut_sapling", COCONUT_SAPLING);
        register("coconut_fruit", COCONUT_FRUIT);
        register("coconut_fronds", COCONUT_FRONDS);
        register("banana_stem", BANANA_STEM);
        register("banana_leaves", BANANA_LEAVES);
        registerNoItem("vertical_banana_leaves", VERTICAL_BANANA_LEAVES);
        registerNoItem("vertical_coconut_fronds", VERTICAL_COCONUT_FRONDS);
    }

    private static void register(String key, Block block)
    {
        FOTPlatform.registerBlockWithItem(key, block);
    }

    private static void registerNoItem(String key, Block block)
    {
        FOTPlatform.registerBlock(key, block);
    }

    private static RotatedPillarBlock log(MapColor topMapColor, MapColor sideMapColor)
    {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    }

    private static FlowerPotBlock flowerPot(Block content)
    {
        var properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        return new FlowerPotBlock(content, properties);
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos)
    {
        return false;
    }

    private static Boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entity)
    {
        return false;
    }
}