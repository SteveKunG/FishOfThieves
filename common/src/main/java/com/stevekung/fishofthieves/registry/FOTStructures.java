package com.stevekung.fishofthieves.registry;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.structure.GuardianFruitTreePiece;
import com.stevekung.fishofthieves.structure.GuardianFruitTreeStructure;
import com.stevekung.fishofthieves.structure.SeapostPieces;
import com.stevekung.fishofthieves.structure.SeapostStructure;

import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public class FOTStructures
{
    public static void bootstrap(BootstapContext<Structure> context)
    {
        var holderGetter = context.lookup(Registries.BIOME);
        context.register(Key.SEAPOST, new SeapostStructure(Structures.structure(holderGetter.getOrThrow(FOTTags.Biomes.HAS_SEAPOST), TerrainAdjustment.BEARD_THIN)));
        context.register(Key.GUARDIAN_FRUIT_TREE, new GuardianFruitTreeStructure(0.85f, Structures.structure(holderGetter.getOrThrow(BiomeTags.IS_DEEP_OCEAN), TerrainAdjustment.BEARD_THIN)));
    }

    public static void init()
    {
        Type.init();
        PieceType.init();
    }

    public interface Sets
    {
        @SuppressWarnings("deprecation")
        static void bootstrap(BootstapContext<StructureSet> context)
        {
            var holderGetter = context.lookup(Registries.STRUCTURE);
            var structureSetLookup = context.lookup(Registries.STRUCTURE_SET);
            context.register(Key.SEAPOSTS, new StructureSet(List.of(StructureSet.entry(holderGetter.getOrThrow(Key.SEAPOST))), new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_3, 0.6f, 26384127, Optional.of(new StructurePlacement.ExclusionZone(structureSetLookup.getOrThrow(BuiltinStructureSets.OCEAN_MONUMENTS), 8)), 64, 32, RandomSpreadType.LINEAR)));
            context.register(Key.GUARDIAN_FRUIT_TREES, new StructureSet(List.of(StructureSet.entry(holderGetter.getOrThrow(Key.GUARDIAN_FRUIT_TREE))), new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_3, 0.8f, 91579157, Optional.empty(), 64, 16, RandomSpreadType.LINEAR)));
        }
    }

    public interface Type
    {
        static void init() {}

        StructureType<SeapostStructure> SEAPOST = register("seapost", SeapostStructure.CODEC);
        StructureType<GuardianFruitTreeStructure> GUARDIAN_FRUIT_TREE = register("guardian_fruit_tree", GuardianFruitTreeStructure.CODEC);

        private static <S extends Structure> StructureType<S> register(String name, Codec<S> codec)
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
        static void init() {}

        StructurePieceType SEAPOST_PIECE = setFullContextPieceId(SeapostPieces.SeapostPiece::new, "seapost");
        StructurePieceType GUARDIAN_FRUIT_TREE_PIECE = setFullContextPieceId(GuardianFruitTreePiece::new, "guardian_fruit_tree");

        private static StructurePieceType setFullContextPieceId(StructurePieceType.StructureTemplateType pieceType, String pieceId)
        {
            return Registry.register(BuiltInRegistries.STRUCTURE_PIECE, FishOfThieves.id(pieceId), pieceType);
        }
    }
}