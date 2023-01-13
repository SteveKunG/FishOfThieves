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

public class SeapostStructure extends Structure
{
    public static final Codec<SeapostStructure> CODEC = simpleCodec(SeapostStructure::new);

    public SeapostStructure(Structure.StructureSettings structureSettings)
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
        BlockPos blockPos = new BlockPos(generationContext.chunkPos().getMinBlockX(), generationContext.chunkGenerator().getSeaLevel() - 4, generationContext.chunkPos().getMinBlockZ());
        SeapostPieces.addPieces(generationContext.structureTemplateManager(), blockPos, rotation, structurePiecesBuilder);
    }

    @Override
    public StructureType<?> type()
    {
        return FOTStructures.Type.SEAPOST;
    }
}