package com.stevekung.fishofthieves.neoforge.mixin.client;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;

import com.stevekung.fishofthieves.block.HangingMangoFruitBlock;
import com.stevekung.fishofthieves.block.MangoFruitBlock;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;

@Mixin(value = { MangoFruitBlock.class, HangingMangoFruitBlock.class })
public class MixinMangoFruitBlock extends Block
{
    MixinMangoFruitBlock()
    {
        super(null);
    }

    @SuppressWarnings("removal")
    @Override
    public void initializeClient(Consumer<IClientBlockExtensions> consumer)
    {
        consumer.accept(new IClientBlockExtensions()
        {
            @Override
            public boolean areBreakingParticlesTinted(BlockState state, ClientLevel level, BlockPos pos)
            {
                return false;
            }
        });
    }
}