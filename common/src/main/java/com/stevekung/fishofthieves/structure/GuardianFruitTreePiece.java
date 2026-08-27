package com.stevekung.fishofthieves.structure;

import java.util.ArrayList;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
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
    private float fruitChance;

    public GuardianFruitTreePiece(StructureTemplateManager structureTemplateManager, BlockPos templatePosition, Identifier location, Rotation rotation, Mirror mirror, BlockPos pivotPos, float fruitChance)
    {
        super(FOTStructures.PieceType.GUARDIAN_FRUIT_TREE_PIECE, 0, structureTemplateManager, location, location.toString(), makeSettings(mirror, rotation, pivotPos), templatePosition);
        this.fruitChance = fruitChance;
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

    @SuppressWarnings("deprecation")
    private static StructurePlaceSettings makeSettings(StructureTemplateManager structureTemplateManager, CompoundTag tag, Identifier location)
    {
        var structureTemplate = structureTemplateManager.getOrCreate(location);
        var blockPos = new BlockPos(structureTemplate.getSize().getX() / 2, 0, structureTemplate.getSize().getZ() / 2);
        return makeSettings(tag.read("Mirror", Mirror.LEGACY_CODEC).orElseThrow(), tag.read("Rotation", Rotation.LEGACY_CODEC).orElseThrow(), blockPos);
    }

    private static StructurePlaceSettings makeSettings(Mirror mirror, Rotation rotation, BlockPos pos)
    {
        var blockIgnoreProcessor = BlockIgnoreProcessor.STRUCTURE_BLOCK;
        var list = new ArrayList<ProcessorRule>();
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
            if (random.nextFloat() <= this.fruitChance)
            {
                level.setBlock(pos, FOTBlocks.GUARDIAN_FRUIT.defaultBlockState(), Block.UPDATE_CLIENTS);
            }
        }
    }

    private void addPrismarineDripColumnsBelow(RandomSource random, LevelAccessor level)
    {
        for (var x = this.boundingBox.minX() + 1; x < this.boundingBox.maxX(); x++)
        {
            for (var z = this.boundingBox.minZ() + 1; z < this.boundingBox.maxZ(); z++)
            {
                var blockPos = new BlockPos(x, this.boundingBox.minY(), z);

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
        var remainingCap = 8;

        while (remainingCap > 0 && random.nextFloat() < 0.5F)
        {
            mutableBlockPos.move(Direction.DOWN);
            remainingCap--;
            this.placeDecoratedBlocksOrMagma(random, level, mutableBlockPos);
        }
    }

    private void spreadPrismarine(RandomSource random, LevelAccessor level)
    {
        var centerPos = this.boundingBox.getCenter();
        var centerX = centerPos.getX();
        var centerZ = centerPos.getZ();
        var prismarineProbabilityByDistance = new float[] { 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.9F, 0.9F, 0.8F, 0.7F, 0.6F, 0.4F, 0.2F };
        var maxDistance = prismarineProbabilityByDistance.length;
        var averageWidth = (this.boundingBox.getXSpan() + this.boundingBox.getZSpan()) / 2;
        var distanceAdjustment = random.nextInt(Math.max(1, 8 - averageWidth / 2));
        var mutableBlockPos = BlockPos.ZERO.mutable();

        for (var x = centerX - maxDistance; x <= centerX + maxDistance; x++)
        {
            for (var z = centerZ - maxDistance; z <= centerZ + maxDistance; z++)
            {
                var distance = Math.abs(x - centerX) + Math.abs(z - centerZ);
                var adjustedDistance = Math.max(0, distance + distanceAdjustment);

                if (adjustedDistance < maxDistance)
                {
                    var probabilityOfPrismarine = prismarineProbabilityByDistance[adjustedDistance];

                    if (random.nextDouble() < probabilityOfPrismarine)
                    {
                        var surfaceY = getSurfaceY(level, x, z);
                        var y = Math.min(this.boundingBox.minY(), surfaceY);
                        mutableBlockPos.set(x, y, z);

                        if (Math.abs(y - this.boundingBox.minY()) <= 3 && this.canBlockBeReplaced(level, mutableBlockPos))
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