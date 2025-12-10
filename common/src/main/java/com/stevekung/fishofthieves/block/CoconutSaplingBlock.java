package com.stevekung.fishofthieves.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoconutSaplingBlock extends SaplingBlock
{
    private static final VoxelShape AABB = Block.box(3, 0, 3, 13, 5, 13);
    private final TreeGrower oldCoconutTree;

    public CoconutSaplingBlock(TreeGrower treeGrower, TreeGrower oldCoconutTree, BlockBehaviour.Properties properties)
    {
        super(treeGrower, properties);
        this.oldCoconutTree = oldCoconutTree;
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier)
    {
        if (level instanceof ServerLevel serverLevel && entity instanceof Ravager && serverLevel.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING))
        {
            serverLevel.destroyBlock(pos, true, entity);
        }
        super.entityInside(state, level, pos, entity, insideBlockEffectApplier);
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random)
    {
        if (state.getValue(STAGE) == 0)
        {
            level.setBlock(pos, state.cycle(STAGE), Block.UPDATE_INVISIBLE);
        }
        else
        {
            if (random.nextInt(8) == 0)
            {
                this.oldCoconutTree.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
            }
            else
            {
                super.advanceTree(level, pos, state, random);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return AABB;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return AABB;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return state.is(Blocks.SAND);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type)
    {
        return false;
    }
}