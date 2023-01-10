package com.stevekung.fishofthieves.structure;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class SeaPostPieces
{
    static final BlockPos PIVOT = new BlockPos(-5, 0, 10);
    private static final ResourceLocation SEA_POST = new ResourceLocation(FishOfThieves.MOD_ID, "sea_post");

    public static void addPieces(StructureTemplateManager structureTemplateManager, BlockPos pos, Rotation rotation, StructurePieceAccessor pieces)
    {
        pieces.addPiece(new SeaPostPiece(structureTemplateManager, SEA_POST, pos, rotation));
    }

    public static class SeaPostPiece extends TemplateStructurePiece
    {
        public SeaPostPiece(StructureTemplateManager structureTemplateManager, ResourceLocation resourceLocation, BlockPos blockPos, Rotation rotation)
        {
            super(FOTStructures.PieceType.SEA_POST_PIECE, 0, structureTemplateManager, resourceLocation, resourceLocation.toString(), makeSettings(rotation), blockPos);
        }

        public SeaPostPiece(StructureTemplateManager structureTemplateManager, CompoundTag compoundTag)
        {
            super(FOTStructures.PieceType.SEA_POST_PIECE, compoundTag, structureTemplateManager, resourceLocation -> makeSettings(Rotation.valueOf(compoundTag.getString("Rot"))));
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag)
        {
            super.addAdditionalSaveData(context, tag);
            tag.putString("Rot", this.placeSettings.getRotation().name());
        }

        private static StructurePlaceSettings makeSettings(Rotation rotation)
        {
            return new StructurePlaceSettings().setRotation(rotation).setMirror(Mirror.NONE).setRotationPivot(SeaPostPieces.PIVOT).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box)
        {
            switch (name)
            {
                case "sea_post_barrel" ->
                        RandomizableContainerBlockEntity.setLootTable(level, random, pos.below(), BuiltInLootTables.SHIPWRECK_SUPPLY);
                case "sea_post_leather_worker" ->
                {
                    var villager = EntityType.VILLAGER.create(level.getLevel());
                    villager.setVillagerData(new VillagerData(VillagerType.PLAINS, VillagerProfession.LEATHERWORKER, 3));
                    villager.setPersistenceRequired();
                    villager.moveTo(pos, 0.0F, 0.0F);
                    villager.finalizeSpawn(level, level.getCurrentDifficultyAt(villager.blockPosition()), MobSpawnType.STRUCTURE, null, null);
                    level.addFreshEntityWithPassengers(villager);
                    level.setBlock(pos, Blocks.SPRUCE_TRAPDOOR.defaultBlockState().setValue(TrapDoorBlock.FACING, this.placeSettings.getRotation().rotate(Direction.NORTH)).setValue(TrapDoorBlock.HALF, Half.TOP).setValue(TrapDoorBlock.OPEN, true), Block.UPDATE_CLIENTS);
                }
                case "sea_post_villager" ->
                {
                    //TODO New villager profession
                    var villager = EntityType.VILLAGER.create(level.getLevel());
                    villager.setVillagerData(new VillagerData(VillagerType.PLAINS, VillagerProfession.FISHERMAN, 3));
                    villager.setPersistenceRequired();
                    villager.moveTo(pos, 0.0F, 0.0F);
                    villager.finalizeSpawn(level, level.getCurrentDifficultyAt(villager.blockPosition()), MobSpawnType.STRUCTURE, null, null);
                    level.addFreshEntityWithPassengers(villager);
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                }
            }
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos)
        {
            this.templatePosition = new BlockPos(this.templatePosition.getX(), generator.getSeaLevel(), this.templatePosition.getZ());
            super.postProcess(level, structureManager, generator, random, box, chunkPos, pos);
        }
    }
}