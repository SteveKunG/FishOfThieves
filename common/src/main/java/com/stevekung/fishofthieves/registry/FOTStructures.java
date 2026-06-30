package com.stevekung.fishofthieves.registry;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.structure.GuardianFruitTreePiece;
import com.stevekung.fishofthieves.structure.GuardianFruitTreeStructure;
import com.stevekung.fishofthieves.structure.SeapostPieces;
import com.stevekung.fishofthieves.structure.SeapostStructure;

import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.placement.AbstractSpreadingStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class FOTStructures
{
    public static void bootstrap(BootstrapContext<Structure> context)
    {
        var holderGetter = context.lookup(Registries.BIOME);
        context.register(Key.SEAPOST, new SeapostStructure(new Structure.StructureSettings.Builder(holderGetter.getOrThrow(FOTTags.Biomes.HAS_SEAPOST)).terrainAdapation(TerrainAdjustment.BEARD_THIN).build()));
        context.register(Key.GUARDIAN_FRUIT_TREE, new GuardianFruitTreeStructure(0.85f, new Structure.StructureSettings.Builder(holderGetter.getOrThrow(BiomeTags.IS_DEEP_OCEAN)).terrainAdapation(TerrainAdjustment.BEARD_THIN).build()));
    }

    public static void init()
    {
        Type.init();
        PieceType.init();
    }

    public interface Sets
    {
        @SuppressWarnings("deprecation")
        static void bootstrap(BootstrapContext<StructureSet> context)
        {
            var holderGetter = context.lookup(Registries.STRUCTURE);
            var structureSetLookup = context.lookup(Registries.STRUCTURE_SET);
            context.register(Key.SEAPOSTS, new StructureSet(List.of(StructureSet.entry(holderGetter.getOrThrow(Key.SEAPOST))), new RandomSpreadStructurePlacement(Vec3i.ZERO, AbstractSpreadingStructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_3, 0.6f, 26384127, Optional.of(new AbstractSpreadingStructurePlacement.ExclusionZone(structureSetLookup.getOrThrow(BuiltinStructureSets.OCEAN_MONUMENTS), 8)), 64, 32, RandomSpreadType.LINEAR)));
            context.register(Key.GUARDIAN_FRUIT_TREES, new StructureSet(List.of(StructureSet.entry(holderGetter.getOrThrow(Key.GUARDIAN_FRUIT_TREE))), new RandomSpreadStructurePlacement(Vec3i.ZERO, AbstractSpreadingStructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_3, 0.8f, 91579157, Optional.empty(), 64, 16, RandomSpreadType.LINEAR)));
        }
    }

    public interface Type
    {
        static void init()
        {
            FishOfThieves.LOGGER.info("Registering Structure Type");
        }

        StructureType<SeapostStructure> SEAPOST = register("seapost", SeapostStructure.CODEC);
        StructureType<GuardianFruitTreeStructure> GUARDIAN_FRUIT_TREE = register("guardian_fruit_tree", GuardianFruitTreeStructure.CODEC);

        private static <S extends Structure> StructureType<S> register(String name, MapCodec<S> codec)
        {
            return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, FishOfThieves.id(name), () -> codec);
        }
    }

    public interface Key
    {
        ResourceKey<Structure> SEAPOST = registerStructure("seapost");
        ResourceKey<Structure> GUARDIAN_FRUIT_TREE = registerStructure("guardian_fruit_tree");
        ResourceKey<StructureSet> SEAPOSTS = registerStructureSet("seapost");
        ResourceKey<StructureSet> GUARDIAN_FRUIT_TREES = registerStructureSet("guardian_fruit_trees");

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
        StructurePieceType GUARDIAN_FRUIT_TREE_PIECE = setFullContextPieceId("guardian_fruit_tree", GuardianFruitTreePiece::new);

        private static StructurePieceType register(String pieceId, StructurePieceType.StructureTemplateType pieceType)
        {
            return Registry.register(BuiltInRegistries.STRUCTURE_PIECE, FishOfThieves.id(pieceId), pieceType);
        }

        private static StructurePieceType setFullContextPieceId(String pieceId, StructurePieceType pieceType)
        {
            return Registry.register(BuiltInRegistries.STRUCTURE_PIECE, FishOfThieves.id(pieceId), pieceType);
        }
    }
}