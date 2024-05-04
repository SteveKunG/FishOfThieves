package com.stevekung.fishofthieves.structure;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTStructures;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomizableContainer;
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
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class SeapostPieces
{
    private static final ResourceLocation SEAPOST = FishOfThieves.res("seapost");
    private static final ResourceLocation SEAPOST_BASE = FishOfThieves.res("seapost_base");
    private static final Map<ResourceLocation, BlockPos> PIVOTS = ImmutableMap.of(SEAPOST, new BlockPos(-5, 0, 10), SEAPOST_BASE, new BlockPos(-5, 0, 11));
    private static final Map<ResourceLocation, BlockPos> OFFSETS = ImmutableMap.of(SEAPOST, new BlockPos(0, 8, 0), SEAPOST_BASE, BlockPos.ZERO);
    private static final List<Block> POTTED_BLOCKS = ImmutableList.of(Blocks.POTTED_POPPY, Blocks.POTTED_DANDELION, Blocks.POTTED_AZURE_BLUET, Blocks.POTTED_DEAD_BUSH, Blocks.POTTED_MANGROVE_PROPAGULE, Blocks.POTTED_AZALEA, Blocks.POTTED_FLOWERING_AZALEA);

    public static void addPieces(StructureTemplateManager structureTemplateManager, BlockPos pos, Rotation rotation, StructurePieceAccessor pieces)
    {
        pieces.addPiece(new SeapostPiece(structureTemplateManager, SEAPOST, pos, rotation, 8));
        pieces.addPiece(new SeapostPiece(structureTemplateManager, SEAPOST_BASE, pos.below(1), rotation, 0));
    }

    public static class SeapostPiece extends TemplateStructurePiece
    {
        public SeapostPiece(StructureTemplateManager structureTemplateManager, ResourceLocation resourceLocation, BlockPos blockPos, Rotation rotation, int offset)
        {
            super(FOTStructures.PieceType.SEAPOST_PIECE, 0, structureTemplateManager, resourceLocation, resourceLocation.toString(), makeSettings(rotation, resourceLocation), makePosition(resourceLocation, blockPos, offset));
        }

        public SeapostPiece(StructureTemplateManager structureTemplateManager, CompoundTag compoundTag)
        {
            super(FOTStructures.PieceType.SEAPOST_PIECE, compoundTag, structureTemplateManager, resourceLocation -> makeSettings(Rotation.valueOf(compoundTag.getString("Rot")), resourceLocation));
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag)
        {
            super.addAdditionalSaveData(context, tag);
            tag.putString("Rot", this.placeSettings.getRotation().name());
        }

        private static StructurePlaceSettings makeSettings(Rotation rotation, ResourceLocation location)
        {
            return new StructurePlaceSettings().setRotation(rotation).setMirror(Mirror.NONE).setRotationPivot(PIVOTS.get(location)).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }

        private static BlockPos makePosition(ResourceLocation location, BlockPos pos, int down)
        {
            return pos.offset(OFFSETS.get(location)).below(down);
        }

        private ResourceLocation getRandomLootTables(RandomSource random)
        {
            if (random.nextFloat() < 0.35f)
            {
                return FOTLootTables.Chests.SEAPOST_BARREL_COMBAT;
            }
            else if (random.nextFloat() < 0.2f)
            {
                return FOTLootTables.Chests.SEAPOST_BARREL_FIREWORK;
            }
            else
            {
                return FOTLootTables.Chests.SEAPOST_BARREL_SUPPLY;
            }
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box)
        {
            switch (name)
            {
                case "seapost_barrel" ->
                {
                    var blockEntity = level.getBlockEntity(pos.below());

                    if (blockEntity instanceof RandomizableContainer randomizableContainer)
                    {
                        randomizableContainer.setLootTable(this.getRandomLootTables(random), random.nextLong());
                    }
                }
                case "seapost_barrel_with_flower_pot" ->
                {
                    var blockEntity = level.getBlockEntity(pos.below());

                    if (blockEntity instanceof RandomizableContainer randomizableContainer)
                    {
                        randomizableContainer.setLootTable(this.getRandomLootTables(random), random.nextLong());
                    }
                    level.setBlock(pos, Util.getRandom(POTTED_BLOCKS, random).defaultBlockState(), Block.UPDATE_CLIENTS);
                }
                case "seapost_leather_worker" ->
                {
                    var villager = EntityType.VILLAGER.create(level.getLevel());
                    villager.setVillagerData(new VillagerData(VillagerType.PLAINS, VillagerProfession.LEATHERWORKER, 1));
                    villager.setPersistenceRequired();
                    villager.moveTo(pos, 0.0F, 0.0F);
                    villager.finalizeSpawn(level, level.getCurrentDifficultyAt(villager.blockPosition()), MobSpawnType.STRUCTURE, null, null);
                    level.addFreshEntityWithPassengers(villager);
                    level.setBlock(pos, Blocks.SPRUCE_TRAPDOOR.defaultBlockState().setValue(TrapDoorBlock.FACING, this.placeSettings.getRotation().rotate(Direction.NORTH)).setValue(TrapDoorBlock.HALF, Half.TOP).setValue(TrapDoorBlock.OPEN, true), Block.UPDATE_CLIENTS);
                }
                case "seapost_fisherman" ->
                {
                    var villager = EntityType.VILLAGER.create(level.getLevel());
                    villager.setVillagerData(new VillagerData(VillagerType.PLAINS, VillagerProfession.FISHERMAN, 1));
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
            var resourceLocation = new ResourceLocation(this.templateName);
            this.templatePosition = new BlockPos(this.templatePosition.getX(), generator.getSeaLevel() - OFFSETS.get(resourceLocation).getY(), this.templatePosition.getZ());
            super.postProcess(level, structureManager, generator, random, box, chunkPos, pos);
        }
    }
}