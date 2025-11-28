package com.stevekung.fishofthieves.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

record PosAndState(BlockPos pos, BlockState state) {}