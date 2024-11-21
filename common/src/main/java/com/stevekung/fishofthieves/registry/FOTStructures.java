package com.stevekung.fishofthieves.registry;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.structure.SeapostPieces;
import com.stevekung.fishofthieves.structure.SeapostStructure;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class FOTStructures
{
    public static void bootstrap(BootstrapContext<Structure> context)
    {
        var holderGetter = context.lookup(Registries.BIOME);
        context.register(Key.SEAPOST, new SeapostStructure(new Structure.StructureSettings.Builder(holderGetter.getOrThrow(FOTTags.Biomes.HAS_SEAPOST)).terrainAdapation(TerrainAdjustment.BEARD_THIN).build()));
    }

    public static void init()
    {
        Type.init();
        PieceType.init();
    }

    public interface Sets
    {
        static void bootstrap(BootstrapContext<StructureSet> context)
        {
            var holderGetter = context.lookup(Registries.STRUCTURE);
            context.register(Key.SEAPOSTS, new StructureSet(List.of(StructureSet.entry(holderGetter.getOrThrow(Key.SEAPOST))), new RandomSpreadStructurePlacement(512, 64, RandomSpreadType.LINEAR, 26384127)));
        }
    }

    public interface Type
    {
        static void init()
        {
            FishOfThieves.LOGGER.info("Registering Structure Type");
        }

        StructureType<SeapostStructure> SEAPOST = register("seapost", SeapostStructure.CODEC);

        private static <S extends Structure> StructureType<S> register(String name, MapCodec<S> codec)
        {
            return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, FishOfThieves.id(name), () -> codec);
        }
    }

    public interface Key
    {
        ResourceKey<Structure> SEAPOST = registerStructure("seapost");
        ResourceKey<StructureSet> SEAPOSTS = registerStructureSet("seapost");

        private static ResourceKey<Structure> registerStructure(String name)
        {
            return ResourceKey.create(Registries.STRUCTURE, FishOfThieves.id(name));
        }

        private static ResourceKey<StructureSet> registerStructureSet(String name)
        {
            return ResourceKey.create(Registries.STRUCTURE_SET, FishOfThieves.id(name));
        }
    }

    public interface PieceType
    {
        static void init()
        {
            FishOfThieves.LOGGER.info("Registering Structure Piece Type");
        }

        StructurePieceType SEAPOST_PIECE = register("seapost", SeapostPieces.SeapostPiece::new);

        private static StructurePieceType register(String pieceId, StructurePieceType.StructureTemplateType pieceType)
        {
            return Registry.register(BuiltInRegistries.STRUCTURE_PIECE, FishOfThieves.id(pieceId), pieceType);
        }
    }
}