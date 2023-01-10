package com.stevekung.fishofthieves.registry;

import java.util.List;
import java.util.Locale;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.structure.SeaPostPieces;
import com.stevekung.fishofthieves.structure.SeaPostStructure;
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
    public static final Holder<Structure> SEA_POST = register(Key.SEA_POST, new SeaPostStructure(Structures.structure(FOTTags.Biomes.HAS_SEA_POST, TerrainAdjustment.BEARD_BOX)));

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
        Holder<StructureSet> SEA_POSTS = StructureSets.register(Key.SEA_POSTS, new StructureSet(List.of(StructureSet.entry(FOTStructures.SEA_POST)), new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 165745269)));
    }

    public interface Type
    {
        static void init() {}

        StructureType<SeaPostStructure> SEA_POST = register("sea_post", SeaPostStructure.CODEC);

        private static <S extends Structure> StructureType<S> register(String name, Codec<S> codec)
        {
            return Registry.register(Registry.STRUCTURE_TYPES, new ResourceLocation(FishOfThieves.MOD_ID, name), () -> codec);
        }
    }

    public interface Key
    {
        ResourceKey<Structure> SEA_POST = registerStructure("sea_post");
        ResourceKey<StructureSet> SEA_POSTS = registerStructureSet("sea_post");

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
        StructurePieceType SEA_POST_PIECE = setFullContextPieceId(SeaPostPieces.SeaPostPiece::new, "SeaPost");

        private static StructurePieceType setFullContextPieceId(StructurePieceType.StructureTemplateType pieceType, String pieceId)
        {
            return Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(FishOfThieves.MOD_ID, pieceId.toLowerCase(Locale.ROOT)), pieceType);
        }
    }
}