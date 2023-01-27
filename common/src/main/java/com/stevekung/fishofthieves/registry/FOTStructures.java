package com.stevekung.fishofthieves.registry;

import java.util.List;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.structure.SeapostPieces;
import com.stevekung.fishofthieves.structure.SeapostStructure;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class FOTStructures
{
    public static final Holder<Structure> SEAPOST = register(Key.SEAPOST, new SeapostStructure(Structures.structure(FOTTags.Biomes.HAS_SEAPOST, TerrainAdjustment.BEARD_THIN)));

    public static void init()
    {
        Type.init();
        Sets.init();
        PieceType.init();
    }

    private static Holder<Structure> register(ResourceKey<Structure> key, Structure structure)
    {
        return BuiltinRegistries.register(BuiltinRegistries.STRUCTURES, key, structure);
    }

    public interface Sets
    {
        static void init() {}

        Holder<StructureSet> SEAPOSTS = StructureSets.register(Key.SEAPOSTS, new StructureSet(List.of(StructureSet.entry(FOTStructures.SEAPOST)), new RandomSpreadStructurePlacement(32, 16, RandomSpreadType.LINEAR, 26384127)));
    }

    public interface Type
    {
        static void init() {}

        StructureType<SeapostStructure> SEAPOST = register("seapost", SeapostStructure.CODEC);

        private static <S extends Structure> StructureType<S> register(String name, Codec<S> codec)
        {
            return Registry.register(Registry.STRUCTURE_TYPES, new ResourceLocation(FishOfThieves.MOD_ID, name), () -> codec);
        }
    }

    public interface Key
    {
        ResourceKey<Structure> SEAPOST = registerStructure("seapost");
        ResourceKey<StructureSet> SEAPOSTS = registerStructureSet("seapost");

        private static ResourceKey<Structure> registerStructure(String name)
        {
            return ResourceKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, name));
        }

        private static ResourceKey<StructureSet> registerStructureSet(String name)
        {
            return ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, name));
        }
    }

    public interface PieceType
    {
        static void init() {}

        StructurePieceType SEAPOST_PIECE = setFullContextPieceId(SeapostPieces.SeapostPiece::new, "seapost");

        private static StructurePieceType setFullContextPieceId(StructurePieceType.StructureTemplateType pieceType, String pieceId)
        {
            return Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(FishOfThieves.MOD_ID, pieceId), pieceType);
        }
    }
}