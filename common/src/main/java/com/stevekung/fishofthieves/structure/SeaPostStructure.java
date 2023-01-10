package com.stevekung.fishofthieves.structure;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.registry.FOTStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class SeaPostStructure extends Structure
{
    public static final Codec<SeaPostStructure> CODEC = simpleCodec(SeaPostStructure::new);

    public SeaPostStructure(Structure.StructureSettings structureSettings)
    {
        super(structureSettings);
    }

    @Override
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context)
    {
        return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR_WG, structurePiecesBuilder -> this.generatePieces(structurePiecesBuilder, context));
    }

    private void generatePieces(StructurePiecesBuilder structurePiecesBuilder, Structure.GenerationContext generationContext)
    {
        Rotation rotation = Rotation.getRandom(generationContext.random());
        BlockPos blockPos = new BlockPos(generationContext.chunkPos().getMinBlockX(), generationContext.chunkGenerator().getSeaLevel(), generationContext.chunkPos().getMinBlockZ());
        SeaPostPieces.addPieces(generationContext.structureTemplateManager(), blockPos, rotation, structurePiecesBuilder);
    }

    @Override
    public StructureType<?> type()
    {
        return FOTStructures.Type.SEA_POST;
    }
}