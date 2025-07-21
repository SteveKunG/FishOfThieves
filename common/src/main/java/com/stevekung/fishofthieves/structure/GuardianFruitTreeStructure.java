package com.stevekung.fishofthieves.structure;

import java.util.Optional;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class GuardianFruitTreeStructure extends Structure
{
    private static final String[] GUARDIAN_FRUIT_TREES = IntStream.rangeClosed(1, 5).mapToObj(value -> "guardian_fruit_tree_" + value).toArray(String[]::new);
    public static final Codec<GuardianFruitTreeStructure> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    Codec.floatRange(0.0f, 1.0f).fieldOf("fruit_chance").forGetter(structure -> structure.fruitChance),
                    settingsCodec(instance)
            ).apply(instance, GuardianFruitTreeStructure::new));
    private final float fruitChance;

    public GuardianFruitTreeStructure(float fruitChance, Structure.StructureSettings settings)
    {
        super(settings);
        this.fruitChance = fruitChance;
    }

    @Override
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context)
    {
        var worldgenRandom = context.random();
        var resourceLocation = FishOfThieves.id(GUARDIAN_FRUIT_TREES[worldgenRandom.nextInt(GUARDIAN_FRUIT_TREES.length)]);
        var structureTemplate = context.structureTemplateManager().getOrCreate(resourceLocation);
        var rotation = Rotation.getRandom(worldgenRandom);
        var mirror = worldgenRandom.nextFloat() < 0.5F ? Mirror.NONE : Mirror.FRONT_BACK;
        var blockPos = new BlockPos(structureTemplate.getSize().getX() / 2, 0, structureTemplate.getSize().getZ() / 2);
        var chunkGenerator = context.chunkGenerator();
        var levelHeightAccessor = context.heightAccessor();
        var randomState = context.randomState();
        var blockPos2 = context.chunkPos().getWorldPosition();
        var boundingBox = structureTemplate.getBoundingBox(blockPos2, rotation, blockPos, mirror);
        var blockPos3 = boundingBox.getCenter();
        var height = chunkGenerator.getBaseHeight(blockPos3.getX(), blockPos3.getZ(), Heightmap.Types.OCEAN_FLOOR_WG, levelHeightAccessor, randomState) - 1;
        var y = findSuitableY(chunkGenerator, height, boundingBox, levelHeightAccessor, randomState);
        var blockPos4 = new BlockPos(blockPos2.getX(), y, blockPos2.getZ());
        return Optional.of(new Structure.GenerationStub(blockPos4, structurePiecesBuilder -> structurePiecesBuilder.addPiece(new GuardianFruitTreePiece(context.structureTemplateManager(), blockPos4, resourceLocation, rotation, mirror, blockPos, this.fruitChance))));
    }

    private static int findSuitableY(ChunkGenerator chunkGenerator, int height, BoundingBox box, LevelHeightAccessor level, RandomState randomState)
    {
        var i = level.getMinBuildHeight() + 15;
        var list = ImmutableList.of(new BlockPos(box.minX(), 0, box.minZ()), new BlockPos(box.maxX(), 0, box.minZ()), new BlockPos(box.minX(), 0, box.maxZ()), new BlockPos(box.maxX(), 0, box.maxZ()));
        var list2 = list.stream().map(blockPos -> chunkGenerator.getBaseColumn(blockPos.getX(), blockPos.getZ(), level, randomState)).toList();
        int l;

        for (l = height; l > i; l--)
        {
            var m = 0;

            for (var noiseColumn : list2)
            {
                var blockState = noiseColumn.getBlock(l);

                if (Heightmap.Types.OCEAN_FLOOR_WG.isOpaque().test(blockState))
                {
                    if (++m == 3)
                    {
                        return l;
                    }
                }
            }
        }
        return l;
    }

    @Override
    public StructureType<?> type()
    {
        return FOTStructures.Type.GUARDIAN_FRUIT_TREE;
    }
}