package com.stevekung.fishofthieves.registry;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.structure.SeapostPieces;
import com.stevekung.fishofthieves.structure.SeapostStructure;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Structures;
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
        context.register(Key.SEAPOST, new SeapostStructure(Structures.structure(holderGetter.getOrThrow(FOTTags.Biomes.HAS_SEAPOST), TerrainAdjustment.BEARD_THIN)));
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
        static void init() {}

        StructureType<SeapostStructure> SEAPOST = register("seapost", SeapostStructure.CODEC);

        private static <S extends Structure> StructureType<S> register(String name, MapCodec<S> codec)
        {
            return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, FishOfThieves.res(name), () -> codec);
        }
    }

    public interface Key
    {
        ResourceKey<Structure> SEAPOST = registerStructure("seapost");
        ResourceKey<StructureSet> SEAPOSTS = registerStructureSet("seapost");

        private static ResourceKey<Structure> registerStructure(String name)
        {
            return ResourceKey.create(Registries.STRUCTURE, FishOfThieves.res(name));
        }

        private static ResourceKey<StructureSet> registerStructureSet(String name)
        {
            return ResourceKey.create(Registries.STRUCTURE_SET, FishOfThieves.res(name));
        }
    }

    public interface PieceType
    {
        static void init() {}

        StructurePieceType SEAPOST_PIECE = setFullContextPieceId(SeapostPieces.SeapostPiece::new, "seapost");

        private static StructurePieceType setFullContextPieceId(StructurePieceType.StructureTemplateType pieceType, String pieceId)
        {
            return Registry.register(BuiltInRegistries.STRUCTURE_PIECE, FishOfThieves.res(pieceId), pieceType);
        }
    }
}