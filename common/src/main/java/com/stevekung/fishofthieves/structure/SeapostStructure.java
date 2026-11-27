package com.stevekung.fishofthieves.structure;

import java.util.Optional;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.registry.FOTStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class SeapostStructure extends Structure
{
    public static final MapCodec<SeapostStructure> CODEC = simpleCodec(SeapostStructure::new);

    public SeapostStructure(Structure.StructureSettings structureSettings)
    {
        super(structureSettings);
    }

    @Override
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context)
    {
        return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR_WG, builder -> this.generatePieces(builder, context));
    }

    private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context)
    {
        var rotation = Rotation.getRandom(context.random());
        var blockPos = new BlockPos(context.chunkPos().getMinBlockX(), context.chunkGenerator().getSeaLevel() - 4, context.chunkPos().getMinBlockZ());
        SeapostPieces.addPieces(context.structureTemplateManager(), blockPos, rotation, builder);
    }

    @Override
    public StructureType<?> type()
    {
        return FOTStructures.Type.SEAPOST;
    }
}