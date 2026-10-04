package com.stevekung.fishofthieves.mixin.level.levelgen;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.stevekung.fishofthieves.levelgen.ChunkAccessExtender;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.material.MaterialRuleContext;

@Mixin(MaterialRuleContext.class)
public class MixinMaterialRuleContext implements ChunkAccessExtender
{
    @Shadow
    @Final
    RandomState randomState;

    @Override
    public ChunkAccess fishofthieves$getChunkAccess()
    {
        return this.randomState.fishofthieves$getChunkAccess();
    }
}