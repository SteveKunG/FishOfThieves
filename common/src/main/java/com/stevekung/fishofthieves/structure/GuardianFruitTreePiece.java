package com.stevekung.fishofthieves.structure;

import com.google.common.collect.Lists;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

public class GuardianFruitTreePiece extends TemplateStructurePiece
{
    public GuardianFruitTreePiece(StructureTemplateManager structureTemplateManager, BlockPos templatePosition, ResourceLocation location, Rotation rotation, Mirror mirror, BlockPos pivotPos)
    {
        super(FOTStructures.PieceType.GUARDIAN_FRUIT_TREE_PIECE, 0, structureTemplateManager, location, location.toString(), makeSettings(mirror, rotation, pivotPos), templatePosition);
    }

    public GuardianFruitTreePiece(StructureTemplateManager structureTemplateManager, CompoundTag tag)
    {
        super(FOTStructures.PieceType.GUARDIAN_FRUIT_TREE_PIECE, tag, structureTemplateManager, resourceLocation -> makeSettings(structureTemplateManager, tag, resourceLocation));
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag)
    {
        super.addAdditionalSaveData(context, tag);
        tag.putString("Rotation", this.placeSettings.getRotation().name());
        tag.putString("Mirror", this.placeSettings.getMirror().name());
    }

    private static StructurePlaceSettings makeSettings(StructureTemplateManager structureTemplateManager, CompoundTag tag, ResourceLocation location)
    {
        var structureTemplate = structureTemplateManager.getOrCreate(location);
        var blockPos = new BlockPos(structureTemplate.getSize().getX() / 2, 0, structureTemplate.getSize().getZ() / 2);
        return makeSettings(Mirror.valueOf(tag.getString("Mirror")), Rotation.valueOf(tag.getString("Rotation")), blockPos);
    }

    private static StructurePlaceSettings makeSettings(Mirror mirror, Rotation rotation, BlockPos pos)
    {
        var blockIgnoreProcessor = BlockIgnoreProcessor.STRUCTURE_BLOCK;
        var list = Lists.<ProcessorRule>newArrayList();
        list.add(getBlockReplaceRule(Blocks.PRISMARINE, 0.1F, FOTBlocks.PRISMARIZED_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z)));
        list.add(getBlockReplaceRule(Blocks.PRISMARINE, 0.1F, FOTBlocks.PRISMARIZED_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)));
        list.add(getBlockReplaceRule(Blocks.PRISMARINE, 0.03F, Blocks.MAGMA_BLOCK));
        return new StructurePlaceSettings().setRotation(rotation).setMirror(mirror).setRotationPivot(pos)
                .addProcessor(blockIgnoreProcessor)
                .addProcessor(new RuleProcessor(list))
                .addProcessor(new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos)
    {
        var boundingBox = this.template.getBoundingBox(this.placeSettings, this.templatePosition);

        if (box.isInside(boundingBox.getCenter()))
        {
            box.encapsulate(boundingBox);
            super.postProcess(level, structureManager, generator, random, box, chunkPos, pos);
            this.spreadPrismarine(random, level);
            this.addPrismarineDripColumnsBelow(random, level);
        }
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box)
    {
        if (name.equals("guardian_fruit_block"))
        {
            level.setBlock(pos, FOTBlocks.RIPE_PINEAPPLE_BLOCK.defaultBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    private void addPrismarineDripColumnsBelow(RandomSource random, LevelAccessor level)
    {
        for (var i = this.boundingBox.minX() + 1; i < this.boundingBox.maxX(); i++)
        {
            for (var j = this.boundingBox.minZ() + 1; j < this.boundingBox.maxZ(); j++)
            {
                var blockPos = new BlockPos(i, this.boundingBox.minY(), j);

                if (level.getBlockState(blockPos).is(Blocks.PRISMARINE))
                {
                    this.addPrismarineDripColumn(random, level, blockPos.below());
                }
            }
        }
    }

    private void addPrismarineDripColumn(RandomSource random, LevelAccessor level, BlockPos pos)
    {
        var mutableBlockPos = pos.mutable();
        this.placeDecoratedBlocksOrMagma(random, level, mutableBlockPos);
        var i = 8;

        while (i > 0 && random.nextFloat() < 0.5F)
        {
            mutableBlockPos.move(Direction.DOWN);
            i--;
            this.placeDecoratedBlocksOrMagma(random, level, mutableBlockPos);
        }
    }

    private void spreadPrismarine(RandomSource random, LevelAccessor level)
    {
        var blockPos = this.boundingBox.getCenter();
        var i = blockPos.getX();
        var j = blockPos.getZ();
        var fs = new float[] { 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.9F, 0.9F, 0.8F, 0.7F, 0.6F, 0.4F, 0.2F };
        var k = fs.length;
        var l = (this.boundingBox.getXSpan() + this.boundingBox.getZSpan()) / 2;
        var m = random.nextInt(Math.max(1, 8 - l / 2));
        var mutableBlockPos = BlockPos.ZERO.mutable();

        for (var o = i - k; o <= i + k; o++)
        {
            for (var p = j - k; p <= j + k; p++)
            {
                var q = Math.abs(o - i) + Math.abs(p - j);
                var r = Math.max(0, q + m);

                if (r < k)
                {
                    var f = fs[r];

                    if (random.nextDouble() < (double) f)
                    {
                        var s = getSurfaceY(level, o, p);
                        var t = Math.min(this.boundingBox.minY(), s);
                        mutableBlockPos.set(o, t, p);

                        if (Math.abs(t - this.boundingBox.minY()) <= 3 && this.canBlockBeReplaced(level, mutableBlockPos))
                        {
                            this.placeDecoratedBlocksOrMagma(random, level, mutableBlockPos);
                            this.addPrismarineDripColumn(random, level, mutableBlockPos.below());
                        }
                    }
                }
            }
        }
    }

    private boolean canBlockBeReplaced(LevelAccessor level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        return !blockState.is(Blocks.AIR) && !blockState.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }

    private void placeDecoratedBlocksOrMagma(RandomSource random, LevelAccessor level, BlockPos pos)
    {
        if (random.nextFloat() < 0.03F)
        {
            level.setBlock(pos, Blocks.MAGMA_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
        }
        else if (random.nextFloat() < 0.1F)
        {
            level.setBlock(pos, FOTBlocks.PRISMARIZED_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z), Block.UPDATE_ALL);
        }
        else
        {
            level.setBlock(pos, Blocks.PRISMARINE.defaultBlockState(), Block.UPDATE_ALL);
        }
    }

    private static int getSurfaceY(LevelAccessor level, int x, int z)
    {
        return level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z) - 1;
    }

    private static ProcessorRule getBlockReplaceRule(Block block, float probability, Block replaceBlock)
    {
        return new ProcessorRule(new RandomBlockMatchTest(block, probability), AlwaysTrueTest.INSTANCE, replaceBlock.defaultBlockState());
    }

    private static ProcessorRule getBlockReplaceRule(Block block, float probability, BlockState blockState)
    {
        return new ProcessorRule(new RandomBlockMatchTest(block, probability), AlwaysTrueTest.INSTANCE, blockState);
    }
}