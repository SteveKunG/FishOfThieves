package com.stevekung.fishofthieves.mixin.level.levelgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.stevekung.fishofthieves.levelgen.MaterialRuleContextExtender;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.material.MaterialRuleContext;

@Mixin(MaterialRuleContext.class)
public class MixinMaterialRuleContext implements MaterialRuleContextExtender
{
    @Unique
    private ChunkAccess chunkAccess;

    @Override
    public ChunkAccess getChunkAccess()
    {
        return this.chunkAccess;
    }

    @Override
    public void setChunkAccess(ChunkAccess chunkAccess)
    {
        this.chunkAccess = chunkAccess;
    }
}